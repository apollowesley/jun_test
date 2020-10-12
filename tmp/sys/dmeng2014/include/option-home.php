<?php   
  
$pageinfo = array('full_name' => '网站首页设置', 'optionname'=>'theme', 'child'=>true, 'filename' => basename(__FILE__));   
  
$options = array();   
  
$options[] = array(    
                "name"=>"说明文档",   
                "desc"=>"设置主题首页的显示方式",   
                "type" => "title"); 

$options[] = array( "type" => "open","desc"=>"首页幻灯片设置");  
				
$options[] = array( 
			"name" => "是否显示幻灯片",   
            "desc" => "",   
            "id" => "_slide_display",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );
			
$options[] = array(   
                "name"=>"幻灯片显示数量",   
                "id"=>"_slide_number",   
                "std"=>"1",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );

$options[] = array( 
			"name" => "是否显示遮罩层",   
            "desc" => "在幻灯片左右的上一张下一张的半透明遮罩层",   
            "id" => "_slide_nav_display",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'显示',   
                'just-arrow'=>'只显示箭头',
				'no'=>'不显示',
                )   
            );			
               
$options[] = array( "type" => "close"); 

$options[] = array( "type" => "open","desc"=>"首页头条推荐");  
				
$options[] = array(   
            "name" => "头条图片",   
            "desc" => "请上传一个图片或填写一个图片地址",   
            "std"=>"",   
            "id" => "_headline_img",   
            "type" => "upload");
			
$options[] = array(   
                "name"=>"头条链接",   
                "id"=>"_headline_url",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );	

$options[] = array(   
                "name"=>"头条标题",   
                "id"=>"_headline_title",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );	

$options[] = array(   
                "name"=>"头条简介",   
                "id"=>"_headline_desc",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"textarea"  
            ); 			
               
$options[] = array( "type" => "close");

$options[] = array( "type" => "open","desc"=>"首页置顶文章");  
				
$options[] = array( 
			"name" => "是否显示置顶文章",   
            "desc" => "",   
            "id" => "_sticky_display",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );
			
$options[] = array( 
			"name" => "置顶文章显示方式",   
            "desc" => "",   
            "id" => "_sticky_arrange",   
            "type" => "radio",
			"std"=> "auto", 
            "buttons" => array(
				'auto'=>'自动变换（推荐）',
                'col-lg-12'=>'一列|两列|两列|一列',   
                'col-lg-6'=>'两列|两列|两列|一列',
				'col-lg-4'=>'三列|两列|两列|一列',
                )   
            );
			
$options[] = array(   
                "name"=>"置顶文章显示数量",   
                "id"=>"_sticky_number",   
                "std"=>"3",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );			
               
$options[] = array( "type" => "close"); 

$options[] = array( "type" => "open","desc"=>"首页分类列表");  

$options[] = array( 
			"name" => "是否显示分类列表",   
            "desc" => "",   
            "id" => "_display_cat",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );
			
$options[] = array( 
			"name" => "是否显示文章浏览量",   
            "desc" => "",   
            "id" => "_display_post_views",   
            "type" => "radio",
			"std"=> "no", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );
			
$options[] = array( 
			"name" => "分类列表显示方式",   
            "desc" => "",   
            "id" => "_arrange_cat",   
            "type" => "radio",
			"std"=> "col-lg-6", 
            "buttons" => array(   
                'col-lg-12'=>'一列|两列|两列|一列',   
                'col-lg-6'=>'两列|两列|两列|一列',
				'col-lg-4'=>'三列|两列|两列|一列',
                )   
            );
	
$options[] = array(   
                "name"=>"要排除的分类ID",   
                "id"=>"_exclude_cat",   
                "std"=>"",   
                "desc"=>"多个分类请用英文逗号隔开，如：1,2,3",   
                "size"=>"60",   
                "type"=>"text"  
            );
               
$options[] = array( "type" => "close");

$options[] = array( "type" => "open","desc"=>"首页最新文章");  

$options[] = array( 
			"name" => "是否显示最新文章",   
            "desc" => "",   
            "id" => "_display_latest_post",   
            "type" => "radio",
			"std"=> "no", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );
			
$options[] = array( 
			"name" => "是否忽略置顶文章",   
            "desc" => "选择否将置顶显示置顶文章",   
            "id" => "_ignore_sticky_posts",   
            "type" => "radio",
			"std"=> "1",
            "buttons" => array(   
                '1'=>'是',
                '0'=>'否',
                )
            );
			
$options[] = array( 
			"name" => "是否显示发布时间",   
            "desc" => "",   
            "id" => "_latest_post_time",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );
			
$options[] = array(   
                "name"=>"最新文章标题文字",   
                "id"=>"_latest_post_title",   
                "std"=>"最新发布的文章",   
                "desc"=>"留空则不显示标题",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array( 
			"name" => "是否显示文章摘要",   
            "desc" => "",   
            "id" => "_latest_post_excerpt",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );
			
$options[] = array( 
			"name" => "是否显示缩略图",   
            "desc" => "",   
            "id" => "_latest_post_thumbnail",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );
			
$options[] = array( 
			"name" => "是否显示分页",   
            "desc" => "选择是，超过“设置 》 阅读 ”中设置的博客页面至多显示文章数量将自动显示分页",   
            "id" => "_latest_post_pagination",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );
	
$options[] = array(   
                "name"=>"要排除的分类ID",   
                "id"=>"_exclude_post_cat",   
                "std"=>"",
                "desc"=>"请在数字前添加 - 号，多个分类请用英文逗号隔开，如：-1,-2,-3",   
                "size"=>"60",   
                "type"=>"text"  
            );
               
$options[] = array( "type" => "close");
  
$options_page = new dmeng_option_class($options, $pageinfo);   
?>