package com.size.common.core.result;

import com.size.common.core.exception.BizException;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果
 */
@Data
public class R<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    private R() {}

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        r.setData(data);
        return r;
    }

    public static <T> R<T> fail(String message) {
        R<T> r = new R<>();
        r.setCode(ResultCode.FAIL.getCode());
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        R<T> r = new R<>();
        r.setCode(resultCode.getCode());
        r.setMessage(resultCode.getMessage());
        return r;
    }

    /**
     * 校验 Feign/远程调用结果并返回 data（由业务方显式调用，非自动解包）
     */
    public static <T> T requireData(R<T> result, int errorCode, String errorMessage) {
        if (result == null || result.getCode() != ResultCode.SUCCESS.getCode() || result.getData() == null) {
            throw new BizException(errorCode, errorMessage);
        }
        return result.getData();
    }
}
