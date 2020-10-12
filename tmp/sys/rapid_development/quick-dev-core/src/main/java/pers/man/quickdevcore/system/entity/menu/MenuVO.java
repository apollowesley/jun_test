package pers.man.quickdevcore.system.entity.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pers.man.quickdevcore.system.entity.BaseVO;

import java.util.List;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-12 12:32
 * @project quick-dev
 */
@Getter
@Setter
@ToString
public class MenuVO extends BaseVO {
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单名称（与视图的文件夹名称和路由路径对应）
     */
    private String name;
    /**
     * 菜单路由地址
     */
    private String jump;
    /**
     * 是否默认展开子菜单
     */
    private Boolean spread;
    /**
     * 子菜单 最多到三级菜单
     */
    private List<MenuVO> list;
}
