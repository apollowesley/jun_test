<?php   
  
$pageinfo = array('full_name' => '广告位设置', 'optionname'=>'advert', 'child'=>true, 'filename' => basename(__FILE__));   
  
$options = array();  
  
$options[] = array(    
                "name"=>"说明文档",   
                "desc"=>"设置主题的广告位，留空则不显示。推荐使用自适应广告！响应式广告根据浏览器窗口大小改变大小，最大宽度是698PX。<br>为提高用户体验，保证网站品质，首页不设广告位（侧边栏除外），手机端访问不显示广告。页面广告只在默认页面模板显示，其他页面模板没有广告位。",   
                "type" => "title"); 

$options[] = array( "type" => "open","desc"=>"归档页广告（包括分类、标签、搜索、月份等归档页面）");  

$options[] = array(   
                "name"=>"归档列表顶部",   
                "id"=>"_archive_ad",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"textarea"  
            );

$options[] = array(   
                "name"=>"归档列表底部",   
                "id"=>"_archive_ad2",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"textarea"  
            );			
			   
$options[] = array( "type" => "close"); 

$options[] = array( "type" => "open","desc"=>"文章页广告");  

$options[] = array(   
                "name"=>"文章内容顶部",   
                "id"=>"_single_ad",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"textarea"  
            );

$options[] = array(   
                "name"=>"文章内容底部",   
                "id"=>"_single_ad2",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"textarea"  
            );			
			   
$options[] = array( "type" => "close");

$options[] = array( "type" => "open","desc"=>"页面广告");  

$options[] = array(   
                "name"=>"页面内容顶部",   
                "id"=>"_page_ad",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"textarea"  
            );

$options[] = array(   
                "name"=>"页面内容底部",   
                "id"=>"_page_ad2",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"textarea"  
            );			
			   
$options[] = array( "type" => "close");

$options_page = new dmeng_option_class($options, $pageinfo);   
?>