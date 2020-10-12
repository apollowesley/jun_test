<!DOCTYPE html>
<html lang="zh-CN">
<?php global $dmeng_option;?>
<head>
<meta charset="<?php bloginfo( 'charset' ); ?>" />
<meta content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport" />
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
<style type="text/css">
@font-face{font-family:'Glyphicons Halflings';src:url('<?php bloginfo('template_url');?>/fonts/glyphicons-halflings-regular.eot');src:url('<?php bloginfo('template_url');?>/fonts/glyphicons-halflings-regular.eot?#iefix') format('embedded-opentype'),url('<?php bloginfo('template_url');?>/fonts/glyphicons-halflings-regular.woff') format('woff'),url('<?php bloginfo('template_url');?>/fonts/glyphicons-halflings-regular.ttf') format('truetype'),url('<?php bloginfo('template_url');?>/fonts/glyphicons-halflings-regular.svg#glyphicons-halflingsregular') format('svg');}.glyphicon{position:relative;top:1px;display:inline-block;font-family:'Glyphicons Halflings';font-style:normal;font-weight:normal;line-height:1;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale;}.glyphicon:empty{width:1em;}.glyphicon-asterisk:before{content:"\2a";}.glyphicon-plus:before{content:"\2b";}.glyphicon-euro:before{content:"\20ac";}.glyphicon-minus:before{content:"\2212";}.glyphicon-cloud:before{content:"\2601";}.glyphicon-envelope:before{content:"\2709";}.glyphicon-pencil:before{content:"\270f";}.glyphicon-glass:before{content:"\e001";}.glyphicon-music:before{content:"\e002";}.glyphicon-search:before{content:"\e003";}.glyphicon-heart:before{content:"\e005";}.glyphicon-star:before{content:"\e006";}.glyphicon-star-empty:before{content:"\e007";}.glyphicon-user:before{content:"\e008";}.glyphicon-film:before{content:"\e009";}.glyphicon-th-large:before{content:"\e010";}.glyphicon-th:before{content:"\e011";}.glyphicon-th-list:before{content:"\e012";}.glyphicon-ok:before{content:"\e013";}.glyphicon-remove:before{content:"\e014";}.glyphicon-zoom-in:before{content:"\e015";}.glyphicon-zoom-out:before{content:"\e016";}.glyphicon-off:before{content:"\e017";}.glyphicon-signal:before{content:"\e018";}.glyphicon-cog:before{content:"\e019";}.glyphicon-trash:before{content:"\e020";}.glyphicon-home:before{content:"\e021";}.glyphicon-file:before{content:"\e022";}.glyphicon-time:before{content:"\e023";}.glyphicon-road:before{content:"\e024";}.glyphicon-download-alt:before{content:"\e025";}.glyphicon-download:before{content:"\e026";}.glyphicon-upload:before{content:"\e027";}.glyphicon-inbox:before{content:"\e028";}.glyphicon-play-circle:before{content:"\e029";}.glyphicon-repeat:before{content:"\e030";}.glyphicon-refresh:before{content:"\e031";}.glyphicon-list-alt:before{content:"\e032";}.glyphicon-lock:before{content:"\e033";}.glyphicon-flag:before{content:"\e034";}.glyphicon-headphones:before{content:"\e035";}.glyphicon-volume-off:before{content:"\e036";}.glyphicon-volume-down:before{content:"\e037";}.glyphicon-volume-up:before{content:"\e038";}.glyphicon-qrcode:before{content:"\e039";}.glyphicon-barcode:before{content:"\e040";}.glyphicon-tag:before{content:"\e041";}.glyphicon-tags:before{content:"\e042";}.glyphicon-book:before{content:"\e043";}.glyphicon-bookmark:before{content:"\e044";}.glyphicon-print:before{content:"\e045";}.glyphicon-camera:before{content:"\e046";}.glyphicon-font:before{content:"\e047";}.glyphicon-bold:before{content:"\e048";}.glyphicon-italic:before{content:"\e049";}.glyphicon-text-height:before{content:"\e050";}.glyphicon-text-width:before{content:"\e051";}.glyphicon-align-left:before{content:"\e052";}.glyphicon-align-center:before{content:"\e053";}.glyphicon-align-right:before{content:"\e054";}.glyphicon-align-justify:before{content:"\e055";}.glyphicon-list:before{content:"\e056";}.glyphicon-indent-left:before{content:"\e057";}.glyphicon-indent-right:before{content:"\e058";}.glyphicon-facetime-video:before{content:"\e059";}.glyphicon-picture:before{content:"\e060";}.glyphicon-map-marker:before{content:"\e062";}.glyphicon-adjust:before{content:"\e063";}.glyphicon-tint:before{content:"\e064";}.glyphicon-edit:before{content:"\e065";}.glyphicon-share:before{content:"\e066";}.glyphicon-check:before{content:"\e067";}.glyphicon-move:before{content:"\e068";}.glyphicon-step-backward:before{content:"\e069";}.glyphicon-fast-backward:before{content:"\e070";}.glyphicon-backward:before{content:"\e071";}.glyphicon-play:before{content:"\e072";}.glyphicon-pause:before{content:"\e073";}.glyphicon-stop:before{content:"\e074";}.glyphicon-forward:before{content:"\e075";}.glyphicon-fast-forward:before{content:"\e076";}.glyphicon-step-forward:before{content:"\e077";}.glyphicon-eject:before{content:"\e078";}.glyphicon-chevron-left:before{content:"\e079";}.glyphicon-chevron-right:before{content:"\e080";}.glyphicon-plus-sign:before{content:"\e081";}.glyphicon-minus-sign:before{content:"\e082";}.glyphicon-remove-sign:before{content:"\e083";}.glyphicon-ok-sign:before{content:"\e084";}.glyphicon-question-sign:before{content:"\e085";}.glyphicon-info-sign:before{content:"\e086";}.glyphicon-screenshot:before{content:"\e087";}.glyphicon-remove-circle:before{content:"\e088";}.glyphicon-ok-circle:before{content:"\e089";}.glyphicon-ban-circle:before{content:"\e090";}.glyphicon-arrow-left:before{content:"\e091";}.glyphicon-arrow-right:before{content:"\e092";}.glyphicon-arrow-up:before{content:"\e093";}.glyphicon-arrow-down:before{content:"\e094";}.glyphicon-share-alt:before{content:"\e095";}.glyphicon-resize-full:before{content:"\e096";}.glyphicon-resize-small:before{content:"\e097";}.glyphicon-exclamation-sign:before{content:"\e101";}.glyphicon-gift:before{content:"\e102";}.glyphicon-leaf:before{content:"\e103";}.glyphicon-fire:before{content:"\e104";}.glyphicon-eye-open:before{content:"\e105";}.glyphicon-eye-close:before{content:"\e106";}.glyphicon-warning-sign:before{content:"\e107";}.glyphicon-plane:before{content:"\e108";}.glyphicon-calendar:before{content:"\e109";}.glyphicon-random:before{content:"\e110";}.glyphicon-comment:before{content:"\e111";}.glyphicon-magnet:before{content:"\e112";}.glyphicon-chevron-up:before{content:"\e113";}.glyphicon-chevron-down:before{content:"\e114";}.glyphicon-retweet:before{content:"\e115";}.glyphicon-shopping-cart:before{content:"\e116";}.glyphicon-folder-close:before{content:"\e117";}.glyphicon-folder-open:before{content:"\e118";}.glyphicon-resize-vertical:before{content:"\e119";}.glyphicon-resize-horizontal:before{content:"\e120";}.glyphicon-hdd:before{content:"\e121";}.glyphicon-bullhorn:before{content:"\e122";}.glyphicon-bell:before{content:"\e123";}.glyphicon-certificate:before{content:"\e124";}.glyphicon-thumbs-up:before{content:"\e125";}.glyphicon-thumbs-down:before{content:"\e126";}.glyphicon-hand-right:before{content:"\e127";}.glyphicon-hand-left:before{content:"\e128";}.glyphicon-hand-up:before{content:"\e129";}.glyphicon-hand-down:before{content:"\e130";}.glyphicon-circle-arrow-right:before{content:"\e131";}.glyphicon-circle-arrow-left:before{content:"\e132";}.glyphicon-circle-arrow-up:before{content:"\e133";}.glyphicon-circle-arrow-down:before{content:"\e134";}.glyphicon-globe:before{content:"\e135";}.glyphicon-wrench:before{content:"\e136";}.glyphicon-tasks:before{content:"\e137";}.glyphicon-filter:before{content:"\e138";}.glyphicon-briefcase:before{content:"\e139";}.glyphicon-fullscreen:before{content:"\e140";}.glyphicon-dashboard:before{content:"\e141";}.glyphicon-paperclip:before{content:"\e142";}.glyphicon-heart-empty:before{content:"\e143";}.glyphicon-link:before{content:"\e144";}.glyphicon-phone:before{content:"\e145";}.glyphicon-pushpin:before{content:"\e146";}.glyphicon-usd:before{content:"\e148";}.glyphicon-gbp:before{content:"\e149";}.glyphicon-sort:before{content:"\e150";}.glyphicon-sort-by-alphabet:before{content:"\e151";}.glyphicon-sort-by-alphabet-alt:before{content:"\e152";}.glyphicon-sort-by-order:before{content:"\e153";}.glyphicon-sort-by-order-alt:before{content:"\e154";}.glyphicon-sort-by-attributes:before{content:"\e155";}.glyphicon-sort-by-attributes-alt:before{content:"\e156";}.glyphicon-unchecked:before{content:"\e157";}.glyphicon-expand:before{content:"\e158";}.glyphicon-collapse-down:before{content:"\e159";}.glyphicon-collapse-up:before{content:"\e160";}.glyphicon-log-in:before{content:"\e161";}.glyphicon-flash:before{content:"\e162";}.glyphicon-log-out:before{content:"\e163";}.glyphicon-new-window:before{content:"\e164";}.glyphicon-record:before{content:"\e165";}.glyphicon-save:before{content:"\e166";}.glyphicon-open:before{content:"\e167";}.glyphicon-saved:before{content:"\e168";}.glyphicon-import:before{content:"\e169";}.glyphicon-export:before{content:"\e170";}.glyphicon-send:before{content:"\e171";}.glyphicon-floppy-disk:before{content:"\e172";}.glyphicon-floppy-saved:before{content:"\e173";}.glyphicon-floppy-remove:before{content:"\e174";}.glyphicon-floppy-save:before{content:"\e175";}.glyphicon-floppy-open:before{content:"\e176";}.glyphicon-credit-card:before{content:"\e177";}.glyphicon-transfer:before{content:"\e178";}.glyphicon-cutlery:before{content:"\e179";}.glyphicon-header:before{content:"\e180";}.glyphicon-compressed:before{content:"\e181";}.glyphicon-earphone:before{content:"\e182";}.glyphicon-phone-alt:before{content:"\e183";}.glyphicon-tower:before{content:"\e184";}.glyphicon-stats:before{content:"\e185";}.glyphicon-sd-video:before{content:"\e186";}.glyphicon-hd-video:before{content:"\e187";}.glyphicon-subtitles:before{content:"\e188";}.glyphicon-sound-stereo:before{content:"\e189";}.glyphicon-sound-dolby:before{content:"\e190";}.glyphicon-sound-5-1:before{content:"\e191";}.glyphicon-sound-6-1:before{content:"\e192";}.glyphicon-sound-7-1:before{content:"\e193";}.glyphicon-copyright-mark:before{content:"\e194";}.glyphicon-registration-mark:before{content:"\e195";}.glyphicon-cloud-download:before{content:"\e197";}.glyphicon-cloud-upload:before{content:"\e198";}.glyphicon-tree-conifer:before{content:"\e199";}.glyphicon-tree-deciduous:before{content:"\e200";}
*{margin:0;padding:0;list-style:none;text-decoration:none;color:#333;letter-spacing:0.05em;font-family:'Microsoft Yahei',"Tahoma",Arial;font-weight:normal;word-wrap: break-word;} a{color:#428bca;} a:hover{text-decoration:none;} a:visited{color:#428bca;} span{color:#428bca;} .clean{clear:both;} .text-muted{color:#8b8b8b;} .mr10{margin-right:10px;}
body{background:#f1f1f1;}
header{height:60px;line-height:62px;overflow:hidden;background:#efefef;border-top:6px solid #428bca;border-bottom:1px solid #ccc;}
header h1{font-size:22px;color:#666;display:inline;margin:0 0 0 15px;}
header h1 span{font-size:18px;float:none;margin:0 15px 0 0;color:#428bca;}
header h1 a{color:#428bca;letter-spacing:0.1em;}
header h1 a:hover{text-decoration:none;}
header span{font-size:26px;float:right;margin:15px 15px 0 0;color:#999;}
form{margin:0;border-bottom:1px solid #ccc;background:#f5f5f5;color:#999;font-size:14px;}
form input[type=text], input[type=submit], input[type=reset]{padding:0.5em;border: 1px solid #e1e6ed;display:inline-block;background:white;}
form input[type=text]{margin:10px 0 10px 10px;width:65%;}
form input[type=submit]{margin:10px 0 10px 10px;}
nav{padding:0;background:#f5f5f5;display:none;}
nav ul li{padding:0;}
nav ul li a{padding:10px;border-bottom:1px solid #ccc;display:block;background:#efefef;line-height:32px;}
nav ul li.dropdown a{color:#666;background:#ccc;}
nav ul li ul.sub-menu li a{padding-left:20px;background:white;color:#428bca;}
article{background:#fff;padding-top:20px;border-top:1px solid #e1e6ed;}
article ul{border-top:1px solid #e1e6ed;margin-bottom:20px;}
article ul li{border-bottom:1px solid #e1e6ed;}
article ul li a{display:block;padding:10px;}
article ul li h2{font-size:18px;line-height:22px;margin:3px 0 6px 0;}
article ul li p{font-size:12px;line-height:20px;color:#777;}
article .header{margin-bottom:20px;padding:10px;border-left:6px solid #428bca;}
article .header h1{font-size:22px;line-height:27px;}
article .header p,time{color:#8b8b8b;font-size:12px;line-height:12px;margin-top:10px;}
article .content{padding:10px;color:#202020;font-size:1em;line-height:30px;}
article .content p{margin:8px 0 12px;}
article .content img{max-width:100%;}
article .content pre{margin:0;padding:0;border:1px solid #e1e6ed;display:inline-table;padding:10px;margin-bottom:20px;}
article .content pre ol li{margin:0;padding:0;display:inline-table;}
article .content h2{font-size:18px;line-height:38px;border-bottom:1px solid #e1e6ed;}
article .content h3{font-size:16px;font-weight:bold;line-height:38px;}
article .list-group-item{line-height:38px;}
article .list-group-item span{color:#999;padding:0;margin:0;}
article .list-group-item a, .list-group-item a span{display:inline;color:#428bca;padding:0;margin:0;}
article .footer{background:#efefef;border:1px solid #ccc;border-left:0;border-right:0;line-height:26px;padding:10px;font-size:12px;}
code,code p{white-space:normal;word-break:break-all;padding:2px 4px;font-size:90%;color:#c7254e;background-color:#f9f2f4;border-radius:4px;}
blockquote {text-indent:0;border-left:10px solid #ccc;padding:15px 25px;background-color:#eee;display:block;}
blockquote p{font-size:14px;line-height:24px;}
.pagination li{display:inline-block;background:#fff;padding:5px 10px;border:1px solid #e1e6ed;margin:10px 0 0 10px;}
.pagination li span{color:#999;}
.pager{margin-bottom:0;}
.tougao {margin:10px 10px 30px;padding:10px;background:#efefef;border:1px solid #ccc;}
.tougao label{display:block;}
.tougao input[type=text]{width:85%;}
.tougao textarea{margin: 10px 0 10px 10px;width:85%;padding:0.5em;border: 1px solid #e1e6ed;background:white;display:inline-block;}
#comments{padding:10px;}
#commentform{background:none;}
#comments h3{margin:15px 0;}
#comments p{margin:10px 0;}
#comments label{display:block;}
#comments input[type=text]{width:85%;}
#comments textarea{margin: 10px 0 10px 10px;width:85%;padding:0.5em;border: 1px solid #e1e6ed;background:white;display:inline-block;}
#comments ol li{border: 1px solid #e1e6ed;background:white;margin:5px 0;padding:10px;}
#comments ol.children{margin-left:25px;}
#comments .fn{font-style:normal;}
#comments .reply{text-align:right;}
#comments .comment-meta a{font-size:13px;margin:5px 0;color:#999;}
#comments .pager{margin:5px 0;}
#comments .pager .previous{float:left;}
#comments .pager .next{float:right;}
footer{margin:0;padding:10px 0;color:#8b8b8b;font-size:14px;text-align:center;}
footer ul li{float:left;width:33.333333%;}
footer ul li a{display:block;background:#fff;border:1px solid #e1e6ed;text-align:center;line-height:26px;padding:10px 0;margin:10px 0;}
footer p{margin:10px 0;}
</style>
<script>
function showEm(id){
	var element=document.getElementById(id);
	if ( element.style.display=="block" ){ element.style.display="none";}
	else { element.style.display="block"; }
}
</script>
<?php wp_head();?>
<?php setPostViews('43420024420');//保存流量数据?>
</head>
<body>

<header>
	<a href="#" onclick="showEm('navbar')"><span class="glyphicon glyphicon-list"></span></a>
	<h1><a href="<?php echo home_url();?>"><?php $logo = $dmeng_option['theme']['_site_logo'];	if(!empty($logo)){ echo '<span class="'.$logo.'"></span>';} ?> <?php bloginfo('name');?></a></h1>
</header>
<nav id="navbar">
	<ul>
		<?php wp_nav_menu( array( 'container' => '','theme_location' => 'header-menu','items_wrap' => '%3$s' ) ); ?>
		<?php if ( has_nav_menu( 'header-menu-right' ) ) {wp_nav_menu( array( 'container' => '','theme_location' => 'header-menu-right','items_wrap' => '%3$s' ) );} ?>
		<div class="clean"></div>
	</ul>
</nav>
<form method="get" id="search" action="<?php echo home_url( '/' ); ?>">
  <input type="text" placeholder="输入关键词搜索" name="s" id="s" >
  <input type="submit" id="searchsubmit" value="搜索">
</form>
<?php
function moblie_excerpt_length( $length ) {
	return 50;
}
add_filter( 'excerpt_length', 'moblie_excerpt_length', 999 );
?>