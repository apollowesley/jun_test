/**
 * 
 * @auther wuwang
 * @createTime 2015-1-24 下午11:33:40
 */
package com.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import com.api.PortalReal;
import com.globalInterface.RequestParameter2;
import com.hibernate.voDao.BillsDAO;
import com.staticClass.Const;
import com.staticClass.JsonUtils;
import com.staticClass.MapToJson;

/**
 * 收藏
 * 
 * 
 * @author peaches
 */
public class CollectionAction extends RequestParameter2 {
	private BillsDAO billsDao;

	@Override
	public void initClass() {
		billsDao = new BillsDAO();
	}

	public String collection() {
		HashMap<String, Comparable<?>> map = new HashMap<String, Comparable<?>>();
		String uid = getParameter("UOID");// 主角
		String bid = getParameter("bid");// 收藏的记录
		try {
			String sql = "insert into bills_collection(uid,bid,collection_datetime) values(?,?,now())";
			generalDao.executeUpdate(sql, uid, bid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("success", true);
		return JsonUtils.parseObjectFromObject(map).toString();
	}

	public String unCollection() {

		HashMap<String, Comparable<?>> map = new HashMap<String, Comparable<?>>();
		String uid = getParameter("UOID");// 主角
		String bid = getParameter("bid");// 不收藏的记录
		try {
			String sql = "delete from bills_collection where bid=? and uid=?";
			generalDao.executeUpdate(sql, bid, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("success", true);
		map.put("bid", bid);
		return JsonUtils.parseObjectFromObject(map).toString();
	}

	public String getUserCollection() {

		HashMap<String, Comparable<?>> map = new HashMap<String, Comparable<?>>();
		String uid = getParameter("UOID");// 主角
		List listBills = null;
		try {
			int pagNum = Integer.parseInt(getParameter("pagNum"));// 页数
			int pageCount = Integer.parseInt(getParameter("pageCount"));// 每页条数
			int hasGetCount = pagNum * pageCount;
			String sql = "select id,uid,bid from bills_collection where uid=? order by collection_datetime desc limit " + String.valueOf(hasGetCount) + ","
					+ String.valueOf(pageCount);
			List<Object[]> list = generalDao.getList(sql, uid);
			if (Const.ListIsNotBlank(list)) {
				String bids = "";
				for (Object[] objs : list) {
					bids += objs[2] + ",";
				}
				listBills = billsDao.findByPropertyPaging(bids);
				PortalReal.dealMultiImage(listBills);
			} else {
				listBills = new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			listBills = new ArrayList<>();
		}
		String result = MapToJson.parseJsonAndroid(listBills);
		return JSONObject.fromObject(result).getString("type");
	}
}
