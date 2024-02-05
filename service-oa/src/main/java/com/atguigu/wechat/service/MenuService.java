package com.atguigu.wechat.service;

import com.atguigu.auth.model.wechat.Menu;
import com.atguigu.auth.vo.wechat.MenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author smg
 * @since 2024-02-03
 */
public interface MenuService extends IService<Menu> {

    List<MenuVo> findMenuInfo();

    void syncMenu();
    void removeMenu();


}
