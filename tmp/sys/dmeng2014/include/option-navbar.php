<?php   
  
$pageinfo = array('full_name' => '导航条设置', 'optionname'=>'navbar', 'child'=>true, 'filename' => basename(__FILE__));   
  
$options = array();   
  
$options[] = array(    
                "name"=>"说明文档",   
                "desc"=>"设置导航条的显示方式",   
                "type" => "title"); 

$options[] = array( "type" => "open","desc"=>"导航条");  
				
$options[] = array( 
			"name" => "配色方案",   
            "desc" => "",   
            "id" => "_navbar_color",   
            "type" => "radio",
			"std"=> "navbar-default", 
            "buttons" => array(   
                'navbar-default'=>'默认（白底黑字）',   
                'navbar-inverse'=>'反向（黑底白字）',   
                )   
            );				
               
$options[] = array( 
			"name" => "显示方式",   
            "desc" => "",   
            "id" => "_navbar_position",   
            "type" => "radio",
			"std"=> "navbar-static-top", 
            "buttons" => array(   
                'navbar-static-top'=>'静止在顶部',   
                'navbar-fixed-top'=>'固定在顶部',   
                'navbar-fixed-bottom'=>'固定在底部'   
                )   
            );

$options[] = array( 
			"name" => "显示搜索框",   
            "desc" => "",   
            "id" => "_search_display",   
            "type" => "radio",
			"std"=> "search-display", 
            "buttons" => array(   
                'search-display'=>'显示',   
                'search-hidden'=>'不显示',   
                )   
            );
			
$options[] = array( 
			"name" => "搜索框位置",   
            "desc" => "选择显示搜索框时有效",   
            "id" => "_search_position",   
            "type" => "radio",
			"std"=> "search-left", 
            "buttons" => array(   
                'search-left'=>'显示在左侧',   
                'search-right'=>'显示在右侧', 
                )   
            );			
               
$options[] = array( "type" => "close");   
  
$options_page = new dmeng_option_class($options, $pageinfo);   
?>