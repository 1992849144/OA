package org.java.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.java.entity.Personalnotes;
import tk.mybatis.MyMapper;

import java.util.List;

public interface PersonalnotesMapper extends MyMapper<Personalnotes> {

    /**
     * 根据用户名获得改用户的所有便签
     * @param userId
     * @return
     */
    @Select("select * from personalNotes where userId=#{userId}")
    public List<Personalnotes> getPersonalnotesByUserId(@Param("userId") String userId);
}