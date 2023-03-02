package com.atguigu.auth;

import com.atguigu.auth.service.SysRoleService;
import com.atguigu.model.system.SysRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMpDemo1 {

    @Resource
    private SysRoleService sysRoleService;

    @Test
    public void getAll(){
        List<SysRole> sysRoleList = sysRoleService.list();
        for (SysRole role : sysRoleList) {
            System.out.println(role);
        }
    }
}
