<?php
error_reporting(E_ERROR);

include 'db.php';

$idfa=db_real_string($_REQUEST['idfa']);
$mac=db_real_string($_REQUEST['mac']);
$channel=db_real_string($_REQUEST['channel']);
$callback=db_real_string($_REQUEST['callback']);

if($idfa && $callback){
	open_connection();
	$time=time();
	$sql='INSERT i_active (id,activeTime,mac,iy,channel,callback) VALUES ("'.$idfa.'",'.$time.',"'.$mac.'",1,"'.$channel.'","'.$callback.'")';
	#echo $sql;
	if(mysql_query($sql)) {
		die('{"success":true,"message":"ok"}');
		
	}else {
		mysql_query('UPDATE i_active SET activeTime='.$time.', iy=iy+1, callback="'.$callback.'" WHERE id = "'.$idfa.'" AND iy > 0 AND iy < 1024');
	}
}

close_connection();
echo '{"success":false,"message":"false"}';