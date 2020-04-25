package org.java.web.AttendanceManage;

import org.java.service.CheckingService;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/checking")
public class CheckingController {

    @Autowired
    private CheckingService checkingService;

    @RequestMapping("/getAllCheckInfo")
    @ResponseBody
    public ResultVo<Map> getAllCheckInfo(String stateTime, String endTime, String branchName, String departName, Integer page, Integer limit, HttpSession session){

        ResultVo<Map> vo =  checkingService.getAllCheckInfo(stateTime,endTime,branchName,departName,page,limit);

        return vo;
    }

}
