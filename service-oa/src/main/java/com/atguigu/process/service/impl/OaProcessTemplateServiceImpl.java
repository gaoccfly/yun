package com.atguigu.process.service.impl;


import com.atguigu.auth.model.process.ProcessTemplate;
import com.atguigu.auth.model.process.ProcessType;
import com.atguigu.process.mapper.OaProcessTemplateMapper;
import com.atguigu.process.service.OaProcessService;
import com.atguigu.process.service.OaProcessTemplateService;
import com.atguigu.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 审批模板 服务实现类
 * </p>
 *
 * @author smg
 * @since 2024-01-26
 */
@Service
public class OaProcessTemplateServiceImpl extends ServiceImpl<OaProcessTemplateMapper, ProcessTemplate> implements OaProcessTemplateService {
    @Autowired
    private OaProcessTypeService processTypeService;
    @Autowired
    private OaProcessService processService;
//    @Autowired
//    private OaProcessTemplateMapper processTemplateMapper;
    //1.分页查询审批模版，把审批类型对应名称查询
//    TypeService;    //2.第一步分页查询返回分页数据，从分页数据获取list集合
//    3.遍历list集合，得到每个对象的审批类型id
//    4.根据审批类型id，查询获取对应名称
//    完成最终封装processTypeName
    @Override
    public IPage<ProcessTemplate> selectPageProcessTempate(Page<ProcessTemplate> pageParam) {
        Page<ProcessTemplate> processTemplatePage = baseMapper.selectPage(pageParam, null);
        //2.第一步分页查询返回分页数据，从分页数据获取list集合
        List<ProcessTemplate> processTemplateList = processTemplatePage.getRecords();
        //    3.遍历list集合，得到每个对象的审批类型id
        for (ProcessTemplate processTemplate:processTemplateList)
        {
            Long processTypeId=processTemplate.getProcessTypeId();
            LambdaQueryWrapper<ProcessType> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(ProcessType::getId,processTypeId);
            ProcessType processType=processTypeService.getOne(wrapper);
            if (processType==null){
                continue;
            }

//            完成最终封装processTypeName
        processTemplate.setProcessTypeName(processType.getName());
        }
        return processTemplatePage;
    }

    @Override
    public void publish(Long id) {
        ProcessTemplate processTemplate = this.getById(id);
        processTemplate.setStatus(1);
        baseMapper.updateById(processTemplate);

        //TODO 部署流程定义，后续完善
        //优先发布在线流程设计
        if(!StringUtils.isEmpty(processTemplate.getProcessDefinitionPath())) {
            processService.deployByZip(processTemplate.getProcessDefinitionPath());
        }
    }

}
