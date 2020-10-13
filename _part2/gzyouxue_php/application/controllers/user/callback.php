<?php if (! defined ( 'BASEPATH' )) exit ( 'No direct script access allowed' );

class Callback extends CI_Controller {
	
	public function qq() {
		$code = $this->input->get ( "code" );
		$state = $this->input->get ( "state" );
		$state_in_session = "";
		if (session_id () == '') {
			session_start ();
		}
		if (isset ( $_SESSION ["QC_userData"] )) {
			$state_in_session = $_SESSION ["QC_userData"] ["state"];
		}
		if (isset ( $code ) && ! empty ( $code ) && isset ( $state ) && ! empty ( $state ) && $state_in_session == $state) {
			$this->load->library ( "social", "social" );
			$qq = $this->social->getQQ();
			$access_token = $qq->qq_callback();
			$openid = $qq->get_openid();
			$user_data = array();
			$this->load->model("user_model", "user");
			$query = $this->user->getUserByOpenid($openid);
			if ($query->num_rows() == 1) { // 该用户已经绑定
				$user = $query->row();
				$user_data["nickname"] = $user->nickname;
				$user_data["figure"] = $user->figure_normal;
				$user_data["user_type"] = $user->user_type;
				$user_data["uid"] = $user->uid;
				$user_data["email"] = $user->email;
				$logined_user = array("uid" => $user_data["uid"], "access_token" => $access_token, "openid"=>$openid,
						"figure" => $user_data["figure"], "nickname"=>$user_data["nickname"],
						"user_type" => $user_data["user_type"], "logined_from" => USER_LOGINED_FROM_QQ,
						"bind_with" => BIND_WITH_QQ, "email" => $user_data["email"]);
				$this->user->updateWhenLogin($user_data["uid"]);
				$this->session->set_userdata(LOGINED_USER, $logined_user);
				redirect(base_url());
			} else { // 将用户绑定到数据库，两种情况：一种是已经有用户账号，则进行绑定；一种是新登录，则进行添加。
				$qq = $this->social->getQQ($access_token, $openid);
				$user_info = $qq->get_user_info();
				$user_data["access_token"] = $access_token;
				$user_data["openid"] = $openid;
				$logined_user = $this->session->userdata(LOGINED_USER);
				if (isset($logined_user) && !empty($logined_user) && isset($logined_user["uid"])) { //绑定用户
					if ($logined_user["bind_with"] != BIND_WITH_EMAIL) { // 表示该用户已经绑定了社交账号，无需再次绑定
						redirect(base_url()."user/setting.html");
					} else { // 表示该用户没有绑定Q社交号，则进行绑定
						$user_data["uid"] = $logined_user["uid"];
						if($this->user->bindFromQQ($user_data) == 1) { // 绑定成功
							$logined_user["access_token"] = $access_token;
							$logined_user["openid"] = $openid;
							$logined_user["logined_from"] = USER_LOGINED_FROM_QQ;
							$logined_user["bind_with"] = BIND_WITH_QQ;
							$logined_user["is_bind"] = true; // 用户绑定成功状态
							$this->user->updateWhenLogin($user_data["uid"]);
							$this->session->set_userdata(LOGINED_USER, $logined_user);
							redirect(base_url()."user/setting.html");
						} else { //绑定失败
							$logined_user["is_bind"] = false; // 用户绑定失败状态
						}
					}
				} else { //添加用户
					$user_data["nickname"] = $user_info["nickname"];
					$user_data["gender"] = $user_info["gender"];
					$figureurl = $user_info["figureurl"]; // 30*30
					$figureurl_1 = $user_info["figureurl_1"]; // 50*50
					$figureurl_2 = $user_info["figureurl_2"]; //100*100
					$figureurl_qq_1 = $user_info["figureurl_qq_1"]; // 40*40
					$figureurl_qq_2 = $user_info["figureurl_qq_2"]; // 100*100
					$user_data["figure_small"] = $figureurl_2;
					$user_data["figure_normal"] = $figureurl_2;
					$user_data["figure"] = $figureurl_2;
					$user_data["user_type"] = USER_TYPE_NORMAL;
					$uid = $this->user->addFromQQ($user_data);
					if($uid != "") {
						// 保存用户成功
						$user_data["uid"] = $uid;
						$logined_user = array("uid" => $user_data["uid"], "access_token" => $access_token, "openid"=>$openid,
								"figure" => $user_data["figure"], "nickname"=>$user_data["nickname"],
								"user_type" => $user_data["user_type"], "logined_from" => USER_LOGINED_FROM_QQ,
								"bind_with" => BIND_WITH_QQ);
						$this->user->updateWhenLogin($user_data["uid"]);
						$this->session->set_userdata(LOGINED_USER, $logined_user);
					} else {
						// 保存用户出错
					}
					redirect(base_url());
				}
			} 
		} else {
			redirect ( base_url ().TO_PAGE_NOT_FOUND );
		}
	}
	
