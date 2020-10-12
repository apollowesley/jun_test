package org.durcframework.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.durcframework.expression.subexpression.ListExpression;
import org.durcframework.expression.subexpression.ValueExpression;
import org.springframework.util.StringUtils;

/**
 * 查询条件类
 * 
 * @author thc 2011-10-22
 */
public class ExpressionQuery {

	/** 第一页 */
	private int pageIndex = 1;
	/** 每页10条记录 */
	private int pageSize = 10;
	
	private String sortname;
	private String sortorder;
	// 是否查询全部
	private boolean isQueryAll;
	
	private Map<String, Object> paramMap = new HashMap<String, Object>();

	private List<ValueExpression> valueExprList = new ArrayList<ValueExpression>();
	private List<JoinExpression> joinExprList = new ArrayList<JoinExpression>();
	private List<ListExpression> listExprList = new ArrayList<ListExpression>();
	
	public static ExpressionQuery buildQueryAll(){
		ExpressionQuery query = new ExpressionQuery();
		query.isQueryAll = true;
		return query;
	}

	public void addValueExpression(ValueExpression expression) {
		valueExprList.add(expression);
	}

	public void addJoinExpression(JoinExpression expression) {
		joinExprList.add(expression);
	}

	public void addListExpression(ListExpression expression) {
		listExprList.add(expression);
	}

	public void addParam(String name,Object value) {
		paramMap.put(name, value);
	}

	public String getOrder() {
		String sortname = getSortname();
		String sortorder = getSortorder();
		
		if (StringUtils.hasText(sortname) && StringUtils.hasText(sortorder)) {
			return SqlContent.BLANK + sortname + SqlContent.BLANK + sortorder
					+ SqlContent.BLANK;
		} else if (StringUtils.hasText(sortname)) {
			return SqlContent.BLANK + sortname + SqlContent.BLANK;
		} else {
			return null;
		}
	}

	public void add(Expression expre) {
		expre.addToQuery(this);
	}
	
	/**
	 * 是否查询全部
	 * @return
	 */
	public boolean getIsQueryAll() {
		return this.isQueryAll;
	}

	public void setIsQueryAll(boolean isQueryAll) {
		this.isQueryAll = isQueryAll;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}
	
	public void setSortorder(String sortorder) {
		if(SqlContent.ASC.equalsIgnoreCase(sortorder) || SqlContent.DESC.equalsIgnoreCase(sortorder)) {
			this.sortorder = sortorder;
		}else{
			this.sortorder = SqlContent.ASC;
		}
	}
	
	/**
	 * 返回第一条记录
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (int) ((pageIndex - 1) * pageSize);
	}

	public List<ValueExpression> getValueExprList() {
		return valueExprList;
	}

	public void setValueExprList(List<ValueExpression> valueExprList) {
		this.valueExprList = valueExprList;
	}

	public List<JoinExpression> getJoinExprList() {
		return joinExprList;
	}

	public void setJoinExprList(List<JoinExpression> joinExprList) {
		this.joinExprList = joinExprList;
	}

	public List<ListExpression> getListExprList() {
		return listExprList;
	}

	public void setListExprList(List<ListExpression> listExprList) {
		this.listExprList = listExprList;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

}
