package com.imooc.sell.exception;


import com.imooc.sell.enmus.OrderMasteEnum;
import com.imooc.sell.enmus.ResultEnum;
import lombok.Getter;

/*
* 异常类，用于处理报错的异常。
* */
@Getter
public class SellException extends RuntimeException {

    private Integer code;


    /*将错误类信息传递给参数，因为RuntimeException类中就包含有输出方法*/
    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
