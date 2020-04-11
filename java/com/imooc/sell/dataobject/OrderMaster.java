package com.imooc.sell.dataobject;

import com.imooc.sell.enmus.OrderMasteEnum;
import com.imooc.sell.enmus.PayStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/*
 * 商品订单实体类
 * */
@Entity
@Data
public class OrderMaster {

    @Id
    private String orderId;
    private String buyerName;
    private String buyerAddress;
    private String buyerPhone;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus = OrderMasteEnum.NEW.getCode();
    private Integer payStatus = PayStatusEnum.NOPAY.getCode();
    private Date createTime;
    private Date updateTime;

}
