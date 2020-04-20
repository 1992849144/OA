package org.java.web;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    /**该控制器有两种情况下会进入：
     *  1、没有登录
     *  2、登录失败
     *
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request) throws Exception {
        //获得错误信息，判断 是没有登录，还是登录失败
        String err = (String) request.getAttribute("shiroLoginFailure");
        if(!StringUtils.isEmpty(err)){
            //用户是因为登录失败进入的---抛出异常，由异常处理器进行捕获
            //用户名不存在 UnknownAccountException
            if(err.endsWith("UnknownAccountException")){
                throw new Exception("用户名不存在");
            }
            //密码错误 IncorrectCredentialsException
            if(err.endsWith("IncorrectCredentialsException")){
                throw new Exception("密码错误");
            }
        }
        //如果执行此处，表示，用户是没有登录进入
        return "/login";
    }
}
