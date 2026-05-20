package com.size.auth.controller;

import com.size.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证接口
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public R<String> health() {
        return R.ok("auth-service is running");
    }
}
