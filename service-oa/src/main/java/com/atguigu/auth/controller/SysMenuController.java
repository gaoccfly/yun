package com.atguigu.auth.controller;


import com.atguigu.auth.service.SysMenuService;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssginMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author roy
 * @since 2023-03-23
 */
@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation("查询所有菜单和角色分配的菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable("roleId") Long roleId){
        List<SysMenu> list = sysMenuService.findMenuByRoleId(roleId);
        return Result.ok(list);
    }

    @ApiOperation("角色分配菜单")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo){
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }

    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> list =  sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation("新增菜单")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation("修改菜单")
    @PutMapping("/update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/remove/{id}")
    public Result removeMenuById(@PathVariable("id") Long id){
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }

}

