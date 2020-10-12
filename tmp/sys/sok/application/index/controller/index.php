<?php
namespace app\index\controller;
use serve\websocket\common\traits\End;

class Index extends \sok\Base {
	use End;
	public function Index($a = 'a') {
		echo "<pre>";
		// $params = Request::params();
		$data = $this->request->get;
		// print_r($this->request);
		print_r($data);

		// // http返回消息
		$this->End($this->response, $data, $code = 200, $message = "请求成功", $content_type = "text");

	}
}
?>