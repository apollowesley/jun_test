<?php

// 毫秒级时间戳函数
function getMillisecond() {
	list($t1, $t2) = explode(' ', microtime());
	return (float) sprintf('%.0f', (floatval($t1) + floatval($t2)) * 1000);
}

//______________________________请求函数
#websocket服务对外开放api接口示例
// include '../sok/Base.php';
// UDP请求函数
function udpGet($sendMsg = '', $ip = '127.0.0.1', $port = '9502') {
	$ip = "192.168.0.191";
	$handle = stream_socket_client("udp://{$ip}:{$port}", $errno, $errstr);
	if (!$handle) {
		die("ERROR: {$errno} - {$errstr}\n");
	}
	fwrite($handle, $sendMsg . "\n");
	$result = fread($handle, 1024);
	fclose($handle);
	return $result;
}

//______________________________请求接口数据格式
// 接口验证秘钥sk+
$apiAuth = json_decode('{"sk":"123456","means":"to_uid"}', true);

//______________________________推送到服务器的数据内容
$data = json_decode('{"to_uid":"123456","type":"chat","uid":"123456","time":"1599877568","content":{"type":"text","value":"新内容"}}', true);

$apiAuth['data'] = array_merge($data, $_GET);
// echo "<pre>";
// print_r($apiAuth);
// return;
//______________________________数据内容转成json字符串
$data = json_encode($apiAuth, JSON_UNESCAPED_UNICODE);
$startTime = getMillisecond();

//______________________________用for循环进行10000次请求向服务器发送消息
echo "开始时间-startTime:" . $startTime . "<br>";
for ($i = 0; $i < 10000; $i++) {
	$result = udpGet($data);
}
$endTime = getMillisecond();
echo "完成时间-endTime:" . $endTime . "<br>";
echo "耗时毫秒-take up time:" . ($endTime - $startTime);
// $result = udpGet($data);
print_r($result);

?>