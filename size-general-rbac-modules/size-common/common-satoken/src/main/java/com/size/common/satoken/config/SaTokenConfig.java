package com.size.common.satoken.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.SaTokenDaoRedisJackson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 配置 作用是配置sa-token的dao层 用于将token存储到redis中 如果不配置 则默认使用内存存储 
 */
@Configuration
public class SaTokenConfig {

    @Bean
    public SaTokenDao saTokenDao() {
        return new SaTokenDaoRedisJackson();
    }
}
