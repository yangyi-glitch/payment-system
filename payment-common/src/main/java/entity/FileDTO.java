package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("file")
@Data
public class FileDTO {
    /**
     * 文件名称
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件url
     */
    private String fileUrl;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * word预览url
     */
    private String wordUrl;
}
