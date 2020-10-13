<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App_common {
	
	function isStrEqual($str1, $str2) {
		return $str1 === $str2;
	}
	
	function isStrEqualIgnoreCase($str1, $str2) {
		return strtolower($str1) === strtolower($str2);
	}
	
	function create_uuid($prefix = ""){
		$str = md5(uniqid(mt_rand(), true));
		$uuid  = substr($str,0,8) . '-';
		$uuid .= substr($str,8,4) . '-';
		$uuid .= substr($str,12,4) . '-';
		$uuid .= substr($str,16,4) . '-';
		$uuid .= substr($str,20,12);
		return $prefix . $uuid;
	}
	
	function getFormErrors($result_array, $name_array) {
		foreach ($name_array as $name) {
			$error = form_error($name);
			if ($error != "") {
				$result_array[$name] = $error;
			}
		}
		return $result_array;
	}
	
}