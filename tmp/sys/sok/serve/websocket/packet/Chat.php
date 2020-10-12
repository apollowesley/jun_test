<?php
namespace serve\websocket\packet;
use serve\websocket\common\traits\End;
use serve\websocket\common\traits\Fast;
use serve\websocket\common\traits\Push;
use sok\App;
use sok\utils\Arr;

// [ 应用入口文件 ]
class Chat {
	use Fast, Push, End;

	function __construct() {

	}
	public $server; //websocket 主线server
	public $udpsocket_group; //udp分布式组
	/**
	 * 收到消息触发
	 * @param $server
	 * @param $data [json [means:"toUid"|toAll|isOnLine,to_uid:"123645",uid:"123411",data:[content:['type':'text','text':'第一条消息']]]]
	 * @$data['means'] toUid[向用户ui发送消息]|toAll[向所有人发送]|isOnLine[用户是否在线]
	 * @$data['data'] 要发送的数据
	 * @to_uid[接收方uid] $uid[发送方uid]
	 * @param $clientInfo
	 * @return 1|0 [1用户在线|0用户不在线]
	 */
	public function onPacket($serv, $data, $clientInfo) {
		// 测试并发能力
		// if ($this->num) {
		// 	$this->num = $this->num + 1;
		// } else {
		// 	$this->num = 1;
		// }
		// if ($this->num % 500 == 0) {
		// 	echo $this->num . "\n";
		// 	# code...
		// }
		// print_r($data);
		// print_r([$clientInfo['address']]);
		// $serv->sendto($clientInfo['address'], $clientInfo['port'], json_encode([$clientInfo['address'], 1, $data]));

		// print_r($clientInfo);
		$server = $this->server;
		$udpsocket_group = $this->udpsocket_group;
		$isAuth = false;
		$data = json_decode($data, true);
		// 本机ip免鉴权
		if ($clientInfo['address'] === '127.0.0.1') {

			$isAuth = true;
		} else {

			// 分布式服务器验证。 需要在config目录websocket.php->udpsokcket节点设置secret_key秘钥和ip，这里验证秘钥和ip
			foreach ($udpsocket_group as $key => $value) {
				if ($value['ip'] === $clientInfo['address'] && $value['secret_key'] === $data['sk']) {
					$isAuth = true;
					// $data = $value['data'] ?? $value;
					break;
				}
			}
		}

		$isOnLine = 0; //对方是否在线
		$data['state'] = $isOnLine;
		if ($isAuth === true) {

			// 鉴权成功 可以发送消息了
			$means = $data['means'] ?? 'to_uid'; //发送方法
			// echo "\nUdp发送成功" . $means;
			// print_r($data['data']);
			switch ($means) {
			case 'to_fd':
				// 发给单个用户
				$isOnLine = 1;
				$data = $data['data'] ?? [];
				if (!empty($data['to_fd'])) {
					$to_uid = $data['to_fd'];
					$isOnLine = $this->pushFd($server, $data, $to_fd);
				}
			// break;
			case 'to_fd_all':
				$data = $data['data'] ?? [];
				$fdAll = $data['fd_all'] ?? '';
				// 消息推送给全部在线用户
				$isOnLine = $this->pushFdAll($server, $data, $fdAll);
				// $isOnLine = 1;
				# code...
				break;
			case 'to_uid':

				$data = $data['data'] ?? [];
				//print_r($data);
				$data['state'] = $GLOBALS['clientInfo'][$data['to_uid']] ? 1 : 0;

				if (!empty($data['to_uid'])) {
					$to_uid = $data['to_uid'];

					$isOnLine = $this->pushUid($server, $data, $to_uid);
					if (is_array($isOnLine)) {
						$isOnLine = json_encode($isOnLine);
					}
					// $serv->sendto($clientInfo['address'], $clientInfo['port'], $data['state']);
				}
				break;
			case 'to_uid_all':
				// 发给单个用户
				$data = $data['data'] ?? [];
				$uidAll = $data['uid_all'] ?? '';
				$isOnLine = $this->pushUidAll($server, $data, $uidAll);

				break;
			case 'is_on_line':
				if (!empty($data['to_uid']) && empty($GLOBALS['clientInfo'][$data['to_uid']])) {
					// 用户不在线
					$isOnLine = 0;
				} else {
					$isOnLine = 1;
				}
				break;
			default:
				$isOnLine = 0;
				# code...
				break;
			}

		}
		// 发给单个用户
		$serv->sendto($clientInfo['address'], $clientInfo['port'], json_encode(['onLine' => $data['state']]));

	}

}
