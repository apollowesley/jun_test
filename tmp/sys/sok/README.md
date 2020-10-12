

#websocket消息推送框架
```
作者：yichen
email：2782268022@qq.com
移动应用开发交流qq群714566447
```
#介绍
>swoole搭建的一个websocket聊天消息推送服务端，支持分布式。1核1g虚拟机。实测发送1万条数据完成时间1.1秒。 框架依赖 1、swoole 2、php 3、redis 4、mysql

#服务
1. websocket 消息服务(用户接受消息，用户之间通讯)
2. http 代理服务（负责接受用户或管理员指令，推送消息到websocket服务，再由websocket广播到用户端）
~~~
serve\sh\websocket #执行sh脚本，启动websocket服务 |start|restart|stop
~~~

然后就可以在浏览器中访问

~~~
http://localhost:9501
~~~
## 业务流程
一、用户建立连接
1.用户需要有登录账号，或在其他http服务器注册账号。
2.用户在其他服务器登录，并且拿到token值
3.用户创建websocket连接携带token值
4.服务器根据token值匹配redis缓存的登录状态，失败会断开连接，成功会把用户id记录在主线程。
5.若开启了分布式服务器，服务器同时会把用户所连接的服务器ip信息存到redis。
6.用户登录成功，就可以接收消息了。
7.分布式服务器
###推送消息 
说明:单服务器和多服务器指的是websocket服务，1核的单服务器维持几万连接同时在线是没问题的。发消息服务器可以分布式布置。例如：你原来应用是才用thinkphp框架，那么你就在你的thinkphp框架内，写几行代码，创建udp客户端，把消息推送到websocket服务器的udp端口即可。
下面说下两种推送消息方式。
方式一 单服务器连接模式(利用web前端js的websocket接口推送对话消息)
1.非分布式服务器，登录后的客户端可以直接在web页面利用js的websocket接口收发消息。也可以用udp方式发消息。

方式二 用http服务器或其他服务器推送消息到udp端口，websocket服务监听了udp端口
推荐用此方法,支持分布式。

验证方式，在websocket配置文件中分布式配置节点udpsocket_group下配置ip+secret_key+udp端口。服务器会根据连接ip和秘钥来鉴权
1.其他任意框架语言的服务器都可以利用udp协议直接推送到websocket服务器。websocket监听了udp端口。会根据数据进行相应转发。
2.支持本地多机充当接收用户消息处理然后用udp协议推送即可。
3.可以群发消息。
4.更多功能可在udp接口处自定义添加。

### 架构流程
每台单核websocket至少能维持5万个用户在线。收发消息要注意mysql数据库。我测试每秒写入大概2500条左右,如果速度不够，需要在config配置关闭数据备份。
如果有图片音频等资源要处理，我觉得应该通过http处理，然后通过udo推到websocket
普通聊天消息可以用h5的websocket接口直推
如果使用h5的websocket直推消息,一定要注意防御，socket接口收发消息非常快。每秒几万的连接
```
服务器流程
客户端←——→						←——(udp) http
客户端←——→		[websocket]    	←——(udp) http
客户端←——→						←——(udp) http
客户端←——→		   	↑↓(udp)		←——(udp) http
					
客户端←——→						←——(udp) http
客户端←——→		[websocket]     ←——(udp) http
客户端←——→			 			←——(udp) http
客户端←——→		   	↑↓(udp)		←——(udp) http
					
客户端←——→						←——(udp) http
客户端←——→		[websocket]    	←——(udp) http
客户端←——→						←——(udp) http
客户端←——→		   	↑↓(udp)		←——(udp) http
```
### websocket h5消息直推流程
```
框架采用单进程接收，多进程处理数据模式。这样主进程可以写全局变量。根据需要随时切换到主进程获取,修改。
server绑定了onMessage接收消息,消息内容为json字符串，接收后转数组。其中
1.worker主线程单进程接收,从全局变量获取到用户对应信息,剩下业务交个task进程(task_worker=2*cpu核数，多进程处理),继续接收消息。
2.
```
### udp发送消息简单示例。
```
function udpGet($sendMsg = '', $ip = '192.168.0.150', $port = '9502',$secret_key='123456') {
	$handle = stream_socket_client("udp://{$ip}:{$port}", $errno, $errstr);
	if (!$handle) {
		die("ERROR: {$errno} - {$errstr}\n");
	}
	fwrite($handle, $sendMsg . "\n");
	$result = fread($handle, 1024);
	fclose($handle);
	return $result;
}
$_GET['sk']=$secret_key;
$data = json_encode($_GET, JSON_UNESCAPED_UNICODE);
$result = udpGet($data);
print_r($result);
```
### 开启http服务
```
 serve/sh/http #执行命令启动http服务

 http://www.xxx.cn:8880/chat/send?to_uid=123456&content=fdsfdsf #浏览器输入，利用http服务向websocket推送消息，请参考\application\chat\controller\chat->send()
```

## 目录结构

初始的目录结构如下：

