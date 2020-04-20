package org.java.web.ScheduleList;

import org.java.entity.Precontract;
import org.java.service.PrecontractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 *预约人
 */
@Controller
public class PrecontractController {

    @Autowired
    private PrecontractService precontractService;

    /**
     * 根据日程id，获得所有的预约人
     * @param scheduleid
     * @return
     */
    @PostMapping("getPreContractByScheduleId")
    @ResponseBody
    public List<Map> getPreContractByScheduleId(Integer scheduleid){
        return precontractService.getPreContractByScheduleId(scheduleid);
    }
}
