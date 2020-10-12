<?php
namespace sok;
class Loader {
	/* 路径映射 */
	public static $classMap = [];
	// 注册类别名
	public static $classAlias = [];
	public static $rootPath;
	/**
	 * 自动加载器
	 */
	public static function autoload($class) {
		// 是否注册类库别名
		if (isset(self::$classAlias[$class])) {
			$class = self::$classAlias[$class];
		}

		$file = self::findFile($class);

		if (file_exists($file)) {
			self::includeFile($file);
		}
	}
	/**
	 * 解析文件路径
	 */
	private static function findFile($class) {
		$vendor = substr($class, 0, strpos($class, '\\')); // 顶级命名空间

		if (isset(self::$classMap[$vendor])) {
			$vendorDir = self::$classMap[$vendor]; // 文件基目录
			$filePath = substr($class, strlen($vendor)) . '.php'; // 文件相对路径
			return strtr($vendorDir . $filePath, '\\', DIRECTORY_SEPARATOR); // 文件标准路径
		}
		return $class;
	}

	/**
	 * 引入文件
	 */
	private static function includeFile($file) {
		if (is_file($file)) {
			include $file;
		}
	}
	// 注册classmap
	public static function addClassMap($class, $map = '') {
		if (is_array($class)) {
			self::$classMap = array_merge(self::$classMap, $class);
		} else {
			self::$classMap[$class] = $map;
		}
	}

	// 注册类别名
	public static function addClassAlias($alias, $class = null) {
		if (is_array($alias)) {
			self::$classAlias = array_merge(self::$classAlias, $alias);
		} else {
			self::$classAlias[$alias] = $class;
		}
	}
	public static function getRootPath() {
		return self::$rootPath;
	}
}
?>