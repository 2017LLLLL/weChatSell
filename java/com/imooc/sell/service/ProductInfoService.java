package com.imooc.sell.service;


import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import org.apache.tomcat.jni.Proc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/*
* 商品信息service
*/
public interface ProductInfoService {

    ProductInfo findOne(String productId);
    //查询所有上架的商品
    List<ProductInfo> findAllByStatus();
    //通过分页查询所有商品
    Page<ProductInfo> findAll(Pageable pageable);
    //新增或修改
    ProductInfo save(ProductInfo productInfo);
    //加库存
    void addCount(List<CartDto> cartDtos);
    //减库存
    void deleteCount(List<CartDto> cartDtos);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);

}
