package com.hwua.filter;

import com.hwua.mapper.UserMapper;
import com.hwua.util.JWTToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JWTFilter extends BasicHttpAuthenticationFilter {
    @Autowired
    private UserMapper userMapper;
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)){
            try {
                executeLogin(request,response);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        //判断请求路径
        String servletPath = req.getServletPath();
        if("login".equals(servletPath)){
            //判断请求头是否携带token
            //获取请求消息头信息或者路径信息    获取token
            String token = req.getHeader("token");
            if (token!=null&&token.trim()!=""){
                return true;
            }
            token = req.getParameter("token");
            if (token!=null&&token.trim()!=""){
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        //获取请求消息头信息或者路径信息    获取token
        String token = req.getHeader("token");
        if (token==null||token.trim()==""){
            token = req.getParameter("token");
        }
        //上述代码确保了token一定不为空
        /**
         * login 方法的参数是AuthenticationToken 对象
         * 两个办法:
         * 1)构建UsernamPasswordToken
         * 2)自定义一个token对象
         */
        JWTToken myJwtToken = new JWTToken();
        myJwtToken.setToken(token);
        getSubject(request, response).login(myJwtToken);
        return true;
    }

//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse resp = (HttpServletResponse) response;
//        String token = req.getHeader("token");
//        //判断token的值是否是真的
//        String username = JWTUtil.decodeToken(token, "username");
//        User paramUser = new User();
//        paramUser.setUsername(username);
//        //校验用户是否存在
//        User user = userMapper.selectByUsername(paramUser);
//        if (user==null){
//            //继续登录
//            return true;
//        }else{
//            //不用登陆
//            return false;
//        }
//    }
}
