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

namespace sok\cache;

/**
 *
 */
class File {
	private $root_dir;

	public function __construct($root_dir) {
		$this->root_dir = $root_dir;

		if (FALSE == file_exists($this->root_dir)) {
			mkdir($this->root_dir, 0700, true);
		}
	}

	public function set($key, $value) {
		$key = $this->escape_key($key);

		$file_name = $this->root_dir . DIRECTORY_SEPARATOR . $key;

		$dir = dirname($file_name);

		if (FALSE == file_exists($dir)) {
			mkdir($dir, 0700, true);
		}

		file_put_contents($file_name, serialize($value), LOCK_EX);
	}

	public function get($key) {
		$key = $this->escape_key($key);

		$file_name = $this->root_dir . DIRECTORY_SEPARATOR . $key;

		if (file_exists($file_name)) {
			return unserialize(file_get_contents($file_name));
		}

		return null;
	}

	public function remove($key) {
		$key = $this->escape_key($key);

		$file = $this->root_dir . DIRECTORY_SEPARATOR . $key;

		if (file_exists($file)) {
			unlink($file);
		}
	}

	public function remove_by_search($key) {
		$key = $this->escape_key($key);

		$dir = $this->root_dir . DIRECTORY_SEPARATOR . $key;

		if (strrpos($key, DIRECTORY_SEPARATOR) < 0) {
			$key .= DIRECTORY_SEPARATOR;
		}

		if (file_exists($dir)) {
			$this->removeDir($dir);
		}
	}

	private function escape_key($key) {
		return str_replace('..', '', $key);
	}

	function removeDir($dirName) {
		$result = false;

		$handle = opendir($dirName);

		while (($file = readdir($handle)) !== false) {
			if ($file != '.' && $file != '..') {
				$dir = $dirName . DIRECTORY_SEPARATOR . $file;

				is_dir($dir) ? $this->removeDir($dir) : unlink($dir);
			}
		}

		closedir($handle);

		rmdir($dirName) ? true : false;

		return $result;
	}
}
?>