package com.atguigu.auth.controller;

import com.atguigu.auth.model.system.SysUser;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.auth.vo.system.LoginVo;
import com.atguigu.auth.vo.system.RouterVo;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.common.jwt.JWTHelper;
import com.atguigu.common.result.Result;
import com.atguigu.common.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台登录登出
 * </p>
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        // 1、获取用户名和密码
        // 2、根据用户名查询数据库

        String username = loginVo.getUsername();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        // 3、用户信息是否存在
        if (sysUser == null) {
            throw new GuiguException(201, "用户不存在...");
        }
        // 4、判断密码
        // 取出数据库中的密文密码（MD5）
        String password_dB = sysUser.getPassword();
        String password_input = MD5.encrypt(loginVo.getPassword());
        if (!password_dB.equals(password_input)) {
            throw new GuiguException(201, "密码错误...");
        }
        // 5、判断用户是否被禁用  1  可用    0   禁用
        if (sysUser.getStatus().intValue() == 0) {
            throw new GuiguException(201, "用户被禁用...");
        }
        // 6、使用jwt根据用户id和用户名称生成token的字符串
        String token = JWTHelper.creatToken(sysUser.getId(), sysUser.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }


    /**
     * info
     *
     * @return
     */
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {

        // 1、从请求头获取用户信息（获取请求头的 token 字符串）
        String token = request.getHeader("token");

        // 2、从 token 字符串中获取 用户id 或者 用户名称
        Long userId = 2L;
//                JWTHelper.getUserId(token); //1L;

        // 3、根据 用户id 查询数据库， 获取用户信息
        SysUser sysUser = sysUserService.getById(userId);

        // 4、根据 用户id 获取用户可以操作的菜单列表
        // 查询数据库动态构建路由结构，进行显示
        List<RouterVo> routerList = sysMenuService.findUserMenuListByUserId(userId);


        // 5、根据 用户id 获取用户可以操作的按钮列表
        List<String> permsList = sysMenuService.findUserPermsByUserId(userId);

        // 6、返回相应的数据


        Map<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name", sysUser.getName());
        map.put("avatar", "https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");

        // 返回用户可以操作的菜单
        map.put("routers", routerList);

        // 返回用户可以操作的按钮
        map.put("buttons", permsList);

        return Result.ok(map);
    }

    /**
     * logout
     *
     * @return
     */
    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result logout() {
        return Result.ok();
    }
}