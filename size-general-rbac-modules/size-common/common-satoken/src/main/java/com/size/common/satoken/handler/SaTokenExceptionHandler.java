package com.size.common.satoken.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.size.common.core.result.R;
import com.size.common.core.result.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Sa-Token 异常处理
 */
@RestControllerAdvice
public class SaTokenExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public R<Void> handleNotLogin(NotLoginException e) {
        R<Void> r = R.fail(ResultCode.UNAUTHORIZED.getMessage());
        r.setCode(ResultCode.UNAUTHORIZED.getCode());
        return r;
    }

    @ExceptionHandler(NotPermissionException.class)
    public R<Void> handleNotPermission(NotPermissionException e) {
        R<Void> r = R.fail(ResultCode.FORBIDDEN.getMessage());
        r.setCode(ResultCode.FORBIDDEN.getCode());
        return r;
    }

    @ExceptionHandler(NotRoleException.class)
    public R<Void> handleNotRole(NotRoleException e) {
        R<Void> r = R.fail(ResultCode.FORBIDDEN.getMessage());
        r.setCode(ResultCode.FORBIDDEN.getCode());
        return r;
    }
}
