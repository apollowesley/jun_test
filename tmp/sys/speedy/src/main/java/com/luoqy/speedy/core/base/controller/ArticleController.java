package com.luoqy.speedy.core.base.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoqy.speedy.common.WebCodeGenerate;
import com.luoqy.speedy.data.MySqldbUtil;
import com.luoqy.speedy.util.Result;
/**
 *  文章管理
 *  增加搜索关键字keywords和 类型标识"";
 */
@Controller
@RequestMapping("/common/article")
public class ArticleController{
	private String PREFIX = "WEB-INF/common/article/";
	
	/**
	 * @return
	 */
	@RequestMapping("")
	public String index(){
		return PREFIX+"article";
	}
	@RequestMapping("/addPage")
	public String addPage(){
		return PREFIX+"articleAdd";
	}
	@RequestMapping("/updatePage")
	public String updatePage(Model model,String id){
		Object article=MySqldbUtil.mysqlSelect("select * from speedy_article where id="+id, "none",null);
		model.addAttribute("article", article);
		return PREFIX+"articleUpdate";
	}
	
	@RequestMapping("/loadPage")
	@ResponseBody
	public Result list(String page,String pageSize){
		Result result=new Result();
		int pages=0;
		int pageSizes=20;
		if(null!=pageSize&&!"".equals(pageSize)){
			pageSizes=Integer.parseInt(pageSize);
		}
		if(null!=page&&null!=pageSize&&!"".equals(page)&&!"".equals(pageSize)){
			pages=Integer.valueOf(page);
			pageSizes=Integer.valueOf(pageSize);
			pages=pages*pageSizes;
		}
		Object menu=MySqldbUtil.mysqlSelect("select *,(select name from speedy_articletype  where speedy_articletype.id=speedy_article.columntype) columntype,if(type=1,'列表','单页') type,if(stick=1,'推荐','未推荐') stick,if(home=1,'显示','未显示') home from speedy_article limit "+pages+","+pageSize, "list", null);
		result.setData(menu);
		result.setMessage("数据加载成功");
		result.setState(1);
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Result update(HttpServletRequest req,String id) throws UnsupportedEncodingException {
		Result result=new Result();
		Enumeration<String> parames=req.getParameterNames();
		String set=" set ";
		while(parames.hasMoreElements()){
			String name = (String)parames.nextElement();//调用nextElement方法获得元素
			String value=req.getParameter(name);
			if(!"id".equals(name)){
				if(null!=value&&!"".equals(value)){
					if(name.equals("content")){
						//value=value.replaceAll("\\\\'","\'");
						value=URLDecoder.decode(value, "utf-8");

						//URLEncoder.encode(value, "utf-8");
						/*if(value.contains("\"")){
							value=value.replaceAll("\"","\\\"");
						}*/
					}
					set+=name+"='"+value+"',";
				}
			}
		}
		if("set".equals(set.trim())){
			result.setData(null);
			result.setMessage("请传入需要更新的参数");
			result.setState(0);
		}else{
			String time=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			HttpSession  session=req.getSession(true);
			Map<String,Object> user=(Map<String,Object>)session.getAttribute("user");
			//这里不存储内容数据，需要处理特殊HTML格式数据，以便存储到数据库里，直接生成页面文件
			set+="updatetime='"+time+"',updateuserid="+user.get("id");
			int call=MySqldbUtil.mysqlExecute("update speedy_article"+set+" where id="+id, null);
			if(call>0){
				Object content=MySqldbUtil.mysqlSelect("select * from speedy_article where id="+id,"none",null);
				if(WebCodeGenerate.createView("", id, content)){
					result.setData(null);
					result.setMessage("更新成功");
					result.setState(1);
				}else{
					result.setData(null);
					result.setMessage("SEO生成失败");
					result.setState(0);
				}
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
		Enumeration<String> parames=req.getParameterNames();
		String set=" set ";
		while(parames.hasMoreElements()){
			String name = (String)parames.nextElement();//调用nextElement方法获得元素
			String value=req.getParameter(name);
			if(!"id".equals(name)){
				if(null!=value&&!"".equals(value)){
					if(value.contains("'")){
						value=value.replaceAll("'","\\'");
					}
					if(value.contains("\"")){
						value=value.replaceAll("\"","\\\"");
					}
					set+=name+"='"+value+"',";
				}
			}
		}
		if("set".equals(set.trim())){
			result.setData(null);
			result.setMessage("请传入需要保存的参数");
			result.setState(0);
		}else{
			String time=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
			HttpSession  session=req.getSession(true);
			Map<String,Object> user=(Map<String,Object>)session.getAttribute("user");
			set+="code='"+time+"',createtime='"+time+"',createuserid="+user.get("id")+",updatetime='"+time+"',updateuserid="+user.get("id");
			
			int call=MySqldbUtil.mysqlExecute("insert into speedy_article"+set, null);
			if(call>0){
				Object content=MySqldbUtil.mysqlSelect("select * from speedy_article where id="+call,"none",null);
				if(WebCodeGenerate.createView("", call+"", content)){
					result.setData(null);
					result.setMessage("更新成功");
					result.setState(1);
				}else{
					result.setData(null);
					result.setMessage("SEO生成失败");
					result.setState(0);
				}
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