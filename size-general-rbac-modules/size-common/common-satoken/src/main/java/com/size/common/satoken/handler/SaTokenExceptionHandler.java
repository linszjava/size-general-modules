package com.size.common.satoken.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.size.common.core.result.HttpStatusMapper;
import com.size.common.core.result.R;
import com.size.common.core.result.ResultCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Sa-Token 异常处理
 */
@RestControllerAdvice
public class SaTokenExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public ResponseEntity<R<Void>> handleNotLogin(NotLoginException e) {
        return errorResponse(ResultCode.UNAUTHORIZED);
    }

    @ExceptionHandler(NotPermissionException.class)
    public ResponseEntity<R<Void>> handleNotPermission(NotPermissionException e) {
        return errorResponse(ResultCode.FORBIDDEN);
    }

    @ExceptionHandler(NotRoleException.class)
    public ResponseEntity<R<Void>> handleNotRole(NotRoleException e) {
        return errorResponse(ResultCode.FORBIDDEN);
    }

    private static ResponseEntity<R<Void>> errorResponse(ResultCode resultCode) {
        R<Void> body = R.fail(resultCode.getMessage());
        body.setCode(resultCode.getCode());
        return ResponseEntity.status(HttpStatusMapper.fromBizCode(resultCode.getCode())).body(body);
    }
}
