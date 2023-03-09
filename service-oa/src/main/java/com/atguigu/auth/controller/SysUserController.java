package com.atguigu.auth.controller;


import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author roy
 * @since 2023-03-09
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    //用户条件分页查询
    @ApiOperation("用户条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result index(@PathVariable("page") Long page,
                        @PathVariable("limit") Long limit,
                        SysUserQueryVo sysUserQueryVo){
        Page<SysUser> pageParam = new Page<>(page, limit);
        IPage<SysUser> pageModel =  sysUserService.index(pageParam, sysUserQueryVo);

        return Result.ok(pageModel);
    }

    @ApiOperation("获取用户")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable("id") Long id){
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }


}

