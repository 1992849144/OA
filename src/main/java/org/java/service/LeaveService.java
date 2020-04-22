package org.java.service;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.java.dao.LeaveMapper;
import org.java.util.ResourcesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeaveService {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private TaskService taskService;

    //获得上传的文件地址
    @Value("${file.processDefinitionKey}")
    private String processDefinitionKey;

    /**
     * 创建请假单,需要完成两个任务
     * 1、启动一个流程实例
     * 2、向pur_order表中，增加一条业务数据（采购记录）
     * @param map
     */
    public void createLeave(Map map){
        //任务1：启动流程实例（启动流程时，要设置业务主键，用于关联到业务表的数据）

        //在启动流程实例之前，获得当前用户名，通过identityService，把它设置为任务的发起人，赋值给变量  startUser
        //从map中，取得userId
        String userId = map.get("userId").toString();
        identityService.setAuthenticatedUserId(userId);//设置任务发起人

        //通过uuid生成业务主键
        String businessKey = UUID.randomUUID().toString();

        //设置流程定义的key,根据key启动实例
//        String processDefinitionKey = ResourcesUtil.getValue("/process","processDefinitionKey");

        Integer days=Integer.parseInt(map.get("days").toString());
        System.out.println(days);
        Map variables=new HashMap();
        variables.put("days",days);

//        启动流程实例，同时设置业务主键
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,variables);

        /****************以上代码，可以产生流程实例********************************/

        //任务2：向采购单表，增加一条采购记录
        //向map添加id,也就是业务主键对应的值
        map.put("id",businessKey);
        //设置采购单的创建时间
        map.put("createTime",new Date());
        //设置该业务数据，对应的流程实例id
        map.put("processInstanceId",instance.getProcessInstanceId());

        leaveMapper.createLeave(map);
    }

    public List<Map> queryPersonTask(String userId) {
        //创建任务的查询接口
        TaskQuery query = taskService.createTaskQuery();

        //指定要查询哪一个用户的任务
        query.taskAssignee(userId.toString());

        //查询任务，得到任务的集合  List<Task>,此集合中，仅包含工作流的信息，不包含业务数据
        List<Task> taskList = query.list();



        //创建一个集合List<Map>用于封装工作流的信息，以及对应的业务数据
        List<Map> list = new ArrayList<>();

        for(Task t:taskList){
            //得到该任务对应的流程实例的id
            String processInstanceId = t.getProcessInstanceId();

            //根据流程实例Id，查询业务表，获得对应的业务数据 (返回的map中，只要业务数据，不包含工作流的信息)
            Map map = leaveMapper.findByProcessInstanceId(processInstanceId);

            //把task中的工作流信息，也封装到map中返回
            map.put("taskId",t.getId());//任务ID
            map.put("taskName",t.getName());//任务名称
            map.put("assignee",t.getAssignee());//任务的处理人
            map.put("taskCreateTime",t.getCreateTime());//任务的开始时间
            map.put("taskDef",t.getTaskDefinitionKey());//任务执行到流程实例中的哪一个阶段 createOrder,firstAudit,secondAudit,thirdAudit

            list.add(map);
        }

        return list;
    }

    public List<Map> queryProcessInstance() {
        //创建查询接口
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();

        //查询，得到所有的流程实例(不包含业务数据)
        List<ProcessInstance> instanceList = query.list();

        //创建一个集合List<Map>用于封装工作流的信息，以及对应的业务数据
        List<Map> list = new ArrayList<>();

        for(ProcessInstance t:instanceList){
            //得到该任务对应的流程实例的id
            String processInstanceId = t.getProcessInstanceId();

            //根据流程实例Id，查询业务表，获得对应的业务数据 (返回的map中，只要业务数据，不包含工作流的信息)
            Map map = leaveMapper.findByProcessInstanceId(processInstanceId);

            //封装流程实例的信息
            map.put("processInstanceId",processInstanceId);
            map.put("activityId",t.getActivityId());//任务执行到哪一个阶段

            list.add(map);
        }
        return list;
    }

    public List<Map> queryGroupTask(String userId) {
        //创建任务查询接口
        TaskQuery query = taskService.createTaskQuery();
        //指定要查询哪一个用户的任务
        query.taskCandidateUser(userId);//查询候选人的组任务，是通过candiateUser
        //查询任务，得到任务的集合  List<Task>,此集合中，仅包含工作流的信息，不包含业务数据
        List<Task> taskList  = query.list();

        //创建一个集合List<Map>用于封装工作流的信息，以及对应的业务数据
        List<Map> list = new ArrayList<>();

        for(Task t:taskList){
            //得到该任务对应的流程实例的id
            String processInstanceId = t.getProcessInstanceId();

            //根据流程实例Id，查询业务表，获得对应的业务数据 (返回的map中，只要业务数据，不包含工作流的信息)
            Map map = leaveMapper.findByProcessInstanceId(processInstanceId);

            //把task中的工作流信息，也封装到map中返回
            map.put("taskId",t.getId());//任务ID
            map.put("taskName",t.getName());//任务名称
            map.put("assignee",t.getAssignee());//任务的处理人
            map.put("taskCreateTime",t.getCreateTime());//任务的开始时间
            map.put("taskDef",t.getTaskDefinitionKey());//任务执行到流程实例中的哪一个阶段 createOrder,firstAudit,secondAudit,thirdAudit

            list.add(map);
        }
        return list;
    }

    /**
     * 根据采购单id，得到采购详情
     * @param orderId
     * @return
     */
    public Map findOrderById(String orderId) {
        return  leaveMapper.findOrderById(orderId);
    }

    /**
     * 提交审核意见
     * 今天：只考核全部审核通过的情况
     * @param map
     * 该方法中，需要完成两个任务:
     *
     * 1、向审核意见表中添加一条审核记录
     *
     * 2、完成任务，让任务向推进(今天只考虑同意的情况 )
     */
    public void submitAudit(Map map) {
        //生成id
        map.put("id",UUID.randomUUID().toString());

        //指定提交意见的时间
        map.put("createtime",new Date());

        leaveMapper.submitAudit(map);

        String taskDef=map.get("taskDef").toString();
        Integer status=Integer.parseInt(map.get("status").toString());

        Map variables=new HashMap();
        variables.put(taskDef,status);

        //获得任务id
        String taskId=(String)map.get("taskId");

        //提交任务id，让流程继续往下走 (只考虑通过)
        taskService.complete(taskId,variables);
    }

    //财务结算
    public void settlement(String taskId) {
        taskService.complete(taskId);
    }

    //采购入库
    public void storage(String taskId) {
        taskService.complete(taskId);
    }
}
