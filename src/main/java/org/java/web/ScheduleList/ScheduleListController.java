package org.java.web.ScheduleList;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.java.entity.ScheduleList;
import org.java.entity.Util;
import org.java.service.ScheduleService;
import org.java.service.UtilService;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 日程
 */
@Controller
@RequestMapping("scheduleList")
public class ScheduleListController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UtilService utilService;

    /**
     * 显示所有日程到日程控件
     * @return
     */
    @PostMapping("init")
    @ResponseBody
    public List<Map> init(){
        return scheduleService.getScheduleListByUserName();
    }

    /**
     *根据用户和等于0的就是部门日程
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("getScheduleListByIsremindEqualZero")
    @ResponseBody
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualZero(String title,String startTime,String endTime,Integer page, Integer limit){
        ResultVo<ScheduleList> resultVo = scheduleService.getScheduleListByIsremindEqualZero(title,startTime,endTime,page, limit);
        return resultVo;
    }

    /**
     * 根据用户和等于1的就是个人日程
     * @param
     * @return
     */
    @GetMapping("getScheduleListByIsremindEqualOne")
    @ResponseBody
    public ResultVo<ScheduleList> getScheduleListByIsremindEqualOne(String title,String startTime,String endTime,Integer page, Integer limit){
        ResultVo<ScheduleList> resultVo = scheduleService.getScheduleListByIsremindEqualOne(title,startTime,endTime,page, limit);
        return resultVo;
    }

    /**
     * 删除个人日程
     * @param scheduleId
     * @return
     */
    @Transactional
    @PostMapping("delScheduleList")
    @RequiresPermissions("schedule:delScheduleList")
    @ResponseBody
    public int delScheduleList(Integer scheduleId){
        scheduleService.delScheduleList(scheduleId);
        return 1;
    }

    /**
     * 删除部门日程
     * @param scheduleId
     * @return
     */
    @Transactional
    @PostMapping("delDepartmentScheduleList")
    @RequiresPermissions("schedule:delDepartmentScheduleList")
    @ResponseBody
    public int delDepartmentScheduleList(Integer scheduleId){
        scheduleService.delDepartmentScheduleList(scheduleId);
        return 1;
    }

    /**
     * 修改个人日程
     * @param scheduleList
     */
    @Transactional
    @PostMapping("updateScheduleList")
    @RequiresPermissions("schedule:updateScheduleList")
    @ResponseBody
    public int updateScheduleList(ScheduleList scheduleList){
        scheduleService.updateScheduleList(scheduleList);
        return 1;
    }

    /**
     * 修改部门日程
     * @param map
     */
    @Transactional
    @PostMapping("updateDepartmentScheduleList")
    @RequiresPermissions("schedule:updateDepartmentScheduleList")
    @ResponseBody
    public int updateDepartmentScheduleList(@RequestParam Map  map){
        scheduleService.updateDepartmentScheduleList(map);
        return 1;
    }

    /**
     * 添加日程
     * @param
     */
    @Transactional
    @PostMapping("addScheduleList")
    @RequiresPermissions("schedule:add")
    @ResponseBody
    public int addScheduleList(ScheduleList scheduleList ){
        scheduleService.addScheduleList(scheduleList);
        return 1;
    }

    /**
     * 获得树的数据
     * @return
     */
    @PostMapping("show")
    @ResponseBody
    public List<Util> show(){
        //获取所有的菜单(包括子菜单和父级菜单)
        List<Util> list = utilService.getAllDepartInfo();

        //创建一个集合用于保存所有的主菜单
        List<Util> rootMeun=new ArrayList<>();

        //遍历所有菜单集合,如果是主菜单的话直接放入rootMeun集合
        for (Util info:list){
            //判断为0是因为我的主菜单标识是0
            rootMeun.add(info);
        }

        //这个是遍历所有主菜单,分别获取所有主菜单的所有子菜单
        for (Util info:rootMeun){
            //获取所有子菜单 递归
            List<Map> childrenList=utilService.getDepartInfoByBranchId(info.getId());
            List<Util> departInfo=new ArrayList<>();
            for (Map m:childrenList){
                Util departInfo1=new Util();
                departInfo1.setId(Integer.parseInt(m.get("departId").toString()));
                departInfo1.setTitle(m.get("departName").toString());
                departInfo1.setPrincipalUser(Integer.parseInt(m.get("principalUser").toString()));
                departInfo.add(departInfo1);
            }
            info.setChildren(departInfo);
            getchildrenMeun(departInfo,info.getId());
        }
        return rootMeun;
    }

    /**
     *    递归获取子菜单(这个我也不太理解，copy过去就行)
     **/
    public List<Util> getchildrenMeun(List<Util> allMeun,Integer id){
        int count=0;
        for (Util p:allMeun){
            List<Map> permissionByPid = utilService.getDepartInfoById(id);
            List<Util> departInfo=new ArrayList<>();
            for (Map m:permissionByPid){
                int principalUser = Integer.parseInt(m.get("principalUser").toString());
                if(principalUser==0){
                    if (count==0){
                        p.setChildren(departInfo);
                        count++;
                        break;
                    }
                }else {
                    int departId = Integer.parseInt(m.get("departId").toString());
                    List<Map> user = utilService.getDepartInfoByDepartId(departId );
                    for (Map ms:user){
                        if (p.getTitle().equals(ms.get("departName"))){
                            Util departInfo1=new Util();
                            departInfo1.setId(Integer.parseInt(ms.get("id").toString()));
                            departInfo1.setTitle(ms.get("nickname").toString());
                            departInfo.add(departInfo1);
                        }

                    }
                    p.setChildren(departInfo);
                }
            }
        }
        return allMeun;
    }
}
