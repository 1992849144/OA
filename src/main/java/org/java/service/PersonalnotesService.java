package org.java.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.dao.PersonalnotesMapper;
import org.java.entity.Personalnotes;
import org.java.entity.ScheduleList;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class PersonalnotesService {

    @Autowired
    private PersonalnotesMapper personalnotesMapper;

    /**
     * 根据用户名获得改用户的所有便签
     * @param userId
     * @return
     */
    public ResultVo<Personalnotes> getPersonalnotesByUserId(String userId,Integer page,Integer limit){
        PageHelper.startPage(page,limit);
        PageInfo<Personalnotes> pageInfo = new PageInfo<>(personalnotesMapper.getPersonalnotesByUserId(userId));

        //将查询结果，封装成ResultVo
        ResultVo<Personalnotes> resultVo = new ResultVo<>();

        resultVo.setData(pageInfo.getList());//集合
        resultVo.setCount(pageInfo.getTotal());//总数

        return resultVo;
    }

    /**
     * 添加个人便签
     * @param personalnotes
     */
    public void addPersonalnotes(Personalnotes personalnotes){
        personalnotesMapper.insert(personalnotes);
    }

    /**
     * 删除个人便签
     * @param personalnotesid
     */
    public void delPersonalnotes(Integer personalnotesid) {
        personalnotesMapper.deleteByPrimaryKey(personalnotesid);
    }

    /**
     * 修改个人便签
     * @param personalnotes
     */
    public void updatePersonalnotes(Personalnotes personalnotes) {
        personalnotesMapper.updateByPrimaryKey(personalnotes);
    }
}
