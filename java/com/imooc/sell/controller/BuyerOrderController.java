package com.imooc.sell.controller;


import com.imooc.sell.VO.ResultView;
import com.imooc.sell.change.OrderFormChangeOrderDto;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.BuyerOrderService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 订单控制类
* */
@Controller
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private BuyerOrderService buyerOrderService;

    //创建订单，返回JSON数据
    @PostMapping("/create")
    public ResultView<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //判断表单校验是否有误
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数错误 order={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDto orderDto = OrderFormChangeOrderDto.conver(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderMasterList())){
            log.error("【创建订单】 购物车为空");
            throw new SellException(ResultEnum.CARTS_ERROR);
        }
        OrderDto createResult = orderService.create(orderDto);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultUtils.success(map);
    }

    //创建订单，返回支付页面
    @PostMapping("/createPay")
    @ResponseBody
    public ResultView<Map<String,String>> createPay(@RequestParam(name = "list")String list){
        System.out.println(list);
        OrderForm orderForm = new OrderForm();
        orderForm.setAddress("nnn");
        orderForm.setOpenid("123456");
        orderForm.setName("test");
        orderForm.setPhone("123123");
        /*String cart = "[{\n" +
                "    productId: "+productId+"," +
                "    productQuantity: "+amount+"" +
                "}]";*/
        orderForm.setItems(list);
        OrderDto orderDto = OrderFormChangeOrderDto.conver(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderMasterList())){
            log.error("【创建订单】 购物车为空");
            throw new SellException(ResultEnum.CARTS_ERROR);
        }
        OrderDto createResult = orderService.create(orderDto);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultUtils.success(map);
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
        return new ModelAndView("pay/detail",map);
    }


    //订单列表
    @GetMapping("/list")
    public ResultView<List<OrderDto>> orderList(@RequestParam("openid")String openid,
                                                @RequestParam(value = "page",defaultValue = "0")Integer page,
                                                @RequestParam(value = "size",defaultValue = "10")Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("【查看订单列表】 openid不能为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDto> orderDtoPage  = orderService.findList(openid,pageRequest);
        return ResultUtils.success(orderDtoPage.getContent());
    }

    //订单详情
    @GetMapping("/details")
    public ResultView<OrderDto> detail(@RequestParam("openid")String openid,
                                       @RequestParam("orderId")String orderId){
        //openid在这里用于表示安全性，不能直接传一个orderId就能查询信息
        OrderDto orderDto = buyerOrderService.findOrderOne(openid,orderId);
        return ResultUtils.success(orderDto);

    }
    @PostMapping("/quit")
    public ResultView<OrderDto> quit(@RequestParam("openid")String openid,
                                     @RequestParam("orderId")String orderId){
        //TODO，安全性
        buyerOrderService.findOrderOne(openid,orderId);
        return ResultUtils.success();
    }
    //取消订单



}
