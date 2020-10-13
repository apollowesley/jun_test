<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Login extends CI_Controller {
	
	public function index() {
		$this->load->library('app_http');
		if ($this->app_http->isGet()) {
			$data["title"] = ADMIN_LOGIN_PAGE_TITLE;
			$this->load->view("admin/login", $data);
		} else if ($this->app_http->isPost()) {
			$this->load->helper("form");
			$this->load->library("form_validation");
			$this->lang->load('form_validation', 'chinese');
			$this->form_validation->set_error_delimiters("", "");
			$this->form_validation->set_message('valid_authcode', '请输入正确的%s');
			$this->form_validation->set_message("valid_admin", "该管理员账号被冻结，不允许登录，请联系平台管理者");
			$result_array = array();
			if (!$this->form_validation->run("admin_login")) {
				$this->load->library("app_common");
				$result_array = $this->app_common->getFormErrors($result_array, array("email", "password", "auth_code"));
				if (count($result_array) > 0) {
					$result_array["result"] = "failed";
				}
				echo json_encode($result_array);
			} else {
				$email = $this->input->post("email");
				$password = $this->input->post("password");
				$authcode = $this->input->post("auth_code");
				$this->load->model("admin_model", "admin");
				$query = $this->admin->findAdmin($email, $password);
				if ($query->num_rows() == 1) {
					$admin = $query->row();
					$logined_admin = array("uid" => $admin->uid, "figure" => $admin->figure_normal, 
							"real_name" => $admin->real_name, "admin_type" => $admin->admin_type, "email" => $email);
					$this->admin->updateWhenLogin($admin->uid);
					$this->session->set_userdata(LOGINED_ADMIN, $logined_admin);
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
	
	function valid_authcode($authcode = "") {
		if ($authcode == "") {
			return false;
		} else {
			return strtolower($authcode) == strtolower($this->session->userdata("auth_code"));
		}
	}
	
	function valid_admin() {
		$this->load->model("admin_model", "admin");
		return $this->admin->isValid($this->input->post("email"));
	}
	
}