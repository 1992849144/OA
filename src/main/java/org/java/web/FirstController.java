package org.java.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class FirstController {

    @RequestMapping("/")
    public String first(HttpSession session){
        System.out.println("-----进入控制器first---------");

        //获得认证的主体
        Subject subject = SecurityUtils.getSubject();

        Map map = (Map) subject.getPrincipal();//由于之前存放的就是String,所以取出来也是String, 即：用户名

        String name= map.get("name").toString();//获得职位名称

        String username=map.get("username").toString();//获得用户名

        Integer id =(Integer) map.get("id");//获得用户id

        //把信息，存放在session中
        session.setAttribute("map",map);
        session.setAttribute("nickname",map.get("nickname"));
        session.setAttribute("userId",id);
        session.setAttribute("username",username);
        session.setAttribute("name",name);
        return "/main";
    }
}
