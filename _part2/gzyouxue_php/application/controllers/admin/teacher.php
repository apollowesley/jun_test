<?php if (! defined ( 'BASEPATH' )) exit ( 'No direct script access allowed' );

class Teacher extends CI_Controller {
	
	public function all() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_TEACHERS_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $logined_admin;
			$data["teachers"] = true;
			$this->load->view("admin/teachers", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
	
	public function detail() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_TEACHER_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $admin_email;
			$data["teachers"] = true;
			$this->load->view("admin/teacher", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
	
	public function add() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_ADD_TEACHER_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $admin_email;
			$data["add"] = true;
			$data["add_teacher"] = true;
			$this->load->view("admin/add-teacher", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
}