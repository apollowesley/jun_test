<?php
namespace sok;
include 'Loader.php'; // 引入加载器
define("ROOTPATH", dirname(__DIR__)); //项目根路径
/* 路径映射 */
Loader::addClassMap([
	'app' => ROOTPATH . DIRECTORY_SEPARATOR . 'application',
	'serve' => ROOTPATH . DIRECTORY_SEPARATOR . 'serve',
	'sok' => __DIR__ . DIRECTORY_SEPARATOR . 'library',
	'root' => ROOTPATH,
	'utils' => __DIR__ . DIRECTORY_SEPARATOR . 'library' . DIRECTORY_SEPARATOR . 'utils',
]);
Loader::$rootPath = ROOTPATH;
// 注册类库别名
// Loader::addClassAlias([
// 	// 'App' => App::class,
// 	// 'Erro' => \app\Erro::class,
// 	// 'Query' => Query::class,
// 	// 'Cache' => facade\Cache::class,
// 	// 'Config' => facade\Config::class,
// 	// 'Db' => \sok\Db::class,
// ]);

spl_autoload_register('\sok\Loader::autoload'); // 注册自动加载
// 引入全局函数
include App::getAppPath() . DIRECTORY_SEPARATOR . 'common.php';

// print_r((new App)->config('app'));

?>