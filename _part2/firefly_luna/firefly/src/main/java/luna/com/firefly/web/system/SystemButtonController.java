package luna.com.firefly.web.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import luna.com.firefly.entity.system.SystemButton;
import luna.com.firefly.entity.system.SystemMenu;
import luna.com.firefly.utils.HttpResult;
import luna.com.firefly.utils.HttpStatus;
import luna.com.firefly.utils.Servlets;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <按钮管理控制类>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年9月11日]
 */
@Slf4j
@Controller
@RequestMapping(value = "/sysButton")
public class SystemButtonController extends SystemBasicController
{
    
    /**
     * 获取button列表
     * 
     * @param pageNumber
     * @param pageSize
     * @param orderDirection
     * @param orderField
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "buttonList")
    public String buttonList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNumber,
        @RequestParam(value = "numPerPage", defaultValue = "20") Integer pageSize,
        @RequestParam(value = "orderDirection", defaultValue = "asc") String orderDirection,
        @RequestParam(value = "orderField", defaultValue = "buttonCode") String orderField, Model model,
        HttpServletRequest request)
    {
        // 获取查询条件
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        try
        {
            // 根据条件查询按钮信息
            Page<SystemButton> buttonList =
                buttonService.getButtonList(searchParams, pageNumber, pageSize, orderDirection, orderField);
            model.addAttribute("button", buttonList);
            model.addAttribute("totalCount", buttonList.getTotalElements());
            model.addAttribute("pageNum", pageNumber);
            model.addAttribute("numPerPage", pageSize);
        }
        catch (Exception e)
        {
            log.error("Inner func[buttonList] catch exception while get button list ", e);
        }
        return "manager/button/buttonList";
    }
    
    /**
     * 跳转到button增加页面 <功能详细描述>
     * 
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "add")
    public String addButton(ModelMap model)
    {
        return "manager/button/buttonInfo";
    }
    
    /**
     * 保存按钮信息
     * 
     * @param button
     * @param buttonElderCode
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "saveButton")
    @ResponseBody
    public HttpResult saveButton(@Valid @ModelAttribute("button") SystemButton button,
        @RequestParam(value = "buttonElderCode") String buttonElderCode, ModelMap model, HttpServletRequest request,
        HttpServletResponse response)
    {
        HttpResult result = new HttpResult();
        SystemButton buttonEntity = null;
        try
        {
            // buttonElderCode不为空时，执行更新操作
            if (StringUtils.isNotBlank(buttonElderCode))
            {
                buttonEntity = buttonService.findByButtonCode(buttonElderCode);
                button.setCreateTime(buttonEntity.getCreateTime());
                button.setUpdateTime(new Date());
                button.setCreateUser(this.getCurrentUser().name);
                buttonService.saveButton(button);
            }
            else
            {
                buttonEntity = buttonService.findByButtonCode(button.getButtonCode());
                if (buttonEntity != null)
                {
                    result.setResult(HttpResult.SYSTEM_ERROR, "当前按钮代码已存在，请重新输入");
                    return result;
                }
                else
                {
                    button.setCreateUser(this.getCurrentUser().name);
                    button.setCreateTime(new Date());
                    buttonService.saveButton(button);
                }
            }
            
            result.setResult(HttpStatus.OK);
            
        }
        catch (Exception e)
        {
            result.setResult("300", "操作失败");
            log.debug("Enter func[saveMenu] catch exception", e);
        }
        return result;
    }
    
    /**
     * 删除按钮信息 <功能详细描述>
     * 
     * @param buttonCodes
     * @param response
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResult deleteByIds(@RequestParam(value = "ids") String[] buttonCodes, HttpServletResponse response,
        HttpServletRequest request)
    {
        HttpResult result = new HttpResult();
        
        try
        {
            String Ids = "";
            for (String buttonCode : buttonCodes)
            {
                buttonService.deleteButtonByButtonCode(buttonCode);
                Ids += buttonCode + ",";
            }
            Ids = Ids.substring(0, Ids.length() - 1);
            result.setResult(HttpStatus.OK);
        }
        catch (Exception e)
        {
            result.setResult("300", "操作失败");
            log.info("Enter func[deleteByIds]", e);
            
        }
        // 返回客户端信息，打印相关信息
        return result;
    }
    
    /**
     * 编辑按钮信息 <功能详细描述>
     * 
     * @param buttonCode
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "edit/{buttonCode}")
    public String editButtonInfo(@PathVariable String buttonCode, ModelMap model)
    {
        SystemButton button = new SystemButton();
        try
        {
            button = buttonService.findByButtonCode(buttonCode);
            model.addAttribute("button", button);
        }
        catch (Exception e)
        {
            log.error("Enter func[editButtonInfo] catch exception ", e);
        }
        
        return "manager/button/buttonInfo";
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "getMenuList")
    @ResponseBody
    public List<List> getMenuList()
    {
        List<SystemMenu> menuList = new ArrayList<SystemMenu>();
        menuList = sysMenuService.getAllMenuList();
        List<List> result = new ArrayList<List>();
        List<String> list = null;
        for (SystemMenu menu : menuList)
        {
            list = new ArrayList<String>();
            list.add(menu.getId() + "");
            list.add(menu.getName());
            result.add(list);
        }
        return result;
    }
}
