package org.java.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {
    //根据id查询对应的部门，机构
    public Map findInfoById(Integer id);

    //添加一条记录(签到)
    public void addSignIn(@Param("onDutyTime") String onDutyTime, @Param("signDesc") String signDesc, @Param("id") Integer id);

    List<Map> getSing(Integer id);

    List<Map> getOffTimeById(Integer id);

    //添加一条记录(签退)
    void addSignOff(@Param("onDutyTime") String onDutyTime, @Param("signDesc") String signDesc, @Param("signId") Integer signId);


}
