package mav.shan.payment.service.file;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import entity.FileDTO;
import lombok.extern.slf4j.Slf4j;
import mav.shan.payment.mapper.FileMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileDTO> implements FileService {
    @Resource
    private FileMapper fileMapper;

    @Override
    public Page<FileDTO> fileList() {
        Page<FileDTO> page = new Page<>(1, 10);
        Page<FileDTO> fileDTOPage = fileMapper.selectPage(page, new LambdaQueryWrapper<>());
        return fileDTOPage;
    }
}
