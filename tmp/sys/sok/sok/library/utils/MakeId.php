<?php
namespace sok\utils;
use sok\App;

class MakeId {
	// App::config('database')
	// public $appHostId; //服务器id数字;
	public static $error = ''; //失败信息
	public static function getError() {
		return self::$error;
	}
	function __construct($config = null) {

	}
	public function __set($name, $value) {
		// echo $name . "的值为:" . $value . "<br>";
		$this->$name = $value;
	}
	public function __get($name) {
		// print_r($name);
		switch ($name) {
		case 'appHostId':
			return $this->getHostId();
			# code...
			break;

		default:
			# code...
			break;
		}
		return $name;
	}
	public function getHostId() {
		return $this->appHostId = App::config('app')["app_host_id"] ? App::config('app')["app_host_id"] : 100;
	}
	/**
	 * 随机数字
	 */
	public function makeCode($num = 6) {
		$max = pow(10, $num) - 1;
		$min = pow(10, $num - 1);
		return rand($min, $max);
	}
	/**
	 * 生成token;
	 */
	public function Token() {
		return self::Str();
	}
	/**
	 * 生成用户id;
	 * randNum blur 默认4位随机数,true|fasle|int
	 * lenth  随机数长度 int
	 * 返回值= 服务器id+时间戳+随机数；
	 */
	public function User($randNum = true, $lenth = 5) {
		if ($randNum) {
			if (is_numeric($randNum)) {
				$randNum = $randNum;
			} else {
				$randNum = self::Number();
			}
		} else {
			$randNum = '';
		}

		return $this->appHostId . time() . str_pad($randNum, $lenth, 0, STR_PAD_LEFT);
	}
	/**
	 * 生成唯一ID(28位) 2位服务器id编号+26位唯一编号;
	 */
	public function Number() {
		$code = rand(1000, 9999);
		return $code;
	}
	/*
		* array unique_rand( int $min, int $max, int $num )
		* 生成一定数量的不重复随机数
		* $min 和 $max: 指定随机数的范围
		* $num: 指定生成数量
	*/

	//随机生成不重复的N个数
	public function unique_rand($min = 10000, $max = 99999, $num = 1) {
		//初始化变量为0
		$count = 0;
		//建一个新数组
		$return = [];
		while ($count < $num) {
			//在一定范围内随机生成一个数放入数组中
			$return[] = mt_rand($min, $max);
			//去除数组中的重复值用了“翻翻法”，就是用array_flip()把数组的key和value交换两次。这种做法比用 array_unique() 快得多。
			$return = array_flip(array_flip($return));
			//将数组的数量存入变量count中
			$count = count($return);
		}
		//为数组赋予新的键名
		shuffle($return);
		return $return;
	}
	/**
	 * 生成唯一ID(18位) 由redis;
	 */
	public function Str() {
		return $this->appHostId() . md5(uniqid() . self::Number());
	}
// 	1、md5(time() . mt_rand(1,1000000));
	// 　　这种方法有一定的概率会出现重复
	// 2、php内置函数uniqid()
}