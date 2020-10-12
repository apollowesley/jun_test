/**
 * 
 * @auther wuwang
 * @createTime 2014-6-23 下午11:05:09
 */
package com.admin;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hibernate.voDao.DAO;
import com.staticClass.JsonUtils;

/**
 * 
 * 
 * @author peaches
 */
public abstract class AdminBase<T, D extends DAO> extends HttpServlet {

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	public T bean;
	public D dao;
	public String SUCCESS = "{\"realSucess\":\"true\",\"success\":\"true\"}";
	public JSONObject FAIL = JSONObject.fromObject("{\"realSucess\":\"false\",\"success\":\"true\"}");

	/**
	 * 必须调用这个方法，用来获得参数
	 * 
	 * @param request
	 * @param response
	 * @createTime 2014-6-23 下午11:15:01
	 */
	public void init(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.setBean();
		this.setDao();
	}

	/**
	 * 设置实体类bean，子类必需实现
	 * 
	 * @createTime 2014-7-11 上午12:14:20
	 */
	public abstract void setBean();

	/**
	 * 设置 数据访问对象类DAO，子类必需实现
	 * 
	 * @createTime 2014-7-11 上午12:14:18
	 */
	public abstract void setDao();

	/**
	 * 获取参数用的
	 * 
	 * @param s
	 * @return
	 * @createTime 2014-7-16 下午9:29:04
	 */
	public String getParameter(String s) {
		return request.getParameter(s);
	}

	/**
	 * 获取参数用的，i为0（任意），返回int类型
	 * 
	 * @param s
	 * @param i
	 * @return
	 * @createTime 2014-7-16 下午9:28:55
	 */
	public int getParameter(String s, int i) {
		return Integer.valueOf(request.getParameter(s));
	}

	/**
	 * 普通的object转Json字符串方法
	 * 
	 * @param object
	 * @return
	 * @createTime 2014-7-7 下午10:41:02
	 */
	public String toJson(Object object) {
		JSONArray jsonarray = JsonUtils.parseArrayFromObject(object);
		return jsonarray.toString();
	}

	/**
	 * 能用返所有数据 未实现
	 * 
	 * @return
	 * @createTime 2014-7-7 下午10:46:12
	 */
	public String List() {
		return toJson(dao.findAll());
	}

	/**
	 * 获取一条数据
	 * 
	 * @return
	 * @createTime 2014-7-16 下午10:44:13
	 */
	public String getById() {
		return toJson(dao.findById(getParameter("id", 0)));
	}

	/**
	 * 删除一条数据
	 * 
	 * @return
	 * @createTime 2014-7-11 上午12:33:24
	 */
	public String delete() {
		String ids = getParameter("ids");
		String[] id = ids.split(",");
		for (String i : id) {
			bean = (T) dao.findById(Integer.valueOf(i));
			dao.delete(bean);
		}
		return SUCCESS;
	}

	public void save() {
		dao.save(bean);
	}

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}

	public D getDao() {
		return dao;
	}

	public void setDao(D dao) {
		this.dao = dao;
	};
}
