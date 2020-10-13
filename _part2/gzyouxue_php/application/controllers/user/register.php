<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Register extends CI_Controller {
	
public function index() {
		$this->load->library('app_http');
		if ($this->app_http->isGet()) {
			$data["title"] = USER_REG_PAGE_TITLE;
			$this->load->view("user/register", $data);
		} else if ($this->app_http->isPost()) {
			$this->load->helper("form");
			$this->load->library("form_validation");
			$this->lang->load('form_validation', 'chinese');
			$this->form_validation->set_error_delimiters("", "");
			$this->form_validation->set_message("email_not_exist", "请更换登录邮箱，该邮箱已经被注册");
			$result_array = array();
			if (!$this->form_validation->run("user_reg")) {
				$this->load->library("app_common");
				$result_array = $this->app_common->getFormErrors($result_array, array("email", "password", "confirm_password"));
				if (count($result_array) > 0) {
					$result_array["result"] = "failed";
				}
				echo json_encode($result_array);
			} else {
				$email = $this->input->post("email");
				$password = $this->input->post("password");
				$this->load->model("user_model", "user");
				$result = $this->user->reg();
				if ($result != "") {
					$logined_user = array("uid" => $result["uid"], "figure" => $result["figure_normal"], 
							"nickname" => $result["nickname"], "user_type" => $result["user_type"],
							"logined_from" => USER_LOGINED_FROM_EMAIL, "bind_with" => BIND_WITH_EMAIL, "email" => $result["email"]);
					$this->session->set_userdata(LOGINED_USER, $logined_user);
					$result_array["result"] = "successed";
				} else {
					$result_array["result"] = "failed";
					$result_array["email"] = "登录邮箱或登录密码错误";
					$result_array["password"] = "登录邮箱或登录密码错误";
				}
				echo json_encode($result_array);
			}
		}
	}
	
	function email_not_exist() {
		$this->load->model("user_model", "user");
		return $this->user->findEmail($this->input->post("email")) == 0;
	}
	
}