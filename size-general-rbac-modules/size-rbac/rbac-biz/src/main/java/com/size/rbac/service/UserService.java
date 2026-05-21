package com.size.rbac.service;

import com.size.rbac.api.dto.AuthInfoDTO;
import com.size.rbac.api.dto.UserDTO;

/**
 * 用户服务
 */
public interface UserService {

    UserDTO getByUsername(String username);

    AuthInfoDTO getAuthInfo(Long userId);
}
