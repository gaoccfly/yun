package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.auth.service.SysUserRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.vo.system.AssginRoleVo;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysUserRoleService sysUserRoleService;

    //条件分页查询
    @Override
    public IPage<SysRole> pageQueryRole(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {

        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();

        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)){
            wrapper.like(SysRole::getRoleName, roleName);
        }

        return baseMapper.selectPage(pageParam, wrapper);
    }

    //查询所有角色和当前用户所属角色
    @Override
    public Map<String, Object> findRoleDataByUserId(Long userId) {

        //1.查询所有角色, 返回list集合, 返回
        List<SysRole> allRoleList = baseMapper.selectList(null);

        //2.根据userId查询 角色用户关系表, 查询userId对应所有角色id
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> exisUserRoleList = sysUserRoleService.list(wrapper);

        //从查询出来的用户id对应的角色list集合, 获取所有角色id
        List<Long> exisRoleIdList = exisUserRoleList.stream().map(c -> c.getRoleId()).collect(Collectors.toList());

        //3.根据查询所有角色id, 找到对应角色信息
        //根据角色id到所有的角色的list集合进行比较
        List<SysRole> assignRole = new ArrayList<>();
        for (SysRole sysRole : allRoleList) {
            //比较
            if(exisRoleIdList.contains(sysRole.getId())){
                assignRole.add(sysRole);
            }
        }

        //4.把得到两个部分数据封装map集合, 返回
        Map<String, Object> map = new HashMap<>();
        map.put("assignRoleList", assignRole);
        map.put("allRoleList", allRoleList);

        return map;
    }

    //为用户分配角色
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        Long userId = assginRoleVo.getUserId();

        //把用户之前分配的角色数据删除, 用户角色关系列表里面, 根据userId删除
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleService.remove(wrapper);

        //重新进行分配
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        for (Long roleId : roleIdList) {
            if(StringUtils.isEmpty(roleId)){
                continue;
            }
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRoleService.save(sysUserRole);
        }
    }
}
