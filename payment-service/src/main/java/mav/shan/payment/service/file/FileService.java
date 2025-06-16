package mav.shan.payment.service.file;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import mav.shan.common.entity.FileDTO;
import org.springframework.web.multipart.MultipartFile;
import mav.shan.common.vo.req.FileReqVO;


public interface FileService extends IService<FileDTO> {
    /**
     * 获取附件列表
     *
     * @return
     */
    Page<FileDTO> fileList(FileReqVO reqVO);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    boolean upload(MultipartFile file);

    /**
     * 文件转换
     * @param file
     * @return
     */
    FileDTO convert(MultipartFile file);

    /**
     * 获取预览链接 一小时过期时间
     *
     * @param id
     * @return
     */
    String previewUrl(Long id);
}
