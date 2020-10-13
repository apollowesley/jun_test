<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Admin_model extends CI_Model {
	
	const UID = "uid";
	const EMAIL = "email";
	const PASSWORD = "password";
	const GENDER = "gender";
	const REAL_NAME = "real_name";
	const FIGURE_SMALL = "figure_small";
	const FIGURE_NORMAL = "figure_normal";
	const AREA = "area";
	const LOCATION = "location";
	const PHONE = "phone";
	const QQ = "qq";
	const WEIBO = "weibo";
	const WECHAT = "wechat";
	const ADMIN_TYPE = "admin_type";
	const IS_VALID = "is_valid";
	const ADD_TIME = "add_time";
	const ADD_BY_UID = "add_by_uid";
	const LOGIN_TIME = "login_time";
	const UPDATE_TIME = "update_time";
	const UPDATE_BY_UID = "update_by_uid";
	
	/**
	 * 初始化一个管理员数据，只会被执行一次，controller admin/profile/init_admin
	 */
	public function init_admin() {
		$this->load->library("app_common");
		$time = date("Y-m-d H:i:s", time());
		$uid = $this->app_common->create_uuid();
		$data = array(self::UID => $uid,
			self::EMAIL => "wgssmarter@126.com",
			self::PASSWORD => md5("666666"),
			self::REAL_NAME => "王根参",
			self::GENDER => "男",
			self::AREA => "赣州市章贡区",
			self::LOCATION => "赣州市章贡区经济开发区塞纳春天小区13栋904",
			self::PHONE => "18507074625",
			self::QQ => "847315251",
			self::WEIBO => "Wgssmart",
			self::WECHAT => "847315251",
			self::ADMIN_TYPE => ADMIN_TYPE_SUPER,
			self::IS_VALID => "Y",
			self::ADD_BY_UID => $uid,
			self::ADD_TIME => $time,
			self::UPDATE_BY_UID => $uid,
			self::UPDATE_TIME => $time,
			self::LOGIN_TIME => $time);
		$this->db->insert("admin", $data);
		return $this->db->affected_rows();
	}
	
	/**
	 * 添加管理员信息
	 * @param unknown $_data
	 */
	public function add($_data) {
		$this->load->library("app_http");
		$this->load->library("app_common");
		$data = $this->app_http->getPostData4DB(array(
				self::EMAIL,
				self::REAL_NAME,
				self::GENDER,
				self::AREA,
				self::LOCATION,
				self::PHONE,
				self::QQ,
				self::WEIBO,
				self::WECHAT,
				self::ADMIN_TYPE
		));
		$time = date("Y-m-d H:i:s", time());
		$data[self::UID] = $this->app_common->create_uuid();
		$data[self::PASSWORD] = md5($this->input->post(self::PASSWORD));
		$data[self::ADD_BY_UID] = $_data[LOGINED_ADMIN]["uid"];
		$data[self::ADD_TIME] = $time;
		$data[self::UPDATE_TIME] = $time;
		$data[self::UPDATE_BY_UID] = $_data[LOGINED_ADMIN]["uid"]; 
		$this->db->insert("admin", $data);
		return $this->db->affected_rows();
	}
	
	/**
	 * 按照登录邮箱和密码查询管理员
	 * @param unknown $email
	 * @param unknown $password
	 */
	public function findAdmin($email, $password) {
		$this->db->select(self::UID.",".self::FIGURE_NORMAL.",".self::REAL_NAME.",".self::ADMIN_TYPE)
			->from("admin")->where(array(self::EMAIL => $email, self::PASSWORD => md5($password)));
		return $this->db->get();
	}
	
	/**
	 * 根据uid查询管理员的所有信息
	 * @param unknown $uid
	 */
	public function findAdminByUid($uid) {
		return $this->db->get_where("admin", array(self::UID => $uid));
	}
	
	/**
	 * 查询所有管理员的所有信息
	 */
	public function findAllAdmin() {
		$this->db->from("admin")->order_by(self::ADD_TIME);
		return $this->db->get();
	}
	
	/**
	 * 管理员登录时，需要更新的数据
	 * @param unknown $uid
	 */
	public function updateWhenLogin($uid) {
		$time = date("Y-m-d H:i:s", time());
		$data[self::LOGIN_TIME] = $time;
		$this->db->update("admin", $data, array(self::UID => $uid));
	}
	
	/**
	 * 由超级管理员更新单个管理员的信息
	 * @param unknown $_data
	 */
	public function updateByAdmin($uid, $admin_uid) {
		$this->load->library("app_http");
		$data = $this->app_http->getPostData4DB(array(
				self::EMAIL,
				self::REAL_NAME,
				self::GENDER,
				self::AREA,
				self::LOCATION,
				self::PHONE,
				self::QQ,
				self::WEIBO,
				self::WECHAT,
				self::ADMIN_TYPE
		));
		$data[self::UPDATE_BY_UID] = $admin_uid;
		$data[self::UPDATE_TIME] = date("Y-m-d H:i:s", time());
		$this->db->update("admin", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 由管理员更新自己的信息
	 * @param unknown $_data
	 */
	public function updateByUid($uid) {
		$this->load->library("app_http");
		$data = $this->app_http->getPostData4DB(array(
				self::EMAIL,
				self::REAL_NAME,
				self::GENDER,
				self::AREA,
				self::LOCATION,
				self::PHONE,
				self::QQ,
				self::WEIBO,
				self::WECHAT,
				self::ADMIN_TYPE
		));
		$data[self::UPDATE_BY_UID] = $uid;
		$data[self::UPDATE_TIME] = date("Y-m-d H:i:s", time());
		$this->db->update("admin", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 激活或冻结管理员信息
	 * @param unknown $uid
	 * @param unknown $is_valid true or false
	 * @return number
	 */
	public function valid($uid, $is_valid) {
		$yes_or_no = $is_valid ? "Y" : "N";
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$data[self::IS_VALID] = $yes_or_no;
		$this->db->update("admin", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 判断管理员账号是否被冻结，主要用于管理员登录时
	 * @param unknown $email
	 */
	public function isValid($email) {
		$result = $this->db->select(self::IS_VALID)->from("admin")->where(array(self::EMAIL => $email))->get()->result();
		if (count($result) == 1) {
			$yes_or_no = $result[0]->is_valid;
			if ($yes_or_no == "Y") {
				return true;
			} else {
				return false;
			}
		}
	}
	
}