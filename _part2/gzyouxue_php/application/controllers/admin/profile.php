<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Profile extends CI_Controller {
	
	/**
	 * 获取管理员信息，跳转到admin/profile.html
	 */
	public function index() {
		$logined_admin = $this->session->userdata(LOGINED_ADMIN);
		if (isset($logined_admin) && !empty($logined_admin)) {
			$data["title"] = PROFILE_PAGE_TITLE;
			$data[LOGINED_ADMIN] = $logined_admin;
			$data["profile"] = true;
			$this->load->model("admin_model", "admin");
			$query = $this->admin->findAdminByUid($logined_admin["uid"]);
			if ($query->num_rows() == 1) {
				$data["admin"] = $query->row();
			}
			$this->load->view("admin/profile", $data);
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 初始化管理员信息
	 */
	private function init_admin() {
		$this->load->model("admin_model", "admin");
		$this->admin->init_admin();
		
	}
	
	/**
	 * 跳转到添加管理员页面
	 */
	public function add_admin() {
		$logined_admin = $this->session->userdata(LOGINED_ADMIN);
		if (isset($logined_admin) && !empty($logined_admin)) {
			$this->load->view();
		} else {
			redirect(base_url());
		}
	}
	
	/**
	 * 添加管理员信息
	 */
	public function do_add_admin() {
		
	}
	
}