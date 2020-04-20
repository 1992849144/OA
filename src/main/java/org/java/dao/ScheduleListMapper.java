package org.java.dao;

import org.apache.ibatis.annotations.Param;
import org.java.entity.ScheduleList;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 日程详情
 */
public interface ScheduleListMapper extends MyMapper<ScheduleList> {

    /**
     * 根据用户，获得日程详情,返回map
     * @param
     * @return
     */
    public List<Map> getScheduleListByUserName();

    /**
     * 根据用户和等于0的就是部门日程
     * @param
     * @return
     */
    public List<ScheduleList> getScheduleListByIsremindEqualZero (@Param("title") String title, @Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 根据用户和等于1的就是个人日程
     * @param
     * @return
     */
    public List<ScheduleList> getScheduleListByIsremindEqualOne(@Param("title") String title, @Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 修改个人日程
     * @param scheduleList
     */
    public void updateScheduleList(ScheduleList scheduleList);

    /**
     * 添加日程
     * @param scheduleList
     */
    public void addScheduleList(ScheduleList scheduleList);

    /**
     * 修改部门日程
     * @param map
     */
    public void updateDepartmentScheduleList(Map map);
}