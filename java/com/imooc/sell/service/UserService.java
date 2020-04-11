package com.imooc.sell.service;

import com.imooc.sell.dto.UserModel;


public interface UserService {
    UserModel getUserById(Integer id);
    void regiter(UserModel userModel);
    UserModel validateLogin(String telphone, String password);
}
