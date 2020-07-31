package com.hxh.controller;

import com.hxh.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

@ExceptionHandler
    @ResponseBody
    public Result doException(Exception e){
    return new Result(false,e.getMessage(),null);
}
//    //统一异常响应处理handler 方法   通过ajax调用产生的异常需要用content-type:application/json响应格式
//    @ExceptionHandler
//    @ResponseBody
//    public Result doException(Exception e){
//       return new Result(false,e.getMessage(),null);
//    }
}
