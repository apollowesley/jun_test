<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Student extends CI_Controller {

	public function page()
	{
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && $logined_user != "") {
			$data[LOGINED_USER] = $logined_user;
		}
		$data['title'] = STUDENTS_PAGE_TITLE;
		$data[LOGINED_USER] = $logined_user;
		$pageNo = $this->uri->segment(3, 1); // /student/page/page_no
		if ($pageNo <= 0) {
			$pageNo = 1;
		}
		$data['students'] = true;
		$this->load->view("students", $data);
	}
	
	public function detail()
	{
		$user_email = $this->session->userdata(LOGINED_USER);
		if (isset($user_email) && $user_email != "") {
			$data[LOGINED_USER] = $user_email;
		}
		$data['title'] = STUDENT_PAGE_TITLE;
		$data['students'] = true;
		$this->load->view("student", $data);
	}
}