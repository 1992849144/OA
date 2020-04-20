package org.java.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跳转页面
 */
@Controller
public class WebConfig {
    @GetMapping("/forward/{scheduleList}/{target}")
    public String forward(@PathVariable("scheduleList") String scheduleList,@PathVariable("target") String target){
        return "/"+scheduleList+"/"+target;
    }

    @GetMapping("/forward/{target}")
    public String forwards(@PathVariable("target") String target){
        return "/"+target;
    }
}
