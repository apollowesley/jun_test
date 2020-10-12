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

/**
 * Redis缓存驱动
 * 要求安装phpredis扩展：https://github.com/nicolasff/phpredis
 * @author    yichen <2782268022@qq.com>
 */
class Redis {
	protected $options = [
		// 'host' => '192.168.0.246',
		'host' => '127.0.0.1',
		'port' => 6379,
		'password' => 'root',
		'select' => 0,
		'timeout' => 0, //连接超时时间
		'expire' => 31536000, //默认存储有效期1年
		'persistent' => false, //是否长连接,关闭脚本连接不释放
		'prefix' => '', //key前缀
		'serialize' => true, //存取前 是否json格式化
	];
	public $handler; //redis使用的连接
	/**
	 * 序列化方法 因为我其他服务器用了thinkphp的缓存方法，这个序列化参数必须一致
	 * @var array
	 */
	protected $serialize = ['serialize', 'unserialize', 'think_serialize:', 16];

	/**
	 * 序列化数据 写入前
	 * @access protected
	 * @param  mixed $data
	 * @return string
	 */
	protected function serialize($data) {
		// print_r($data);
		if (is_scalar($data) || !$this->options['serialize']) {
			return $data;
		}

		$serialize = $this->serialize[0];

		return $this->serialize[2] . $serialize($data);
	}

	/**
	 * 反序列化数据
	 * @access protected
	 * @param  string $data
	 * @return mixed
	 */
	protected function unserialize($data) {

		if ($this->options['serialize'] && 0 === strpos($data, $this->serialize[2])) {
			$unserialize = $this->serialize[1];

			return $unserialize(substr($data, $this->serialize[3]));
		} else {
			return $data;
		}
	}

	/**
	 * 注册序列化机制
	 * @access public
	 * @param  callable $serialize      序列化方法
	 * @param  callable $unserialize    反序列化方法
	 * @param  string   $prefix         序列化前缀标识
	 * @return $this
	 */
	public function registerSerialize($serialize, $unserialize, $prefix = 'think_serialize:') {
		$this->serialize = [$serialize, $unserialize, $prefix, strlen($prefix)];
	}
	/**
	 * 架构函数
	 * @access public
	 * @param  array $options 缓存参数
	 */
	public function __construct($options = null) {
		if (!is_array($options)) {
			$options = isset(App::config('redis')['default']) ? App::config('redis')['default'] : [];
		}
		$this->options = array_merge($this->options, $options);
		return $this->handler($this->options);

	}
	/**
	 * 返回句柄对象，可执行其它高级方法
	 *
	 * @access public
	 * @return object
	 */
	public function handler($options = null) {
		if (is_array($options)) {
			$options = isset(App::config('redis')['default']) ? App::config('redis')['default'] : [];
			$this->options = array_merge($this->options, $options);
			if (extension_loaded('redis')) {
				try {
					$this->handler = new \Redis;

					if ($this->options['persistent']) {
						$this->handler->pconnect($this->options['host'], $this->options['port'], $this->options['timeout'], 'persistent_id_' . $this->options['select']);
					} else {

						$this->handler->connect($this->options['host'], $this->options['port'], $this->options['timeout']);

					}
				} catch (\Exception $e) {
					$msg = "redis服务异常";
					\sok\Erro::send(["msg" => $msg, "e" => $e]);
					// print_r($e);
					return false;
				}
				if (!empty($this->options['password'])) {
					$this->handler()->auth($this->options['password']);
				}

				if (0 != $this->options['select']) {
					$this->handler->select($this->options['select']);
				}
			} elseif (class_exists('\Predis\Client')) {
				$params = [];
				foreach ($this->options as $key => $val) {
					if (in_array($key, ['aggregate', 'cluster', 'connections', 'exceptions', 'prefix', 'profile', 'replication', 'parameters'])) {
						$params[$key] = $val;
						unset($this->options[$key]);
					}
				}

				if ('' == $this->options['password']) {
					unset($this->options['password']);
				}

				$this->handler = new \Predis\Client($this->options, $params);

				$this->options['prefix'] = '';
			} else {
				throw new \BadFunctionCallException('not support: redis');
			}
			return $this->handler;
		} elseif ($this->handler) {
			return $this->handler;
		}
		return null;
	}

