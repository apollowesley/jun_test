package cc.yihy.utils.domain;


/**
 * 
 * @ClassName:ResultObj.java
 * @Description:
 * @Author: yihy
 * @Since: 2016年8月29日
 * @Version: 1.0
 */
public class ResultObj<T> {
	// 搜索的排序条件
	String orderBy;

	// 全局搜索 对象 关系or
	T globalSearchObj;

	// 指定列搜索 and
	T columnsSearchObj;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public T getGlobalSearchObj() {
		return globalSearchObj;
	}

	public void setGlobalSearchObj(T globalSearchObj) {
		this.globalSearchObj = globalSearchObj;
	}

	public T getColumnsSearchObj() {
		return columnsSearchObj;
	}

	public void setColumnsSearchObj(T columnsSearchObj) {
		this.columnsSearchObj = columnsSearchObj;
	}

}
