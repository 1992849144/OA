package org.java.web.AttendanceManage;

import org.java.service.EmployeeService;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //添加一条签到记录
    @RequestMapping("/addSignIn")
    @ResponseBody
    @Transactional
    public void addSignIn(String onDutyTime, String signDesc, HttpSession session){
        Map map = (Map) session.getAttribute("map");
        Integer id = (Integer) map.get("id");//用户id
        employeeService.addSignIn(onDutyTime,signDesc,id);
    }


    //查询签到信息
    @RequestMapping("/GetSingById")
    @ResponseBody
    public Map GetSingById(HttpSession session){
        Map map = (Map) session.getAttribute("map");
        String username = (String) map.get("username");//用户号
        Integer id = (Integer) map.get("id");//用户id
        String name = (String) map.get("name");//姓名
        Map sing = employeeService.GetSingById(id,name,username);
        return sing;
    }

    //根据ID查看签退是否为空
    @RequestMapping("/getOffTimeById")
    @ResponseBody
    public void getOffTimeById(HttpSession session, Model model){
        Map map = (Map) session.getAttribute("map");
        Integer id = (Integer) map.get("id");//用户id
        Map offTimeById = employeeService.getOffTimeById(id);
        Object offDutyTime = offTimeById.get("offDutyTime");//签退时间
        Object onDutyTime = offTimeById.get("onDutyTime");//签到时间
        model.addAttribute("offDutyTime",offDutyTime);
        model.addAttribute("onDutyTime",onDutyTime);
    }

    //添加一条签退记录
    @RequestMapping("/addSignOff")
    @ResponseBody
    @Transactional
    public void addSignOff(String onDutyTime, String signDesc, HttpSession session){
        Map map = (Map) session.getAttribute("map");
        Integer id = (Integer) map.get("id");//用户id
        Map offTimeById = employeeService.getOffTimeById(id);
        Integer signId = (Integer) offTimeById.get("signId");
        employeeService.addSignOff(onDutyTime,signDesc,signId);
    }
}
