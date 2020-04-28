package org.java.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.java.dao.SysRoleMapper;
import org.java.dao.UserManageMapper;
import org.java.dao.UtilMapper;
import org.java.entity.SysRole;
import org.java.entity.SysUser;
import org.java.entity.Util;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 梅佳杰写的用户的
 */
@Service
public class UserManageService {

    @Autowired
    private UserManageMapper userManageMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private UtilMapper utilMapper;

    @Autowired
    private HttpSession session;

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
        map1.put("role_id",map.get("name"));
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

    /**
     * 查询除管理员外的所有用户信息
     * @param page
     * @param limit
     * @return
     */
    public ResultVo<Map> getSysUser(Integer page, Integer limit,String username,String nickname) {
        PageHelper.startPage(page,limit);
        PageInfo<Map> pageInfo = new PageInfo<>(userManageMapper.getSysUser(username,nickname));
        //将查询结果，封装成ResultVo
        ResultVo<Map> resultVo = new ResultVo<>();
        resultVo.setData(pageInfo.getList());//集合
        resultVo.setCount(pageInfo.getTotal());//总数
        return resultVo;
    }

    /**
     * 获得权限树
     * @return
     */
    public List<Util> getAllUtil(){
        //获取所有的菜单(包括子菜单和父级菜单)
        List<Map> list = utilMapper.getMenu();
        //创建一个集合用于保存所有的主菜单
        List<Util> rootMeun=new ArrayList<>();
        //遍历所有菜单集合,如果是主菜单的话直接放入rootMeun集合
        for (Map m:list){
            Util util=new Util();
            util.setId((Integer) m.get("id"));
            util.setTitle(m.get("name").toString());
            rootMeun.add(util);
        }

        //这个是遍历所有主菜单,分别获取所有主菜单的所有子菜单
        for (Util info:rootMeun){
            //获取所有子菜单 递归
            List<Map> childrenList=utilMapper.getSecondaryMenu(info.getId());
            List<Util> departInfo=new ArrayList<>();
            for (Map m:childrenList){
                Util departInfo1=new Util();
                departInfo1.setId(Integer.parseInt(m.get("id").toString()));
                departInfo1.setTitle(m.get("name").toString());
                departInfo.add(departInfo1);
            }
            info.setChildren(departInfo);
            getchildrenMeun(departInfo);
        }
        return rootMeun;
    }

    /**
     *    递归获取子菜单(这个我也不太理解，copy过去就行)
     **/
    public List<Util> getchildrenMeun(List<Util> allMeun){
        for (Util u:allMeun){
            List<Util> departInfo=new ArrayList<>();
            List<Map> levelMenu = utilMapper.getLevelMenu(u.getId());
            for (Map m:levelMenu){
                Util departInfo1=new Util();
                departInfo1.setId(Integer.parseInt(m.get("id").toString()));
                departInfo1.setTitle(m.get("name").toString());
                departInfo.add(departInfo1);
            }
            u.setChildren(departInfo);
        }
        return allMeun;
    }


    /**
     * 添加权限
     * @param map
     */
    public void  addSysRolePermission(Map map){
        Integer id =  Integer.parseInt(map.get("id").toString());
        Map map1 = userManageMapper.showUpdatUser(id);
        Integer ids=(Integer) map1.get("Ids");
        map.put("ids",ids);
        userManageMapper.addSysRolePermission(map);
    }
}
