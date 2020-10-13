<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class User_model extends CI_Model {
	
	const UID = "uid";
	const PARENT_UID = "parent_uid";
	const EMAIL = "email";
	const PASSWORD = "password";
	const NICKNAME = "nickname";
	const GENDER = "gender";
	const QQ_ACCESS_TOKEN = "qq_access_token";
	const QQ_OPEN_ID = "qq_open_id";
	const WEIBO_ACCESS_TOKEN = "weibo_access_token";
	const WEIBO_UID = "weibo_uid";
	const BIND_WITH = "bind_with";
	const REAL_NAME = "real_name";
	const FIGURE_SMALL = "figure_small";
	const FIGURE_NORMAL = "figure_normal";
	const LOCATION = "location";
	const PHONE = "phone";
	const QQ = "qq";
	const WEIBO = "weibo";
	const WECHAT = "wechat";
	const MY_EMAIL = "my_email";
	const USER_TYPE = "user_type";
	const SCHOOL = "school";
	const GRADE = "grade";
	const WHERE_FROM = "where_from";
	const SPECIALITY = "speciality";
	const EVALUATION = "evaluation";
	const T_TYPE = "t_type";
	const IS_VALID = "is_valid";
	const STATUS = "status";
	const REG_TIME = "reg_time";
	const LOGIN_TIME = "login_time";
	const UPDATE_TIME = "update_time";
	const KID_ADD_TIME = "kid_add_time";
	const KID_UPDATE_TIME = "kid_update_time";
	
	/**
	 * 通过qq登录的openid或者微博登录的uid去查找数据库中是否已经存在该用户，如果存在则返回该用户的uid, nickname、figure_normal和user_type信息，
	 * 
	 * @param string $openid
	 * @param string $uid
	 * @return boolean|string
	 */
	public function getUserByOpenid($openid = "", $uid = "") {
		if ($openid != "") {
			$this->db->select(self::UID.",".self::EMAIL.",".self::NICKNAME.",".self::FIGURE_NORMAL.",".self::USER_TYPE)->from("user")
				->where(array(self::QQ_OPEN_ID => $openid));
			return $this->db->get();
		}
		if ($uid != "") {
			$this->db->select(self::UID.",".self::EMAIL.",".self::NICKNAME.",".self::FIGURE_NORMAL.",".self::USER_TYPE)->from("user")
				->where(array(self::WEIBO_UID => $uid));
			return $this->db->get();
		}
	}
	
	/**
	 * 首次使用qq登录时绑定qq
	 * @param unknown $_data
	 * @return unknown|string
	 */
	public function addFromQQ($_data) {
		$this->load->library("app_common");
		$data[self::UID] = $this->app_common->create_uuid();
		$data[self::NICKNAME] = $_data["nickname"];
		$data[self::QQ_ACCESS_TOKEN] = $_data["access_token"];
		$data[self::QQ_OPEN_ID] = $_data["openid"];
		$data[self::BIND_WITH] = BIND_WITH_QQ;
		$data[self::GENDER] = $_data["gender"];
		$data[self::FIGURE_SMALL] = $_data["figure_small"];
		$data[self::FIGURE_NORMAL] = $_data["figure_normal"];
		$data[self::USER_TYPE] = USER_TYPE_NORMAL;
		$time = date("Y-m-d H:i:s", time());
		$data[self::REG_TIME] = $time;
		$data[self::LOGIN_TIME] = $time;
		$data[self::UPDATE_TIME] = $time;
		$data[self::IS_VALID] = "Y";
		$this->db->insert("user", $data);
		if ($this->db->affected_rows() == 1) {
			return $data[self::UID];
		} 
		return "";
	}
	
	/**
	 * 已经有用户账号，进行绑定
	 * @param unknown $_data
	 */
	public function bindFromQQ($_data) {
		$data[self::QQ_ACCESS_TOKEN] = $_data["access_token"];
		$data[self::QQ_OPEN_ID] = $_data["openid"];
		$data[self::BIND_WITH] = BIND_WITH_QQ;
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $_data["uid"]));
		return $this->db->affected_rows();
	}
	
	/**
	 * 首次使用微博登录时绑定微博
	 * @param unknown $_data
	 * @return unknown|string
	 */
	public function addFromWeibo($_data) {
		$this->load->library("app_common");
		$data[self::UID] = $this->app_common->create_uuid();
		$data[self::NICKNAME] = $_data["nickname"];
		$data[self::WEIBO_ACCESS_TOKEN] = $_data["access_token"];
		$data[self::WEIBO_UID] = $_data["openid"];
		$data[self::BIND_WITH] = BIND_WITH_WEIBO;
		$data[self::GENDER] = $_data["gender"];
		$data[self::FIGURE_SMALL] = $_data["figure_small"];
		$data[self::FIGURE_NORMAL] = $_data["figure_normal"];
		$data[self::USER_TYPE] = USER_TYPE_NORMAL;
		$time = date("Y-m-d H:i:s", time());
		$data[self::REG_TIME] = $time;
		$data[self::LOGIN_TIME] = $time;
		$data[self::UPDATE_TIME] = $time;
		$data[self::IS_VALID] = "Y";
		$this->db->insert("user", $data);
		if ($this->db->affected_rows() == 1) {
			return $data[self::UID];
		}
		return "";
	}
	
	/**
	 * 已经有用户账号，进行绑定
	 * @param unknown $_data
	 */
	public function bindFromWeibo($_data) {
		$data[self::WEIBO_ACCESS_TOKEN] = $_data["access_token"];
		$data[self::WEIBO_UID] = $_data["openid"];
		$data[self::BIND_WITH] = BIND_WITH_WEIBO;
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $_data["uid"]));
		return $this->db->affected_rows();
	}
	
	/**
	 * 用户注册，保存用户信息
	 * @param string $_data
	 * @return multitype:unknown |string
	 */
	public function reg($_data = NULL) {
		$this->load->library("app_http");
		$this->load->library("app_common");
		$data = $this->app_http->getPostData4DB(array(
				self::EMAIL
		));
		$data[self::UID] = $this->app_common->create_uuid();
		$data[self::PASSWORD] = md5($this->input->post(self::PASSWORD));
		$data[self::BIND_WITH] = BIND_WITH_EMAIL;
		$data[self::USER_TYPE] = USER_TYPE_NORMAL;
		$time = date("Y-m-d H:i:s", time());
		$data[self::REG_TIME] = $time;
		$data[self::LOGIN_TIME] = $time;
		$data[self::UPDATE_TIME] = $time;
		$data[self::IS_VALID] = "Y";
		$data[self::FIGURE_NORMAL] = base_url().DEFAULT_USER_ICON;
		$data[self::NICKNAME] = $data[self::EMAIL];
		$data[self::MY_EMAIL] = $data[self::EMAIL];
		$this->db->insert("user", $data);
		if ($this->db->affected_rows() == 1) {
			return array(self::UID => $data[self::UID], self::EMAIL=> $data[self::EMAIL], self::NICKNAME => $data[self::EMAIL], 
					self::USER_TYPE => $data[self::USER_TYPE], self::FIGURE_NORMAL => $data[self::FIGURE_NORMAL]);
		}
		return "";
	}
	
	/**
	 * 用户登录，通过登录邮箱和密码查询用户
	 * @param unknown $email
	 * @param unknown $password
	 */
	public function findUser($email, $password) {
		$this->db->select(self::UID.",".self::EMAIL.",".self::FIGURE_NORMAL.",".self::NICKNAME.",".self::USER_TYPE.",".self::BIND_WITH)
		->from("user")->where(array(self::EMAIL => $email, self::PASSWORD => md5($password)));
		return $this->db->get();
	}
	
	/**
	 * 查询用户数据库中邮箱是否被使用，主要用于用户注册时的邮箱检测
	 * @param unknown $email
	 */
	public function findEmail($email) {
		return $this->db->select(self::UID)->from("user")->where(array(self::EMAIL => $email))->count_all_results();
	}
	
	/**
	 * 通过登录邮箱判断该用户是否被冻结，主要用于用户登录时
	 * @param unknown $email
	 */
	public function isValid($email) {
		$result = $this->db->select(self::IS_VALID)->from("user")->where(array(self::EMAIL => $email))->get()->result();
		if (count($result) == 1) {
			$yes_or_no = $result[0]->is_valid;
			if ($yes_or_no == "Y") {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * 通过用户唯一编码判断该用户是否被冻结，主要用于社交账号登录时
	 * @param unknown $uid
	 */
	public function isValidByUid($uid) {
		$result = $this->db->select(self::IS_VALID)->from("user")->where(array(self::UID => $uid))->get()->result();
		if (count($result) == 1) {
			$yes_or_no = $result[0]->is_valid;
			if ($yes_or_no == "Y") {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * 用户登录时需要更新的一些数据 
	 * @param unknown $uid
	 */
	public function updateWhenLogin($uid) {
		$time = date("Y-m-d H:i:s", time());
		$data[self::LOGIN_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $uid));
	}
	
	/**
	 * 查询普通用户类型的用户数据
	 * @param unknown $uid
	 */
	public function findNormalByUid($uid) {
		$this->db->select(self::UID.",".self::PARENT_UID.",".self::EMAIL.",".self::NICKNAME.",".self::GENDER.","
			.self::REAL_NAME.",".self::LOCATION.",".self::QQ.",".self::WEIBO.",".self::WECHAT.",".self::PHONE.","
			.self::REG_TIME.",".self::LOGIN_TIME.",".self::UPDATE_TIME.",".self::FIGURE_NORMAL.",".self::FIGURE_SMALL.","
			.self::MY_EMAIL)
			->from("user")->where(array(self::UID => $uid));
		return $this->db->get();
	}
	
	/**
	 * 查询家长类型的用户数据，与普通用户类型是一致的，但是家长用户可以添加孩子信息，查询孩子信息
	 * @param unknown $uid
	 */
	public function findParentByUid($uid) {
		return $this->findNormalByUid($uid);
	}
	
	/**
	 * 家长用户查询自己的孩子信息
	 * 返回对象数组，可能有多个孩子
	 * @param unknown $parent_uid
	 */
	public function findKidsByParentUid($parent_uid) {
		$this->db->select(self::UID.",".self::PARENT_UID.",".self::NICKNAME.",".self::GENDER.","
				.self::REAL_NAME.",".self::LOCATION.",".self::FIGURE_NORMAL.",".self::FIGURE_SMALL.","
				.self::SCHOOL.",".self::GRADE.",".self::EVALUATION.",".self::KID_ADD_TIME.",".self::KID_UPDATE_TIME)
				->from("user")->where(array(self::PARENT_UID => $parent_uid))->order_by(self::KID_ADD_TIME, "desc");
		return $this->db->get();
	}
	
	/**
	 * 查询学生类型的用户数据
	 * @param unknown $uid
	 */
	public function findStudentByUid($uid) {
		$this->db->select(self::UID.",".self::PARENT_UID.",".self::EMAIL.",".self::NICKNAME.",".self::GENDER.","
				.self::REAL_NAME.",".self::LOCATION.",".self::QQ.",".self::WEIBO.",".self::WECHAT.",".self::PHONE.","
				.self::REG_TIME.",".self::LOGIN_TIME.",".self::UPDATE_TIME.",".self::FIGURE_NORMAL.",".self::FIGURE_SMALL.","
				.self::SCHOOL.",".self::GRADE.",".self::EVALUATION.",".self::MY_EMAIL)
				->from("user")->where(array(self::UID => $uid));
		return $this->db->get();
	}
	
	/**
	 * 分页查找学生信息
	 * @param unknown $pageNo
	 * @param unknown $pageSize
	 */
	public function findStudentByPage($pageNo, $pageSize) {
		$where = self::IS_VALID."='Y' and ".self::USER_TYPE."=".USER_TYPE_STUDENT." or ".self::USER_TYPE."=".USER_TYPE_KID;
		$this->db->select(self::UID.",".self::PARENT_UID.",".self::EMAIL.",".self::NICKNAME.",".self::GENDER.","
				.self::REAL_NAME.",".self::LOCATION.",".self::QQ.",".self::WEIBO.",".self::WECHAT.",".self::PHONE.","
				.self::REG_TIME.",".self::LOGIN_TIME.",".self::UPDATE_TIME.",".self::FIGURE_NORMAL.",".self::FIGURE_SMALL.","
				.self::SCHOOL.",".self::GRADE.",".self::EVALUATION.",".self::MY_EMAIL)
				->from("user")->where($where)->order_by(self::UPDATE_TIME, "desc")
				->limit($pageSize, ($pageNo - 1) * $pageSize);
		return $this->db->get();
	}
	
	/**
	 * 学生总记录数
	 */
	public function totalStudents() {
		$where = self::IS_VALID." = 'Y' and ".self::USER_TYPE." = ".USER_TYPE_STUDENT." or ".self::USER_TYPE." = ".USER_TYPE_KID;
		return $this->db->select(self::UID)->from("user")->where($where)->count_all_results();
	}
	
	/**
	 * 查询教师类型的用户数据
	 * @param unknown $uid
	 */
	public function findTeacherByUid($uid) {
		$this->db->select(self::UID.",".self::PARENT_UID.",".self::EMAIL.",".self::NICKNAME.",".self::GENDER.","
				.self::REAL_NAME.",".self::LOCATION.",".self::QQ.",".self::WEIBO.",".self::WECHAT.",".self::PHONE.","
				.self::REG_TIME.",".self::LOGIN_TIME.",".self::UPDATE_TIME.",".self::FIGURE_NORMAL.",".self::FIGURE_SMALL.","
				.self::WHERE_FROM.",".self::SPECIALITY.",".self::EVALUATION.",".self::T_TYPE.",".self::MY_EMAIL)
				->from("user")->where(array(self::UID => $uid));
		return $this->db->get();
	}
	
	/**
	 * 分页查找教师信息
	 * @param unknown $uid
	 */
	public function findTeacherByPage($pageNo, $pageSize) {
		$this->db->select(self::UID.",".self::PARENT_UID.",".self::EMAIL.",".self::NICKNAME.",".self::GENDER.","
				.self::REAL_NAME.",".self::LOCATION.",".self::QQ.",".self::WEIBO.",".self::WECHAT.",".self::PHONE.","
				.self::REG_TIME.",".self::LOGIN_TIME.",".self::UPDATE_TIME.",".self::FIGURE_NORMAL.",".self::FIGURE_SMALL.","
				.self::WHERE_FROM.",".self::SPECIALITY.",".self::EVALUATION.",".self::T_TYPE.",".self::MY_EMAIL)
				->from("user")->where(array(self::USER_TYPE => USER_TYPE_TEACHER, self::IS_VALID => "Y"))->order_by(self::UPDATE_TIME, "desc")
				->limit($pageSize, ($pageNo - 1) * $pageSize);
		return $this->db->get();
	}
	
	/**
	 * 教师总数量
	 */
	public function totalTeachers() {
		return $this->db->select(self::UID)->from("user")->where(array(self::USER_TYPE => USER_TYPE_TEACHER, self::IS_VALID => "Y"))
		->count_all_results();
	}
	
	/**
	 * 判断用户输入的旧密码是否正确
	 * @param unknown $uid
	 * @param unknown $pwd
	 */
	public function isOldPwdValid($uid, $pwd) {
		return $this->db->select(self::UID)->from("user")->where(array(self::UID => $uid, self::PASSWORD => md5($pwd)))
			->count_all_results(); 
	}
	
	/**
	 * 更新用户密码
	 * @param unknown $uid
	 * @param unknown $pwd
	 */
	public function updatePassword($uid, $pwd) {
		$data[self::PASSWORD] = md5($pwd);
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 更新用户的用户类型，用于用户设置用户类型时
	 * @param unknown $uid
	 * @param unknown $type
	 */
	public function updateUserType($uid, $type) {
		$data[self::USER_TYPE] = $type;
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 添加孩子信息
	 * @param unknown $_data
	 */
	public function addKid($parent_uid) {
		$this->load->library("app_http");
		$this->load->library("app_common");
		$data = $this->app_http->getPostData4DB(array(
				self::NICKNAME,
				self::REAL_NAME,
				"radio_".self::GENDER,
				self::SCHOOL,
				"select_".self::GRADE,
				"text_".self::EVALUATION				
		));
		$data[self::UID] = $this->app_common->create_uuid();
		$data[self::USER_TYPE] = USER_TYPE_KID;
		$data[self::PARENT_UID] = $parent_uid;
		$time = date("Y-m-d H:i:s", time());
		$data[self::KID_ADD_TIME] = $time;
		$data[self::KID_UPDATE_TIME] = $time;
		$data[self::UPDATE_TIME] = $time;
		$data[self::IS_VALID] = "Y";
		$this->db->insert("user", $data);
		return $this->db->affected_rows();
	}
	
	/**
	 * 根据孩子的uid和家长的uid删除孩子信息
	 * @param unknown $uid
	 * @param unknown $parent_uid
	 */
	public function deleteKid($uid, $parent_uid) {
		$this->db->delete("user", array(self::UID => $uid, self::PARENT_UID => $parent_uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 完善学生信息
	 * @param unknown $uid
	 */
	public function addStudent($uid) {
		$this->load->library("app_http");
		$data = $this->app_http->getPostData4DB(array(
				self::SCHOOL,
				"select_".self::GRADE,
				"text_".self::EVALUATION
		));
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 完善教师信息
	 * @param unknown $uid
	 */
	public function addTeacher($uid) {
		$this->load->library("app_http");
		$data = $this->app_http->getPostData4DB(array(
				self::WHERE_FROM,
				"radio_".self::T_TYPE,
				"select_".self::SPECIALITY,
				"text_".self::EVALUATION
		));
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 更新普通用户类型信息
	 * @param unknown $uid
	 */
	public function updateNormal($uid) {
		$this->load->library("app_http");
		$data = $this->app_http->getPostData4DB(array(
				self::NICKNAME,
				self::REAL_NAME,
				"radio_".self::GENDER,
				self::LOCATION,
				self::PHONE,
				self::QQ,
				self::WEIBO,
				self::WECHAT,
				self::MY_EMAIL
		));
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 更新家长用户类型信息
	 * @param unknown $uid
	 */
	public function updateParent($uid) {
		return $this->updateNormal($uid);
	}
	
	/**
	 * 更新学生用户类型信息
	 * @param unknown $uid
	 */
	public function updateStudent($uid) {
		$this->load->library("app_http");
		$data = $this->app_http->getPostData4DB(array(
				self::NICKNAME,
				self::REAL_NAME,
				"radio_".self::GENDER,
				self::LOCATION,
				self::PHONE,
				self::QQ,
				self::WEIBO,
				self::WECHAT,
				self::MY_EMAIL,
				self::SCHOOL,
				"select_".self::GRADE,
				"text_".self::EVALUATION
		));
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 更新教师用户类型信息
	 * @param unknown $uid
	 */
	public function updateTeacher($uid) {
		$this->load->library("app_http");
		$data = $this->app_http->getPostData4DB(array(
				self::NICKNAME,
				self::REAL_NAME,
				"radio_".self::GENDER,
				self::LOCATION,
				self::PHONE,
				self::QQ,
				self::WEIBO,
				self::WECHAT,
				self::MY_EMAIL,
				self::WHERE_FROM,
				"select_".self::SPECIALITY,
				"radio_".self::T_TYPE,
				"text_".self::EVALUATION
		));
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("user", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 激活或冻结用户信息
	 * @param unknown $uid
	 * @param unknown $is_valid true or false
	 * @return number
	 */
	public function valid($uid, $is_valid) {
		$yes_or_no = $is_valid ? "Y" : "N";
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$data[self::IS_VALID] = $yes_or_no;
		$this->db->update("user", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 根据uid判断该用户是否存在
	 */
	public function userExistByUid($uid) {
		return $this->db->select(self::UID)->from("user")->where(array(self::UID => $uid))->count_all_results();
	}
}