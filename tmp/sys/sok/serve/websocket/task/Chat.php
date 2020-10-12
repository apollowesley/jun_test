<?php
namespace serve\websocket\task;
use serve\websocket\common\traits\Fast;
use serve\websocket\common\traits\Push;
use sok\App;
use sok\utils\Arr;
use sok\utils\Url;

// [ 应用入口文件 ]
class Chat {
	use Fast, Push;
	// 对接受消息字段type=？ 进行方法映射 允许访问请求类型 例如前端数据含有type="chat" 将会触发执行 $this->$router['chat']($server,$frame);
	public $router = [
		"chat" => "actionChat", //聊天对话消息业务标识
	];

	function __construct() {
		$this->router = App::config('route')['websocket']['message'] ?? [];
		// 监听收发消息

	}
	/**
	 * 收到消息触发
	 * @param $server
	 * @param $frame
	 */
	public function onTask($server, $task) {
		// print_r("chat/onTask\n");

		if ($task->data->action == 'onMessage' && !empty($data = json_decode($task->data->data, true))) {

			$task->data->data = $data;
			// print_r($task);

			$frame = $task->data;
			// $action = $this->router[$frame->data['type']] ?? null;
			if (isset($frame->data['type']) && isset($this->router[$frame->data['type']])) {
				$action = $this->router[$frame->data['type']];
				// 是用户端发过来的消息 如果有io交互，或者图片等比较占用时间的业务，请写在这里执行处理，处理完在finish投递到主线程，因为只有主线程能有效使用存储用户信息的全局变量
				$frame->action = $action;
				$task->data = $frame;
				// config配置router路由允许的消息类型->去处理复杂io交互等复杂逻辑
				return $this->$action($server, $task);
			}

		} elseif ($task->data->action == 'onOpen' && !empty($task->data->data)) {

			// echo "request\n"; //首次连接会有个request请求 登录验证
			// $action = $task->data->action;
			// $task->data = $request;
			return $this->onOpen($server, $task);
			// print_r($request->action);

		} elseif ($task->data->action == 'tasks') {

			$action = 'tasks' . $task->data->class;

			return $this->$action($server, $task);
			// print_r($task);
		}

	}
	public function tasksSaveChatDb($server, $task) {
		// echo "\ntaskssacechatdb\n";
		$action = $task->data->class;
		// print_r(count($task->data->data));
		// echo "\ntaskssacechatdb______\n";
		foreach ($task->data->data as $key => $value) {
			$this->$action($value);
		}
	}

