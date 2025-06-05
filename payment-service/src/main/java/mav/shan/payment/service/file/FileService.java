package mav.shan.payment.service.file;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import entity.FileDTO;


public interface FileService extends IService<FileDTO> {
    /**
     * 获取附件列表
     *
     * @return
     */
    Page<FileDTO> fileList();
}
