<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Page_not_found extends CI_Controller {

	public function index()
	{
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && $logined_user != "") {
			$data[LOGINED_USER] = $logined_user;
		}
		$data["title"] = PAGE_NOT_FOUND_TITLE;
		$this->load->view("page-not-found", $data);
	}
}