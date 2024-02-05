package com.atguigu.process.service;


import com.atguigu.auth.model.process.Process2;
import com.atguigu.auth.vo.process.ApprovalVo;
import com.atguigu.auth.vo.process.ProcessFormVo;
import com.atguigu.auth.vo.process.ProcessQueryVo;
import com.atguigu.auth.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 审批类型 服务类
 * </p>
 *
 * @author smg
 * @since 2024-01-31
 */
public interface OaProcessService extends IService<Process2> {
    //审批管理列表
    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam,  ProcessQueryVo processQueryVo);
    //部署流程定义

    void deployByZip(String deployPath);


    //启动流程
    void startUp(ProcessFormVo processFormVo);


    IPage<ProcessVo> findfindPending(Page<Process> pageParam);

    Object show(Long id);

    void approve(ApprovalVo approvalVo);

    IPage<ProcessVo> findProcessed(Page<Process> pageParam);

    IPage<ProcessVo> findStarted(Page<ProcessVo> pageParam);
}
