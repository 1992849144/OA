package org.java.entity;

import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * 用户角色
     */
    @Column(name = "roleId")
    private Integer roleid;

    /**
     * 用户状态
     */
    @Column(name = "userState")
    private Integer userstate;

    /**
     * 图片
     */
    private String picture;

    /**
     * 账号是否锁定，1：锁定，0未锁定
     */
    private Integer locked;

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取真实姓名
     *
     * @return nickname - 真实姓名
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置真实姓名
     *
     * @param nickname 真实姓名
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取所在部门
     *
     * @return departId - 所在部门
     */
    public Integer getDepartid() {
        return departid;
    }

    /**
     * 设置所在部门
     *
     * @param departid 所在部门
     */
    public void setDepartid(Integer departid) {
        this.departid = departid;
    }

    /**
     * 获取性别
     *
     * @return gender - 性别
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取用户角色
     *
     * @return roleId - 用户角色
     */
    public Integer getRoleid() {
        return roleid;
    }

    /**
     * 设置用户角色
     *
     * @param roleid 用户角色
     */
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    /**
     * 获取用户状态
     *
     * @return userState - 用户状态
     */
    public Integer getUserstate() {
        return userstate;
    }

    /**
     * 设置用户状态
     *
     * @param userstate 用户状态
     */
    public void setUserstate(Integer userstate) {
        this.userstate = userstate;
    }

    /**
     * 获取图片
     *
     * @return picture - 图片
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置图片
     *
     * @param picture 图片
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 获取账号是否锁定，1：锁定，0未锁定
     *
     * @return locked - 账号是否锁定，1：锁定，0未锁定
     */
    public Integer getLocked() {
        return locked;
    }

    /**
     * 设置账号是否锁定，1：锁定，0未锁定
     *
     * @param locked 账号是否锁定，1：锁定，0未锁定
     */
    public void setLocked(Integer locked) {
        this.locked = locked;
    }
}