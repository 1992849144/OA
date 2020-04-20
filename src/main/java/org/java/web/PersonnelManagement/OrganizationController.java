package org.java.web.PersonnelManagement;

import org.java.service.OrganizationService;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 机构信息
 */
@Controller
@RequestMapping("PersonnelManagement")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    //获得所有机构
    @RequestMapping("/getAllOrg")
    @ResponseBody
    public ResultVo<Map> getAllOrg(String orgName,String orgAbbreviation,Integer page,Integer limit){
        ResultVo<Map> resultVo = organizationService.getAllOrg(orgName, orgAbbreviation, page, limit);
        return resultVo;
    }

    //添加机构
    @RequestMapping("/addOrg")
    @ResponseBody
    @Transactional
    public void addOrg(String branchName,String branchShortName){
        organizationService.addOrg(branchName,branchShortName);
    }

    //修改机构
    @RequestMapping("/update")
    @ResponseBody
    @Transactional
    public void updateOrg(String branchName,String branchShortName,Integer branchId){
        organizationService.updateOrg(branchName,branchShortName,branchId);
    }


    //删除机构
    @RequestMapping("/delete")
    @ResponseBody
    @Transactional
    public void deleteOrg(Integer branchId){
         organizationService.deleteOrg(branchId);
    }

    /**
     * 获得所有机构
     * @return
     */
    @PostMapping("getAllBranchInfo")
    @ResponseBody
    public List<Map> getAllBranchInfo(){
        return organizationService.getAllBranchInfo();
    }
}
