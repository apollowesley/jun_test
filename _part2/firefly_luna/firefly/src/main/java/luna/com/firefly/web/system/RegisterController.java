package luna.com.firefly.web.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import luna.com.firefly.entity.system.SystemUser;
import luna.com.firefly.utils.HttpResult;
import luna.com.firefly.utils.HttpStatus;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户注册的Controller.
 * 
 * @author 陆小凤
 */
@Slf4j
@Controller
@RequestMapping(value = "/register")
public class RegisterController extends SystemBasicController
{
    
    @RequestMapping(value = "save")
    @ResponseBody
    public HttpResult register(@Valid SystemUser user, HttpServletRequest request, HttpServletResponse response)
    {
        HttpResult result = new HttpResult();
        try
        {
            accountService.registerUser(user);
            // 执行成功则 返回信息 为 SUCCESS
            result.setResult(HttpStatus.OK);
            
        }
        catch (Exception e)
        {
            // 执行失败则 返回信息 为 ERROR
            result.setResult("300", "操作失败");
            if (log.isErrorEnabled())
            {
                log.info("Enter func[RegisterController.register]" + e);
            }
        }
        // 页面返回信息
        return result;
    }
    
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String registerForm(ModelMap model)
    {
        return "account/register";
    }
    
    /**
     * Ajax请求校验loginName是否唯一。
     */
    @RequestMapping(value = "checkLoginName")
    @ResponseBody
    public String checkLoginName(@RequestParam("loginName") String loginName)
    {
        if (accountService.findUserByLoginName(loginName) == null)
        {
            return "true";
        }
        else
        {
            return "false";
        }
    }
    
}
