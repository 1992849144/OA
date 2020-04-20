package org.java.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sys_role")
public class SysRole implements Serializable {
    /**
     * 角色编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 是否可用,1：可用，0不可用
     */
    private Integer available;

    /**
     * 获取角色编号
     *
     * @return id - 角色编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置角色编号
     *
     * @param id 角色编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取是否可用,1：可用，0不可用
     *
     * @return available - 是否可用,1：可用，0不可用
     */
    public Integer getAvailable() {
        return available;
    }

    /**
     * 设置是否可用,1：可用，0不可用
     *
     * @param available 是否可用,1：可用，0不可用
     */
    public void setAvailable(Integer available) {
        this.available = available;
    }
}