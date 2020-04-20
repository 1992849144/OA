package org.java.web.mail;

import org.java.entity.Messagetype;
import org.java.service.MessagetypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("messagetype")
public class MessagetypeController {

    @Autowired
    private MessagetypeService messagetypeService;

    /**
     * 获得所有消息类型
     * @return
     */
    @PostMapping("init")
    @ResponseBody
    public List<Messagetype> getAllMessagetype(){
        return messagetypeService.getAllMessagetype();
    }
}
