package com.imooc.sell.enmus;

import lombok.Getter;

/*
*  商品状态
* */
@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"商品已上架"),
    DOWN(1,"商品未上架")
    ;
    private Integer code;
    private String message;

    //构造方法
    ProductStatusEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

}
