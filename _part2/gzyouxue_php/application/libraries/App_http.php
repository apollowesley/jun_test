<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class App_http {
	
	const REQUEST_METHOD = 'REQUEST_METHOD';
	const METHOD_GET = "get";
	const METHOD_POST = "post";
	
	public function isGet() {
		$CI = & get_instance();
		$CI->load->library('app_common');
		return $CI->app_common->isStrEqualIgnoreCase($CI->input->server(App_http::REQUEST_METHOD), App_http::METHOD_GET);
	}
	
	public function isPost() {
		$CI = & get_instance();
		$CI->load->library('app_common');
		return $CI->app_common->isStrEqualIgnoreCase($CI->input->server(App_http::REQUEST_METHOD), App_http::METHOD_POST);
	}
	
	/**
	 * 此方法能获取post的数据，并将其组合成database所需要的data参数。
	 * post过来的数据可以设定prefix和suffix，同时，针对input text, select, radio, checkbox, textarea，要指定其类型。
	 * 
	 * @param unknown $post_array
	 * @param unknown $prefix
	 * @param unknown $suffix
	 * @return multitype:NULL
	 */
	public function getPostData4DB($key_array, $prefix = "", $suffix = "") {
		$data = array();
		$CI = & get_instance();
		foreach ($key_array as $key) {
			$column = "";
			if (strpos($key, "select_") !== false) {
				$prefix = $prefix."select_";
			} else if (strpos($key, "radio_") !== false) {
				$prefix = $prefix."radio_";
			} else if (strpos($key, "check_") !== false) {
				$prefix = $prefix."check_";
			} else if (strpos($key, "text_") !== false) {
				$prefix = $prefix."text_";
			}
			$column = substr($key, strlen($prefix));
			$column = substr($column, 0, strlen($column) - strlen($suffix) + 1);
			$prefix = "";
			$suffix = "";
			$data[$column] = $CI->input->post($key);
		}
		return $data;
	}
	
	public function getPostArryData4DB($column_array, $prefix = NULL, $suffix = NULL) {
		$data = array();
		$CI = & get_instance();
		foreach ($column_array as $column) {
			$post_column = $column;
			if (isset($prefix) && $prefix != NULL) {
				$post_column = $prefix."_".$post_column;
			}
			if (isset($suffix) && $suffix != NULL) {
				$post_column = $post_column."_".$suffix;
			}
			$post_array_data = $CI->input->post($post_column);
			$str_data = "";
			foreach ($post_array_data as $post_data) {
				if ($str_data == "") {
					$str_data = $post_data;
				} else {
					$str_data += ",".$post_data;
				}
			}
			$data[$column] = $str_data;
		}
		return $data;
	}
	
}