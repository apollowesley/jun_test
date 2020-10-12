<?php

include '../sok/Base.php';

// echo time();

// // print_r(sok\App::config());
// $db = new sok\Db;
// $sql = new sok\Sql;
// $sql1 = $sql->table('chat')->field(["create_time" => 'time', 'content' => 'c'])->where([['id', 'LIKE', '"1%"']])->getSql('select');
// // $sql1 = $sql->table('user')->field('username')->where([['username', 'LIKE', '"15%"']])->where([['id', '=', 10101555674769293], ['username', '=', 10101555674769293]], 'or')->getSql('select');
// // $sql1 = $sql->table('user')->field('user')->into(['username' => time(), 'nickname' => time()])->where([['id', '>', 1]])->getSql('insert');
// // echo $sql->error;
// // $sql->clear();
// echo "<pre>";
// // echo '<br>', $sql1, '<br>';
// $data = ['data' => $db->getAll($sql1), 'sql' => $sql1];
// // $sql->deleteGuolv = [];
// // $sql1 = $sql->table('chat')->where([['id', '=', time()]])->where(['create_time', '<', time()], 'or')->getSql('delete');
// // $d = $db->exec($sql1);
// print_r($data['data']);
// return;
// $data = [];
// // $time = 155910666219999;
// for ($i = 0; $i < 10000; $i++) {
// 	$sql->init();
// // $sql1 = $sql->table('user')->field('username')->where([['username', '=', '"15591015800"']])->set(['password' => 'abcd' . time() . $i, 'headimg' => time() . $i])->getSql('update'); //单进程每秒2000+
// 	// $sql1 = $sql->table('user')->field('username')->where([['username', 'LIKE', '"15%"']])->where([['id', '>', 10101555674769293], ['username', '<', 10101555674769293]], 'or')->limit(30, 5)->getSql('select'); //$db->query($sql1)->fetchAll()单进程 每秒10000+

// // $sql1 = $sql->table('user')->field('username')->where([['id', '=', 10101555674769293], ['id', '=', 10101555674769293]])->getSql('select');//$db->query($sql1)->fetch()单进程每秒10000+
// 	$inData = ['id' => time() . $i, 'uid' => time() . $i, 'to_uid' => time() . $i, 'create_time' => time()];
// 	$inData['content'] = json_encode(json_encode($inData));
// 	$sql1 = $sql->table('chat')->into($inData)->getSql('insert'); //$db->exec($sql1);单进程每秒2500+
// 	$d = $db->exec($sql1);
// 	// $sql->deleteGuolv = [];
// 	// $sql1 = $sql->table('chat')->where([['id', '=', $inData['id']]])->where(['create_time', '<', time()], 'or')->getSql('delete'); //$db->exec($sql1);单进程每秒2500+

// // $data[] = ['data' => $db->exec($sql1), 'sql' => $sql1, "time" => time()];
// 	$d = $db->exec($sql1);
// 	// $pdoStatement = $db->prepare($sql1, array(PDO::ATTR_CURSOR => PDO::CURSOR_FWDONLY));
// 	// $execRet = $pdoStatement->execute($mapData);
// 	// $lastInsertId = $execRet ? ($returnInsertId ? $db->lastInsertId('id') : $execRet) : false;

// 	$data[] = ['data' => $d, 'pd' => $execRet, 'sql' => $sql1, "time" => time(), 'id' => $d];
// }
// echo "<br>$i<br>";
// echo time();
// echo "<pre>";
// print_r($data);
// echo $sql->error;
// $sql->clear();
// // echo $sql->table('user')->field('user')->into(['a' => 'b', 'ac' => 'bc'])->where([['id', '>', 1]])->getSql('insert');
// // echo $sql->table('user')->field('user')->where([['id', '=', 1]])->getSql('delete');
// echo "string";
// return;

function udpGet($sendMsg = '', $ip = '192.168.0.150', $port = '9502') {
	$handle = stream_socket_client("udp://{$ip}:{$port}", $errno, $errstr);
	if (!$handle) {
		die("ERROR: {$errno} - {$errstr}\n");
	}
	fwrite($handle, $sendMsg . "\n");
	$result = fread($handle, 1024);
	fclose($handle);
	return $result;
}

//支持接口means=to_uid|to_uid_all|to_fd|to_fd_all
// 接口验证秘钥sk+
$apiAuth = json_decode('{"sk":"123456","means":"to_uid"}', true);
// 要发送的数据内容
$data = json_decode('{"to_uid":"123456","type":"chat","uid":"123456","time":"1599877568","content":{"type":"text","value":"新内容"}}', true);

$apiAuth['data'] = array_merge($data, $_GET);
// echo "<pre>";
// print_r($apiAuth);
// 转成json字符串发给服务
$data = json_encode($apiAuth, JSON_UNESCAPED_UNICODE);
$state = udpGet($data);
//对方在线返回1，否则返回0
print_r($state);

?>