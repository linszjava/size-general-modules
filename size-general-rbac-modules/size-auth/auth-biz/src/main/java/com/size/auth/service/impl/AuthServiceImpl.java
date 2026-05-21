package com.size.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.size.auth.dto.LoginRequest;
import com.size.auth.dto.LoginVO;
import com.size.auth.service.AuthService;
import com.size.common.core.exception.BizException;
import com.size.common.core.result.R;
import com.size.common.core.result.ResultCode;
import com.size.rbac.api.RbacUserApi;
import com.size.rbac.api.dto.AuthInfoDTO;
import com.size.rbac.api.dto.UserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/**
 * 认证服务实现
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String SESSION_AUTH_INFO = "authInfo";
    private static final String LOGIN_FAIL_MSG = "用户名或密码错误";

    private final RbacUserApi rbacUserApi;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginVO login(LoginRequest request) {
        UserDTO user = requireFeignData(
                () -> rbacUserApi.getByUsername(request.getUsername()),
                ResultCode.UNAUTHORIZED.getCode(),
                LOGIN_FAIL_MSG);
        verifyLoginCredential(user, request.getPassword());
        AuthInfoDTO authInfo = requireFeignData(
                () -> rbacUserApi.getAuthInfo(user.getId()),
                ResultCode.FAIL.getCode(),
                "获取用户权限信息失败");

        StpUtil.login(user.getId());
        StpUtil.getSession().set(SESSION_AUTH_INFO, authInfo);
        return new LoginVO(StpUtil.getTokenValue());
    }

    @Override
    public AuthInfoDTO getUserInfo() {
        StpUtil.checkLogin();
        AuthInfoDTO cached = (AuthInfoDTO) StpUtil.getSession().get(SESSION_AUTH_INFO);
        if (cached != null) {
            return cached;
        }
        AuthInfoDTO authInfo = requireFeignData(
                () -> rbacUserApi.getAuthInfo(StpUtil.getLoginIdAsLong()),
                ResultCode.UNAUTHORIZED.getCode(),
                ResultCode.UNAUTHORIZED.getMessage());
        StpUtil.getSession().set(SESSION_AUTH_INFO, authInfo);
        return authInfo;
    }

    @Override
    public void logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
    }

    private void verifyLoginCredential(UserDTO user, String rawPassword) {
        if (user.getStatus() != null && user.getStatus() != 0) {
            throw new BizException(ResultCode.UNAUTHORIZED.getCode(), "账号已停用");
        }
        if (user.getPassword() == null || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BizException(ResultCode.UNAUTHORIZED.getCode(), LOGIN_FAIL_MSG);
        }
    }

    private <T> T requireFeignData(Supplier<R<T>> supplier, int errorCode, String errorMessage) {
        try {
            return R.requireData(supplier.get(), errorCode, errorMessage);
        } catch (FeignException e) {
            throw new BizException(e.status(), errorMessage);
        }
    }
}
