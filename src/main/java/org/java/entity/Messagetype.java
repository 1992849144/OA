package org.java.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 消息类型
 */
public class Messagetype implements Serializable {
    /**
     * 消息类型Id
     */
    @Id
    @Column(name = "messageTypeId")
    private Integer messagetypeid;

    /**
     * 消息类型名称
     */
    @Column(name = "messageTypeName")
    private String messagetypename;

    /**
     * 获取消息类型Id
     *
     * @return messageTypeId - 消息类型Id
     */
    public Integer getMessagetypeid() {
        return messagetypeid;
    }

    /**
     * 设置消息类型Id
     *
     * @param messagetypeid 消息类型Id
     */
    public void setMessagetypeid(Integer messagetypeid) {
        this.messagetypeid = messagetypeid;
    }

    /**
     * 获取消息类型名称
     *
     * @return messageTypeName - 消息类型名称
     */
    public String getMessagetypename() {
        return messagetypename;
    }

    /**
     * 设置消息类型名称
     *
     * @param messagetypename 消息类型名称
     */
    public void setMessagetypename(String messagetypename) {
        this.messagetypename = messagetypename;
    }
}