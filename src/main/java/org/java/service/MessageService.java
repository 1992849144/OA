package org.java.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.dao.MessageMapper;
import org.java.entity.Message;
import org.java.entity.ScheduleList;
import org.java.entity.SysUser;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 邮件
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private HttpSession session;

    /**
     * 获得邮件详情
     * @return
     */
    public ResultVo<Map> getAllMessage(Integer page,Integer limit,String name,Integer fromUserId,String time,String startTime,String endTime){
        PageHelper.startPage(page,limit);
        //将查询结果，封装成ResultVo
        ResultVo<Map> resultVo = new ResultVo<>();
        if (name.equals("系统管理员")){
            //管理员获得所有邮件
            PageInfo<Map> pageInfo = new PageInfo<Map>(messageMapper.getAllMessage(time,startTime,endTime));
            resultVo.setData(pageInfo.getList());//集合
            resultVo.setCount(pageInfo.getTotal());//总数
        }else{
            //根据用户id，获得该用户的所有邮件/
            PageInfo<Map> pageInfo = new PageInfo<Map>(messageMapper.getMessageByFromUserId(fromUserId,time,startTime,endTime));
            resultVo.setData(pageInfo.getList());//集合
            resultVo.setCount(pageInfo.getTotal());//总数
        }
        return resultVo;
    }


    /**
     * 删除邮件
     * @param messageId
     */
    public void delMessage(Integer messageId){
        //删除Message
        messageMapper.updateIfDelete(messageId);

//        //删除choosePerson表
//        messageMapper.delChoosePerson(messageId);

        //添加到delmessage
        Integer userId = (Integer) session.getAttribute("userId");
        Map map=new HashMap();
        map.put("messageId",messageId);
        map.put("userId",userId);
        messageMapper.addDelMessage(map);
    }

    /**
     * 根据邮件id,修改为已发布的邮件，只修改IfPublish这个字段
     * @param messageId
     */
    public void updateIfPublish(Integer messageId){
        messageMapper.updateIfPublish(messageId);
    }

    /**
     * 添加邮件
     * @param map
     */
    public void addMessage(Map map){
        Integer fromUserId = (Integer) session.getAttribute("userId");//获得用户id
        String ifowner = map.get("ifowner").toString();
        int messageId = new Random().nextInt(50000) + 100;
        if (ifowner.equals("所有人")){
            //添加到choosePerson
            List<SysUser> sysUser = sysUserService.getAllSysUser();//获得所有用户
            for(SysUser s:sysUser){
                Map map1=new HashMap();
                map1.put("userId",s.getId());
                map1.put("messageId",messageId);
                messageMapper.addChoosePerson(map1);
            }
        }else{
            //选择的特定人
            //添加到choosePerson
            String bns = map.get("bns").toString();//多个用户
            String username=(String)map.get("username");//用户名
            String nickname =(String) map.get("nickname");//昵称
            if (!StringUtils.isEmpty(bns)){
                if (bns.indexOf(',')==-1){
                    Map map1=new HashMap();
                    map1.put("userId",bns);
                    map1.put("messageId",messageId);
                    messageMapper.addChoosePerson(map1);
                }else{
                    String[] split = bns.split(",");
                    for (int i=0;i<split.length;i++){
                        Map map1=new HashMap();
                        map1.put("userId",split[i]);
                        map1.put("messageId",messageId);
                        messageMapper.addChoosePerson(map1);
                    }
                }
            }else if (!StringUtils.isEmpty(username)){
                Map login = sysUserService.login(username);
                Map map1=new HashMap();
                map1.put("userId",login.get("id"));
                map1.put("messageId",messageId);
                messageMapper.addChoosePerson(map1);
            }else if(!StringUtils.isEmpty(nickname)){
                SysUser sysUser = sysUserService.getSysUserByUserName(nickname);
                Map map1=new HashMap();
                map1.put("userId",sysUser.getId());
                map1.put("messageId",messageId);
                messageMapper.addChoosePerson(map1);
            }
        }
        //添加到message
        map.put("fromUserId",fromUserId);
        map.put("recordTime",new Date());
        map.put("ifPublish",0);
        map.put("messageId",messageId);
        messageMapper.addMessage(map);
    }

    /**
     * 消息修改时回显
     * @param messageId
     * @return
     */
    public Message showUpdateMessage(Integer messageId){
        return messageMapper.selectByPrimaryKey(messageId);
    }

    /**
     * 修改发送给所有人的消息
     * @param map
     */
    public void updateMessage(Map map){
        messageMapper.updateMessage(map);
    }


    /**
     * 统计邮箱
     * @return
     */
    public List<Map> statisticsEmil(){
        Integer userId = (Integer) session.getAttribute("userId");
        return messageMapper.statisticsEmil(userId);
    }

    /**
     * 统计已发送的
     * @return
     */
    public List<Map> sent(){
        Integer userId = (Integer) session.getAttribute("userId");
        return messageMapper.sent(userId);
    }

    /***
     * 统计草稿箱
     * @return
     */
    public List<Map> drafts(){
        Integer userId = (Integer) session.getAttribute("userId");
        return messageMapper.drafts(userId);
    }

    /**
     * 根据用户id，获得消息详情
     * @return
     */
    public ResultVo<Map>  getMessageByUserId(Integer page,Integer limit){
        Integer userId = (Integer) session.getAttribute("userId");
        PageHelper.startPage(page,limit);
        //将查询结果，封装成ResultVo
        ResultVo<Map> resultVo = new ResultVo<>();
        PageInfo<Map> pageInfo = new PageInfo<Map>(messageMapper.getMessageByUserId(userId));
        resultVo.setData(pageInfo.getList());//集合
        resultVo.setCount(pageInfo.getTotal());//总数
        return resultVo;
    }

    /**
     * 根据消息id，获得相应的消息选择人
     * @param messageId
     * @return
     */
    public List<Map> getChoosePersonByMessageId(Integer messageId){
        return messageMapper.getChoosePersonByMessageId(messageId);
    }

    /**
     * 根据用户id，添加删除的消息
     * @return
     */
    public Integer countDelmessage(){
        Integer userId = (Integer) session.getAttribute("userId");
        return messageMapper.countDelmessage(userId);
    }

    /**
     * 查询是否已读
     * @return
     */
    public Integer queryIfRead(){
        Integer userId = (Integer) session.getAttribute("userId");
        return messageMapper.queryIfRead(userId);
    }

    /**
     * 用户点击查看，修改消息发送对象为已读
     * @param messageId
     */
    public void updateMessageToUser(Integer messageId){
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println(userId);
        System.out.println(messageId);
        messageMapper.updateMessageToUser(userId,messageId);
    }
}
