package com.cxcacm.commons.handler.exception;


import com.cxcacm.commons.entity.ResponseResult;
import com.cxcacm.commons.enums.AppHttpCodeEnum;
import com.cxcacm.commons.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
        log.error("出现了异常：" + e);
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult systemExceptionHandler(Exception e) {
        log.error("出现了异常：" + e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
