package com.hwua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hwua.mapper")
public class SsoUserRolesModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoUserRolesModuleApplication.class, args);
    }

}
