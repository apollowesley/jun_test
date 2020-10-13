<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Privacy extends CI_Controller {

	public function index()
	{
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && !empty($logined_user)) {
			$data[LOGINED_USER] = $logined_user;
		}
		$data["title"] = PRIVACY_PAGE_TITLE;
		$data["privacy"] = true;
		$this->load->view("privacy", $data);
	}
}