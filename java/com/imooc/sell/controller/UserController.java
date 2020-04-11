package com.imooc.sell.controller;

import com.imooc.sell.VO.ResultView;
import com.imooc.sell.dto.UserModel;
import com.imooc.sell.dto.UserView;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.UserService;
import com.imooc.sell.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/*在类上表明注解ResponseBody，那么在这个类的整个方法都会处于ResponseBody状态，返回JSON键值对*/
@RestController
@ResponseBody
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
/*继承了抛出异常就会走异常处理的方法*/
public class UserController {

    @Autowired
    private UserService userService;

    /*自动注入本身就是一个多线程的操作，每个HTTP请求对应一个HttpServletRequest */
    @Autowired
    private HttpServletRequest httpServletRequest;


    /*用哈希表对手机号和otp进行存储*/
    public static Map<String,String> phoneCode = new HashMap<String,String>();

    @RequestMapping(value = "/login")
    public ResultView login(@RequestParam(name = "telphone")String telphone,
                            @RequestParam(name = "password")String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验（非空验证）
        if(org.apache.commons.lang3.StringUtils.isEmpty(telphone)||
                                    StringUtils.isEmpty(password)){
            return null;
        }
        //用户登录服务（校验登录是否合法）
        UserModel userModel = userService.validateLogin(telphone,this.encodeByMD5(password));
        /*将登录凭证加入到用户登录成功的session内*/
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return ResultUtils.success();
    }

    /*用户注册接口*/
    @RequestMapping(value = "/register")
    public ResultView register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name = "password")String password,
                                     @RequestParam(name = "otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "gender")Integer gender) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        /*验证手机号跟otpCode相匹配*/
        //取出手机号对应的otpCode
        String inSessionOtpCode = this.httpServletRequest.getSession().getAttribute(telphone).toString();
       // String inSessionOtpCode = phoneCode.get(telphone);
        /*使用阿里巴巴的StringUtils.equals()可自动判空*/
        if(!StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        /*对应用户注册流程*/
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        /*使用MD5闪电加密，将明文变成密文*/
        /*encode传入byte单位*/
        userModel.setEncrptPassword(this.encodeByMD5(password));
        userService.regiter(userModel);
        return ResultUtils.success();
    }

    public String encodeByMD5(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        /*确定计算方法*/
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        /*加密字符串*/
        String finPwd = base64Encoder.encode(md5.digest(password.getBytes("utf-8")));
        return finPwd;
    }

    //用户获取otp短信接口（POST请求）
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST})
    public ResultView getOtp(@RequestParam(name="telphone")String telphone){
        //需要按照指定规则生成otp验证码（随机数生成）
        Random random = new Random();
        int randomint = random.nextInt(99999);//此时随机数取值：0-99999
        randomint +=10000;//此时随机数取值：10000-99999
        String otpCode = String.valueOf(randomint);
        //将otp验证码同对应用户的手机号关联
        //一般来说用redis进行关联，redis本身就是键值对形式，并且含有有效时间，可以被覆盖。
        //本例中使用HttpSession
        this.httpServletRequest.getSession().setAttribute(telphone,otpCode);
        //System.out.println("this.httpServletRequest.getSession()"+this.httpServletRequest.getSession().getAttribute(telphone).toString());
        //phoneCode.put(telphone,otpCode);
        //将otp验证码通过短信通道发送给用户(省略)
        System.out.println("telphone" + telphone + "&otpCode" + otpCode);
        /*该方法不需要返回任何参数，所以直接置空*/
        return ResultUtils.success(otpCode);
    }



    @RequestMapping("/get")
    public ResultView getUser(@RequestParam(name="id")Integer id){
        //调用service服务，获取对应id的用户对象并返回给前端
        UserModel usermodel  = userService.getUserById(id);
        /*当用户不存在时抛出错误类型之用户不存在*/
        if(usermodel == null){
            /*将对应枚举数据中某一个传给抛出异常的errorMessage*/
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        UserView userview =  UserChangeView(usermodel);
        return ResultUtils.success();
    }

    /*值的转化，给前端的值不能有敏感信息*/
    private UserView UserChangeView(UserModel usermode){
        if(usermode == null){
            return  null;
        }
        UserView userview = new UserView();
        BeanUtils.copyProperties(usermode,userview);
        return userview;
    }


}
