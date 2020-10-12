package com.luoqy.speedy.core.base.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoqy.speedy.core.base.model.Common;
import com.luoqy.speedy.data.MySqldbUtil;
import com.luoqy.speedy.util.Result;

public abstract class BaseController<T extends Common> {
	/**
	 * @param page 页码
	 * @param pageSize 
	 * @param condition 查询条件
	 * @param tablename 表名
	 * @return
	 */
	@RequestMapping("/loadPage")
	@ResponseBody
	public Result lodPage(String page,String pageSize,String condition,String tablename){
		Result result=new Result();
		int pages=0;
		int pageSizes=20;
		Map<String,String> map=new HashMap<String,String>();
		if(page!=null&&pageSize!=null&&!"".equals(page)&&!"".equals(pageSize)){
			pages=Integer.valueOf(page);
			pageSizes=Integer.valueOf(pageSize);
			pages=pages*pageSizes;
			map.put("page", ""+pages);
			map.put("pageSize", pageSize);
		}
		String sql="select * from "+tablename+" limit "+pages+","+pageSizes;
		if(null!=condition&&!"".equals(condition)){
			sql="select * from "+tablename+" where 1=1 "+condition+" limit "+pages+","+pageSizes;
		}
		Object object=MySqldbUtil.mysqlSelect(sql,"list",null);
		if(null!=object){
			result.setData(object);
			result.setMessage("查询分页成功");
			result.setState(1);
		}else{
			result.setData(object);
			result.setMessage("查询分页失败");
			result.setState(0);
		}
		return result;
	}
	
	/**
	 * @param tablename 表名
	 * @return 根据表名加载所有数据
	 */
	@RequestMapping("/load")
	@ResponseBody
	public Result load(String tablename){
		Result result=new Result();
		Object object=MySqldbUtil.mysqlSelect("select * from "+tablename,"list",null);
		if(null!=object){
			result.setData(object);
			result.setMessage("查询分页成功");
			result.setState(1);
		}else{
			result.setData(object);
			result.setMessage("查询分页失败");
			result.setState(0);
		}
		return result;
	}
	
	/**
	 * 
	 * @param tablename 表名
	 * @param id 数据ID
	 * @return 根据所提供信息彻底删除数据
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String tablename,String id){
		Result result=new Result();
		int call=MySqldbUtil.mysqlExecute("delete form "+tablename+" where id="+id,null);
		if(call>0){
			result.setData(null);
			result.setMessage("删除成功");
			result.setState(1);
		}else{
			result.setData(null);
			result.setMessage("删除失败");
			result.setState(0);
		}
		return result;
	}
	
	/**
	 * @param tablename 表名
	 * @param id 数据ID
	 * @param req 请求参数集合
	 * @return 根据表和所传入参数更新数据
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(String tablename,String id,HttpServletRequest req){
		Result result=new Result();
		Enumeration<String> parames=req.getParameterNames();
		String set=" set ";
		while(parames.hasMoreElements()){
			String name = (String)parames.nextElement();//调用nextElement方法获得元素
			String value=req.getParameter(name);
			if(!"tablename".equals(name)&&!"id".equals(name)){
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
			int call=MySqldbUtil.mysqlExecute("update "+tablename+set+" where id="+id,null);
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
	/**
	 * @param tablename 表名
	 * @param req 请求参数集合
	 * @return 根据表和所传入参数保存数据
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result save(String tablename,HttpServletRequest req){
		Result result=new Result();
		Enumeration<String> parames=req.getParameterNames();
		String set=" set ";
		while(parames.hasMoreElements()){
			String name = (String)parames.nextElement();//调用nextElement方法获得元素
			String value=req.getParameter(name);
			if(!"tablename".equals(name)&&!"id".equals(name)){
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
			int call=MySqldbUtil.mysqlExecute("insert into "+tablename+set,null);
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
