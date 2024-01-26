package com.atguigu.auth;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Author: jerry_jy
 * @Create: 2024年1月25日16:11:13
 * @Version: 1.0
 */

@SpringBootTest
public class ProcessTest1 {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    ///
    // 监听器分配任务
    // 部署流程定义
    @Test
    public void deployProcess02() {
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("process/jiaban02.bpmn20.xml").name("加班申请流程02").deploy();
        System.out.println("deploy.getId() = " + deploy.getId()); // ed080f00-bb41-11ed-a6f2-005056c00001
        System.out.println("deploy.getName() = " + deploy.getName()); // 加班申请流程02
    }

    @Test
    public void startProcessInstance02(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("jiaban02");
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId()); // jiaban02:1:ed150752-bb41-11ed-a6f2-005056c00001
        System.out.println("processInstance.getId() = " + processInstance.getId()); // 06eca124-bb42-11ed-9bbc-005056c00001
    }
    // 启动流程实例
    @Test
    public void startProcessInstance2() {
        Map<String, Object> map = new HashMap<>();
        // 设置任务人
        map.put("assignee1","Tim");
        map.put("assignee2","LiLei");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("jiaban02", map);

        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId()); // jiaban:1:5c60d97f-bb1d-11ed-b5c8-005056c00001

        System.out.println("processInstance.getId() = " + processInstance.getId()); // 7f720dd9-bb1d-11ed-b6e9-005056c00001
    }

    // 查询个人的代办任务--Tim
    @Test
    public void findTaskList02(){
        String assign = "Tim";
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(assign).list();
        for (Task task : list) {
            System.out.println("task.getProcessInstanceId() = " + task.getProcessInstanceId()); // 06eca124-bb42-11ed-9bbc-005056c00001
            System.out.println("任务id：" + task.getId()); // 06f071b8-bb42-11ed-9bbc-005056c00001
            System.out.println("任务负责人：" + task.getAssignee()); // Tim
            System.out.println("任务名称：" + task.getName()); // 经理审批
        }
    }

    ///
    // uel-method
    // 部署流程定义
    @Test
    public void deployProcess01() {
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("process/jiaban01.bpmn20.xml").name("加班申请流程1").deploy();
        System.out.println("deploy.getId() = " + deploy.getId()); // 8c4ac05e-bb20-11ed-8d65-005056c00001
        System.out.println("deploy.getName() = " + deploy.getName()); // 加班申请流程01
    }

    @Test
    public void startProcessInstance01(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("jiaban01");
        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId()); // jiaban01:1:8c56a740-bb20-11ed-8d65-005056c00001
        System.out.println("processInstance.getId() = " + processInstance.getId()); // abb9c7c4-bb20-11ed-b608-005056c00001
    }

    // 查询个人的代办任务--LiLei
    @Test
    public void findTaskList01(){
        String assign = "LiLei";
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(assign).list();
        for (Task task : list) {
            System.out.println("task.getProcessInstanceId() = " + task.getProcessInstanceId()); // abb9c7c4-bb20-11ed-b608-005056c00001
            System.out.println("任务id：" + task.getId()); // abbd4a38-bb20-11ed-b608-005056c00001
            System.out.println("任务负责人：" + task.getAssignee()); // LiLei
            System.out.println("任务名称：" + task.getName()); // 经理审批
        }
    }

    ///
    // uel-value
    // 部署流程定义
    @Test
    public void deployProcess1() {
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("process/jiaban.bpmn20.xml").name("加班申请流程").deploy();
        System.out.println("deploy.getId() = " + deploy.getId()); // 5c5519ad-bb1d-11ed-b5c8-005056c00001
        System.out.println("deploy.getName() = " + deploy.getName()); // 加班申请流程
    }

    // 启动流程实例
    @Test
    public void startProcessInstance() {
        Map<String, Object> map = new HashMap<>();
        // 设置任务人
        map.put("assignee1","tom");
        map.put("assignee2","jerry");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("jiaban01", map);

        System.out.println("processInstance.getProcessDefinitionId() = " + processInstance.getProcessDefinitionId()); // jiaban:1:5c60d97f-bb1d-11ed-b5c8-005056c00001

        System.out.println("processInstance.getId() = " + processInstance.getId()); // 7f720dd9-bb1d-11ed-b6e9-005056c00001
    }

    // 查询个人的代办任务--tom
    @Test
    public void findTaskList(){
        String assign = "tom";
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(assign).list();
        for (Task task : list) {
            System.out.println("task.getProcessInstanceId() = " + task.getProcessInstanceId()); //7f720dd9-bb1d-11ed-b6e9-005056c00001
            System.out.println("任务id：" + task.getId()); // 7f759051-bb1d-11ed-b6e9-005056c00001
            System.out.println("任务负责人：" + task.getAssignee()); // tom
            System.out.println("任务名称：" + task.getName()); // 经理审批
        }
    }
    @Test
    public void deployProcess03() {
        // 流程部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/jiaban03.bpmn20.xml")
                .name("加班申请流程003")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startUpProcess03() {
        //创建流程实例,我们需要知道流程定义的key
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("jiaban03");
        //输出实例的相关信息
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id：" + processInstance.getId());
    }


}
