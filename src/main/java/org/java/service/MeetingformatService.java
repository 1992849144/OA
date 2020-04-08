package org.java.service;

import org.java.dao.MeetingformatMapper;
import org.java.entity.Meetingformat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingformatService {

    @Autowired
    private MeetingformatMapper meetingformatMapper;

    /**
     * 获得所有会议
     * @return
     */
    public List<Meetingformat> getAllMeetingformat(){
       return meetingformatMapper.selectAll();
    }
}
