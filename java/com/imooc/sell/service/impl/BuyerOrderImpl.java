package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.BuyerOrderService;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerOrderImpl implements BuyerOrderService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
        return isOwnerOrder(openid,orderId);
    }

    @Override
    public OrderDto quitOrder(String openid, String orderId) {
        OrderDto orderDto =  isOwnerOrder(openid,orderId);
        if(orderDto == null){
            log.error("【取消订单】 查不到对应订单 order = {}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.quit(orderDto);
    }


    private OrderDto isOwnerOrder(String openid, String orderId){
        OrderDto orderDto = orderService.findOne(orderId);
        /*这个要进行非空判断*/
        if(orderDto == null){
            return null;
        }
        /*判断订单是否是自己的*/
        if(!orderDto.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【身份错误】 订单中OPENID不一致 order={},orderDto = {}",orderId,orderDto);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }

}
