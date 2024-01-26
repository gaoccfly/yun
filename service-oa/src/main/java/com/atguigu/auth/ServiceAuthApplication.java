package com.atguigu.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//        (exclude = {
//        //activiti 默认整合security，屏蔽Security认证
//        SecurityAutoConfiguration.class,
//        ManagementWebSecurityAutoConfiguration.class
//})
@MapperScan("com.atguigu.auth.mapper")
@ComponentScan("com.atguigu")
public class ServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }

}