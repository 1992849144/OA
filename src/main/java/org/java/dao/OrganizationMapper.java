package org.java.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 机构
 */
public interface OrganizationMapper  {

    //获得机构
    public List<Map> getAllOrg(@Param("orgName") String orgName, @Param("orgAbbreviation") String orgAbbreviation);

    //添加机构
    public void addOrg(@Param("branchName") String branchName, @Param("branchShortName") String branchShortName);

    //修改机构
    public void updateOrg(@Param("branchName") String branchName, @Param("branchShortName") String branchShortName, @Param("branchId") Integer branchId);

    //删除机构
    public void deleteOrg(Integer branchId);

    /**
     * 获得所有机构
     * @return
     */
    @Select("select * from branchInfo")
    public List<Map> getAllBranchInfo();
}
