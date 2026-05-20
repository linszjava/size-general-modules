package com.size.system.controller;

import com.size.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典管理接口
 */
@Tag(name = "字典管理")
@RestController
@RequestMapping("/system/dict")
public class DictController {

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public R<String> health() {
        return R.ok("system-service is running");
    }
}
