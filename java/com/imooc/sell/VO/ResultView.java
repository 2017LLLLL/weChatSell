package com.imooc.sell.VO;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

/*
* 返回给前端的总数据
* 因为data数据不固定所以给了一个泛型可根据类型而变化
* */
@Data
public class ResultView<T> {
    private Integer code;
    private String msg;

    private T data;
}
