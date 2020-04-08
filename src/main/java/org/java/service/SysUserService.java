package org.java.service;

import org.java.dao.SysUserMapper;
import org.java.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户名获得用户详情
     * @param username
     * @return
     */
    public Map login(String username){
        List<Map> map = sysUserMapper.login(username);
        if (!map.isEmpty()){
            return  map.get(0);
        }
        return null;
    }

    /**
     * 根据用户id，获得用户权限
     * @param id
     * @return
     */
    public List<String> loadPermission(Integer id){
        List<String> list = sysUserMapper.loadPermission(id);
        return list;
    }

    /**
     *根据id，好的用户详情
     * @param id
     * @return
     */
    public SysUser getSysUserById(Integer id){
        SysUser user = sysUserMapper.selectByPrimaryKey(id);
        return user;
    }

}
