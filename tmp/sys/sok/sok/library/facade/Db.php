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

namespace sok\facade;

/**
 * Class Db
 * $db= NEW Db($config)
 * $db->switchConfig($dbDepotName = 'db_config2');
 * $db->table('user')->field(['username'=>'name'])->where([['id','=',123],['state','=',1]])->limit(1,10)->group('id')->order(true)->select();//$db->getSelectSql();
 * $db->table(['user'])->into([['id'=>123],['id'=>124]])->insert(); //$db->getInsertSql();
 * $db->table(['user'])->set(['id'=>123])->where()->update();//$db->getUpdateSql();
 * $db->table(['user'])->into(['id'=>123])->where()->delete();//$db->getDeleteSql();
 * $db->query($sql);
 * $db->exec($sql);
 * $dbh=$db->connection;//原生连接
 * try {
$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
//开启事务机制
$dbh->beginTransaction();
$dbh->exec("insert into staff (id, first, last) values (23, 'Joe', 'Bloggs')");
$dbh->exec("insert into salarychange (id, amount, changedate)
values (23, 50000, NOW())");
//提交事务
$dbh->commit();

} catch (Exception $e) {
//事务回滚
$dbh->rollBack();
echo "Failed: " . $e->getMessage();
}
 */
class Db {
	public static $where = null;
	public static $field = null;
	public static $into = null;
	public static $error = null;
	public static $table = null;
	public static $name = null;
	public static $find = null;
	public static $data = null;
	public static $order = null;
	public static $group = null;
	public static $limit = null;
	public static $set = null;
	public static $lastSql = null;
	public static $whereGuolv = ['=', 'like', 'in', 'between', '>', '<', '>=', '<=', 'not between']; //仅支持where使用的连接符
	public static $deleteGuolv = ['<', '>', '*', '!']; //删除语句不可使用的字符
	/**
	 * 当前数据库连接对象
	 * @var Connection
	 */
	public static $connection;

	/**
	 * 数据库配置
	 * @var array
	 */
	public static $config = [

	];
	// 是否存在表前缀
	public static $isPrefix = null; //数据表前缀

	function __construct($config = null) {
		$this->init($config);
	}

	public function __set($name, $value) {
		// echo $name . "的值为:" . $value . "<br>";
		$this->$name = $value;
	}
	public function __get($name) {

		switch ($name) {
		case 'connection':
			if (!self::${$name}) {
				// echo "string";
				self::${$name} = $this->connect();
			}
			# 数据库连接

			break;
		case 'lastSql':
			if (self::$lastSql) {
				return self::$lastSql;
			}
			# 数据库连接
			return $this->getSelectSql();
			break;
		default:
			return null;
			self::${$name} = null;
			# code...
			break;
		}
		return self::${$name};
	}

	/**
	 * 配置
	 * @access public
	 * @param  mixed $config
	 * @return void
	 */
	public static function clear() {
		self::$where = null;
		self::$into = null;
		self::$field = null;
		self::$table = null;
		self::$name = null;
		self::$find = null;
		self::$data = null;
		self::$order = null;
		self::$group = null;
		self::$limit = null;
		self::$lastSql = null;
		self::$isPrefix = null;
		self::$connection = null;
		self::$set = null;
	}
	public function init($config = null) {
		self::clear();
		if (!self::$config) {
			self::$config = isset(App::config('database')['default']) ? App::config('database')['default'] : [];
		}

		if ($config && is_array($config)) {

			if (is_array(self::$config)) {
				self::$config = array_merge(self::$config, $config);
			} else {
				self::$config = $config;
			}

		}
		if (isset(self::$config['prefix'])) {
			self::$isPrefix = self::$config['prefix'];
		}
		return self::$config;

	}

	public static function switchConfig($dbDepotName = null) {

		if ($dbDepotName && isset(App::config('database')[$dbDepotName])) {
			self::clear();
			$defConfig = [];
			if (isset(App::config('database')['default'])) {
				$defConfig = App::config('database')['default'];
			}
			self::$config = array_merge($defConfig, App::config('database')[$dbDepotName]);

			return true;
		} else {
			return false;
		}
	}
	// Db->table('user')
	// 要操作的数据表名[tablename=>'as tablename']
	public function table($arr = []) {
		$table = [];

		$prefix = isset(self::$config['prefix']) ? self::$config['prefix'] : '';

		foreach ($arr as $key => $value) {
			if (is_numeric($key)) {
				$key = $value;
			}
			$table[$prefix . $key] = $value;

		}
		self::$table = $table;
		return $this;
	}

