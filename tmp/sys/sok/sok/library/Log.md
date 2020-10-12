###Log类使用说明
查看配置信息
(new App)->config('app')['log'];
### config 配置文件
文件位置 config/log.php 
$config=[
// 总配置设定

	'default' => [
		'logPath' => '',
		// 日志根目录
		'logFile' => 'default.log', // 日志文件
		'format' => 'Y/m/d', // 日志自定义目录，使用日期时间定义

	],
	'user' => [
		'logPath' => '',
		'logFile' => 'user.log',
		'format' => 'Ym/d',

	],
	'order' => [
		'logPath' => '',
		'logFile' => 'order.log',
		'format' => 'Y/m/d',
	],

];

### 使用方法
Log::getLogger('user')->info('Test Add Info Log');
Log::getLogger()->warn('Test Add Info Log');

// 设置配置Log::setConfig($config); 
// 调用日志类，使用默认设定
$logger = Log::getLogger();
$logger->info('Test Add Info Log');
$logger->warn('Test Add Warn Log');
$logger->error('Test Add Error Log');
// 调用日志类，使用user设定