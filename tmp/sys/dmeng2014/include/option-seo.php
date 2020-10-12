<?php   
  
$pageinfo = array('full_name' => '网站SEO设置', 'optionname'=>'seo', 'child'=>true, 'filename' => basename(__FILE__));   
  
$options = array();   
  
$options[] = array(    
                "name"=>"说明文档",   
                "desc"=>"<p>设置SEO让网站对搜索引擎更友好。</p><p>内页关键词会自动生成，描述则要手工添加，如文章的需要添加手工摘要，如目录需要在编辑目录的时候添加目录描述，标签也是如此。</p>",   
                "type" => "title"); 

$options[] = array( "type" => "open","desc"=>"关键词和描述");  
			
$options[] = array(   
                "name"=>"网站关键词",   
                "id"=>"_seo_keywords",   
                "std"=>"",   
                "desc"=>"以英文逗号或空格或竖线隔开，一般不超过100个字符",   
                "size"=>"60",   
                "type"=>"text"  
            );	
			
$options[] = array(   
                "name"=>"网站描述",   
                "id"=>"_seo_description",   
                "std"=>"",   
                "desc"=>"对网站的简短描述，一般不超过200个字符",   
                "size"=>"60",   
                "type"=>"textarea"  
            );
               
$options[] = array( "type" => "close");				
				
$options[] = array( "type" => "open","desc"=>"链接设置");  
			
$options[] = array( 
			"name" => "是否自动在目录链接后加 / ？",   
            "desc" => "",   
            "id" => "_seo_slash_url",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是（推荐）',   
                'no'=>'否',   
                )   
            );	
               
$options[] = array( "type" => "close");   

$options[] = array( "type" => "open","desc"=>"搜索结果"); 

$options[] = array( 
			"name" => "是否强调搜索的关键词？",   
            "desc" => "强调关键词只是用strong标签强调语义，选择是或否对前端效果并没有变化",
            "id" => "_seo_search_keyword",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是（推荐）',   
                'no'=>'否',   
                )   
            ); 
			
$options[] = array( 
			"name" => "是否排除所有页面？",   
            "desc" => "建议选择是，把所有页面从搜索结果中排除",   
            "id" => "_seo_search_exclude_page",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是（推荐）',   
                'no'=>'否',   
                )   
            );	
			
$options[] = array(   
                "name"=>"要排除的分类ID",   
                "id"=>"_seo_search_exclude_cat",   
                "std"=>"",   
                "desc"=>"在分类ID前加负号代表排除，如 -1 ；如直接填写分类ID则代表只在该分类中搜索，如 1 。多个ID以英文逗号分开。如 -1,-3,-5 或者 1,3,5 ",   
                "size"=>"60",   
                "type"=>"text"  
            );
               
$options[] = array( "type" => "close"); 
  
$options_page = new dmeng_option_class($options, $pageinfo);   
?>