```
www  WEB部署目录（或者子目录）
├─application           应用目录 （只针对http服务、websocket服务代码请写到serve/websocket下）
│  ├─common             公共模块目录（可以更改）
│  ├─module_name        模块目录
│  │  ├─controller      控制器目录
│  │  ├─model           模型目录
│  │  └─ ...            更多类库目录
│  ├─common.php         公共函数文件
│  └─
│
├─config                应用配置目录        
│  │
│  ├─app.php            		应用配置
│  ├─http.php          	 		http默认服务配置参考
│  ├─route.php          	 	路由配置
│  ├─websocket.php      		websocket默认服务配置参考
│  ├─database.php      	 		数据库配置
│  ├─redis.php       			redis配置
│  ├─log.php            		日志配置
│  ├─http_message.php   		http_message服务配置
│  ├─websocket_message.php   	websocket_message服务配置
│  └─
├─serve                服务目录        
│  │
│  ├─run       			启动服务执行文件目录，文件名对应相应服务模块名，不含后缀
│  │              
│  ├─socket           		socket服务目录
│  ├─websocket   	websocket服务目录
│  │  ├─common           公共类
│  │  ├─managerstart    server  onManagerStart触发执行类目录
│  │  ├─message         server  onMessage 触发执行类 目录
│  │  ├─open     		server onOpen触发执行类目录 
│  │  ├─task           	server onTask触发执行目录
│  │  ├─Message.php     server启动服务模块名 server->on()对应各目录的同名文件，例如onMessage 对应open/Message.php
│  │  └─
│  └─
│
├─route                 		路由定义目录
│  ├─http_message.php          http_message服务路由定义
│  └─...                		更多
│
├─public                WEB目录（对外访问目录）
│  ├─index.php          入口文件
│  └─
│
├─sok              		框架系统目录
│  ├─library            框架类库目录
│  │  ├─cache           缓存相关
│  │  ├─utils           工具类
│  │  └─traits          Trait类目录 可以use引入到类内部
│  │
│  ├─base.php           基础定义文件
│  └─Loader.php         框架加载文件
│
├─ssl                ssl证书目录
├─composer.json         composer 定义文件
├─README.md             README 文件
├─sok                 命令行入口文件
```

> 可以使用php自带webserver快速测试
> 切换到根目录后，启动命令：php think run


##登录token值规范

建立连接会执行onOpen方法

1.普通用户验证规则如下 
```
$userToken=[
//token授权终端源 app|pc|weixin 
	"app" => [
	 		'uid' => 123456,
	  		'token' => '123456',
	 		'expires' => 15666063466,
	  	];
];

$userToken = $this->redis()->get('login' . $uid); //token存放规则
//验证token通过后向下执行

//用户id对应连接信息
				$ClientInfo = [
					'time' => time(),//建立时间
					'fd' => $request->fd,//连接fd值
					"port" => $distributed['local']['port'],//连接所在服务器ip
					'ip' => $distributed['local']['ip'],//连接所在服务器端口号
				];
//fd对应的连接信息
				$fdInfo = [
					'uid' => $uid,//用户唯一id
					'isServer' => false,//是否联推送服务器
					"source" => $data['source'],//终端来源 app|pc|app
					"time" => time(),//建立时间
				];
				//uid缓存到redis， key=本机ip+端口， 字段key=fd
				$this->redis()->hSet("ws_fdInfo:" . $distributed['local']['ip'] . $distributed['local']['port'], $request->fd, $fdInfo);

				$oldClient = $this->redis()->hGet("ws_clientInfo", $uid) ?? [];
				$oldClient[$data['source']] = $ClientInfo;

				$this->redis()->hSet("ws_clientInfo", $uid, $oldClient);
```
2.分布服务器的uid值命名

存储规则同用户相同，只是多了个标识isServer=true;
通过ip值+secret_key秘钥 来确定是分布服务器建立的连接。
服务器的uid是这样命名的。
```
$uid = intval(ip2long($distributed['local']['ip']) . $distributed['local']['port'] . ip2long($ip));

$fdInfo['isServer']=true; //标识是服务器，否则是普通用户
```
##用户端创建websocket连接
用户创建连接需要三个参数，token.uid.source
例如 base64_encode(fe24NDU2341Nierer632rfd.2109323.app)
转码后需要替换字符'+/='换成'-_,';#防止浏览器url地址栏解析这些字符出问题
实例
```
ws://192.168.0.150:9501?access_token= MTIzNDU2LjEyMzQ1Ni5hcHA
服务端接收到参数先解密
服务器收到先替换特殊字符'-_,'换成'+/=';然后进行base64解码
$token=base64_encode($get['access_token']);
解密后数据样式
	$token = [
	'info'=>[
		'uid'=>'123456',
		'xx'=>'xx'
	],
	'app'=>[
		'uid' => 123456,
		'token' => '123456',
		'time'=>1234567897,
		],
	'weixin'=>[
		'uid' => 123456,
		'token' => '123456',
		'time'=>1234567897,
		]
	];
```

##用户发送消息模板
to_uid 接收消息的用户id,发送人id由服务器从后台获取。
```
{"to_uid":"123456","type":"chat","content":{"type":"text","value":"新内容"}}
```
##分布服务器udp连接到其他服务器udp协议推消息示例
创建连接需要1个参数，secret_key秘钥 简写sk
为了保障安全，只有sk是无法验证通过的，服务端需要配置允许组网服务器ip。验证规则会自动根据请求ip+sk,组合验证。
```
udp://192.168.0.150:9502?sk=1234567898

```
####udp请求接口规范

```
<?php


//______________________________请求函数

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
	$result = udpGet($data);//返回值
print_r($result);
```

