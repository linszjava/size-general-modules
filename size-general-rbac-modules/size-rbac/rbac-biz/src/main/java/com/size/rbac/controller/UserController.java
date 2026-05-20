package com.size.rbac.controller;

import com.size.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理接口
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/rbac/user")
public class UserController {

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public R<String> health() {
        return R.ok("rbac-service is running");
    }
}
