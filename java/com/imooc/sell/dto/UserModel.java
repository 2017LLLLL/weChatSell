package com.imooc.sell.dto;


import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/*真正用户模型，包含了密码字段；但是在entity中用户的POJO对应数据库列表，无密码字段*/
@Data
public class UserModel {
    private Integer id;
    private String name;
    @NotNull(message = "性别不能为空")
    private Byte gender;
    @NotNull(message = "年龄不能为空")
    @Min(value = 0,message = "年龄必须大于0")
    @Max(value = 150,message = "年龄必须小于150")
    private Integer age;
    private String telphone;
    private String registerMode;
    private String thirdPartyId;
    private String encrptPassword;

}
