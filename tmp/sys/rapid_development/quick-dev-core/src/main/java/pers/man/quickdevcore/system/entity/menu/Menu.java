package pers.man.quickdevcore.system.entity.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pers.man.quickdevcore.system.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单实体
 * <p>
 * 注意:前端是layui-admin 单页面的菜单
 * 菜单的路由地址默认是按照菜单层级的 name 来设定的
 * 我们假设一级菜单的 name 是：a，二级菜单的是：b，三级菜单的 name 是 c，那么：
 * 三级菜单最终的路由地址就是：/a/b/c
 * 如果二级菜单没有三级菜单，那么二级菜单就是最终路由，地址就是：/a/b/
 * 如果一级菜单没有二级菜单，那么一级菜单就是最终路由，地址就是：/a/
 * <p>
 * 但如果你设置了参数jump，那么就会优先读取jump设定的路由地址 如："jump": "/user/set"
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-03 18:12
 * @project quick-dev
 */
@Setter
@Getter
@ToString
public class Menu extends BaseEntity {
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 菜单名称（与视图的文件夹名称和路由路径对应）
     */
    private String name;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单路由地址
     */
    private String jump;
    /**
     * 是否默认展开子菜单
     */
    private Boolean spread;
    /**
     * 父菜单id
     */
    private String parentMenuId;
    /**
     * 子菜单 最多到三级菜单
     */
    private List<Menu> childMenus = new ArrayList<>();
}
