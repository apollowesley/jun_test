<?php
namespace serve\websocket\request;
use serve\websocket\common\traits\End;
use serve\websocket\common\traits\Fast;
use serve\websocket\common\traits\Push;
use sok\App;
use sok\utils\Arr;

// [ 应用入口文件 ]
class Chat {
	use Fast, Push, End;
	// 对接受消息字段type=？ 进行方法映射 允许访问请求类型 例如前端数据含有type="chat" 将会触发执行 $this->$router['chat']($server,$frame);
	public $router = [
		"chat" => "Mchat",
	];
	function __construct($server) {
		$this->router = App::config('route')['websocket']['message'] ?? [];
		$this->server = $server;
		// 监听收发消息
		$server->on('request', [$this, 'onRequest']);
	}

	/**
	 * 收到消息触发
	 * @param $server
	 * @param $frame
	 */
	public function onRequest($request, $response) {
		if ($request->server['path_info'] == '/favicon.ico' || $request->server['request_uri'] == '/favicon.ico') {
			return $response->end();
		}
		print_r("request/onRequest");

		// 模拟推送websocket广播消息
		// foreach ($this->server->connections as $fd) {
		// 	// 需要先判断是否是正确的websocket连接，否则有可能会push失败
		// 	if ($this->server->isEstablished($fd)) {
		// 		echo "$fd \n";
		// 		$this->server->push($fd, $request->get['message']);
		// 	}
		// }

		// // http返回消息
		// $this->End($response, $data = $request->get, $code = 200, $message = "请求成功", $content_type = "text");

		// 路由地址过滤
		$route = \sok\Route::http($request->server['path_info'], $request->server['request_method'], "websocket_http");
		// print_r($route);
		// return;
		if (!empty($route)) {
			//www.index.php/模块名/控制器名/方法名
			// 执行路由地址 app\模块名\controller\控制器名
			// $action=执行方法
			$class = "\\app\\" . $route['class'];
			$action = $route['action'];
			if (class_exists($class)) {
				// $this->server
				// $this, $request, $response
				if (!isset($this->$class)) {
					$this->$class = new $class();
					// $this->$class=$that;
				};
				$this->$class->server = $this->server;
				$this->$class->request = $request;
				$this->$class->response = $response;
				if (method_exists($this->$class, $action)) {
					$this->$class->$action();
				}
			}

		}

	}

	// 推送消息到socket服务器
	public function sendWebsocket($to_uid = 12345, $from_uid, $content, $controller = 'chat') {

		$ip = "127.0.0.1";
		$port = 9501;
		$data = [
			'from_uid' => $from_uid, //发送人
			'c' => $controller, //推送控制器
			'to_uid' => $to_uid, //接收人
			'content' => $content, //内容
			'time' => time(),
		];

		$secret_key = '';
		if ($this->websocketConfig['distributed']) {
			$distributed = $this->websocketConfig['distributed'];
			// $local = $distributed['local'];
			$websocket_group = $distributed['websocket_group'];
			$secret_key = "sk=" . $this->websocketConfig['secret_key'];
			for ($i = 0; $i < count($websocket_group); $i++) {
				if ($ip === $websocket_group[$i]['ip'] && $port === $websocket_group[$i]['port']) {
					$secret_key = "sk=" . $websocket_group[$i]['secret_key'];
				}
			}

		}
		$cli = new \Co\http\Client($ip, $port);
		$ret = $cli->upgrade("/?" . $secret_key);
		// print_r($ret);
		echo "\n";
		if ($ret) {
			// while (true) {
			$cli->push(json_encode($data, JSON_UNESCAPED_UNICODE));
			var_dump($cli->recv());
			\co::sleep(0.1);
			// }
		}

	}
}
