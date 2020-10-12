package com.luoqy.speedy.core.base.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoqy.speedy.data.MySqldbUtil;
import com.luoqy.speedy.util.Result;
import com.luoqy.speedy.util.SessionUtil;
/**
 * @author luoqy
 * @date 2019年8月24日
 * 	后台统一菜单路由
 */
@Controller
@RequestMapping("/common/menu")
public class MenuController{
	private String PREFIX = "WEB-INF/common/menu/";
	
	/**
	 * @return
	 */
	@RequestMapping("")
	public String index(){
		return PREFIX+"menu";
	}
	@RequestMapping("/addPage")
	public String addPage(){
		return PREFIX+"menuAdd";
	}
	@RequestMapping("/updatePage")
	public String updatePage(Model model,String id){
		Object menu=MySqldbUtil.mysqlSelect("select * from speedy_menu where id="+id, "none",null);
		model.addAttribute("menu", menu);
		return PREFIX+"menuUpdate";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadPage") 
	@ResponseBody
	public Result loadPage(String page,String pageSize){
		Result result=new Result();
		int pages=0;
		int pageSizes=20;
		if(null!=pageSize){
			pageSizes=Integer.parseInt(pageSize);
		}
		if(page!=null&&pageSize!=null&&!"".equals(page)&&!"".equals(pageSize)){
			pages=Integer.valueOf(page);
			pageSizes=Integer.valueOf(pageSize);
			pages=pages*pageSizes;
		}
		List<Map<String,Object>> list=(List<Map<String,Object>>)MySqldbUtil.mysqlSelect("select sm1.*,if(sm1.ismenu=1,\"是\",\"否\") as ismenu,if(parent=0,'最顶级',(select name from speedy_menu sm2 where sm2.id=sm1.parent)) as parent from speedy_menu as sm1 where sm1.parent=0 and sm1.ismenu=1 limit "+pages+","+pageSizes, "list",null);
		for(int i=0;i<list.size();i++){
			Object id=list.get(i).get("id");
			List<Map<String,Object>> sonList=(List<Map<String, Object>>) MySqldbUtil.mysqlSelect("select sm1.*,if(sm1.ismenu=1,\"是\",\"否\") as ismenu,if(sm1.parent=0,'最顶级',(select sm2.name from speedy_menu sm2 where sm2.id=sm1.parent)) as parentName from speedy_menu as sm1 where parent="+id, "list",null);
			list.get(i).put("menuSon",sonList);
			//这里应查询权限
			for(int j=0;j<sonList.size();j++){
				String sonId=(String) sonList.get(j).get("id");
				Object handle=MySqldbUtil.mysqlSelect("select sm1.*,if(sm1.ismenu=1,\"是\",\"否\") as ismenu,if(sm1.parent=0,'最顶级',(select sm2.name from speedy_menu sm2 where sm2.id=sm1.parent)) as parentName from speedy_menu as sm1 where parent="+sonId, "list",null);
				sonList.get(j).put("handle", handle);
			}
		}
		
		result.setData(list);
		result.setState(1);
		return result;
	}
	@RequestMapping("/update")
	@ResponseBody
	public Result update(HttpServletRequest req,String id){
		Result result=new Result();
		String path=req.getParameter("path");
		String sql="select  id from speedy_menu where path='"+path+"'";
		Object menu=MySqldbUtil.mysqlSelect(sql, "none",null);
		if(null!=menu){
			result.setData(null);
			result.setMessage("已存在的请求");
			result.setState(0);
			return result;
		}
		Enumeration<String> parames=req.getParameterNames();
		String set=" set ";
		while(parames.hasMoreElements()){
			String name = (String)parames.nextElement();//调用nextElement方法获得元素
			String value=req.getParameter(name);
			if(!"id".equals(name)){
				if(null!=value&&!"".equals(value)){
					set+=name+"='"+value+"',";
				}
			}
		}
		if("set".equals(set.trim())){
			result.setData(null);
			result.setMessage("请传入需要更新的参数");
			result.setState(0);
		}else{
			set=set.substring(0, set.length()-1);
			int call=MySqldbUtil.mysqlExecute("update speedy_menu"+set+" where id="+id,null);
			if(call>0){
				result.setData(null);
				result.setMessage("更新成功");
				result.setState(1);
			}else{
				result.setData(null);
				result.setMessage("更新失败");
				result.setState(0);
			}
			
		}
		return result;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Result add(HttpServletRequest req){
		Result result=new Result();
		String path=req.getParameter("path");
		String sql="select  id from speedy_menu where path='"+path+"'";
		Object menu=MySqldbUtil.mysqlSelect(sql, "none",null);
		if(null!=menu){
			result.setData(null);
			result.setMessage("已存在的请求");
			result.setState(0);
			return result;
		}
		Enumeration<String> parames=req.getParameterNames();
		String set=" set ";
		while(parames.hasMoreElements()){
			String name = (String)parames.nextElement();//调用nextElement方法获得元素
			String value=req.getParameter(name);
			if(!"id".equals(name)){
				if(null!=value&&!"".equals(value)){
					set+=name+"='"+value+"',";
				}
			}
		}
		if("set".equals(set.trim())){
			result.setData(null);
			result.setMessage("请传入需要保存的参数");
			result.setState(0);
		}else{
			set=set.substring(0, set.length()-1);
			int call=MySqldbUtil.mysqlExecute("insert into speedy_menu"+set,null);
			if(call>0){
				result.setData(null);
				result.setMessage("保存成功");
				result.setState(1);
			}else{
				result.setData(null);
				result.setMessage("保存失败");
				result.setState(0);
			}
			
		}
		return result;
	}
}