package org.java.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 部门
 */
public interface DepInfoMapper {

    /**
     * 获得所有部门，加多条件
     * @param departName
     * @param branchId
     * @param principalUser
     * @return
     */
    public List<Map> getllDep(@Param("departName") String departName,@Param("branchId") String branchId,@Param("principalUser") String principalUser);

    /**
     * 删除部门
     * @param departId
     */
    public void delete(Integer departId);

    /**
     * 添加部门
     * @param map
     */
    public void addDep(Map map);

    /**
     * 修改部门
     * @param map
     */
    public void updateDep(Map map);

    /**
     * 获得部门
     * @return
     */
    @Select("SELECT * FROM departInfo WHERE  departName IS NOT NULL")
    public List<Map> getDep();

    /**
     * 查询部门是否存在
     * @return
     */
    @Select("select * from departInfos where departName=#{departName}")
    public List<Map> getDepByDepartName(@Param("departName") String departName);

    /**
     * 添加departInfo这张表
     * @param map
     */
    public void addDepartInfo(Map map);

    /**
     * 修改departInfo这张表
     * @param map
     */
    public void updateDepartInfo(Map map);

    /**
     * 根据部门名称获得departInfo部门详情
     * @param departName
     * @return
     */
    @Select("select * from departInfo where departName=#{departName}")
    public Map getDfByDepartName(String departName);

    /**
     * 根据部门id找机构
     * @param id
     * @return
     */
    @Select("select * from departInfo where departId=#{id}")
    public Map getDepByDepartById (Integer id);

    /**
     * 删除departinfo表的部门，根据部门名称删除
     * @param departName
     */
    @Delete("delete from departInfo where departName=#{departName}")
    public void deleteDepartinfo(@Param("departName") String departName);
}
