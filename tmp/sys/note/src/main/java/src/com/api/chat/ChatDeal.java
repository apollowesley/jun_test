/**
 * 
 * @auther wuwang
 * @createTime 2014-12-21 上午3:58:34
 */
package com.api.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.globalInterface.RequestParameter2;
import com.hibernate.voDao.ChatHistory;
import com.hibernate.voDao.ChatHistoryDAO;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;
import com.staticClass.Const;
import com.staticClass.DateUtil;
import com.staticClass.JsonUtils;
import com.uploadimage.FileUpload;
import com.weiwo.exception.BeanException;
import com.weiwo.jpush.PushMessage;

/**
 * 
 * 
 * @author peaches
 */

public class ChatDeal extends RequestParameter2 {
	private ChatHistory chatHistory;
	private ChatHistoryDAO chatHistoryDao;
	private UsersDAO usersDao;

	public ChatHistory getChatHistory() {
		return chatHistory;
	}

	public void setChatHistory(ChatHistory chatHistory) {
		this.chatHistory = chatHistory;
	}

	public ChatHistoryDAO getChatHistoryDao() {
		return chatHistoryDao;
	}

	public void setChatHistoryDao(ChatHistoryDAO chatHistoryDao) {
		this.chatHistoryDao = chatHistoryDao;
	}

