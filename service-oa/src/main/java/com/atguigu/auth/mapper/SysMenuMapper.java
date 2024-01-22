package com.atguigu.auth.mapper;


import com.atguigu.auth.model.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author smg
 * @since 2024-01-21
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> findListByUserId(@Param("userId") Long userId);

    List<SysMenu> findMenuListByUserId(@Param("userId") Long userId);
}
