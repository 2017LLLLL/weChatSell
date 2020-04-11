package com.imooc.sell.enmus;

import lombok.Getter;



/*
* 订单状态枚举类
* */
@Getter
public enum OrderMasteEnum implements CodeEnum {
    NEW(0,"新订单"),
    FINISH(1,"订单完成"),
    QUIT(2,"已取消")
    ;
    private Integer code;
    private String message;

    //构造方法
    OrderMasteEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }


}
