package com.size.common.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "success"),
    FAIL(500, "fail"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限");

    private final int code;
    private final String message;
}
