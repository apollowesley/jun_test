package cn.gson.framework.controller.system;

import cn.gson.framework.controller.BaseController;
import cn.gson.framework.model.pojo.Account;
import cn.gson.framework.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.controller.system</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月30日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@RestController
@RequestMapping("system/account")
public class AccountController extends BaseController<AccountService, Account> {

}
