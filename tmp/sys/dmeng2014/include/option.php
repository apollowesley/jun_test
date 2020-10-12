<?php   
  
$pageinfo = array('full_name' => '多梦主题设置', 'optionname'=>'theme', 'child'=>false, 'filename' => basename(__FILE__));   
  
$options = array();  
  
$options[] = array(    
                "name"=>"说明文档",   
                "desc"=>"<p>网站LOGO图标设置请访问<a href='http://cdn.dmeng.net/glyphicons.html' target='_blank'>Glyphicons字体图标</a>选择你喜欢的图标，然后复制图标下的类名粘贴到输入框。</p><p>极速版手机主题简洁快速，以内容为核心，推荐以文字为主的博客启用。</p>",   
                "type" => "title"); 

$options[] = array( "type" => "open","desc"=>"全局设置"); 

$options[] = array(   
                "name"=>"网站LOGO图标",   
                "id"=>"_site_logo",   
                "std"=>"glyphicon glyphicon-send",   
                "desc"=>"字体图标类，如 glyphicon glyphicon-send ，留空则不显示。",   
                "size"=>"60",
                "type"=>"text"  
            ); 
				
$options[] = array(   
                "name"=>"网站备案号",   
                "id"=>"_icp_text",   
                "std"=>"",   
                "desc"=>"",   
                "size"=>"60",   
                "type"=>"text"  
            );
			
$options[] = array(   
                "name"=>"头部HEAD代码",   
                "id"=>"_head_code",   
                "std"=>"",   
                "desc"=>"如添加meta信息验证网站所有权",   
                "size"=>"60",   
                "type"=>"textarea"  
            );

$options[] = array(   
                "name"=>"脚部统计代码",   
                "id"=>"_analytics_code",   
                "std"=>"",   
                "desc"=>"放置CNZZ、百度统计或安全网站认证小图标等",   
                "size"=>"60",   
                "type"=>"textarea"  
            );
			
$options[] = array( 
			"name" => "是否显示浮动按钮",   
            "desc" => "选择是时显示到顶部、刷新、到底部等浮动按钮",   
            "id" => "_floatButton_display",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );			
			   
$options[] = array( "type" => "close"); 

$options[] = array( "type" => "open","desc"=>"内容设置");  
				
$options[] = array( 
			"name" => "是否显示百度分享",   
            "desc" => "选择是时将在内容页面显示百度分享按钮",   
            "id" => "_share_display",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',
                )   
            );
			
$options[] = array( 
			"name" => "是否自动生成锚点导航",   
            "desc" => "选择是时将把文章页和页面内容中的h2标题生成锚点导航目录",   
            "id" => "_index_display",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',   
                )   
            );

$options[] = array( 
			"name" => "是否显示面包屑导航",   
            "desc" => "设置文章页的面包屑导航",   
            "id" => "_breadcrumbs_display",   
            "type" => "radio",
			"std"=> "no", 
            "buttons" => array(   
                'yes-top'=>'显示在内容上方',
				'yes-bottom'=>'显示在内容下方',   
                'no'=>'不显示',
                )   
            );			
			   
$options[] = array( "type" => "close"); 

$options[] = array( "type" => "open","desc"=>"移动端优化");  
				
$options[] = array( 
			"name" => "是否开启极速版手机主题",   
            "desc" => "选择是时手机访问将自动使用更快更简洁的手机专用主题",   
            "id" => "_mobile_theme",   
            "type" => "radio",
			"std"=> "yes", 
            "buttons" => array(   
                'yes'=>'是',   
                'no'=>'否',
                )   
            );		
			   
$options[] = array( "type" => "close"); 
  
$options_page = new dmeng_option_class($options, $pageinfo);   
?>