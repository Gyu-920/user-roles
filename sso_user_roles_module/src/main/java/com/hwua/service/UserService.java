package com.hwua.service;

import com.hwua.domain.User;
import com.hwua.util.ResponseData;
import org.springframework.stereotype.Service;

@Service
public interface UserService  {
    User getUserByUsername(User oneuser);
    //根据用户名查询用户权限
    User getUserInfoByUsername(String username);
    void updatePassWord(String username,String password);
}
