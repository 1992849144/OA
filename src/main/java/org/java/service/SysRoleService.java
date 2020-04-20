package org.java.service;

import org.java.dao.SysRoleMapper;
import org.java.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 获得全部职位
     * @return
     */
    public List<SysRole> getAllSysRole(){
        return sysRoleMapper.selectAll();
    }
}
