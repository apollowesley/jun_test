<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Help extends CI_Controller {

	public function index()
	{
		$user_email = $this->session->userdata(LOGINED_USER);
		if (isset($user_email) && $user_email != "") {
			$data[LOGINED_USER] = $user_email;
		}
		$data["title"] = HELP_PAGE_TITLE;
		$data["help"] = true;
		$this->load->view("help", $data);
	}
}