<?php
return [
	// http服务路由配置
	"http" => [
		"default" => [
			"class" => "index\\controller\\index", //映射类
			"action" => "index", //执行的方法
		],
		// 地址栏path
		"/chat/index" => [
			// 映射的控制器类
			"class" => "chat\\controller\\index",
			// 映射的控制器方法
			"action" => "index",
			// 请求方法
			"method" => "POST",
		],
		// 地址栏path
		"/chat/send" => [
			// 映射的控制器类
			"class" => "chat\\controller\\chat",
			// 映射的控制器方法
			"action" => "send",
			// 请求方法
			"method" => "GET",
		],
	],

	// 客户端直连发来消息。websocket 路由映射
	"websocket" => [
		// 根据onMessage方法，拦截客户端发来的消息，type=? 进行方法映射，映射到serve/websocket/message/服务名.php 内的方法执行
		"message" => [
			"chat" => "actionChat",
		],
	],

];
?>