package com.atguigu.process.service.impl;

import com.atguigu.auth.model.process.ProcessRecord;
import com.atguigu.auth.model.system.SysUser;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.process.mapper.OaProcessRecordMapper;
import com.atguigu.process.service.OaProcessRecordService;
import com.atguigu.security.custom.LoginUserInfoHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批记录 服务实现类
 * </p>
 *
 * @author smg
 * @since 2024-02-02
 */
@Service
public class OaProcessRecordServiceImpl extends ServiceImpl<OaProcessRecordMapper, ProcessRecord> implements OaProcessRecordService {
private  SysUserService sysUserService;
    @Override
    public void record(Long processId, Integer status, String description) {
        Long userId= LoginUserInfoHelper.getUserId();
        SysUser systUser=sysUserService.getById(userId);
        ProcessRecord processRecord=new ProcessRecord();
        processRecord.setProcessId(processId);
        processRecord.setStatus(status);
        processRecord.setDescription(description);
        processRecord.setOperateUser(systUser.getName());
        processRecord.setOperateUserId(userId);
        baseMapper.insert(processRecord);
    }
}
