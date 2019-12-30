package com.hwua.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //添加token
    public void addToken(String username,String token){
        redisTemplate.opsForValue().set(username,token,30, TimeUnit.MINUTES);
    }
    public String getToken(String token){
        String password = redisTemplate.opsForValue().get(token);
        return password;
    }
    public void deleteToken(String username){
       redisTemplate.delete(username);
    }
}
