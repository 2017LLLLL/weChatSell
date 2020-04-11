package com.imooc.sell.handler;

import com.imooc.sell.VO.ResultView;
import com.imooc.sell.exception.ResponseBankException;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.util.ResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常捕获
 */
@ControllerAdvice
public class SellExceptionHandler {
    /*
    * 对SellException异常的处理
    * */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultView handlerSellerException(SellException e) {
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /*
    * 如果抛出异常需要返回错误状态码（403,405等），可以用过这个处理
    * */
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleResponseBankException() {

    }
}
