<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Parents extends CI_Controller {
	
	/**
	 * 家长用户跳转到user/parents.html
	 */
	public function index() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_PROFILE_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$user_type = $logined_user["user_type"];
			if ($user_type != USER_TYPE_PARENT) { // 用户不是家长类型
				if ($user_type == USER_TYPE_STUDENT) {
					redirect(base_url()."user/student.html");
				} else if ($user_type == USER_TYPE_TEACHER) {
					redirect(base_url()."user/teacher.html");
				} else if ($user_type == USER_TYPE_NORMAL) {
					redirect(base_url()."user/normal.html");
				}
			} else { // 此用户已经设置为家长类型
				$this->load->model("user_model", "user");
				$query = $this->user->findParentByUid($logined_user["uid"]);
				if ($query->num_rows() == 1) {
					$data["user"] = $query->row();
				}
				$data["profile"] = true;
				$data["parent_profile"] = true;
				$data["tutorship_admin"] = true;
				$data["parent_add_tutorship"] = true;
				$this->load->view("user/parent-profile", $data);
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 显示所有孩子，跳转到user/parents/kids.html
	 */
	public function kids() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_MY_KIDS_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$user_type = $logined_user[SESSION_USER_TYPE];
			if ($user_type == USER_TYPE_PARENT) {
				$this->load->model("user_model", "user");
				$kids = $this->user->findKidsByParentUid($logined_user["uid"])->result();
				$data["all_kids"] = $kids;
				$data["parent_profile"] = true;
				$data["kids"] = true;
				$data["tutorship_admin"] = true;
				$data["parent_add_tutorship"] = true;
				$this->load->view("user/kids", $data);
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				redirect(base_url()."user/student.html");
			} else if ($user_type == USER_TYPE_TEACHER) {
				redirect(base_url()."user/teacher.html");
			} else {
				redirect(base_url()."page_not_found.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 跳转到添加孩子信息页面
	 */
	public function add_kid() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_PROFILE_ADD_KID_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$user_type = $logined_user["user_type"];
			if ($user_type != USER_TYPE_PARENT) { // 如果该用户不是家长用户类型
				if ($user_type == USER_TYPE_STUDENT) {
					redirect(base_url()."user/student.html");
				} else if ($user_type == USER_TYPE_NORMAL) {
					redirect(base_url()."user/normal.html");
				} else if ($user_type == USER_TYPE_TEACHER) {
					redirect(base_url()."user/teacher.html");
				}
			} else { // 此用户是家长用户类型
				$data[LOGINED_USER] = $logined_user;
				$data["parent_profile"] = true;
				$data["kids"] = true;
				$data["tutorship_admin"] = true;
				$data["parent_add_tutorship"] = true;
				$this->load->view("user/add-kid", $data);
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 添加孩子信息
	 */
	public function do_add_kid() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_PARENT) {
				$this->load->helper("form");
				$this->load->library("form_validation");
				$this->lang->load('form_validation', 'chinese');
				$this->form_validation->set_error_delimiters("", "");
				$result_array = array();
				if (!$this->form_validation->run("user_add_kid")) {
					$nickname_error = form_error("nickname");
					$gender_error = form_error("radio_gender");
					$school_error = form_error("school");
					$grade_error = form_error("select_grade");
					if ($nickname_error != "") {
						$result_array["nickname"] = $nickname_error;
					}
					if ($gender_error != "") {
						$result_array["radio_gender"] = $gender_error;
					}
					if ($school_error != "") {
						$result_array["school"] = $school_error;
					}
					if ($grade_error != "") {
						$result_array["select_grade"] = $grade_error;
					}
					if (count($result_array) > 0) {
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				} else {
					$this->load->model("user_model", "user");
					if ($this->user->addKid($logined_user["uid"]) == 1) { // 成功添加孩子信息
						$result_array["result"] = "successed";
					} else { //添加孩子信息失败
						$result_array["nickname"] = "添加孩子信息失败，请稍候重试";
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				}
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
			} else if ($user_type == USER_TYPE_TEACHER) {
				redirect(base_url()."user/teacher.html");
			} else if ($user_type == USER_TYPE_STUDENT) {
				redirect(base_url()."user/student.html");
			}
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 删除孩子信息，必须满足两个条件：一为孩子的uid，二为家长的uid
	 */
	public function delete_kid() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$this->load->model("user_model", "user");
			$result_array = array();
			if ($this->user->deleteKid($this->input->post("uid"), $logined_user["uid"]) == 1) { // 删除成功
				$result_array["result"] = "successed";
			} else { // 删除失败
				$result_array["result"] = "failed";
			}
			echo json_encode($result_array);
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 跳转到家长用户信息编辑页面
	 */
	public function edit_parent() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_PARENT) {
				$data["title"] = USER_PROFILE_EDIT_PAGE_TITLE;
				$data["profile"] = true;
				$data[LOGINED_USER] = $logined_user;
				$uid = $logined_user["uid"];
				$this->load->model("user_model", "user");
				$query = $this->user->findParentByUid($logined_user["uid"]);
				if ($query->num_rows() == 1) {
					$data["user"] = $query->row();
				}
				$this->load->view("user/parent-profile-edit", $data);
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
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
	 * 保存家长用户类型的信息
	 */
	public function save_parent() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_PARENT) {
				$this->load->helper("form");
				$this->load->library("form_validation");
				$this->lang->load('form_validation', 'chinese');
				$this->form_validation->set_error_delimiters("", "");
				$result_array = array();
				if (!$this->form_validation->run("user_save_parent")) {
					$this->load->library("app_common");
					$result_array = $this->app_common->getFormErrors($result_array, array("nickname", "radio_gender", "my_email"));
					if (count($result_array) > 0) {
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				} else {
					$this->load->model("user_model", "user");
					if ($this->user->updateParent($logined_user["uid"]) == 1) { // 成功家长用户信息
						$result_array["result"] = "successed";
					} else { //家长用户信息更新失败
						$result_array["nickname"] = "编辑我的信息失败，请稍候重试";
						$result_array["result"] = "failed";
					}
					echo json_encode($result_array);
				}
			} else if ($user_type == USER_TYPE_NORMAL) {
				redirect(base_url()."user/normal.html");
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