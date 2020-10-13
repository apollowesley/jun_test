<?php
class Enterprise_model extends CI_Model {
	
	const ID = "id";
	const CODE = "code";
	const NAME_ZH = "name_zh";
	const NAME_EN = "name_en";
	const SHORT_NAME_ZH = "short_name_zh";
	const SHORT_NAME_EN = "short_name_en";
	const NAME_LETTER_ZH = "name_letter_zh";
	const ADDRESS = "address";
	const AREA = "area";
	const CATEGORY = "category";
	const SET_DATE = "set_date";
	const FOUNDER = "founder";
	const LOGO = "logo";
	const TYPE = "type";
	const VISION = "vision";
	const SLOGAN = "slogan";
	const COORDINATE = "coordinate";
	const CONTACT_NAME = "contact_name";
	const CONTACT_TEL = "contact_tel";
	const CONTACT_EMAIL = "contact_email";
	const WEBSITE = "website";
	const QQ = "qq";
	const WECHAT = "wechat";
	const WECHAT_BARCODE = "wechat_barcode";
	const WEIBO = "weibo";
	const SHOP_WEBSITE = "shop_website";
	const INTRODUCTION = "introduction";
	const SERVICE = "service";
	const CULTURE = "culture";
	const VIEW_COUNT = "view_count";
	const COLLECT_COUNT = "collect_count";
	const COMMENT_COUNT = "comment_count";
	const ENABLED_TYPE = "enabled_type";
	const CREATE_TIME = "create_time";
	const CREATE_AUTHOR = "create_author";
	const LAST_UPDATE_TIME = "last_update_time";
	const LAST_UPDATE_AUTHOR = "last_update_author";
	const DYNAMIC_LINK = "dynamic_link";
	
	public function add($_data) {
		$this->load->library("apphttp");
		$data = $this->apphttp->getPostData4DB(array(
				self::CODE,
				self::NAME_ZH,
				self::NAME_EN,
				self::SHORT_NAME_ZH,
				self::SHORT_NAME_EN,
				self::NAME_LETTER_ZH,
				self::AREA,
				self::CATEGORY,
				self::ADDRESS,
				self::SET_DATE,
				self::FOUNDER,
				self::LOGO,
				self::TYPE,
				self::VISION,
				self::SLOGAN,
				self::COORDINATE,
				self::CONTACT_NAME,
				self::CONTACT_EMAIL,
				self::CONTACT_TEL,
				self::WEBSITE,
				self::QQ,
				self::WECHAT,
				self::WECHAT_BARCODE,
				self::WEIBO,
				self::SHOP_WEBSITE,
				self::INTRODUCTION,
				self::SERVICE,
				self::CULTURE
		), NULL, NULL);
		$time = date("Y-m-d H:i:s", time());
		$data[self::CREATE_AUTHOR] = $_data[ADMIN_EAMIL];
		$data[self::CREATE_TIME] = $time;
		$data[self::LAST_UPDATE_AUTHOR] = $_data[ADMIN_EAMIL];
		$data[self::LAST_UPDATE_TIME] = $time;
		$this->db->insert("enterprise", $data);
		return $this->db->insert_id();
	}
	
	public function findByPage($pageNo, $pageSize) {
		$this->db->from("enterprise")->order_by(self::LAST_UPDATE_TIME, "desc")->limit($pageSize, ($pageNo - 1) * $pageSize);
		return $this->db->get();
	}
	
	public function findByPageWithEnabledType($pageNo, $pageSize, $enabled_type) {
		$this->db->from("enterprise")->where(array(self::ENABLED_TYPE => $enabled_type))->order_by(self::LAST_UPDATE_TIME, "desc")->limit($pageSize, ($pageNo - 1) * $pageSize);
		return $this->db->get();
	}
	
	public function total() {
		return $this->db->select(self::ID)->from("enterprise")->count_all_results();
	}
	
	public function totalWithEnabledType($enabled_type) {
		return $this->db->select(self::ID)->from("enterprise")->where(array(self::ENABLED_TYPE => $enabled_type))->count_all_results();
	}
	
	public function entry($id) {
		return $this->db->get_where("enterprise", array(self::ID => $id));
	}
	
	public function update($_data) {
		$this->load->library("apphttp");
		$data = $this->apphttp->getPostData4DB(array(
				self::ID,
				self::CODE,
				self::NAME_ZH,
				self::NAME_EN,
				self::SHORT_NAME_ZH,
				self::SHORT_NAME_EN,
				self::NAME_LETTER_ZH,
				self::AREA,
				self::CATEGORY,
				self::ADDRESS,
				self::SET_DATE,
				self::FOUNDER,
				self::LOGO,
				self::TYPE,
				self::VISION,
				self::SLOGAN,
				self::COORDINATE,
				self::CONTACT_NAME,
				self::CONTACT_EMAIL,
				self::CONTACT_TEL,
				self::WEBSITE,
				self::QQ,
				self::WECHAT,
				self::WECHAT_BARCODE,
				self::WEIBO,
				self::SHOP_WEBSITE,
				self::INTRODUCTION,
				self::SERVICE,
				self::CULTURE
		), "enterprise", NULL);
		$time = date("Y-m-d H:i:s", time());
		$data[self::CREATE_AUTHOR] = $_data[ADMIN_EAMIL];
		$data[self::CREATE_TIME] = $time;
		$data[self::LAST_UPDATE_AUTHOR] = $_data[ADMIN_EAMIL];
		$data[self::LAST_UPDATE_TIME] = $time;
		$this->db->update("enterprise", $data, array(self::ID => $data[self::ID]));
		return $this->db->affected_rows();
	}
	
	public function enable($enabled_type) {
		$data[self::ENABLED_TYPE] = $enabled_type;
		$this->db->update("enterprise", $data, array(self::ID => $this->input->post(self::ID)));
		return $this->db->affected_rows();
	}

}