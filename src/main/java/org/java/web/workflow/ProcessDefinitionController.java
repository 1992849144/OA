package org.java.web.workflow;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 流程定义的控制器类
 */
@Controller
public class ProcessDefinitionController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 部署流程定义
     * @param bpmn
     * @param png
     * @return
     * @throws IOException
     */
    @PostMapping("/deploy")
    public String deploy(MultipartFile bpmn, MultipartFile png) throws IOException {

        //获得文件名
        String bpmnName = bpmn.getOriginalFilename();
        String pngName = png.getOriginalFilename();

        //把文件转换成 inputStream
        InputStream bpmnIn = bpmn.getInputStream();
        InputStream pngIn = png.getInputStream();

        repositoryService.createDeployment().addInputStream(bpmnName,bpmnIn).addInputStream(pngName,pngIn).deploy();

        return "/ok";
    }

    /**
     * 显示流程定义
     * @param model
     * @return
     */
    @GetMapping("showProcessDefinition")
    public String showProcessDefinition(Model model){

        //创建流程定义的查询接口
        ProcessDefinitionQuery query  =repositoryService.createProcessDefinitionQuery();
        //查询所有的流程定义
        List<ProcessDefinition> list = query.list();

        model.addAttribute("list",list);

        return "/flow/showProcessDefinition";//到页面显示
    }


    /**
     * 在浏览器中显示资源的方法
     * @param deploymentId :部署id
     * @param name :资源名称
     */
    @GetMapping("showResources/{deploymentId}/{name}")
    public void showResources(HttpServletResponse response, @PathVariable("deploymentId") String deploymentId, @PathVariable("name") String name) throws IOException {

        //根据部署id,以前资源名称，加载资源，得到的一个输入流
        InputStream in  = repositoryService.getResourceAsStream(deploymentId,name);

        //指定数据缓冲区
        byte[] b = new byte[8192];
        int len=0;

        //创建一个输出流，用于把数据，输出显示在浏览器中
        //得到输出流
        ServletOutputStream out = response.getOutputStream();

        //将输入流的信息，输出显示到浏览器
        while((len=in.read(b,0,8192))!=-1){
            out.write(b,0,len);
        }
        out.close();
        in.close();


    }


    /**
     * 删除流程实例
     * 参数：流程实例的id
     * @return
     */
    @GetMapping("delProcessInstance/{processInstanceId}")
    public String delProcessInstance(@PathVariable("processInstanceId") String processInstanceId){
        runtimeService.deleteProcessInstance(processInstanceId,"业务取消");
        return "redirect:/queryProcessInstance";
    }
}
