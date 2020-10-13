<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Student extends CI_Controller {
	
	/**
	 * 学生用户跳转到user/student.html
	 */
	public function index() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_PROFILE_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$user_type = $logined_user["user_type"];
			if ($user_type != USER_TYPE_STUDENT) { // 用户不是学生类型
				if ($user_type == USER_TYPE_TEACHER) {
					redirect(base_url()."user/teacher.html");
				} else if ($user_type == USER_TYPE_PARENT) {
					redirect(base_url()."user/parents.html");
				} else if ($user_type == USER_TYPE_NORMAL) {
					redirect(base_url()."user/normal.html");
				}
			} else { // 此用户已经设置为学生类型
				$this->load->model("user_model", "user");
				$query = $this->user->findStudentByUid($logined_user["uid"]);
				if ($query->num_rows() == 1) {
					$data["user"] = $query->row();
				}
				$data["profile"] = true;
				$data["tutorship_admin"] = true;
				$data["student_add_tutorship"] = true;
				$this->load->view("user/student-profile", $data);
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 完善学生信息
	 */
	public function add_student() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_STUDENT) {
				$this->load->helper("form");
				$this->load->library("form_validation");
				$this->lang->load('form_validation', 'chinese');
				$this->form_validation->set_error_delimiters("", "");
				$result_array = array();
				if (!$this->form_validation->run("user_add_stu")) {
					$this->load->library("app_common");
					$result_array = $this->app_common->getFormErrors($result_array, array("school", "select_grade"));
					if (count($result_array) > 0) {
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				} else {
					$this->load->model("user_model", "user");
					if ($this->user->addStudent($logined_user["uid"]) == 1) { // 成功完善学生信息
						$result_array["result"] = "successed";
					} else { //完善学生信息失败
						$result_array["radio_t_type"] = "完善学生信息失败，请稍候重试";
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				}
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			} else if ($user_type == USER_TYPE_PARENT) {
				redirect(base_url()."user/parents.html");
			} else if ($user_type == USER_TYPE_TEACHER) {
				redirect(base_url()."user/teacher.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 跳转到学生用户信息编辑页面
	 */
	public function edit_student() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_STUDENT) {
				$data["title"] = USER_PROFILE_EDIT_PAGE_TITLE;
				$data["profile"] = true;
				$data[LOGINED_USER] = $logined_user;
				$uid = $logined_user["uid"];
				$this->load->model("user_model", "user");
				$query = $this->user->findStudentByUid($logined_user["uid"]);
				if ($query->num_rows() == 1) {
					$data["user"] = $query->row();
				}
				$this->load->view("user/student-profile-edit", $data);
			} else if ($user_type == USER_TYPE_PARENT) {
				redirect(base_url()."user/parents.html");
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			} else if ($user_type == USER_TYPE_TEACHER) {
				redirect(base_url()."user/teacher.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 保存学生用户类型的信息
	 */
	public function save_student() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_STUDENT) {
				$this->load->helper("form");
				$this->load->library("form_validation");
				$this->lang->load('form_validation', 'chinese');
				$this->form_validation->set_error_delimiters("", "");
				$result_array = array();
				if (!$this->form_validation->run("user_save_student")) {
					$this->load->library("app_common");
					$result_array = $this->app_common->getFormErrors($result_array, array("nickname", "radio_gender", "my_email", "school", "select_grade"));
					if (count($result_array) > 0) {
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				} else {
					$this->load->model("user_model", "user");
					if ($this->user->updateStudent($logined_user["uid"]) == 1) { // 成功学生用户信息
						$result_array["result"] = "successed";
					} else { //更新学生用户信息失败
						$result_array["nickname"] = "编辑我的信息失败，请稍候重试";
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				}
			} else if ($user_type == USER_TYPE_PARENT) {
				redirect(base_url()."user/parents.html");
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			} else if ($user_type == USER_TYPE_TEACHER) {
				redirect(base_url()."user/teacher.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
}