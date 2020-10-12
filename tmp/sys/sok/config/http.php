<?php
// http配置
return [
	// 扩展自身配置 本机
	'ip' => '0.0.0.0', // 监听地址
	'port' => 8880, // 监听端口
	"set" => [
		'http_parse_post' => true,
		'ssl_cert_file' => '/ssl.crt',
		'ssl_key_file' => '/ssl.key',
		'open_http2_protocol' => true,
	],
	// 分布式服务器通道
	"distributed" => [
		//提供websocket服务的服务器组，本机可连接到websocket服务器进行消息推送服务
		"websocket_group" => [
			"ip" => '192.168.0.150', //如果分布式都布置在同一局域网，这就用局域网ip。保证http服务器能链接上
			"secret_key" => "123456",
			"port" => 9501,
		],

	],
];
