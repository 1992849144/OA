package org.java.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class WebConfig {
    @GetMapping("/forward/{ScheduleList}/{target}")
    public String forward(@PathVariable("ScheduleList") String ScheduleList,@PathVariable("target") String target){
        return "/"+ScheduleList+"/"+target;
    }

    @GetMapping("/forward/{target}")
    public String forwards(@PathVariable("target") String target){
        return "/"+target;
    }
}
