package com.imooc.sell.dao;


import com.imooc.sell.dataobject.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByTelphone(String telphone);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}