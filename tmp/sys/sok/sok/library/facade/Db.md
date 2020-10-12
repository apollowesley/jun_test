###Db 类使用说明
查看配置信息
(new App)->config('app')['database'];
### config 配置文件
文件位置 config/database.php 
$config=[
'db_user'=>[
		'type' => 'mysql',
		'hostname' => '127.0.0.1',//数据库地址
		'database' => 'admin',//数据库名
		'username' => 'root',//用户名
		'password' => 'root',//用户密码
		'hostport' => '3306',//端口号
		'dsn' => '',
		'params' => array(
		),
		'charset' => 'utf8',//字符集
		'prefix' => ''//表前缀
		]
]
		$db = new Db($config['db_user']);//初始化
		// $db->init($config['db_user']);//重置初始化
### 切换数据库
		$db::switchConfig('db_user');//切换数据库 对应config/database.php

### 查询
	$db->table(['user', 'abc' => 'a'])//要查询的表名支持as
	->field(['user' => 'u', 'abc' => 'a'])//要查询的字段 支持as
	->where([['a', 'like', 'b%'], ['b', '=', 'b']])//where条件 
	->where([['a', 'like', 'b'], ['b', '=', 'b']])//默认二次调用where第二个参数 and|或or 

	->where([['b', 'between', [1, 2]]],'and')// and 条件
	->where([['b', 'between', [1, 2]]],'or')// or 条件

	->limit('1,9')//原生方法
	->group($arr)//原生方法
	->order($arr)//原生方法
	->select($isfetchAll);//查询 返回结果集默认是fethAll为true
		$db->table(['user'])->field(['username'])->where([['id', '=', '10101555674757204']])->select();
		print_r($db->data);
		返回 空 或结果
###写入支持批量写入
		$data=[];
		$data[]=['username' => '232d', 'id' => time(), 'content' => '内容d'];
		$data[]=['username' => '232d', 'id' => time() + 1, 'content' => '内容d'];
		$db->into($data);
		// print_r($db->getInsertSql());//获取sql写入语句
		print_r($db->table(['user'])->insert());

		//$this->db //获取执行完成的sql语句
		返回 空 或影响的条数
###删除 where不支持>,<,!,*条件
		$db->table(['user'])->where([['id', '=', '100155722489400000']]->delete());
		print_r($db->getDeleteSql());//获取sql语句
		返回 空 或影响的条数

###修改 where不支持>,<,!,*条件
		$db->table(['user'])->where([['id', '=', '100155722489400001']])->set(['username' => '我修改23']);
		print_r($db->update());//执行修改 返回影响的条数 或空
		print_r($db->getUpdateSql());//获取sql语句
		返回 空 或影响的条数

### 简述
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