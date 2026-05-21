package com.size.rbac.api;

import com.size.common.core.result.R;
import com.size.rbac.api.dto.AuthInfoDTO;
import com.size.rbac.api.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RBAC 用户 Feign 接口
 */
@FeignClient(name = "size-rbac", contextId = "rbacUserApi")
public interface RbacUserApi {

    @GetMapping("/rbac/user/username/{username}")
    R<UserDTO> getByUsername(@PathVariable("username") String username);

    @GetMapping("/rbac/user/{userId}/auth-info")
    R<AuthInfoDTO> getAuthInfo(@PathVariable("userId") Long userId);
}
