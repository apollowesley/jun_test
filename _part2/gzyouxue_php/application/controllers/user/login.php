<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Login extends CI_Controller {
	
	public function index() {
		$this->load->library('app_http');
		if ($this->app_http->isGet()) {
			$data["title"] = USER_LOGIN_PAGE_TITLE;
			$this->load->view("user/login", $data);
		} else if ($this->app_http->isPost()) {
			$this->load->helper("form");
			$this->load->library("form_validation");
			$this->lang->load('form_validation', 'chinese');
			$this->form_validation->set_error_delimiters("", "");
			$this->form_validation->set_message("valid_user", "该用户账号被冻结，不允许登录，请联系平台管理人员");
			$result_array = array();
			if (!$this->form_validation->run("user_login")) {
				$this->load->library("app_common");
				$result_array = $this->app_common->getFormErrors($result_array, array("email", "password"));
				if (count($result_array) > 0) {
					$result_array["result"] = "failed";
				}
				echo json_encode($result_array);
			} else {
				$email = $this->input->post("email");
				$password = $this->input->post("password");
				$this->load->model("user_model", "user");
				$query = $this->user->findUser($email, $password);
				if ($query->num_rows() == 1) {
					$user = $query->row();
					$logined_user = array("uid" => $user->uid, "email" => $user->email, "figure" => $user->figure_normal, 
							"nickname" => $user->nickname, "user_type" => $user->user_type, 
							"logined_from" => USER_LOGINED_FROM_EMAIL, "bind_with" => $user->bind_with);
					$this->user->updateWhenLogin($user->uid);
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
	
	function valid_user() {
		$this->load->model("user_model", "user");
		return $this->user->isValid($this->input->post("email"));
	}
}