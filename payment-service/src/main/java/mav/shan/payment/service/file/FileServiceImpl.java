package mav.shan.payment.service.file;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import mav.shan.common.entity.FileDTO;
import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.mapper.FileMapper;
import mav.shan.payment.start_minio.minio.MinioService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import mav.shan.common.vo.req.FileReqVO;

import javax.annotation.Resource;

import java.util.Objects;

import static mav.shan.common.constants.RedisConstants.FILE_KEY_PREFIX;
import static mav.shan.common.enums.ContentTypeEnums.getContentType;
import static mav.shan.common.utils.FileUtils.*;
import static mav.shan.common.utils.converter.PDFToWordConverter.escalateConvert_pdf;
import static mav.shan.common.utils.converter.WordToPDFConverter.escalateConvert_word;

@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDTO> implements FileService {
    @Resource
    private FileMapper fileMapper;
    @Resource
    private MinioService minioService;

    @Override
    public Page<FileDTO> fileList(FileReqVO reqVO) {
        Page<FileDTO> page = new Page<>(1, 10);
        Page<FileDTO> fileDTOPage = fileMapper.selectPage(page, new LambdaQueryWrapper<>());
        return fileDTOPage;
    }

    @Override
    public boolean upload(MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            return false;
        }
        //获取后缀
        String suffix = getFileSuffix(file.getOriginalFilename());
        //拼接路径
        String url = getFileUrl(suffix, file.getOriginalFilename());
        //上传minio
        boolean upload = minioService.upload(url, file, suffix);
        //保存文件数据库
        if (upload) {
            String wordUrl = "";
            if ("docx".equals(suffix) || "doc".equals(suffix)) {
                wordUrl = this.convert(file).getWordUrl();
            }
            boolean insert = fileMapper.insertFile(file.getOriginalFilename().split("\\.")[0], url, suffix, wordUrl);
            if (!insert) {
                minioService.remove(url);
                return insert;
            }
        }
        return true;
    }

    @Override
    public FileDTO convert(MultipartFile file) {
        FileDTO fileDTO = new FileDTO();
        try {
            //获取后缀
            String suffix = getFileSuffix(file.getOriginalFilename());
            switch (suffix) {
                case "pdf":
                    byte[] bytes_word = escalateConvert_pdf(file.getInputStream());
                    String url_word = conventerUrl(file.getOriginalFilename(), "docx");
                    minioService.upload(bytes_word, getContentType("docx"), url_word);
                    fileDTO.setFileName(conventerName(file.getOriginalFilename(), "docx"));
                    fileDTO.setFileUrl(url_word);
                    break;
                case "docx":
                    byte[] bytes_pdf = escalateConvert_word(file.getInputStream());
                    String url_pdf = conventerUrl(file.getOriginalFilename(), "pdf");
                    minioService.upload(bytes_pdf, getContentType("pdf"), url_pdf);
                    fileDTO.setFileName(conventerName(file.getOriginalFilename(), "pdf"));
                    fileDTO.setFileUrl(url_pdf);
                    break;
                default:
                    return null;
            }
            String http_url = minioService.perviewUrl(fileDTO.getFileUrl());
            fileDTO.setWordUrl(http_url);
            return fileDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String previewUrl(Long id) {
        FileDTO fileDTO = fileMapper.selectById(id);
        if (Objects.isNull(fileDTO)) {
            return null;
        }
        if ("docx".equals(fileDTO.getFileType()) || "doc".equals(fileDTO.getFileType())) {
            return fileDTO.getWordUrl();
        }
        return minioService.perviewUrl(fileDTO.getFileUrl());
    }

    private String formatKey(String key) {
        return String.format(FILE_KEY_PREFIX, key);
    }
}