	public function field($arr = []) {
		self::$field = $arr;
		return $this;
	}
	public function set($arr = []) {
		$set = '';
		foreach ($arr as $key => $value) {
			$set .= $key . ' = ' . $this->toSqlStr($value) . ',';
		}
		self::$set = $set;
		return $this;
	}

// Db->table('user')->where([])
	//数组转where条件字符串,arr=[['field','=','val']]
	//多次调用where(),第二个参数是连接符$connect='or'|'and'
	// 需要toWhere()辅助完成转换
	public function where($arr = [], $connect = null) {

		$newWhere = $this->toWhere($arr);

		if (!empty($newWhere)) {

			if (!empty($connect)) {
				$where = self::$where . ' ' . $connect . ' ';
			} else {
				self::$where = ' ';
				$where .= ' ';
			}
			self::$where = $where . '(' . $newWhere . ')';
		}
		return $this;
	}
	public function limit($str = '') {
		self::$limit = $str;
		return $this;
	}
	public function group($arr) {
		self::$group = $arr;
		require $this;
	}
	public function order($arr) {
		self::$order = $arr;
		require $this;
	}
	// public function save($arr) {

	// }

	// 数组转字符串，数据表名带前缀，支持as
	public function getTableName($arr = null) {
		if (!$arr) {
			$arr = self::$table;
		}
		$str = '';
		if (is_array($arr)) {
			foreach ($arr as $key => $value) {

				if (is_numeric($key) || $key == $value) {
					$str .= ' `' . $value . '`,';
				} else {

					$str .= ' `' . $key . '` ,';

				}
			}
			return rtrim($str, ',');
		}
		return '';

	}
	//数组转字符串, as名称
	public function toAs($arr = []) {
		$str = '';
		foreach ($arr as $key => $value) {

			if (is_numeric($key) || $key == $value) {
				$str .= ' ' . $value . ',';
			} else {

				$str .= ' ' . $key . '  ' . $value . ' ,';

			}
		}
		return $str;
	}
	// 数组批量转成where条件字符串，需toWhereOne()辅助完成
	public function toWhere($arr = null) {
		$where = '';
		$n = 0;
		if (is_array($arr)) {
			if (is_array($arr[0])) {
				foreach ($arr as $key => $value) {
					$where .= $this->toWhereOne($value, $n);
				}
			} else {
				$where .= $this->toWhereOne($arr, $n);
			}
		}
		return $where;
	}
	//一维数组 组装合成 where条件 字符串
	public function toWhereOne($value = [], $n = 0) {

		$connect = '';
		if (is_array($value) && count($value) == 3 && in_array($value[1], self::$whereGuolv)) {
			if ($n > 0) {
				if (is_numeric($key)) {
					$connect = ' AND ';
				} else {
					$connect = ' ' . $key . ' ';
				}
			}
			$content = '';
			if (is_array($value[2])) {
				if ($value[1] == 'BETWEEN') {
					$content = $value[2][0] . ' AND ' . $value[2][1];
				} elseif ($value[1] == 'in') {
					$content = $value[2][0];
					for ($i = 1; $i < count($value[2]); $i++) {
						$content .= ',' . $value[2][$i];
					}
					$connect = ' (' . $content . ') ';
				}

			} else {
				$content = $value[2];
			}
			$where .= $connect . $value[0] . ' ' . $value[1] . ' ' . $content;

		} else {
			$this->error = 'WHERE条件有误' . json_encode($value) . ';条件仅支持' . json_encode(self::$whereGuolv);
			return '';
		}
		$n++;
		return $where;

	}
	// 字符转数据库可读内容 把非数字字符串添加单引号
	public function toSqlStr($v) {
		// return $v;
		return is_numeric($v) ? $v : "'" . $v . "'";
		// return is_numeric($v) ? $v : "'" . $v . "'";
	}
	// 仅判断是否允许允许写入数据库的字段
	public function fieldIsIntoDB($arr = null, $field = null) {
		if (empty($arr)) {
			// 未设置任何限制 返回true
			return true;
		} else {
			foreach ($arr as $key => $value) {
				// 是放行的字段 返回true
				if ($value == $field) {
					return true;
				}
			}
			return false;
		}

	}
//数组拼接成可写入数据库的字符 id,num VALUES (1,2)
	public function into($arr = null, $onlyInto = []) {

		if ($arr) {
			$k = [];
			$v = '';
			$isMany = false;
			foreach ($arr as $key => $value) {
				if (is_array($value)) {
					$isMany = true;
					$value1 = '';

					foreach ($value as $k2 => $v2) {
						if ($this->fieldIsIntoDB($onlyInto, $k2)) {
							$k[$k2] = $k2;
							$value1 .= $this->toSqlStr($v2) . ',';
						}
					}
					if (!empty($value1)) {
						$v .= "(" . rtrim($value1, ",") . "),";
					}
				} else {
					if ($this->fieldIsIntoDB($onlyInto, $key)) {
						$k[$key] = ' ' . $key . '';
						$v .= ' ' . $value . ',';
					}
				}

			}
			$field = '';
			$v = rtrim($v, ",");
			if (!$isMany) {
				$v = "(" . $v . ")";
			}
			foreach ($k as $kk => $kv) {
				$field .= $kv . ',';
			}
			self::$into = '(' . rtrim($field, ",") . ') VALUES ' . $v;
		}
		return $this;

	}
	public function getSet() {
		return self::$set ? rtrim(self::$set, ',') : '';
	}
	// 拼接成写入sql语句
	public function getInsertSql() {
		return !empty(self::$into) ? 'INSERT INTO ' . $this->getTableName() . self::$into : '';
	}
	// 拼接成写入sql语句
	public function getUpdateSql() {
		$where = $this->getWhere();
		if (strstr($where, '>') || strstr($where, '<') || strstr($where, '*') || strstr($where, '!')) {
			// echo "where条件不支持含有特殊字符>,<,*,!,";
			$this->error = 'getDeleteSql不能包含特殊字符，查询条件不符合';
			return null;
		}
		return 'UPDATE ' . $this->getTableName() . ' SET ' . $this->getSet() . $where;
	}

