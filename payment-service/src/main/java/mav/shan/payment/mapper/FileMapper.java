package mav.shan.payment.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mav.shan.common.entity.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper extends BaseMapper<FileDTO> {


    @Select("SELECT * FROM file WHERE ORDER BY id DESC LIMIT #{page} ,#{pageSize}")
    List<FileDTO> page(int page, int pageSize);

    @Select("SELECT count(*) FROM file")
    Long count();

    default boolean insertFile(String filename, String url, String suffix, String wordUrl) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(filename.trim() + "." + suffix);
        fileDTO.setFileUrl(url);
        fileDTO.setFileType(suffix);
        fileDTO.setWordUrl(wordUrl);
        int insert = this.insert(fileDTO);
        return insert > 0 ? true : false;
    }
}
