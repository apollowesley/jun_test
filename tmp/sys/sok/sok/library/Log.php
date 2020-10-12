<?php
namespace sok;
/**

 * php日志类
 * Date:    2019-05-12
 * Author:  yichen
 * Version: 1.0
 *
 * Description:

 * 1.自定义日志根目录及日志文件名称。
 * 2.使用日期时间格式自定义日志目录。
 * 3.自动创建不存在的日志目录。
 * 4.记录不同分类的日志，例如信息日志，警告日志，错误日志。
 * 5.可自定义日志配置，日志根据标签调用不同的日志配置。

 *
 * Func

 * public  static setConfig 设置配置
 * public  static getLogger 获取日志类对象
 * public  info              写入信息日志
 * public  warn              写入警告日志
 * public  error             写入错误日志
 * private add               写入日志

 * private createLogPath   创建日志目录
 * private getLogFile      获取日志文件名称
 */

class Log {
	// 日志根目录

	private $logPath = '.'; // 日志文件

	private $logFile = 'default.log'; // 日志自定义目录

	private $format = 'Y/m/d'; // 日志标签

	private $tag = 'default'; // 总配置设定

	private static $config; /**

	 * 设置配置

	 * @param  Array $config 总配置设定

	 */
	public function __get($name) {
		return self::$$name;
	}
	public function __set($name, $val) {
		$this->name = $val;
	}
	public static function setConfig($config = []) {

		if (!empty($config) && is_array($config)) {
			$config = array_merge(App::config('log'), $config);
		} else {
			$config = App::config('log');
		}
		// $logPath = $this->path;
		// // $this->logPath = $logPath;
		// foreach ($config as $key => $value) {
		// 	if ($key == 'logPath') {
		// 		$config[$key] = $logPath . $value;
		// 	}
		// }
		self::$config = $config;
	}
	/**

	 * 获取日志类对象

	 * @param  Array $config 总配置设定

	 * @return Obj

	 */

	public static function getLogger($tag = 'default') {
		self::setConfig();
		// 根据tag从总配置中获取对应设定，如不存在使用default设定
		$config = isset(self::$config[$tag]) ? self::$config[$tag] : (isset(self::$config['default']) ? self::$config['default'] : []); // 设置标签
		$config['tag'] = $tag != '' && $tag != 'default' ? $tag : '-'; // 返回日志类对象
		return new Log($config);

	}/**

	 * 初始化

	 * @param Array $config 配置设定

	 */

	public function __construct($config = []) {
		$this->logPath = App::getRootPath() . DIRECTORY_SEPARATOR . 'runtime' . DIRECTORY_SEPARATOR . 'log' . DIRECTORY_SEPARATOR;

		if (isset($config['logPath'])) {

			$this->logPath = $this->logPath . $config['logPath'];

		} // 日志文件

		if (isset($config['logFile'])) {

			$this->logFile = $config['logFile'];

		} // 日志自定义目录

		if (isset($config['format'])) {

			$this->format = $config['format'];

		} // 日志标签

		if (isset($config['tag'])) {

			$this->tag = $config['tag'];

		}
	}
	/**

	 * 写入信息日志

	 * @param  String $data 信息数据

	 * @return Boolean

	 */

	public function info($data) {
		return $this->add('INFO', $data);

	}/**

	 * 写入警告日志

	 * @param  String  $data 警告数据

	 * @return Boolean

	 */

	public function warn($data) {
		return $this->add('WARN', $data);

	}/**

	 * 写入错误日志

	 * @param  String  $data 错误数据

	 * @return Boolean

	 */

	public function error($data) {
		return $this->add('ERROR', $data);

	}/**

	 * 写入日志

	 * @param  String  $type 日志类型

	 * @param  String  $data 日志数据

	 * @return Boolean

	 */

	private function add($type, $data) {
		// 获取日志文件

		$logFile = $this->getLogFile(); // 创建日志目录

		$is_create = $this->createLogPath(dirname($logFile)); // 创建日期时间对象

		$dt = new \DateTime; // 日志内容

		$log_data = sprintf('[%s] %-5s %s %s' . PHP_EOL, $dt->format('Y-m-d H:i:s'), $type, $this->tag, $data); // 写入日志文件

		if ($is_create) {
			return file_put_contents($logFile, $log_data, FILE_APPEND);

		}return false;

	}/**

	 * 创建日志目录

	 * @param  String  $logPath 日志目录

	 * @return Boolean

	 */

	private function createLogPath($logPath) {
		if (!is_dir($logPath)) {
			return mkdir($logPath, 0777, true);

		}return true;

	}/**

	 * 获取日志文件名称

	 * @return String

	 */

	private function getLogFile() {
		// 创建日期时间对象

		$dt = new \DateTime; // 计算日志目录格式

		return sprintf("%s/%s/%s", $this->logPath, $dt->format($this->format), $this->logFile);

	}

}

?>