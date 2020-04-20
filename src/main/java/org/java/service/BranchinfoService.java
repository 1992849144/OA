package org.java.service;

import org.java.dao.BranchinfoMapper;
import org.java.entity.Branchinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构
 */
@Service
public class BranchinfoService {

    @Autowired
    private BranchinfoMapper branchinfoMapper;


    /**
     * 获得所有机构名称
     * @return
     */
    public List<Branchinfo> getAllBranchinfo(){
        return branchinfoMapper.selectAll();
    }

    /**
     * 根据机构id，获得机构详情
     * @param id
     * @return
     */
    public Branchinfo getBranchinfoById(Integer id){
        return branchinfoMapper.selectByPrimaryKey(id);
    }
}
