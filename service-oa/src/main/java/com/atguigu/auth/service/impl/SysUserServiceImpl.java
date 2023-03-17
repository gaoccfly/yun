package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysUserMapper;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author roy
 * @since 2023-03-09
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {



    //用户条件分页查询
    @Override
    public IPage<SysUser> index(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();

        if(!StringUtils.isEmpty(username)){
            wrapper.like(SysUser::getUsername, username);
        }

        if(!StringUtils.isEmpty(createTimeBegin)){
            wrapper.ge(SysUser::getCreateTime, createTimeBegin);
        }

        if(!StringUtils.isEmpty(createTimeEnd)){
            wrapper.le(SysUser::getCreateTime, createTimeEnd);
        }


        return baseMapper.selectPage(pageParam, wrapper);
    }

    //更新状态
    @Override
    public void updateStatus(Long id, Integer status) {
        //根据userId查询用户
        SysUser sysUser = baseMapper.selectById(id);

        //设置修改状态
        sysUser.setStatus(status);

        //调用方法进行修改
        baseMapper.updateById(sysUser);
    }


}
