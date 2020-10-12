<?php
// +----------------------------------------------------------------------
namespace sok\utils;

//----------------------------------

class Arr {
	public function __construct() {
	}
	// 字符串转数组/对象/返回数据或[]
	public function json_decode($data, $isArr = true) {
		if (!empty($data)) {

			if (is_array($data)) {
				return $data;
			} else {
				@$data = json_decode($data, $isArr);

				return $this->json_decode($data, $isArr);
			}
		}
		return null;
	}
	public function json_encode($data) {
		return json_encode($data, JSON_UNESCAPED_UNICODE);
	}
}