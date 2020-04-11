package com.imooc.sell.change;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/*
* 模型转换类
* */
public class OrderMasterChangeOrderDto {

    /*
    * 返回单个DTO
    * */
    public static OrderDto conver(OrderMaster orderMaster){
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }

    /*
    * 返回列表集合
    * */
    public static List<OrderDto> conver(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e->
                        conver(e)
                ).collect(Collectors.toList());
    }

}
