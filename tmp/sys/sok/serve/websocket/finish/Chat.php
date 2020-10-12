<?php
namespace serve\websocket\finish;
use serve\websocket\common\traits\Fast;
use serve\websocket\common\traits\Push;
use sok\App;
use sok\utils\Arr;

// [ 应用入口文件 ]
class Chat {
	use Fast, Push;
	// 对接受消息字段type=？ 进行方法映射 允许访问请求类型 例如前端数据含有type="chat" 将会触发执行 $this->$router['chat']($server,$frame);
	public $router = [
		"chat" => "actionChat",
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
	public function onFinish($server, $task_id, $frame) {

		// print_r("chat/onFinish\n");
		// print_r($task_id);
		// print_r($frame);
		// config配置router路由允许的消息类型
		$action = $frame->action;
		if (method_exists($this, $action)) {
			$this->$action($server, $frame);
		}

	}

	public function actionChat($server, $frame) {

		// echo "serve\\websocket\\finish\\actionChat\n";
		// return;
		if (isset($frame->to_uid)) {
			$frame->data['uid'] = $frame->uid;
			// 本机消息推送给指定用户id
			$this->pushUid($server, $frame->data, $frame->to_uid);

		}
	}
// 建立连接成功，存入登陆缓存，触发未读消息
	public function onOpen($server, $frame) {
		// echo "serve\\websocket\\finish\\login\n";
		// print_r($frame);
		// return;
		//uid缓存到本机缓存池， key=本机ip

		$fdInfo = $frame->fdInfo;
		$clientInfo = $frame->clientInfo;
		$fd = $clientInfo['fd'];
		$source = $fdInfo['source'];
		$uid = $fdInfo['uid'];
		$oldClient = $GLOBALS['clientInfo'][$uid] ?? [];
		$GLOBALS['client'][$fd] = $fdInfo;
		$msg = '';

		if (isset($oldClient[$source]) &&  isset($oldClient[$source]['fd'])  && (!isset($oldClient[$source]['token']) || $oldClient[$source]['token'] != $clientInfo['token'])) {

			$msg = ',该账号已在别处登录，已被您踢下线。';
			$returnMsg = [
				'message' => '该账号已在别处登录，您被踢下线',
				'type' => 'login',
				'code' => 441,
				'data' => [],
			];
			// print_r([$oldClient, $source]);
			$server->push($oldClient[$source]['fd'], json_encode($returnMsg, JSON_UNESCAPED_UNICODE));
			// 如果相同设备下当前用户有在线将会踢掉用户
			$server->close($oldClient[$source]['fd']);
		}

		$oldClient = array_merge($oldClient, [$source => $clientInfo]);
		$GLOBALS['clientInfo'][$uid] = $oldClient;

		// return;
		$returnMsg = [
			'message' => '授权成功' . $msg,
			'type' => 'login',
			'code' => 200,
			'data' => ['token'=> $clientInfo['token']],
		];

		$server->push($fd, json_encode($returnMsg, JSON_UNESCAPED_UNICODE));

		$websocketConfig = $server->websocketConfig;

		// 是分布式服务触发
		if (!empty($websocketConfig['is_distributed'])) {
			// $distributed = $server->distributed;
			$distributed = $websocketConfig['distributed'];
			go(function () use ($distributed, $uid, $source) {
				// 如果是分布式服务，用户连接的服务器ip和端口会被存入到redis
				$ClientServe = [
					'udp_port' => $distributed['local']['udp_port'],
					"port" => $distributed['local']['port'],
					'ip' => $distributed['local']['ip'],
				];
				$ClientServeAll = $this->redis()->hGet("clientServer", $uid);

				if (is_array($ClientServeAll)) {
					$ClientServeAll = array_merge($ClientServeAll, [$source => $ClientServe]);
				} else {
					$ClientServeAll = [$source => $ClientServe];
				}
				$this->redis()->hSet("clientServer", $uid, $ClientServeAll);

			});

			// $ClientServeAll = $this->redis()->hGet("clientServer", $uid);
			echo "\n已开启分布式\n";

		}

		// 是否开启数据备份，未读消息入库
		if ($websocketConfig['save_unread_db'] || $websocketConfig['backup_db']) {
			go(function () use ($uid, $websocketConfig, $server, $fd) {
				// 获取未读消息
				$oldChat = $this->getChatDb($uid);

				if (!empty($oldChat) && is_array($oldChat)) {

					foreach ($oldChat as $key => $value) {
						// $value['content'] = json_decode($value['content'], true) ?? $value['content'];
						$value['content'] = json_decode($value['content'], true) ?? $value['content'] ?? [];
						$value = json_encode($value, JSON_UNESCAPED_UNICODE);
						$server->push($fd, $value);
					}
					// 开启了数据备份，读取后修改已读状态
					if ($websocketConfig['backup_db']) {
						// 消息状态修改为已读
						// $update = $this->updateChatDb(['to_uid', '=', $uid], ['state' => 1]);
						// 消息状态修改为已读——携程
						go([$this, 'updateChatDb'], ['to_uid', '=', $uid], ['state' => 1]);

					} else {
						// 仅开启未读消息入库。读取后删除——携程
						go([$this, 'deleteChatDb'], ['to_uid', '=', $uid]);
						// $delete = $this->deleteChatDb(['to_uid', '=', $uid]);
					}

				}
			});

		}
	}

}
