package org.java.web.mail;

import org.java.entity.Branchinfo;
import org.java.entity.Message;
import org.java.entity.Messagetype;
import org.java.entity.SysUser;
import org.java.service.*;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 邮件
 */
@Controller
@RequestMapping("mail")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MessagetypeService messagetypeService;

    @Autowired
    private DepInfoService depInfoService;

    @Autowired
    private BranchinfoService branchinfoService;

    /**
     * 显示所有邮件
     * @param page
     * @param limit
     * @param session
     * @return
     */
    @GetMapping("init")
    @ResponseBody
    public ResultVo<Map> showMessage(int page, Integer limit,String times,String startTime,String endTime, HttpSession session){
        if (times==null){
            times=(String)session.getAttribute("times");
        }
        if (startTime==null){
            startTime=(String)session.getAttribute("startTime");
        }
        if (endTime==null){
            endTime=(String)session.getAttribute("endTime");
        }
        String name = session.getAttribute("name").toString();
        String username = session.getAttribute("username").toString();
        Map user = sysUserService.login(username);
        Integer fromUserId=Integer.parseInt(user.get("id").toString());
        session.setAttribute("startTime",startTime);
        session.setAttribute("endTime",endTime);
        session.setAttribute("times",times);
        return  messageService.getAllMessage(page,limit,name,fromUserId,times,startTime,endTime);
    }

    /**
     * 删除邮件
     * @param messageId
     */
    @PostMapping("delMessage")
    @ResponseBody
    public void delMessage(Integer messageId){
        messageService.delMessage(messageId);
    }

    /**
     * 根据邮件id,修改为已发布的邮件，只修改IfPublish这个字段
     * @param messageId
     * @return
     */
    @GetMapping("updateIfPublish")
    public String updateIfPublish(Integer messageId){
        messageService.updateIfPublish(messageId);
        return "/mail/showMail";
    }

    /**
     * 添加邮件
     * @param map
     */
    @PostMapping("addMessage")
    @ResponseBody
    @Transactional
    public void addMessage(@RequestParam Map map){
        messageService.addMessage(map);
    }

    /**
     * 消息修改时回显
     * @param messageId
     * @return
     */
    @GetMapping("showUpdateMessage")
    public String showUpdateMessage(Integer messageId, Model model){
        Message message = messageService.showUpdateMessage(messageId);//获得消息详情
        List<Messagetype> messagetype = messagetypeService.getAllMessagetype();//获得所有消息类型
        List<Map> dep = depInfoService.getDep();//获得所有部门
        List<Branchinfo> branchInfo = branchinfoService.getAllBranchinfo();//获得所有机构
        model.addAttribute("message",message);
        model.addAttribute("messageType",messagetype);
        return "/mail/updateMessage";
    }

    /**
     * 修改发送给所有人的消息
     * @param map
     */
    @PostMapping("updateMessage")
    @ResponseBody
    @Transactional
    public void updateMessage(@RequestParam Map map){
        messageService.updateMessage(map);
    }

    /**
     * 统计邮箱
     * @return
     */
    @GetMapping("statisticsEmil")
    public String statisticsEmil(Model model){
        List<Map> countReceiveFiles = messageService.statisticsEmil();//统计收件箱的数量
        List<Map> sent = messageService.sent();//统计已发送的数量
        List<Map> drafts = messageService.drafts();//统计草稿箱的数量
        Integer count = messageService.countDelmessage();//统计删除信息
        Integer integer = messageService.queryIfRead();//统计末读的数量
        model.addAttribute("countReceiveFiles",countReceiveFiles);
        model.addAttribute("sent",sent);
        model.addAttribute("drafts",drafts);
        model.addAttribute("count",count);
        model.addAttribute("integer",integer);
        return "/mail/letterBox";
    }

    /**
     * 根据用户，获得相应的收件箱
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("getMessageByUserId")
    @ResponseBody
    public ResultVo<Map> getMessageByUserId(int page, Integer limit){
        return messageService.getMessageByUserId(page,limit);
    }

    /**
     * 根据消息id，获得相应的消息选择人
     * @param messageId
     * @return
     */
    @PostMapping("getChoosePersonByMessageId")
    @ResponseBody
    public List<Map> getChoosePersonByMessageId(Integer messageId){
        return messageService.getChoosePersonByMessageId(messageId);
    }

    /**
     * 用户点击查看，修改消息发送对象为已读
     */
    @PostMapping("updateMessageToUser")
    @ResponseBody
    public void updateMessageToUser(Integer messageId){
        messageService.updateMessageToUser(messageId);
    }
}
