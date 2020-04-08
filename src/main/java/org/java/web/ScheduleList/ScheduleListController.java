package org.java.web.ScheduleList;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.java.entity.ScheduleList;
import org.java.service.ScheduleService;
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
import java.util.Map;

@Controller
@RequestMapping("scheduleList")
public class ScheduleListController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 显示日程的
     * @return
     */
    @PostMapping("init")
    @ResponseBody
    public List<Map> init(){
        return scheduleService.getScheduleListByUserName();
    }

    /**
     *根据用户和等于0的就是部门日程
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("getScheduleListByIsremindEqualZero")
    @ResponseBody
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualZero(Integer page, Integer limit){
        ResultVo<ScheduleList> resultVo = scheduleService.getScheduleListByIsremindEqualZero(page, limit);
        return resultVo;
    }

    /**
     * 根据用户和等于1的就是个人日程
     * @param
     * @return
     */
    @GetMapping("getScheduleListByIsremindEqualOne")
    @ResponseBody
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualOne(Integer page, Integer limit){
        ResultVo<ScheduleList> resultVo = scheduleService.getScheduleListByIsremindEqualOne(page, limit);
        return resultVo;
    }

    /**
     * 删除个人日程
     * @param scheduleId
     * @return
     */
    @Transactional
    @PostMapping("delScheduleList")
    @RequiresPermissions("schedule:del")
    @ResponseBody
    public int delScheduleList(Integer scheduleId){
        scheduleService.delScheduleList(scheduleId);
        return 1;
    }

    /**
     * 删除部门日程
     * @param scheduleId
     * @return
     */
    @Transactional
    @PostMapping("delDepartmentScheduleList")
    @ResponseBody
    public int delDepartmentScheduleList(Integer scheduleId){
        System.out.println(scheduleId);
        scheduleService.delDepartmentScheduleList(scheduleId);
        return 1;
    }
}
