package tom.test.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.util.logging.resources.logging;
import tom.cocook.core.RequestContext;
import tom.db.jdbc.simple.DBUtil;

public class BaseService {
	
	public int getDayFund(Integer pay,String unit){
		int dPay = 0 ;
		int dnum = 0 ;
		if("day".equals(unit)){
			dnum = 1;
		}else if("month".equals(unit)){
			dnum = getTheMonthDays();
		}else if("year".equals(unit)){
			dnum = getTheYearDays();
		}
		dPay =  pay * 10 / dnum;
		dPay = dPay%10 >= 5 ? dPay/10 + 1 : dPay / 10;
		return dPay;
	}
	
	public int getTheMonthDays(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.add(Calendar.MONTH,1);
		cal.add(Calendar.DAY_OF_MONTH,-1);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getTheYearDays(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,2);
		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.add(Calendar.DAY_OF_MONTH,-1);
		return 365 + cal.get(Calendar.DAY_OF_MONTH) - 28 ;
	}
	
	/**
	 * 分页
	 * @param sql
	 * @param reqData
	 * @param obj
	 * @return
	 */
	public RespData<List<Map<String, Object>>> page(String sql, ReqData reqData, Object... obj) {
		RespData<List<Map<String, Object>>> respData = new RespData<List<Map<String, Object>>>();
		respData.setPagenumber(reqData.getPage());
		int cnt = DBUtil.getInt("SELECT COUNT(*) FROM (" + sql + ")CNT", obj);
		respData.setTotal(cnt);
		if(cnt==0){
			respData.setTotal(0);
			respData.setRows(Collections.EMPTY_LIST);
			return respData;
		}
		int totalpage = cnt%reqData.getRows()==0 ? cnt/reqData.getRows() : (cnt/reqData.getRows())+1 ;
		respData.setTotalPage(totalpage);
		
		if (reqData.getSort() != null) {
			sql += " ORDER BY " + reqData.getSort() + " " + reqData.getOrder();
		}
		int start = (reqData.getPage() - 1) * reqData.getRows();
		respData.setRows(split(sql, start, reqData.getRows(), obj));
		return respData;
	}
	
	
	/**
	 * 分页
	 * @param sql
	 * @param reqData
	 * @param obj
	 * @return
	 */
	public Map<String, Object> page(String sql,Map<String,String> params, Object... obj) {
		Map<String, Object> respMap = new HashMap<String, Object>();
		Integer page = 1;
		Integer rows = 10;
		try {
			page = Integer.parseInt(params.get("page")==null?"1":params.get("page"));
			rows = Integer.parseInt(params.get("rows")==null?"10":params.get("rows"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		respMap.put("pagenumber", page);
		int cnt = DBUtil.getInt("SELECT COUNT(*) FROM (" + sql + ")CNT", obj);
		respMap.put("total", cnt);
		if(cnt==0){
			respMap.put("totalpage", 0);
			respMap.put("rows", Collections.EMPTY_LIST);
			return respMap;
		}
		int totalpage = cnt%rows==0 ? cnt/rows : (cnt/rows)+1 ;
		respMap.put("totalpage", totalpage);
		
		if (params.get("sort") != null) {
			sql += " ORDER BY " + params.get("sort") + " " + params.get("order");
		}
		int start = (page - 1) * rows;
		respMap.put("rows", split(sql, start, rows, obj));
		return respMap;
	}
	
	
	public List<Map<String, Object>> split(String sql, int start, int cnt, Object... obj){
		sql = sql+ " LIMIT "+cnt+" OFFSET " +start;
		return DBUtil.getList(sql, obj);
	}
	
	public Map<String, Object> getUser(){
		return (Map<String, Object>) RequestContext.get().getAttribute("user");
	}
	
	public Integer getUid(){
		Integer uid = null;
		try {
			uid = (Integer)((Map<String,Object>)RequestContext.get().getAttribute("user")).get("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uid;
	}
	
	public int getUserId(){
		return (int) getUser().get("id");
	}
	
	
	public Map<String, Object> error(String msg, Object... obj){
		HashMap<String , Object> error = new HashMap<>();
		error.put("msg", msg);
		error.put("state", "001");
		if(obj!=null && obj.length>0){
			error.put("data", obj[0]);
		}
		return error;
	}
	
	
	public Map<String, Object>  correct(String msg, Object... obj){
		HashMap<String , Object> correct = new HashMap<>();
		correct.put("msg", msg);
		correct.put("state", "000");
		if(obj!=null && obj.length>0){
			correct.put("data", obj[0]);
		}
		return correct;
	}
	
	/**
	 * 判断必须参数是否齐全
	 * @param params
	 * @param arr
	 * @return
	 */
	public boolean isParFull(Map<String, String> params,String ... arr){
		if(arr.length>0){
			for(String k:arr){
				String v = params.get(k);
				if(v==null||v.length()==0){
					System.out.println("参数错误: "+ k);
					return false;
				}
					
			}
		}
		return true;
	}
	
	
	public static class ReqData{

		private int page = 1; // 页码
		private int rows = 20;// 每页行数
		private String sort = null; //排序字段
		private String order = null; // 排序方式

		public int getPage() {
			return page;
		}

		public void setPage(int page) {
			this.page = page;
		}

		public int getRows() {
			return rows;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public String getOrder() {
			return order;
		}

		public void setOrder(String order) {
			this.order = order;
		}

	}

	
	public static class RespData<E> {

		private E rows;
		private int totalPage;
		private int total;
		private int pagenumber;

		public E getRows() {
			return this.rows;
		}

		public void setRows(E rows) {
			this.rows = rows;
		}

		public int getTotalPage() {
			return totalPage;
		}

		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}

		public int getPagenumber() {
			return pagenumber;
		}

		public void setPagenumber(int pagenumber) {
			this.pagenumber = pagenumber;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

	}
	
}
