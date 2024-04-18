package com.project.homework.common;

/**
 * enum for possible error
 *
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "request param error"),
    NOT_LOGIN_ERROR(40100, "not login"),
    NO_AUTH_ERROR(40101, "not authorized"),
    NOT_FOUND_ERROR(40400, "request data doesn't exist"),
    FORBIDDEN_ERROR(40300, "forbidden to access"),
    API_REQUETS_ERROR(50010,"api fail to respond"),
    SYSTEM_ERROR(50000, "internal system error"),
    OPERATION_ERROR(50001, "operation fail");

    /**
     * status code
     */
    private final int code;

    /**
     * information
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
