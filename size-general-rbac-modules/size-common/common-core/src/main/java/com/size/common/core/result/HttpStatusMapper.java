package com.size.common.core.result;

import org.springframework.http.HttpStatus;

/**
 * 业务 code 与 HTTP 状态码映射
 */
public final class HttpStatusMapper {

    private HttpStatusMapper() {}

    public static HttpStatus fromBizCode(int code) {
        return switch (code) {
            case 200 -> HttpStatus.OK;
            case 400 -> HttpStatus.BAD_REQUEST;
            case 401 -> HttpStatus.UNAUTHORIZED;
            case 403 -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
