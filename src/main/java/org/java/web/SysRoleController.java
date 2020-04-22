package org.java.web;

import org.java.entity.SysRole;
import org.java.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 获得全部职位
     * @return
     */
    @PostMapping("init")
    @ResponseBody
    public List<SysRole> getAllSysRole(){
        return sysRoleService.getAllSysRole();
    }

}
