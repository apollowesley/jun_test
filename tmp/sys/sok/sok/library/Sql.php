<?php
// +----------------------------------------------------------------------
// +----------------------------------------------------------------------
// | Licensed ( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author: yichen <2782268022@qq.com>
// +----------------------------------------------------------------------

namespace sok;

/**
 * 用于生成原生sql语句。
 * $sql= NEW Sql($config)
 * $sql->switchConfig($dbDepotName = 'db_config2');
 * $sql->table('user')->field(['username'=>'name'])->where([['id','=',123],['state','=',1]])->limit(1,10)->group('id')->order(true);//$db->getSelectSql();
 * $sql->table(['user'])->into([['id'=>123],['id'=>124]])->getSql('insert'); //$db->getInsertSql();
 * $sql->table(['user'])->set(['id'=>123])->where()->getSql('update');//$db->getUpdateSql();
 * $sql->table(['user'])->into(['id'=>123])->where()getSql('delete';//$db->getDeleteSql();
 * 有了sql语句，就可以用任何数据库来进行操作了
 * $db->query($sql);
 * $db->exec($sql);
 *———————————————————————————————————————————————————————测试示例—————
$sql->init();
$db=new Db;//数据库模型，
修改
// $sql1 = $sql->table('user')->field('username')->where([['username', '=', '"15591015800"']])->set(['password' => 'abcd' . time() . $i, 'headimg' => time() . $i])->getSql('update'); //单进程每秒2000+
查询
// $sql1 = $sql->table('user')->field('username')->where([['username', 'LIKE', '"15%"']])->where([['id', '>', 10101555674769293], ['username', '<', 10101555674769293]], 'or')->limit(30, 5)->getSql('select'); //$db->query($sql1)->fetchAll()单进程 每秒10000+
查询
// $sql1 = $sql->table('user')->field('username')->where([['id', '=', 10101555674769293], ['id', '=', 10101555674769293]])->getSql('select');//$db->query($sql1)->fetch()单进程每秒10000+
新增
// $sql1 = $sql->table('user')->field('user')->into(['username' => time() . $i, 'nickname' => time() . $i, 'id' => time() . $i, 'create_time' => time()])->getSql('insert'); //$db->exec($sql1);单进程每秒2500+
删除
$sql->deleteGuolv = []; //如果不想过滤!=|>|<特殊条件字符，可以在请求前设置过滤参数为空
// $sql1 = $sql->table('user')->where([['id', '=', $time + $i]])->getSql('delete'); //$db->exec($sql1);单进程每秒2500+

 */
class Sql {
	public $where = null;
	public $field = null;
	public $into = null;
	public $error = null;
	public $table = null;
	public $name = null;
	public $find = null;
	public $data = null;
	public $order = null;
	public $group = null;
	public $limit = null;
	public $set = null;

	public $whereGuolv = ['=', 'like', 'in', 'between', '>', '<', '>=', '<=', 'not between']; //仅支持where使用的连接符
	public $deleteGuolv = ['<', '>', '*', '!']; //删除语句不可使用的字符

	/**
	 * 数据库配置
	 * @var array
	 */
	public $config = [

	];
	// 是否存在表前缀
	public $isPrefix = null; //数据表前缀

	function __construct($config = null) {
		if (is_array($config)) {
			$this->config = $config;
		} elseif (is_string($config)) {
			$this->switchConfig($config);
		} else {
			$this->config = App::config('database')['default'];
		}
	}

	public function __set($name, $value) {
		// echo $name . "的值为:" . $value . "<br>";
		$this->$name = $value;
	}
	public function __get($name) {

		switch ($name) {
		case 'lastSql':
			if ($this->lastSql) {
				return $this->lastSql;
			}
			# 数据库连接
			return $this->lastSql = $this->getSelectSql();
			break;
		default:
			return null;
			# code...
			break;
		}
	}

	/**
	 * 配置
	 * @access public
	 * @param  mixed $config
	 * @return void
	 */
	public function clear() {
		$this->where = null;
		$this->into = null;
		$this->field = null;
		$this->table = null;
		$this->name = null;
		$this->find = null;
		$this->data = null;
		$this->order = null;
		$this->group = null;
		$this->limit = null;
		$this->lastSql = null;
		// $this->isPrefix = null;
		$this->set = null;
	}
	// 切换配置
	public function switchConfig($dbDepotName = null) {

		if ($dbDepotName && isset(App::config('database')[$dbDepotName])) {
			$this->clear();
			$defConfig = [];
			if (isset(App::config('database')['default'])) {
				$defConfig = App::config('database')['default'];
			}
			$this->config = array_merge($defConfig, App::config('database')[$dbDepotName]);
			if (isset($this->config['prefix'])) {
				$this->isPrefix = $this->config['prefix'];
			}
			return true;
		} else {
			return false;
		}
	}
	// Db->table('user')
	// 要操作的数据表名[tablename=>'as tablename']
	public function table($arr = []) {
		$table = [];
		$prefix = isset($this->config['prefix']) ? $this->config['prefix'] : '';
		if (is_array($arr)) {

			foreach ($arr as $key => $value) {
				if (is_numeric($key)) {
					$key = $value;
				}
				$table[$prefix . $key] = $value;

			}
		} else {
			$table[$prefix . $arr] = $arr;
		}

		$this->table = $table;
		return $this;
	}

