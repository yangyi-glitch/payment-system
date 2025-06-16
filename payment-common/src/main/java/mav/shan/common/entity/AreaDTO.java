package mav.shan.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("area")
@Data
public class AreaDTO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer type;

    private Long parentId;
}
