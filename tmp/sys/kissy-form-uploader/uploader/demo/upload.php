<?php

sleep(1.5);

$files = array();

$url = 'http://'.$_SERVER['SERVER_NAME'].':'.$_SERVER['SERVER_PORT'].rtrim(dirname($_SERVER['PHP_SELF']), '/\\')."/";

// echo $url;
// print_r($_SERVER);

// print_r ($_FILES);
function uploadFile($file_label){
	// global $url;
	$this_file = $_FILES[$file_label];

}
// 这里设置input的name，如果不需要多选上传的话只要设置这一个就够了。
$fileInput = 'Filedata';
$dir = $_POST['dir'];
$files_name_arr = array($fileInput);
foreach($files_name_arr as $k=>$v){
	$pic = $_FILES[$v];
	if($pic['error']==0){
		if(file_exists($dir.$pic['name'])){
			@unlink($dir.$pic['name']);
		}
		move_uploaded_file($pic['tmp_name'], $dir.$pic['name']);
		$files[$k] = $url.$dir.$pic['name'];
	}else{
		$files[$k] = '';
	}
}

$arr = array(
	'success' => 1,
    'result' => array('name' => $_FILES[$fileInput]['name'],
                      'url' => $dir.$_FILES[$fileInput]['name'])
);

echo json_encode($arr);

?>