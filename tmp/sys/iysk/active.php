<?php
error_reporting(E_ERROR);

include 'db.php';

$idfa = db_real_string($_GET['id']);
if($idfa) {
	$key=md5($idfa.'#%$%^FA454');
	//echo $key;
	if($_GET['key'] != $key) {
		//echo "key=".$key;
		die('key is error');
	}
	
	$device = db_real_string($_REQUEST['device']);
	$os = db_real_string($_REQUEST['os']);
	open_connection();
	$time=time();
	$res=mysql_query('SELECT callback FROM i_active WHERE id = "'.$idfa.'" AND iy>0 AND iy<16 AND activeTime>'.($time-72000));
	$row=mysql_fetch_row($res);
	if($row) {
		$callback=$row['callback'];
		if($callback) {
			file_get_contents($callback);
		}

		mysql_query('UPDATE i_active SET iy=0 WHERE id = "'.$idfa.'", device="'.$device.'", os="'.$os.'"');
	
	} else {
		mysql_query('INSERT i_active (id,activeTime,iy,device,os) VALUES ("'.$idfa.'",'.$time.',0,"'.$device.'","'.$os.'")');
	}
	
	close_connection();
	die('ok');
}
echo 'fail';