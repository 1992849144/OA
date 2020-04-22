package org.java.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.dao.DepInfoMapper;
import org.java.entity.SysUser;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门
 */
@Service
public class DepInfoService {

    @Autowired
    private DepInfoMapper depInfoMapper;

    @Autowired
    private SysUserService sysUserService;

    //获得部门信息
    public ResultVo<Map> getAllDep(Integer page, Integer limit,String departName,String branchId,String principalUser){
        PageHelper.startPage(page,limit);
        PageInfo<Map> pageInfo = new PageInfo<>(depInfoMapper.getllDep(departName,branchId,principalUser));
        //将查询结果，封装成ResultVo
        ResultVo<Map> resultVo = new ResultVo<>();
        resultVo.setData(pageInfo.getList());//集合
        resultVo.setCount(pageInfo.getTotal());//总数
        return resultVo;
    }

    //删除部门人员
    public void delete(Integer departId,String departName) {
        //删除departinfos表的部门
        depInfoMapper.delete(departId);

        depInfoMapper.deleteDepartinfo(departName);
    }

    /**
     * 添加部门
     * @param map
     */
    public void addDep(Map map){
        //是往departInfos表添加
        SysUser user = sysUserService.getSysUserByUserName(map.get("principalUser").toString());
        map.put("principalUsers",user.getId());//获得部门负责人的id
        depInfoMapper.addDep(map);

        //是往departInfo表添加
        Map map1=new HashMap();
        map1.put("departName",map.get("departName"));//部门名称
        map1.put("principalUser",user.getId());//获得部门负责人的id
        map1.put("branchId",map.get("branchId"));//机构id
        depInfoMapper.addDepartInfo(map1);
}

    /**
     * 修改部门
     * @param map
     */
    public void updateDep(Map map){
        //是往departInfos表修改
        SysUser user = sysUserService.getSysUserByUserName(map.get("principalUser").toString());
        map.put("principalUsers",user.getId());
        depInfoMapper.updateDep(map);

        //是往departInfo表修改
        Map map1=new HashMap();
        Map depInfo = depInfoMapper.getDfByDepartName(map.get("departNames").toString());
        map1.put("departId",depInfo.get("departId"));//部门id
        map1.put("departName",map.get("departName"));//部门名称
        map1.put("principalUser",user.getId());//获得部门负责人的id
        depInfoMapper.updateDepartInfo(map1);
    }

    /**
     * 查询所有部门，不加条件
     * @return
     */
    public List<Map> getDep(){
        return  depInfoMapper.getDep();
    }

    /**
     * 查询部门是否存在
     * @return
     */
    public List<Map> getDepByDepartName(String departName){
        return depInfoMapper.getDepByDepartName(departName);
    }

    /**
     * 根据部门id找机构
     * @param id
     * @return
     */
    public Map getDepByDepartById (Integer id){
        return depInfoMapper.getDepByDepartById(id);
    }
}
