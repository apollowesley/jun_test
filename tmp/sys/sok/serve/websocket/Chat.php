<?php
//本示例只支持单worker_num=1单进程，因为要使用全局变量共享，为了保证最大效率化，未使用redis等内存共享工具。
// 如果要开启多线程/进程；支持多核cpu。请用redis或其他内存工具替换$GLOBALS全局变量，来保存用户信息。
//本示例 websocket只负责与用户建立连接维持连接，和推送消息。消息内容由，其他服务器或应用来完成。通过udp端口推送过来，websocket监听了udp端口,把转发到客户端。
namespace serve\websocket;

use serve\websocket\common\traits\Fast;
use serve\websocket\common\traits\Push;
use sok\App;
use sok\utils\Url;
// use sok\utils\Arr;
// use Swoole\Timer;
use Swoole\WebSocket\Server;

//连接标识，如果开启多进程多核cpu支持，需把所有的$GLOBALS函数替换成redis等内存管理工具
$GLOBALS['client'] = []; //存储用户信息含用户id key=用户的fd,
$GLOBALS['clientInfo'] = []; //存连接信息含连接fd key=uid
// 内容格式如下：
// $ClientInfo = [
// 	'time' => time(),
// 	'fd' => $request->fd,
// ];

// $GLOBALS['clientInfo'][$uid] = $ClientInfo;

// $fdInfo = [
// 	'uid' => $uid,
// 	"source" => $source,
// 	"time" => time(),
// ];
// //uid缓存到本机缓存池， key=本机ip
// $GLOBALS['client'][$fd] = $fdInfo;

// 分布式 redis存入
// 	$ClientServer=[
//'app'=>[
//  "port" => $distributed['local']['port'],
// 	'ip' => $distributed['local']['ip'],
//   ]]
// [ 应用入口文件 ]
class Chat {
	use Fast;
	use Push;

	// public  $MakeId;
	public $server; //当前socket服务对象

	public $websocketConfig = [
		// 本机服务配置
		'ip' => '0.0.0.0', // 监听地址
		'port' => 9501, // 监听端口
		'secret_key' => '123456', //允许推送消息的秘钥
		'mode' => "SWOOLE_PROCESS", // 运行模式 默认为SWOOLE_PROCESS
		'sock_type' => ["SWOOLE_SOCK_TCP", "SWOOLE_SSL"], //数组 sock type 默认为SWOOLE_SOCK_TCP | SWOOLE_SSL
		'isInsertMysql' => true, //发送消息后是否写入到mysql数据库

		// 分布式服务器通道 //允许哪些ip +秘钥连接到与本机接收推送消息
		"distributed" => [

			'local' => [
				// 本机配置
				"host" => '27.1.0.1', //公网ip
				"ip" => '192.168.0.150', //本机ip局域组网，分布式接口ip
				"secret_key" => "123456",
				"port" => 9501,
			],
			// 搭档服务器配置
			"websocket_group" => [
				[
					"ip" => "192.168.0.150", //ip组网，分布式连接ip
					"secret_key" => "123456", //组网，分布式连接秘钥
					"port" => 9501, //服务端开启服务的端口号
				], [
					"ip" => "192.168.0.150",
					"secret_key" => "123456",
					"port" => 9501, //
				],
			],
		],
		'set' => [
			'dispatch_mode' => 2, //绑定uid=5
			'daemonize' => false, //守护进程化。

			'heartbeat_check_interval' => 10, //间隔多少秒进行心跳检测
			'heartbeat_idle_time' => 60, //间隔多少秒无状态 踢下线

			'worker_num' => 1, //开启进程数量，本示例只支持开启一个进程，否则全局变量无法共享，会出错

			'insert_db_turns_time' => 5, //轮流读取redis缓存写入数据库。间隔秒,一般对数据库实时要求并不高,时间可以设置在5-10秒，读取redis缓存消息堆，批量写入数据库，减少数据库操作频率。提升写入性能
			'insert_db_number' => 1000, //间隔秒，读取redis缓存消息堆数量，批量写入数据库，如果单台redis主机建议每秒吞吐量2000以内。
		],
	];

