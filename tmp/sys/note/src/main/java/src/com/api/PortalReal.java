/**
 * 
 * @auther wuwang
 * @createTime 2014-5-24 下午9:50:53
 */
package com.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.sso.Login;
import com.avatarUpload.GenerateImg;
import com.avatarUpload.ImageRequest;
import com.globalInterface.RequestParameter2;
import com.hibernate.voDao.Bills;
import com.hibernate.voDao.BillsDAO;
import com.hibernate.voDao.BillsImages;
import com.hibernate.voDao.BillsImagesDAO;
import com.hibernate.voDao.BillsTopicDAO;
import com.hibernate.voDao.BillsTypeDAO;
import com.hibernate.voDao.CatchException;
import com.hibernate.voDao.CatchExceptionDAO;
import com.hibernate.voDao.Feedbacks;
import com.hibernate.voDao.FeedbacksDAO;
import com.hibernate.voDao.JoinAsAPartner;
import com.hibernate.voDao.JoinAsAPartnerDAO;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;
import com.hibernate.voDao.Version;
import com.hibernate.voDao.VersionDAO;
import com.javasrc.AutoGeneratePassword;
import com.javasrc.CipherUtil;
import com.javasrc.sendEmail;
import com.javasrc.service.FriendsService;
import com.javasrc.service.MessageService;
import com.listener.MySessionContext;
import com.staticClass.Const;
import com.staticClass.JsonUtils;
import com.staticClass.MapToJson;
import com.staticClass.NetworkUtil;
import com.staticClass.TopicKeyWords;
import com.uploadimage.FileUpload;
import com.weiwo.jpush.PushMessage;

/**
 * 
 * 
 * @author peaches
 */
public class PortalReal extends RequestParameter2 {
	private static final Logger log = LoggerFactory.getLogger(PortalReal.class);
	/** 使用时替换掉message **/
	private String SUCCESS = "{\"realSucess\":\"true\",\"success\":\"true\",\"code\":\"200\",\"msg\":\"message\"}";
	private String FAIl = "{\"realSucess\":\"false\",\"success\":\"true\",\"code\":\"200\",\"msg\":\"message\"}";
	private UsersDAO usersDao;
	private BillsDAO billsDao;
	private VersionDAO versionDao;
	private Bills bill;
	private Users users;
	private Version version;
	private BillsTypeDAO bills_typeDao;

	private JoinAsAPartnerDAO joinAsAPartnerDao;
	private JoinAsAPartner joinAsAPartner;
	private FriendsService friendsService;
	private MessageService messageService;

	public UsersDAO getUsersDao() {
		if (usersDao == null) {
			usersDao = new UsersDAO();
		}
		return usersDao;
	}

	public void setUsersDao(UsersDAO usersDao) {
		this.usersDao = usersDao;
	}

	public BillsDAO getBillsDao() {
		if (billsDao == null) {
			billsDao = new BillsDAO();
		}
		return billsDao;
	}

	public void setBillsDao(BillsDAO billsDao) {
		this.billsDao = billsDao;
	}

	public VersionDAO getVersionDao() {
		if (versionDao == null) {
			versionDao = new VersionDAO();
		}
		return versionDao;
	}

	public void setVersionDao(VersionDAO versionDao) {
		this.versionDao = versionDao;
	}

	public BillsTypeDAO getBills_typeDao() {
		if (bills_typeDao == null) {
			bills_typeDao = new BillsTypeDAO();
		}
		return bills_typeDao;
	}

	public void setBills_typeDao(BillsTypeDAO bills_typeDao) {
		this.bills_typeDao = bills_typeDao;
	}

	public JoinAsAPartnerDAO getJoinAsAPartnerDao() {
		if (joinAsAPartnerDao == null) {
			joinAsAPartnerDao = new JoinAsAPartnerDAO();
		}
		return joinAsAPartnerDao;
	}

	public void setJoinAsAPartnerDao(JoinAsAPartnerDAO joinAsAPartnerDao) {
		this.joinAsAPartnerDao = joinAsAPartnerDao;
	}

	public Bills getBill() {
		if (bill == null) {
			bill = new Bills();
		}
		return bill;
	}

	public void setBill(Bills bill) {
		this.bill = bill;
	}

