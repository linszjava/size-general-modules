package com.size.auth.service;

import com.size.auth.dto.LoginRequest;
import com.size.auth.dto.LoginVO;
import com.size.rbac.api.dto.AuthInfoDTO;

/**
 * 认证服务
 */
public interface AuthService {

    LoginVO login(LoginRequest request);

    AuthInfoDTO getUserInfo();

    void logout();
}
