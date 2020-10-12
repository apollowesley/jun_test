<!DOCTYPE html>
<html lang="zh-CN">
<?php global $dmeng_option;?>
<head>
<meta charset="<?php bloginfo( 'charset' ); ?>" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
<!--[if lte IE 7]><script>window.location.href='http://cdn.dmeng.net/upgrade-your-browser.html';</script><![endif]-->
<?php
function title(){
if ( is_single() ) { echo trim(wp_title('',FALSE)).' - ';if (get_query_var('page')) : echo '第';echo get_query_var('page');echo '页 - '; endif; $cat = get_the_category();foreach($cat as $key=>$category){echo $category->cat_name.' - ';};bloginfo('name');} 
elseif ( is_home() || is_front_page() ) { bloginfo('name');$dec = get_bloginfo('description');if(!empty($dec)) : echo ' - '.$dec; endif; }
elseif( is_search() ){  the_search_query();echo ' - 搜索结果 - ';$paged = get_query_var('paged'); if ( $paged > 1 ) printf('第 %s 页 - ',$paged); bloginfo('name');}
else { echo trim(wp_title('',FALSE)).' - ';$paged = get_query_var('paged'); if ( $paged > 1 ) printf('第 %s 页 - ',$paged); bloginfo('name');}
}
function keywords(){
	if( is_home() || is_front_page() ){ global $dmeng_option;echo $dmeng_option['seo']['_seo_keywords']; }
	elseif( is_category() ){ single_cat_title(); }
	elseif( is_single() ){ 
		echo trim(wp_title('',FALSE)).',';
		if ( has_tag() ) {foreach((get_the_tags()) as $tag ) { echo $tag->name.','; } }//循环所有标签
		foreach((get_the_category()) as $category) { echo $category->cat_name.','; } //循环所有分类目录
	}
	elseif( is_search() ){ the_search_query(); }
	else{ echo trim(wp_title('',FALSE)); }
}
function description(){
	if( is_home() || is_front_page() ){ global $dmeng_option;echo $dmeng_option['seo']['_seo_description']; }
	elseif( is_category() ){ $description = strip_tags(category_description());echo trim($description);}
	elseif( is_single() ){ echo get_the_excerpt(); }
	elseif( is_search() ){ echo '“';the_search_query();echo '”为您找到结果 ';global $wp_query;echo $wp_query->found_posts;echo ' 个'; }
	elseif( is_tag() ){  $description = strip_tags(tag_description());echo trim($description); }
	else{ $description = strip_tags(term_description());echo trim($description); }
}
?>
<title><?php title();?></title>
<meta name="description" content="<?php description();?>" />
<meta name="keywords" content="<?php keywords();?>" />
<meta property="og:title" content="<?php title();?>"/>
<meta property="og:description" content="<?php description();?>"/>
<meta property="og:url" content="<?php echo get_bloginfo('url').$_SERVER["REQUEST_URI"];?>"/>
<meta property="og:site_name" content="<?php bloginfo('name');?>"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<?php bloginfo('template_url');?>/css/bootstrap.min.css" rel="stylesheet" media="screen">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="<?php bloginfo('template_url');?>/js/html5shiv.js"></script>
<script src="<?php bloginfo('template_url');?>/js/respond.min.js"></script>
<![endif]-->
<link href="<?php bloginfo('stylesheet_url');?>" rel="stylesheet" media="screen">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<?php bloginfo('template_url');?>/js/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<?php bloginfo('template_url');?>/js/bootstrap.min.js"></script>
<?php wp_head();?>
<?php echo $dmeng_option['theme']['_head_code']; ?>
<?php setPostViews('43420024420');//保存流量数据?>
</head>
<body id="body">
<header>
	<nav id="navbar" class="navbar <?php echo $dmeng_option['navbar']['_navbar_color']; ?> <?php echo $dmeng_option['navbar']['_navbar_position']; ?>" role="navigation">
	<div class="container">
	  <!-- Brand and toggle get grouped for better mobile display -->
	  <div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
		  <span class="sr-only">切换导航</span>
		  <span class="icon-bar"></span>
		  <span class="icon-bar"></span>
		  <span class="icon-bar"></span>
		</button>
		<?php $logo = $dmeng_option['theme']['_site_logo'];	if(!empty($logo)){ $logo = '<span class="'.$logo.'"></span>';} ?>
		<?php if ( is_home() || is_front_page() ) { //首页输出H1?>
		<h1 class="site-title"><a class="navbar-brand" href="<?php echo home_url( '/' ); ?>"><?php echo $logo;?> <?php bloginfo('name');?></a></h1>
		<?php } else { //否则输出DIV?>
		<div class="site-title"><a class="navbar-brand" href="<?php echo home_url( '/' ); ?>"><?php echo $logo;?> <?php bloginfo('name');?></a></div>
		<?php } ?>
	  </div>

	  <!-- Collect the nav links, forms, and other content for toggling . -->
	  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
<?php if ( has_nav_menu( 'header-menu' ) ) {
wp_nav_menu( array('theme_location' => 'header-menu','container' => '','container_class' => '','container_id' => '','menu_class' => 'nav navbar-nav','items_wrap' => '<ul class="%2$s">%3$s</ul>','walker' => new themeslug_walker_nav_menu )); //左侧主菜单 
}else{
echo '<ul class="nav navbar-nav">';
wp_list_pages('sort_column=menu_order&title_li=');
echo '</ul>';
} ?>
<?php $search_display = $dmeng_option['navbar']['_search_display'];$search_position = $dmeng_option['navbar']['_search_position']; //定义搜索框设置选项值 ?>
<?php if ( $search_display=="search-display") { //如果选择显示搜索框就显示 ?>
		<form class="navbar-form <?php if ( $search_position=="search-left") { echo 'navbar-left'; } else if ( $search_position=="search-right") { echo 'navbar-right'; } //根据后台设置不同显示不同的搜索框位置 ?>" role="search" method="get" id="searchform" action="<?php echo home_url( '/' ); ?>">
		  <div class="form-group">
			<input type="text" class="form-control" placeholder="输入关键词搜索" name="s" id="s" >
		  </div>
		  <button type="submit" class="btn btn-default" id="searchsubmit"><span class="glyphicon glyphicon-search"></span></button>
		</form>
<?php } //判断搜索框结束 ?>
<?php if ( has_nav_menu( 'header-menu-right' ) ) {
wp_nav_menu( array('theme_location' => 'header-menu-right','container' => '','container_class' => '','container_id' => '','menu_class' => 'nav navbar-nav navbar-right header-nav-right','items_wrap' => '<ul class="%2$s">%3$s</ul>','walker' => new themeslug_walker_nav_menu )); //右侧副菜单 
} ?>
	  </div><!-- /.navbar-collapse -->
	  </div>
	</nav>
</header>
<?php $navbar_position = $dmeng_option['navbar']['_navbar_position'];
//如果固定在顶部，按navbar高度设置body上边距
if ( $navbar_position=="navbar-fixed-top") { echo "<script>var body=$('#body'),st;var nav=$('#navbar'),st;var navh=nav.innerHeight()+20;body.attr({style:'padding-top:'+navh+'px'});</script>";  }
//如果固定在底部，设置body上边距为20PX以及按navbar高度设置body下边距
elseif ( $navbar_position=="navbar-fixed-bottom") { echo "<script>var body=$('#body'),st;var nav=$('#navbar'),st;var navh=nav.innerHeight()+20;body.attr({style:'padding-top:20px;padding-bottom:'+navh+'px'});</script>"; } ?>
<div <?php if ( !wp_is_mobile() ) { echo 'class="container"'; }?>>