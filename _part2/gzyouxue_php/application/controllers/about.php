<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class About extends CI_Controller {

	public function index()
	{
		$user_email = $this->session->userdata(LOGINED_USER);
		if (isset($user_email) && $user_email != "") {
			$data[LOGINED_USER] = $user_email;
		}
		$data["title"] = ABOUT_PAGE_TITLE;
		$data["about"] = true;
		$this->load->view("about", $data);
	}
}