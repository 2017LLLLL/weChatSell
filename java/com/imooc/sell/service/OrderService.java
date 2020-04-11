package com.imooc.sell.service;


import com.imooc.sell.dataobject.Flow;
import com.imooc.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 订单的service蹭
* */
public interface OrderService {
    //创建订单
    OrderDto create(OrderDto orderDto);
    //查询单个订单
    OrderDto findOne(String orderId);
    //根据openID查询所有订单
    /*不会返回具体订单*/
    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);
    //完成订单
    OrderDto finish(OrderDto orderDto);
    //取消订单
    OrderDto quit(OrderDto orderDto);
    //支付订单
    OrderDto pay(OrderDto orderDto,Flow flow);
    //查询所有id
    Page<OrderDto> findList(Pageable pageable);
}
