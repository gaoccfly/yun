package com.atguigu.auth.service.impl;


import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.mapper.SysRoleMenuMapper;
import com.atguigu.auth.model.system.SysMenu;
import com.atguigu.auth.model.system.SysRoleMenu;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysRoleMenuService;
import com.atguigu.auth.utils.MenuHelper;
import com.atguigu.auth.vo.system.AssginMenuVo;
import com.atguigu.auth.vo.system.MetaVo;
import com.atguigu.auth.vo.system.RouterVo;
import com.atguigu.common.config.exception.GuiguException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author smg
 * @since 2024-01-21
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private  SysMenuMapper sysMenuMapper;

    //菜单列表
    @Override
    public List<SysMenu> findNodes() {

        //1.查询所有菜单数据
        List<SysMenu> sysMenuList = baseMapper.selectList(null);

        //2.构建树形结构
        List<SysMenu> resultList = MenuHelper.buildTree(sysMenuList);

        return resultList;
    }

    //删除菜单
    @Override
    public void removeMenuById(Long id) {
        //判断当前菜单是否有下一层菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysMenu::getParentId, id);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GuiguException(201, "菜单不能删除");
        }
        baseMapper.deleteById(id);
    }

    //查询所有菜单和角色分配的菜单
    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        //1.查询所有菜单- 添加条件 status=1
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getStatus, 1);
        List<SysMenu> allSysMenuList = baseMapper.selectList(wrapper);

        //2.根据角色id roleId查询 角色菜单关系表里面, 角色id对应所有的菜单id
        LambdaQueryWrapper<SysRoleMenu> wrapperSysRoleMenu = new LambdaQueryWrapper<>();
        wrapperSysRoleMenu.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(wrapperSysRoleMenu);

        //3.根据获取菜单id, 获取对应菜单对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(item -> item.getMenuId()).collect(Collectors.toList());
        //3.1.拿着菜单id 和所有菜单集合里面的id进行比较, 如果相同封装
        allSysMenuList.forEach(menu -> {
            if(menuIdList.contains(menu.getId())){
                menu.setSelect(true);
            }else{
                menu.setSelect(false);
            }
        });

        //4.返回规定树形格式菜单列表
        List<SysMenu> sysMenuList = MenuHelper.buildTree(allSysMenuList);
        return sysMenuList;
    }

    //角色分配菜单
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //1.根据角色id 删除菜单角色表 分配的数据
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, assginMenuVo.getRoleId());
        sysRoleMenuService.remove(wrapper);

        //2.从参数里面获取角色新分配的菜单id列表, 进行遍历, 把每个id数据添加菜单角色表
        for (Long menuId : assginMenuVo.getMenuIdList()) {
            if(StringUtils.isEmpty(menuId)){
                continue;
            }
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenuService.save(sysRoleMenu);
        }
    }

    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userId) {
        List<SysMenu> sysMenusList = null;
        // 1、判断当前用户是否是管理员       userId=1 是管理员
        // 1.1、 如果是管理员，查询所有菜单列表
        if (userId.longValue() == 1) {
            // 查询所有菜单列表
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysMenu::getStatus, 1);
            queryWrapper.orderByAsc(SysMenu::getSortValue);
            sysMenusList = baseMapper.selectList(queryWrapper);
        } else {
            // 1.2、如果不是管理员，根据 userId 查询可以操作菜单列表
            // 多表关联查询:sys_role、sys_role_mexnu、sys_menu
            sysMenusList = baseMapper.findMenuListByUserId(userId);
        }


        // 2、把查询出来的数据列表， 构建成框架要求的路由结构
        // 先构建树形结构
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenusList);
        // 构建框架要求的路由结构
        List<RouterVo> routerList = this.buildRouter(sysMenuTreeList);

        return routerList;
    }

    private List<RouterVo> buildRouter(List<SysMenu>  menus) {
        // 创建 list 集合，存值最终数据
        List<RouterVo> routers = new ArrayList<>();
        // menus 遍历
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
            // 下一层数据
            List<SysMenu> children = menu.getChildren();
            if (menu.getType().intValue() == 1) {
                // 加载隐藏路由
                List<SysMenu> hiddenMenuList = children.stream().filter(item -> !StringUtils.isEmpty(item.getComponent())).collect(Collectors.toList());
                for (SysMenu hiddenMenu : hiddenMenuList) {
                    RouterVo hiddenRouter = new RouterVo();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
                    routers.add(hiddenRouter);
                }
            }else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    // 递归
                    router.setChildren(buildRouter(children));
                }
            }
            routers.add(router);
        }
        return routers;

    }


    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if (menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }



    @Override
    public List<String> findUserPermsByUserId(Long userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (userId.longValue() == 1) {
            sysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        } else {
            sysMenuList = sysMenuMapper.findMenuListByUserId(userId);
        }
        List<String> permsList = sysMenuList.stream().filter(item -> item.getType() == 2).map(item -> item.getPerms()).collect(Collectors.toList());
        return permsList;
    }
}



