package com.size.auth.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.size.auth.dto.LoginRequest;
import com.size.auth.dto.LoginVO;
import com.size.auth.service.AuthService;
import com.size.common.core.result.R;
import com.size.rbac.api.dto.AuthInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证接口
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @SaIgnore
    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public R<String> health() {
        return R.ok("auth-service is running");
    }

    @SaIgnore
    @Operation(summary = "登录")
    @PostMapping("/login")
    public R<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        return R.ok(authService.login(request));
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public R<AuthInfoDTO> info() {
        return R.ok(authService.getUserInfo());
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public R<Void> logout() {
        authService.logout();
        return R.ok();
    }
}
