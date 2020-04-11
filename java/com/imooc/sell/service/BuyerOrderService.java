package com.imooc.sell.service;


import com.imooc.sell.dto.OrderDto;

/*
* 安全业务逻辑类，必须要根据OPENID和
* */
public interface BuyerOrderService {

    /*查询单个订单*/
    OrderDto findOrderOne(String openid,String orderId);

    /*取消订单*/
    OrderDto quitOrder(String openid,String orderId);

}
