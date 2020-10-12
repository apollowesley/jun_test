<?php   

$slide_number = $dmeng_option['theme']['_slide_number'];

$pageinfo = array('full_name' => '幻灯片设置', 'optionname'=>'slide', 'child'=>true, 'filename' => basename(__FILE__));   
  
$options = array();   
  
$options[] = array(    
                "name"=>"说明文档",   
                "desc"=> "幻灯片数量根据<a href='./admin.php?page=option.php'>主题设置</a>的数量变化，当前幻灯片数量为 ".$slide_number." 个。",   
                "type" => "title"); 

$options[] = array( "type" => "open","desc"=>"幻灯片 1 设置");  
			
$options[] = array(   
            "name" => "图片",   
            "desc" => "必须要有！请上传一个图片或填写一个图片地址",   
            "std"=>"http://opencdn.oss.aliyuncs.com/dmeng-theme-beta.png",   
            "id" => "_slide_a1_img",   
            "type" => "upload");
			
$options[] = array(   
                "name"=>"链接",   
                "id"=>"_slide_a1_url",   
                "std"=>"",   
                "desc"=>"可选",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"标题",   
                "id"=>"_slide_a1_title",   
                "std"=>"",   
                "desc"=>"可选",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"描述",   
                "id"=>"_slide_a1_desc",   
                "std"=>"",   
                "desc"=>"可选",   
                "size"=>"60",   
                "type"=>"textarea"  
            );  
			
$options[] = array( "type" => "close");  
               
$num = $slide_number-1;
for ($i = 1 , $inum = $i+1 ; $i <= $num; $i++ ,$inum++)
{
$options[] = array( "type" => "open","desc"=>"幻灯片 ".$inum." 设置");
			
$options[] = array(   
            "name" => "图片",   
            "desc" => "必须要有！请上传一个图片或填写一个图片地址",   
            "std"=>"",   
            "id" => "_slide_a".$inum."_img",   
            "type" => "upload"); 
			
$options[] = array(   
                "name"=>"链接",   
                "id"=>"_slide_a".$inum."_url",   
                "std"=>"",   
                "desc"=>"可选",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"标题",   
                "id"=>"_slide_a".$inum."_title",   
                "std"=>"",   
                "desc"=>"可选",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"描述",   
                "id"=>"_slide_a".$inum."_desc",   
                "std"=>"",   
                "desc"=>"可选",   
                "size"=>"60",   
                "type"=>"textarea"  
            ); 
			
$options[] = array( "type" => "close");  

}		    
  
$options_page = new dmeng_option_class($options, $pageinfo);   
?>