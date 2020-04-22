package org.java.web.PersonnelManagement;

import org.java.entity.SysRole;
import org.java.service.DepInfoService;
import org.java.service.SysRoleService;
import org.java.service.UserManageService;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理用户
 */
@Controller
@RequestMapping("/userManage")
public class UserManageController {

    @Autowired
    private UserManageService userManageService;

    //获得所有的用户信息
    @RequestMapping("/getAllUserInfo")
    @ResponseBody
    public ResultVo<Map> getAllDep(Integer page, Integer limit,String username,String nickname){
        return userManageService.getAllDep(page,limit,username,nickname);
    }


    /**
     * 修改图片
     * @param file
     * @param servletRequest
     * @return
     * @throws Exception
     */
    @Transactional
    @RequestMapping("/upload")
    @ResponseBody
    public Map uploadPicture( MultipartFile file, HttpServletRequest servletRequest) throws Exception {
        System.out.println("********************");
        Map res = new HashMap();
        //上传文件路径
        String path ="D:\\project\\oa\\oa\\src\\main\\resources\\static\\images";
        System.out.println("文件名称:"+file.getOriginalFilename());
        //上传文件名
        String name = file.getOriginalFilename();//上传文件的真实名称

        File newFile  = new File(path, name);
        //判断，文件夹是否存在，不存在，则创建
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        //将上传文件中的数据，写入到新文件中
        file.transferTo(newFile);

        // resUrl.put("src", tempFile.getPath());
        res.put("code", "0");
        res.put("msg", "");
        res.put("data", newFile.getName());
        return res;
    }

    /**
     * 添加用户
     * @param map
     */
    @PostMapping("addUser")
    @ResponseBody
    @Transactional
    public void addUser(@RequestParam Map map){
        userManageService.addUser(map);
    }


    /**
     * 修改用户的时候，显示在文本框
     * @param id
     * @param model
     * @return
     */
    @GetMapping("showUpdatUser")
    public String showUpdatUser(Integer id, Model model){
        Map map = userManageService.showUpdatUser(id);//根据id，获得用户详情
        System.out.println(map);
        model.addAttribute("map",map);
        return "/PersonnelManagement/updateUser";
    }

    /**
     * 修改用户
     * @param map
     */
    @PostMapping("updateUser")
    @ResponseBody
    @Transactional
    public void updateUser(@RequestParam Map map){
        userManageService.updateUser(map);
    }

}
