package org.java.vo;

import lombok.Data;

import java.util.List;

/**
 * 该对象，用于将控制器的数据，返回到页面显示
 * @param <T>
 */
@Data
public class ResultVo<T> {

    private List<T> data;//要返回的集合
    private String msg;//消息内容
    private int code=0;//状态码
    private Long count;//数据总数，用于分页
}
