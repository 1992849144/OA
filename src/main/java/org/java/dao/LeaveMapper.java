package org.java.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface LeaveMapper{

    public void createLeave(Map map);

    public Map findByProcessInstanceId(@Param("instanceId") String processInstanceId);

    @Select("select * from pur_order where id=#{orderId}")
    public  Map findOrderById(String orderId);

    public void submitAudit(Map map);
}
