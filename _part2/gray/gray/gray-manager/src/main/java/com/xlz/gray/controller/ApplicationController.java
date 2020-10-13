package com.xlz.gray.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.ParseException;
import com.xlz.admin.model.Tree;
import com.xlz.admin.service.OrganizationService;
import com.xlz.commons.base.BaseController;
import com.xlz.commons.base.mapper.FilterRule;
import com.xlz.commons.base.mapper.PageQuery;
import com.xlz.commons.utils.PageInfo;
import com.xlz.commons.utils.StringUtils;
import com.xlz.engine.common.config.Level;
import com.xlz.gray.model.Application;
import com.xlz.gray.model.ApplicationNginx;
import com.xlz.gray.model.ApplicationStrategy;
import com.xlz.gray.model.Setting;
import com.xlz.gray.service.ApplicationNginxService;
import com.xlz.gray.service.ApplicationService;
import com.xlz.gray.service.ApplicationStrategyService;
import com.xlz.gray.service.SettingService;

/**
 * @description：被灰度系统管理
 * @author：zhagnll
 */
@Controller
@RequestMapping("/application")
public class ApplicationController extends BaseController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
	private ApplicationNginxService	 applicationNginxService;
    @Autowired
    private ApplicationStrategyService applicationStrategyService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private SettingService settingService;
    
    /**
     * 被灰度系统管理页
     *
     * @return
     */
    @GetMapping("/manager")
    public String manager() {
        return "gray/application/application_manager";
    }

    /**
     * 被灰度系统管理列表
     *
     * @param entity
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @PostMapping("/dataGrid")
    @ResponseBody
    public Object dataGrid(Application entity,Integer page, Integer rows, String sort, String order) {
        List<FilterRule> filterRuleList = new ArrayList<>();
        if (StringUtils.isNotBlank(entity.getName())) {
            filterRuleList.add(new FilterRule("name","like",entity.getName() + "%"));
        }
        if (StringUtils.isNotBlank(entity.getCreateUser())) {
        	filterRuleList.add(new FilterRule("create_user","=",entity.getCreateUser()));
        }
        if (entity.getOrganizationId() == null) {
        	entity.setOrganizationId("-1");
        }
        filterRuleList.add(new FilterRule("organization_id","=",entity.getOrganizationId()));
		PageQuery pageQuery = new PageQuery(page,rows,sort,order);
		PageInfo<Application> pageInfo = applicationService.findByPage(filterRuleList, pageQuery);
        return pageInfo;
    }

    /**
     * 添加被灰度系统页
     *
     * @return
     */
    @GetMapping("/addPage")
    public String addPage(Model model) {
    	model.addAttribute("engineList",applicationService.findGraySetting());
        return "gray/application/application_add";
    }

    /**
     * 添加被灰度系统
     *
     * @param entity
     * @return
     * @throws IOException 
     */
    @PostMapping("/add")
    @ResponseBody
    public Object add(Application entity,HttpServletRequest request) throws IOException {
        return applicationService.saveGrayConfig(entity, request);
    }

    /**
     * 编辑被灰度系统页
     *
     * @param id
     * @param model
     * @return
     * @throws ParseException 
     */
    @GetMapping("/editPage")
    public String editPage(Model model, Long id) throws ParseException {
        Application entity = applicationService.findById(id);
        List<FilterRule> filterRuleList = new ArrayList<>();
    	filterRuleList.add(new FilterRule("application_id","=",id));
    	List<ApplicationNginx>  list = applicationNginxService.findAll(filterRuleList);
        StringBuilder sbuilder = new StringBuilder();
        int index = 0;
    	for(ApplicationNginx nginx : list){
    		if(index != 0){
    			sbuilder.append(",\n");
    		}
    		index ++;
    		sbuilder.append(nginx.getIp()).append(":").append(nginx.getPort());
    	}
        entity.setNginxs(sbuilder.toString());
        
        //获取应用策略
        filterRuleList.clear();
    	filterRuleList.add(new FilterRule("link_id","=",entity.getId()));
    	filterRuleList.add(new FilterRule("level","=",Level.application));
    	List<ApplicationStrategy> applicationStrategys = applicationStrategyService.findAll(filterRuleList);
    	
    	Map<String,ApplicationStrategy> strategys = (Map<String,ApplicationStrategy>)entity.getStrategy();
    	for(ApplicationStrategy applicationStrategy :  applicationStrategys){
    		strategys.put(applicationStrategy.getType(), applicationStrategy);
    	}
    	
    	List<Setting> engineTypeList = settingService.findListBySettingType("gray_engine");
    	for (Setting type : engineTypeList) {  
    		ApplicationStrategy applicationStrategy = strategys.get(type.getSettingValue());
    		if(applicationStrategy == null){
    			applicationStrategy = new ApplicationStrategy();
    			applicationStrategy.setType(type.getSettingValue());
    			applicationStrategy.setStatus(0);
    			strategys.put(type.getSettingValue(),applicationStrategy);
    		}
    	}
    	
    	model.addAttribute("entity", entity);
    	
    	List<Setting> engineList = applicationService.findGraySettingConfig(entity.getId()) ;
    	//是否勾选
    	for(Setting setting : engineList){
    		setting.setSettingType("");
    		ApplicationStrategy strategy = strategys.get(setting.getSettingValue());
    		if(strategy != null && strategy.getStrategyId()!=null){
    			//临时存放策略id
    			setting.setCategory(strategy.getStrategyId().toString());
    			setting.setSettingType("checked");
    		}else{
    			setting.setCategory("");
    		}
    	}
    	model.addAttribute("engineList",engineList);
        return "gray/application/application_edit";
    }

    /**
     * 编辑被灰度系统
     *
     * @param entity
     * @return
     * @throws IOException 
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Object edit(Application entity,String oldApplicationId,HttpServletRequest request) throws IOException {
        return applicationService.editGrayConfig(entity, oldApplicationId, request);
    }
    
    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String id) {
    	//检查是否下面有白名单，如果有不允许删除
    	/*List<FilterRule> filterRuleList = new ArrayList<>();
    	filterRuleList.add(new FilterRule("group_id","=",id));
        Integer count = whiteListService.getTotalCount(filterRuleList);
        if (count != null && count.intValue() > 0) {
            return renderSuccess("请先删除组下的白名单!");
        }*/
    	applicationService.delete(id);
        return renderSuccess("删除成功！");
    }
    
    
    /**
     * 部门和系统资源树
     *
     * @return
     */
    @PostMapping(value = "/tree")
    @ResponseBody
    public Object selectTree() {
    	List<Tree> trees = organizationService.selectTree();
    	List<FilterRule> filterRuleList = new ArrayList<>();
    	List<Application> applicationlist = applicationService.findAll(filterRuleList);
        for(Application application : applicationlist){
        	Tree tree = new Tree();
            tree.setId(application.getId()+10000000);
            tree.setText(application.getName());
            tree.setIconCls("fi-widget");
            tree.setPid(new Long(application.getOrganizationId()));
            trees.add(tree);
        }
        
        return trees;
    }
}
