package org.java.service;

import org.java.dao.UtilMapper;
import org.java.entity.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装树的
 */
@Service
public class UtilService {

    @Autowired
    private UtilMapper utilMapper;

    /**
     * 获得所有机构
     * @return
     */
    public List<Util> getAllDepartInfo(){
        List<Map> list = utilMapper.getAllDepartInfo();
        List<Util> departInfo=new ArrayList<>();
        //实体类封装
        for (Map m:list){
            Util util=new Util();
            util.setId(Integer.parseInt(m.get("branchId").toString()));
            util.setTitle(m.get("branchName").toString());
            departInfo.add(util);
        }
        return departInfo;
    }

    /**
     * 根据机构id，获得该机构的不重复的部门名称
     * @param branchId
     * @return
     */
    public List<Map> getDepartInfoByBranchId(Integer branchId) {
        return utilMapper.getDepartInfoByBranchId(branchId);
    }

    /**
     * 根据机构id，获得该机构所有部门
     * @param branchId
     * @return
     */
    public List<Map> getDepartInfoById(Integer branchId){
        return utilMapper.getDepartInfoById(branchId);
    }

    /**
     * 根据部门负责人id，获得用户的名称
     * @param principalUser
     * @return
     */
    public List<Map> getDepartInfoByPrincipalUser(Integer principalUser){
        return utilMapper.getDepartInfoByPrincipalUser(principalUser);
    }

    /**
     * 根据部门id，获得用户的名称
     * @param departId
     * @return
     */
    public List<Map> getDepartInfoByDepartId(Integer departId){
        return utilMapper.getDepartInfoByDepartId(departId);
    }
}
