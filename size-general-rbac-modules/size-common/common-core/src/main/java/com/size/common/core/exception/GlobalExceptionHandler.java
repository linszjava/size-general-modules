package com.size.common.core.exception;

import com.size.common.core.result.HttpStatusMapper;
import com.size.common.core.result.R;
import com.size.common.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理（方式 A：HTTP 状态码表达错误，body 仍用 R 携带 message）
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<R<Void>> handleBizException(BizException e) {
        log.warn("业务异常: {}", e.getMessage());
        return errorResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<R<Void>> handleException(Exception e) {
        log.error("系统异常", e);
        return errorResponse(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage());
    }

    private static ResponseEntity<R<Void>> errorResponse(int code, String message) {
        HttpStatus httpStatus = HttpStatusMapper.fromBizCode(code);
        R<Void> body = R.fail(message);
        body.setCode(code);
        return ResponseEntity.status(httpStatus).body(body);
    }
}
