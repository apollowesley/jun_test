<?php   
  
$pageinfo = array('full_name' => '评论框设置', 'optionname'=>'comments', 'child'=>true, 'filename' => basename(__FILE__));   
  
$options = array();   
  
$options[] = array(    
                "name"=>"说明文档",   
                "desc"=>"这里对主题默认评论框进行设置，如果你启用了第三方评论框（如多说）则这里的设置不生效。",   
                "type" => "title"); 

$options[] = array( "type" => "open","desc"=>"评论框样式");  
				
$options[] = array( 
			"name" => "（有评论时）配色方案",   
            "desc" => "",   
            "id" => "_have_comments_color",   
            "type" => "radio",
			"std"=> "panel-primary", 
            "buttons" => array(   
				'panel-default'=>'浅灰（默认）',
                'panel-primary'=>'深蓝（主要）',  
                'panel-success'=>'淡绿（成功）',  
                'panel-info'=>'淡蓝（信息）',   
                'panel-warning'=>'淡黄（警示）',
                'panel-danger'=>'淡红（危险）',   		
                )   
            );	
				
$options[] = array( 
			"name" => "（没评论时）配色方案",   
            "desc" => "",   
            "id" => "_no_comments_color",   
            "type" => "radio",
			"std"=> "panel-default", 
            "buttons" => array(   
				'panel-default'=>'浅灰（默认）',
                'panel-primary'=>'深蓝（主要）',   
                'panel-success'=>'淡绿（成功）',  
                'panel-info'=>'淡蓝（信息）',   
                'panel-warning'=>'淡黄（警示）',
                'panel-danger'=>'淡红（危险）',   		
                )   
            );

$options[] = array( 
			"name" => "（评论内容）配色方案",   
            "desc" => "",   
            "id" => "_comments_color",   
            "type" => "radio",
			"std"=> "panel-default", 
            "buttons" => array(   
				'panel-default'=>'浅灰（默认）',
                'panel-primary'=>'深蓝（主要）',   
                'panel-success'=>'淡绿（成功）',  
                'panel-info'=>'淡蓝（信息）',   
                'panel-warning'=>'淡黄（警示）',
                'panel-danger'=>'淡红（危险）',   		
                )   
            );				
               
$options[] = array( "type" => "close");  

$options[] = array( "type" => "open","desc"=>"评论框文本");   

$options[] = array(   
                "name"=>"没有评论时",   
                "id"=>"_comments_zero",   
                "std"=>"咦？还没有评论，抢沙发！",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"一条评论时",   
                "id"=>"_comments_one",   
                "std"=>"沙发被抢了，赶快抢前排！",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"%条评论时",   
                "id"=>"_comments_more",   
                "std"=>"现有 % 条评论",   
                "desc"=>"%代表评论数量",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"名称",   
                "id"=>"_comment_author",   
                "std"=>"名称",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"名称背景文本",   
                "id"=>"_comment_author_placeholder",   
                "std"=>"请输入你的名称",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );

$options[] = array(   
                "name"=>"邮箱",   
                "id"=>"_comment_email",   
                "std"=>"邮箱",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"邮箱背景文本",   
                "id"=>"_comment_email_placeholder",   
                "std"=>"请输入你的邮箱",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );

$options[] = array(   
                "name"=>"网址",   
                "id"=>"_comment_url",   
                "std"=>"网址",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"网址背景文本",   
                "id"=>"_comment_url_placeholder",   
                "std"=>"请输入你的网址",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"评论框标题",   
                "id"=>"_title_reply",   
                "std"=>"发表评论",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"评论给 %s",   
                "id"=>"_title_reply_to",   
                "std"=>"评论给 %s",   
                "desc"=>"%s 代表目标对象的名称",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"取消评论",   
                "id"=>"_cancel_reply_link",   
                "std"=>"取消评论",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"发表评论（按钮）",   
                "id"=>"_label_submit",   
                "std"=>"发表评论",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );

$options[] = array(   
                "name"=>"评论框背景文本",   
                "id"=>"_from_placeholder",   
                "std"=>"说点什么吧...",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );

$options[] = array(   
                "name"=>"评论框上方文本",   
                "id"=>"_comment_notes_before",   
                "std"=>"带 * 的是必填项目，电子邮件地址不会被公开。",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );

$options[] = array(   
                "name"=>"评论框下方文本",   
                "id"=>"_comment_notes_after",   
                "std"=>"文字的交流也是情感的交流，技能的交流也是学术的交流。",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"关闭评论时",   
                "id"=>"_comment_close",   
                "std"=>"评论功能已经关闭",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array( "type" => "close");  
  
$options_page = new dmeng_option_class($options, $pageinfo);   
?>