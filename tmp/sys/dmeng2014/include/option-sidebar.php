<?php   
  
$pageinfo = array('full_name' => '侧边栏设置', 'optionname'=>'sidebar', 'child'=>true, 'filename' => basename(__FILE__));   
  
$options = array();   
  
$options[] = array(    
                "name"=>"说明文档",   
                "desc"=>"设置页面以及页面侧边栏的显示方式。<br/><br/>页面模板总共有三个，分别是<b>默认模板</b>（左内容、右边栏），<b>左边栏模板</b>（左边栏、右内容）和<b>三列模板</b>（左边栏，中内容，右边栏）。<br/><br/>页面边栏总共设置四个，默认情况下文章、分类目录、标签、默认页面模板和左边栏页面模板使用同一个边栏（一号），三列模板使用一号和二号边栏。<br/><br/>您可以根据自己的喜好来设置边栏，主题根据设置的不同显示不同号码的侧边栏。",   
                "type" => "title"); 

$options[] = array( "type" => "open","desc"=>"首页侧边栏");  
				
$options[] = array( 
			"name" => "是否显示边栏",   
            "desc" => "",   
            "id" => "_home_display",   
            "type" => "radio",
			"std"=> "yes-right", 
            "buttons" => array(   
                'yes-right'=>'显示在右侧',   
                'yes-left'=>'显示在左侧',
				'no'=>'不显示',   
                )   
            );
			
$options[] = array( 
			"name" => "显示几号边栏",   
            "desc" => "",   
            "id" => "_home_number",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( "type" => "close");
				
$options[] = array( "type" => "open","desc"=>"文章页侧边栏");  
				
$options[] = array( 
			"name" => "是否显示边栏",   
            "desc" => "",   
            "id" => "_post_display",   
            "type" => "radio",
			"std"=> "yes-right", 
            "buttons" => array(   
                'yes-right'=>'显示在右侧',   
                'yes-left'=>'显示在左侧',
				'no'=>'不显示',   
                )   
            );
			
$options[] = array( 
			"name" => "显示几号边栏",   
            "desc" => "",   
            "id" => "_post_number",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( "type" => "close");

$options[] = array( "type" => "open","desc"=>"分类页侧边栏");  
				
$options[] = array( 
			"name" => "是否显示边栏",   
            "desc" => "",   
            "id" => "_cat_display",   
            "type" => "radio",
			"std"=> "yes-right", 
            "buttons" => array(   
                'yes-right'=>'显示在右侧',   
                'yes-left'=>'显示在左侧',
				'no'=>'不显示',   
                )   
            );
			
$options[] = array( 
			"name" => "显示几号边栏",   
            "desc" => "",   
            "id" => "_cat_number",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( "type" => "close");

$options[] = array( "type" => "open","desc"=>"标签页侧边栏");  
				
$options[] = array( 
			"name" => "是否显示边栏",   
            "desc" => "",   
            "id" => "_tag_display",   
            "type" => "radio",
			"std"=> "yes-right", 
            "buttons" => array(   
                'yes-right'=>'显示在右侧',   
                'yes-left'=>'显示在左侧',
				'no'=>'不显示',   
                )   
            );
			
$options[] = array( 
			"name" => "显示几号边栏",   
            "desc" => "",   
            "id" => "_tag_number",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( "type" => "close"); 

$options[] = array( "type" => "open","desc"=>"搜索页侧边栏");  
				
$options[] = array( 
			"name" => "是否显示边栏",   
            "desc" => "",   
            "id" => "_search_display",   
            "type" => "radio",
			"std"=> "yes-right", 
            "buttons" => array(   
                'yes-right'=>'显示在右侧',   
                'yes-left'=>'显示在左侧',
				'no'=>'不显示',   
                )   
            );
			
$options[] = array( 
			"name" => "显示几号边栏",   
            "desc" => "",   
            "id" => "_search_number",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( "type" => "close"); 

$options[] = array( "type" => "open","desc"=>"归档页侧边栏");  
				
$options[] = array( 
			"name" => "是否显示边栏",   
            "desc" => "",   
            "id" => "_archive_display",   
            "type" => "radio",
			"std"=> "yes-right", 
            "buttons" => array(   
                'yes-right'=>'显示在右侧',   
                'yes-left'=>'显示在左侧',
				'no'=>'不显示',   
                )   
            );
			
$options[] = array( 
			"name" => "显示几号边栏",   
            "desc" => "",   
            "id" => "_archive_number",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( "type" => "close");

$options[] = array( "type" => "open","desc"=>"附件页侧边栏");  
				
$options[] = array( 
			"name" => "是否显示边栏",   
            "desc" => "",   
            "id" => "_attachment_display",   
            "type" => "radio",
			"std"=> "yes-right", 
            "buttons" => array(   
                'yes-right'=>'显示在右侧',   
                'yes-left'=>'显示在左侧',
				'no'=>'不显示',   
                )   
            );
			
$options[] = array( 
			"name" => "显示几号边栏",   
            "desc" => "",   
            "id" => "_attachment_number",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( "type" => "close");
				
$options[] = array( "type" => "open","desc"=>"页面侧边栏");  
				
$options[] = array( 
			"name" => "默认模板（边栏在右侧）",   
            "desc" => "",   
            "id" => "_page_default",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( 
			"name" => "左边栏模板（边栏在左侧）",   
            "desc" => "",   
            "id" => "_page_reverse",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( 
			"name" => "三列模板（左侧边栏）",   
            "desc" => "",   
            "id" => "_page_three_rows_left",   
            "type" => "radio",
			"std"=> "one", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
			
$options[] = array( 
			"name" => "三列模板（右侧边栏）",   
            "desc" => "",   
            "id" => "_page_three_rows_right",   
            "type" => "radio",
			"std"=> "two", 
            "buttons" => array(   
                'one'=>'使用一号边栏',   
                'two'=>'使用二号边栏',
				'three'=>'使用三号边栏',   
                'four'=>'使用四号边栏', 
                )   
            );
               
$options[] = array( "type" => "close");   
  
$options_page = new dmeng_option_class($options, $pageinfo);   
?>