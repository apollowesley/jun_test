<?php
namespace serve\http\common\traits;
use \sok\App;
use \sok\Db;
use \sok\Redis;
use \sok\Sql;
use \sok\utils\Arr;
// use \sok\utils\MakeId;
/**
 *
 */
trait Fast {
	public $attribute;
	public $redis;
	public $db;

	// ————————————————————————————————生成唯一id $GLOBALS[$attribute]=time().0000;
	public function makeOnlyID($type = 'chat') {
		$time = time();
		$attribute = 'incrID' . $type;
		if (empty($GLOBALS[$attribute])) {
			$GLOBALS[$attribute] = $time * 10000;
		} elseif (substr($GLOBALS[$attribute], 0, 10) == $time) {
			$GLOBALS[$attribute] = $GLOBALS[$attribute] + 1;
		} else {
			$GLOBALS[$attribute] = $time * 10000;
		}
		return $GLOBALS[$attribute];
	}
	// 连接redis
	public function redis($name = 'default') {
		$redis = $this->redis;
		// 连接redis类
		if (empty($redis) && empty($redis = new Redis(App::config('redis')[$name]))) {
			echo "redis连接失败，正则连接备用redis服务器";
			//连接失败切换到备用redis服务器
			$redis = new Redis(App::config('redis')['default']);
			if (!empty($redis)) {
				echo "备用服务器连接成功";
			}

		}
		$this->redis = $redis;
		// echo "redis";
		// p
		return $this->redis;
	}
	// 连接mysql
	public function db($num = 5) {
		$dbAll = $this->db;
		if (empty($dbAll)) {

			// 连接mysql类
			if (empty($db) && empty($db = new Db(App::config('database')['default']))) {
				echo "mysql数据库连接失败，正则连接备用redis服务器<br>\n";
				//连接失败切换到备用redis服务器
				if (!empty($db = new Db(App::config('database')['spare']))) {
					echo "备用mysql服务器连接成功<br>\n";
				}

			}
		}

		// print_r($redis);
		if (!$db) {
			echo "mysql备用服务器连接失败，请检查服务器<br>\n";
			return null;
		}
		return $this->db = $db;
	}

}
?>