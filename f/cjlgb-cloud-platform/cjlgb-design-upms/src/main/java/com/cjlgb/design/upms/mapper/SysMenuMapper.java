package com.cjlgb.design.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjlgb.design.common.upms.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author WFT
 * @date 2020/7/19
 * description:
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据某节点下的最大排序值
     * @param pid 父级Id
     * @return java.lang.Integer
     */
    @Select(value = "SELECT max(sort) FROM sys_menu WHERE pid = #{pid}")
    Integer selectMaxSort(@Param(value = "pid") Long pid);

    /**
     * 根据权限列表查询可见的菜单列表
     * @param authorities java.lang.String
     * @return java.util.List
     */
    @Select(value = "" +
            "WITH RECURSIVE tmp as ( " +
            "   SELECT `id`,`name`,`path`,`icon`,`pid`,`sort` FROM sys_menu WHERE permission IN (${authorities}) " +
            "   UNION DISTINCT " +
            "   SELECT m.`id`,m.`name`,m.`path`,m.`icon`,m.`pid`,m.`sort` FROM sys_menu m INNER JOIN tmp t ON m.id=t.pid" +
            ") SELECT * FROM tmp ")
    List<SysMenu> selectByAuthorities(@Param(value = "authorities") String authorities);
}
