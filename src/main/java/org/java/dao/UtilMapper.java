package org.java.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 封装树的
 */
public interface UtilMapper {

    /**
     * 获得所有机构
     * @return
     */
    @Select("SELECT * FROM branchInfo ")
    public List<Map> getAllDepartInfo();

    /**
     * 根据机构id，获得该机构的不重复的部门名称
     * @param branchId
     * @return
     */
    @Select("SELECT * FROM departInfo WHERE branchId=#{branchId} AND departName IS NOT NULL")
    public List<Map> getDepartInfoByBranchId(Integer branchId);

    /**
     * 根据机构id，获得该机构所有部门
     * @param branchId
     * @return
     */
    @Select("SELECT * FROM departInfos WHERE branchId=#{branchId}")
    public List<Map> getDepartInfoById(Integer branchId);

    /**
     * 根据部门负责人id，获得用户的名称
     * @param principalUser
     * @return
     */
    @Select("SELECT * FROM departInfos d,sys_user u WHERE d.principalUser=u.id AND principalUser=#{principalUser}")
    public List<Map> getDepartInfoByPrincipalUser(Integer principalUser);

    /**
     * 根据部门id，获得用户的名称
     * @param departId
     * @return
     */
    @Select("SELECT * FROM departInfos d,sys_user u WHERE d.principalUser=u.id AND d.departId=#{departId}")
    public List<Map> getDepartInfoByDepartId(Integer departId);
}
