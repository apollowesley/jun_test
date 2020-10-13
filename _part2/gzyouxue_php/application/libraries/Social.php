<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Social {
	
	public function getQQ($access_token = "", $openid = "") {
		require_once(APPPATH."libraries/qq/qqConnectAPI.php");
		return new QC($access_token, $openid);
	}
	
	public function getWeiboOAuth($access_token = NULL, $refresh_token = NULL) {
		if (session_id() == "") {
			session_start();
		}
		require_once(APPPATH."libraries/weibo/config.php");
		require_once(APPPATH."libraries/weibo/saetv2.ex.class.php");
		return new SaeTOAuthV2( WB_AKEY , WB_SKEY, $access_token, $refresh_token );
		
	}
	
	public function getWeiboClient($access_token, $refresh_token = NULL) {
		if (session_id() == "") {
			session_start();
		}
		require_once(APPPATH."libraries/weibo/config.php");
		require_once(APPPATH."libraries/weibo/saetv2.ex.class.php");			
		return new SaeTClientV2( WB_AKEY , WB_SKEY, $access_token, $refresh_token );
	}
	
}