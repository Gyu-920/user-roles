package com.hwua.service.impl;

import com.hwua.domain.User;
import com.hwua.mapper.UserMapper;
import com.hwua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public User getUserByUsername(User oneUser) {
        User user = userMapper.selectByUsername(oneUser);
        return user;
    }

    @Override
    public User getUserInfoByUsername(String username) {
        User user = userMapper.selectUserInfoByUsername(username);
        return user;
    }

    @Override
    public void updatePassWord(String username, String password) {
        userMapper.updateByUsername(username,password);
    }
}
