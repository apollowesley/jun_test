package com.baomidou.crab.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.crab.sys.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统字典表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2018-10-20
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * <p>
     * 根据父字典编码查询字典列表
     * </p>
     *
     * @param parentCode 父字典编码
     * @return
     */
    List<Dict> selectByParentCode(@Param("parentCode") String parentCode);
}
