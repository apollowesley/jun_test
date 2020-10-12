package org.nature4j.framework.bean;

import java.util.Enumeration;
import java.util.List;

import org.nature4j.framework.cache.NatureContext;
import org.nature4j.framework.core.NatureMap;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.util.ReflectionUtil;
import org.nature4j.framework.util.StringUtil;

public class Page extends NatureMap {
	private static final long serialVersionUID = 1L;
	
	public Page() {
	}
	
	/**
	 * this method is deprecated ,please use the static factory method newInstance(int,int,String) replacing; 
	 * @param pageSize
	 * @param pageNum
	 */
	@Deprecated
	public Page(int pageSize,int pageNum) {
		put("pageSize", pageSize);
		put("pageNum", pageNum);
	}
	
	/**
	 * 实例化Page
	 * @param pageSize 每页显示记录数
	 * @param pageNum 页码
	 * @param pageName 变量名
	 * @return
	 */
	public static Page newInstance(int pageSize,int pageNum,String pageName){
		Page page = null;
		String pagination = ConfigHelper.getPagination();
		if (StringUtil.isNotBank(pagination)) {
			page = (Page) ReflectionUtil.newInstance(pagination);
		}else{
			page = new Page();
		}
		page.put("pageSize", pageSize);
		page.put("pageNum", pageNum);
		page.put("pageName", pageName);
		return page;
	}
	
	/**
	 * 实例化Page
	 * @param pageSize 每页显示记录数
	 * @param pageNum 页码
	 * @param pageName 变量名
	 * @return
	 */
	public static Page newInstance(String pageName){
		int pageSize=ConfigHelper.pageSize();
		return newInstance(pageSize,1,pageName);
	}
	
	/**
	 * 自定义实例化Page
	 * @param page 自定义page对象
	 * @param pageSize 每页显示记录数
	 * @param pageNum 页码
	 * @param pageName 变量名
	 * @return
	 */
	public static Page newInstance(Page page,int pageSize,int pageNum,String pageName){
		page.put("pageSize", pageSize);
		page.put("pageNum", pageNum);
		page.put("pageName", pageName);
		return page;
	}
	
	public String getPageName(){
		return this.getString("pageName","page");
	}

	public void pagination(){
		String uri = NatureContext.getRequest().getRequestURI();
		String params = apendParams();
		int pageNum = getPageNum();
		int pageCnt = getInt("pageCnt");
		int rowCnt = getInt("rowCnt");
		String pageName = getString("pageName","page");
		StringBuffer pagination = pageView(uri, params, pageNum, pageCnt, rowCnt, pageName);
		put("pagination", pagination.toString());
	}
	
	/**
	 * page翻页样式，若需要修改样式，继承父类然后，重写此方法
	 * @param uri 列表连接
	 * @param params 拼接后的key=value&参数
	 * @param pageNum 当前页
	 * @param pageCnt 总页数
	 * @param rowCnt 总记录数
	 * @param pageName 分页实体类名称
	 * @return
	 */
	public StringBuffer pageView(String uri, String params, int pageNum, int pageCnt, int rowCnt, String pageName) {
		StringBuffer pagination = new StringBuffer();
		pagination.append("<div id='_pagination' class='_pagination'>");
		pagination.append("<span class='_desc_word'>共 </span>");
		pagination.append("<span class='_desc_num'>"+rowCnt+"</span>");
		pagination.append("<span class='_desc_word'>条 </span>");
		pagination.append("<span class='_desc_num'>"+pageNum+"</span>");
		pagination.append("<span class='_desc_word_'>/</span>");
		pagination.append("<span class='_desc_num'>"+pageCnt+"</span>");
		pagination.append("<span class='_desc_word'>页 </span>");
		if (pageNum>1) {
			pagination.append("<a class='_link_word' href='"+uri+"?"+pageName+".pageNum=1"+params+"'>首页</a>");
			pagination.append("&nbsp;&nbsp;");
			pagination.append("<a class='_link_word' href='"+uri+"?"+pageName+".pageNum="+(pageNum-1)+params+"'>上一页</a>");
		}else {
			pagination.append("<a class='_link_word'>首页</a>");
			pagination.append("&nbsp;&nbsp;");
			pagination.append("<a class='_link_word'>上一页</a>");
		}
		pagination.append("&nbsp;&nbsp;");
		if (pageNum<pageCnt) {
			pagination.append("<a class='_link_word' href='"+uri+"?"+pageName+".pageNum="+(pageNum+1)+params+"'>下一页</a>");
			pagination.append("&nbsp;&nbsp;");
			pagination.append("<a class='_link_word' href='"+uri+"?"+pageName+".pageNum="+pageCnt+params+"'>尾页</a>");
		}else {
			pagination.append("<a class='_link_word'>下一页</a>");
			pagination.append("&nbsp;&nbsp;");
			pagination.append("<a class='_link_word'>尾页</a>");
		}
		
		pagination.append("</div>");
		return pagination;
	}
	
	public String apendParams() {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> parameterNames = NatureContext.getRequest().getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			if (!name.contains(getPageName()+".")) {
				String value = NatureContext.getRequest().getParameter(name);
				if(value!=null){
					sb.append("&").append(name).append("=").append(value);
				}
			}
		}
		return sb.toString();
	}

	public int getPageSize() {
		return getInt("pageSize");
	}

	public void setPageSize(int pageSize) {
		put("pageSize", pageSize);
	}

	
	public int getPageNum() {
		int pageNum = getInt("pageNum");
		if (pageNum<=0) {
			pageNum=1;
		}
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.put("pageNum", pageNum);
	}

	public int getRowNum() {
		int rowNum = getInt("pageSize")*(getInt("pageNum")-1);
		return rowNum;
	}


	public void setRowCnt(int rowCnt) {
		int pageCnt = (rowCnt+getInt("pageSize")-1)/getInt("pageSize");
		put("pageCnt", pageCnt);
		put("rowCnt", rowCnt);
	}

	@SuppressWarnings("unchecked")
	public List<NatureMap> getDataList() {
		return (List<NatureMap>) get("dataList");
	}

	public void setDataList(List<? extends NatureMap> dataList) {
		put("dataList", dataList);
	}

}
