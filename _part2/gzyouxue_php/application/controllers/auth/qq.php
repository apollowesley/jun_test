<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Qq extends CI_Controller {

	public function index()
	{
		$this->load->library("social", "social");
		$qq = $this->social->getQQ();
		$qq->qq_login();
	}
}