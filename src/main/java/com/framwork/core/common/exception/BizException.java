package com.framwork.core.common.exception;

public class BizException extends RuntimeException {

    private final String code;

    public BizException(String code) {
        this.code = code;
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code, Throwable cause) {
        super(cause != null ? cause.getMessage() : null, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
