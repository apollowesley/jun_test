<?php

class Area_model extends CI_Model {
	
	const ID = "id";
	const PARENT_ID = "parent_id";
	const NAME = "name";
	const CREATE_TIME = "create_time";
	const IS_HOT = "is_hot";
	const CREATE_AUTHOR = "create_author";
	const LAST_UPDATE_TIME = "last_update_time";
	const LAST_UPDATE_AUTHOR = "last_update_author";
	const NAME_LETTER = "name_letter";
	const TYPE = "type";
	
	public function add($_data) {
		$this->load->library("apphttp");
		$data = $this->apphttp->getPostData4DB(array(
			self::PARENT_ID,
				self::NAME,
				self::NAME_LETTER,
				self::TYPE
		), NULL, NULL);
		$time = date("Y-m-d H:i:s", time());
		$data[self::CREATE_AUTHOR] = $_data[ADMIN_EAMIL];
		$data[self::CREATE_TIME] = $time;
		$data[self::LAST_UPDATE_AUTHOR] = $_data[ADMIN_EAMIL];
		$data[self::LAST_UPDATE_TIME] = $time; 
		$this->db->insert("area", $data);
		return $this->db->affected_rows();
	}
	
	public function all() {
		$parent_id = $this->input->post(self::PARENT_ID);
		$data = array();
		if (isset($parent_id) && $parent_id != NULL) {
			$data[self::PARENT_ID] = $parent_id;
		} else {
			$data[self::PARENT_ID] = 0;
		}
		$this->db->from("area")->where(array(self::PARENT_ID => $data[self::PARENT_ID]))->order_by(self::NAME_LETTER);
		return $this->db->get();
	}
	
	public function entry() {
		return $this->db->get_where("area", array(self::ID => $this->input->post("area_id")));
	}
	
	public function update($_data) {
		$this->load->library("apphttp");
		$data = $this->apphttp->getPostData4DB(array(
				self::ID,
				self::NAME,
				self::NAME_LETTER,
				self::IS_HOT,
				self::TYPE
		), "area", NULL);
		$data[self::LAST_UPDATE_AUTHOR] = $_data[ADMIN_EAMIL];
		$data[self::LAST_UPDATE_TIME] = date("Y-m-d H:i:s", time());
		$this->db->update("area", $data, array(self::ID => $data[self::ID]));
		return $this->db->affected_rows();
	}
	
	public function delete() {
		$id = $this->input->post("area_id");
		$this->db->from("area")->where(array(self::PARENT_ID => $id));
		if ($this->db->count_all_results() > 0) {
			return -1;
		} else {
			$this->db->delete("area", array(self::ID => $id));
			return $this->db->affected_rows();
		}
	}
	
}