	public function __set($name, $vue) {
		$this->$name = $vue;
	}
	public function __construct($config = []) {
		if (!empty($config) && is_array($config)) {
			$websocketConfig = array_merge($this->websocketConfig, $config);
		} else {
			$websocketConfig = array_merge($this->websocketConfig, App::config('websocket'));
		}
		$isSsl = false;
		if (isset($websocketConfig['set']['ssl_cert_file']) && isset($websocketConfig['set']['ssl_key_file']) && !empty($websocketConfig['set']['ssl_cert_file'])) {
			$websocketConfig['set']['ssl_cert_file'] = App::getRootPath() . '/ssl/' . $websocketConfig['set']['ssl_cert_file'];
			$websocketConfig['set']['ssl_key_file'] = App::getRootPath() . '/ssl/' . $websocketConfig['set']['ssl_key_file'];
			$isSsl = true;
		}

		echo "serve\websocket\chat.php isSsl:$isSsl:\n";
		// ws服务
		// print_r($websocketConfig);
		if (isset($websocketConfig['sock_type'][1]) && $isSsl) {

			$server = new Server($websocketConfig['ip'], $websocketConfig['port'], constant($websocketConfig['mode']), constant($websocketConfig['sock_type'][0]) | constant($websocketConfig['sock_type'][1]));
			// echo "开启wss服务\n";
		} else {
			$server = new Server($websocketConfig['ip'], $websocketConfig['port'], constant($websocketConfig['mode']), constant($websocketConfig['sock_type'][0]));
		}

		$local = $websocketConfig['distributed']['local']; //本机ip配置
		// $udpsocket_group = $websocketConfig['distributed']['udpsocket_group'] ?? null; //联机ip配置

		$server->websocketConfig = $websocketConfig;

		//配置参数
		$server->set($websocketConfig['set']);

		// 当管理进程启动时调用它
		$server->on('ManagerStart', [$this, 'onManagerStart']);

		//启动每个worker进程会触发
		$server->on('WorkerStart', [$this, 'onWorkerStart']);
		//建立连接触发
		$server->on('open', [$this, 'onOpen']);

		// $onMessage = new \serve\websocket\message\Chat;
		$server->on('message', [$this, 'onMessage']); //onMessage websocket接收客户端发来的消息业务处理在这里绑定。

		$onTask = new \serve\websocket\task\Chat;
		// $onTask->onTask($server); //onTask绑定 开启多task进程在这里写业务
		$server->on('task', [$onTask, 'onTask']); //开启多task进程在这里写业务
		$onFinish = new \serve\websocket\finish\Chat;
		$server->on('finish', [$onFinish, 'onFinish']); //task处理完毕 返回到主线程

		//关闭连接触发
		$server->on('close', [$this, 'onClose']);

		//_____________________________UDP端口监听__1_外集群设防____config/websocket配置文件_udpsocket_group___节点_____________________________________________________________

		//创建Server对象，监听 127.0.0.1:9502端口，类型为SWOOLE_SOCK_UDP
		$udp_port = $websocketConfig['distributed']['local']['udp_port'];

		$udp_server = $server->listen($websocketConfig['distributed']['local']['ip'], $udp_port, SWOOLE_SOCK_UDP);

		// udp服务器组
		$udpsocket_group = $websocketConfig['distributed']['udpsocket_group'] ?? null;
		$onPacket = new \serve\websocket\packet\Chat;
		$onPacket->server = $server;
		$onPacket->udpsocket_group = $udpsocket_group;
		//监听其他服务器通过udp端口发来的数据，
		$udp_server->on('packet', [$onPacket, 'onPacket']); //task处理完毕 返回到主线程
		//监听其他服务器通过udp端口发来的数据，
		// $udp_server->on('packet', [$this, 'onPacket']); //task处理完毕 返回到主线程
		// $udp_server->on('packet', function ($serv, $data, $clientInfo) use ($server) {
		// 	$this->num = 1;
		// 	$serv->sendto($clientInfo['address'], $clientInfo['port'], json_encode([$clientInfo['address'], $this->num]));
		// });
		//task处理完毕 返回到主线程
		//___________________________________________________________UDP端口监听__2___127.0.0.1___ 单例本机无需设防__________________________________________________________________
		//创建Server对象，监听 127.0.0.1:9502端口，类型为SWOOLE_SOCK_UDP
		// $udp_port = $websocketConfig['distributed']['local']['udp_port'];
		// // 监听本机127.0.0.1Udp端口 所有Udp端口的业务逻辑相同
		// $udp_server2 = $server->listen('127.0.0.1', $udp_port, SWOOLE_SOCK_UDP);

		// // udp服务器组
		// $onPacket2 = new \serve\websocket\packet\Chat;
		// $onPacket2->server = $server;
		// $onPacket2->udpsocket_group = $udpsocket_group;
		// //监听其他服务器通过udp端口发来的数据，
		// $udp_server2->on('packet', [$onPacket2, 'onPacket']); //task处理完毕 返回到主线程
$server->on('request', function ($request,$response) {
    global $server;//调用外部的server
  print_r("request");
    // $server->connections 遍历所有websocket连接用户的fd，给所有用户推送
			$response->header('content-type', 'text/html;charset=utf-8', true);
			$response->status(200);
  $response->end("<h1>Hello Swoole. #" . rand(1000, 9999) . "</h1><div>websocket-HTTP请求</div>");
});
		$server->start();

	}
	public function onManagerStart($server) {
		// 开启轮询读取redis缓存数据堆，执行批量写入数据库
		// swoole_timer_tick(6000, function ($timer_id) {
		// 	$this->insertDb();
		// 	echo "tick-4000ms\n";

		// });
		echo "\n管理进程启动server->start，onManagerStart\n";
	}

