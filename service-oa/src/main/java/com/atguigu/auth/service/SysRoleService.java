package com.atguigu.auth.service;

import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {

    //条件分页查询
    IPage<SysRole> pageQueryRole(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);

    //查询所有角色和当前用户所属角色
    Map<String, Object> findRoleDataByUserId(Long userId);

    //为用户分配角色
    void doAssign(AssginRoleVo assginRoleVo);
}
