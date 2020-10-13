<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Logout extends CI_Controller {
	
	public function index() {
		$logined_admin = $this->session->userdata(LOGINED_ADMIN);
		if (isset($logined_admin) && $logined_admin != "") {
			$this->session->unset_userdata(LOGINED_ADMIN);
			$this->session->sess_destroy();
		}
		redirect(base_url()."admin/login.html");
	}
	
}