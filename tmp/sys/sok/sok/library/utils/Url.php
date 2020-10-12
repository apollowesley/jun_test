<?php
// +----------------------------------------------------------------------
namespace sok\utils;

//----------------------------------

class Url {

	// 替换特殊字符编码
	public static function base64url_encode($plainText) {

		$base64 = base64_encode($plainText);
		$base64url = strtr($base64, '+/=', '-_,');
		return $base64url;
	}
	// 替换特殊字符解码
	public static function base64url_decode($plainText) {

		$base64url = strtr($plainText, '-_,', '+/=');
		$base64 = base64_decode($base64url);
		return $base64;
	}
}