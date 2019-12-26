package com.hwua.action;

import com.hwua.domain.User;
import com.hwua.service.UserService;
import com.hwua.util.ResponseData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAction {
    @PostMapping("/user/login")
    public ResponseData login(@RequestBody User user){
        //获取当前对象
        Subject subject = SecurityUtils.getSubject();
        //构建UsernamePasswordToken对象
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        //执行登录
        subject.login(usernamePasswordToken);
        ResponseData<User> userResponseData = new ResponseData<>();
        userResponseData.setCode(200);
        userResponseData.setMessage("login success");
        userResponseData.setT(user);
        return userResponseData;
    }
    @Autowired
    private UserService userService;
    //添加注解指定角色
//    @RequiresRoles("users")
    //指定权限
//    @RequiresPermissions("pwd")
    @PutMapping("/users/user/pwd")
    public void pwd(String oldPwd,String newPwd,String rePass) throws Exception{
        System.out.println(oldPwd+"   "+newPwd+"   "+rePass);
    }
//    public ResponseData updatePassWorld(String username,String password) throws Exception{
//
//        userService.updatePassWord(username,password);
//        return new ResponseData();
//    }
}
