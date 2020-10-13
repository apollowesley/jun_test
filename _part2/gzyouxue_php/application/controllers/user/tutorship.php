<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Tutorship extends CI_Controller {
	
	/**
	 * 显示用户所有辅导
	 */
	public function all() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_PARENT) {
				$data["title"] = USER_TUTORSHIPS_TITLE;
				$data[LOGINED_USER] = $logined_user;
				$data["tutorship_admin"] = true;
				$data["tutorships"] = true;
				$data["parent_profile"] = true;
				$data["parent_add_tutorship"] = true;
				$this->load->model("tutorship_model", "tutorship");
				$query = $this->tutorship->findByAddUid($logined_user["uid"]);
				$data["tutorships"] = $query->result();
				$this->load->view("user/tutorships", $data);
			} else if ($user_type == USER_TYPE_STUDENT) {
				$data["title"] = USER_TUTORSHIPS_TITLE;
				$data[LOGINED_USER] = $logined_user;
				$data["tutorship_admin"] = true;
				$data["tutorships"] = true;
				$data["student_add_tutorship"] = true;
				$this->load->view("user/tutorships", $data);
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
	 * 家长用户添加辅导前先选择孩子，跳转到选择孩子页面
	 */
	public function add_tutorship_student() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_PARENT) {
				$data["title"] = USER_ADD_TUTORSHIP_KID_TITLE;
				$data[LOGINED_USER] = $logined_user;
				$data["parent_profile"] = true;
				$data["tutorship_admin"] = true;
				$data["parent_add_tutorship"] = true; 
				$data["add_tutorship"] = true;
				$this->load->model("user_model", "user");
				$query = $this->user->findKidsByParentUid($logined_user["uid"]);
				$data["students"] = $query->result();
				$this->load->view("user/add-tutorship-student", $data);
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				redirect(base_url()."user/student.html");
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
	 * 跳转到添加辅导信息页面
	 */
	public function add_tutorship() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			$student_uid = $this->input->get("student");
			$this->load->model("user_model", "user");
			if ($user_type == USER_TYPE_PARENT) {
				if (isset($student_uid) && !empty($student_uid) && $this->user->userExistByUid($student_uid) == 1) {
					$this->session->set_userdata("student_uid", $student_uid);
					$data[LOGINED_USER] = $logined_user;
					$data["title"] = USER_ADD_TUTORSHIP_INFO_TITLE;
					$data["parent_profile"] = true;
					$data["tutorship_admin"] = true;
					$data["parent_add_tutorship"] = true;
					$data["add_tutorship"] = true;
					$this->load->view("user/add-tutorship-info", $data);
				} else {
					redirect(base_url()."user/parents.html");
				}
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				$data[LOGINED_USER] = $logined_user;
				$data["title"] = USER_ADD_TUTORSHIP_INFO_TITLE;
				$data["tutorship_admin"] = true;
				$data["student_add_tutorship"] = true;
				$data["add_tutorship"] = true;
				$this->load->view("user/add-tutorship-info", $data);
			} else if ($user_type == USER_TYPE_TEACHER) {
				redirect(base_url()."user/teacher.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 添加辅导信息
	 */
	public function do_add_tutorship() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			$student_uid = $this->session->userdata("student_uid");
			if ($user_type == USER_TYPE_PARENT) {
				if (isset($student_uid) && !empty($student_uid)) {
					$this->load->helper("form");
					$this->load->library("form_validation");
					$this->lang->load('form_validation', 'chinese');
					$this->form_validation->set_error_delimiters("", "");
					$result_array = array();
					if (!$this->form_validation->run("user_add_tutorship")) {
						$this->load->library("app_common");
						$result_array = $this->app_common->getFormErrors($result_array, array("subject", "hours", "days", "start_time"));
						if (count($result_array) > 0) {
							$result_array["result"] = "failed";
						}
						echo json_encode($result_array);
					} else {
						$this->load->model("tutorship_model", "tutorship");
						if ($this->tutorship->add($student_uid, $logined_user["uid"]) == 1) {
							$this->session->unset_userdata("student_uid");
							$result_array["result"] = "successed";	
						} else {
							$result_array["result"] = "failed";
							$result_array["subject"] = "添加辅导信息失败，请稍候重试";
						}
						echo json_encode($result_array);
					}
				} else {
					redirect(base_url()."user/tutorship/add_tutorship_student.html");
				}
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				$this->load->helper("form");
				$this->load->library("form_validation");
				$this->lang->load('form_validation', 'chinese');
				$this->form_validation->set_error_delimiters("", "");
				$result_array = array();
				if (!$this->form_validation->run("user_add_tutorship")) {
					$this->load->library("app_common");
					$result_array = $this->app_common->getFormErrors($result_array, array("subject", "hours", "days", "start_time"));
					if (count($result_array) > 0) {
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				} else {
					$this->load->model("tutorship_model", "tutorship");
					if ($this->tutorship->add($student_uid) == 1) {
						$this->session->unset_userdata("student_uid");
						$result_array["result"] = "successed";
					} else {
						$result_array["result"] = "failed";
						$result_array["subject"] = "添加辅导信息失败，请稍候重试";
					}
					echo json_encode($result_array);
				}
			} else if ($user_type == USER_TYPE_PARENT) {
				redirect(base_url()."user/parents.html");
			} else if ($user_type == USER_TYPE_TEACHER) {
				redirect(base_url()."user/teacher.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
}