package com.imooc.sell.util;


import com.imooc.sell.enmus.CodeEnum;

/*
* 枚举工具类
* */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> emunClass){
        for(T each:emunClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }

}
