<?php
namespace app\index\controller;
// use sok\Request;

class Base {
	function __set($name, $val) {
		$this->$name = $val;
	}
	// function __construct($server, $request = 'request', $response = null) {
	// 	$this->server = $server;
	// 	$this->request = $request;
	// 	$this->response = $response;
	// 	print_r($request);
	// }
}
?>