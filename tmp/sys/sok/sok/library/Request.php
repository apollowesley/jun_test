<?php
namespace sok;
/**
 *
 */
class Request {

	function __construct() {
		$post = json_decode(file_get_contents('php://input'), true);
		if (!empty($post)) {
			$_POST = array_merge($_POST, $post);
		}
	}
	static public function params() {
		return array_merge($_POST, $_GET);
	}
}
?>