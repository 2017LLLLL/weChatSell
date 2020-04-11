package com.imooc.sell.enmus;

import lombok.Getter;

/*
* 支付状态枚举类
* */
@Getter
public enum  PayStatusEnum implements CodeEnum{
    NOPAY(0,"未支付"),
    PAYSUCCESS(1,"支付成功"),
    ;
    private Integer code;
    private String message;

    //构造方法
    PayStatusEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
