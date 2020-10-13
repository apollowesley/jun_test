<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Authcode extends CI_Controller {
	
	public function index() {
		$this->load->library('auth_code');
		$this->session->set_userdata("auth_code", $this->auth_code->authCode);
		$this->auth_code->output();
	}
	
}