package com.size.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证服务启动类
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.size")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.size")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
        log.info("认证服务启动成功，端口 9501");
    }
}