	public UsersDAO getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDAO usersDao) {
		this.usersDao = usersDao;
	}

	@Override
	public void initClass() {
		setChatHistory(new ChatHistory());
		setChatHistoryDao(new ChatHistoryDAO());
		setUsersDao(new UsersDAO());
	}

	public String sendMessageToFriend() {
		try {
			String uid1 = getParameter("uid1");// 自己。。。
			String uid2 = getParameter("uid2");
			String date = getParameter("date");
			String message = getParameter("message");
			Date d = new Date();
			if ("999".equals(uid2)) {
				return sendMessageRobot(uid1, uid2, date, message, d);
			} else {
				return sendMessageNormal(uid1, uid2, date, message, d);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	/**
	 * 用户之间发送消息
	 * 
	 * @param uid1
	 * @param uid2
	 * @param date
	 * @param message
	 * @param d
	 * @return
	 * @throws BeanException
	 * @createTime 2015-3-14 下午9:42:27
	 */
	private String sendMessageNormal(String uid1, String uid2, String date, String message, Date d) throws BeanException {
		saveMessage(uid1, uid2, date, message, d);

		Users user1 = getUsersDao().findById(Integer.valueOf(uid1));
		Users user2 = getUsersDao().findById(Integer.valueOf(uid2));
		HashMap<String, String> extra = new HashMap<String, String>();
		extra.put("uid", uid1);
		extra.put("username", user1.getUsername());
		new PushMessage().sendPush(user1.getUsername() + "  发消息给你  ", Const.subString(message, 18), user2.getjPushId(), extra, PushMessage.MessageAction.CHATMESSAGE.toString());

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("messageDatetime", DateUtil.format(d));

		return JsonUtils.parseObjectFromObject(map).toString();
	}

	/**
	 * 保存消息到数据库
	 * 
	 * @param uid1
	 * @param uid2
	 * @param date
	 * @param message
	 * @param d
	 * @throws BeanException
	 * @createTime 2015-3-14 下午9:42:13
	 */
	@SuppressWarnings("unused")
	private void saveMessage(String uid1, String uid2, String date, String message, Date d) throws BeanException {

		if (Const.stringIsEmpty(message)) {
			throw new BeanException("消息内容为空");
		}
		setChatHistory(new ChatHistory());
		getChatHistory().setUidSend(Integer.valueOf(uid1));
		getChatHistory().setUidReceive(Integer.valueOf(uid2));

		// Date d = new Date();// 都使用服务器时间
		date = null;
		if (date == null) {
			getChatHistory().setMessageDatetime(d);
			getChatHistory().setMessageTimestamp(d.getTime());
		} else {
			// Date d = DateUtil.parse(date);
			getChatHistory().setMessageDatetime(d);
			getChatHistory().setMessageTimestamp(d.getTime());
		}

		getChatHistory().setMessageContent(message);
		getChatHistoryDao().save(getChatHistory());

	}

	/**
	 * 给机器人发消息
	 * 
	 * @param uid1
	 * @param uid2
	 * @param date
	 * @param message
	 * @param d
	 * @return
	 * @throws BeanException
	 * @createTime 2015-3-14 下午9:42:50
	 */
	private String sendMessageRobot(final String uid1, final String uid2, String date, final String message, Date d) throws BeanException {
		saveMessage(uid1, uid2, date, message, d);

		new Thread(new Runnable() {

			@Override
			public void run() {
				/** 从机器人获取消息，后我推送消息 **/
				try {
					String robotReplyMessage = ChatDealRobot.getRobotMessage(message, uid1);

					System.out.println(robotReplyMessage);
					// 保存记录
					String insertSql = "insert into tuling_robot(send_message,receive_message,uid) values (?,?,?)";
					generalDao.executeUpdate(insertSql, message, robotReplyMessage, uid1);
					// 保存聊天信息
					JSONObject robotReplyJson = JSONObject.fromObject(robotReplyMessage);

					Users user1 = getUsersDao().findById(Integer.valueOf(uid1));
					Users user2 = getUsersDao().findById(Integer.valueOf(uid2));
					Date robotDate = new Date();
					String result = ChatDealRobot.dealRobotMessage(robotReplyJson);

					saveMessage(uid2, uid1, null, result, robotDate);
					HashMap<String, String> extra = new HashMap<String, String>();
					extra.put("uid", uid2);
					extra.put("username", user2.getUsername());

					new PushMessage().sendPush(user2.getUsername() + "  发消息给你  ", Const.subString(result, 18), user1.getjPushId(), extra,
							PushMessage.MessageAction.CHATMESSAGE.toString());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("messageDatetime", DateUtil.format(d));

		return JsonUtils.parseObjectFromObject(map).toString();
	}

	public String getHistoryMessage() {
		String uid1 = getParameter("uid1");
		String uid2 = getParameter("uid2");
		String startDate = getParameter("startDate");
		System.out.println(startDate);
		try {
			startDate = String.valueOf(DateUtil.parse(startDate).getTime());
		} catch (Exception e) {

		}
		String direction = getParameter("direction");
		List list = getChatHistoryDao().getChatHistoryByUsers(uid1, uid2, startDate, Boolean.valueOf(direction));
		/** mysql查询结果有些问题 出现一个奇怪的对象 [B@2bf664ef 估计是二进制对象吧。可以在sql中使用cast转换为字符串 **/
		return JsonUtils.parseArrayFromObject(list, 3).toString();
	}

	/**
	 * 上传图片，只能上传1张，聊天图片
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-5-28 下午10:38:47
	 */
	public String uploadChatImage() throws ServletException, IOException {
		try {
			ServletInputStream in = request.getInputStream();
			FileUpload upload1 = new FileUpload();
			upload1.setInputStream(in);
			/*--------这三个的顺序不能变 开始---------*/
			String sessionid = upload1.getParameter();
			String uid = upload1.getParameter();
			String uname = java.net.URLDecoder.decode(upload1.getParameter(), "utf8");
			/*--------这三个的顺序不能变 结束---------*/

			String filename = "image/chat_images/" + upload1.getFileUpload(uid, uname, "image/chat_images/");
			String message = "picture:{\"filepath\":\"" + filename + "\"}";// 固定格式

			String params = java.net.URLDecoder.decode(upload1.getParameter(), "utf8");
			JSONObject json = JSONObject.fromObject(params);

			String uid1 = json.getString("uid1");
			String uid2 = json.getString("uid2");

			getChatHistory().setUidSend(Integer.valueOf(uid1));
			getChatHistory().setUidReceive(Integer.valueOf(uid2));

			Date d = new Date();// 都使用服务器时间
			getChatHistory().setMessageDatetime(d);
			getChatHistory().setMessageTimestamp(d.getTime());

			getChatHistory().setMessageContent(message);
			getChatHistoryDao().save(getChatHistory());

			Users user1 = getUsersDao().findById(Integer.valueOf(uid1));
			Users user2 = getUsersDao().findById(Integer.valueOf(uid2));
			HashMap<String, String> extra = new HashMap<String, String>();
			extra.put("uid", uid1);
			extra.put("username", user1.getUsername());
			new PushMessage().sendPush(user1.getUsername() + "  发消息给你  ", "图片", user2.getjPushId(), extra, PushMessage.MessageAction.CHATMESSAGE.toString());

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("messageDatetime", DateUtil.format(d));
			map.put("filepath", message);

			return JsonUtils.parseObjectFromObject(map).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

	public static class ChatDealRobot {
		/**
		 * 处理机器人返回消息
		 * 
		 * @param jsonArray
		 * @return
		 * @createTime 2015-3-22 下午12:07:14
		 */
		public static String dealRobotMessage(JSONArray jsonArray) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < jsonArray.size(); i++) {
				Object value = jsonArray.get(i);
				if (value instanceof String) {
					sb.append(dealRobotMessage((String) value) + "\n");
				} else if (value instanceof JSONArray) {
					sb.append(dealRobotMessage((JSONArray) value) + "\n");
				} else if (value instanceof JSONObject) {
					sb.append(dealRobotMessage((JSONObject) value));
				}
			}
			return sb.toString();
		}

		/**
		 * 处理机器人返回消息
		 * 
		 * @param jsonObject
		 * @return
		 * @createTime 2015-3-22 下午12:07:14
		 */
		public static String dealRobotMessage(JSONObject jsonObject) {
			StringBuilder sb = new StringBuilder();
			Set<String> keySet = jsonObject.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object value = jsonObject.get(key);
				if (value instanceof String) {
					sb.append(dealRobotMessage((String) value) + "\n");
				} else if (value instanceof JSONArray) {
					sb.append(dealRobotMessage((JSONArray) value) + "\n");
				} else if (value instanceof JSONObject) {
					sb.append(dealRobotMessage((JSONObject) value));
				}
			}
			return sb.toString();
		}

		/**
		 * 处理机器人返回消息
		 * 
		 * @param string
		 * @return
		 * @createTime 2015-3-22 下午1:28:49
		 */
		public static String dealRobotMessage(String string) {
			Pattern p = Pattern.compile("<cd.url.*?>", Pattern.CASE_INSENSITIVE);
			Pattern pu = Pattern.compile("@.*?>", Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(string);
			while (m.find()) {
				String oldStr = m.group();
				String newStr = "http://";
				Matcher mu = pu.matcher(oldStr);
				while (mu.find()) {
					try {
						String s = mu.group();
						newStr += s.substring(1, s.length() - 1);
					} catch (Exception e) {
						e.printStackTrace();
						newStr = "";
					}
				}
				string = string.replace(oldStr, newStr);
			}
			return string;
		}

		public static String APIKEY = "987bfeaaf56ffcdf256808e89391565e";

		/**
		 * 调用图灵机器人平台接口
		 * 
		 * @userId uid1参数也许是为了上下文的作用
		 * 
		 * @throws IOException
		 */
		public static String getRobotMessage(String INFO, String uid1) throws IOException {

			// String INFO = URLEncoder.encode("北京今日天气", "utf-8");
			INFO = URLEncoder.encode(INFO, "utf-8");
			String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO + "&userid=" + uid1;
			System.out.println(getURL);
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.connect();

			// 取得输入流，并使用Reader读取
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			return sb.toString();
		}

	}

}
