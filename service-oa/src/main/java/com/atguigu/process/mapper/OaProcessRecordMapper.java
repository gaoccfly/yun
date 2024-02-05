package com.atguigu.process.mapper;


import com.atguigu.auth.model.process.ProcessRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 审批记录 Mapper 接口
 * </p>
 *
 * @author smg
 * @since 2024-02-02
 */
@Mapper
public interface OaProcessRecordMapper extends BaseMapper<ProcessRecord> {

}
