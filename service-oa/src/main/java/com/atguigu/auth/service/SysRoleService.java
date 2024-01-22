package com.atguigu.auth.service;

import com.atguigu.auth.model.system.SysRole;
import com.atguigu.auth.model.system.SysUserRole;
import com.atguigu.auth.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SysRoleService  extends IService<SysRole> {
    Map<String, Object> findRoleDataByUserId(Long userId);

    void doAssign(AssginRoleVo assginRoleVo);

}
