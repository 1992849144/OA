package org.java.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.dao.PersonalnotesMapper;
import org.java.dao.PrecontractMapper;
import org.java.dao.ScheduleListMapper;
import org.java.dao.SysUserMapper;
import org.java.entity.Precontract;
import org.java.entity.ScheduleList;
import org.java.entity.SysUser;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 日程详情
 */
@Service
public class ScheduleService {

    @Autowired
    private ScheduleListMapper scheduleListMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PrecontractMapper precontractMapper;

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
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualZero(String title,String startTime,String endTime,Integer page, Integer limit){

        PageHelper.startPage(page,limit);
        PageInfo<ScheduleList> pageInfo = new PageInfo<>(scheduleListMapper.getScheduleListByIsremindEqualZero(title,startTime,endTime));

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
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualOne(String title,String startTime,String endTime,Integer page, Integer limit){

        PageHelper.startPage(page,limit);
        PageInfo<ScheduleList> pageInfo = new PageInfo<>(scheduleListMapper.getScheduleListByIsremindEqualOne(title,startTime,endTime));

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
        precontractMapper.delPrecontract(scheduleId);//根据日程id，删除所有的预约人
        scheduleListMapper.deleteByPrimaryKey(scheduleId);
    }

    /**
     * 删除部门日程
     * @param scheduleId
     */
    public void delDepartmentScheduleList(Integer scheduleId) {
        precontractMapper.delPrecontract(scheduleId);//根据日程id，删除所有的预约人
        scheduleListMapper.deleteByPrimaryKey(scheduleId);
    }

    /**
     * 修改个人日程
     * @param scheduleList
     */
    public void updateScheduleList(ScheduleList scheduleList) {
        scheduleListMapper.updateScheduleList(scheduleList);
    }

    /**
     * 添加日程
     * @param scheduleList
     */
    public void addScheduleList(ScheduleList scheduleList){
        Map user = sysUserService.login(scheduleList.getUsername());
        scheduleList.setUserId((Integer) user.get("id"));
        if(StringUtils.isEmpty(scheduleList.getIsRemind())){
            scheduleList.setIsRemind(1);
        }
        scheduleList.setCreatetime(new Date());
        Random rand = new Random();
        int id = rand.nextInt(50000) + 100;
        scheduleList.setScheduleid(id);
        String primarypersonName = scheduleList.getPrimarypersonName();
        if(!StringUtils.isEmpty(primarypersonName)){
            if (primarypersonName.indexOf(",")!=-1){
                String[] split = primarypersonName.split(",");
                for (int i=0;i<split.length;i++){
                    SysUser sysUser = sysUserService.getSysUserByUserName(split[i]);
                    Precontract precontract=new Precontract();
                    precontract.setUserid(sysUser.getId());
                    precontract.setScheduleid(id);
                    precontractMapper.insert(precontract);
                }
            }else {
                Precontract precontract=new Precontract();
                SysUser sysUser = sysUserService.getSysUserByUserName(primarypersonName);
                precontract.setUserid(sysUser.getId());
                precontract.setScheduleid(id);
                precontractMapper.insert(precontract);
            }
        }
        scheduleListMapper.addScheduleList(scheduleList);
    }

    /**
     * 修改部门会议
     * @param map
     */
    public void updateDepartmentScheduleList(Map map) {
        String primarypersonName =map.get("primarypersonName").toString();
        Integer scheduleid=Integer.parseInt(map.get("scheduleid").toString());
        if (primarypersonName.indexOf(",")!=-1){
            String[] split = primarypersonName.split(",");
            precontractMapper.delPrecontract(scheduleid);//根据日程id，删除所有的预约人
            for (int i=0;i<split.length;i++){
                SysUser sysUser = sysUserService.getSysUserByUserName(split[i]);
                Precontract precontract=new Precontract();
                precontract.setScheduleid(scheduleid);
                precontract.setUserid(sysUser.getId());
                precontractMapper.insert(precontract);
            }
        }else {
            Precontract precontract=new Precontract();
            precontractMapper.delPrecontract(scheduleid);//根据日程id，删除所有的预约人
            SysUser sysUser = sysUserService.getSysUserByUserName(primarypersonName);
            precontract.setUserid(sysUser.getId());
            precontract.setScheduleid(scheduleid);
            precontractMapper.insert(precontract);
        }
        scheduleListMapper.updateDepartmentScheduleList(map);
    }
}
