<?php
error_reporting(E_ERROR);

include 'db.php';

$plat=$_REQUEST['p'];
$bTime=$_REQUEST['bt'];
$eTime=$_REQUEST['et'];

$dayly=$_REQUEST['day'];
$group=$_REQUEST['group'];


$sql = "SELECT sum(amount) as allAmount, platform FROM JPayTrade WHERE status = 2";
if($plat) {
	$sql .= ' AND platform = "' . $plat . '"';	
}
?>
<table border="1">
<tr>
	<th>startTime</th>
	<th>endTime</th>
	<th>platform</th>
	<th>allAmount</th>
</tr>
<?php
function showData($bT, $eT, $sql, $group) {
	$e_sql = $sql;
	if($bT > 0) {
		$e_sql .= ' AND createTime >= ' . $bT . '000';	
	}
	
	if($eT > 0) {
		$e_sql .= ' AND createTime < ' . $eT . '000';	
	}
	
	if($group) {
		$e_sql .= ' GROUP BY platform ORDER BY allAmount DESC LIMIT 20';
	}
	
	//echo $e_sql;
	$res=mysql_query($e_sql);
	while($row=mysql_fetch_array($res))
	{
?>
<tr>
	<td><?php echo $bT > 0 ? date('Y-m-d H:i:s', $bT) : '';?></td>
	<td><?php echo $eT > 0 ? date('Y-m-d H:i:s', $eT) : '';?></td>
	<td><?php echo $row['platform']?></td>
	<td><?php echo $row['allAmount']?></td>
</tr>
<?php
		
	}
}

open_connection('sd_master');

$bT = strtotime($bTime);
if($eTime) {
	$eT = strtotime($eTime);
	if($dayly) {
		while($bT < $eT) {
			$bTN = $bT + (24 * 3600);
			if($bTN > $eT) {
				$bTN = $eT;
			}
			
			showData($bT, $bTN, $sql, $group);
			$bT = $bTN;
		}
		
	} else {
		showData($bT, $eT, $sql, $group);
	}
	
} else {
	showData($bT, $bT + (24 * 3600), $sql, $group);
}

close_connection();
?>
</table>






