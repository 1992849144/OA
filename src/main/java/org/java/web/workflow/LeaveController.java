package org.java.web.workflow;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.java.entity.Util;
import org.java.service.LeaveService;
import org.java.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LeaveController {

    @Autowired
    private UtilService utilService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    /**
     * 创建请假单
     * Map集合，包含从页面中，提交过来的 name,price,content
     * 我们需要从session，获得userId,存储到map中，把这一些信息记录在采购记录表中
     * @param map
     * @return
     */
    @PostMapping("createLeave")
    @Transactional
    public String createLeave(@RequestParam Map map, HttpSession session){
        //从session中，获得userId
        String userId = (String) session.getAttribute("username");
        //存储在map中
        map.put("userId",userId);
        leaveService.createLeave(map);
        return "/accomplish";
    }

    /**
     * 查询个人待办理任务
     * 需要从session中，获得userId,根据userId查询任务
     * @return
     */
    @GetMapping("queryProcessInstance")
    public String queryProcessInstance(HttpSession session, Model model){

        //得到所有的流程实例
        List<Map> list = leaveService.queryProcessInstance();

        model.addAttribute("list",list);

        return "/leave/showInstance";
    }


    /**
     * 准备显示流程图，任务运行到哪一个阶段，就把这一个阶段，用于红色边框选中
     * @return
     */
    @RequestMapping("/showProcessMap/{processInstanceId}")
    public String showProcessMap(@PathVariable("processInstanceId") String processInstanceId,Model model){

        //创建流程实例的查询接口
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();

        //设置要查询的流程实例Id
        query.processInstanceId(processInstanceId);

        //查询获得结果（注意，由于根据流程实例I查询，只能返回一个唯一的流程实例，而不是集合）
        ProcessInstance instance = query.singleResult();

        //根据流程实例中的流程定义ID,通过RepositoryService，获得流程定义的实体  ProcessDefinitionEntity
        //流程定义的实体：指定的流程图中，所有任务节点的集合
        ProcessDefinitionEntity entity =(ProcessDefinitionEntity) repositoryService
                .getProcessDefinition(instance.getProcessDefinitionId());


        //通过instance(流程实例)，获得当前任务，执行到了哪一个阶段： createOrder,firstAudit,secondAudit,thirdAudit
        String activityId = instance.getActivityId();

        //根据任务阶段的id，从流程定义实体（任务节点的的集合）获得某一个任务节点
        ActivityImpl activity = entity.findActivity(activityId);

        //获得每一个任务节点的width,height,x,y
        int width = activity.getWidth();
        int height = activity.getHeight();
        int x = activity.getX();
        int y = activity.getY();

        model.addAttribute("width",width);
        model.addAttribute("height",height);
        model.addAttribute("x",x);
        model.addAttribute("y",y);

        //存储当前流程实例的部署id,以及png图片的名称
        model.addAttribute("deploymentId",entity.getDeploymentId());
        model.addAttribute("png",entity.getDiagramResourceName());

        //跳转到显示流程图的页面
        return "/leave/showActiveMap";

    }


    /**
     * 删除流程定义
     * @param deploymentId
     * @return
     */
    @GetMapping("/delProcessDefinition/{deploymentId}")
    public String delProcessDefinition(@PathVariable("deploymentId") String deploymentId){
        repositoryService.deleteDeployment(deploymentId,true);//级联删除
        return "redirect:/showProcessDefinition";
    }

    /**
     * 显示流程实例已经执行的任务
     * @return
     */
    @GetMapping("showHistory/{processInstanceId}")
    public String showHistory(@PathVariable("processInstanceId") String processInstanceId,Model model){

        //创建历史任务的查询接口
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();

        //指定要查询哪一个流程实例的历史任务
        query.processInstanceId(processInstanceId);

        //查询
        List<HistoricTaskInstance> list = query.list();


        model.addAttribute("list",list);

        return "/leave/showHistory";
    }

    /**
     * 查询个人待办理任务
     * 需要从session中，获得userId,根据userId查询任务
     * @return
     */
    @GetMapping("queryPersonTask")
    public String queryPersonTask(HttpSession session, Model model){

        //从session中，得到用户名
        String userId = (String) session.getAttribute("username");

        //根据userId查询任务列表
        List<Map> list = leaveService.queryPersonTask(userId);

        model.addAttribute("list",list);

        return "/leave/showPersonTask";
    }

    /**
     * 完成任务，让任务向后推进1步，进行下一个阶段
     * 我们现在只是临时完成一下任务，如果要审核，是不能这样简单的完成任务
     * 此方法中的方法，后面是需要修改的
     * @return
     */
    @GetMapping("/submitOrder/{taskId}")
    public String submitOrder(@PathVariable("taskId") String taskId){
        //完成任务
        taskService.complete(taskId);

        //重新加载当前用户的任务列表
        return "redirect:/queryPersonTask";
    }


    /**
     * 显示当前用户，作为候选人，可以参与的组任务
     * 需要的参数:userId
     *
     * userId从session中获取
     * @return
     */
    @RequestMapping("queryGroupTask")
    public String queryGroupTask(HttpSession session,Model model){
        //获得用户的userId
        String userId = session.getAttribute("username").toString();

        //根据userId，查询当前用户，可以参与办理的组任务
        List<Map> list = leaveService.queryGroupTask(userId);

        //存储可以拾取的组任务
        model.addAttribute("list",list);

        //进入拾取任务页面
        return "/leave/claimTask";
    }


    /**
     * 拾取任务，将组任务，变为个人任务
     * 需要的参数： taskId,userId
     * taskService.claim(taskId,userId);
     * @return
     */
    @RequestMapping("claimTask/{taskId}")
    public String claimTask(@PathVariable("taskId") String taskId,HttpSession session){
        //获得用户的userId
        String userId = session.getAttribute("username").toString();



        //拾取任务
        taskService.claim(taskId,userId);

        return "redirect:/queryGroupTask";
    }

    /**
     * 审核采购单之前，先要把采购单的详情，显示在页面中，供审核者判断
     * 需要接收下列参数
     *  1、采购单id(orderId)-------------根据采购单的id，查询出对应的业务数据
     *  2、采购单的阶段名称 taskDef，这样才知道是哪一个阶段审核： firstAudit,secondAudit,thirdAudit
     *  3、任务id  taskId:要完成审核任务，必须要任务id才能完成
     * @return
     */
    @GetMapping("showOrder/{orderId}/{taskId}/{taskDef}")
    public String showOrder(@PathVariable("orderId") String orderId,@PathVariable("taskId") String taskId,@PathVariable("taskDef") String taskDef,Model model){
        //根据采购单的id,查询出采购单的详细信息
        Map order = leaveService.findOrderById(orderId);

        //存储采购单数据
        model.addAttribute("order",order);

        //存储任务阶段id,任务id,采购单的id
        model.addAttribute("taskId",taskId);
        model.addAttribute("taskDef",taskDef);
        model.addAttribute("orderId",orderId);

        //跳转到采购单审核页面
        return "/leave/orderAudit";
    }

    /**
     * 提交审核意见，并且完成任务，让任务向后推进1步，进行下一个阶段
     * @param map
     * @param session
     * @return
     */
    @PostMapping("submitAudit")
    public String submitAudit(@RequestParam Map map,HttpSession session){
        String userId= (String) session.getAttribute("username");

        map.put("userId",userId);
        leaveService.submitAudit(map);
        return "redirect:/queryPersonTask";
    }

    /**
     * 财务结算
     * @param taskId
     * @return
     */
    @GetMapping("settlement/{taskId}")
    public String settlement(@PathVariable("taskId") String taskId){

        leaveService.settlement(taskId);

        return "redirect:/queryPersonTask";
    }

    /**
     * 采购入库
     * @param taskId
     * @return
     */
    @GetMapping("storage/{taskId}")
    public String storage(@PathVariable("taskId") String taskId){
        leaveService.storage(taskId);
        return "redirect:/queryPersonTask";
    }


    /**
     * 创建请假单的树
     * @return
     */
    @PostMapping("showLeave")
    @ResponseBody
    public List<Util>  showLeave(){
        //获取所有的菜单(包括子菜单和父级菜单)
        List<Util> list = utilService.getAllDepartInfo();

        //创建一个集合用于保存所有的主菜单
        List<Util> rootMeun=new ArrayList<>();

        //遍历所有菜单集合,如果是主菜单的话直接放入rootMeun集合
        for (Util info:list){
            //判断为0是因为我的主菜单标识是0
            rootMeun.add(info);
        }

        //这个是遍历所有主菜单,分别获取所有主菜单的所有子菜单
        for (Util info:rootMeun){
            //获取所有子菜单 递归
            List<Map> childrenList=utilService.getDepartInfoByBranchId(info.getId());
            List<Util> departInfo=new ArrayList<>();
            for (Map m:childrenList){
                Util departInfo1=new Util();
                departInfo1.setId(Integer.parseInt(m.get("departId").toString()));
                departInfo1.setTitle(m.get("departName").toString());
                departInfo1.setPrincipalUser(Integer.parseInt(m.get("principalUser").toString()));
                departInfo.add(departInfo1);
            }
            info.setChildren(departInfo);
        }
        return rootMeun;
    }
}
