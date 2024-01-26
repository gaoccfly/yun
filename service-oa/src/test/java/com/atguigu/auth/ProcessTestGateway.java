package com.atguigu.auth;


import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ProcessTestGateway <br>
 * Package: com.jerry.auth.activiti <br>
 * Description:
 *
 * @Author: jerry_jy
 * @Create: 2023-03-05 19:52
 * @Version: 1.0
 */
@SpringBootTest
public class ProcessTestGateway {

    @Autowired
    private RepositoryService repositoryService;

    //注入RuntimeService
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    //1 部署流程定义
    @Test
    public void deployProcess() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("process/qingjia002.bpmn20.xml")
                .name("请假申请流程002")
                .deploy();
        System.out.println(deployment.getId()); // af9242f0-bb4c-11ed-85bf-005056c00001
        System.out.println(deployment.getName()); // 请假申请流程002
    }

    //2 启动流程实例
    @Test
    public void startProcessInstance() {
        Map<String, Object> map = new HashMap<>();
        //设置请假天数
//        map.put("day", "2");

        ProcessInstance processInstance =
//              runtimeService.startProcessInstanceByKey("qingjia002", map);
                runtimeService.startProcessInstanceByKey("qingjia002");
//                runtimeService.startProcessInstanceByKey("qingjia003");
        System.out.println(processInstance.getProcessDefinitionId()); // qingjia002:1:afac0c82-bb4c-11ed-85bf-005056c00001
        System.out.println(processInstance.getId()); // 90d46e2c-bb4d-11ed-9b92-005056c00001
    }

    //3 查询个人的代办任务--zhao6
    @Test
    public void findTaskList() {

//        String assign = "zhao6";
//        String assign = "gousheng";
//        String assign = "xiaocui";
//        String assign = "wang5";
//        String assign = "gouwa";
//        String assign = "xiaocui";
        String assign =  "xiaoli";
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(assign).list();
        for (Task task : list) {
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }

    //完成任务
    @Test
    public void completeTask() {
        Task task = taskService.createTaskQuery()
//                .taskAssignee("zhao6")  //要查询的负责人
//                .taskAssignee("xiaocui")  //要查询的负责人
//                .taskAssignee("gousheng")
                .taskAssignee("wanwu")
//                .taskAssignee("gouwa")
                .singleResult();//返回一条

        //完成任务,参数：任务id
        taskService.complete(task.getId());
    }
}
