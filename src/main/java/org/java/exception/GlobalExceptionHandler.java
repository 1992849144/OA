package org.java.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleException(HttpServletRequest req, Exception ex){
        System.out.println("----------------");
        //获得异常原因
        String msg = ex.getMessage();

        System.out.println(msg);
        //判断，当前产生异常的原因是不是因为，访问权限不足，导致的异常
        if(ex instanceof UnauthorizedException){
            msg="访问权限不足";
        }

        //存储异常原因
        req.setAttribute("err",msg);

        //跳转到错误页面
        return "/err";
    }
}