	public Users getUsers() {
		if (users == null) {
			users = new Users();
		}
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Version getVersion() {
		if (version == null) {
			version = new Version();
		}
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public JoinAsAPartner getJoinAsAPartner() {
		if (joinAsAPartner == null) {
			joinAsAPartner = new JoinAsAPartner();
		}
		return joinAsAPartner;
	}

	public void setJoinAsAPartner(JoinAsAPartner joinAsAPartner) {
		this.joinAsAPartner = joinAsAPartner;
	}

	public FriendsService getFriendsService() {
		if (friendsService == null) {
			friendsService = new FriendsService();
		}
		return friendsService;
	}

	public void setFriendsService(FriendsService friendsService) {
		this.friendsService = friendsService;
	}

	public MessageService getMessageService() {
		if (messageService == null) {
			messageService = new MessageService();
		}
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * Constructor of the object.
	 */
	public PortalReal() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			super.service(request, response);
			doPortal();

		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsono = new JSONObject();
			PrintWriter out = response.getWriter();
			jsono.put("code", "400");
			jsono.put("status", "false");
			out.write(jsono.toJSONString());
			out.close();
			return;
		}
	}

	/**
	 * 处理所有请求
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-5-24 下午9:52:35
	 */
	public void doPortal() throws ServletException, IOException {
		String a = getParameter("a");
		a = a == null ? "1" : a;
		log.info("a=" + a);
		if (a.equals("9")) {// 上传图片，以二进制流上传的表单，不能用常规方法获取参数，单独验证
			doUploadImage();
			return;
		}
		if (a.equals("15")) {// 上传图片，以二进制流上传的表单，不能用常规方法获取参数，单独验证
			uploadAvatar();
			return;
		}
		if (a.equals("10")) {// 检查更新，不需要验证
			checkUpdate();
			return;
		}
		if (a.equals("8")) {// 注册功能，不需要验证
			doRegister();
			return;
		}
		if (a.equals("99")) {// 获取下载APP的地址，服务器最大存7个
			getDownloadUrl();
			return;
		}
		if (a.equals("100")) {// 存储App传来的异常信息
			saveException();
			return;
		}
		if (a.equals("29")) {// 获取一起走详细内容
			doGetJoinUsDetail();
			return;
		}
		if (a.equals("98")) {// 保存反馈
			saveFeedback();
			return;
		}
		if (a.equals("97")) {// 获取反馈页面
			getFeedbackPage();
			return;
		}
		if (a.equals("41")) {// QQ登录用户，注册和登录
			QQLogin();
			return;
		}
		if (a.equals("103")) {// 找回密码
			doForgetPassword();
			return;
		}
		if (a.equals("1")) {// 登录
			doLogin();
			return;
		} else if (!this.sessionValide()) {// 验证用户
			// 这里什么也不做，只是为了验证session的，请不要在这里修改，如果有需要，请在sessionValide()中修改。
			return;
		} else if (a.equals("2")) {// 获取自己的记录
			doGetData();
		} else if (a.equals("3")) {// 发表心情记事
			doAddMood();
		} else if (a.equals("4")) {// 发表记账记事
			doAddBill();
		} else if (a.equals("5")) {// 摇一摇功能——随机获取一条数据
			getRandomData();
		} else if (a.equals("6")) {// 删除记录
			delete();
		} else if (a.equals("7")) {// 获取用户信息
			getPersonalData();
		} else if (a.equals("8")) {// 用户注册
			doRegister();
		} else if (a.equals("9")) {// 上传图片
			doUploadImage();
		} else if (a.equals("10")) {// 检测新版本
			checkUpdate();
		} else if (a.equals("11")) {// 获取记账类型
			getBtype();
		} else if (a.equals("12")) {// 获取行程列表
			getBtypes();
		} else if (a.equals("13")) {// 回复
			doAddReply();
		} else if (a.equals("14")) {// 获取回复
			getReplys();
		} else if (a.equals("15")) {// 更新头像
			uploadAvatar();
		} else if (a.equals("16")) {// 获取搭伙（一起走功能）
			doGetJoinUs();
		} else if (a.equals("17")) {// 加入搭伙（一起走功能）
			doJoin();
		} else if (a.equals("18")) {// 发起搭伙（一起走功能）
			doAddJoin();
		} else if (a.equals("19")) {// 删除搭伙（一起走功能）
			deleteJoin();
		} else if (a.equals("20")) {// 修改昵称
			modifyUsername();
		} else if (a.equals("21")) {// 修改邮箱
			modifyEmail();
		} else if (a.equals("22")) {// 修改电话号码
			modifyTel();
		} else if (a.equals("23")) {// 显示搭伙中申请的人（一起走功能）
			getJoins();
		} else if (a.equals("24")) {// 自己已经加入的（一起走功能）
			doGetJoinUsMine();
		} else if (a.equals("25")) {// 自己取消加入（一起走功能）

		} else if (a.equals("26")) {// 检查uid是否已经加入jaapid（一起走功能）
			checkUidJaapid();
		} else if (a.equals("27")) {// 获取最热的3个话题
			getThreeHotTopic();
		} else if (a.equals("28")) {// 看世界，seeworld//不存在的功能
			seeWorld();
		} else if (a.equals("30")) {// 获取好友列表
			getMyFriends();
		} else if (a.equals("31")) {// 查找人
			searchFriends();
		} else if (a.equals("32")) {// 申请添加好友
			addFriends();
		} else if (a.equals("33")) {// 确认添加好友
			confirmFriends();
		} else if (a.equals("34")) {// 删除好友
			deleteFriends();
		} else if (a.equals("35")) {// 获取消息
			getMyMessage();
		} else if (a.equals("36")) {// 拒绝申请好友
			refuseFriends();
		} else if (a.equals("37")) {// 修改密码
			doChangePassword();
		} else if (a.equals("43")) {// 已有账号绑定QQ登录
			binQQLogin();
		} else if (a.equals("101")) {// 处理二维码
			handlerQrCode();
		} else if (a.equals("102")) {// 更新用户的jPushId
			updateJPushId();
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @createTime 2014-12-15 下午8:38:00
	 */
	private void getFeedbackPage() throws ServletException, IOException {
		String ids = getParameter("jaapid");
		int id = 0;
		try {
			id = Integer.valueOf(ids);
		} catch (Exception e) {

		}
		setJoinAsAPartner(getJoinAsAPartnerDao().findById(id));
		getJoinAsAPartner().setJaapcontent(MapToJson.parseHtml(getJoinAsAPartner().getJaapcontent()));
		request.setAttribute("joinAsAPartner", joinAsAPartner);
		request.getRequestDispatcher("api/jsp/helpAndFeedback.jsp").forward(request, response);
	}

	/**
	 * 保存用户反馈信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-12-15 下午8:07:48
	 */
	private void saveFeedback() throws IOException {
		try {
			FeedbacksDAO feedbacksDao = new FeedbacksDAO();
			Feedbacks feedback = new Feedbacks();
			String feedbackc = getParameter("feedbackc");
			feedback.setUid((Integer) getSession().getAttribute("UOID"));
			feedback.setUsername((String) getSession().getAttribute("UName"));
			if (Const.stringIsEmpty(feedbackc)) {
				returnJson(FAIl.replace("message", "反馈内容不能为空"));
				return;
			}
			feedback.setContents(feedbackc);
			feedback.setrcontents("");
			feedback.setFdate(new java.util.Date());
			feedbacksDao.save(feedback);
			returnJson(SUCCESS.replace("message", "成功反馈给开发者\\n感谢您的支持"));
		} catch (Exception e) {
			log.info("feedback content is empty");
			returnJson(FAIl.replace("message", "成功反馈给开发者\\n感谢您的支持"));
		}
	}

	/**
	 * 更新用户的jPushId
	 * 
	 * @param request
	 * @param response
	 * @createTime 2014-12-14 上午1:11:47
	 */
	private void updateJPushId() {
		String jPushId = getParameter("jPushId");
		if (Const.stringIsEmpty(jPushId)) {

		} else {
			getUsersDao().updateJPushId(jPushId, getParameter("UOID"));
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-12-13 上午12:05:55
	 */
	private void handlerQrCode() throws IOException {
		JSONObject jsono = new JSONObject();

		String o = getParameter("o");
		if ("friends".equals(o)) {
			String myidentification = getParameter("myidentification");
			List<Users> list = getUsersDao().findByProperty("identification", myidentification);
			if (Const.ListIsNotBlank(list)) {
				JSONObject person = new JSONObject();
				setUsers(list.get(0));
				person.put("uid", getUsers().getUid());
				person.put("loginId", getUsers().getLoginid());
				person.put("username", getUsers().getUsername());
				String avatarfile = "/image/avatar/avatar_uid_" + getUsers().getUid() + ".jpg";
				File f = new File(getDir() + avatarfile);
				String avatarurl;
				if (f.exists()) {
					avatarurl = "http://" + getHost() + avatarfile;
				} else {
					avatarurl = "";
				}
				person.put("avatar", avatarurl);
				System.out.println(person.get("avatar"));
				jsono.put("result", person);
				jsono.put("code", "200");
				jsono.put("status", "success");
			} else {
				jsono.put("code", "200");
				jsono.put("status", "fail");
				jsono.put("msg", "查无此人");
			}
		} else {
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "");
		}
		returnJson(jsono.toJSONString());
	}

	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-8-16 上午4:58:12
	 */
	private void getPersonalData() throws ServletException, IOException {
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String UOID = getParameter("UOID");
		int uid = Integer.valueOf(getParameter("uid"));
		if (UOID != null) {
			setUsers(getUsersDao().findById(uid));
			jsono.put("result", JSONArray.fromObject(getUsers()));
			jsono.put("code", "200");
			jsono.put("status", "success");
		} else {
			jsono.put("code", "210");
			jsono.put("status", "error");
		}
		out.write(jsono.toJSONString());
		out.close();
	}

	/**
	 * 添加记事
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-5-25 下午4:38:43
	 */
	private void doAddMood() throws ServletException, IOException {
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String bcaption = getParameter("bcaption");
		TopicKeyWords.checkTopic(bcaption);
		String UOID = getParameter("UOID");
		String UName = getParameter("UName");

		BillsDAO billsDao = new BillsDAO();
		Bills bill = new Bills();

		String webpicPath = getParameter("webpicPath");

		if (!Const.stringIsEmpty(webpicPath)) {
			String[] ss = webpicPath.split("\\|");// 后台支持上传多张图片
			for (int i = 0; i < ss.length; i++) {
				if (Const.stringIsEmpty(ss[i])) {
					continue;
				}
				BillsImagesDAO billsImagesDao = new BillsImagesDAO();
				BillsImages billsImages = (BillsImages) billsImagesDao.findByBidir(ss[i]).get(0);
				bill.setBimageid(bill.getBimageid() + "," + billsImages.getBiid().toString());
			}
		}
		bill.setUid(Integer.parseInt(UOID));
		bill.setUsername(UName);
		bill.setBdate(new java.util.Date());
		bill.setBtype(28);
		bill.setBio(5);
		bill.setBamount(0);
		bill.setBcaption(bcaption);
		bill.setBctype(0);
		billsDao.save(bill);
		jsono.put("code", "200");
		jsono.put("status", "success");
		out.write(jsono.toJSONString());
		out.close();
	}

	/**
	 * 添加记账
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-5-25 下午4:38:58
	 */
	private void doAddBill() throws ServletException, IOException {
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String bcaption = getParameter("bcaption");
		String UOID = getParameter("UOID");
		String UName = getParameter("UName");

		TopicKeyWords.checkTopic(bcaption);

		Integer btype = Integer.parseInt(getParameter("btype"));// 这里前台是数字
		Integer bio = Integer.parseInt(getParameter("bio"));
		Double bamount = Double.parseDouble(getParameter("bamount"));
		String travel = getParameter("travel");// 是否是一次行程的子行程（是则为addtravel，否则为null）
		travel = travel == null ? "" : travel;
		BillsDAO billsDao = new BillsDAO();
		Bills bill = new Bills();
		bill.setUid(Integer.parseInt(UOID));
		bill.setUsername(UName);
		bill.setBdate(new java.util.Date());
		bill.setBtype(btype);
		bill.setBio(bio);
		bill.setBamount(bamount);
		bill.setBcaption(bcaption);

		String webpicPath = getParameter("webpicPath");

		System.out.println(webpicPath);
		if (!Const.stringIsEmpty(webpicPath)) {
			String[] ss = webpicPath.split("\\|");// 后台支持上传多张图片
			for (int i = 0; i < ss.length; i++) {
				System.out.println(bill.getBimageid());
				if (Const.stringIsEmpty(ss[i])) {
					continue;
				}
				BillsImagesDAO billsImagesDao = new BillsImagesDAO();
				BillsImages billsImages = (BillsImages) billsImagesDao.findByBidir(ss[i]).get(0);
				bill.setBimageid(bill.getBimageid() + "," + billsImages.getBiid().toString());
				System.out.println(bill.getBimageid());
			}
		}
		if (travel.equals("travel")) {
			String btid = getParameter("btid");// 是否是一次行程的子行程（是则为addtravel，否则为null）
			btid = btid == null ? "" : btid;
			bill.setBtid(Integer.parseInt(btid));
			bill.setBbetravelmember(true);
		}
		billsDao.save(bill);

		jsono.put("code", "200");
		jsono.put("status", "success");
		out.write(jsono.toJSONString());
		out.close();
	}

	/**
	 * 获取自己的记录
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-5-25 上午1:07:41
	 */
	private void doGetData() throws ServletException, IOException {
		/** 如果有bid参数，则只获取一条，否则用num参数获取大于或者小于的一个范围 **/
		String bid = getParameter("bid");
		String num = getParameter("num");
		String onlyOneUser = getParameter("onlyOneUser");// 是否只获取这一个人的
		num = num == null ? "0" : num;
		String directionStr = getParameter("direction");
		boolean direction = "true".equals(directionStr) ? true : false;

		String UOID = getParameter("UOID");
		BillsDAO billsDao = new BillsDAO();
		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);

		String parameter1 = "";
		String parameter2 = "";
		String parameter3 = "";
		if (StringUtils.equalsIgnoreCase("true", onlyOneUser)) {
			String onlyUid = Const.StringToString(getParameter("onlyUid"));// 只获取这一个人的
			parameter3 = "(uid='" + onlyUid + "') and ";
		} else {
			parameter3 = "(uid='" + UOID + "' or uid in(select user_id_1 from relationship_friends where user_id_2='" + UOID
					+ "' and status='2') or uid in(select user_id_2 from relationship_friends where user_id_1='" + UOID + "' and status='2')) and ";
		}
		if (Const.stringIsEmpty(bid)) {
			if (!num.equals("0")) {
				if (direction) {
					parameter3 += " bid >'" + num + "' and ";
				} else {
					parameter3 += " bid <'" + num + "' and ";
				}
			}
		} else {
			parameter3 += " bid ='" + bid + "' and ";
		}
		/** 显示分页数据 */
		List list = billsDao.findByPropertyPaging(parameter1, parameter2, parameter3, 0, 12, "0", currentdatems1 + " 23:59:59", UOID);
		dealMultiImage(list);

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		String result = MapToJson.parseJsonAndroid(list);
		jsono.put("code", "200");
		jsono.put("status", "success");
		jsono.put("result", result);
		out.write(jsono.toJSONString());
		out.close();
	}

	/**
	 * 处理多图片，在获取一条记事后传list参数处理
	 * 
	 * @param list
	 * @createTime 2015-2-1 上午12:57:31
	 */
	public static void dealMultiImage(List list) {
		/** 获取显示多图片 开始 **/
		if (Const.ListIsNotBlank(list)) {
			String sqlTemp = "select t.bidir,'%s' bid,t.biid  from bills_images t where t.biid in(%s) ";
			List listsql = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				String imageIds = "";
				imageIds = Const.StringToString(((Map) list.get(i)).get("bimageid"));
				if (Const.stringIsBlank(imageIds)) {
					continue;
				}
				imageIds = "'" + imageIds.replace(",", "','") + "'";
				String sql = String.format(sqlTemp, ((Map) list.get(i)).get("bid"), imageIds);
				listsql.add(sql);
			}
			String sql = Const.ArraysToString(listsql.toArray(), " union ");
			if (Const.stringIsBlank(sql)) {
				return;
			}
			List listImages = generalDao.getList("select * from (" + sql + ") a order by biid ");
			if (Const.ListIsNotBlank(listImages)) {
				for (int i = 0; i < list.size(); i++) {
					String bidt = ((Map) list.get(i)).get("bid").toString();
					String value = "";
					String values = "";
					for (int j = 0; j < listImages.size(); j++) {
						Object[] o = (Object[]) listImages.get(j);
						if (bidt.equals(o[1])) {
							if (value.length() == 0) {
								value = (String) o[0];
							}
							values += o[0] + "|";
						}
					}
					((Map) list.get(i)).put("bidir", value.length() == 0 ? "null" : value);// 单个，用于支持旧版本
					((Map) list.get(i)).put("bidirs", values);// 多个
				}
			}
		}
		/** 获取显示多图片 结束 **/
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-5-25 上午1:07:23
	 */
	private void doLogin() throws ServletException, IOException {
		String loginId = getParameter("loginId");
		if (StringUtils.isBlank(loginId)) {// 兼容旧版本
			loginId = getParameter("username");
		}
		String password = getParameter("password");
		String UOID = (String) request.getAttribute("UOID");

		PortalReal.log.info("用户尝试使用" + loginId + " " + password + "登录");
		String ip = NetworkUtil.getIpAddr(request);

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		if (UOID == null) {
			UsersDAO usersDao = new UsersDAO();
			usersDao.generateIdentification(loginId);
			List<Users> list = usersDao.findByLoginid(loginId);

			if (list.size() > 0) {
				String password_database = list.get(0).getPassword();
				if (CipherUtil.validatePassword(password, loginId + password_database)) {
					Login.LogLogin(list.get(0).getUid().toString(), ip, "");
					afterLogin(list, jsono);// list.get(0)!=null
					out.write(jsono.toJSONString());
					out.flush();
					out.close();
					return;
				} else {
					jsono.put("code", "200");
					jsono.put("status", "error");
					jsono.put("errpos", "登录");
					jsono.put("errmsg", "密码错误");
					out.write(jsono.toJSONString());
					out.close();
					return;
				}
			} else {
				jsono.put("code", "200");
				jsono.put("status", "error");
				jsono.put("errpos", "登录");
				jsono.put("errmsg", "用户名错误");
				out.write(jsono.toJSONString());
				out.close();
				return;
			}
		} else {
			jsono.put("code", "200");
			jsono.put("status", "success");
			out.write(jsono.toJSONString());
			out.close();
			return;
		}
	}

	private void afterLogin(List<Users> list, JSONObject jsono) {
		/** 唉~以前写的。直接保存对象多好啊 **/

		request.getSession().setAttribute("UOID", list.get(0).getUid());
		request.getSession().setAttribute("ULoginId", list.get(0).getLoginid());
		request.getSession().setAttribute("UPassword", list.get(0).getPassword());
		request.getSession().setAttribute("UName", list.get(0).getUsername());
		request.getSession().setAttribute("UEmail", list.get(0).getEmail() == null ? "" : list.get(0).getEmail());
		request.getSession().setAttribute("UIdentification", list.get(0).getIdentification());
		request.getSession().setAttribute("jPushId", list.get(0).getjPushId());
		request.getSession().setAttribute("qqOpenId", list.get(0).getQqOpenId());
		request.getSession().setAttribute("sinaWeiboAccessToken", list.get(0).getSinaWeiboAccessToken());
		request.getSession().setMaxInactiveInterval(-1);

		setSession(request);

		jsono.put("code", "200");
		jsono.put("status", "success");
		jsono.put("UOID", list.get(0).getUid());
		jsono.put("ULoginId", list.get(0).getLoginid());
		jsono.put("UPassword", list.get(0).getPassword());
		jsono.put("UName", list.get(0).getUsername());
		jsono.put("UEmail", list.get(0).getEmail() == null ? "" : list.get(0).getEmail());
		jsono.put("UIdentification", list.get(0).getIdentification());
		jsono.put("jPushId", list.get(0).getjPushId());
		jsono.put("qqOpenId", list.get(0).getQqOpenId());
		jsono.put("sinaWeiboAccessToken", list.get(0).getSinaWeiboAccessToken());
		jsono.put("sessionid", request.getSession().getId());
		/* 返回sessionid值，下次使用MySessionContext获取session，供手机端使用 */

	}

	/**
	 * 对于没有session保持能力的设备，需要调用这个，保存session到map中，
	 * 
	 * @param request
	 * @createTime 2014-9-6 下午11:13:23
	 */
	private void setSession(HttpServletRequest request) {

		PortalReal.log.info("在Login中，用户" + request.getSession().getAttribute("UOID") + "已经登录！");
		MySessionContext.getInstance().AddSession(request.getSession());
	}

	/**
	 * 用户session验证。默认false会返回json要求重新登录，程序中只需要处理true，false直接返回就可以
	 * 
	 * @param sessionid
	 * @return boolean
	 * @createTime 2014-5-25 下午9:03:58
	 */
	public boolean sessionValide() throws ServletException, IOException {
		System.out.println("sessionid:" + getParameter("sessionid"));
		HttpSession session = getUserSession();
		if (session != null && !session.isNew()) {
			log.info("用户id" + session.getAttribute("UOID"));
			return true;
		} else {
			System.out.println("---session is new ");
			JSONObject jsono = new JSONObject();
			PrintWriter out = response.getWriter();
			jsono.put("code", "209");// 要求重新登录
			jsono.put("status", "error");
			jsono.put("option", "signinagain");
			out.write(jsono.toJSONString());
			out.close();
			return false;
		}
	}

	/**
	 * 重载函数，用于非常规form表单验证时传递字符串
	 * 
	 * @param sessionid
	 * @return
	 * @createTime 2014-5-29 上午12:35:54
	 */
	public boolean sessionValide(String sessionid) {

		MySessionContext myc = MySessionContext.getInstance();
		HttpSession sess = myc.getSession(sessionid);
		if (sess == null || sess.isNew()) {
			log.info("--session is new ");
			return false;
		} else {
			log.info("--session is old ");
			return true;
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-5-26 下午9:54:30
	 */
	private void doRegister() throws ServletException, IOException {

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String username = getParameter("username");
		String password = getParameter("password");
		String email = getParameter("email");
		UsersDAO usersDao = new UsersDAO();
		Users user = new Users();
		List<Users> listu = usersDao.findByUsername(username);

		if (listu.size() > 0) {
			jsono.put("status", "error");
			jsono.put("msg", "此用户名已经存在~");
		} else {
			user.setLoginid(username);
			user.setUsername(username);
			user.setPassword(CipherUtil.generatePassword(password));
			if (!"".equals(email)) {
				user.setEmail(email);
			}
			usersDao.save(user);
			log.info("用户 " + username + " 注册成功！");
			jsono.put("status", "success");
			jsono.put("msg", "注册成功");
		}
		jsono.put("code", "200");
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 上传图片，只能上传1张
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-5-28 下午10:38:47
	 */
	private void doUploadImage() throws ServletException, IOException {
		ServletInputStream in = request.getInputStream();
		FileUpload upload1 = new FileUpload();
		upload1.setInputStream(in);
		/*--------这三个的顺序不能变 开始---------*/
		String sessionid = upload1.getParameter();
		String uid = upload1.getParameter();
		String uname = java.net.URLDecoder.decode(upload1.getParameter(), "utf8");
		/*--------这三个的顺序不能变 结束---------*/

		if (!this.sessionValide(sessionid)) {
			JSONObject jsono = new JSONObject();
			PrintWriter out = response.getWriter();
			jsono.put("code", "209");// 要求重新登录
			jsono.put("status", "error");
			jsono.put("option", "signinagain");
			out.write(jsono.toJSONString());
			out.close();
			upload1.close();
			return;
		}

		String filename = "image/images/" + upload1.getFileUpload(uid, uname, "image/images/");
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		jsono.put("code", "200");// 要求重新登录
		jsono.put("status", "success");
		JSONObject jsonr = new JSONObject();
		jsonr.put("filename", filename);
		jsono.put("result", jsonr);
		out.write(jsono.toJSONString());
		out.close();
		upload1.close();
		return;
	}

	/**
	 * 检查app版本号
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-8-22 下午8:43:19
	 */
	private void checkUpdate() throws IOException {
		version = getVersionDao().findById(1);
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		jsono.put("code", "200");
		jsono.put("status", "success");
		JSONObject jsonr = new JSONObject();
		jsonr.put("version", version.getVersion());
		jsonr.put("content", version.getContent());
		jsono.put("result", jsonr);
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 获取记账类型 btype
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-8-24 上午10:07:00
	 */
	private void getBtype() throws IOException {

		List list = getBills_typeDao().findByUserId(getParameter("UOID"));
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		jsono.put("code", "200");
		jsono.put("status", "success");
		JSONObject jsonr = new JSONObject();
		JSONArray jsonArr = JSONArray.fromObject(list);
		jsono.put("result", jsonArr);
		out.write(jsono.toJSONString());
		out.close();
		return;

	}

	/**
	 * 获取行程 btypes
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-8-24 上午10:07:00
	 */
	private void getBtypes() throws IOException {

		List list = getBillsDao().findByPropertyTravelLeader("uid", getParameter("UOID"));
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		jsono.put("code", "200");
		jsono.put("status", "success");
		JSONObject jsonr = new JSONObject();
		JSONArray jsonArr = JSONArray.fromObject(list);
		jsono.put("result", jsonArr);
		out.write(jsono.toJSONString());
		out.close();
		return;

	}

	/**
	 * 摇一摇功能——随机获取一条数据
	 * 
	 * @createTime 2014-8-30 上午9:55:55
	 */
	private void getRandomData() throws IOException {
		List list = getBillsDao().findAllId(getParameter("UOID"));
		Object obj = null;
		if (list == null || list.isEmpty()) {

		} else {
			int num = (int) (Math.random() * 1000 * list.size()) % list.size();
			obj = getBillsDao().findById(list.get(num).toString());
			List t = new ArrayList();
			t.add(obj);
			dealMultiImage(t);
		}
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		jsono.put("code", "200");
		jsono.put("status", "success");
		JSONObject jsonr = new JSONObject();
		JSONArray jsonArr = JSONArray.fromObject(obj);
		jsono.put("result", jsonArr);
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 标记一条记录为删除
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-3 下午8:03:22
	 */
	private void delete() throws IOException {

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		int bid = Integer.parseInt(getParameter("bid"));
		BillsDAO billsDao = new BillsDAO();
		Bills bill = new Bills();
		bill = billsDao.findById(bid);

		if (bill.getUid().toString().equals(getParameter("UOID"))) {
			billsDao.delete(bill);
			jsono.put("code", "200");
			jsono.put("status", "success");
		} else {
			jsono.put("code", "200");
			jsono.put("status", "fail");
			return;
		}
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 回复
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-4 下午11:10:41
	 */
	private void doAddReply() throws IOException {
		String bcaption = getParameter("replaycontent");
		String frombid = getParameter("frombid");
		Integer bid = Integer.parseInt(frombid);
		String rootid = "";// 推送使用
		// 回复来自哪一条记录id,更新原始记录，评论次数+1//开始
		Bills billfrom = getBillsDao().findById(bid);
		billfrom.setReplys(billfrom.getReplys() + 1);
		String sql = "update bills t set t.replys=(select * from (select count(*)+1 from bills x where x.rootbid=? ) a) where t.bid=?";
		if (billfrom.getRootbid() == 0)// 如果来源记录的根为0，则以它为根，如果根不为0，则以此根为下一个的根
		{
			rootid = frombid;
			getBill().setRootbid(bid);
			generalDao.executeUpdate(sql, String.valueOf(bid), String.valueOf(bid));
		} else {
			rootid = String.valueOf(billfrom.getRootbid());
			getBill().setRootbid(billfrom.getRootbid());
			generalDao.executeUpdate(sql, String.valueOf(billfrom.getRootbid()), String.valueOf(billfrom.getRootbid()));
			generalDao.executeUpdate(sql, String.valueOf(bid), String.valueOf(bid));
		}
		// 回复来自哪一条记录id,更新原始记录，评论次数+1//结束

		getBill().setUid(Integer.parseInt(getParameter("UOID")));
		getBill().setUsername(getParameter("UName"));
		getBill().setBdate(new java.util.Date());
		getBill().setBtype(0);
		getBill().setBio(5);
		getBill().setBamount(0);
		getBill().setBcaption(bcaption);
		getBill().setBctype(1);
		getBill().setFrombid(bid);
		getBillsDao().save(getBill());

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		jsono.put("code", "200");
		jsono.put("status", "success");
		out.write(jsono.toJSONString());
		out.close();

		try {
			if (!billfrom.getUid().toString().equals(getParameter("UOID").toString())) {

				Users user1 = getUsersDao().findById(billfrom.getUid());
				Users user2 = getUsersDao().findById(Integer.parseInt(getParameter("UOID")));
				HashMap extra = new HashMap();
				extra.put("bid", rootid);
				String content = Const.subString(billfrom.getBcaption(), 18);
				content = content.equals("") ? "图片内容" : content;
				new PushMessage().sendPush(user2.getUsername() + "  回复你  " + Const.subString(bcaption, 12), content, user1.getjPushId(), extra,
						PushMessage.MessageAction.REPLY.toString());
				return;
			}
		} catch (Exception e) {
			log.error(e.toString() + "准备推送消息时出现错误");
		}
	}

	/**
	 * 获取回复信息（暂无条数限制）
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-4 下午11:42:19
	 */
	public void getReplys() throws IOException {
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String bid = getParameter("frombid");
		String date = getParameter("date");
		if (bid == null) {
			jsono.put("status", "fail");
		} else {
			List list = getBillsDao().findReplyBybid(bid, date);
			list = TopicKeyWords.addUrlLink(list);
			String result = MapToJson.parseJson(list);
			jsono.put("status", "success");
			jsono.put("result", result);
			jsono.put("replyCount", getBillsDao().findReplyCountBybid(bid));

		}
		jsono.put("code", "200");
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 上传头像
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-6 上午7:48:47
	 */
	public void uploadAvatar() throws IOException {
		ServletInputStream in = request.getInputStream();
		com.avatarUpload.FileUpload upload1 = new com.avatarUpload.FileUpload();
		upload1.setInputStream(in);
		/*--------这三个的顺序不能变 开始---------*/
		String sessionid = upload1.getParameter();
		String uid = upload1.getParameter();
		String uname = java.net.URLDecoder.decode(upload1.getParameter(), "utf8");
		/*--------这三个的顺序不能变 结束---------*/

		if (!this.sessionValide(sessionid)) {
			JSONObject jsono = new JSONObject();
			PrintWriter out = response.getWriter();
			jsono.put("code", "209");// 要求重新登录
			jsono.put("status", "error");
			jsono.put("option", "signinagain");
			out.write(jsono.toJSONString());
			out.close();
			return;
		}

		/**
		 * 保存上传的头像源文件
		 */
		String filename = upload1.getFileUpload();
		/**
		 * 生成头像文件
		 */
		String avatarfilename = uid;

		GenerateImg generate = new GenerateImg();
		String result = null;
		try {
			result = generate.generateImg(filename, avatarfilename);
		} catch (IOException e) {
			throw e;
		}

		UsersDAO usersDao = new UsersDAO();
		Users user = new Users();
		user = usersDao.findById(Integer.parseInt(avatarfilename));
		user.setAvatarstatus(true);
		usersDao.save(user);

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		jsono.put("code", "200");
		jsono.put("status", "success");
		JSONObject jsonr = new JSONObject();
		jsonr.put("filename", result.substring(result.lastIndexOf("/") + 1));
		jsono.put("result", jsonr);
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 修改用户昵称
	 * 
	 * @createTime 2014-9-13 上午12:48:39
	 */
	private void modifyUsername() throws IOException {
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String username_new = getParameter("username_new");

		username_new = username_new.length() > 20 ? username_new.substring(0, 20) : username_new;

		List<Users> list = getUsersDao().findByProperty("username", username_new);
		if (list != null && list.size() > 0) {
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "用户名已经被使用了");
		} else {
			setUsers(getUsersDao().findById(Integer.valueOf(getParameter("UOID"))));
			getUsers().setUsername(username_new);
			getUsersDao().save(getUsers());
			generalDao.executeUpdate("update bills t set t.username=? where t.uid=?", username_new, getParameter("UOID"));
			jsono.put("code", "200");
			jsono.put("status", "success");
		}
		out.write(jsono.toJSONString());
		out.close();
		return;

	}

	/**
	 * 修改邮箱
	 * 
	 * @createTime 2014-9-13 上午12:48:39
	 */
	private void modifyEmail() throws IOException {
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String email_new = getParameter("email_new");

		email_new = email_new.length() > 40 ? email_new.substring(0, 40) : email_new;
		if (email_new.matches(Const.regexp_email)) {

			List<Users> list = getUsersDao().findByProperty("email", email_new);
			if (list != null && list.size() > 0) {
				jsono.put("code", "200");
				jsono.put("status", "fail");
				jsono.put("msg", "Email地址已经被使用");
			} else {
				setUsers(getUsersDao().findById(Integer.valueOf(getParameter("UOID"))));
				getUsers().setEmail(email_new);
				getUsersDao().save(getUsers());
				jsono.put("code", "200");
				jsono.put("status", "success");
			}
		} else {
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "不是正确的Email地址");
		}
		out.write(jsono.toJSONString());
		out.close();
		return;

	}

	/**
	 * 修改邮箱
	 * 
	 * @createTime 2014-9-13 上午12:48:39
	 */
	private void modifyTel() throws IOException {
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String tel_new = getParameter("tel_new");

		tel_new = tel_new.length() > 40 ? tel_new.substring(0, 40) : tel_new;
		if (tel_new.matches(Const.regexp_telephone)) {
			List<Users> list = getUsersDao().findByProperty("telephone", tel_new);
			if (list != null && list.size() > 0) {
				jsono.put("code", "200");
				jsono.put("status", "fail");
				jsono.put("msg", "手机号码已经被使用了");
			} else {
				setUsers(getUsersDao().findById(Integer.valueOf(getParameter("UOID"))));

				getUsers().setTelephone(tel_new);
				getUsersDao().save(getUsers());
				jsono.put("code", "200");
				jsono.put("status", "success");
			}
		} else {
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "不是正确的手机号码");
		}
		out.write(jsono.toJSONString());
		out.close();
		return;

	}

	/**
	 * 获取搭伙的数据
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-13 上午8:40:34
	 */
	private void doGetJoinUs() throws IOException {

		String num = getParameter("num");
		num = num == null ? "0" : num;
		String directionStr = getParameter("direction");
		boolean direction = "true".equals(directionStr) ? true : false;

		String UOID = getParameter("UOID");

		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);

		String parameter1 = "";
		String parameter2 = "";
		String parameter3 = "";
		if (!num.equals("0")) {
			if (direction) {
				parameter3 = " jaapid >'" + num + "' and ";
			} else {
				parameter3 = " jaapid <'" + num + "' and ";
			}
		}

		/** 显示分页数据 */
		List list = getJoinAsAPartnerDao().findByPropertyPaging(parameter1, parameter2, parameter3, 0, 12, "0", currentdatems1 + " 23:59:59");

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		String result = MapToJson.parseJsonAndroid(list);
		jsono.put("code", "200");
		jsono.put("status", "success");
		jsono.put("result", result);
		out.write(jsono.toJSONString());
		out.close();
	}

	/**
	 * 删除搭伙
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-13 上午10:59:23
	 */
	private void deleteJoin() throws IOException {

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		int jaapid = Integer.parseInt(getParameter("jaapid"));

		setJoinAsAPartner(getJoinAsAPartnerDao().findById(jaapid));

		if (getJoinAsAPartner().getUid().toString().equals(getParameter("UOID"))) {

			getJoinAsAPartnerDao().delete(getJoinAsAPartner());
			jsono.put("code", "200");
			jsono.put("status", "success");
		} else {
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "不是您发起的，无权删除");
			return;
		}
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 加入搭伙
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-13 上午11:47:54
	 */
	private void doJoin() throws IOException {
		int applyid = Integer.valueOf(getParameter("applyid"));
		String advice = getParameter("advice");

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		setUsers(getUsersDao().findById(Integer.parseInt(getParameter("UOID"))));

		if (Const.StringIsNull(getUsers().getTelephone())) {
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "请先去个人中心设置手机号码，方便想要一起去的人联系你~");
			out.print(jsono.toString());
			out.close();
			return;
		}

		setJoinAsAPartner(getJoinAsAPartnerDao().findById(applyid));
		if (getJoinAsAPartner() != null) {

			JoinAsAPartner joinAsAPartnerapply = new JoinAsAPartner();
			joinAsAPartnerapply.setJaapcontent("");
			joinAsAPartnerapply.setJaapadvice(advice);
			joinAsAPartnerapply.setJaapsummary("");
			joinAsAPartnerapply.setJaapapplyid(applyid);
			joinAsAPartnerapply.setJaappublicdate(new java.util.Date());

			joinAsAPartnerapply.setUid(Integer.valueOf(getParameter("UOID")));
			joinAsAPartnerapply.setUsername(getParameter("UName"));

			joinAsAPartnerapply.setJaapjoinstate((short) 1);
			joinAsAPartnerDao.save(joinAsAPartnerapply);
			jsono.put("code", "200");
			jsono.put("status", "success");
		} else {
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "此条信息已经不存在");
		}
		out.write(jsono.toJSONString());
		out.close();

	}

	/**
	 * 获取加入的人的信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-13 下午12:56:51
	 */
	public void getJoins() throws IOException {
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String jaapid = getParameter("jaapid");
		if (jaapid == null) {
			jsono.put("status", "fail");
		} else {
			List list = getJoinAsAPartnerDao().findByUidJaapid(jaapid);
			String result = MapToJson.parseJson(list);
			jsono.put("status", "success");
			jsono.put("result", result);
		}
		jsono.put("code", "200");
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 检查用户是否已经加入这个搭伙
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-13 下午1:26:25
	 */
	public void checkUidJaapid() throws IOException {
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		String uid = getParameter("uid");
		String jaapid = getParameter("jaapid");
		if (jaapid == null) {
			jsono.put("status", "fail");
		} else {
			List list = getJoinAsAPartnerDao().checkUidJaapid(jaapid, uid);
			String result = MapToJson.parseJson(list);
			jsono.put("status", "success");
			jsono.put("result", result);
		}
		jsono.put("code", "200");
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 发起搭伙（一起走）
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-13 下午3:56:35
	 */
	private void doAddJoin() throws IOException {
		String title = getParameter("title");
		String deadline = getParameter("deadline");
		String content = getParameter("content");

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();

		getJoinAsAPartner().setJaaptitle(title);
		getJoinAsAPartner().setJaapcontent(content);
		try {
			getJoinAsAPartner().setJaapdeadline(Const.getDate(deadline, Const.sdf2));
		} catch (ParseException e) {
			e.printStackTrace();
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "时间格式不正确");
			return;
		}
		getJoinAsAPartner().setJaappublicdate(new java.util.Date());

		getJoinAsAPartner().setUid(Integer.valueOf(getParameter("UOID")));
		getJoinAsAPartner().setUsername(getParameter("UName"));

		getJoinAsAPartner().setJaapjoinstate((short) 0);
		getJoinAsAPartner().setJaapadvice("");
		getJoinAsAPartner().setJaapsummary("");

		getJoinAsAPartnerDao().save(getJoinAsAPartner());
		jsono.put("code", "200");
		jsono.put("status", "success");

		out.write(jsono.toJSONString());
		out.close();

	}

	/**
	 * 只显示自己已经加入的
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-13 下午11:55:10
	 */
	private void doGetJoinUsMine() throws IOException {

		String num = getParameter("num");
		num = num == null ? "0" : num;
		String directionStr = getParameter("direction");
		boolean direction = "true".equals(directionStr) ? true : false;

		String UOID = getParameter("UOID");

		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);

		String parameter1 = "";
		String parameter2 = "";
		String parameter3 = "";
		if (!num.equals("0")) {
			if (direction) {
				parameter3 = " jaapid >'" + num + "' and ";
			} else {
				parameter3 = " jaapid <'" + num + "' and ";
			}
		}
		parameter3 += " exists (select 1 from join_as_a_partner y where y.jaapapplyid=t.jaapid and y.uid=" + getParameter("UOID") + ") and ";
		/** 显示分页数据 */
		List list = getJoinAsAPartnerDao().findByPropertyPaging(parameter1, parameter2, parameter3, 0, 12, "0", currentdatems1 + " 23:59:59");

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		String result = MapToJson.parseJsonAndroid(list);
		jsono.put("code", "200");
		jsono.put("status", "success");
		jsono.put("result", result);
		out.write(jsono.toJSONString());
		out.close();
	}

	/**
	 * 存储APP传来的异常信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-15 下午11:05:54
	 */
	private void saveException() throws IOException {

		CatchException exception = new CatchException();
		CatchExceptionDAO dao = new CatchExceptionDAO();
		exception.setException(getParameter("exception"));
		dao.save(exception);

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		jsono.put("code", "200");
		jsono.put("status", "success");
		out.write(jsono.toJSONString());
		out.close();
	}

	/**
	 * 获取最热的三个话题（话题是公有的，不分彼此）
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-16 下午10:51:45
	 */
	private void getThreeHotTopic() throws IOException {
		BillsTopicDAO billsTopicDao = new BillsTopicDAO();
		List list = billsTopicDao.getThreeHotTopic();

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		String result = MapToJson.parseJsonAndroid(list);
		jsono.put("code", "200");
		jsono.put("status", "success");
		jsono.put("result", result);
		out.write(jsono.toJSONString());
		out.close();
	}

	/**
	 * 看世界
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-5-25 上午1:07:41
	 */
	private void seeWorld() throws ServletException, IOException {

		String num = getParameter("num");
		num = num == null ? "0" : num;
		String directionStr = getParameter("direction");
		boolean direction = "true".equals(directionStr) ? true : false;

		String UOID = getParameter("UOID");
		BillsDAO billsDao = new BillsDAO();
		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);

		String parameter1 = "";
		String parameter2 = "";
		String parameter3 = "uid!='" + UOID + "' and ";
		if (!num.equals("0")) {
			if (direction) {
				parameter3 += " bid >'" + num + "' and ";
			} else {
				parameter3 += " bid <'" + num + "' and ";
			}
		}
		/** 显示分页数据 */
		List list = billsDao.findByPropertyPaging(parameter1, parameter2, parameter3, 0, 12, "0", currentdatems1 + " 23:59:59");

		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		String result = MapToJson.parseJsonAndroid(list);
		jsono.put("code", "200");
		jsono.put("status", "success");
		jsono.put("result", result);
		out.write(jsono.toJSONString());
		out.close();

	}

	/**
	 * 获取APP下载地址
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-9-17 上午12:16:25
	 */
	private void getDownloadUrl() throws IOException {
		version = getVersionDao().findById(1);
		JSONObject jsono = new JSONObject();
		PrintWriter out = response.getWriter();
		jsono.put("code", "200");
		jsono.put("status", "success");
		JSONArray jsonr = new JSONArray();
		JSONObject jsonr_o = new JSONObject();
		jsonr_o.put("downloadurl1", version.getDownloadurl1());
		jsonr.add(jsonr_o);
		jsonr_o.clear();
		jsonr_o.put("downloadurl2", version.getDownloadurl2());
		jsonr.add(jsonr_o);
		jsonr_o.clear();
		jsonr_o.put("downloadurl3", version.getDownloadurl3());
		jsonr.add(jsonr_o);
		jsonr_o.clear();
		jsonr_o.put("downloadurl4", version.getDownloadurl4());
		jsonr.add(jsonr_o);
		jsonr_o.clear();
		jsonr_o.put("downloadurl5", version.getDownloadurl5());
		jsonr.add(jsonr_o);
		jsonr_o.clear();
		jsonr_o.put("downloadurl6", version.getDownloadurl6());
		jsonr.add(jsonr_o);
		jsonr_o.clear();
		jsonr_o.put("downloadurl7", version.getDownloadurl7());
		jsonr.add(jsonr_o);
		jsono.put("result", jsonr);
		out.write(jsono.toJSONString());
		out.close();
		return;
	}

	/**
	 * 获取一起走详细内容
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @createTime 2014-11-23 上午8:45:03
	 */
	private void doGetJoinUsDetail() throws ServletException, IOException {
		String ids = getParameter("jaapid");
		int id = 0;
		try {
			id = Integer.valueOf(ids);
		} catch (Exception e) {

		}
		setJoinAsAPartner(getJoinAsAPartnerDao().findById(id));
		getJoinAsAPartner().setJaapcontent(MapToJson.parseHtml(getJoinAsAPartner().getJaapcontent()));
		request.setAttribute("joinAsAPartner", joinAsAPartner);
		request.getRequestDispatcher("api/joinus_detail.jsp").forward(request, response);
	}

	/**
	 * 获取我的好友列表
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @createTime 2014-11-29 上午11:00:50
	 */
	private void getMyFriends() throws ServletException, IOException {

		String UOID = getParameter("UOID");
		List list = getFriendsService().myFriends(Integer.valueOf(UOID));
		JSONObject jsono = new JSONObject();
		if (Const.ListIsNotBlank(list)) {
			JsonConfig jc = new JsonConfig();
			jc.setJsonPropertyFilter(new PropertyFilter() {
				// Object source/* 属性的拥有者 */ , String name /*属性名字*/ , Object
				// value/* 属性值 */
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					// return true to skip name
					if (arg0 instanceof Users) {
						if (",password,identityCardNumber,".indexOf("," + arg1 + ",") >= 0) {
							return true;
						}
					}
					return false;
				}
			});
			JSONArray array = JSONArray.fromObject(list, jc);
			jsono.put("result", "{\"type\":" + array.toString() + "}");
		} else {
			jsono.put("result", "{\"type\":[]}");
		}
		jsono.put("code", "200");
		jsono.put("status", "success");
		returnJson(jsono.toJSONString());
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-11-29 下午3:42:58
	 */
	private void deleteFriends() throws IOException {
		int uid1 = Integer.valueOf(getParameter("uid1"));
		int uid2 = Integer.valueOf(getParameter("UOID"));
		boolean b = getFriendsService().deleteFriends(uid1, uid2);
		if (b) {
			returnJson(SUCCESS.replace("message", "你们已经解除好友关系"));
		} else {
			returnJson(FAIl.replace("message", "删除失败，你们依然是好友"));
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-11-29 下午3:42:56
	 */
	private void addFriends() throws IOException {
		int uid1 = Integer.valueOf(getParameter("uid1"));
		int uid2 = Integer.valueOf(getParameter("UOID"));
		int b = getFriendsService().addFriends(uid1, uid2);

		JSONObject jsono = new JSONObject();
		if (b == 3) {// 成功
			jsono.put("code", "200");
			jsono.put("status", "success");
			jsono.put("msg", "申请好友成功");
			returnJson(jsono.toJSONString());
			Users user1 = getUsersDao().findById(Integer.valueOf(uid1));
			Users user2 = getUsersDao().findById(Integer.valueOf(uid2));
			new PushMessage().sendPush("添加好友", user2.getUsername() + "  申请添加你为好友！", user1.getjPushId(), PushMessage.MessageAction.ADD_FRIENDS.toString());
		} else if (b == 1) {// 已经申请好
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "已经申请好友\n    等待确认");
			returnJson(jsono.toJSONString());
		} else if (b == 2) {// 已经是好友
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "你们已经是好友");
			returnJson(jsono.toJSONString());
		} else if (b == 0) {// 不能加自己
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "不能加自己好友");
			returnJson(jsono.toJSONString());
		} else {// 失败
			jsono.put("code", "200");
			jsono.put("status", "fail");
			jsono.put("msg", "申请好友失败");
			returnJson(jsono.toJSONString());
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-11-29 下午3:42:53
	 */
	private void searchFriends() throws IOException {
		String searchWord = getParameter("s_wd");
		List list = getFriendsService().searchPerson(searchWord);
		JSONObject jsono = new JSONObject();
		JSONArray array = new JSONArray();
		if (Const.ListIsNotBlank(list)) {
			JsonConfig jc = new JsonConfig();
			jc.setJsonPropertyFilter(new PropertyFilter() {
				// Object source/* 属性的拥有者 */ , String name /*属性名字*/ , Object
				// value/* 属性值 */
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					// return true to skip name
					if (arg0 instanceof Users) {
						if (",password,identityCardNumber,".indexOf("," + arg1 + ",") >= 0) {
							return true;
						}
					}
					return false;
				}
			});
			array = JSONArray.fromObject(list, jc);
		}
		jsono.put("result", "{\"type\":" + array.toString() + "}");
		jsono.put("code", "200");
		jsono.put("status", "success");
		returnJson(jsono.toJSONString());
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-11-29 下午3:44:31
	 */
	private void confirmFriends() throws IOException {
		int uid1 = Integer.valueOf(getParameter("uid1"));
		int uid2 = Integer.valueOf(getParameter("UOID"));
		boolean b = getFriendsService().confirmFriends(uid1, uid2);

		Users user1 = getUsersDao().findById(Integer.valueOf(uid1));
		Users user2 = getUsersDao().findById(Integer.valueOf(uid2));

		if (b) {
			returnJson(SUCCESS.replace("message", "你们已经是好友了"));
			new PushMessage().sendPush("添加好友", user2.getUsername() + "  已经和你是好友了！", user1.getjPushId(), PushMessage.MessageAction.CONFIRM_FRIENDS.toString());
		} else {
			returnJson(FAIl.replace("message", "确认失败，你们还不是好友"));
		}
	}

	private void refuseFriends() throws IOException {
		int uid1 = Integer.valueOf(getParameter("uid1"));
		int uid2 = Integer.valueOf(getParameter("UOID"));
		boolean b = getFriendsService().refuseFriends(uid1, uid2);

		Users user1 = getUsersDao().findById(Integer.valueOf(uid1));
		Users user2 = getUsersDao().findById(Integer.valueOf(uid2));

		if (b) {
			returnJson(SUCCESS.replace("message", "你已拒绝好友申请"));
			new PushMessage().sendPush("拒绝好友", user2.getUsername() + "  拒绝了你申请好友！", user1.getjPushId(), PushMessage.MessageAction.CONFIRM_FRIENDS.toString());
		} else {
			returnJson(FAIl.replace("message", "确认失败，你们还不是好友"));
		}
	}

	/**
	 * 获取用户消息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @createTime 2014-11-29 下午9:38:11
	 */
	private void getMyMessage() throws IOException {
		int uid = Integer.valueOf(getParameter("UOID"));
		List list = getMessageService().getMessage(uid);
		JSONObject jsono = new JSONObject();
		jsono.put("code", "200");
		jsono.put("status", "success");
		jsono.put("result", "{\"type\":" + JsonUtils.parseArrayFromObject(list) + "}");
		System.out.println(jsono.toJSONString());
		returnJson(jsono.toJSONString());
	}

	/**
	 * 检查qq openId是否已经注册用户
	 * 
	 * @createTime 2015-3-7 上午6:40:41
	 */
	private List QQLoginChech() {
		return generalDao.getListHql("from Users t where t.qqOpenId=?", getParameter("qqOpenId"));
	}

	/**
	 * 验证，注册，登录
	 * 
	 * @throws IOException
	 * 
	 * @createTime 2015-3-7 上午6:40:02
	 */
	private void QQLogin() throws IOException {
		List list = QQLoginChech();
		if (Const.ListIsNotBlank(list)) {
			doQQLogin(list);// list.get(0)!=null
		} else {
			doQQRegister();
		}
	}

	/**
	 * 自动注册生成用户名
	 * 
	 * @param length
	 *            参数为0时，即默认，默认为9，最大为13
	 * @return
	 * @createTime 2015-3-8 上午9:20:18
	 */
	public static String generateUsername(int length) {
		DecimalFormat df = new DecimalFormat("#");
		String username = df.format(Math.floor(Math.random() * Math.pow(10, 13)));
		return Const.subString(username, length == 0 ? 9 : length, "");
	}

	/**
	 * QQ注册
	 * 
	 * @throws IOException
	 * 
	 * @createTime 2015-3-7 上午6:47:18
	 */
	private void doQQRegister() throws IOException {
		try {
			JSONObject jsono = new JSONObject();

			String username = getParameter("username");
			username = generateUsername(0);
			/** 检查用户名是否重复，重复则生成10位的 **/
			String checkSql = "select count(*) from users where loginid=?";
			List checkList = generalDao.getList(checkSql, username);
			if (!checkList.get(0).toString().equals("0")) {
				username = generateUsername(10);
			}

			String password = getParameter("password");
			String qqOpenId = getParameter("qqOpenId");
			String nickname = getParameter("nickname");
			UsersDAO usersDao = new UsersDAO();
			Users user = new Users();

			user.setLoginid(username);
			user.setUsername(nickname);
			user.setPassword(CipherUtil.generatePassword(password));
			user.setQqOpenId(qqOpenId);

			usersDao.save(user);
			log.info("用户 " + username + " 通过 QQ登录 注册成功！");

			List<Users> list = QQLoginChech();
			if (Const.ListIsNotBlank(list)) {
				afterLogin(list, jsono);// list.get(0)!=null
				getUsersDao().generateIdentification(list.get(0).getLoginid());

				doGetQQAvatar(String.valueOf(list.get(0).getUid()));
			} else {
				jsono.put("success", false);
				jsono.put("status", 200);
				jsono.put("message", "QQ登录用户注册失败");
			}
			returnJson(jsono.toJSONString());
		} catch (RuntimeException e) {
			returnJson(SUCCESS.replace("message", "登录失败，请重试"));
			return;
		}
	}

	/**
	 * QQ登录
	 * 
	 * @param list
	 * @throws IOException
	 * @createTime 2015-3-7 上午6:47:15
	 */
	private void doQQLogin(List<Users> list) throws IOException {
		JSONObject jsono = new JSONObject();
		// 苗苗用户说不需要
		// doGetQQAvatar(String.valueOf(list.get(0).getUid()));

		afterLogin(list, jsono);// list.get(0)!=null
		getUsersDao().generateIdentification(list.get(0).getLoginid());
		log.info("用户 " + getParameter("qqOpenId") + " 通过 QQ登录成功！");
		System.out.println(jsono.toJSONString());
		returnJson(jsono.toJSONString());
	}

	/**
	 * 线程中获取qq头像
	 * 
	 * @param uid
	 * @createTime 2015-3-7 下午8:19:16
	 */
	private void doGetQQAvatar(final String uid) {
		final String url = getParameter("figureurl_qq_2");
		log.debug("figureurl_qq_2:" + getParameter("figureurl_qq_2"));
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					ImageRequest.saveWebImage(url, "image/avatar/", "avatar_uid_" + uid + ".jpg");

					UsersDAO usersDao = new UsersDAO();
					Users user = new Users();
					user = usersDao.findById(Integer.parseInt(uid));
					user.setAvatarstatus(true);
					usersDao.save(user);

				} catch (Exception e) {
					log.error("QQ头像保存失败");
					e.printStackTrace();
				}

			}
		}).start();
	}

	/**
	 * 已在账号绑定QQ账号
	 * 
	 * @throws IOException
	 * 
	 * @createTime 2015-3-7 下午9:28:42
	 */
	private void binQQLogin() throws IOException {
		try {
			String loginId = getParameter("loginId");
			String qqOpenId = getParameter("qqOpenId");
			String checkSqlYouHadBindQQ = "select qq_open_id from users where loginid=?";
			String checkSqlQQHadBeenBind = "select loginid from users where qq_open_id=?";
			String updateSqlBindQQ = "update users set qq_open_id=? where loginid=?";
			List list = null;
			list = generalDao.getList(checkSqlYouHadBindQQ, loginId);
			if (Const.ListIsNotBlank(list)) {
				returnJson(FAIl.replace("message", "您已经绑定过QQ账号"));
				return;
			}
			list = generalDao.getList(checkSqlQQHadBeenBind, qqOpenId);
			if (Const.ListIsNotBlank(list)) {
				returnJson(FAIl.replace("message", "此QQ账号已经绑定过其它账号，可直接登录"));
				return;
			}
			int count = generalDao.executeUpdate(updateSqlBindQQ, qqOpenId, loginId);
			if (count > 0) {
				returnJson(SUCCESS.replace("message", "QQ账号绑定成功，可直接登录"));
				return;
			}
		} catch (RuntimeException e) {
			returnJson(SUCCESS.replace("message", "绑定失败"));
			return;
		}
	}

	private void doForgetPassword() throws IOException {
		try {
			String email = getParameter("email");
			String password = AutoGeneratePassword.GeneratePassword();
			Users user = new Users();
			UsersDAO usersDao = new UsersDAO();
			List<Users> list = usersDao.findByEmail(email);
			if (list.size() > 0) {
				user = list.get(0);
				user.setPassword(CipherUtil.generatePassword(password));
				usersDao.save(user);
				sendEmail.send(email, password);
				returnJson(SUCCESS.replace("message", "系统已经发送一封邮件到您输入的邮箱，请您登录邮箱查看"));
				return;
			}
			returnJson(FAIl.replace("message", "您输入的邮箱不存在"));
		} catch (Exception e) {
			returnJson(FAIl.replace("message", "找回密码错误"));
		}
	}

	private void doChangePassword() throws IOException {
		String UOID = getUserSession().getAttribute("UOID").toString();
		String UName = (String) getUserSession().getAttribute("UName");
		String password_old = getParameter("password_old");
		String password_new = getParameter("password_new");
		String password_new2 = getParameter("password_new2");
		if (password_new.equals(password_new2) && password_old != null && !password_old.equals("")) {
			Users user = new Users();
			UsersDAO usersDao = new UsersDAO();
			List<Users> list = usersDao.findByUsername(UName);
			if (list.size() > 0) {
				user = list.get(0);
				String password_database = user.getPassword();
				if (CipherUtil.validatePassword(password_database, password_old)) {
					user.setPassword(CipherUtil.generatePassword(password_new));
					usersDao.save(user);
					returnJson(SUCCESS.replace("message", "修改密码成功"));
					return;
				} else {
					returnJson(FAIl.replace("message", "旧密码不正确"));
					return;
				}
			} else {
				returnJson(FAIl.replace("message", "用户名不存在"));
				return;
			}
		} else {
			returnJson(FAIl.replace("message", "两个新密码不相同"));
			return;
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		// Put your code here
	}

	@Override
	public void initClass() {
		// TODO Auto-generated method stub

	}

}
