package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
* 类目返回给前端的信息
* */
@Data
public class CategoryView<T> {
    /*
    * 使用JsonProperty可以把键值对给序列化。
    * */
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    private T foods;

}
