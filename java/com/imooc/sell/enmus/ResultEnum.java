package com.imooc.sell.enmus;

import lombok.Getter;

/*
* 商品异常类
* */
@Getter
public enum  ResultEnum {

    SUCCESS(0,"成功"),

    PARAM_ERROR(1,"参数错误"),

    CARTS_ERROR(2,"购物车为空"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    STOCK_ERROR_LESSTHAN(11,"库存不够"),

    ORDER_NOT_EXIST(12,"订单不存在"),

    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),

    ORDERMASTER_UPDATE_ERROR(14,"订单修改失败，订单不存在"),

    ORDERMASTER_UPDATE_STATUS_ERROR(15,"订修改失败，修改订单状态失败"),

    ORDERMASTER_DETAIL_UPDATE_ERROR(16,"订单修改失败，订单详情不存在"),

    ORDERMASTER_DB_UPDATE_ERROR(16,"订单修改失败，数据库修改失败"),

    ORDER_UPDATE_STATUS(17,"订单修改失败，订单状态不正确"),

    PAY_ERROR(18,"订单支付失败，支付状态错误"),

    ORDER_OWNER_ERROR(19,"订单OPENID不匹配"),

    ORDER_QUIT_SUCCESS(20,"订单取消成功"),

    ORDER_FINISH_SUCCESS(21,"订单完结成功"),

    PRODUCT_STATUS_ERROR(22,"商品状态不正确"),

    FILE_UP_ERROR(23,"上传文件失败") ,

    CATGEGORY_EXIST(24,"类目以存在") ,
    ;



    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
