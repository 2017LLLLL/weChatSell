package com.imooc.sell.dao;

import com.imooc.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
*  商品类Dao层
*/
public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {
    //查询上架商品
    List<ProductInfo>  findByProductStatus(Integer productStatus);
}
