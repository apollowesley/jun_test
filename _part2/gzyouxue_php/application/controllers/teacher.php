<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Teacher extends CI_Controller {

	public function all()
	{
		$user_email = $this->session->userdata(LOGINED_USER);
		if (isset($user_email) && $user_email != "") {
			$data[LOGINED_USER] = $user_email;
		}
		$data['title'] = TEACHERS_PAGE_TITLE;
		$data['teachers'] = true;
		$this->load->view("teachers", $data);
	}
	
	public function detail()
	{
		$user_email = $this->session->userdata(LOGINED_USER);
		if (isset($user_email) && $user_email != "") {
			$data[LOGINED_USER] = $user_email;
		}
		$data['title'] = TEACHER_PAGE_TITLE;
		$data['teachers'] = true;
		$this->load->view("teacher", $data);
	}
}