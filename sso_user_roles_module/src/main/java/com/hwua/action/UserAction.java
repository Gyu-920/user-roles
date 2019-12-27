package com.hwua.action;

import com.hwua.domain.User;
import com.hwua.service.UserService;
import com.hwua.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserAction {
    @Autowired
    private RedisUtil redisUtil;
    @PostMapping("/user/login")
    public ResponseData login(@RequestBody User user)throws Exception{
        //拆解用户名
        String username = user.getUsername();
        //连接服务器校验
        User resultUser = userService.getUserByUsername(username);
        String password = user.getPassword();
        //比对信息
        if (resultUser==null||!(PasswordUtil.checkPassword(password,resultUser.getPassword(),username,1))){
            throw new Exception("用户名或密码错误!!!");
        }
        //构建返回对象,除此之外的错误对象交给全局处理异常处理
        ResponseData<User> userResponseData = new ResponseData<>();
        userResponseData.setCode(0);
        userResponseData.setT(user);
        userResponseData.setMessage("login success");
        String token = JWTUtil.createToken(username, password);
        userResponseData.setAccessToken(token);
        redisUtil.addToken(username,token);
        //System.out.println(redisUtil.getToken(username));
        return userResponseData;
    }
    @Autowired
    private UserService userService;
    //添加注解指定角色
//    @RequiresRoles("users")
    //指定权限
//    @RequiresPermissions("pwd")
    @PutMapping("/users/user/pwd")
    public ResponseData pwd(@RequestBody String oldPwd,String newPwd, HttpServletRequest req) throws Exception{
        ResponseData responseData = new ResponseData();
        String authorization = req.getParameter("authorization");
        String username = JWTUtil.decodeToken(authorization);
        System.out.println(username);
        User ResultUsername = userService.getUserByUsername(username);
        if (ResultUsername.getUsername().equals(oldPwd)) {
            userService.updatePassWord(username,newPwd);
        } else {
            return responseData.setMessage("密码错误");
        }
        return responseData.setCode(200).setMessage("成功获得信息！");
    }
}
