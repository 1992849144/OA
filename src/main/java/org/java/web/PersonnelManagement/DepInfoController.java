package org.java.web.PersonnelManagement;

import org.java.entity.Branchinfo;
import org.java.entity.SysUser;
import org.java.service.BranchinfoService;
import org.java.service.DepInfoService;
import org.java.service.SysUserService;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */
@Controller
@RequestMapping("/depInfo")
public class DepInfoController {

    @Autowired
    private DepInfoService depInfoService;

    @Autowired
    private BranchinfoService branchinfoService;

    @Autowired
    private SysUserService sysUserService;

    //获得部门信息
    @RequestMapping("/getAllDep")
    @ResponseBody
    public ResultVo<Map> getAllDep(Integer page, Integer limit,String departName,String branchId,String principalUser){
        return depInfoService.getAllDep(page,limit,departName,branchId,principalUser);
    }


    //删除部门人员
    @RequestMapping("/delete")
    @ResponseBody
    @Transactional
    public void delete(Integer departId){
        depInfoService.delete(departId);
    }


    /**
     * 添加部门
     * @param map
     */
    @RequestMapping("/addDep")
    @ResponseBody
    @Transactional
    public void addDep(@RequestParam Map map){
        depInfoService.addDep(map);
    }

    /**
     * 修改部门
     */
    @RequestMapping("/updateDep")
    @ResponseBody
    @Transactional
    public void updateDep(@RequestParam Map map){
        depInfoService.updateDep(map);
    }

    //获得部门
    @RequestMapping("/getDep")
    @ResponseBody
    public List<Map> getDep(){
        return depInfoService.getDep();
    }

    /**
     * 查询部门是否存在
     * @return
     */
    @PostMapping("getDepByDepartName")
    @ResponseBody
    public int getDepByDepartName(String departName){
        List<Map> depByDepartName = depInfoService.getDepByDepartName(departName);
        if (depByDepartName.isEmpty()){
            return 0;
        }
        return 1;
    }

    /**
     * 根据部门id找机构
     * @param id
     * @return
     */
    @PostMapping("getDepByDepartById")
    @ResponseBody
    public Map getDepByDepartById(Integer id){
        Map dep = depInfoService.getDepByDepartById(id);//获得部门信息

        int branchId = Integer.parseInt(dep.get("branchId").toString());//获得机构id
        Branchinfo branchinfo=branchinfoService.getBranchinfoById(branchId);//获得机构详情
        dep.put("departId",branchinfo.getBranchid());
        return dep;
    }

    /**
     * 根据部门id找用户
     * @param id
     */
    @PostMapping("getDepByDepartByIdUser")
    @ResponseBody
    public Map getDepByDepartByIdUser(Integer id){
        Map dep = depInfoService.getDepByDepartById(id);//获得部门信息

        int departId = Integer.parseInt(dep.get("departId").toString());//获得部门id

        List<SysUser> sysUser = sysUserService.getSysUserByDepartId(departId);
        dep.put("sysUser",sysUser);
        return dep;
    }
}