	public function field($arr = []) {
		$this->field = $arr;
		return $this;
	}
	public function set($arr = []) {
		$set = '';
		foreach ($arr as $key => $value) {
			$set .= $key . ' = ' . $this->toSqlStr($value) . ',';
		}
		$this->set = $set;
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
				$where = $this->where . ' ' . $connect . ' ';
			} else {
				$this->where = ' ';
				$where = ' ';
			}
			$this->where = $where . '(' . $newWhere . ')';
		}
		return $this;
	}
	public function limit($str = '', $num = null) {
		if (is_numeric($num) && is_numeric($str)) {
			$this->limit = $str . ',' . $num;
		} else {
			$this->limit = $str;
		}

		return $this;
	}
	public function group($arr) {
		$this->group = $arr;
		require $this;
	}
	public function order($arr) {
		$this->order = $arr;
		require $this;
	}
	// public function save($arr) {

	// }

	// 数组转字符串，数据表名带前缀，支持as
	public function getTableName($arr = null) {
		if (!$arr) {
			$arr = $this->table;
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
		if (is_array($arr)) {
			foreach ($arr as $key => $value) {

				if (is_numeric($key) || $key == $value) {
					$str .= ' ' . $value . ',';
				} else {

					$str .= ' ' . $key . '  ' . $value . ' ,';

				}
			}
		} else {
			$str = $arr;
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
					$n++;
				}
			} else {
				$where .= $this->toWhereOne($arr, $n);
			}
		} else {
			$where = $arr;
		}
		return $where;
	}
	//一维数组 组装合成 where条件 字符串
	public function toWhereOne($value = [], $n = 0) {
		// echo "$n<br>";
		$connect = '';
		$where = '';
		if (is_array($value) && count($value) == 3 && in_array(strtolower($value[1]), $this->whereGuolv)) {
			if ($n > 0) {
				if (is_numeric($value[2])) {
					$connect = ' AND ';
				} else {
					$connect = ' ' . $value[2] . ' ';
				}
			}
			// $content = '';
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
			$where .= strtoupper($connect) . $value[0] . ' ' . $value[1] . ' ' . $content;

		} else {
			$this->error = 'WHERE条件有误' . json_encode($value) . ';条件仅支持' . json_encode($this->whereGuolv);
			return '';
		}
		return $where;

	}
	// 字符转数据库可读内容 把非数字字符串添加双引号
	public function toSqlStr($v) {
		// return $v;
		// return is_numeric($v) ? $v : '"' . $v . '"';
		return is_numeric($v) ? $v : "'" . $v . "'";
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
	public function getSet() {
		return $this->set ? rtrim($this->set, ',') : '';
	}
	//数组拼接成可写入数据库的字符 id,num VALUES (1,2)
	public function into($arr = null, $onlyInto = []) {

		if (!empty($arr) && is_array($arr)) {
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
						$k[$key] = '' . $key . '';
						$v .= '' . $this->toSqlStr($value) . ',';
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
			$this->into = '(' . rtrim($field, ",") . ') VALUES ' . $v;
		}
		return $this;

	}
	//_________________________________________________获取完整sql语句_____________________________________________________

	public function getSql($way = 'select') {

		$sql = '';
		$way = strtolower($way); //转小写

		switch ($way) {
		case 'insert':
			$sql = $this->getInsertSql();
			break;
		case 'delete':

			$sql = $this->getDeleteSql();
			break;
		case 'update':
			$sql = $this->getUpdateSql();
			break;
		case 'select':
			$sql = $this->getSelectSql();
			break;
		default:
			$sql = $this->getSelectSql();
			break;
		}
		return $sql;
	}
	// 拼接成写入sql语句
	public function getInsertSql() {
		return !empty($this->into) ? 'INSERT INTO ' . $this->getTableName() . $this->into : '';
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

		$getTableName = $this->getTableName();

		if (empty($where) || empty($getTableName)) {
			// echo "where条件不支持含有特殊字符>,<,*,!,";
			$this->error = '表名和查询条件不能为空';
			return null;
		} elseif (is_array($this->deleteGuolv)) {

			foreach ($this->deleteGuolv as $key => $value) {

				if (strstr($where, $value)) {

					$this->error = "getDeleteSql不能包含特殊字符{$value}，查询条件不符合";
					return null;
				}
			}
		}
		return 'DELETE FROM' . $getTableName . $where;
	}

	// 拼接成查询sql语句
	public function getSelectSql() {
		$field = $this->getField();
		$table = $this->getTableNameAs();
		$where = $this->getWhere();
		$limit = $this->getLimit();
		if (empty($table) || empty($where)) {
			return $where . $getTableName . $value;
			$this->error = 'table表名，field字段，where条件不能为空';
			return '';
		}
		if (empty($field)) {
			$field = ' * ';
		}
		return "SELECT " . $field . ' FROM ' . $table . $where . $limit;
	}

	public function getLimit($limit = null) {
		$limit = $limit ? $limit : $this->limit;
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
		if (!empty($this->where)) {
			return ' WHERE ' . $this->where;
		}
		return '';
	}

	public function getTableNameAs($isAs = false) {
		return !empty($this->table) ? rtrim($this->toAs($this->table), ",") : '';
	}

	public function getField() {
		return !empty($this->field) ? rtrim($this->toAs($this->field), ",") : '';
	}

	/**
	 * 获取数据库配置
	 * @access public
	 * @param  string $config 配置名称
	 * @return mixed
	 */
	public function getConfig($name = '') {
		if ('' === $name) {
			return $this->config;
		}

		return isset($this->config[$name]) ? $this->config[$name] : null;
	}

	// public function __call($method, $args) {
	// 	print_r($method);
	// 	// 	echo "Db类自动连接，使用原生db语句方法";
	// 	return call_user_func_array([Db::connect(), $method], $args);
	// }
}
