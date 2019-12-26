package com.hwua.sso_user_roles_module;

import com.hwua.util.PasswordUtil;
import org.apache.shiro.crypto.hash.Md5Hash;

public class Check {
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","zhangsan",1);
        System.out.println(md5Hash);
        String s = md5Hash.toString();
        boolean checkPassword = PasswordUtil.checkPassword("123456", s, "zhangsan", 1);
        System.out.println(checkPassword);
    }
}
