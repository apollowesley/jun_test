<?php
//激活主题创建投稿页面
include_once('include/add-page.php');
//添加主题设置
include_once('include/version.php');
include_once('include/optionclass.php');
include_once('include/option.php');
include_once('include/option-home.php');
include_once('include/option-slide.php');
include_once('include/option-navbar.php');
include_once('include/option-comments.php');
include_once('include/option-sidebar.php');
include_once('include/option-seo.php');
include_once('include/option-ad.php');
//载入手机模板
global $dmeng_option;if ( $dmeng_option['theme']['_mobile_theme']=='yes' && wp_is_mobile() ) { include_once('include/mobile.php'); }
//添加文章推荐标签
include_once('include/recommend.php');
//侧边栏和小工具
include_once('include/sidebar.php');
include_once('include/widget.php');
//增强编辑器
include_once('include/editor.php');
//移除修订版本功能
remove_action('pre_post_update', 'wp_save_post_revision' );
//添加文章缩略图
add_theme_support( 'post-thumbnails' );
//用文章中第一张图片作为缩略图
function dmeng_the_thumbnail($class,$style) {
	global $post;
	// 判断该文章是否设置的缩略图，如果有则直接显示
	if ( has_post_thumbnail() ) {
		echo get_the_post_thumbnail($post_id, 'thumbnail', array('class' => $class));
	} else { //如果文章没有设置缩略图，则查找文章内是否包含图片
		$content = $post->post_content;
		preg_match_all('/<img.*?(?: |\\t|\\r|\\n)?src=[\'"]?(.+?)[\'"]?(?:(?: |\\t|\\r|\\n)+.*?)?>/sim', $content, $strResult, PREG_PATTERN_ORDER);
		$n = count($strResult[1]);
		if($n > 0){ // 如果文章内包含有图片，就用第一张图片做为缩略图
			echo '<img src="'.$strResult[1][0].'" itemprop="image" ';
			if (!empty($class)) { echo 'class="'.$class.'"'; }
			if (!empty($style)) { echo 'style="'.$style.'"'; }
			echo ' />';
		}else { // 如果文章内没有图片，则用默认的图片。
			echo '<img src="';bloginfo('template_url');echo '/images/nopic.png" itemprop="image" ';
			if (!empty($class)) { echo 'class="'.$class.'"'; }
			if (!empty($style)) { echo 'style="'.$style.'"'; }
			echo ' />';
		}
	}
}
//自定义搜索结果
function search_filter($query) {
if ($query->is_search) {
global $dmeng_option;
$exclude_page = $dmeng_option['seo']['_seo_search_exclude_page'];
$exclude_cat = $dmeng_option['seo']['_seo_search_exclude_cat'];
if ( $exclude_page=="yes") { $query->set('post_type', 'post'); }
$query->set('cat',$exclude_cat);
}
return $query;
}
add_filter('pre_get_posts','search_filter');
//自动生成锚点导航
if ( $dmeng_option['theme']['_index_display']=="yes") {
function article_index($content) { 
    $matches = array();  
    $ul_li = '';  
    $r = "/<h2>([^<]+)<\/h2>/im";  
    if(preg_match_all($r, $content, $matches)) {  
        foreach($matches[1] as $num => $title) {  
            $content = str_replace($matches[0][$num], '<h2 id="'.$num.'">'.$title.'</h2>', $content);  
            $ul_li .= '<li><a href="#'.$num.'" title="'.$title.'">'.$title."</a></li>\n";  
        }  
        $content = "\n<pre class=\"pull-right ml10 article_index\">
                <h4 class=\"ml10\">目 录</h4>
                <ol>" . $ul_li . "</ol>
            </pre>\n" . $content;  
    }  
    return $content;  
}  
add_filter( "the_content", "article_index" );
}
//规定摘要字数
function custom_excerpt_length( $length ) {
	return 120;
}
add_filter( 'excerpt_length', 'custom_excerpt_length', 999 );
//改变摘要结束省略符号
function new_excerpt_more( $more ) {
	return ' .....';
}
add_filter('excerpt_more', 'new_excerpt_more'); 
//注册菜单
register_nav_menus( array( 'header-menu' => '头部主菜单','header-menu-right' => '头部右侧菜单' ) );
//改变菜单输出样式以适应Bootstrap菜单
class themeslug_walker_nav_menu extends Walker_Nav_Menu {
// add classes to ul sub-menus
function start_lvl( &$output, $depth ) {
    // depth dependent classes
    $indent = ( $depth > 0  ? str_repeat( "\t", $depth ) : '' ); // code indent
    $display_depth = ( $depth + 1); // because it counts the first submenu as 0
  
    // build html
    $output .= "\n" . $indent . '<ul class="dropdown-menu">' . "\n";
}
// add main/sub classes to li's and links
function start_el( &$output, $item, $depth, $args ) {
    global $wp_query;
    $indent = ( $depth > 0 ? str_repeat( "\t", $depth ) : '' ); // code indent
  
    // passed classes
    $classes = empty( $item->classes ) ? array() : (array) $item->classes;
    $class_names = esc_attr( implode( ' ', apply_filters( 'nav_menu_css_class', array_filter( $classes ), $item ) ) );
  
    // build html
    $output .= $indent . '<li class="' . $class_names . '">';
  
    // link attributes
    $attributes  = ! empty( $item->attr_title ) ? ' title="'  . esc_attr( $item->attr_title ) .'"' : '';
    $attributes .= ! empty( $item->target )     ? ' target="' . esc_attr( $item->target     ) .'"' : '';
    $attributes .= ! empty( $item->xfn )        ? ' rel="'    . esc_attr( $item->xfn        ) .'"' : '';
    $attributes .= ! empty( $item->url )        ? ' href="'   . esc_attr( $item->url        ) .'"' : '';
	$attributes .= ! empty( $item->data_toggle ) ? ' data-toggle="'   . esc_attr( $item->data_toggle ) .'"' : '';
	$attributes .= ! empty( $item->a_class ) ? ' class="'   . esc_attr( $item->a_class ) .'"' : '';
    $item_output = sprintf( '%1$s<a%2$s>%3$s%4$s%5$s</a>%6$s',
        $args->before,
        $attributes,
        $args->link_before,
        apply_filters( 'the_title', $item->title, $item->ID ),
        $args->link_after,
        $args->after
    );
    // build html
    $output .= apply_filters( 'walker_nav_menu_start_el', $item_output, $item, $depth, $args );
}
}
//修改拥有二级菜单的顶级菜单项
add_filter( 'wp_nav_menu_objects', 'add_menu_parent_class' );
function add_menu_parent_class( $items ) {
	$parents = array();
	foreach ( $items as $item ) {
		if ( $item->menu_item_parent && $item->menu_item_parent > 0 ) {
			$parents[] = $item->menu_item_parent;
		}
	}
	foreach ( $items as $item ) {
		if ( in_array( $item->ID, $parents ) ) {
			$item->classes[] = 'dropdown';
			$item->title = $item->title.'<b class="caret"></b>';
			$item->a_class = 'dropdown-toggle';
			$item->data_toggle = 'dropdown';
		}
	}
	return $items;	
}
//添加active为当前激活的菜单CSS类
function change_menu_current_class( $classes ) {
     if ( in_array('current-menu-item', $classes ) OR in_array( 'current-menu-ancestor', $classes ) )
          $classes[] = 'active';
     return $classes;
}
add_filter( 'nav_menu_css_class', 'change_menu_current_class' );
//清理菜单多余CSS CLASS，保留二级菜单dropdown和当前激活菜单active
add_filter('nav_menu_css_class', 'clean_menu_class', 100, 1);
add_filter('nav_menu_item_id', 'clean_menu_class', 100, 1);
add_filter('page_css_class', 'clean_menu_class', 100, 1);
function clean_menu_class($var) {
    return is_array($var) ? array_intersect($var, array('active','dropdown')) : '';
}
//评论设置
function mytheme_comment($comment, $args, $depth) {
$GLOBALS['comment'] = $comment; ?>
<li <?php comment_class(); ?> id="li-comment-<?php comment_ID() ?>">
<div id="comment-<?php comment_ID(); ?>">
<div class="panel <?php global $dmeng_option;echo $dmeng_option['comments']['_comments_color'];?>">
  <div class="panel-heading"><?php echo get_avatar( $comment, 32 ); ?> <?php echo '<cite>'.get_comment_author_link().'</cite> ';echo '<span class="text-muted">';echo get_comment_date().' '; echo get_comment_time();echo '</span>'; ?></div>
  <div class="panel-body">
<?php if ($comment->comment_approved == '0') : ?>
<div class="alert alert-warning">您的评论正在等待审核。 </div>
<?php endif; ?>
    <?php comment_text() ?>
	<?php comment_reply_link(array_merge( $args, array('depth' => $depth, 'max_depth' => $args['max_depth']))) ?>
	<?php edit_comment_link(__(' 编辑 '),' ','') ?>
  </div>
</div>
</div>
<?php
}
//设置密码保护文章密码表单样式
function my_password_form() {
    global $post;
    $label = 'pwbox-'.( empty( $post->ID ) ? rand() : $post->ID );
    $o = '<form action="' . esc_url( site_url( 'wp-login.php?action=postpass', 'login_post' ) ) . '" method="post">
    ' . __( '<div class="alert alert-warning">文章已被设置密码保护，需要查看文章内容请输入密码。</div>' ) . '<div class="col-xs-4"> </label> <input name="post_password" id="' . $label . '" type="password" class="form-control" placeholder="请输入密码" /></div> <input type="submit" name="Submit" class="btn btn-primary" value="' . esc_attr__( "提交" ) . '" /> <div class="clean"></div>
    </form>
    ';
    return $o;
}
add_filter( 'the_password_form', 'my_password_form' );
//在目录链接后加/
global $dmeng_option;
$seo_slash_url = $dmeng_option['seo']['_seo_slash_url'];
if ( $seo_slash_url=="yes") {
$permalink_structure = get_option('permalink_structure');
if (!$permalink_structure || '/' === substr($permalink_structure, -1))
	return;
add_filter('user_trailingslashit', 'ppm_fixe_trailingslash', 10, 2);
function ppm_fixe_trailingslash($url, $type)
{
	if ('single' === $type)
		return $url;

	return trailingslashit($url);
}
}
//设置浏览次数
function setPostViews($postID) {
    $count_key = 'post_views_count';
    $count = get_post_meta($postID, $count_key, true);
    if($count==''){
        $count = 0;
        delete_post_meta($postID, $count_key);
        add_post_meta($postID, $count_key, '0');
    }else{
        $count++;
        update_post_meta($postID, $count_key, $count);
    }
}
//为了确保计数的精准度，剔除头部产生的预备代码
remove_action( 'wp_head', 'adjacent_posts_rel_link_wp_head', 10, 0);
//统计文章浏览次数
function getPostViews($postID){
    $count_key = 'post_views_count';
    $count = get_post_meta($postID, $count_key, true);
    if($count==''){
        delete_post_meta($postID, $count_key);
        add_post_meta($postID, $count_key, '0');
        return "0";
    }
    return $count;
}
//分页导航
function dmeng_paging_nav() {
global $wp_query;
$pages = $wp_query->max_num_pages;
if ( $pages >= 2 ):
$big = 999999999;
$paginate = paginate_links( array(
	'base' => str_replace( $big, '%#%', esc_url( get_pagenum_link( $big ) ) ),
	'format' => '?paged=%#%',
	'current' => max( 1, get_query_var('paged') ),
	'total' => $wp_query->max_num_pages,
	'end_size' => 13,//最多显示13个页码
	'type' => 'array'
) );
echo '<ul class="pagination">';
foreach ($paginate as $value) {
    echo '<li>'.$value.'</li>';
}
echo '</ul>';
endif;
}
//文章分页
function dmeng_link_pages() {
$a = wp_link_pages( array(
		'before'           => '<ul class="list-group"><li class="list-group-item"><span class="text-muted mr10">文章导航</span>',
		'after'            => '</li></ul>',
		'link_before'      => '<span class="mr10">第',
		'link_after'       => '页</span>',
		'next_or_number'   => 'number',
		'separator'        => ' ',
		'nextpagelink'     => __( 'Next page' ),
		'previouspagelink' => __( 'Previous page' ),
		'pagelink'         => '%',
		'echo'             => 0
) );
echo $a;
}
?>