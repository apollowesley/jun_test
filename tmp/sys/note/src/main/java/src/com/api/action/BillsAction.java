/**
 * 
 * @auther wuwang
 * @createTime 2015-1-24 下午11:33:40
 */
package com.api.action;

import java.util.HashMap;
import java.util.List;

import com.globalInterface.RequestParameter2;
import com.staticClass.JsonUtils;

/**
 * 
 * 
 * @author peaches
 */
public class BillsAction extends RequestParameter2 {

	@Override
	public void initClass() {

	}

	public String like() {
		HashMap<String, Comparable<?>> map = new HashMap<String, Comparable<?>>();
		String uid = getParameter("UOID");// 主角
		String bid = getParameter("bid");// 喜欢的记录
		try {
			String sql = "insert into bills_like(bid,uid,datetime) values(?,?,now())";
			generalDao.executeUpdate(sql, bid, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "select like_count from bills where bid=?";
		List likeCount = generalDao.getList(sql, bid);
		map.put("success", true);
		map.put("like_bid", bid);
		map.put("like_count", likeCount.get(0).toString());
		map.put("uid", uid);
		return JsonUtils.parseObjectFromObject(map).toString();
	}

	public String unLike() {

		HashMap<String, Comparable<?>> map = new HashMap<String, Comparable<?>>();
		String uid = getParameter("UOID");// 主角
		String bid = getParameter("bid");// 不喜欢的记录
		try {
			String sql = "delete from bills_like where bid=? and uid=?";
			generalDao.executeUpdate(sql, bid, uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "select like_count from bills where bid=?";
		List likeCount = generalDao.getList(sql, bid);
		map.put("success", true);
		map.put("like_bid", bid);
		map.put("like_count", likeCount.get(0).toString());
		map.put("uid", uid);
		return JsonUtils.parseObjectFromObject(map).toString();
	}

	public String likeCount() {

		HashMap<String, Comparable<?>> map = new HashMap<String, Comparable<?>>();
		String uid = getParameter("UOID");// 主角
		String bid = getParameter("bid");// 记录

		String sql = "select like_count from bills where bid=?";
		List likeCount = generalDao.getList(sql, bid);
		map.put("success", true);
		map.put("like_bid", bid);
		map.put("like_count", likeCount.get(0).toString());
		map.put("uid", uid);
		return JsonUtils.parseObjectFromObject(map).toString();
	}

}
