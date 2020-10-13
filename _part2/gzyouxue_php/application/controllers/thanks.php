<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Thanks extends CI_Controller {

	public function index()
	{
		$user_email = $this->session->userdata(LOGINED_USER);
		if (isset($user_email) && $user_email != "") {
			$data[LOGINED_USER] = $user_email;
		}
		$data["title"] = THANKS_PAGE_TITLE;
		$data["thanks"] = true;
		$this->load->view("thanks", $data);
	}
}