package com.imooc.sell.controller;


import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/*
* 卖家端订单
* */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerController {

    @Autowired
    private OrderService orderService;

    /*
    * 获取订单列表
    * */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(name = "page",defaultValue = "1")Integer page ,
                             @RequestParam(name = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<OrderDto> list = orderService.findList(pageRequest);
        map.put("orderDTOPage",list);
        map.put("indexPage",page);
        map.put("sizePage",size);
        return new ModelAndView("order/list",map);
    }

    /*
    * 取消订单
    * */
    @GetMapping("/cancel")
    public ModelAndView canel(@RequestParam(name = "orderId")String orderId,
                              Map<String,Object> map){
        try {
            OrderDto orderDto = orderService.findOne(orderId);
            orderService.quit(orderDto);
        }catch (Exception e){
            log.error("【查询单个订单错误】 发生异常{}",e);
            map.put("errorMesg", e.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("successMesg",ResultEnum.ORDER_QUIT_SUCCESS.getMessage());
        map.put("url","/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    /*
     * 订单详情
     * */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam(name = "orderId")String orderId,
                              Map<String,Object> map){
        OrderDto orderDto = new OrderDto();
        try {
            orderDto = orderService.findOne(orderId);
        }catch (Exception e){
            log.error("【查询单个订单详情】 发生异常{}",e);
            map.put("errorMesg", e.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDto",orderDto);
        return new ModelAndView("order/detail",map);
    }

    /*
     * 完结订单
     * */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam(name = "orderId")String orderId,
                              Map<String,Object> map){
        try {
            OrderDto orderDto = orderService.findOne(orderId);
            orderService.finish(orderDto);
        }catch (Exception e){
            log.error("【完结订单错误】 发生异常{}",e);
            map.put("errorMesg", e.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("successMesg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/seller/order/list");
        return new ModelAndView("common/success",map);
    }

}
