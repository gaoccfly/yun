package com.atguigu.process.service.impl;



import com.atguigu.process.mapper.OaProcessMapper;
import com.atguigu.process.service.OaProcessService;
import com.atguigu.auth.vo.process.ProcessQueryVo;
import com.atguigu.auth.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批类型 服务实现类
 * </p>
 *
 * @author smg
 * @since 2024-01-31
 */
@Service
 public class OaProcessServiceImpl extends ServiceImpl<OaProcessMapper,Process> implements OaProcessService {

//    @Autowired
//    private OaProcessMapper processMapper;
    //审批管理列表
@Override
public IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo) {
    IPage<ProcessVo> pageModel = baseMapper.selectPage(pageParam,processQueryVo);
    return pageModel;
}
}

