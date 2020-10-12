package cn.gson.framework.controller.system;

import cn.gson.framework.controller.BaseController;
import cn.gson.framework.model.pojo.Role;
import cn.gson.framework.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : 角色管理</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年05月04日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@RestController
@RequestMapping("system/role")
public class RoleController extends BaseController<RoleService, Role> {

}
