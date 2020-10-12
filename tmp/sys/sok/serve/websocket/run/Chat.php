<?php
// 命名=模块名 例如Chat 文件名对应Chat.php
//新创建服务改一个名就可以了 启动入口文件对应到websocket根目录同名文件
$phpself = explode(".", substr($_SERVER['PHP_SELF'], strrpos($_SERVER['PHP_SELF'], '/') + 1));
print_r($phpself);
$mod = $phpself[0] ?? 'Chat'; //默认控制器名
$dir = dirname(dirname(dirname(__DIR__)));
// 引入基类
include $dir . '/sok/Base.php';
// print_r($argv);
$start = "serve\\websocket\\" . $mod;
echo "serve/websocet/run/$mod:触发执行启动类：$start\n";
// $http = "serve\\http\\" . $argv[2];
new $start;
