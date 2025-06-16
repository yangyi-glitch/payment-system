package mav.shan.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


@Data
public class UserEcxelVO {
    @ExcelProperty("用户账户")
    private String account;
    @ExcelProperty("用户姓名")
    private String username;
    @ExcelProperty("用户密码")
    private String password;
    @ExcelProperty("用户邮箱")
    private String email;
    @ExcelProperty("用户角色")
    private String role;
}
