package com.hwua.config;

import com.auth0.jwt.JWT;
import com.hwua.filter.JWTFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
        //过滤器工厂对象
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("jWTFilter",new JWTFilter());
        //shiroFilterFactoryBean.setFilters(filters);
        //属性注值
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
        //登录界面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //成功之后的界面
        shiroFilterFactoryBean.setSuccessUrl("/home.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/500.html");
        //不需要认证就可以访问的页面
       shiroFilterFactoryBean.setUnauthorizedUrl("/main.html");

        //获取过滤器的集合
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();

        //设置集合的内容
        /*
        第一个参数是  对应的资源
        第二个参数是  过滤器的名字
         */
        filterChainDefinitionMap.put("/user/login","anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/error/**","anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/layui/**","anon");
        filterChainDefinitionMap.put("/**","jWTFilter");
//        filterChainDefinitionMap.put("/**","authc");
        return shiroFilterFactoryBean;
    }
    //构建安全管理器
    @Bean("securityManager")
    public DefaultWebSecurityManager getSecurityManager(){
        //构建DefaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //设置自定义规则
        defaultWebSecurityManager.setRealm(getMyRealm());
        return defaultWebSecurityManager;
    }

    //提供自定义的realm类对象
    @Bean("myRealm")
    public MyRealm getMyRealm(){
        MyRealm myRealm = new MyRealm();
        //设置加密算法
        myRealm.setCredentialsMatcher(getHashedCredentialsMatcher());
        //myRealm.isCachingEnabled()
        return myRealm;
    }

    //提供自定义的盐值加密的类
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        //构建盐值加密类,指定加密方式为MD5
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("MD5");
        //加盐次数
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    //配置权限验证的注解解析器
    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(){
        //构建解析器对象
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        //设置SecurityManager对象
        authorizationAttributeSourceAdvisor.setSecurityManager(getSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
