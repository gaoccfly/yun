package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysMenuMapper;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysRoleMenuService;
import com.atguigu.auth.utils.MenuHelper;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.vo.system.AssginMenuVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author roy
 * @since 2023-03-23
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysRoleMenuService sysRoleMenuService;

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
}
