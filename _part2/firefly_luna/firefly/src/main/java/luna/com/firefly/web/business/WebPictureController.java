package luna.com.firefly.web.business;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import luna.com.firefly.entity.business.WebPicture;
import luna.com.firefly.entity.system.SystemConfig;
import luna.com.firefly.utils.DownLoadUtils;
import luna.com.firefly.utils.HttpResult;
import luna.com.firefly.utils.HttpStatus;
import luna.com.firefly.utils.ImageUtils;
import luna.com.firefly.utils.Servlets;
import luna.com.firefly.utils.SysDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <图片管理控制类>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年9月11日]
 */
@Slf4j
@Controller
@RequestMapping(value = "/pic")
public class WebPictureController extends BusinessBasicController
{
    @Autowired
    private DownloadFileWorker worker;
    
    /**
     * 获取pic列表
     * 
     * @param pageNumber
     * @param pageSize
     * @param orderDirection
     * @param orderField
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "picList")
    public String buttonList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNumber,
        @RequestParam(value = "numPerPage", defaultValue = "20") Integer pageSize,
        @RequestParam(value = "orderDirection", defaultValue = "asc") String orderDirection,
        @RequestParam(value = "orderField", defaultValue = "type") String orderField, Model model,
        HttpServletRequest request)
    {
        // 获取查询条件
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        try
        {
            // 根据条件查询图片信息
            Page<WebPicture> picList = pictureService.findAll(searchParams, pageNumber, pageSize);
            model.addAttribute("picList", picList.getContent());
            model.addAttribute("totalCount", picList.getTotalElements());
            model.addAttribute("pageNum", pageNumber);
            model.addAttribute("numPerPage", pageSize);
        }
        catch (Exception e)
        {
            log.error("Inner func[buttonList] catch exception while get button list ", e);
        }
        return "business/picture/pictureList";
    }
    
    /**
     * 跳转到图片增加页面  
     * 
     * @param model
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "add")
    public String addButton(ModelMap model)
    {
        return "business/picture/pictureInfo";
    }
    
    /**
     * 保存图片信息
     * 
     * @param button
     * @param buttonElderCode
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "savePicture")
    @ResponseBody
    public HttpResult savePicture(@Valid @ModelAttribute("webPicture") WebPicture picture, ModelMap model,
        HttpServletRequest request, HttpServletResponse response)
    {
        HttpResult result = new HttpResult();
        try
        {
            picture.setCreateTime(SysDate.getSysDate());
            picture.setType(ImageUtils.getPrefix(picture.getSource()));
            pictureService.save(picture);
            worker.putToQueue(picture);
            result.setResult(HttpStatus.OK);
            
        }
        catch (Exception e)
        {
            result.setResult("300", "操作失败");
            log.error("Enter func[savePicture] catch exception", e);
        }
        return result;
    }
    
    /**
     * 删除图片信息
     * 
     * @param buttonCodes
     * @param response
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public HttpResult deleteByIds(@RequestParam(value = "ids") String[] picIds, HttpServletResponse response,
        HttpServletRequest request)
    {
        HttpResult result = new HttpResult();
        
        try
        {
            for (String id : picIds)
            {
                pictureService.delete(Long.valueOf(id));
            }
            
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
    
}
