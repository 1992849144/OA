package org.java.dao;

import org.apache.ibatis.annotations.Select;
import org.java.entity.SysUser;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 用户
 */
public interface SysUserMapper extends MyMapper<SysUser> {
    /**
     * 根据用户名获得用户详情
     * @param username
     * @return
     */
    public List<Map> login(String username);

    /**
     * 根据用户id，获得用户权限
     * @param id
     * @return
     */
    public List<String> loadPermission(Integer id);

    /**
     * 根据昵称获得用户详情
     * @param nickname
     * @return
     */
    @Select("select * from sys_user where nickname=#{nickname}")
    public List<SysUser> getSysUserByUserName(String nickname);

    /**
     * 根据部门id，获得多个用户
     * @param departId
     * @return
     */
    @Select("SELECT * FROM sys_user WHERE departId=#{departId}")
    public List<SysUser> getSysUserByDepartId(Integer departId);

}