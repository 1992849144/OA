package org.java.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.java.entity.Precontract;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

/**
 *预约人
 */
public interface PrecontractMapper extends MyMapper<Precontract> {
    /**
     * 根据日程id，获得所有的预约人
     * @param scheduleId
     * @return
     */
    @Select("SELECT * FROM preContract p,sys_user u WHERE p.userId=u.id AND scheduleId=#{scheduleId}")
    public List<Map> getPreContractByScheduleId(Integer scheduleId);

    /**
     * 根据日程id，删除所有的预约人
     * @param scheduleId
     */
    @Delete("delete from preContract where scheduleId=#{scheduleId}")
    public void delPrecontract(Integer scheduleId);
}