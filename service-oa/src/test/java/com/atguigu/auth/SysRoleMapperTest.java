package com.atguigu.auth;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.model.system.SysRole;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<SysRole> List = sysRoleMapper.selectList(null);
      System.out.println(List);
    }
//    添加操作
    @Test
    public void testInsert(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员2");
        sysRole.setRoleCode("role");
        sysRole.setDescription("角色管理员2");

        int result = sysRoleMapper.insert(sysRole);
        System.out.println(result); //影响的行数
        System.out.println(sysRole.getId()); //id自动回填
    }
//    更新
    @Test
    public void testUpdateById(){
        SysRole sysRole = new SysRole();
        sysRole.setId(1L);
        sysRole.setRoleName("角色管理员2");

        int result = sysRoleMapper.updateById(sysRole);
        System.out.println(result);
        System.out.println("修改后名称"+ sysRole.getRoleName());
    }
    /**
     * application-dev.yml 加入配置
     * 此为默认值，如果你的默认值和mp默认的一样，则不需要该配置
     * mybatis-plus:
     *   global-config:
     *     db-config:
     *       logic-delete-value: 1
     *       logic-not-delete-value: 0
     */
    @Test
    public void testDeleteById(){
        int result = sysRoleMapper.deleteById(9);
        System.out.println(result);
    }
// 批量删除
    @Test
    public void testDeleteBatchIds() {
        int result = sysRoleMapper.deleteBatchIds(Arrays.asList(1, 1));
        System.out.println(result);
    }

    @Test
    public void testSelect1() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name", "角色管理员1");
        List<SysRole> users = sysRoleMapper.selectList(queryWrapper);
        System.out.println(users);
    }

    @Test
    public void testSelect2() {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getRoleName, "角色管理员1");
        List<SysRole> users = sysRoleMapper.selectList(queryWrapper);
        System.out.println(users);
    }
}