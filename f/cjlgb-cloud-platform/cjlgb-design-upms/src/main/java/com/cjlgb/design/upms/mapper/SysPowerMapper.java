package com.cjlgb.design.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjlgb.design.common.upms.entity.SysPower;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author WFT
 * @date 2020/7/14
 * description:
 */
public interface SysPowerMapper extends BaseMapper<SysPower> {


    /**
     * 根据权限列表查询可见的系统权限
     * @param authorities java.lang.String
     * @return java.util.List
     */
    @Select(value = "" +
            "WITH RECURSIVE tmp as ( " +
            "   SELECT `id`,`name`,`pid`,`permission` FROM sys_power WHERE permission IN (${authorities})" +
            "   UNION DISTINCT" +
            "   SELECT sp.`id`,sp.`name`,sp.`pid`,sp.`permission` FROM sys_power sp INNER JOIN tmp t ON sp.id=t.pid" +
            ") SELECT * FROM tmp")
    List<SysPower> selectByAuthorities(@Param(value = "authorities") String authorities);
}
