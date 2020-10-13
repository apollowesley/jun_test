<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Activity extends CI_Controller {
	
	public function index()
	{
		$user_email = $this->session->userdata("user_email");
		if (isset($user_email) && $user_email != "") {
			$data['user_email'] = $user_email;
		}
		$data['title'] = ACTIVITY_PAGE_TITLE;
		$data['activity'] = true;
		$data['footer'] = FOOTER_RIGHT;
		$this->load->view('header', $data);
		$this->load->view('activity');
		$this->load->view('footer');
	}
	
	public function detail() {
		$user_email = $this->session->userdata("user_email");
		if (isset($user_email) && $user_email != "") {
			$data["user_email"] = $user_email;
		}
		$data["title"] = ACTIVITY_DETAIL_TITLE;
		$data['activity'] = true;
		$data["footer"] = FOOTER_LEFT;
		$this->load->view("header", $data);
		$this->load->view("activity_detail");
		$this->load->view("footer");
	}
}