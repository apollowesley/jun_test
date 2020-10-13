<?php if (! defined ( 'BASEPATH' )) exit ( 'No direct script access allowed' );

class Tutorship extends CI_Controller {
	
	public function all() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_TUTORSHIPS_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $logined_admin;
			$data["tutorships"] = true;
			$this->load->view("admin/tutorships", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
	
	public function detail() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_TUTORSHIP_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $logined_admin;
			$data["tutorships"] = true;
			$this->load->view("admin/tutorship", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
	
	public function add_student() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_ADD_TUTORSHIP_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $admin_email;
			$data["add_tutorship"] = true;
			$this->load->view("admin/add-tutorship-student", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
	
	public function add_tutorship() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_ADD_TUTORSHIP_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $logined_admin;
			$data["add_tutorship"] = true;
			$this->load->view("admin/add-tutorship-info", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
	
	
}