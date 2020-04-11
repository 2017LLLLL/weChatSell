package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.enmus.OrderMasteEnum;
import com.imooc.sell.enmus.PayStatusEnum;
import com.imooc.sell.util.EnumUtil;
import com.imooc.sell.util.serializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
* 用与不同层之间的数据转换的DTO
* */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    private String orderId;
    private String buyerName;
    private String buyerAddress;
    private String buyerPhone;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    private Integer payStatus;
    /*使用工具将时间序列化*/
    @JsonSerialize(using = serializer.class)
    private Date createTime;
    @JsonSerialize(using = serializer.class)
    private Date updateTime;
    private List<OrderDetail> orderMasterList = new ArrayList<>();

    /*给前端模板用于对数字和字符串的转换*/
    @JsonIgnore
    public OrderMasteEnum getOrderMasteEnum(){
        return EnumUtil.getByCode(orderStatus,OrderMasteEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }

}

