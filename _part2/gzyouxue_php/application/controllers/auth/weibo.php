<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Weibo extends CI_Controller {

	public function index()
	{
		$this->load->library("social", "social");
		$weibo = $this->social->getWeiboOAuth();
		header("Location:".$weibo->getAuthorizeURL( WB_CALLBACK_URL ));
	}
}