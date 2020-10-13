<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Tutorship_model extends CI_Model {
	
	const UID = "uid";
	const SUBJECT = "subject";
	const HOURS = "hours";
	const DAYS = "days";
	const LOCATION = "location";
	const CLASS_TIME = "class_time";
	const START_TIME = "start_time";
	const REQUEST = "request";
	const REMARKS = "remarks";
	const IS_VALID = "is_valid";
	const ADD_TIME = "add_time";
	const ADD_BY_UID = "add_by_uid";
	const UPDATE_TIME = "update_time";
	const STUDENT_LATEST_THINKING = "student_latest_thinking";
	const TEACHER_LATEST_THINKING = "teacher_latest_thinking";
	const STATUS = "status";
	const STUDENT_UID = "student_uid";
	const TEACHER_UID = "teacher_uid";
	
	/**
	 * 家长用户通过add_by_uid查询所有辅导信息
	 * @param unknown $add_by_uid
	 */
	public function findByAddUid($add_by_uid) {
	 	$this->db->select("student.uid stu_uid, student.nickname stu_nickname, student.school, tutorship.uid tu_uid, tutorship.subject, tutorship.hours, tutorship.location,"
	 			."tutorship.student_latest_thinking, tutorship.teacher_latest_thinking, tutorship.status, tutorship.add_time, tutorship.update_time, teacher.uid te_uid, teacher.nickname te_nickname, teacher.where_from, teacher.speciality")
	 	->from("user student")
	 	->join("tutorship", "student.parent_uid = tutorship.add_by_uid and student.uid = tutorship.student_uid", "right")
	 	->join("user teacher", "teacher.uid = tutorship.teacher_uid", "left")
	 	->where(array("tutorship.add_by_uid" => $add_by_uid, "tutorship.is_valid" => "Y"));
	 	return $this->db->get();
	}
	
	/**
	 * 学生用户通过学生uid查询所有辅导信息
	 * @param unknown $student_uid
	 */
	public function findByStuUid($student_uid) {
		$this->db->select("student.uid stu_uid, student.nickname stu_nickname, student.school, tutorship.uid tu_uid, tutorship.subject, tutorship.hours, tutorship.location,"
				."tutorship.student_latest_thinking, tutorship.teacher_latest_thinking, tutorship.status, tutorship.add_time, tutorship.update_time, teacher.uid te_uid, teacher.nickname te_nickname, teacher.where_from, teacher.speciality")
				->from("user student")
				->join("tutorship", "student.uid = tutorship.student_uid", "right")
				->join("user teacher", "teacher.uid = tutorship.teacher_uid", "left")
				->where(array("tutorship.student_uid" => $student_uid, "tutorship.is_valid" => "Y"));
		return $this->db->get();
	}
	
	/**
	 * 根据辅导信息的uid查询辅导信息
	 * @param unknown $uid
	 */
	public function findByUid($uid) {
		
	}
	
	/**
	 * 查询所有的辅导信息
	 */
	public function findAllTutorships() {
		$this->db->select("student.uid stu_uid, student.nickname stu_nickname, student.school, tutorship.uid tu_uid, tutorship.subject, tutorship.hours, tutorship.location,"
				."tutorship.student_latest_thinking, tutorship.teacher_latest_thinking, tutorship.status, tutorship.add_time, tutorship.update_time, teacher.uid te_uid, teacher.nickname te_nickname, teacher.where_from, teacher.speciality")
				->from("user student")
				->join("tutorship", "student.uid = tutorship.student_uid", "right")
				->join("user teacher", "teacher.uid = tutorship.teacher_uid", "left")
				->where(array("tutorship.is_valid" => "Y"));
		return $this->db->get();
	}
	
	/**
	 * 添加辅导信息
	 */
	public function add($student_uid, $parent_uid = "") {
		$this->load->library("app_http");
		$this->load->library("app_common");
		$data = $this->app_http->getPostData4DB(array(
				self::SUBJECT,
				self::HOURS,
				self::DAYS,
				self::LOCATION,
				self::CLASS_TIME,
				self::START_TIME,
				"text_".self::REQUEST,
				"text_".self::REMARKS				
		));
		$data[self::STUDENT_UID] = $student_uid;
		$data[self::UID] = $this->app_common->create_uuid();
		$time = date("Y-m-d H:i:s", time());
		$data[self::ADD_TIME] = $time;
		$data[self::UPDATE_TIME] = $time;
		$data[self::IS_VALID] = "Y";
		$data[self::STATUS] = TUTORSHIP_STATUS_BEGIN;
		if ($parent_uid != "") {
			$data[self::ADD_BY_UID] = $parent_uid;
		}
		$this->db->insert("tutorship", $data);
		return $this->db->affected_rows();
	}
	
	/**
	 * 确认由哪名教师接受此辅导
	 * @param unknown $teacher_uid
	 */
	public function confirmTeacher($uid, $teacher_uid) {
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$data[self::TEACHER_UID] = $teacher_uid;
		$data[self::STATUS] = TUTORSHIP_STATUS_ING;
		$this->db->update("tutorship", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 根据辅导uid更新辅导信息
	 * @param unknown $uid
	 */
	public function updateTutorship($uid) {
		$this->load->library("app_http");
		$data = $this->app_http->getPostData4DB(array(
				self::SUBJECT,
				self::HOURS,
				self::DAYS,
				self::LOCATION,
				self::CLASS_TIME,
				self::START_TIME,
				"text_".self::REQUEST,
				"text_".self::REMARKS
		));
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$this->db->update("tutorship", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 设置辅导已结束
	 * @param unknown $uid
	 */
	public function completeTutorship($uid) {
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$data[self::STATUS] = TUTORSHIP_STATUS_END;
		$this->db->update("tutorship", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 删除指定的辅导信息
	 * @param unknown $uid
	 */
	public function deleteTutorship($uid, $add_by_uid) {
		$this->db->delete("tutorship", array(self::UID => $uid, self::ADD_BY_UID => $add_by_uid));
		return $this->db->affected_rows();
	}
	
	/**
	 * 激活或冻结辅导信息
	 * @param unknown $uid
	 * @param unknown $is_valid true or false
	 * @return number
	 */
	public function valid($uid, $is_valid) {
		$yes_or_no = $is_valid ? "Y" : "N";
		$time = date("Y-m-d H:i:s", time());
		$data[self::UPDATE_TIME] = $time;
		$data[self::IS_VALID] = $yes_or_no;
		$this->db->update("tutorship", $data, array(self::UID => $uid));
		return $this->db->affected_rows();
	}
	
}