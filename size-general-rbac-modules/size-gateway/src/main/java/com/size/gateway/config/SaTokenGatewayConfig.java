package com.size.gateway.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.size.common.core.result.R;
import com.size.common.core.result.ResultCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                        "/favicon.ico"
                )
                .setAuth(obj -> SaRouter.match("/**", r -> StpUtil.checkLogin()))
                .setError(e -> {
                    R<Void> result = R.fail(ResultCode.UNAUTHORIZED.getMessage());
                    result.setCode(ResultCode.UNAUTHORIZED.getCode());
                    return JSONUtil.toJsonStr(result);
                });
    }
}
