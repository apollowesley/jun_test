/**
 * Copyright (c) 2013-2015 www.javahih.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.hihsoft.sso.sysmonitor.syslogs.web.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.hihframework.exception.ControllerException;
import com.hihsoft.baseclass.web.controller.javahihBaseController;
import com.hihsoft.sso.sysmonitor.syslogs.model.TlogDbsql;
import com.hihsoft.sso.sysmonitor.syslogs.model.TlogLoginlog;
import com.hihsoft.sso.sysmonitor.syslogs.service.TlogDbsqlService;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2013 </p>
 * <p> Company:hihsoft.co.,ltd </p>
 *
 * @author hihsoft.co.,ltd
 * @version 1.0
 */
@Controller
@RequestMapping(value="/tlogDbsqlController.do")
public class TlogDbsqlController extends javahihBaseController {
@Autowired
private TlogDbsqlService tlogDbsqlService;


/**
* 访问列表
* @param request
* @param response
* @return
* @throws Exception
*/
@RequestMapping(params="method=list")
public ModelAndView list(HttpServletRequest request,
		HttpServletResponse response) throws ControllerException {
	ModelAndView mv=new ModelAndView(v_list);
	String sql = "select database_oid , table_name , table_operate , table_access_times , table_access_last_time , columes , u.username as userid from t_log_dbsql l , t_acl_userinfo u where l.userid=u.userid";
	Map<String, Object> filter = WebUtils.getParametersStartingWith(
			request, "srh_");// 得到查询条件
	String userid = (String) filter.get("userid");
	String tableName = (String) filter.get("tableName");
	String columes = (String) filter.get("columes");
	String beginDate = (String) filter.get("beginDate");
	String endDate = (String) filter.get("endDate");
	if (StringUtils.isNotEmpty(userid)) {
		sql += " and u.username like '%" + userid + "%'";
	}
	if (StringUtils.isNotEmpty(tableName)) {
		sql += " and table_name like '%" + tableName + "%'";
	}
	if (StringUtils.isNotEmpty(columes)) {
		sql += " and columes like '%" + columes + "%'";
	}
	if (StringUtils.isNotEmpty(beginDate)
			&& StringUtils.isNotEmpty(endDate)) {
		sql += " and table_access_last_time > '" + beginDate + "' and table_access_last_time < '"
				+ endDate + "'";
	}
	String pageSize = getParam(request, "rows");
	String pageNo = getParam(request, "page");
	if (pageSize == null)
		pageSize = "10";
	if (pageNo == null)
		pageNo = "1";
	List<TlogDbsql> list = tlogDbsqlService.getPageDataBySQL(TlogDbsql.class,
			sql, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
	int total = tlogDbsqlService.getTotalNumBySQL(sql);
	int rows = Integer.parseInt(pageSize);
	int pages = total % rows == 0 ? (total / rows) : (total / rows + 1);
	mv.addObject("userid",userid);
	mv.addObject("tableName",tableName);
	mv.addObject("columes",columes);
	mv.addObject("beginDate",beginDate);
	mv.addObject("endDate",endDate);
	mv.addObject("total", total);
	mv.addObject("rows", pageSize);
	mv.addObject("page", Integer.parseInt(pageNo));
	mv.addObject("pages", pages);
	mv.addObject("list", list);
    return mv;
}

/**
* 進入新增頁面
* @param request
* @param response
* @return
* @throws Exception
*/
@RequestMapping(params="method=add")
public ModelAndView add(HttpServletRequest request,
		HttpServletResponse response) throws ControllerException {
	
	return new ModelAndView("dbsql/tlogdbsqlform");
}

/**
* 查看记录详细
* @param request
* @param response
* @return
* @throws Exception
*/
@RequestMapping(params="method=view")
public ModelAndView view(HttpServletRequest request,
		HttpServletResponse response) throws ControllerException {
	
	return new ModelAndView("dbsql/tlogdbsqlform");
}

/**
* 新增、修改
* @param request
* @param response
* @return
* @throws Exception
*/
@RequestMapping(params="method=save")
public ModelAndView save(HttpServletRequest request,
		HttpServletResponse response) throws ControllerException {
	
	return new ModelAndView(v_save);
}

/**
* 删除
* @param request
* @param response
* @return
* @throws Exception
*/
@RequestMapping(params="method=delete")
public ModelAndView delete(HttpServletRequest request,
		HttpServletResponse response) throws ControllerException {
	String ids = request.getParameter("ids");
	List<TlogLoginlog> list = new ArrayList<TlogLoginlog>();
	if (ids != null && !"".equals(ids)) {
		String[] arr = ids.split(",");
		for (String id : arr) {
			TlogLoginlog obj = new TlogLoginlog();
			obj.setLogid(id);
			list.add(obj);
		}
	}
	tlogDbsqlService.deleteBatchVO(list);
	return new ModelAndView(new RedirectView(request.getContextPath()+"/tlogDbsqlController.do?method=list"));
}
}