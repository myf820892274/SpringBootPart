package com.yf.exception;

import com.yf.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@ControllerAdvice//跳页面
@RestControllerAdvice//响应json
public class MyExceptionHandler {
    @ExceptionHandler(AuthorizationException.class)
    public R handlerException(AuthorizationException e){
        return R.error("您没有权限操作，请联系管理员");
    }

    @ExceptionHandler(RZException.class)
    public R handlerException(RZException e){
        return R.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handlerException(Exception e){
        return R.error(e.getMessage());
    }

}
