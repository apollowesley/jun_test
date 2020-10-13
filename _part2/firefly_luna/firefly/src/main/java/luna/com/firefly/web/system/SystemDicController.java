package luna.com.firefly.web.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import luna.com.firefly.entity.system.SystemDic;
import luna.com.firefly.service.system.DictionaryService;
import luna.com.firefly.utils.HttpResult;
import luna.com.firefly.utils.Servlets;
import luna.com.firefly.utils.SysDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * <系统字典表的接口>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年9月11日]
 */
@Slf4j
@Controller
@RequestMapping(value = "/systemDic")
public class SystemDicController extends SystemBasicController
{
    
    @Autowired
    private DictionaryService ds;
    
    /**
     * <查询系统字典信息>
     * 
     * @param pageNumber
     * @param pageSize
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "systemIdDicList")
    public String systemIdDicList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
        @RequestParam(value = "numPerPage", defaultValue = "20") int numPerPage, Model model, ServletRequest request)
    {
        // 获取查询条件
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        try
        {
            Page<SystemDic> sysDicList = systemDicService.getSysDicList(searchParams, pageNum, numPerPage);
            model.addAttribute("sysDicList", sysDicList);
            model.addAttribute("totalCount", sysDicList.getTotalElements());
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("numPerPage", numPerPage);
        }
        catch (Exception e)
        {
            log.error("Enter func[systemIdDicList].  catch exception while get sysDic list ", e);
        }
        return "manager/systemDic/systemDicList";
    }
    
    /**
     * <进入系统字典 添加页面>
     * 
     * @return
     */
    @RequestMapping(value = "add")
    public String pageAddInfo(ModelMap model)
    {
        return "manager/systemDic/systemDicInfo";
    }
    
    /**
     * <执行系统字典添加，修改操作>
     * 
     * @param sysDic
     * @param request
     * @param response
     * @return
     */
    
    @RequestMapping(value = "saveInfo")
    @ResponseBody
    public HttpResult saveInfo(@Valid @ModelAttribute("systemDic") SystemDic sysDic, HttpServletRequest request,
        HttpServletResponse response)
    {
        HttpResult result = new HttpResult();
        SystemDic systemDic = new SystemDic();
        try
        {
            if (null == sysDic.getId())
            {
                sysDic.setCreateTime(SysDate.getSysDate());
            }
            else
            {
                systemDic = systemDicService.getSystemDic(sysDic.getId());
                sysDic.setUpdateTime(SysDate.getSysDate());
            }
            BeanUtils.copyProperties(sysDic, systemDic);
            // 执行保存信息 操作
            systemDicService.saveSysDic(systemDic);
            ds.flush("SystemDic");
            // 执行失败则 返回信息 为 CORRECT
            result = getCorrectResult();
        }
        catch (Exception e)
        {
            // 执行失败则 返回信息 为 ERROR
            result = getErrorResult();
            log.error("Enter func[saveInfo]", e);
        }
        // 页面返回信息
        return result;
    }
    
    /**
     * <进入系统字典信息维护页面> <转到信息维护页面>
     * 
     * @param id
     * @param model
     * @param response
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "edit/{id}")
    public String editInfo(@PathVariable Long id, ModelMap model, HttpServletResponse response,
        HttpServletRequest request)
    {
        SystemDic systemDic = new SystemDic();
        try
        {
            // 获得 系统字典表信息
            systemDic = systemDicService.getSystemDic(id);
            model.addAttribute("systemDic", systemDic);
        }
        catch (Exception e)
        {
            log.info("Enter func[editInfo]", e);
        }
        return "manager/systemDic/systemDicInfo";
    }
    
    /**
     * <删除系统字典表信息>
     * 
     * @param response
     * @param request
     * @return
     */
    
    @RequestMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResult deleteByIds(HttpServletResponse response, HttpServletRequest request)
    {
        // 获得-选中删除的ids
        String[] ids = request.getParameterValues("ids");
        HttpResult result = new HttpResult();
        try
        {
            for (String id : ids)
            {
                // 执行删除操作
                systemDicService.deleteSystemDic(Long.valueOf(id));
            }
            ds.flush("SystemDic");
            result = getCorrectResult();
        }
        catch (Exception e)
        {
            // 执行失败则 返回信息 为 ERROR
            result = getErrorResult();
            log.error("Enter func[deleteSystemDic] catch exception..", e);
        }
        // 页面返回信息
        return result;
    }
    
    /**
     * <查询SystemDic信息>
     * 
     * @param type
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "getSystemDicByType")
    @ResponseBody
    public List<List> getSystemDicByType(@RequestParam("type") String type)
    {
        List<List> result = new ArrayList<List>();
        List<String> list = null;
        List<SystemDic> dicList = systemDicService.getDicListByType(type);
        for (SystemDic dic : dicList)
        {
            list = new ArrayList<String>();
            list.add(dic.getValue() + "");
            list.add(dic.getName());
            result.add(list);
        }
        return result;
    }
}