	// 拼接成写入sql语句
	public function getDeleteSql() {
		$where = $this->getWhere();
		if (strstr($where, '>') || strstr($where, '<') || strstr($where, '*') || strstr($where, '!')) {
			// echo "where条件不支持含有特殊字符>,<,*,!,";
			$this->error = 'getDeleteSql不能包含特殊字符，查询条件不符合';
			return null;
		}
		return 'DELETE FROM' . $this->getTableName() . $where;
	}

	// 拼接成查询sql语句
	public function getSelectSql() {
		return "SELECT " . $this->getField() . ' FROM ' . $this->getTableNameAs() . $this->getWhere() . $this->getLimit();
	}

	public function getLimit($limit = null) {
		$limit = $limit ? $limit : self::$limit;
		if (is_array($limit)) {
			$str = '';
			foreach ($limit as $key => $value) {
				$str .= $value . ",";
			}
			return ' LIMIT ' . rtrim($str, ',');
		}
		return !empty($limit) ? ' LIMIT ' . $limit : '';

	}

	public function getWhere() {
		if (!empty(self::$where)) {
			return ' WHERE ' . self::$where;
		}
		return '';
	}

	public function getTableNameAs($isAs = false) {
		return !empty(self::$table) ? rtrim($this->toAs(self::$table), ",") : '';
	}

	public function getField() {
		return !empty(self::$field) ? rtrim($this->toAs(self::$field), ",") : '';
	}

	public function query($sql = null) {
		if (empty($sql)) {
			$sql = self::$lastSql;
		} else {
			self::$lastSql = $sql;
		}
		if (!empty($sql)) {
			return $this->connection->query($sql);
			$this->error = '数据库操作有误';

		} else {
			$this->error = 'sql 语句不存在';
		}

		return null;
	}
	public function exec($sql = null) {

		if (empty($sql)) {
			$sql = self::$lastSql;
		} else {
			self::$lastSql = $sql;
		}
		if (!empty($sql)) {
			return $this->connection->exec($sql);
			$this->error = '数据库操作有误';

		} else {
			$this->error = 'sql 语句不存在';
			return false;
		}
	}
	// 删除一条信息
	public function update() {
		self::$lastSql = $this->getUpdateSql();
		if ($query = $this->exec(self::$lastSql)) {
			return $this->data = $query;
		}
		return null;
	}
	// 删除一条信息
	public function delete() {
		self::$lastSql = $this->getDeleteSql();
		if ($query = $this->exec(self::$lastSql)) {
			return $this->data = $query;
		}
		return null;
	}
	// 写入 支持批量写入
	public function insert() {

		self::$lastSql = $this->getInsertSql();
		if ($query = $this->exec(self::$lastSql)) {
			return $this->data = $query;
		}
		return null;
	}
	// 查询 支持结果集
	public function select($isFetchAll = null) {

		self::$lastSql = $this->getSelectSql();
		$query = $this->query(self::$lastSql);
		if (!empty($query)) {
			return $isFetchAll ? $this->data = $query->fetchAll() : $this->data = $query->fetch();
		}
		return null;
	}

