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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String SESSION_AUTH_INFO = "authInfo";

    private final RbacUserApi rbacUserApi;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginVO login(LoginRequest request) {
        R<UserDTO> userResult = rbacUserApi.getByUsername(request.getUsername());
        if (userResult == null || userResult.getData() == null) {
            throw new BizException(ResultCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
        }
        if (userResult.getCode() == ResultCode.FAIL.getCode()) {
            throw new BizException(ResultCode.FAIL.getCode(), "权限服务异常，请检查数据库是否已初始化");
        }
        if (userResult.getCode() != ResultCode.SUCCESS.getCode()) {
            throw new BizException(ResultCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
        }
        UserDTO user = userResult.getData();

        if (user.getStatus() != null && user.getStatus() != 0) {
            throw new BizException(ResultCode.UNAUTHORIZED.getCode(), "账号已停用");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BizException(ResultCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
        }

        R<AuthInfoDTO> authInfoResult = rbacUserApi.getAuthInfo(user.getId());
        if (authInfoResult == null
                || authInfoResult.getCode() != ResultCode.SUCCESS.getCode()
                || authInfoResult.getData() == null) {
            throw new BizException("获取用户权限信息失败");
        }

        StpUtil.login(user.getId());
        StpUtil.getSession().set(SESSION_AUTH_INFO, authInfoResult.getData());

        return new LoginVO(StpUtil.getTokenValue());
    }

    @Override
    public AuthInfoDTO getUserInfo() {
        StpUtil.checkLogin();
        AuthInfoDTO cached = (AuthInfoDTO) StpUtil.getSession().get(SESSION_AUTH_INFO);
        if (cached != null) {
            return cached;
        }
        Long userId = StpUtil.getLoginIdAsLong();
        R<AuthInfoDTO> authInfoResult = rbacUserApi.getAuthInfo(userId);
        if (authInfoResult == null
                || authInfoResult.getCode() != ResultCode.SUCCESS.getCode()
                || authInfoResult.getData() == null) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        AuthInfoDTO authInfo = authInfoResult.getData();
        StpUtil.getSession().set(SESSION_AUTH_INFO, authInfo);
        return authInfo;
    }

    @Override
    public void logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
    }
}
