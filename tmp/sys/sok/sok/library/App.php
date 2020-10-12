<?php
// +----------------------------------------------------------------------
// +----------------------------------------------------------------------

namespace sok;

/**
 *
 */
class App {

	public static $app = null;

	public function __construct() {
		self::config();
	}

	public function __set($name, $val) {
		$this->$name = $val;
	}
	public function __get($name) {
		switch ($name) {
		case 'rootPath':
			return $this->$name = self::getRootPath();
			# code...
			break;
		case 'configPath':
			return $this->$name = self::getConfigPath();
			# code...
			break;
		case 'runtimePath':
			return $this->$name = self::getRuntimePath();
			# code...
			break;
		case 'appPath':
			return $this->$name = self::getAppPath();
			# code...
			break;
		default:
			return null;
			# code...
			break;
		}
	}
	public static function my_dir($dir) {
		$files = array();
		if (@$handle = opendir($dir)) {

			//注意这里要加一个@，不然会有warning错误提示：）
			while (($file = readdir($handle)) !== false) {
				if ($file != ".." && $file != ".") {
					//不然就将文件的名字存入数组；
					$files[] = $file;

				}
			}
			closedir($handle);
			return $files;
		}
	}
// 加载配置目录
	public static function config($item = null) {
		$dir = self::getConfigPath();
		$app = [];
		foreach (self::my_dir($dir) as $key => $value) {
			if (strrchr($value, ".php") == ".php") {
				$name = str_replace(strrchr($value, "."), "", $value);
				if (isset($app['config'][$name]) && is_array($app['config'][$name])) {
					$app['config'][$name] = array_merge($app['config'][$name], include $dir . DIRECTORY_SEPARATOR . $value);
				} else {
					// echo $dir . DIRECTORY_SEPARATOR . $value;
					$app['config'][$name] = include $dir . DIRECTORY_SEPARATOR . $value;
				}
			}
		}
		self::$app = $app;
		if ($item && isset($app['config'][$item])) {
			return $app['config'][$item];
		}
		return $app;
	}
	/**
	 * 获取应用根目录
	 * @access public
	 * @return string
	 */
	public static function getRootPath() {
		return Loader::getRootPath();
	}
	public static function getConfigPath() {
		return Loader::getRootPath() . DIRECTORY_SEPARATOR . 'config';
	}
	/**
	 * 获取应用类库目录
	 * @access public
	 * @return string
	 */
	public static function getAppPath() {

		return Loader::getRootPath() . DIRECTORY_SEPARATOR . 'application';
	}

	/**
	 * 获取应用运行时目录
	 * @access public
	 * @return string
	 */
	public static function getRuntimePath() {
		return Loader::getRootPath() . DIRECTORY_SEPARATOR . 'runtime';
	}
}