package com.imooc.sell.controller;


import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.util.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;

    /*
     * 获取商品列表
     * */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(name = "page",defaultValue = "1")Integer page ,
                             @RequestParam(name = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<ProductInfo> list = productInfoService.findAll(pageRequest);
        map.put("productInfoPage",list);
        map.put("indexPage",page);
        map.put("sizePage",size);
        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/onSale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productInfoService.onSale(productId);
        } catch (SellException e) {
            map.put("errorMesg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/seller/product/list");
        map.put("successMesg", "上架成功");
        return new ModelAndView("common/success", map);
    }

    /**
     * 商品下架
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/offSale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productInfoService.offSale(productId);
        } catch (Exception e) {
            map.put("errorMesg", e.getMessage());
            map.put("url", "/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("successMesg", "下架成功");
        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo", productInfo);
        }

        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/index", map);
    }

    /**
     * 保存/更新
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
//    @Cacheable(cacheNames = "product", key = "123")
//    @Cacheable(cacheNames = "product", key = "456")
//    @CachePut(cacheNames = "product", key = "123")
//    @CacheEvict(cacheNames = "product", allEntries = true, beforeInvocation = true)
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        /*校验数据是否有误*/
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        /*执行文件上传*/
        if (form.getProductIcon().isEmpty()) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        String fileName = form.getProductIcon().getOriginalFilename();
        /*本地地址*/
        String filePath = "E:/Download/";
        /*阿里云地址*/
        /*String filePath = "/usr/java/tomcat/apache-tomcat-8.5.39/webapps/image/";*/
        File dest = new File(filePath+fileName);
        try {
            form.getProductIcon().transferTo(dest);
            log.info("【文件上传】 上传成功");
        } catch (IOException e) {
            log.error("【文件上传】 文件上传失败");
            throw new SellException(ResultEnum.FILE_UP_ERROR);
        }


        ProductInfo productInfo = new ProductInfo();
        try {
            //如果productId为空, 说明是新增
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productInfoService.findOne(form.getProductId());
            } else {
                form.setProductId(KeyUtils.getUniqueKey());
            }
            String fileReadPath = "http://112.74.186.128:8080/image/"+fileName;
            productInfo.setProductIcon(fileReadPath);
            BeanUtils.copyProperties(form, productInfo);
            productInfoService.save(productInfo);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/seller/product/list");
        return new ModelAndView("common/success", map);
    }

}
