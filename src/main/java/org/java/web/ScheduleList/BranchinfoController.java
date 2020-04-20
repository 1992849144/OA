package org.java.web.ScheduleList;

import org.java.entity.Branchinfo;
import org.java.service.BranchinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 机构
 */
@Controller
@RequestMapping("branchinfo")
public class BranchinfoController {

    @Autowired
    private BranchinfoService branchinfoService;

    @PostMapping("init")
    @ResponseBody
    public List<Branchinfo> getAllBranchinfo(){
        return branchinfoService.getAllBranchinfo();
    }
}
