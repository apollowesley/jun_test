<?php
namespace sok;
class Base {
	function __set($name, $val) {
		$this->$name = $val;
	}
	function __construct() {
		// $this->server = $server;
		// $this->request = $request;
		// $this->response = $response;
	}
}
?>