package org.java.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class Meetingformat implements Serializable {
    @Id
    private Integer meetingformatid;

    private String meetingformatname;

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
     * @return meetingformatname
     */
    public String getMeetingformatname() {
        return meetingformatname;
    }

    /**
     * @param meetingformatname
     */
    public void setMeetingformatname(String meetingformatname) {
        this.meetingformatname = meetingformatname;
    }
}