	// 启动worker进程触发
	public function onWorkerStart($server, $worker_id) {
		echo "启动worker_id：$worker_id\n";
		// if ($server->worker_id == 0) {
		// }
	}
	public function onPacket($serv, $data, $clientInfo) {
		$this->num = 1;
		$serv->sendto($clientInfo['address'], $clientInfo['port'], json_encode([$clientInfo['address'], $this->num]));
	}

	// public function onFinish($server, $task_id, $data) {
	// 	// print_r([$data, $task_id]);
	// 	echo "onFinish\n";
	// 	// print_r($response);
	// }
	// websocket新用户创建连接触发验证在这里
	public function onOpen($server, $request) {

		//  print_r("message/onOpen\n");
		// $fd = $server->fd;
		// print_r($GLOBALS['client']);
		if (!empty($request->get)) {
			// 从全局变量取出当前用户的基本信息
			// $frame->client = $GLOBALS['client'][$frame->fd];
			$frame = (object) [];
			// 剩下业务处理交给task多进程处理，主进程继续接收消息
			// print_r($request);
			// $request->action = 'login';
			$frame->action = 'onOpen';
			$frame->data = get_object_vars($request);
			$server->task($frame);
		} else {
			$server->close($request->fd);
		}

	}

	/**
	 * 主进程收到消息转给task进程
	 * @param $server
	 * @param $frame
	 */
	public function onMessage($server, $frame) {
		// 不处理接收来的消息
		return;
		// print_r("message/onMessage\n");
		// print_r($GLOBALS['client']);
		if ($frame->data != 'ping') {
			// 从全局变量取出当前用户的基本信息
			$frame->client = $GLOBALS['client'][$frame->fd];
			$frame->action = 'onMessage';
			// 剩下业务处理交给task进程，主进程继续接收消息
			$server->task($frame);
		}
	}

	// 断开连接触发
	public function onClose($server, $fd) {
		echo "\nclient {$fd} closed.\n";
		// if ($server->exist($fd)) {
		// 	$server->push($fd, 0);
		// }
		$distributed = $server->websocketConfig['distributed'];
		$client = $GLOBALS['client'][$fd];

		if (isset($client['uid'])) {
			$uid = $client['uid'];
			$source = $client['source'] ?? "app";
			// $clientInfo = $this->redis()->hGet("ws_clientInfo", $fdInfo['uid']);
			$clientInfo = $GLOBALS['clientInfo'][$uid] ?? [];
			// echo "删除前\n";
			// print_r($fdInfo);
			// print_r($clientInfo);

			if (!empty($clientInfo)) {

				unset($clientInfo[$source]);
				if (!empty($clientInfo)) {
					// 没有在线的用户 清空缓存
					unset($GLOBALS['clientInfo'][$uid]);
					// $this->redis()->hDel("clientInfo", $fdInfo['uid']);
				} else {
					$GLOBALS['clientInfo'][$uid] = $clientInfo;
				}

			}

			// 分布式服务器 删除redis
			if (!empty($websocketConfig['is_distributed'])) {
				// 如果是分布式服务，用户连接的服务器ip和端口会被存入到redis
				go(function () use ($uid, $source) {
					$ClientServeAll = $this->redis()->hGet("clientServer", $uid);

					if (is_array($ClientServeAll) && isset($ClientServeAll[$source])) {
						unset($ClientServeAll[$source]);
					}
					// 不存在其他在线终端，删除全部缓存
					if (empty($ClientServeAll)) {

						$this->redis()->hDel("clientServer", $uid);
					} else {
						// 还存在其他在线终端，存回去。
						$this->redis()->hSet("clientServer", $uid, $ClientServeAll);
					}
				});

			}

		}
		unset($GLOBALS['client'][$fd]);
		// print_r($GLOBALS);
		// echo "下线用户信息删除后\n";

	}

}
