package org.java.web.ScheduleList;

import org.java.entity.Personalnotes;
import org.java.service.PersonalnotesService;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 个人便签
 */
@Controller
@RequestMapping("personalnotes")
public class PersonalnotesController {

    @Autowired
    private PersonalnotesService personalnotesService;

    /**
     * 显示所有个人便签
     * @param session
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("init")
    @ResponseBody
    public ResultVo<Personalnotes> init(HttpSession session,Integer page,Integer limit,String personalnotestitle){
        String username = session.getAttribute("username").toString();
        ResultVo<Personalnotes> personalnotes = personalnotesService.getPersonalnotesByUserId(username,page,limit,personalnotestitle);
        return personalnotes;
    }

    /**
     * 添加个人便签
     * @param personalnotes
     */
    @PostMapping("addPersonalnotes")
    @Transactional
    @ResponseBody
    public void addPersonalnotes(Personalnotes personalnotes){
        System.out.println(personalnotes);
        personalnotesService.addPersonalnotes(personalnotes);
    }

    /**
     * 删除个人便签
     * @param personalnotesid
     */
    @PostMapping("delPersonalnotes")
    @Transactional
    @ResponseBody
    public void delPersonalnotes(Integer personalnotesid){
        personalnotesService.delPersonalnotes(personalnotesid);
    }

    /**
     * 修改个人便签
     * @param personalnotes
     */
    @PostMapping("updatePersonalnotes")
    @Transactional
    @ResponseBody
    public void updatePersonalnotes(Personalnotes personalnotes){
        personalnotesService.updatePersonalnotes(personalnotes);
    }
}
