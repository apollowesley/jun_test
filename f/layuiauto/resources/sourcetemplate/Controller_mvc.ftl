
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.bessky.financial.pojo.system.PageData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 
 * ${remark!}操作相关
 * 
 * @author wangbo  ${.now?string["yyyy年MM月dd"]}
 * @version Bessky V200R001
 * @since Bessky V200R001C01
 */
@Controller
@RequestMapping(value = "/${entityName?lower_case}")
public class ${entityCamelName}Controller{

    private static  Logger logger= LoggerFactory.getLogger(${entityCamelName}Controller.class);

    @Autowired
    private ${entityCamelName}Service ${entityName}Service;

    /**
     * 
     * 跳至列表页面
     * 
     * @author 
     * @param model
     * @return String
     */    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) 
    {
        return "/${entityName}/${tableName}_list";
    }
    
    /**
     * 
     * 跳至编辑页面
     * 
     * @author 
     * @param model
     * @param id
     * @return String
     */   
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(ModelMap model,Integer ${primaryProperty}) 
    {
    	${entityCamelName} item = ${entityName}Service.selectByPrimaryKey(${primaryProperty});
    	model.put("${entityName}", item);
        return "/${entityName}/${tableName}_edit";
    }
    
        
    /**
     * 
     * 分页查询
     * 
     * @author 
     * @param request
     * @return ResponseResult
     */      
    @RequestMapping(value = "/search")
    @ResponseBody
    public ResponseResult search( HttpServletRequest request)
    {
    	PageData pd = new PageData(request);
        PageHelper.startPage(Integer.parseInt((String)pd.get("page")), Integer.parseInt((String)pd.get("limit")));
        Page<${entityCamelName}> list = ${entityName}Service.selectPageList(pd);
        return new ResponseResult(0, "获取数据成功", list, list.getTotal());
    }
    

    /**
     * 
     * 批量删除
     * 
     * @author 
     * @param ids
     * @return ResponseResult
     */	
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseResult delete(@RequestParam("${primaryProperty}s[]") List<Integer> ${primaryProperty}s)
    {
		if(CollectionUtils.isNotEmpty(${primaryProperty}s)) 
        {
            for(int ${primaryProperty} : ${primaryProperty}s)
            {
                 ${entityName}Service.deleteByPrimaryKey(${primaryProperty});
            }
        }
    	return new ResponseResult(200, "success", null, 0);
    }    
    
    /**
     * 
     * 新增或者更新记录
     * 
     * @author 
     * @param ${entityCamelName}
     * @return ResponseResult
     */	      
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseResult save(${entityCamelName} item)
    {
		int result = 0;
	    //新增
	    if( item.get${primaryCamelProperty}() == null) 
	    {
	        result = ${entityName}Service.insertSelective(item);
	    }else {//更新
	        result = ${entityName}Service.updateByPrimaryKey(item);
	    }
		if(result > 0) 
		{
		    return new ResponseResult(200, "success", null, 0);
		}
		return new ResponseResult(500, "faild", null, 0);
			
    }

}
