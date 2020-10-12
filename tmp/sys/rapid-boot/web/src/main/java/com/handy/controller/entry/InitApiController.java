package com.handy.controller.entry;

import cn.hutool.json.JSONUtil;
import com.handy.common.constants.Constants;
import com.handy.param.init.*;
import com.handy.service.entity.sys.SysResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author handy
 * @Description: {首页数据加载}
 * @date 2019/8/21 15:24
 */
@RestController
@RequestMapping("/api")
@Api(value = "首页数据加载")
public class InitApiController {

    @GetMapping("/init")
    @ApiOperation(value = "首页初始化参数")
    public String init(@ApiIgnore() HttpSession session) {
        Init init = new Init();
        // 缓存清理加载
        getClearInfo(init);
        // 首页加载
        getHomeInfo(init);
        // logo加载
        getLogoInfo(init);
        // 菜单加载
        getMenuInfo(session, init);
        return JSONUtil.toJsonStr(init);
    }

    private void getClearInfo(Init init) {
        ClearInfo clearInfo = new ClearInfo();
        clearInfo.setClearUrl("");
        init.setClearInfo(clearInfo);
    }

    private void getHomeInfo(Init init) {
        HomeInfo homeInfo = new HomeInfo();
        homeInfo.setTitle("首页");
        homeInfo.setIcon("fa fa-home");
        homeInfo.setHref("/entry/home/view");
        init.setHomeInfo(homeInfo);
    }

    private void getLogoInfo(Init init) {
        LogoInfo logoInfo = new LogoInfo();
        logoInfo.setTitle("Rapid-Boot");
        logoInfo.setImage("images/logo.png");
        logoInfo.setHref("");
        init.setLogoInfo(logoInfo);
    }

    private void getMenuInfo(HttpSession session, Init init) {
        val userResource = (List<SysResource>) session.getAttribute(Constants.USERRESOURCEKEY);
        if (userResource == null) {
            return;
        }
        MenuInfo menuInfo = new MenuInfo();
        List<Menu> list = set(userResource, "0");
        if (list.size() > 0) {
            menuInfo.setMenu1(list.get(0));
        }
        if (list.size() > 1) {
            menuInfo.setMenu2(list.get(1));
        }
        if (list.size() > 2) {
            menuInfo.setMenu3(list.get(2));
        }
        if (list.size() > 3) {
            menuInfo.setMenu4(list.get(3));
        }
        if (list.size() > 4) {
            menuInfo.setMenu5(list.get(4));
        }
        init.setMenuInfo(menuInfo);
    }

    private List<Menu> set(List<SysResource> userResource, String superId) {
        // 一级菜单
        List<Menu> list = new ArrayList<>();
        for (SysResource sysResource : userResource) {
            if (superId.equals(sysResource.getSuperId())) {
                // 权限菜单
                Menu menu = new Menu();
                menu.setId(sysResource.getId());
                menu.setTitle(sysResource.getTitle());
                menu.setIcon(sysResource.getIcon());

                List<Child> childList = setChild(userResource, sysResource.getId().toString());
                if (childList.size() > 1) {
                    menu.setChild(childList);
                }
                list.add(menu);
            }
        }
        return list;
    }

    private List<Child> setChild(List<SysResource> userResource, String superId) {
        // 2级菜单
        List<Child> list = new ArrayList<>();
        for (SysResource sysResource : userResource) {
            if (superId.equals(sysResource.getSuperId())) {
                Child child = new Child();
                child.setId(sysResource.getId());
                child.setTitle(sysResource.getTitle());
                child.setIcon(sysResource.getIcon());
                child.setHref(sysResource.getHref());
                child.setTarget(sysResource.getTarget());

                List<Child> childList = setChild(userResource, sysResource.getId().toString());
                if (childList.size() > 0) {
                    child.setChild(childList);
                }

                list.add(child);
            }
        }
        return list;
    }
}
