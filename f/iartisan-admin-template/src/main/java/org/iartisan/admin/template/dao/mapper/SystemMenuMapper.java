package org.iartisan.admin.template.dao.mapper;


import org.iartisan.admin.template.dao.model.SystemMenuDO;
import org.iartisan.runtime.jdbc.MybatisBaseMapper;

import java.util.List;

/**
 * system_menu 表操作接口
 *
 * @author King
 */
public interface SystemMenuMapper extends MybatisBaseMapper<SystemMenuDO> {

    List<SystemMenuDO> selectFirstMenus(SystemMenuDO t);

    List<SystemMenuDO> selectSecondMenus(SystemMenuDO t);

}