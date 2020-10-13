<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Teacher extends CI_Controller {
	
	/**
	 * 教师用户跳转到user/teacher.html
	 */
	public function index() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_PROFILE_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$user_type = $logined_user["user_type"];
			if ($user_type != USER_TYPE_TEACHER) { // 用户不是教师类型
				if ($user_type == USER_TYPE_STUDENT) {
					redirect(base_url()."user/profile/student.html");
				} else if ($user_type == USER_TYPE_PARENT) {
					redirect(base_url()."user/profile/parents.html");
				} else if ($user_type == USER_TYPE_NORMAL) {
					redirect(base_url()."user/profile/normal.html");
				}
			} else { // 此用户已经设置为教师类型
				$this->load->model("user_model", "user");
				$query = $this->user->findTeacherByUid($logined_user["uid"]);
				if ($query->num_rows() == 1) {
					$data["user"] = $query->row();
				}
				$data["profile"] = true;
				$data["tutorship_admin"] = true;
				$this->load->view("user/teacher-profile", $data);
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 完善教师信息
	 */
	public function add_teacher() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_TEACHER) {
				$this->load->helper("form");
				$this->load->library("form_validation");
				$this->lang->load('form_validation', 'chinese');
				$this->form_validation->set_error_delimiters("", "");
				$result_array = array();
				if (!$this->form_validation->run("user_add_teacher")) {
					$this->load->library("app_common");
					$result_array = $this->app_common->getFormErrors($result_array, array("where_from", "radio_t_type", "select_speciality"));
					if (count($result_array) > 0) {
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				} else {
					$this->load->model("user_model", "user");
					if ($this->user->addTeacher($logined_user["uid"]) == 1) { // 成功完善教师信息
						$result_array["result"] = "successed";
					} else { //完善教师信息失败
						$result_array["radio_t_type"] = "完善教师信息失败，请稍候重试";
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				}
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				redirect(base_url()."user/student.html");
			} else if ($user_type == USER_TYPE_PARENT) {
				redirect(base_url()."user/parents.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 跳转到教师用户信息编辑页面
	 */
	public function edit_teacher() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_TEACHER) {
				$data["title"] = USER_PROFILE_EDIT_PAGE_TITLE;
				$data["profile"] = true;
				$data["tutorship_admin"] = true;
				$data[LOGINED_USER] = $logined_user;
				$uid = $logined_user["uid"];
				$this->load->model("user_model", "user");
				$query = $this->user->findTeacherByUid($logined_user["uid"]);
				if ($query->num_rows() == 1) {
					$data["user"] = $query->row();
				}
				$this->load->view("user/teacher-profile-edit", $data);
			} else if ($user_type == USER_TYPE_PARENT) {
				redirect(base_url()."user/parents.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				redirect(base_url()."user/student.html");
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 保存教师用户类型的信息
	 */
	public function save_teacher() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_TEACHER) {
				$this->load->helper("form");
				$this->load->library("form_validation");
				$this->lang->load('form_validation', 'chinese');
				$this->form_validation->set_error_delimiters("", "");
				$result_array = array();
				if (!$this->form_validation->run("user_save_teacher")) {
					$nickname_error = form_error("nickname");
					$gender_error = form_error("radio_gender");
					$email_error = form_error("my_email");
					$type_error = form_error("radio_t_type");
					$where_from_error = form_error("where_from");
					$speciality_error = form_error("select_speciality");
					if ($nickname_error != "") {
						$result_array["nickname"] = $nickname_error;
					}
					if ($gender_error != "") {
						$result_array["radio_gender"] = $gender_error;
					}
					if ($email_error != "") {
						$result_array["my_email"] = $email_error;
					}
					if ($where_from_error != "") {
						$result_array["where_from"] = $where_from_error;
					}
					if ($type_error != "") {
						$result_array["radio_t_type"] = $type_error;
					}
					if ($speciality_error != "") {
						$result_array["select_speciality"] = $speciality_error;
					}
					if (count($result_array) > 0) {
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				} else {
					$this->load->model("user_model", "user");
					if ($this->user->updateTeacher($logined_user["uid"]) == 1) { // 成功更新教师信息
						$result_array["result"] = "successed";
					} else { //更新教师用户信息失败
						$result_array["nickname"] = "编辑我的信息失败，请稍候重试";
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				}
			} else if ($user_type == USER_TYPE_PARENT) {
				redirect(base_url()."user/parents.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				redirect(base_url()."user/student.html");
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
}