	// 新连接登录验证
	public function onOpen($server, $task) {
		$request = $task->data->data;
		// // print_r($request);
		// // 模拟用户登录数据
		// $cache = [
		// 	'uid' => 123456,
		// 	'token' => '123456',
		// 	'expires' => 15666063466,
		// ];
	//	$userOpen = $this->redis()->set('login' . $cache['uid'], ['app' => $cache]);

		// $fd = $request->fd;

		$param = $request['get'];

		if (is_array($param) && isset($param['access_token'])) {
			$data = [];
			$token = explode('.', Url::base64url_decode($param['access_token']));
			//print_r($token);
			if (isset($token[2])) {
				$data = [
					'uid' => $token[0],
					'token' => $token[1],
					'source' => $token[2],
					// 'time' => $token[3],
				];

				$uid = $data['uid'];

				// $source; //登录来源标识 pc|weixin|app
				// token获取———实际运营中用户登录状态需要在web服务器授权获取存入redis，这里只读—————————————————————————————————请检查登录状态key是否与http服务器redis保存的key相同
				$userToken = $this->redis()->get('login' . $uid);
				//print_r($userToken);
				if (is_array($userToken)) {
					foreach ($userToken as $source => $token) {
						if (isset($userToken[$source]['token']) && $userToken[$source]['token'] === (string) $data['token']) {

							$token = $param['token'] ?? '';
							if (mb_strlen($token, 'UTF8') < 10) {
								$token = md5(microtime());
							}

							// echo "\n授权成功\n";
							$ClientInfo = [
								// token 连续断开反复重连，用户同设备token不变。如果token不相同。服务端可以选择踢下线同应用的其他在线设备。
								'token' => $token,
								'time' => time(),
								'fd' => $request['fd'],
							];

							$fdInfo = [
								'uid' => $uid,
								"source" => $source,
								"time" => time(),
							];
							// print_r(expression)
							$frame = (object) [];
							$frame->action = $task->data->action;
							$frame->clientInfo = $ClientInfo;
							$frame->fdInfo = $fdInfo;
							// $frame->fd = $request;

							// 切到主线程
							$task->finish($frame);

							return true;
						}
					}

				}
			}
			// print_r($userToken);
			// echo "\n授权失败\n";
			// 校验token是否正确,无效关闭连接
		}

		$returnMsg = [
			'message' => '授权失败',
			'code' => 401,
			'data' => [],
		];
		$server->push($request['fd'], json_encode($returnMsg));
		// echo "验证失败断开连接fd:" . $request->fd;
		$server->close($request['fd']);
	}
	public function actionChat($server, $task) {
		$frame = $task->data;
		if (!empty($frame->data['to_uid'])) {
			//如果要查询数据库接受人id是否存在。也就是to_uid
			//__________________________验证内容合法性逻辑代码写在这里，非法用户直接停止往下执行。

			//——————————————————————————————————————————————————————————————————

			$frame->uid = $frame->client['uid'];
			$frame->to_uid = $frame->data['to_uid'];
			$data = $frame->data;
			$websocketConfig = $server->websocketConfig;
			// 分布式服务器 推送到udp端口
			if (!empty($websocketConfig['is_distributed'])) {
				$distributed = $websocketConfig['distributed'];
				$local = $distributed['local'] ?? [];

				// 如果是分布式服务，用户连接的服务器ip和端口会被存入到redis,获取当前用户所有连接设备所在服务器的ip，

				$ClientServerAll = $this->redis()->hGet("clientServer", $frame->to_uid);

				// echo "\n task/chat->actionChat 业务进程启动";
				// print_r($ClientServerAll);
				if (is_array($ClientServerAll)) {
					// echo "\n分布式服务器\n";
					// print_r($distributed['udpsocket_group']);
					$udpsocket_group = $distributed['udpsocket_group'] ?? [];

					foreach ($ClientServerAll as $key => $clientServer) {
						if (isset($clientServer['ip']) && isset($clientServer['udp_port'])) {

							// 判断是本机
							if ($clientServer['ip'] == '127.0.0.1' || ($clientServer['ip'] == $local['ip'] && $local['udp_port'] == $clientServer['udp_port'])) {
								// echo "\n本地推送";
								// 本机消息推送
								$task->finish($frame);

							} elseif (is_array($udpsocket_group)) {
								// echo "\n联机推送";
								// 联机推送
								foreach ($udpsocket_group as $idx => $udpsocket) {
									if ($udpsocket['ip'] === $clientServer['ip'] && $clientServer['udp_port'] == $udpsocket['udp_port']) {
										// echo "\n联机推送 sk验证";
										$sk = $udpsocket['secret_key'];
										$data['to_uid'] = $frame->to_uid;
										$data['uid'] = $frame->uid;

										$udpData = [
											'sk' => $sk,
											'means' => 'to_uid', //推送方法
											'to_uid' => $frame->to_uid,
											'data' => $data,
										];
										go(function () use ($clientServer, $udpData) {

											$client = new \swoole_client(SWOOLE_SOCK_UDP, SWOOLE_SOCK_SYNC);
											$client->connect($clientServer['ip'], $clientServer['udp_port']);
											$client->send(json_encode($udpData, JSON_UNESCAPED_UNICODE));
										});
									}
								}

							}
						} else {
							return;
						}
					}
				} else {
					// 用户不在线
					$task->finish($frame);
					return;
				}

			} else {
				$task->finish($frame);
			}

		} else {
			return;
		}

	}
}
