<?php
error_reporting(E_ERROR);

include 'db.php';

$idfas=file_get_contents('php://input');
if(!$idfas){
	$idfas = $_GET['ids'];
	if(!$idfas) die('{}');
}

$idfaAry = explode(',',$idfas);
#for($i = 0; $i < 1000;$i++) {
#	$idfaAry[$i] = 'A58EF1E-EEF2-478D-94EE-709B98407589'.$i;
#}

$idfaMap = array();
foreach($idfaAry as $idfa)
{
	$idfaMap[$idfa]='0';
}

open_connection();
//OR iy > 8
$sql='SELECT id from i_active WHERE id IN ("'.implode('","',$idfaAry).'") AND (iy < 1)';
//echo $sql;
$res=mysql_query($sql);
$sql=null;
while($row=mysql_fetch_array($res))
{
	$idfaMap[$row['id']]='1';
}

close_connection();
echo json_encode($idfaMap);