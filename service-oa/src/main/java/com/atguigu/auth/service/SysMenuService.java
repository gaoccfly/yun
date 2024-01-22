package com.atguigu.auth.service;


import com.atguigu.auth.model.system.SysMenu;
import com.atguigu.auth.vo.system.AssginMenuVo;
import com.atguigu.auth.vo.system.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author smg
 * @since 2024-01-21
 */
public interface SysMenuService extends IService<SysMenu> {
    //菜单列表
    List<SysMenu> findNodes();

    //删除菜单
    void removeMenuById(Long id);

    //查询所有菜单和角色分配的菜单
    List<SysMenu> findMenuByRoleId(Long roleId);

    //角色分配菜单
    void doAssign(AssginMenuVo assginMenuVo);

    // 根据 用户id 获取用户可以操作的菜单列表
    List<RouterVo> findUserMenuListByUserId(Long userId);

    // 根据 用户id 获取用户可以操作的按钮列表
    List<String> findUserPermsByUserId(Long userId);
}
