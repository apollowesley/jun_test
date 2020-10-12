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

class Erro {
	public function __construct() {
		$this->iserr();
	}
	public function iserr() {
		// set_exception_handler — 设置用户自定义的异常处理函数
		set_exception_handler([$this, 'ex']);

		// set_error_handler — 设置用户自定义的错误处理函数
		set_error_handler([$this, 'err']);

		// register_shutdown_function — 注册一个会在php中止时执行的函数
		register_shutdown_function([$this, 'last_error']);
	}

	// 异常接管
	public function ex($ex) {
		// 获取错误异常信息
		$message = $ex->getMessage();
		// 获取错误异常代码
		$code = $ex->getCode();
		// 获取错误异常文件
		$file = $ex->getFile();
		// 获取错误异常文件行数
		$line = $ex->getLine();
	}

	// 错误接管
	public function err($code, $message, $file, $line) {
		// 记录日志
		$this->errlog($code, $message, $file, $line);
	}

	// 脚本结束前获取最后错误
	public function last_error() {
		// error_get_last — 获取最后发生的错误
		$last = error_get_last();
		// set_error_handler有些错误是无法获取的，所以价格判断
		if ($last['type'] == 1 || $last['type'] == 4 || $last['type'] == 16 || $last['type'] == 64 || $last['type'] == 128) {
			$this->errlog($last['type'], $last['message'], $last['file'], $last['line']);
		}
	}

	// 错误信息收集并记录 (参数传输的顺序不一样，参数还不一样)
	public function errlog($code, $message, $file, $line) {
		// 拼接错误信息
		$errstr = date('Y-m-d h:i:s') . "\r\n";
		$errstr .= '  错误级别：' . $code . "\r\n";
		$errstr .= '  错误信息：' . $message . "\r\n";
		$errstr .= '  错误文件：' . $file . "\r\n";
		$errstr .= '  错误行数：' . $line . "\r\n";
		$errstr .= "\r\n";

		// error_log — 发送错误信息到某个地方
		error_log($errstr, 3, __DIR__ . '/error.log');
	}
	public static function send($Arr = []) {
		print_r(['Erro.send', $Arr['msg']]);
		return $msg;
	}

}