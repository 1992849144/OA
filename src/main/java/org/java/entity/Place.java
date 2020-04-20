package org.java.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地点
 */
@Data
public class Place implements Serializable {
    @Id
    private Integer placeid;

    private String placename;

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
     * @return placename
     */
    public String getPlacename() {
        return placename;
    }

    /**
     * @param placename
     */
    public void setPlacename(String placename) {
        this.placename = placename;
    }
}