package pers.man.quickdevcore.system.controller.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.man.quickdevcore.system.controller.BaseController;
import pers.man.quickdevcore.system.entity.web.ResultBean;
import pers.man.quickdevcore.system.service.menu.MenuService;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-10 19:37
 * @project quick-dev
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/listAll")
    public ResultBean listAll(){
        return menuService.getAll();
    }
}
