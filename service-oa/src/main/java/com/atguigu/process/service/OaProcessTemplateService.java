package com.atguigu.process.service;


import com.atguigu.auth.model.process.ProcessTemplate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 审批模板 服务类
 * </p>
 *
 * @author smg
 * @since 2024-01-26
 */
public interface OaProcessTemplateService extends IService<ProcessTemplate> {
    //分页查询审批模版，把审批类型对应名称查询
    IPage<ProcessTemplate> selectPageProcessTempate(Page<ProcessTemplate> pageParam);

    void publish(Long id);
}
