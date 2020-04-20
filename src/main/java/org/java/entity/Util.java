package org.java.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 封装树的
 */
@Data
public class Util implements Serializable {
    private Integer id;

    private String title;

    private  Integer principalUser;

    private List<Util> children;
}
