package com.atguigu.auth.service;

import com.atguigu.auth.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author
 * @since 2023-03-15
 */

public interface SysUserService extends IService<SysUser> {

    void updateStatus(Long id, Integer status);

    SysUser getByUsername(String username);

    Map<String, Object> getCurrentUser();
}