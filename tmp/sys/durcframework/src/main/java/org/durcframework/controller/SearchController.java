package org.durcframework.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.durcframework.dao.BaseDao;
import org.durcframework.entity.ResultHolder;
import org.durcframework.entity.SearchEntity;
import org.durcframework.expression.ExpressionQuery;
import org.durcframework.processor.JsonObjProcessor;
import org.durcframework.service.SearchService;
import org.durcframework.util.JsonUtil;
import org.durcframework.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

/**
 * 负责查询的Controller,新建的Controller如果只有查询功能可以继承这个类
 * 
 * @author hc.tang 2013年11月14日
 * 
 * @param <Entity>
 *            实体类
 * @param <Service>
 *            查询的Service
 */
public abstract class SearchController<Entity, Service extends SearchService<Entity, ? extends BaseDao<Entity>>> {

	private static DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static CustomDateEditor customDateEditor = new CustomDateEditor(defaultDateFormat, true);
	
	private Service service;

	public Service getService() {
		return service;
	}

	@Autowired
	public void setService(Service service) {
		this.service = service;
	}
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, customDateEditor);
    }
	
	
	/**
	 * 返回成功的视图
	 * @return
	 */
	public ModelAndView success() {
		return ResultUtil.success();
	}
	
	/**
	 * 返回成功
	 * @param msg
	 * @return {"success":true,"msg":""}
	 */
	public ModelAndView success(String msg) {
		return ResultUtil.success(msg);
	}
	
	public ModelAndView success(List<Entity> list){
		
		ResultHolder holder = new ResultHolder(list, list.size(), 1, 0);
		
		String json = JsonUtil.toJsonString(buildJsonMap(holder), getDateFormatPattern());
		
		return ResultUtil.buildModelAndView(json);
	}
	
	/**
	 * 返回错误的视图
	 * @param errorMsg 错误信息
	 * @return {"success":false,"errorMsg":""}
	 */
	public ModelAndView error(String errorMsg) {
		return ResultUtil.error(errorMsg);
	}

	
	/**
	 * 带条件的查询
	 * @param query 条件封装对象
	 * @return 返回json数据并封装在ModelAndView中返回
	 * 前端页面需要用一个jsp文件接收,内容为:<br>
	 * </code>
	 * <pre>
	 * {@literal
	 * <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<%
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0);
		String json = (String)request.getAttribute("json");
		%>
		<%=json %>}
		</pre>
		</code>
	 */
	public ModelAndView query(ExpressionQuery query) {
		String json = queryForJson(query);
		return ResultUtil.buildModelAndView(json);
	}
	
	public ModelAndView queryWithProcessor(ExpressionQuery query,JsonObjProcessor<Entity> processor) {
		String json = queryForJsonProcessor(query,processor);
		return ResultUtil.buildModelAndView(json);
	}
	
	public ModelAndView queryByEntity(SearchEntity searchEntity) {
		ExpressionQuery query = searchEntity.buildExpressionQuery();
		return this.query(query);
	}
	
	public ModelAndView queryByEntityWithProcessor(SearchEntity searchEntity,JsonObjProcessor<Entity> processor) {
		ExpressionQuery query = searchEntity.buildExpressionQuery();
		String json = queryForJsonProcessor(query,processor);
		return ResultUtil.buildModelAndView(json);
	}
	
	public ModelAndView queryAll(ExpressionQuery query) {
		String json = queryAllForJson(query);
		return ResultUtil.buildModelAndView(json);
	}
	
	public ModelAndView queryAllWithProcessor(ExpressionQuery query,JsonObjProcessor<Entity> processor) {
		String json = queryAllForJsonProcessor(query,processor);
		return ResultUtil.buildModelAndView(json);
	}
	
	/**
	 * 将查询结果放到ResultHolder类中,可以从这个类中获取查询信息
	 * @param query
	 * @return
	 */
	public ResultHolder queryToResult(ExpressionQuery query) {
		List<Entity> list = this.service.find(query);
		
		int total = 0; // 总条数
		int pageIndex = query.getPageIndex(); // 当前第几页
		
		if(list.size() > 0) {
			// 总数
			// 如果是查询全部则直接返回结果集条数
			// 如果是分页查询则还需要带入条件执行一下sql
			total = query.getIsQueryAll() ? list.size() : this.service.findTotalCount(query);
		}

		return new ResultHolder(list, total, pageIndex, query.getPageSize());
	}
	
	/**
	 * 将查询结果放到ResultHolder类中,可以从这个类中获取查询信息
	 * @param query
	 * @return 返回所有的查询信息
	 */
	public ResultHolder queryAllToResult(ExpressionQuery query) {
		query.setIsQueryAll(true);
		return this.queryToResult(query);
	}
	
	/**
	 * 将查询结果放到ResultHolder类中,可以从这个类中获取查询信息
	 * @param searchEntity
	 * @return
	 */
	public ResultHolder queryToResult(SearchEntity searchEntity){
		ExpressionQuery query = searchEntity.buildExpressionQuery();
		return this.queryToResult(query);
	}

	/**
	 * 返回json字符串的查询
	 * 
	 * @param query
	 *            查询条件
	 * @return 以json格式字符串返回,如:<br>
	 *         <code>{page:1,total:23,rows[{name:"Jim",age:20},{name:"Tom",age:21}]}</code>
	 */
	private String queryForJson(ExpressionQuery query) {
		return JsonUtil.toJsonString(queryToMap(query), getDateFormatPattern());
	}
	
	private Map<String, Object> queryToMap(ExpressionQuery query){
		ResultHolder resultHolder = this.queryToResult(query);
		
		return buildJsonMap(resultHolder);
	}
	
	/**
	 * 方法重载,JsonObjProcessor用来处理list中的单个元素
	 * 
	 * @param query
	 *            条件对象
	 * @param processor
	 *            json对象处理器
	 * @return 以json格式字符串返回,如:<br>
	 *         <code>{page:1,total:23,rows[{name:"Jim",age:20},{name:"Tom",age:21}]}</code>
	 */
	private String queryForJsonProcessor(ExpressionQuery query,
			JsonObjProcessor<Entity> processor) {

		Map<String, Object> map = queryToMap(query);
		
		// 取出结果集
		@SuppressWarnings("unchecked")
		List<Entity> list = (List<Entity>)map.get(getRowsName());
		
		List<JSONObject> jsonObjList = processEntityToJSONObject(list, processor);
		// 重新设置结果集
		map.put(getRowsName(), jsonObjList);

		return JsonUtil.toJsonString(map);
	}

	/**
	 * 查询全部,并返回json字符串
	 * 
	 * @return 以json格式字符串返回,如:<br>
	 *         <code>{page:1,total:23,rows[{name:"Jim",age:20},{name:"Tom",age:21}]}</code>
	 */
	private String queryAllForJson(ExpressionQuery query) {
		query.setIsQueryAll(true);
		return this.queryForJson(query);
	}
	
	/**
	 * 查询全部<br>
	 * 方法重载,JsonObjProcessor用来处理list中的单个元素
	 * 
	 * @param query
	 *            条件对象
	 * @param processor
	 *            json对象处理器
	 * @return 以json格式字符串返回,如:<br>
	 *         <code>{page:1,total:23,rows[{name:"Jim",age:20},{name:"Tom",age:21}]}</code>
	 */
	private String queryAllForJsonProcessor(ExpressionQuery query,JsonObjProcessor<Entity> processor) {
		query.setIsQueryAll(true);
		return this.queryForJsonProcessor(query,processor);
	}


	/**
	 * 日期转化格式,默认为yyyy-MM-dd<br>
	 * 可以重写此方法返回自定义的日期格式
	 * @return
	 */
	protected String getDateFormatPattern() {
		return "yyyy-MM-dd";
	}

	/**
	 * json化后分页索引的名字
	 * 
	 * @return
	 */
	protected String getPageName() {
		return "page";
	}

	/**
	 * json化后总记录数的名字
	 * 
	 * @return
	 */
	protected String getTotalName() {
		return "total";
	}

	/**
	 * json化后结果集的名字
	 * 
	 * @return
	 */
	protected String getRowsName() {
		return "rows";
	}
	
	/**
	 * json化后总页数的名字
	 * @return
	 */
	protected String getPageCountName() {
		return "pageCount";
	}
	
	/**
	 * json化后每页记录数
	 * @return
	 */
	protected String getPageSizeName() {
		return "pageSize";
	}

	/**
	 * 返回ModelMap
	 * 
	 * @return
	 */
	protected Map<String, Object> getModelMap() {
		return new HashMap<String, Object>();
	}

	// 将list中的entity对象处理成JSONObject对象
	private List<JSONObject> processEntityToJSONObject(List<Entity> list,
			JsonObjProcessor<Entity> processor) {
		List<JSONObject> jsonObjList = new ArrayList<JSONObject>(list.size());

		for (Entity entity : list) {
			JSONObject jsonObject = JsonUtil.toJsonObj(entity,getDateFormatPattern());
			processor.process(entity, jsonObject);
			jsonObjList.add(jsonObject);
		}

		return jsonObjList;
	}

	// 将查询结果放进map中
	private Map<String, Object> buildJsonMap(ResultHolder resultHolder) {
		
		Map<String, Object> map = getModelMap();

		map.put(getPageName(), resultHolder.getPageIndex());
		map.put(getPageSizeName(), resultHolder.getPageSize());
		map.put(getTotalName(), resultHolder.getTotal());
		map.put(getRowsName(), resultHolder.getList());
		map.put(getPageCountName(), resultHolder.getPageCount());

		return map;
	}

}
