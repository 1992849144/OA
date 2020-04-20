package org.java.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 我写的用户的
 */
@Data
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    @Id
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String nickname;

    /**
     * 所在部门
     */
    @Column(name = "departId")
    private Integer departid;

    /**
     * 性别
     */
    private Integer gender;


    /**
     * 用户状态
     */
    @Column(name = "userState")
    private Integer userstate;

    /**
     * 用户图片
     */
    private String picture;

    public List<ScheduleList> scheduleLists;//日程集合

}