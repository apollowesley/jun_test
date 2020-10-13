<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Tutorship extends CI_Controller {
	
	public function all()
	{
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data[LOGINED_USER] = $logined_user;
		}
		$data['title'] = TUTORSHIPS_PAGE_TITLE;
		$data['tutorships'] = true;
		$this->load->view("tutorships", $data);
	}

	public function detail()
	{
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && $logined_user != "") {
			$data[LOGINED_USER] = $logined_user;
		}
		$data['title'] = TUTORSHIPS_PAGE_TITLE;
		$data['tutorships'] = true;
		$this->load->view("tutorship", $data);
	}
}