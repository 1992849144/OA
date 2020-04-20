package org.java.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.java.dao.SysRoleMapper;
import org.java.dao.UserManageMapper;
import org.java.entity.SysRole;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 梅佳杰写的用户的
 */
@Service
public class UserManageService {

    @Autowired
    private UserManageMapper userManageMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //获得所有的用户信息
    public ResultVo<Map> getAllDep(Integer page, Integer limit,String username,String nickname){
        PageHelper.startPage(page,limit);
        PageInfo<Map> pageInfo = new PageInfo<>(userManageMapper.getAllUserInfo(username,nickname));
        //将查询结果，封装成ResultVo
        ResultVo<Map> resultVo = new ResultVo<>();
        resultVo.setData(pageInfo.getList());//集合
        resultVo.setCount(pageInfo.getTotal());//总数
        return resultVo;
    }

    /**
     * 添加用户
     * @param map
     */
    public void addUser(Map map){
        //添加用户表
        int id  = new Random().nextInt(50000) + 100;
        String userStates = map.get("userState").toString();
        if (userStates.equals("正常状态")){
            map.put("userStates",1);
        }
        map.put("id",id);

        //密码加密
        String password = map.get("password").toString();

        //指定混合在加密算法中的盐（密钥）
        String salt = "accp";

        //设置加密次数
        int count = 3;

        Md5Hash md5 = new Md5Hash(password,salt,count);
        String s = md5.toString();

        map.put("s",s);
        userManageMapper.addUser(map);

        //用户与角色的关系表
       Map map1=new HashMap();
       map1.put("user_id",id);
       map1.put("role_id",map.get("departName"));
       sysRoleMapper.addSysUserRole(map1);
    }


    /**
     * 根据用户id获得用户详情
     * @param id
     * @return
     */
    public Map showUpdatUser(Integer id){
        return  userManageMapper.showUpdatUser(id);
    }

    /**
     * 修改用户
     * @param map
     */
    public void updateUser(Map map){
        //修改用户表
        userManageMapper.updateUser(map);

        //修改用户与角色的部门id
        Integer id = Integer.parseInt(map.get("id").toString());
        String name = (String) map.get("name");
        Map map1=sysRoleMapper.getSysUserRoleByUseIdRoleId(id);
        if (name!=null){
            map1.put("name",name);
            sysRoleMapper.updateSysUserRole(map1);
        }
    }
}
