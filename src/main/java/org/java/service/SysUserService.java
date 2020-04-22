package org.java.service;

import org.java.dao.SysUserMapper;
import org.java.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 我写的用户的
 */
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
     *根据用户id，好的用户详情
     * @param id
     * @return
     */
    public SysUser getSysUserById(Integer id){
        SysUser user = sysUserMapper.selectByPrimaryKey(id);
        return user;
    }

    /**
     * 根据用户昵称获得用户详情
     * @param nickname
     * @return
     */
    public SysUser getSysUserByUserName(String nickname){
        List<SysUser> user = sysUserMapper.getSysUserByUserName(nickname);
        if (!user.isEmpty()){
            return  user.get(0);
        }
        return null;
    }

    /**
     * 根据部门id，获得多个用户
     * @param departId
     * @return
     */
    public List<SysUser> getSysUserByDepartId(Integer departId){
        return sysUserMapper.getSysUserByDepartId(departId);
    }

    /**
     * 获得所有用户
     * @return
     */
    public List<SysUser> getAllSysUser(){
        return sysUserMapper.selectAll();
    }

    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(Integer id) {
        sysUserMapper.deleteByPrimaryKey(id);
    }
}
