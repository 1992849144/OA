package org.java.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 预约人
 */
public class Precontract implements Serializable {
    /**
     * 预约序号Id
     */
    @Id
    @Column(name = "preContractId")
    private Integer precontractid;

    /**
     * 日程Id
     */
    @Column(name = "scheduleId")
    private Integer scheduleid;

    /**
     * 预约人
     */
    @Column(name = "userId")
    private Integer userid;

    /**
     * 获取预约序号Id
     *
     * @return preContractId - 预约序号Id
     */
    public Integer getPrecontractid() {
        return precontractid;
    }

    /**
     * 设置预约序号Id
     *
     * @param precontractid 预约序号Id
     */
    public void setPrecontractid(Integer precontractid) {
        this.precontractid = precontractid;
    }

    /**
     * 获取日程Id
     *
     * @return scheduleId - 日程Id
     */
    public Integer getScheduleid() {
        return scheduleid;
    }

    /**
     * 设置日程Id
     *
     * @param scheduleid 日程Id
     */
    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    /**
     * 获取预约人
     *
     * @return userId - 预约人
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 设置预约人
     *
     * @param userid 预约人
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}