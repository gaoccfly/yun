package com.atguigu.process.service;


import com.atguigu.auth.model.process.ProcessRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 审批记录 服务类
 * </p>
 *
 * @author smg
 * @since 2024-02-02
 */
public interface OaProcessRecordService extends IService<ProcessRecord> {
    void  record(Long processId,Integer status, String description );
}
