package com.imooc.sell.util;


import java.util.Random;

/*
*  生成主键工具类
* */
public class KeyUtils {


    //synchronized是多线程的关键字
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        //获取系统毫秒数
        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
