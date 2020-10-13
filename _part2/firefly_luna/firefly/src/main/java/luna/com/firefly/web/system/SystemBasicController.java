package luna.com.firefly.web.system;

import luna.com.firefly.service.system.AccountService;
import luna.com.firefly.service.system.ShiroDbRealm.ShiroUser;
import luna.com.firefly.service.system.SystemButtonService;
import luna.com.firefly.service.system.SystemConfigService;
import luna.com.firefly.service.system.SystemDicService;
import luna.com.firefly.service.system.SystemMenuService;
import luna.com.firefly.service.system.SystemRoleService;
import luna.com.firefly.service.system.SystemUserService;
import luna.com.firefly.utils.HttpResult;
import luna.com.firefly.utils.HttpStatus;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <功能详细描述>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年9月11日]
 */
public abstract class SystemBasicController
{
    
    @Autowired
    protected SystemRoleService roleService;
    
    @Autowired
    protected SystemDicService systemDicService;
    
    @Autowired
    protected SystemButtonService buttonService;
    
    @Autowired
    protected AccountService accountService;
    
    @Autowired
    protected SystemMenuService sysMenuService;
    
    @Autowired
    protected SystemUserService userService;
    
    @Autowired
    protected SystemConfigService systemConfigService;
    
    /**
     * 取出Shiro中的当前用户.
     */
    public ShiroUser getCurrentUser()
    {
        ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
        return user;
    }
    
    public HttpResult getCorrectResult()
    {
        HttpResult result = new HttpResult();
        result.setResult(HttpStatus.OK);
        return result;
    }
    
    public HttpResult getErrorResult()
    {
        HttpResult result = new HttpResult();
        result.setResult("300", "操作失败");
        return result;
    }
}
