package org.java.service;

import org.java.dao.EmployeeMapper;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public void addSignIn(String onDutyTime, String signDesc,Integer id){
        employeeMapper.addSignIn(onDutyTime,signDesc,id);
    }

    public Map GetSingById(Integer id,String name,String username) {
        Map info = employeeMapper.findInfoById(id);
        String departName = (String) info.get("departName");//所属部门
        String branchName = (String) info.get("branchName");//所属机构
        List<Map> maps = employeeMapper.getSing(id);
        Map sing = maps.get(0);
        sing.put("departName",departName);
        sing.put("branchName",branchName);
        sing.put("name",name);
        sing.put("username",username);
        System.out.println(sing);
        return sing;
    }

    public Map getOffTimeById(Integer id) {
        return employeeMapper.getOffTimeById(id).get(0);
    }


    //添加签退记录
    public void addSignOff(String onDutyTime, String signDesc, Integer signId) {
        employeeMapper.addSignOff(onDutyTime,signDesc,signId);
    }



}
