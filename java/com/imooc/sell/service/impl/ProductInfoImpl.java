package com.imooc.sell.service.impl;

import com.imooc.sell.dao.ProductInfoDao;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.enmus.ProductStatusEnum;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoImpl implements ProductInfoService {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDao.findOne(productId);
    }

    @Override
    public List<ProductInfo> findAllByStatus() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }

    /*
    * 增加库存
    * */
    @Override
    @Transactional
    public void addCount(List<CartDto> cartDtos) {
        for(CartDto cartDto:cartDtos){
            /*遍历取出商品*/
            ProductInfo productInfo = productInfoDao.findOne(cartDto.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            /*加库存*/
            Integer result = productInfo.getProductStock() + cartDto.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }
    }

    /*在这里添加事物，要么全部都能成功保存。要么一个都保存不了*/
    @Override
    @Transactional
    public void deleteCount(List<CartDto> cartDtos) {
        for(CartDto cartDto:cartDtos){
            ProductInfo productInfo = productInfoDao.findOne(cartDto.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfoDao.findOne(cartDto.getProductId()).getProductStock() - cartDto.getProductQuantity();
            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            /*修改库存数量*/
            productInfo.setProductStock(result);
            /*保存入库*/
            productInfoDao.save(productInfo);
        }
    }

    /*
    * 商品上架
    * */
    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        ProductInfo productInfoUpdate = productInfoDao.save(productInfo);
        return productInfoUpdate;
    }

    /*
    * 商品下架
    * */
    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoDao.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        ProductInfo productInfoUpdate = productInfoDao.save(productInfo);
        return productInfoUpdate;
    }
}
