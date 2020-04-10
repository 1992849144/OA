package org.java.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Data
public class Personalnotes implements Serializable {
    @Id
    @Column(name = "personalNotesId")
    private Integer personalnotesid;

    @Column(name = "personalNotesTitle")
    private String personalnotestitle;

    @Column(name = "personalNotesContent")
    private String personalnotescontent;

    @Column(name = "userId")
    private String userid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createtime;

    /**
     * @return personalNotesId
     */
    public Integer getPersonalnotesid() {
        return personalnotesid;
    }

    /**
     * @param personalnotesid
     */
    public void setPersonalnotesid(Integer personalnotesid) {
        this.personalnotesid = personalnotesid;
    }

    /**
     * @return personalNotesTitle
     */
    public String getPersonalnotestitle() {
        return personalnotestitle;
    }

    /**
     * @param personalnotestitle
     */
    public void setPersonalnotestitle(String personalnotestitle) {
        this.personalnotestitle = personalnotestitle;
    }

    /**
     * @return personalNotesContent
     */
    public String getPersonalnotescontent() {
        return personalnotescontent;
    }

    /**
     * @param personalnotescontent
     */
    public void setPersonalnotescontent(String personalnotescontent) {
        this.personalnotescontent = personalnotescontent;
    }

    /**
     * @return userId
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
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
}