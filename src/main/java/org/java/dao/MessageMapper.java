package org.java.dao;

import org.apache.ibatis.annotations.*;
import org.java.entity.Message;
import org.springframework.web.bind.annotation.PostMapping;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 邮件
 */
public interface MessageMapper extends MyMapper<Message> {

    /**
     * 管理员获得所有邮件详情
     * @return
     */
    public List<Map> getAllMessage(@Param("time") String time,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 根据用户id，获得该用户的所有邮件/
     * @return
     */
    public List<Map> getMessageByFromUserId(@Param("fromUserId") Integer fromUserId,@Param("time") String time,@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 根据邮件id,修改为已发布的邮件，只修改IfPublish这个字段
     * @param messageId
     */
    public void updateIfPublish(Integer messageId);

    /**
     * 添加邮件
     * @param map
     */
    public void addMessage(Map map);

    /**
     * 添加到消息选择人（choosePerson）
     * @param map
     */
    public void addChoosePerson(Map map);

    /**
     * 修改发送给所有人的消息
     * @param map
     */
    public void updateMessage(Map map);

    /**
     * 根据日程id，删除ChoosePerson详情
     * @param messageId
     */
    @Delete("delete from choosePerson where messageId=#{messageId}")
    public void delChoosePerson(Integer messageId);

    /**
     * 统计邮箱
     * @param userId
     * @return
     */
    public List<Map> statisticsEmil(Integer userId);

    /**
     * 统计已发送的
     * @return
     */
    @Select("SELECT * FROM message WHERE fromUserId=#{userId} AND ifPublish=1")
    public List<Map> sent(Integer userId);

    /***
     * 统计草稿箱
     * @param userId
     * @return
     */
    @Select("SELECT * FROM message WHERE fromUserId=#{userId} AND ifPublish=0")
    public List<Map> drafts(Integer userId);

    /**
     * 根据用户id，获得消息详情
     * @param userId
     * @return
     */
    public List<Map> getMessageByUserId(Integer userId);

    /**
     * 根据消息id，获得相应的消息选择人
     * @param messageId
     * @return
     */
    public List<Map> getChoosePersonByMessageId(Integer messageId);

    /**
     * 添加删除消息（DelMessage）
     * @param map
     */
    @Insert("insert into delmessage values(null,#{messageId},#{userId})")
    public void addDelMessage(Map map);

    /**
     * 根据用户id，添加删除的消息
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) FROM delmessage WHERE userId=#{userId}")
    public Integer countDelmessage(Integer userId);

    /**
     * 修改消息为删除 0：末删除 1：已删除
     * @param messageId
     */
    @Update("update message set ifdelete=1 where messageId=#{messageId}")
    public void updateIfDelete(Integer messageId);

    /**
     * 查询是否已读
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) FROM choosePerson WHERE userId=#{userId} and ifRead=2")
    public Integer queryIfRead(Integer userId);

    /**
     * 用户点击查看，修改消息发送对象为已读
     * @param userId
     * @param messageId
     */
    @Update("update choosePerson set ifRead=1 where messageId=#{messageId} and userId=#{userId}")
    public void updateMessageToUser(@Param("userId") Integer userId,@Param("messageId") Integer messageId);
}

