###Db 类使用说明
查看配置信息
(new App)->config('app')['redis'];
### config 配置文件
文件位置 config/redis.php 
$config=[
	'default' => [
		'host' => '192.168.0.246',
		// 'host' => '127.0.0.1',
		'port' => 6379,
		'password' => 'root',
		'select' => 0,
		'timeout' => 0, //连接超时时间
		'expire' => 31536000, //默认存储有效期1年
		'persistent' => false, //是否长连接,关闭脚本连接不释放
		'prefix' => '', //key前缀
		'serialize' => true, //存取前 是否json格式化
	],
]
		$redis= new Redis($config['db_user']);//初始化
### 切换库
		$redis->switchConfig('db_user');//切换数据库 对应config/redis.php



### 简述
	$redis->set('name',$value,3600);
	如果设置成功返回true，否则返回false。

	缓存自增
	针对数值类型的缓存数据，可以使用自增操作，例如：

	// name自增（步进值为1）
	$redis->inc('name');
	// name自增（步进值为3）
	$redis->inc('name',3);
	缓存自减
	针对数值类型的缓存数据，可以使用自减操作，例如：

	// name自减（步进值为1）
	$redis->dec('name');
	// name自减（步进值为3）
	$redis->dec('name',3);
	获取缓存
	获取缓存数据可以使用：

	dump($redis->get('name')); 
	如果name值不存在，则默认返回 false。

	支持指定默认值，例如：

	dump($redis->get('name','')); 
	表示如果name值不存在，则返回空字符串。

	删除缓存
	$redis->rm('name'); 

	获取并删除缓存
	$redis->pull('name'); 

	如果name值不存在，则返回null。
	获取并更新缓存有效期
	$redis->getUpdate('name',$expire=100); 

	如果name值不存在，则返回null。
	清空缓存
	$redis->clear(); 

	判断缓存是否存在
	$redis->has('name')

	查看缓存有效期
	$redis->ttl('name')

	获取缓存key带前缀
	$redis->getCacheKey('name')

	// 读取后格式化
	$redis->unserialize('name')
	// 写入前格式化
	$redis->serialize(['value']) 
	// 前缀
	$redis->getCacheKey('name')
	// 缓存时间
	$redis->getExpireTime($expire = 100)
### 其他原生操作
$redis->handler->get('a');
