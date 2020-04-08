package org.java.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.dao.ScheduleListMapper;
import org.java.entity.ScheduleList;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleListMapper scheduleListMapper;

    /**
     * 根据用户，获得日程详情,返回map
     * @param
     * @return
     */
    public List<Map> getScheduleListByUserName(){
        return scheduleListMapper.getScheduleListByUserName();
    }

    /**
     * 根据用户和等于0的就是部门日程
     * @param
     * @return
     */
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualZero(Integer page, Integer limit){

        PageHelper.startPage(page,limit);
        PageInfo<ScheduleList> pageInfo = new PageInfo<>(scheduleListMapper.getScheduleListByIsremindEqualZero());

        //将查询结果，封装成ResultVo
        ResultVo<ScheduleList> resultVo = new ResultVo<>();

        resultVo.setData(pageInfo.getList());//集合
        resultVo.setCount(pageInfo.getTotal());//总数

        return resultVo;
    }


    /**
     * 根据用户和等于1的就是个人日程
     * @param
     * @return
     */
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualOne(Integer page, Integer limit){

        PageHelper.startPage(page,limit);
        PageInfo<ScheduleList> pageInfo = new PageInfo<>(scheduleListMapper.getScheduleListByIsremindEqualOne());

        //将查询结果，封装成ResultVo
        ResultVo<ScheduleList> resultVo = new ResultVo<>();

        resultVo.setData(pageInfo.getList());//集合
        resultVo.setCount(pageInfo.getTotal());//总数

        return resultVo;
    }

    /**
     * 删除个人日程
     * @param scheduleId
     */
    public void delScheduleList(Integer scheduleId) {
        scheduleListMapper.deleteByPrimaryKey(scheduleId);
    }

    /**
     * 删除部门日程
     * @param scheduleId
     */
    public void delDepartmentScheduleList(Integer scheduleId) {
        scheduleListMapper.deleteByPrimaryKey(scheduleId);
    }
}
