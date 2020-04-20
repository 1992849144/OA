package org.java.service;

import org.java.dao.PrecontractMapper;
import org.java.entity.Precontract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 预约人
 */
@Service
public class PrecontractService {

    @Autowired
    private PrecontractMapper precontractMapper;

    /**
     * 根据日程id，获得所有的预约人
     * @param scheduleId
     * @return
     */
    public List<Map> getPreContractByScheduleId(Integer scheduleId){
        return  precontractMapper.getPreContractByScheduleId(scheduleId);
    }
}
