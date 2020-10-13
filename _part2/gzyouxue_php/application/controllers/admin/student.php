<?php if (! defined ( 'BASEPATH' )) exit ( 'No direct script access allowed' );

class Student extends CI_Controller {
	
	public function all() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_STUDENTS_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $logined_admin;
			$data["students"] = true;
			$this->load->view("admin/students", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
	
	public function detail() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_STUDENT_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $logined_admin;
			$data["students"] = true;
			$this->load->view("admin/student", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
	
	public function add() {
		$logined_admin = $this->session->userdata (LOGINED_ADMIN);
		if (isset ( $logined_admin ) && $logined_admin != NULL) {
			$data ["title"] = ADMIN_ADD_STUDENT_PAGE_TITLE;
			$data [LOGINED_ADMIN] = $logined_admin;
			$data["add"] = true; 
			$data["add_student"] = true;
			$this->load->view("admin/add-student", $data);
		} else {
			redirect(base_url()."admin/login.html");
		}
	}
}