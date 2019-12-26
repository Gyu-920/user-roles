package com.hwua.util;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTToken implements AuthenticationToken {
    private String  token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
