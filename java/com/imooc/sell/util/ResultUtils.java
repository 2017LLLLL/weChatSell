package com.imooc.sell.util;

import com.imooc.sell.VO.ResultView;


/*
* 对返回类型的封装
* */
public class ResultUtils {

    public static ResultView success(Object object){
        ResultView resultView = new ResultView();
        resultView.setCode(0);
        resultView.setMsg("ok");
        resultView.setData(object);
        return resultView;
    }

    public static ResultView success(){
        ResultView resultView = new ResultView();
        resultView.setCode(0);
        resultView.setMsg("ok");
        return resultView;
    }

    public static ResultView error(Object object){
        ResultView resultView = new ResultView();
        resultView.setCode(1);
        resultView.setMsg("error");
        resultView.setData(object);
        return resultView;
    }

    public static ResultView error(int code,String object){
        ResultView resultView = new ResultView();
        resultView.setCode(code);
        resultView.setMsg(object);
        return resultView;
    }

}
