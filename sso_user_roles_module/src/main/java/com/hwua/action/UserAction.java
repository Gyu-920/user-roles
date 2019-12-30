package com.hwua.action;

import com.alibaba.fastjson.JSONObject;
import com.hwua.domain.User;
import com.hwua.filter.JWTFilter;
import com.hwua.service.UserService;
import com.hwua.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

    @PutMapping("/users/user/pwd")
    public ResponseData pwd(@RequestBody String Pwd, HttpServletRequest req) throws Exception{
        ResponseData responseData = new ResponseData();
        String token = JWTFilter.getToken(req);
        String username = JWTUtil.decodeToken(token);
        JSONObject json = JSONObject.parseObject(Pwd);
        String newPwd = json.getString("newPwd");
        String oldPwd = json.getString("oldPwd");
        User ResultUser = userService.getUserByUsername(username);
        if (ResultUser==null||!(PasswordUtil.checkPassword(oldPwd,ResultUser.getPassword(),username,1))) {
            return responseData.setMessage("密码输入错误");
        } else {
            Md5Hash md5Hash = new Md5Hash(newPwd,username,1);
            String password = md5Hash.toString();
            userService.updatePassWord(username,password);
            redisUtil.addToken(username,token);
//            System.out.println(redisUtil.getToken(username));
        }
        return responseData.setCode(0).setMessage("成功修改密码！").setAccessToken(token);
    }
    @PostMapping("/users")
    public ResponseData select(@RequestBody String msg,HttpServletRequest req)throws Exception{
        ResponseData responseData = new ResponseData();
        String token = JWTFilter.getToken(req);
        String username = JWTUtil.decodeToken(token);
        return responseData;
    }
    @PutMapping("/user/info")
    public ResponseData msg(@RequestBody String info,HttpServletRequest req)throws Exception{
        String token = JWTFilter.getToken(req);
        String tokenUsername = JWTUtil.decodeToken(token);
        ResponseData responseData = new ResponseData();
        JSONObject json = JSONObject.parseObject(info);
        //{"id":"","username":"zhangsan","realName":"张三","phone":"15098810033","email":"hehe@qq.com","status":"on","sex":"1"}
        String username = json.getString("username");
        String realName = json.getString("realName");
        String phone = json.getString("phone");
        String email = json.getString("email");
        String getStatus = json.getString("status");
        int sex = Integer.parseInt(json.getString("sex"));
        User ResultUser = userService.getUserByUsername(username);
        if (ResultUser==null||!username.equals(tokenUsername)){
            return responseData.setMessage("请输入正确的用户名");
        }else{
            if (getStatus.equals("on")){
                int status=1;
                userService.updateUserInfo(username,realName,phone,email,status,sex);
            }else {
                int status=2;
                userService.updateUserInfo(username,realName,phone,email,status,sex);
            }
        }
        return responseData.setCode(0).setMessage("保存成功！");
    }
    @GetMapping("/user/logout")
    public ResponseData loginOut(HttpServletRequest req)throws Exception{
        ResponseData responseData = new ResponseData();
        String token = JWTFilter.getToken(req);
        if (token==null||token.equals("")){
            throw new Exception("token不存在");
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
            String username = JWTUtil.decodeToken(token);
            redisUtil.deleteToken(username);
        }
        return responseData.setCode(0).setMessage("退出成功");
    }
}
