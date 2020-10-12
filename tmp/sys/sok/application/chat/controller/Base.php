<?php
namespace app\chat\controller;
use serve\http\common\traits\End;
use sok\utils\Url;
use \serve\http\common\traits\Fast;
use \sok\Redis;

class Base extends \sok\Base {
	use End;
	use Fast;
	protected $token = [
		'uid' => '', //用户id
		'token' => '', //token
		'source' => '', //终端来源标识 app|weixin|pc
	];
	protected $userInfo = [
		'uid' => null,
	];

	function __set($name, $val) {
		$this->$name = $val;
	}
	// function __construct($server, $request = 'request', $response = null) {
	// 	$this->server = $server;
	// 	$this->request = $request;
	// 	$this->response = $response;
	// 	print_r($request);
	// }
	public function init() {
		// $data = $this->request->get;
		// print_r($data);
		$this->formatToken();
		$this->validateLogin();
		// print_r([$this->token, $this->userInfo['uid']]);
	}
	public function param() {
		return $this->request->get ?? [];
	}

	/**
	 * 把截取前端token进行格式化处理
	 */
	public function formatToken() {
		$param = $this->param();

		if (isset($param['access_token'])) {
			// 解码
			$param['access_token'] = Url::base64url_decode($param['access_token']);
			// print_r($param);
			$token = explode('.', $param['access_token']);
			if (isset($token[2]) && strlen($token[0]) > 10) {
				$this->token = [
					'uid' => $token[0],
					'token' => $token[1],
					'source' => $token[2],
				];
				$this->setUserInfo();
			}
			unset($param['access_token']);
			$this->param = $param;
		}

	}
	// 如果用户已登陆会生成用户id等信息
	public function setUserInfo() {
		$this->userInfo['uid'] = $this->token['uid'];

	}

	public function validateLogin() {
		echo "validateLogin\n";
		echo $this->token['uid'] . "\n";
		$login = $this->redis()->get('login' . $this->token['uid']);
		$source = $this->token['source'] ?? 'app';
		// ReturnMsg::returnMsg(441, '请求未授权,或已过期,请重新登陆 validateLogin', $this->token);
		$loginToken = $login[$source] ?? null;
		print_r($login);
		// return;
		if (empty($loginToken) || !is_array($loginToken) || !isset($loginToken['token']) || !isset($loginToken['expires']) || $loginToken['expires'] < time()) {
			$this->End($this->response, $data, $code = 441, $message = "请求未授权,或已过期,请重新登陆 validateLogin", $content_type = "text");
			// ReturnMsg::returnMsg(441, '请求未授权,或已过期,请重新登陆 validateLogin', $login);
		}

		if ($loginToken['token'] !== $this->token['token']) {
			$this->End($this->response, $data, $code = 441, $message = "授权失效或已过期,请重新登陆validateLogin", $content_type = "text");
			// ReturnMsg::returnMsg(441, '授权失效或已过期,请重新登陆validateLogin', $this->token);
		}
		// ReturnMsg::returnMsg(200, '授权通过validateLogin', $this->token);
	}
}
?>