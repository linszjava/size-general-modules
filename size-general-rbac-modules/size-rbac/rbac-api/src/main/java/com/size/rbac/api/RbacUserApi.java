package com.size.rbac.api;

import com.size.common.core.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RBAC Feign 接口
 */
@FeignClient(name = "size-rbac", contextId = "rbacUserApi")
public interface RbacUserApi {

    @GetMapping("/rbac/user/username/{username}")
    R<Object> getByUsername(@PathVariable("username") String username);
}