	/**
	 * 获取数据库配置
	 * @access public
	 * @param  string $config 配置名称
	 * @return mixed
	 */
	public static function getConfig($name = '') {
		if ('' === $name) {
			return self::$config;
		}

		return isset(self::$config[$name]) ? self::$config[$name] : null;
	}
	public function __isset($name) {
		return $name;
	}
	// 检查pdo数据库连接是否可用
	public function pdo_ping($dbconn) {
		try {
			$dbconn->getAttribute(PDO::ATTR_SERVER_INFO);
		} catch (PDOException $e) {
			if (strpos($e->getMessage(), 'MySQL server has gone away') !== false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 切换数据库连接
	 * @access public
	 * @param  mixed         $config 连接配置
	 * @param  bool|string   $reset 连接标识 true 重新连接
	 * @return mixed 返回查询对象实例
	 * @throws Exception
	 */
	public static function connect($config = null, $reset = false) {

		// 解析配置参数
		$options = self::parseConfig($config ?: self::$config);

		if (!isset($options['type'])) {
			$options['type'] = 'mysql';
		}
		// 创建数据库连接对象实例
		if (isset($options['dsn']) && !empty($options['dsn'])) {
			$dsn = $options['dsn'];
		} else {
			$dsn = "{$options['type']}:host=" . $options['hostname'] . ";dbname=" . $options['database'] . ";charset={$options['charset']}; port={$options['hostport']}";
		}
		if (self::$connection && !$reset) {
			return self::$connection;
		}
		// echo "连接<br>\n";
		// print_r($options);
		// echo "$dsn;u=" . $options['username'] . ';ass=' . $options['password'];
		try {
			// $connection = new \PDO($dsn, $options['username'], $options['password'], $options['params']);
			return self::$connection = new \PDO($dsn, $options['username'], $options['password'], $options['params']);
		} catch (\Exception $e) {
			self::$error = '数据库连接失败';
			return false;
		}
	}

	/**
	 * 数据库连接参数解析
	 * @access private
	 * @param  mixed $config
	 * @return array
	 */
	private static function parseConfig($config) {
		if (is_string($config) && false === strpos($config, '/')) {
			// 支持读取配置参数
			$config = self::$config;
		}

		$result = is_string($config) ? self::parseDsnConfig($config) : $config;

		return $result;
	}

	/**
	 * DSN解析
	 * 格式： mysql://username:passwd@localhost:3306/DbName?param1=val1&param2=val2#utf8
	 * @access private
	 * @param  string $dsnStr
	 * @return array
	 */
	private static function parseDsnConfig($dsnStr) {
		$info = parse_url($dsnStr);

		if (!$info) {
			return [];
		}

		$dsn = [
			'type' => $info['scheme'],
			'username' => isset($info['user']) ? $info['user'] : '',
			'password' => isset($info['pass']) ? $info['pass'] : '',
			'hostname' => isset($info['host']) ? $info['host'] : '',
			'hostport' => isset($info['port']) ? $info['port'] : '',
			'database' => !empty($info['path']) ? ltrim($info['path'], '/') : '',
			'charset' => isset($info['fragment']) ? $info['fragment'] : 'utf8',
		];

		if (isset($info['query'])) {
			parse_str($info['query'], $dsn['params']);
		} else {
			$dsn['params'] = [];
		}

		return $dsn;
	}

	public static function __callStatic($method, $args) {
		// 	echo "Db类自动连接，使用原生db语句方法";
		return call_user_func_array([static::connect(), $method], $args);
	}
}
