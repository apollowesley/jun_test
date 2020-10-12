<?php
namespace app\chat\controller;
use serve\http\common\traits\End;

class Chat extends Base {
	use End;
	// use Swoole;
	public function Index($a = 'a') {
		// echo "<pre>";
		// $params = Request::params();
		$data = $this->request->get;
		// print_r($this->request);
		// print_r($data);

		// // http返回消息
		$this->End($this->response, $data, $code = 200, $message = "index请求成功", $content_type = "text");

	}
	public function send($to_uid = '', $msg = []) {
		$data = $this->request->get;
		// print_r($data);
		$to_uid = '123456';
		$user_id = '123456';
		$type = 'chat';
		$content = json_encode($data);
		// 接口验证秘钥sk+
		$apiAuth = [
			"sk" => "123456",
			"means" => "to_uid",
		];

		//______________________________推送到服务器的数据内容
		$sendMsg = [
			"to_uid" => $to_uid,
			"type" => "chat",
			"uid" => '123456',
			"time" => "159987740",
			"content" => [
				"type" => $type,
				"value" => $content,
			],
		];
		$sendMsg['time'] = time();
		// echo time() . "-" . $sendMsg['time'];
		if (abs(time() - $sendMsg['time']) > 30) {
			ReturnMsg::returnMsg($code = 400, $message = "消息已过期，请从新发送", $data ?? []);
		}
		$apiAuth['data'] = $sendMsg;
		// echo "<pre>";
		// print_r($apiAuth = (new Redis)->inc(time(), 1, 1));
		// // print_r((new Redis));
		// return;
		//______________________________数据内容转成json字符串
		$sendData = json_encode($apiAuth, JSON_UNESCAPED_UNICODE);

		$client = new \swoole_client(SWOOLE_SOCK_UDP);
		if (!$client->connect('192.168.1.201', 9502, 1)) {
			exit("connect failed. Error: {$client->errCode}\n");
		}
		$client->send($sendData);
		$data = $client->recv();
		$client->close();
		$data = json_decode($data, true) ?? $data;
		$apiAuth['data']['onLine'] = $data['onLine'] ?? 0;
		// // http返回消息
		$this->End($this->response, $apiAuth['data'], $code = 200, $message = "send请求成功", $content_type = "text");
	}
}
?>