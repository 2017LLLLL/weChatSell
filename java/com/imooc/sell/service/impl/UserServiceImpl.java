package com.imooc.sell.service.impl;

import com.imooc.sell.dao.UserMapper;
import com.imooc.sell.dao.UserPasswordMapper;
import com.imooc.sell.dataobject.User;
import com.imooc.sell.dataobject.UserPassword;
import com.imooc.sell.dto.UserModel;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserPasswordMapper userPasswordMapper;


    /*单用户查询*/
    @Override
    public UserModel getUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null) {
            return null;
        }
        /*通过用户ID获取对应的加密密码*/
        UserPassword userpassword = userPasswordMapper.selectByUserId(user.getId());
        return userModelChange(user,userpassword);
    }

    /*用户注册*/
    @Override
    /*@Transactional:事务管理注解*/
    @Transactional
    public void regiter(UserModel userModel){
        if(userModel == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        /*使用类型转化能存储到对应数据库*/
        User user = modelChangeDB(userModel);
        try{
            userMapper.insertSelective(user);
        }catch(DuplicateKeyException ex){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        /*如果有外键关联都这么用，在插入后系统会赋予自增的ID，这个主键的ID刚好是外键赋值*/
        userModel.setId(user.getId());
        UserPassword userPassword = modelChangePasswordDB(userModel);
        userPasswordMapper.insertSelective(userPassword);
        return;
    }
    /*用户登录*/
    @Override
    public UserModel validateLogin(String telphone, String password) {
        //根据用户手机获取信息
        User user = userMapper.selectByTelphone(telphone);
        if(user == null){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        UserPassword userPassword = userPasswordMapper.selectByUserId(user.getId());
        /*模型转化*/
        UserModel userModel = userModelChange(user,userPassword);
        //与密码进行比对
        if(userModel.getEncrptPassword().equals(password)){
            return userModel;
        }else{
            System.out.println("2");
            return null;
        }
    }

    /*UserModel->UserPassword*/
    private UserPassword modelChangePasswordDB(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPassword userPassword = new UserPassword();
        userPassword.setEncrptPassword(userModel.getEncrptPassword());
        userPassword.setUserId(userModel.getId());
        return userPassword;
    }



    /*UserModel->User数据库模型*/
    private User modelChangeDB(UserModel userModel){
        if(userModel == null){
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userModel,user);
        return user;
    }


    /*定义一个私有保护方法，用于模型的转换*/
    private UserModel userModelChange(User user, UserPassword userpassword){
        if(user == null){
            return null;
        }
        UserModel usermodel = new UserModel();
        /*将user中的数据拷贝到usermodel中*/
        BeanUtils.copyProperties(user,usermodel);
        if(userpassword != null){
            usermodel.setEncrptPassword(userpassword.getEncrptPassword());
        }
        return usermodel;
    }
}

