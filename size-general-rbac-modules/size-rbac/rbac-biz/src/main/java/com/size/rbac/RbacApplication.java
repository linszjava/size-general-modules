package com.size.rbac;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * RBAC 服务启动类
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.size")
@EnableDiscoveryClient
@MapperScan("com.size.rbac.mapper")
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
        log.info("RBAC 服务启动成功，端口 9502");
    }
}
