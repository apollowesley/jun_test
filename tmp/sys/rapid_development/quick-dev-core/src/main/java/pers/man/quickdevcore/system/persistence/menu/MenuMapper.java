package pers.man.quickdevcore.system.persistence.menu;

import org.springframework.stereotype.Repository;
import pers.man.quickdevcore.system.entity.menu.Menu;
import pers.man.quickdevcore.system.persistence.BaseMapper;

import java.util.List;

/**
 * 菜单Mapper
 * @author MAN
 * @version 1.0
 * @date 2020-04-10 15:44
 * @project quick-dev
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu,String> {
    /**
     * 根据父菜单id获取菜单
     * @param id
     * @return
     */
    List<Menu> getByParentMenuId(String id);
}
