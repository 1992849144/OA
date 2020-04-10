package org.java.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "schedule_list")
public class ScheduleList implements Serializable {
    @Id
    @Column(name = "scheduleId")
    private Integer scheduleid;

    private String title;

    private Integer placeid;

    private Integer meetingformatid;

    @Column(name = "start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String miaoshu;

    private Integer appointment;

    @Column(name = "user_id")
    private Integer userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createtime;

    @Column(name = "is_remind")
    private Double isRemind;

    private String colour;


    private SysUser sysUser;//用户对象


    private Meetingformat meetingformat;//会议类型对象

    /**
     * @return scheduleId
     */
    public Integer getScheduleid() {
        return scheduleid;
    }

    /**
     * @param scheduleid
     */
    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return placeid
     */
    public Integer getPlaceid() {
        return placeid;
    }

    /**
     * @param placeid
     */
    public void setPlaceid(Integer placeid) {
        this.placeid = placeid;
    }

    /**
     * @return meetingformatid
     */
    public Integer getMeetingformatid() {
        return meetingformatid;
    }

    /**
     * @param meetingformatid
     */
    public void setMeetingformatid(Integer meetingformatid) {
        this.meetingformatid = meetingformatid;
    }

    /**
     * @return start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * @return miaoshu
     */
    public String getMiaoshu() {
        return miaoshu;
    }

    /**
     * @param miaoshu
     */
    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu;
    }

    /**
     * @return appointment
     */
    public Integer getAppointment() {
        return appointment;
    }

    /**
     * @param appointment
     */
    public void setAppointment(Integer appointment) {
        this.appointment = appointment;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return is_remind
     */
    public Double getIsRemind() {
        return isRemind;
    }

    /**
     * @param isRemind
     */
    public void setIsRemind(Double isRemind) {
        this.isRemind = isRemind;
    }

    /**
     * @return colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * @param colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public Meetingformat getMeetingformat() {
        return meetingformat;
    }

    public void setMeetingformat(Meetingformat meetingformat) {
        this.meetingformat = meetingformat;
    }

    @Override
    public String toString() {
        return "ScheduleList{" +
                "scheduleid=" + scheduleid +
                ", title='" + title + '\'' +
                ", placeid=" + placeid +
                ", meetingformatid=" + meetingformatid +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", miaoshu='" + miaoshu + '\'' +
                ", appointment=" + appointment +
                ", userId=" + userId +
                ", createtime=" + createtime +
                ", isRemind=" + isRemind +
                ", colour='" + colour + '\'' +
                ", sysUser=" + sysUser +
                ", meetingformat=" + meetingformat +
                '}';
    }
}