	public function weibo() {
		if ($this->input->get ( "code" )) {
			$this->load->library ( "social", "social" );
			$weibo_auth = $this->social->getWeiboOAuth ();
			$keys = array ();
			$keys ['code'] = $this->input->get ( "code" );
			$keys ['redirect_uri'] = WB_CALLBACK_URL;
			try {
				$token = $weibo_auth->getAccessToken ( 'code', $keys );
			} catch ( OAuthException $e ) {
			}
			if (isset ( $token ) && $token) {
				$_SESSION['token'] = $token;
				$access_token = $token["access_token"];
				$weibo = $this->social->getWeiboClient ($access_token);
				$uid_get = $weibo->get_uid ();
				$openid = $uid_get ['uid'];
				$user_data = array();
				$this->load->model("user_model", "user");
				$query = $this->user->getUserByOpenid("", $openid);
				if ($query->num_rows() == 1) { // 该用户已经绑定
					$user = $query->row();
					$user_data["nickname"] = $user->nickname;
					$user_data["figure"] = $user->figure_normal;
					$user_data["user_type"] = $user->user_type;
					$user_data["uid"] = $user->uid;
					$user_data["email"] = $user->email;
					$logined_user = array("uid" => $user_data["uid"], "access_token" => $access_token, "openid"=>$openid,
							"figure" => $user_data["figure"], "nickname"=>$user_data["nickname"],
							"user_type" => $user_data["user_type"], "logined_from" => USER_LOGINED_FROM_WEIBO,
							"bind_with" => BIND_WITH_WEIBO, "email" => $user_data["email"]);
					$this->user->updateWhenLogin($user_data["uid"]);
					$this->session->set_userdata(LOGINED_USER, $logined_user);
					redirect(base_url());
				} else { // 将用户绑定到数据库，两种情况：一种是已经有用户账号，则进行绑定；一种是新登录，则进行添加。
					$user_message = $weibo->show_user_by_id ( $openid );
					$user_data["access_token"] = $access_token;
					$user_data["openid"] = $openid;
					$logined_user = $this->session->userdata(LOGINED_USER);
					if (isset($logined_user) && !empty($logined_user) && isset($logined_user["uid"])) { //绑定用户
						if ($logined_user["bind_with"] != BIND_WITH_EMAIL) { // 表示该用户已经绑定了社交账号，无需再次绑定
							redirect(base_url()."user/setting.html");
						} else { // 表示该用户未绑定社交账号，则进行绑定
							$user_data["uid"] = $logined_user["uid"];
							if($this->user->bindFromWeibo($user_data) == 1) { // 绑定成功
								$logined_user["access_token"] = $access_token;
								$logined_user["openid"] = $openid;
								$logined_user["logined_from"] = USER_LOGINED_FROM_WEIBO;
								$logined_user["bind_with"] = BIND_WITH_WEIBO;
								$logined_user["is_bind"] = true; // 用户绑定成功状态
								$this->user->updateWhenLogin($user_data["uid"]);
								$this->session->set_userdata(LOGINED_USER, $logined_user);
								redirect(base_url()."user/setting.html");
							} else { //绑定失败
								$logined_user["is_bind"] = false; // 用户绑定失败状态
							}
						}
					} else { //添加用户
						$user_data["nickname"] = $user_message["screen_name"];
						$gender = $user_message["gender"];
						if ($gender == "m") {
							$user_data["gender"] = "男";
						} else if ($gender == "f") {
							$user_data["gender"] = "女";
						} else {
							$user_data["gender"] = "";
						}
						$user_data["figure_small"] = $user_message["profile_image_url"];
						$user_data["figure_normal"] = $user_message["avatar_large"];
						$user_data["figure"] = $user_message["profile_image_url"];
						$user_data["user_type"] = USER_TYPE_NORMAL;
						$uid = $this->user->addFromWeibo($user_data);
						if($uuid != "") {
							// 保存用户成功
							$user_data["uid"] = $uid;
							$logined_user = array("uid" => $user_data["uid"], "access_token" => $access_token, "openid"=>$openid,
									"figure" => $user_data["figure"], "nickname"=>$user_data["nickname"],
									"user_type" => $user_data["user_type"], "logined_from" => USER_LOGINED_FROM_WEIBO,
									"bind_with" => BIND_WITH_WEIBO);
							$this->user->updateWhenLogin($user_data["uid"]);
							$this->session->set_userdata(LOGINED_USER, $logined_user);
						} else {
							// 保存用户出错
						}
						redirect(base_url());
					}
				}
			} else {
				redirect ( base_url ().TO_PAGE_NOT_FOUND );
			}
		} else {
			redirect ( base_url ().TO_PAGE_NOT_FOUND );
		}
	}
}