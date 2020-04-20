package org.java.service;

import org.java.dao.MessagetypeMapper;
import org.java.entity.Messagetype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息类型
 */
@Service
public class MessagetypeService {

    @Autowired
    private MessagetypeMapper messagetypeMapper;

    /**
     * 获得所有消息类型
     * @return
     */
    public List<Messagetype> getAllMessagetype(){
        return messagetypeMapper.selectAll();
    }
}
