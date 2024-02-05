package com.atguigu.process.mapper;

import com.atguigu.auth.model.process.Process2;
import com.atguigu.auth.vo.process.ProcessQueryVo;
import com.atguigu.auth.vo.process.ProcessVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 审批类型 Mapper 接口
 * </p>
 *
 * @author smg
 * @since 2024-01-31
 */
public interface OaProcessMapper extends BaseMapper<Process2> {
     IPage<ProcessVo> selectPage(Page<ProcessVo> pageInfo, @Param("vo") ProcessQueryVo processQueryVo);
}
