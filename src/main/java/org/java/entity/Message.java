package org.java.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * 邮件
 */
@Data
public class Message implements Serializable {
    /**
     * 消息Id
     */
    @Id
    @Column(name = "messageId")
    private Integer messageid;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 开始有效时间
     */
    @Column(name = "beginTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begintime;

    /**
     * 有效结束时间
     */
    @Column(name = "endTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endtime;

    /**
     * 发送者
     */
    @Column(name = "fromUserId")
    private String fromuserid;

    /**
     * 是否已发布
     */
    @Column(name = "ifPublish")
    private Integer ifpublish;

    /**
     * 是否发送所有人
     */
    private String ifowner;

    /**
     * 发送时间
     */
    @Column(name = "recordTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date recordtime;

    private SysUser sysUser;//用户对象

    private Messagetype messagetype;//消息类型对象

}