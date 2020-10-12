<?php
namespace serve\http;
use sok\App;
use sok\Request;

/**
 *
 */
class Chat {

	public $httpConfig = [
		"ip" => "127.0.0.1",
		"port" => 8880,
		"set" => [
			'ssl_cert_file' => '/ssl.crt',
			'ssl_key_file' => '/ssl.key',
			'open_http2_protocol' => true,
		],

	];

	public $websocketConfig = [
		// 本机服务配置
		'ip' => '0.0.0.0', // 监听地址
		'port' => 9501, // 监听端口
		'secret_key' => '123456', //允许推送消息的秘钥
		'mode' => "SWOOLE_PROCESS", // 运行模式 默认为SWOOLE_PROCESS
		'sock_type' => ["SWOOLE_SOCK_TCP", "SWOOLE_SSL"], //数组 sock type 默认为SWOOLE_SOCK_TCP | SWOOLE_SSL

		// 分布式服务器通道 //允许哪些ip +秘钥连接到与本机接收推送消息
		// 分布式服务器通道
		"distributed" => [
			//提供websocket服务的服务器组，本机可连接到websocket服务器进行消息推送服务
			"websocket_group" => [
				"ip" => '192.168.0.150', //如果分布式都布置在同一局域网，这就用局域网ip。保证http服务器能链接上
				"secret_key" => "123456",
				"port" => 9501,
			],

		],

		'set' => [
			'dispatch_mode' => 2, //绑定uid=5
			'daemonize' => false, //守护进程化。

			'heartbeat_check_interval' => 10, //间隔多少秒进行心跳检测
			'heartbeat_idle_time' => 60, //间隔多少秒无状态 踢下线

			// 'ssl_cert_file' => App::getRootPath() . '/ssl/1_www.01film.cn_bundle.crt',
			// 'ssl_key_file' => App::getRootPath() . '/ssl/2_www.01film.cn.key',

			'worker_num' => 1, //开启进程数量
			// 'task_worker_num' => 1, //swoole 任务工作进程数量

			'insert_db_turns_time' => 5, //轮流读取redis缓存写入数据库。间隔秒,一般对数据库实时要求并不高,时间可以设置在5-10秒，读取redis缓存消息堆，批量写入数据库，减少数据库操作频率。提升写入性能
			'insert_db_number' => 1000, //间隔秒，读取redis缓存消息堆数量，批量写入数据库，如果单台redis主机建议每秒吞吐量2000以内。
		],
	];

	function __construct($config = []) {
		if (!empty($httpConfig) && is_array($config)) {
			$this->httpConfig = array_merge($this->httpConfig, $config);
		} else {
			$this->httpConfig = array_merge($this->httpConfig, App::config('http'));
		}
		echo "<pre>Run\n";
		// print_r(App::config('http'));
		echo "开启serve/http/run服务器";

		$this->http = $this->http();
		$this->http->start();
	}

	// 创建 http 服务
	public function http() {
		$ip = $this->httpConfig['ip'];
		$port = $this->httpConfig['port'];
		$http = new \Swoole\Http\Server($ip, $port);
		$http->set($this->httpConfig['set']);

		$http->on('request', function ($request, $response) {
			if ($request->server['path_info'] == '/favicon.ico' || $request->server['request_uri'] == '/favicon.ico') {
				return $response->end();
			}

			// go(function () use ($request, $response) {
			// $response->header('content-type', 'text/html;charset=utf-8', true);
			// $response->status(200);
			$data = [];
			// 路由地址过滤
			$route = \sok\Route::http($request->server['path_info'], $request->server['request_method']);
			// print_r($request->server['request_method']);
			// print_r($route);
			// return;
			if (!empty($route)) {
				//www.index.php/模块名/控制器名/方法名
				// 执行路由地址 app\模块名\controller\控制器名
				// $action=执行方法
				$class = "\\app\\" . $route['class'];
				// print_r($class);
				$action = $route['action'];
				if (class_exists($class)) {
					// $this->server
					// $this, $request, $response
					if (!isset($this->$class)) {
						$this->$class = new $class();
						// $this->$class=$that;
					};
					$this->$class->server = $this;
					$this->$class->request = $request;
					$this->$class->response = $response;

					if (method_exists($this->$class, "init")) {
						$this->$class->init();
					}

					if (method_exists($this->$class, $action)) {
						// $p = new \ReflectionClass($class); // 建立 Person这个类的反射类
						// $instance = $p->newInstanceArgs($args); // 相当于实例化Person 类
						// $p = new \ReflectionMethod($class, $action);
						// $Parameters=$p->getParameters();

						$data = $this->$class->$action();
					}
				}

			}

			// echo "发送消息\n";
			// $response->end("<h1>Hello Swoole. #" . rand(1000, 9999) . "</h1><div>" . json_encode($data, JSON_UNESCAPED_UNICODE) . "</div>");

			// });
			// echo "___________\n";

		});

		return $http;
	}
}

?>