package pers.man.quickdevcore.system.service.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.man.quickdevcore.system.entity.menu.Menu;
import pers.man.quickdevcore.system.entity.menu.MenuDTO;
import pers.man.quickdevcore.system.entity.menu.MenuVO;
import pers.man.quickdevcore.system.entity.web.ResultBean;
import pers.man.quickdevcore.system.persistence.menu.MenuMapper;
import pers.man.quickdevcore.system.service.menu.MenuService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-10 19:34
 * @project quick-dev
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResultBean save(MenuDTO menuDTO) {
        return null;
    }

    @Override
    public ResultBean getById(String id) {
        return null;
    }

    @Override
    public ResultBean getAll() {
        List<Menu> menus = menuMapper.listAll();
        LinkedList<MenuVO> vos = new LinkedList<>();
        menus.stream().forEach(menu -> {
            if (menu.getChildMenus() != null && menu.getChildMenus().size() != 0) {
                vos.add(convertToVo(menu));
            }
        });
        return ResultBean.success(vos);
    }


    @Override
    public ResultBean update(MenuDTO menuDTO) {
        return null;
    }

    @Override
    public ResultBean removeById(String id) {
        return null;
    }

    @Override
    public ResultBean remove(MenuDTO menuDTO) {
        return null;
    }

    private MenuVO convertToVo(Menu menu) {
        MenuVO menuVO = new MenuVO();
        menuVO.setTitle(menu.getTitle());
        menuVO.setIcon(menu.getIcon());
        menuVO.setJump(menu.getJump());
        menuVO.setName(menu.getName());
        menuVO.setSpread(menu.getSpread());
        if (menu.getChildMenus() != null && menu.getChildMenus().size() != 0) {
            LinkedList<MenuVO> childs = new LinkedList<>();
            menu.getChildMenus().stream().forEach(child -> {
                childs.add(convertToVo(child));
            });
            menuVO.setList(childs);
        }else{
            menuVO.setList(new ArrayList<>());
        }
        return menuVO;
    }
}
