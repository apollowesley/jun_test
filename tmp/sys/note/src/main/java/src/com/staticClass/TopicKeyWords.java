/**
 * 
 * @auther wuwang
 * @createTime 2014-4-30 下午10:31:29
 */
package com.staticClass;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hibernate.voDao.BillsTopic;
import com.hibernate.voDao.BillsTopicDAO;

/**
 * 关于话题功能，数据库中存储的没有改变，只是在前台显示的时候，添加一个<a>标签的链接，也就是一个模糊搜索的功能。 对于用户提交的数据，也需要检测，
 * 如果有新话题，则需要创建。 对于已经存在的话题，需要修改其使用次数和最后使用时间。
 * 
 * @author peaches
 * 
 */
public class TopicKeyWords {
	/**
	 * 用于给 #话题# 文字加链接，此方法接收一个 String 参数
	 * 
	 * @param String
	 * @return String
	 * @createTime 2014-4-30 下午10:31:48
	 */
	public static String addUrlLink(String str) {
		return str;
	}

	/**
	 * 用于给 #话题# 文字加链接，此方法接收一个 List 参数
	 * 
	 * @param List
	 * @return List
	 * @throws UnsupportedEncodingException
	 * @createTime 2014-4-30 下午10:31:48
	 */
	public static List addUrlLink(List list) throws UnsupportedEncodingException {
		int y = list.size();
		if (y > 0) {
			Map map = (Map) list.get(0);
			Set mapkey = map.keySet();
			BillsTopicDAO billsTopicDao = new BillsTopicDAO();
			List Tlist = billsTopicDao.findAllName();// 所有的话题
			String[] btn = parseString(Tlist);// Bills Topic Name
			for (int i = 0; i < y; i++) {
				map = (Map) list.get(i);
				String z = map.get("bcaption").toString();
				for (int j = 0; j < btn.length; j++) {
					z = z.replace(btn[j],
							"<a style=color:#9475ff href=Bills.yy?a=searchTopic&topicname=" + java.net.URLEncoder.encode(java.net.URLEncoder.encode(btn[j], "UTF-8"), "UTF-8") + ">"
									+ btn[j] + "</a>");
				}
				map.put("bcaption", z);
				list.set(i, map);
			}
		}
		return list;
	}

	/**
	 * 检查话题是否存在，不存在则添加，存在则更新
	 * 
	 * @param String
	 * @createTime 2014-4-30 下午11:06:06
	 */
	public static void checkTopic(String str) {
		/*
		 * 先匹配查找是否包含话题 #话题#
		 * 如果没有，直接返回，如果有，先用数据库的遍历匹配，如果成功，则更新，如果没有，则添加。每匹配到一个则删除一个，避免下次出错。
		 */
		while (!str.equals("end")) {
			str = checkTopicSub(str);
		}
	}

	/**
	 * checkTopic方法的子匹配查询，每一次都删除上一次匹配成功的字符串，直到匹配不到
	 * 
	 * @param str
	 * @createTime 2014-5-1 上午3:32:46
	 */
	public static String checkTopicSub(String str) {
		/*
		 * 先匹配查找是否包含话题 #话题#
		 * 如果没有，直接返回，如果有，先用数据库的遍历匹配，如果成功，则更新，如果没有，则添加。每匹配到一个则删除一个，避免下次出错。
		 */

		String regex = "#.*?#";
		String candidate = str;

		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(candidate);
		String g = "";
		if (matcher.find()) {
			g = matcher.group(0);

			BillsTopicDAO billsTopicDao = new BillsTopicDAO();
			List Tlist = billsTopicDao.findAllName();// 所有的话题
			String[] btn = parseString(Tlist);// Bills Topic Name
			for (int j = 0; j < btn.length; j++) {
				if (g.equals(btn[j])) {// 如果已经有这个话题了
					updateTopic(btn[j]);
					str = str.replace(g, "");// 删除
					return str;
				}
			}
			// 如果没有这个话题了
			addTopic(g);
			str = str.replace(g, "");// 删除
			return str;
		}
		// 如果匹配不成功，则返回 "end"，标记失败。
		str = "end";
		return str;
	}

	/**
	 * 向数据库中添加 新话题
	 * 
	 * @createTime 2014-4-30 下午10:42:40
	 */
	public static void addTopic(String topic) {
		BillsTopicDAO billsTopicDao = new BillsTopicDAO();
		BillsTopic billsTopic = new BillsTopic();
		billsTopic.setBtpname(topic);
		billsTopic.setBtptime(new java.util.Date());
		billsTopic.setBtplasttime(new java.util.Date());
		billsTopic.setTopiccount(1);
		billsTopicDao.save(billsTopic);
	}

	/**
	 * 更新话题最后使用时间和使用次数
	 * 
	 * @createTime 2014-4-30 下午10:43:20
	 */
	public static void updateTopic(String topic) {
		BillsTopicDAO billsTopicDao = new BillsTopicDAO();
		BillsTopic billsTopic = billsTopicDao.findByName(topic);
		billsTopic.setBtplasttime(new java.util.Date());
		billsTopic.setTopiccount(billsTopic.getTopiccount() + 1);
		billsTopicDao.save(billsTopic);
	}

	/**
	 * 把list转成，只有值的Bills Topic Name字段的字符数组
	 * 
	 * @param list
	 * @return String[]
	 * @createTime 2014-4-30 下午11:26:43
	 */
	public static String[] parseString(List list) {
		int y = list.size();
		String result[] = new String[y];
		if (y > 0) {
			Map map = (Map) list.get(0);
			Set mapkey = map.keySet();
			Object[] mapkeyA = mapkey.toArray();
			for (int i = 0; i < y; i++) {
				map = (Map) list.get(i);
				result[i] = map.get("btpname").toString();

			}
		}
		return result;
	}
}
