package com.atguigu.auth.service;

import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author roy
 * @since 2023-03-09
 */
public interface SysUserService extends IService<SysUser> {

    //用户条件分页查询
    IPage<SysUser> index(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);
}
