package com.imooc.sell.change;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/*
* 订单表单数据转成dto数据
* */
@Slf4j
public class OrderFormChangeOrderDto {

    public static OrderDto conver(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
        orderDto.setBuyerAddress(orderForm.getAddress());

        /*购物车前端传过来的是JSON字符串，需要进行解析。*/
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        }catch (Exception e){
            log.error("【对象转换】错误 String = {}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR );
        }
        orderDto.setOrderMasterList(orderDetailList);
        return  orderDto;
    }
}