	// 写入前数组进行批量格式化
	public function serializeArr($Arr) {
		if ($this->options['serialize']) {
			foreach ($Arr as $key => $value) {
				$Arr[$key] = $this->serialize($value);
			}
		}
		return $Arr;
	}
	// 读取时批量格式化还原，适用与取列表、哈希内容进行格式化
	public function unserializeArr($Arr) {
		if ($this->options['serialize']) {
			foreach ($Arr as $key => $value) {
				$Arr[$key] = $this->unserialize($value);
			}
		}
		return $Arr;
	}
	// 前缀
	public function getCacheKey($name = '') {
		return $this->options['prefix'] ? $this->options['prefix'] . $name : $name;
	}
	// 缓存时间
	public function getExpireTime($expire = null) {
		return is_numeric($expire) ? $expire : $this->options['expire'];
	}
	public function getTagKey($name) {
		return $name;
		// return $this->options['prefix'] ? $this->options['prefix'] . $name : $name;
	}

	/**
	 * 判断缓存
	 * @access public
	 * @param  string $name 缓存变量名
	 * @return bool
	 */
	public function has($name) {
		return $this->handler()->exists($this->getCacheKey($name));
	}
	/**
	 * 查看缓存有效期
	 * @access public
	 * @param  string $name 缓存变量名
	 * @return number
	 */
	public function ttl($name) {
		return $redis->handler()->ttl($this->getCacheKey($name));
	}
	/**
	 * 获取并删除缓存
	 * @access public
	 * @param  string $name 缓存变量名
	 * @return bool
	 */
	public function pull($name) {
		$value = $this->get($name);
		if ($value) {
			$this->rm($name);
		}
		return $value;
	}
	/**
	 * 获取并更新缓存 时间
	 * @access public
	 * @param  string $name 缓存变量名
	 * @return bool
	 */
	public function getUpdate($name, $expire = null) {
		$value = $this->get($name);
		if ($value && is_numeric($expire)) {
			$this->set($name, $value, $expire);
		}
		return $value;
	}
	/**
	 * 读取缓存
	 * @access public
	 * @param  string $name 缓存变量名
	 * @param  mixed  $default 默认值
	 * @return mixed
	 */
	public function get($name, $default = false) {
		// $this->readTimes++;

		$value = $this->handler()->get($this->getCacheKey($name));
		if (empty($value) || false === $value) {
			return $default;
		}

		return $this->unserialize($value);
	}

	/**
	 * 写入缓存
	 * @access public
	 * @param  string            $name 缓存变量名
	 * @param  mixed             $value  存储数据
	 * @param  integer|\DateTime $expire  有效时间（秒）
	 * @return boolean
	 */
	public function set($name, $value, $expire = null) {
		// $this->writeTimes++;
		$key = $this->getCacheKey($name);
		$expire = $this->getExpireTime($expire);

		$value = $this->serialize($value);

		if ($expire) {
			$result = $this->handler()->setex($key, $expire, $value);
		} else {
			$result = $this->handler()->set($key, $value);
		}

		return $result;
	}

	/**
	 * 获取旧值并写入新值
	 * @access public
	 * @param  string            $name 缓存变量名
	 * @param  mixed             $value  存储数据
	 * @param  integer|\DateTime $expire  有效时间（秒）
	 * @return boolean
	 */
	public function getset($name, $value, $expire = null) {
		// $this->writeTimes++;
		$key = $this->getCacheKey($name);
		$expire = $this->getExpireTime($expire);

		$value = $this->serialize($value);

		if ($expire) {
			$result = $this->handler()->setex($key, $expire, $value);
		} else {
			$result = $this->handler()->set($key, $value);
		}

		return $this->unserialize($result);
	}
	// 如果值存在就在结尾追加新值
	public function append($name, $value, $expire = null) {

		$key = $this->getCacheKey($name);

		$value = $this->serialize($value);

		if ($expire) {
			$result = $this->handler()->setex($key, $expire, $value);
		} else {
			$result = $this->handler()->append($key, $value);
		}

		return $result;
	}
	/**
	 * 自增缓存（针对数值缓存）
	 * @access public
	 * @param  string    $name 缓存变量名
	 * @param  int       $step 步长
	 * @return false|int
	 */
	public function inc($name, $step = 1, $expire = 60) {
		// $this->writeTimes++;

		$key = $this->getCacheKey($name);

		$val = $this->handler()->incrby($key, $step);
		if ($val == 1) {
			$this->handler()->expire($key, $expire);
		}
		return $val;

	}
	public function expire($name, $expire = 60) {
		return $this->handler()->expire($this->getCacheKey($name), $expire);
	}
	/**
	 * 自减缓存（针对数值缓存）
	 * @access public
	 * @param  string    $name 缓存变量名
	 * @param  int       $step 步长
	 * @return false|int
	 */
	public function dec($name, $step = 1) {
		// $this->writeTimes++;

		$key = $this->getCacheKey($name);

		return $this->handler()->decrby($key, $step);
	}

