<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Setting extends CI_Controller {
	
	public function index() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data["title"] = USER_SETTING_PAGE_TITLE;
			$data[LOGINED_USER] = $logined_user;
			$data["setting"] = true;
			if (isset($logined_user["is_bind"])) { // 有可能是用户绑定社交账号后再重定向到此页面user/setting.html
				$data["from_bind"] = true;
			}
			$user_type = $logined_user["user_type"];
			if ($user_type == USER_TYPE_PARENT) {
				$data["parent_profile"] = true;
				$data["tutorship_admin"] = true;
				$data["parent_add_tutorship"] = true;
			} else if ($user_type == USER_TYPE_STUDENT) {
				$data["tutorship_admin"] = true;
				$data["student_add_tutorship"] = true;
			} else if ($user_type == USER_TYPE_TEACHER) {
				$data["tutorship_admin"] = true;
			}
			$this->load->view("user/setting", $data);
		} else {
			redirect(base_url());
		}
	}
	
	public function password() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$this->load->helper("form");
			$this->load->library("form_validation");
			$this->lang->load('form_validation', 'chinese');
			$this->form_validation->set_error_delimiters("", "");
			$this->form_validation->set_message("valid_old_password", "请输入正确的旧密码");
			$result_array = array();
			if (!$this->form_validation->run("user_change_pwd")) {
				$this->load->library("app_common");
				$result_array = $this->app_common->getFormErrors($result_array, array("old_password", "password", "confirm_password"));
				if (count($result_array) > 0) {
					$result_array["result"] = "failed";
				}
				echo json_encode($result_array);
			} else {
				$password = $this->input->post("password");
				$this->load->model("user_model", "user");
				if ($this->user->updatePassword($logined_user["uid"], $password) == 1) {
					$result_array["result"] = "successed";
				} else {
					$result_array["result"] = "failed";
					$result_array["old_password"] = "修改密码失败，请稍候重试";
				}
				echo json_encode($result_array);
			} 
		} else {
			redirect(base_url());
		}
	}
	
	function valid_old_password() {
		$this->load->model("user_model", "user");
		$logined_user = $this->session->userdata(LOGINED_USER);
		return $this->user->isOldPwdValid($logined_user["uid"], $this->input->post("old_password")) == 1;
	}
	
}