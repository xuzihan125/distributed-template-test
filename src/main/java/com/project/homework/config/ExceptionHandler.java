package com.project.homework.config;

import com.project.homework.common.BaseResponse;
import com.project.homework.common.ErrorCode;
import com.project.homework.common.ResultUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public BaseResponse<String> handleGlobalException(ExceptionHandler ex, WebRequest request) {
        // 处理全局异常
        return ResultUtils.error(ErrorCode.OPERATION_ERROR, ex.toString());
    }
}
