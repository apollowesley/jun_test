package luna.com.firefly.web.system;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import luna.com.firefly.entity.system.SystemConfig;
import luna.com.firefly.utils.HttpResult;
import luna.com.firefly.utils.Servlets;
import luna.com.firefly.utils.SysDate;

import org.springframework.beans.BeanUtils;
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

import com.fasterxml.jackson.databind.util.BeanUtil;

/**
 * <系统参数配置>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年9月11日]
 */
@Slf4j
@Controller
@RequestMapping(value = "/systemConfig")
public class SystemConfigController extends SystemBasicController
{
    
    /**
     * <查询系统配置信息>
     * 
     * @param pageNumber
     * @param pageSize
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "systemConfigList")
    public String systemConfigList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
        @RequestParam(value = "numPerPage", defaultValue = "20") int numPerPage, Model model, ServletRequest request)
    {
        // 获取查询条件
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        try
        {
            // 根据条件查询角色信息
            Page<SystemConfig> systemConfigList =
                systemConfigService.getSystemConfigList(searchParams, pageNum, numPerPage);
            model.addAttribute("systemConfigList", systemConfigList);
            model.addAttribute("totalCount", systemConfigList.getTotalElements());
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("numPerPage", numPerPage);
        }
        catch (Exception e)
        {
            log.error("Enter func[systemConfigList] catch exception while get systemConfigList ", e);
        }
        return "manager/systemConfig/systemConfigList";
    }
    
    /**
     * <进入系统配置添加页面>
     * 
     * @return
     */
    @RequestMapping(value = "add")
    public String addConfig(ModelMap model)
    {
        return "manager/systemConfig/systemConfigInfo";
    }
    
    /**
     * <进入编辑页面>
     * 
     * @param topicId
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}")
    public String editInfo(@PathVariable Long id, ModelMap model)
    {
        SystemConfig systemConfig = new SystemConfig();
        try
        {
            systemConfig = systemConfigService.getSystemConfigById(id);
            model.addAttribute("systemConfig", systemConfig);
        }
        catch (Exception e)
        {
            log.error("Enter func[editInfo] catch exception ", e);
        }
        return "manager/systemConfig/systemConfigInfo";
    }
    
    /**
     * <保存 系统配置>
     * 
     * @param menu
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = "saveSystemConfig")
    @ResponseBody
    public HttpResult saveSystemConfig(@Valid @ModelAttribute("systemConfig") SystemConfig systemConfig,
        ModelMap model, HttpServletRequest request, HttpServletResponse response)
    {
        HttpResult result = new HttpResult();
        SystemConfig config = new SystemConfig();
        try
        {
            if (null != systemConfig.getConfigId())
            {
                config = systemConfigService.getSystemConfigById(systemConfig.getConfigId());
                systemConfig.setUpdateTime(SysDate.getSysDate());
                systemConfig.setCreateUser(getCurrentUser().name);
                BeanUtils.copyProperties(systemConfig, config);
            }
            else
            {
                config = systemConfig;
                config.setCreateTime(SysDate.getSysDate());
                config.setCreateUser(getCurrentUser().name);
            }
            // 保存
            systemConfigService.saveSystemConfig(config);
            // 返回正确的的结果
            result = getCorrectResult();
        }
        catch (Exception e)
        {
            // 返回错误的结果
            result = getErrorResult();
            log.error("Enter func[saveSystemConfig] catch exception", e);
        }
        return result;
        
    }
    
    /**
     * <删除系统参数配置>
     * 
     * @param response
     * @param request
     */
    @RequestMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResult deleteByIds(@RequestParam(value = "ids") String[] ids, HttpServletResponse response,
        HttpServletRequest request)
    {
        HttpResult result = new HttpResult();
        
        try
        {
            for (String id : ids)
            {
                systemConfigService.deleteConfigById(Long.valueOf(id));
            }
            // 返回正确的的结果
            result = getCorrectResult();
        }
        catch (Exception e)
        {
            // 返回错误的结果
            result = getErrorResult();
            log.info("Enter func[deleteByIds] catch exception", e);
        }
        // 返回客户端信息，打印相关信息
        return result;
    }
    
}
