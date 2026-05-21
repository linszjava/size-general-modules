package com.size.rbac.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.size.common.core.result.R;
import com.size.rbac.api.dto.AuthInfoDTO;
import com.size.rbac.api.dto.UserDTO;
import com.size.rbac.mapper.SysUserMapper;
import com.size.rbac.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理接口
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/rbac/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SysUserMapper sysUserMapper;

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public R<String> health() {
        return R.ok("rbac-service is running");
    }

    @SaIgnore
    @Operation(summary = "数据库连通性检查")
    @GetMapping("/db-check")
    public R<String> dbCheck() {
        Long count = sysUserMapper.selectCount(null);
        return R.ok("数据库正常，用户总数: " + count);
    }

    @SaIgnore
    @Operation(summary = "按用户名查询用户")
    @GetMapping("/username/{username}")
    public R<UserDTO> getByUsername(@PathVariable("username") String username) {
        return R.ok(userService.getByUsername(username));
    }

    @SaIgnore
    @Operation(summary = "获取用户认证信息")
    @GetMapping("/{userId}/auth-info")
    public R<AuthInfoDTO> getAuthInfo(@PathVariable("userId") Long userId) {
        return R.ok(userService.getAuthInfo(userId));
    }
}
