package com.imooc.sell.service.impl;

import com.imooc.sell.change.OrderMasterChangeOrderDto;
import com.imooc.sell.dao.OrderDetailDao;
import com.imooc.sell.dao.OrderMasterDao;
import com.imooc.sell.dataobject.Flow;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enmus.OrderMasteEnum;
import com.imooc.sell.enmus.PayStatusEnum;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.FlowService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.service.Websocket;
import com.imooc.sell.util.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*订单实现类*/
@Service
@Slf4j
public class OrderImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private Websocket websocket;

    @Autowired
    private FlowService flowService;


    /*
    * 创建订单流程，涉及插入添加事物（@Transactional）,事物回滚
    * */
    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        String orderId = KeyUtils.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        //1.查询商品（数量，单价）
        for(OrderDetail orderDetail:orderDto.getOrderMasterList()){
            System.out.println(orderDetail.getProductId());
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo ==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价（对于Bigdecimal的增删改查在CSDN博客中有）
            //根据前端传过来的规矩，并没有传输给后台单价数据，所以单价数据只能在查找中获得
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            //值的拷贝
            BeanUtils.copyProperties(productInfo,orderDetail);
            //订单ID在创建订单的时候就应该生成了
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtils.getUniqueKey());
            orderDetailDao.save(orderDetail);
        }

        //3.写入数据库（订单详情表和订单表）
        OrderMaster orderMaster = new OrderMaster();
        //防止被赋值的值为空
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatusEnum.NOPAY.getCode());
        orderMaster.setOrderStatus(OrderMasteEnum.NEW.getCode());
        /*返回成功的值*/
        OrderMaster orderMasterChange = orderMasterDao.save(orderMaster);
        BeanUtils.copyProperties(orderMasterChange,orderDto);
        //4.扣库存
        List<CartDto> cartDtos = orderDto.getOrderMasterList().stream().map(e ->
                new CartDto(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());
        productInfoService.deleteCount(cartDtos);
        /*发送websocket消息*/
        websocket.sendMessage(orderDto.getOrderId());
        return orderDto;
    }

    /*
    * 查询单个订单
    * */
    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        /*判断集合是否为空*/
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderMasterList(orderDetailList);
        return orderDto;
    }

    /*
    * 分页查找订单列表
    * */
    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterList = orderMasterDao.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDto> orderDtoList = OrderMasterChangeOrderDto.conver(orderMasterList.getContent());
        /* PageImpl<OrderDto>需要三个参数，该类的集合类，pageable，总数*/
        return new PageImpl<OrderDto>(orderDtoList,pageable,orderMasterList.getTotalElements());
    }

    /*
    * 完成订单
    * */
    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        //查看订单状态
        /*只有新下单才能完结，其他的（取消或者已完成都不能完结订单）*/
        if(!orderDto.getOrderStatus().equals(OrderMasteEnum.NEW.getCode())){
            log.error("【完结订单】 订单状态错误 orderId = {} orderStatus = {}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_UPDATE_STATUS);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderMasteEnum.FINISH.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        /*判断订单是否修改成功*/
        if(result == null){
            log.error("【完结订单】 订单状态修改失败 result={}",orderMaster);
            throw new SellException(ResultEnum.ORDERMASTER_DB_UPDATE_ERROR);
        }
        return orderDto;
    }

    /*
     * 取消订单
     * */
    @Override
    @Transactional
    public OrderDto quit(OrderDto orderDto) {
        /*转换属性值*/
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderMasteEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确 orderId= {} orderStatus = {}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_UPDATE_STATUS);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderMasteEnum.QUIT.getCode());
        //值的拷贝
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster orderUpdate = orderMasterDao.save(orderMaster);
        if(orderUpdate == null){
            log.error("【取消订单】 订单状态修改失败 result={}",orderMaster);
            throw new SellException(ResultEnum.ORDERMASTER_DB_UPDATE_ERROR);
        }
        //判断列表
        if(CollectionUtils.isEmpty(orderDto.getOrderMasterList())){
            log.error("【取消订单】订单列表不存在 result = {} ",orderDto);
            throw new SellException(ResultEnum.ORDERMASTER_DETAIL_UPDATE_ERROR);
        }
        //修改库存
        List<CartDto> cartDtoList = orderDto.getOrderMasterList().stream().map(e->
                new CartDto(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.addCount(cartDtoList);
        //如果已支付就退款
        if(orderMaster.getPayStatus().equals(PayStatusEnum.PAYSUCCESS)){
            //TODO
        }
        return orderDto;
    }

    /*
    * 支付订单
    * */
    @Override
    @Transactional
    public OrderDto pay(OrderDto orderDto, Flow flow) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderMasteEnum.NEW.getCode())){
            log.error("【订单支付】 订单状态错误 orderId = {} orderStatus = {}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_UPDATE_STATUS);
        }
        //判断支付状态
        if(!orderDto.getPayStatus().equals(PayStatusEnum.NOPAY.getCode())){
            log.error("【订单支付】 支付状态错误 orderId = {} orderStatus = {}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.PAY_ERROR);
        }
        //修改支付状态
        orderDto.setPayStatus(PayStatusEnum.PAYSUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMasterDao.save(orderMaster);

        /*保存支付宝订单*/
        flowService.save(flow);
        return orderDto;
    }

    /*查询库中所有订单*/
    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
        List<OrderDto> orderDtoList = OrderMasterChangeOrderDto.conver(orderMasterPage.getContent());
        /* PageImpl<OrderDto>需要三个参数，该类的集合类，pageable，总数*/
        return new PageImpl<OrderDto>(orderDtoList,pageable,orderMasterPage.getTotalElements());
    }
}