	/**
	 * 删除缓存
	 * @access public
	 * @param  string $name 缓存变量名
	 * @return boolean
	 */
	public function rm($name) {
		// $this->writeTimes++;

		return $this->handler()->del($this->getCacheKey($name));
	}
// HSET 哈希相关方法————————————————————————————————————————————————

// 设置哈希
	public function hSet($name, $field, $value) {
		$key = $this->getCacheKey($name);
		// $expire = $this->getExpireTime($expire);

		$value = $this->serialize($value);

		$result = $this->handler()->hset($key, $field, $value);

		return $result;
	}
	//批量设置哈希表 k-v值

	public function hMset($name, $fieldValueArr) {
		$key = $this->getCacheKey($name);

		$fieldValueArr = $this->serializeArr($fieldValueArr);

		$result = $this->handler()->hMset($key, $fieldValueArr);

		return $result;
	}
	// 设置哈希 只有 keyfield不存在时会设置
	public function hSetNx($name, $field, $value) {
		$key = $this->getCacheKey($name);
		// $expire = $this->getExpireTime($expire);

		$value = $this->serialize($value);

		$result = $this->handler()->hSetNx($key, $field, $value);

		return $result;
	}
	// 获取一个值
	public function hGet($name, $field) {
		return $this->unserialize($this->handler()->hGet($this->getCacheKey($name), $field));
	}
	// 获取哈希所有key vue
	public function hGetAll($name) {
		return $this->unserialize($this->handler()->hGetAll($this->getCacheKey($name)));
	}
	// 获取哈希表所有字段
	public function hKeys($name) {
		return $this->unserialize($this->handler()->hKeys($this->getCacheKey($name)));
	}
	// 获取哈希表字段数量
	public function hLen($name) {
		return $this->unserialize($this->handler()->hLen($this->getCacheKey($name)));
	}
	//获取哈希表 一些字段的值
	public function hMget($name, $field) {
		return $this->unserialize($this->handler()->hMget($this->getCacheKey($name), $field));
	}
	//删除哈希表 一个段或多个字段
	public function hDel($name, $field) {
		return $this->handler()->hDel($this->getCacheKey($name), $field);
	}
	// 查看哈希表 key 中，指定的字段是否存在。 返回true 或false
	public function hExists($name, $field) {
		return $this->handler()->hExists($this->getCacheKey($name), $field);
	}

	// 获取哈希表所有值，不存在返回空
	public function hVals($name, $field) {
		return $this->unserializeArr($this->handler()->hVals($this->getCacheKey($name), $field));
	}

	// 迭代哈希表所有键值对， key cursor [MATCH pattern] [COUNT count]
	public function hScan($name, $cursor, $match, $count = 5) {
		return $this->unserializeArr($this->handler()->hScan($this->getCacheKey($name), $cursor, $match, $count));
	}
	// 为哈希表 key 中的指定字段的整数值加上增量 increment 。正数为+，负数为-；不能用字符串。会返回false
	public function hIncrBy($name, $field, $number) {
		return $this->handler()->hIncrBy($this->getCacheKey($name), $field, $number);
	}

	// 为哈希表 key 中的指定字段的浮点数值加上增量 increment 。不存在的key初始化0，不能用负数
	public function hIncrByFloat($name, $field, $number) {
		return $this->handler()->hIncrByFloat($this->getCacheKey($name), $field, $number);
	}
	// list 相关方法——————————————————————————————————————————————————————

	// 从右面追加值
	public function rPush($name, $value) {
		$key = $this->getCacheKey($name);
		// $expire = $this->getExpireTime($expire);

		$value = $this->serialize($value);
		$this->handler()->rPush($key, $value);
	}
	// 从左面取出值
	public function lRange($name, $idx = 0, $rightIdx = 10) {
		$data = $this->handler()->lRange($this->getCacheKey($name), $idx, $rightIdx);
		return $this->unserializeArr($data);

	}

	//截取保留索引$idx-r之间的数据，删除其他数据，第二个参数-1代表最右边
	public function lTrim($name, $idx = 0, $rightIdx = -1) {
		return $this->handler()->lTrim($this->getCacheKey($name), $idx, $rightIdx);
	}
	//从最左边索引0开始取出-索引endIdx之间的数据,删除源数据
	public function getlTrim($name, $endIdx = 0) {
		$key = $this->getCacheKey($name);
		$data = $this->lRange($key, 0, $endIdx);
		if (!empty($data)) {
			$this->handler()->lTrim($key, count($data), -1);
			return $data;
		}
		return false;
	}
	// 左面移除一个值返回并 删除库存
	public function lPop($name) {

		return $this->unserializeArr($this->handler()->lPop($this->getCacheKey($name)));
	}
	//右面 移除最后一个值返回并 删除库存
	public function rPop($name) {
		return $this->unserializeArr($this->handler()->rPop($this->getCacheKey($name)));
	}

}
