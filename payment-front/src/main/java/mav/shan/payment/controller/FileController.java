package mav.shan.payment.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import mav.shan.common.entity.FileDTO;
import mav.shan.payment.service.file.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mav.shan.common.utils.ResultUtils;
import mav.shan.common.vo.req.FileReqVO;

import javax.annotation.Resource;

import static mav.shan.common.utils.ResultUtils.success;

@RequestMapping("/file")
@RestController
public class FileController {

    @Resource
    private FileService fileService;

    @GetMapping("/list")
    public ResultUtils list(FileReqVO reqVO) {
        Page<FileDTO> fileDTOPage = fileService.fileList(reqVO);
        return success(fileDTOPage);
    }
}
