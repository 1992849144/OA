package org.java.service;

import org.java.dao.PlaceMapper;
import org.java.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {

    @Autowired
    private PlaceMapper placeMapper;

    /**
     * 获得所有地点
     * @return
     */
    public List<Place> getAllPlace(){
        return placeMapper.selectAll();
    }
}
