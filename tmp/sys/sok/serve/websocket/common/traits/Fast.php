<?php
namespace serve\websocket\common\traits;
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
	public $saveDb;
	public $saveSql;
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

	// public function arr() {
	// 	if ($this->arr) {
	// 		return $this->arr;
	// 	} else {
	// 		return $this->arr = new Arr;
	// 	}
	// }
	// 把消息存入数据库
	public function saveChatDb($data = []) {
		// ____________________________________________修改数据格式-数据库可写入规范____________________始__
		$time = time();
		$create_time = $time;

		$inData = ['uid' => $data['uid'], 'to_uid' => $data['to_uid'], 'create_time' => $create_time, 'state' => $state, 'update_time' => $time, 'state' => $data['state']];

		$inData['content'] = json_encode($data['content'], JSON_UNESCAPED_UNICODE);

		$inData['type'] = $data['type'] ?? $inData['content']['type'] ?? '';

		if (empty($inData['content']) || empty($inData['type'])) {
			return;
		}

		$inData['id'] = $data['id'] ?? $this->makeOnlyID();

		// echo $inData['id'] . ":写入库\n";
		// 配置数据库类
		if (empty($this->saveDb)) {
			$dbName = App::config('websocket')['save_dbtable'] ?? 'default';
			$saveDbConfig = App::config('database')[$dbName] ?? null;
			$saveDb = new Db($saveDbConfig);
			$this->saveDbConfig = $saveDbConfig;
			$this->saveDb = $saveDb;

			$saveSql = new Sql($saveDbConfig);
			$this->saveSql = $saveSql;
		} else {
			// 已连接数据库
			$saveSql = $this->saveSql;
			$saveDb = $this->saveDb;
			// 清楚上次数据
			$saveSql->clear();
		}

		$sql = $saveSql->table('chat')->into($inData)->getSql('insert'); //$db->exec($sql1);单进程每秒2500+

		return $saveDb->exec($sql) ? $inData : 0;

	}
	// 获取消息
	public function getChatDb($to_uid, $state = 0) {
		if (empty($this->saveDb)) {
			$dbName = App::config('websocket')['save_dbtable'] ?? 'default';
			$saveDbConfig = App::config('database')[$dbName] ?? null;
			$saveDb = new Db($saveDbConfig);
			$this->saveDbConfig = $saveDbConfig;
			$this->saveDb = $saveDb;

			$saveSql = new Sql($saveDbConfig);
			$this->saveSql = $saveSql;
		} else {
			$saveSql = $this->saveSql;
			$saveDb = $this->saveDb;
			$saveSql->clear();
			// $this->saveDb->init();

		}
		$saveSql = $this->saveSql;
		$sql = $saveSql->table('chat')->field(['id', 'to_uid', 'uid', 'type','state',"create_time" => "time", 'content'])->where([['to_uid', '=', $to_uid], ['state', '=', $state]])->getSql('select');

		return $saveDb->getAll($sql);
	}
	// 修改消息状态,用户登录读完未读消息后，消息状态改为已读。
	public function updateChatDb($where = [], $updateK_V = ['state' => 1]) {
		// echo "update()/db";
		// print_r([$where, $updateK_V]);
		if (is_array($where) && is_array($updateK_V)) {
			if (empty($this->saveDb)) {
				$dbName = App::config('websocket')['save_dbtable'] ?? 'default';
				$saveDbConfig = App::config('database')[$dbName] ?? null;
				$saveDb = new Db($saveDbConfig);
				$this->saveDbConfig = $saveDbConfig;
				$this->saveDb = $saveDb;

				$saveSql = new Sql($saveDbConfig);
				$this->saveSql = $saveSql;
			} else {
				$saveSql = $this->saveSql;
				$saveDb = $this->saveDb;
				$saveSql->clear();
			}

			$saveSql = $this->saveSql;
			$updateK_V['update_time'] = time();
			$sql = $saveSql->table('chat')->where($where)->set($updateK_V)->getSql('update'); //$db->exec($sql1);单进程每秒2500+
			$inData['Sql'] = $sql;
			// print_r($inData);
			return $saveDb->exec($sql);
		}
	}

	public function deleteChatDb($where) {
		// echo "delete()/db";
		if (is_array($where)) {
			if (empty($this->saveDb)) {
				$dbName = App::config('websocket')['save_dbtable'] ?? 'default';
				$saveDbConfig = App::config('database')[$dbName] ?? null;
				$saveDb = new Db($saveDbConfig);
				$this->saveDbConfig = $saveDbConfig;
				$this->saveDb = $saveDb;

				$saveSql = new Sql($saveDbConfig);
				$this->saveSql = $saveSql;
			} else {
				$saveSql = $this->saveSql;
				$saveDb = $this->saveDb;
				$saveSql->clear();
			}

			$saveSql = $this->saveSql;
			// $inData = ['id' => time(), 'uid' => $data['uid'], 'to_uid' => $data['to_uid'], 'create_time' => time(), 'state' => $state];
			$sql = $saveSql->table('chat')->where($where)->getSql('delete'); //$db->exec($sql1);单进程每秒2500+

			// $inData['jieguo'] = $saveDb->exec($sql);
			// $inData['Sql'] = $sql;

			return $saveDb->exec($sql);
		}
	}

}
?>