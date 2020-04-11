package com.imooc.sell.dto;


import lombok.Data;

/*
* 购物车Dto
* */
@Data
public class CartDto {

    private String ProductId;
    /*购买商品数量*/
    private Integer ProductQuantity;

    public CartDto(String productId, Integer productQuantity) {
        ProductId = productId;
        ProductQuantity = productQuantity;
    }
}
