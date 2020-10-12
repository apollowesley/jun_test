<?php
$con=null;
function open_connection($db='iysk'){
	if($con) return;
	$con = mysql_connect('127.0.0.1','absir','absir');
	if(!$con) die('Could not connect: '.mysql_error());
	mysql_select_db($db,$con);
}

function close_connection(){
	if($con) {
		mysql_close($con);
		$con = null;
	}
}

function db_real_string(& $str) {
	return mysql_escape_string($str);
}