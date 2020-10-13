<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Logout extends CI_Controller {
	
	public function index() {
		$logined_user = $this->session->userdata(LOGINED_USER);
		if (isset($logined_user) && $logined_user != "") {
			$this->session->unset_userdata(LOGINED_USER);
			$this->session->sess_destroy();
		}
		redirect(base_url());
	}
	
}