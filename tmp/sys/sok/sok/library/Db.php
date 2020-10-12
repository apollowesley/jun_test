<?php
// +----------------------------------------------------------------------
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: yichen <2782268022@qq.com>
// +----------------------------------------------------------------------

namespace sok;

/**
 * Class Db
 * $db= NEW Db($config)
 * $db->switchConfig($dbDepotName = 'db_config2');//切换数据库
 * $db->get($sql);//执行qruey($sql)->fetch();获取一条数据
 * $db->getAll($sql);//执行qruey($sql)->fetchAll();获取多条数据

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

	public static $lastSql = null;
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

	}
	public function __get($name) {
		// print_r($name);
		switch ($name) {
		case 'connection':
			if (!self::${$name}) {
				// echo "string";
				self::${$name} = self::connect($config = null, true);
			}
			# 数据库连接

			break;
		default:
			return null;
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

		self::$connection = null;

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

	public function query($sql = null) {

		if (!empty($sql)) {

			$data = $this->connection->query($sql);
			if ($data) {

				return $data;
			} else {

				$this->connection = self::connect($config = null, true);
				return $this->connection->query($sql);
			}

			$this->error = '数据库操作有误';

		} else {
			$this->error = 'sql 语句不存在';
		}

		return null;
	}
	// 查询 支持结果集
	public function select($sql = null, $isFetchAll = null) {

		self::$lastSql = $sql ?? $this->getSelectSql();
		$query = $this->query(self::$lastSql);
		if (!empty($query) && is_object($query)) {
			return $isFetchAll ? $this->data = $query->fetchAll(\PDO::FETCH_ASSOC) : $this->data = $query->fetch(\PDO::FETCH_ASSOC);
		}
		return null;
	}
	public function exec($sql = null) {

		if (!empty($sql)) {
			$data = $this->connection->exec($sql);

			if ($data) {
				return $data;
			} else {

				$this->connection = self::connect($config = null, true);
				return $this->connection->exec($sql);
			}
			$this->error = '数据库操作有误';

		} else {
			$this->error = 'sql 语句不存在';
			return false;
		}
	}
	public function get($sql) {
		$qurey = $this->connection->query($sql);
		if ($qurey && is_object($qurey)) {
			return $qurey->fetch(\PDO::FETCH_ASSOC);
		}
		return null;
	}
	public function getAll($sql) {
		$qurey = $this->connection->query($sql);
		if ($qurey && is_object($qurey)) {
			return $qurey->fetchAll(\PDO::FETCH_ASSOC);
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
		// print_r([$dsn, $options['username'], $options['password'], $options['params']]);
		// $connection = new \PDO($dsn, $options['username'], $options['password'], $options['params']);
		// self::$connection = $connection;
		// print_r(self::$connection);
		// echo "$dsn;u=" . $options['username'] . ';ass=' . $options['password'];
		try {
			$connection = new \PDO($dsn, $options['username'], $options['password'], $options['params']);
			self::$connection = $connection;
			// $connection = new \PDO($dsn, $options['username'], $options['password'], $options['params']);
			return $connection;
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
	public function __call($method, $args) {

		return static::connect()->$method($args[0]);

	}
	public static function __callStatic($method, $args) {
		// 	echo "Db类自动连接，使用原生db语句方法";
		return call_user_func_array([static::connect(), $method], $args);
	}
}
