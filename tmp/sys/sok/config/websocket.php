<?php
// webcoket message分布式服务器配置
return [

	// 本机服务配置
	'ip' => '0.0.0.0', // 监听地址
	'port' => 9501, // 监听端口 udp端口==port+100；请配置到distributed节点下手动填写udp_port=9601

	'is_distributed' => true, //是否分布式。开启后 将会把用户连接的ip+端口存入到redis缓存。
	'secret_key' => '123456', //允许推送消息的秘钥
	'mode' => "SWOOLE_PROCESS", // 运行模式 默认为SWOOLE_PROCESS
	'sock_type' => ["SWOOLE_SOCK_TCP", "SWOOLE_SSL"], //数组 sock type 默认为SWOOLE_SOCK_TCP | SWOOLE_SSL

// ——————————————————————对话内容 数据备份。
	'save_unread_db' => true, //未读消息存入数据库,若为true，用户离线收不到的消息会存储数据到mysql,用户上线接收消息后，会删除。

	'backup_db' => true, //是否备份全部数据 ，=true会备份全部消息数据到mysql数据库,优先权大于save_unread_db。共用统一的数据库表

	'db_name' => 'default', // 存储消息的数据库名 对应config/database.php 数据库配置节点

	// 'insert_db_turns_time' => 5, //轮流读取redis缓存写入数据库。间隔秒,一般对数据库实时要求并不高,时间可以设置在5-10秒，读取redis缓存消息堆，批量写入数据库，减少数据库操作频率。提升写入性能
	// 'insert_db_number' => 1000, //间隔秒，读取redis缓存消息堆数量，批量写入数据库，如果单台redis主机建议每秒吞吐量2000以内。

	// 允许客户端用户访问的应用来源标识
	'source' => [
		'app' => 'app',
		'pc' => 'pc',
		'weixin' => 'weixin',
	],
// websocket 相关设置
	'set' => [
		'dispatch_mode' => 2, //绑定uid=5
		'daemonize' => false, //守护进程化。
		'open_websocket_protocol' => true,

		'heartbeat_check_interval' => 5, //间隔多少秒进行心跳检测
		'heartbeat_idle_time' => 30, //间隔多少秒无状态 踢下线

		'ssl_cert_file' => '1_api.01film.cn_bundle.crt', //域名证书 请放在根路径ssl目录下
		'ssl_key_file' => '2_api.01film.cn.key', //域名证书 请放在根路径ssl目录下
		'reactor_num' => 1, //线程数量
		'worker_num' => 1, //开启主进程数量使用1,在task进程可使用进程，这样比较节省资源。因为多进程内存数据是不共享，反之需要利用redis等外界通道来关联，太浪费资源
		'task_worker_num' => 2, //swoole 任务工作进程数量工作在worker进程下的异步进程 用来处理数据不共享全局数据/可以充分利用多核cpu提示并行执行效率
		'task_enable_coroutine' => true,

	],
	// 分布式服务器通道 //允许哪些ip +秘钥连接到与本机接收推送消息
	"distributed" => [
		// 本机网络配置
		// 如果本机不提供websocket服务，local配置可以不填，local服务器可以布置在局域网其他机器。
		'local' => [
			// websoket服务本机对外提供接口访问配置，若要监听udp端口，tcp端口写在这里。ip对应到这里
			"ip" => '127.0.0.1', //本机ip分布式接口ip，（如果你接收udp的服务器在同一局域网，这就用局域网ip。要保证其他服务器能链接上本机）
			"secret_key" => "123456",
			"port" => 9501,
			'udp_port' => 9601, //udp端口号
		],

		// websocket服务器集群，UDP端口监听配置,udpsocket，每台websocket都配备了udp监听。如果接收用户不在本服务器，会根据用户所在服务器推送至远程udp口,
		"udpsocket_group" => [
			// 如果来源ip不是127.0.0.1 需要验证secret_key秘钥值
			[
				"ip" => "127.0.0.1", //ip组网，分布式连接ip ，你http服务器ip必须和这里一致，采用websocket客户端协议连接到local里的ip服务器。验证密码通过。就可以推送消息了到local服务器了。
				"secret_key" => "123456", //组网，分布式连接秘钥
				"udp_port" => 9502, //udp端口 ，webSocket集群监听跨服务器接收消息
			], [
				"ip" => "192.168.1.201",
				"secret_key" => "123456",
				"udp_port" => 9502, //udp端口
			], [
				"ip" => "127.0.0.1",
				"secret_key" => "123456",
				"udp_port" => 9503, //udp端口
			],
		],
	],
];
