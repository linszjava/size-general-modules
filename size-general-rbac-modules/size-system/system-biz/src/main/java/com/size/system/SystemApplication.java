package com.size.system;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 系统服务启动类
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.size")
@EnableDiscoveryClient
@MapperScan("com.size.system.mapper")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        log.info("系统服务启动成功，端口 9503");
    }
}
