package com.size.gateway.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.reactor.context.SaReactorSyncHolder;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.size.common.core.result.R;
import com.size.common.core.result.ResultCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * Gateway Sa-Token 鉴权配置
 */
@Configuration
public class SaTokenGatewayConfig {

    @Bean
    public SaReactorFilter saReactorFilter() {
        return new SaReactorFilter()
                .addInclude("/**")
                .addExclude(
                        "/api/auth/login",
                        "/api/auth/health",
                        "/favicon.ico",
                        // Knife4j 网关聚合文档（开发环境）
                        "/doc.html",
                        "/doc.html/**",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/size-auth/**",
                        "/size-rbac/**",
                        "/size-system/**"
                )
                .setAuth(obj -> SaRouter.match("/**", r -> StpUtil.checkLogin()))
                .setError(e -> {
                    var exchange = SaReactorSyncHolder.getContext();
                    if (exchange != null) {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    }
                    R<Void> result = R.fail(ResultCode.UNAUTHORIZED.getMessage());
                    result.setCode(ResultCode.UNAUTHORIZED.getCode());
                    return JSONUtil.toJsonStr(result);
                });
    }
}
