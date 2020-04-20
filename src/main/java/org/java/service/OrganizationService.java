package org.java.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.dao.OrganizationMapper;
import org.java.entity.Personalnotes;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 机构
 */
@Service
public class OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    //获得机构
    public ResultVo<Map> getAllOrg(String orgName, String orgAbbreviation, Integer page, Integer limit){
        PageHelper.startPage(page,limit);
        PageInfo<Map> pageInfo = new PageInfo<>(organizationMapper.getAllOrg(orgName,orgAbbreviation));
        //将查询结果，封装成ResultVo
        ResultVo<Map> resultVo = new ResultVo<>();
        resultVo.setData(pageInfo.getList());//集合
        resultVo.setCount(pageInfo.getTotal());//总数
        return resultVo;
    }

    //添加机构
    public void addOrg(String branchName,String branchShortName){
        organizationMapper.addOrg(branchName,branchShortName);
    }

    //修改机构
    public void updateOrg(String branchName,String branchShortName,Integer branchId){
        organizationMapper.updateOrg(branchName,branchShortName,branchId);
    }

    //删除机构
    public void deleteOrg(Integer branchId) {
         organizationMapper.deleteOrg(branchId);
    }

    /**
     * 获得所有机构
     * @return
     */
    public List<Map> getAllBranchInfo(){
       return organizationMapper.getAllBranchInfo();
    }
}
