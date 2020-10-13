<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Normal extends CI_Controller {
	
	/**
	 * 普通用户登录后，跳转到user/normal.html
	 */
	public function index() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_PROFILE_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$user_type = $logined_user["user_type"];
			if ($user_type != USER_TYPE_NORMAL) { // 用户已经设置了用户类型，则不再访问些页面
				if ($user_type == USER_TYPE_STUDENT) {
					redirect(base_url()."user/student.html");
				} else if ($user_type == USER_TYPE_PARENT) {
					redirect(base_url()."user/parents.html");
				} else if ($user_type == USER_TYPE_TEACHER) {
					redirect(base_url()."user/teacher.html");
				}
			} else { // 此用户还未设置用户类型
				$this->load->model("user_model", "user");
				$query = $this->user->findNormalByUid($logined_user["uid"]);
				if ($query->num_rows() == 1) {
					$data["user"] = $query->row();
				}
				$data["profile"] = true;
				$this->load->view("user/normal-profile", $data);
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 当用户设置为家长用户类型后，跳转到添加孩子信息页面
	 */
	public function to_add_kid() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_PROFILE_ADD_KID_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$user_type = $logined_user["user_type"];
			if ($user_type != USER_TYPE_NORMAL) { // 用户已经设置用户类型，不是普通类型
				if ($user_type == USER_TYPE_STUDENT) {
					redirect(base_url()."user/student.html");
				} else if ($user_type == USER_TYPE_PARENT) {
					redirect(base_url()."user/parents.html");
				} else if ($user_type == USER_TYPE_TEACHER) {
					redirect(base_url()."user/teacher.html");
				}
			} else { // 此用户仍是普通类型
				$this->load->model("user_model", "user");
				if ($this->user->updateUserType($logined_user["uid"], USER_TYPE_PARENT) == 1) { // 更新用户类型成功
					// 更新session数据
					$logined_user["user_type"] = USER_TYPE_PARENT;
					$this->session->set_userdata(LOGINED_USER, $logined_user);
					$data[LOGINED_USER] = $logined_user;
					$data["parent_profile"] = true;
					$data["kids"] = true;
					$data["tutorship_admin"] = true;
					$data["parent_add_tutorship"] = true;
					$this->load->view("user/add-kid", $data);
				} else { // 更新用户类型失败
					redirect(base_url()."user/normal.html");
				}
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 当用户设置为学生类型用户后，跳转到完善学生信息页面
	 */
	public function to_student() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_PROFILE_COMPLETE_STUDENT_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$user_type = $logined_user["user_type"];
			if ($user_type != USER_TYPE_NORMAL) { // 用户已经设置用户类型，不是普通类型
				if ($user_type == USER_TYPE_STUDENT) {
					redirect(base_url()."user/student.html");
				} else if ($user_type == USER_TYPE_PARENT) {
					redirect(base_url()."user/parents.html");
				} else if ($user_type == USER_TYPE_TEACHER) {
					redirect(base_url()."user/teacher.html");
				}
			} else { // 此用户仍是普通类型
				$this->load->model("user_model", "user");
				if ($this->user->updateUserType($logined_user["uid"], USER_TYPE_STUDENT) == 1) { // 更新用户类型成功
					// 更新session数据
					$logined_user["user_type"] = USER_TYPE_STUDENT;
					$this->session->set_userdata(LOGINED_USER, $logined_user);
					$data[LOGINED_USER] = $logined_user;
					$data["profile"] = true;
					$data["tutorship_admin"] = true;
					$data["student_add_tutorship"] = true;
					$this->load->view("user/to-student", $data);
				} else { // 更新用户类型失败
					redirect(base_url()."user/normal.html");
				}
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 当用户设置为教师用户类型后，跳转到完善教师信息页面
	 */
	public function to_teacher() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_PROFILE_COMPLETE_TEACHER_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$user_type = $logined_user["user_type"];
			if ($user_type != USER_TYPE_NORMAL) { // 用户已经设置用户类型，不是普通类型
				if ($user_type == USER_TYPE_STUDENT) {
					redirect(base_url()."user/student.html");
				} else if ($user_type == USER_TYPE_PARENT) {
					redirect(base_url()."user/parents.html");
				} else if ($user_type == USER_TYPE_TEACHER) {
					redirect(base_url()."user/teacher.html");
				}
			} else { // 此用户仍是普通类型
				$this->load->model("user_model", "user");
				if ($this->user->updateUserType($logined_user["uid"], USER_TYPE_TEACHER) == 1) { // 更新用户类型成功
					// 更新session数据
					$logined_user["user_type"] = USER_TYPE_TEACHER;
					$this->session->set_userdata(LOGINED_USER, $logined_user);
					$data[LOGINED_USER] = $logined_user;
					$data["profile"] = true;
					$data["tutorship_admin"] = true;
					$this->load->view("user/to-teacher", $data);
				} else { // 更新用户类型失败
					redirect(base_url()."user/normal.html");
				}
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 跳转到普通用户信息编辑页面
	 */
	public function edit_normal() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_NORMAL) {
				$data["title"] = USER_PROFILE_EDIT_PAGE_TITLE;
				$data["profile"] = true;
				$data[LOGINED_USER] = $logined_user;
				$uid = $logined_user["uid"];
				$this->load->model("user_model", "user");
				$query = $this->user->findNormalByUid($logined_user["uid"]);
				if ($query->num_rows() == 1) {
					$data["user"] = $query->row();
				}
				$this->load->view("user/normal-profile-edit", $data);
			} else if ($user_type == USER_TYPE_PARENT) {
				redirect(base_url()."user/parents.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				redirect(base_url()."user/student.html");
			} else if ($user_type == USER_TYPE_TEACHER) {
				redirect(base_url()."user/teacher.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 保存普通用户类型的信息
	 */
	public function save_normal() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_NORMAL) {
				$this->load->helper("form");
				$this->load->library("form_validation");
				$this->lang->load('form_validation', 'chinese');
				$this->form_validation->set_error_delimiters("", "");
				$result_array = array();
				if (!$this->form_validation->run("user_save_normal")) {
					$this->load->library("app_common");
					$result_array = $this->app_common->getFormErrors($result_array, array("nickname", "radio_gender", "my_email"));
					if (count($result_array) > 0) {
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				} else {
					$this->load->model("user_model", "user");
					if ($this->user->updateNormal($logined_user["uid"]) == 1) { // 成功更新用户信息
						$result_array["result"] = "successed";
					} else { //更新用户信息失败
						$result_array["nickname"] = "编辑我的信息失败，请稍候重试";
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				}
			} else if ($user_type == USER_TYPE_PARENT) {
				redirect(base_url()."user/parents.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				redirect(base_url()."user/student.html");
			} else if ($user_type == USER_TYPE_TEACHER) {
				redirect(base_url()."user/teacher.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
}