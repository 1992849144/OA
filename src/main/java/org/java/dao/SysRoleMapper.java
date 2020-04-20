package org.java.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.java.entity.SysRole;
import tk.mybatis.MyMapper;

import java.util.Map;

public interface SysRoleMapper extends MyMapper<SysRole> {

    /**
     * 添加到用户与角色的关系表
     * @param map
     */
    public void addSysUserRole(Map map);

    /**
     * 根据用户id和角色id，获得用户与角色详情
     * @param userId
     * @return
     */
    @Select("SELECT * FROM  sys_user_role WHERE user_id=#{userId}")
    public Map getSysUserRoleByUseIdRoleId(Integer userId);

    /**
     * 修改用户与角色表
     * @param map
     */
    @Update("update sys_user_role set role_id =#{name} where id=#{id}")
    public void updateSysUserRole(Map map);
}