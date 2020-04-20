package org.java.dao;

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
}
