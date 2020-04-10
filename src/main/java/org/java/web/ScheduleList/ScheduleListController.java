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

/**
 * 日程
 */
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
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualZero(String title,String startTime,String endTime,Integer page, Integer limit){
        ResultVo<ScheduleList> resultVo = scheduleService.getScheduleListByIsremindEqualZero(title,startTime,endTime,page, limit);
        return resultVo;
    }

    /**
     * 根据用户和等于1的就是个人日程
     * @param
     * @return
     */
    @GetMapping("getScheduleListByIsremindEqualOne")
    @ResponseBody
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualOne(String title,String startTime,String endTime,Integer page, Integer limit){
        ResultVo<ScheduleList> resultVo = scheduleService.getScheduleListByIsremindEqualOne(title,startTime,endTime,page, limit);
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
        scheduleService.delDepartmentScheduleList(scheduleId);
        return 1;
    }

    /**
     * 修改个人日程
     * @param scheduleList
     */
    @Transactional
    @PostMapping("updateScheduleList")
    @ResponseBody
    public void updateScheduleList(ScheduleList scheduleList){
        System.out.println(scheduleList);
        scheduleService.updateScheduleList(scheduleList);
    }
}
