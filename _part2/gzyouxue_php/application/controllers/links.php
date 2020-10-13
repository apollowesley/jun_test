<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Links extends CI_Controller {

	public function index()
	{
		$user_email = $this->session->userdata("user_email");
		if (isset($user_email) && $user_email != "") {
			$data['user_email'] = $user_email;
		}
		$data["title"] = LINKS_PAGE_TITLE;
		$data["links"] = true;
		$this->load->view("links", $data);
	}
}