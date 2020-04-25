package org.java.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 梅佳杰写的用户的
 */
public interface UserManageMapper {

    //获得所有用户信息
    public List<Map> getAllUserInfo(@Param("username") String username,@Param("nickname") String nickname);

    /**
     * 添加用户
     * @param map
     */
    public void addUser(Map map);

    /**
     * 根据用户id获得用户详情
     * @param id
     * @return
     */
    public Map showUpdatUser(Integer id);

    /**
     * 修改用户
     * @param map
     */
    public void updateUser(Map map);

    /**
     * 查询除管理员外的所有用户信息
     * @return
     */
    public List<Map> getSysUser(@Param("username") String username,@Param("nickname") String nickname);

    /**
     * 添加权限
     * @param map
     */
    @Insert("insert into sys_role_permission values(null,#{ids},#{permissionI})")
    public void  addSysRolePermission(Map map);
}
