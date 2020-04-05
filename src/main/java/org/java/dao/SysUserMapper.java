package org.java.dao;

import org.apache.ibatis.annotations.Select;
import org.java.entity.SysUser;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

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
}