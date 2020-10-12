/**
 * 
 * @auther wuwang
 * @createTime 2015-1-24 下午11:33:40
 */
package com.api.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONObject;

import com.api.PortalReal;
import com.globalInterface.RequestParameter2;
import com.hibernate.voDao.BillsDAO;
import com.staticClass.MapToJson;

/**
 * 
 * 
 * @author peaches
 */
public class DynamicAction extends RequestParameter2 {

	@Override
	public void initClass() {

	}

	/**
	 * 获取最新回复
	 * 
	 * @return
	 * @createTime 2015-4-21 上午12:18:35
	 */
	public String latestReply() {
		/** 如果有bid参数，则只获取一条，否则用num参数获取大于或者小于的一个范围 **/
		String bids = getParameter("bids");
		String num = getParameter("num");
		String date;
		try {
			date = URLDecoder.decode(getParameter("date"), "utf-8");
		} catch (Exception e) {
			date = "";
			e.printStackTrace();
		}
		num = num == null ? "0" : num;
		String directionStr = getParameter("direction");
		boolean direction = "true".equals(directionStr) ? true : false;

		String UOID = getParameter("UOID");
		BillsDAO billsDao = new BillsDAO();
		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);

		/** 显示分页数据 获取最新回复的5条 */
		List list = billsDao.findByPropertyPaging(0, 5, "0", currentdatems1 + " 23:59:59", UOID, bids, num, date, direction);
		PortalReal.dealMultiImage(list);

		String result = MapToJson.parseJsonAndroid(list);
		return JSONObject.fromObject(result).getString("type");
	}

}
