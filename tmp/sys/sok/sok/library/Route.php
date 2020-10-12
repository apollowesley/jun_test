<?php
// +----------------------------------------------------------------------
// | ThinkPHP [ WE CAN DO IT JUST THINK ]
// +----------------------------------------------------------------------
// | Copyright (c) 2006~2018 http://thinkphp.cn All rights reserved.
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: liu21st <liu21st@gmail.com>
// +----------------------------------------------------------------------

namespace sok;
//----------------------------------
// Leephp 错误接管类
// 2017-07-06
// PengchongLee
//----------------------------------

// error_reporting — 设置应该报告何种 PHP 错误
// error_reporting(0);

class Route {

// 过滤请求 返回路由地址 第一个参数path名，第二个请求方法,第三路由配置的key
	public static function http($pathInfo = "chat", $method = "GET", $routeName = 'http') {
		$uri = explode("/", $pathInfo);
		// 删除空的
		foreach ($uri as $key => $value) {
			if (empty($value)) {
				unset($uri[$key]);
			}
		}
		// $class = "";
		$pathLen = count($uri);

		if (App::config('app')['routeLen'] == 3) {

			$module = $uri[1] ?? 'index';
			$controller = $uri[2] ?? 'index';
			$action = $uri[3] ?? 'index';
			$path = "/" . $module . "/" . $controller . "/" . $action;
		} else {
			// echo $pathLen . "\n";
			// $module = App::config('app')['module'] ?? 'index';
			$controller = $uri[1] ?? 'index';
			$action = $uri[2] ?? 'index';
			$path = "/" . $controller . "/" . $action;

		}
		if (!App::config('app')['isRoute']) {
			// 不开启严格路由

			$class = $module . "\\controller\\" . $controller;
			return ['action' => $action, "class" => $class];
		} else {
			// 开启严格路由
			$route = App::config('route')[$routeName] ?? [];
			// print_r($route);

			print_r($path);
			foreach ($route as $key => $value) {

				if ($key === $path) {
					$value['method'] = $value['method'] ?? $method;
					$value['class'] = $value['class'] ?? $route['default']['class'];
					$value['action'] = $value['action'] ?? $route['default']['action'];

					if (empty($value['method'])) {
						return $value;
					} elseif ($value['method'] === $method) {
						return $value;
					}

				}
			}
			return $route['default'];
		}

	}

}