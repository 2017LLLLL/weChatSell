package com.imooc.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/*
* 用于订单表单验证，属性为前端给的参数名
* */
@Data
public class OrderForm {
    @NotEmpty(message = "买家姓名必填")
    private String name;

    @NotEmpty(message = "买家电话必填")
    private String phone;

    @NotEmpty(message = "买家地址必填")
    private String address;

    @NotEmpty(message = "买家微信openid必填")
    private String openid;

    @NotEmpty(message = "购物车必填")
    private String items;





}
