package org.java.web.AttendanceManage;

import org.java.service.AttendanceHistoryService;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/checkWorkAttendance")
public class AttendanceHistoryController {

    @Autowired
    private AttendanceHistoryService attendanceHistoryService;
    //获得所有考勤信息
    @RequestMapping("/getAllInfo")
    @ResponseBody
    public ResultVo<Map> getAllInfo(String stateTime, String endTime, String branchName, String departName, Integer page, Integer limit,HttpSession session){
        ResultVo<Map> vo = attendanceHistoryService.
                getAllInfo(stateTime, endTime, branchName, departName, page, limit);

        return vo;
    }



    //获得所有部门
    @RequestMapping("/getAllDep")
    @ResponseBody
    public  List<Map> getAllDep(){
        return attendanceHistoryService.getAllDep();
    }

    //获得所有机构
    @RequestMapping("/getAllBranch")
    @ResponseBody
    public  List<Map> getAllBranch(){
        return attendanceHistoryService.getAllBranch();
    }



}
