<?php get_header();?>

<?php $search_display = $dmeng_option['sidebar']['_search_display'];$search_number = $dmeng_option['sidebar']['_search_number']; ?>
<?php if ( $search_display=="yes-left") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($search_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>
<div itemscope itemtype="http://schema.org/ItemList">
<div class="<?php if ( $search_display=="no") { echo 'col-lg-12 col-md-12'; }else{echo 'col-lg-8 col-md-8'; } ?> content">
<div class="clean"></div>
<?php $archive_ad = $dmeng_option['advert']['_archive_ad'];if( !wp_is_mobile() && $archive_ad!==''){ echo'<div class="panel panel-default"><div class="panel-body">'.$archive_ad.'</div></div>';} ?>
<div class="panel panel-default">
  <div class="panel-heading">
	<h1 class="mr10 ml10 h4 pull-left" itemprop="headline">搜索结果 &raquo; <?php the_search_query(); ?></h1>
	<?php $shareDisplay = $dmeng_option['theme']['_share_display'];?>
	<?php
	if ( $shareDisplay=="yes") {
		if ( !wp_is_mobile() ) { ?>
			<div class="pull-right">
			<?php get_template_part( 'share' ); ?>
			</div>
	<?php
		}
	}	?>
	<div class="clean"></div>
	<div class="navbar navbar-default">
		<form class="navbar-form" role="search" method="get" id="searchform" action="<?php echo home_url( '/' ); ?>">
			<div class="input-group">
				<input type="text" class="form-control" value="<?php the_search_query(); ?>" name="s" id="s" >
				<span class="input-group-btn">
				<button type="submit" class="btn btn-default" id="searchsubmit"> 搜 索 </button>
				</span>
			</div>
		</form>
	</div>
  </div>
  <div class="panel-body">
<?php if (have_posts()) : ?>
<?php while (have_posts()) : the_post(); ?>
<?php
global $dmeng_option;
$search_keyword = $dmeng_option['seo']['_seo_search_keyword'];
if ( $search_keyword=="yes") :
    $title = get_the_title();
    $content = mb_strimwidth(strip_tags(apply_filters('the_content', $post->post_content)), 0, 300,"......");
    $keys = explode(" ",$s);
    $title = preg_replace('/('.implode('|', $keys) .')/iu','<strong class="text-danger">\0</strong>',$title);
    $content = preg_replace('/('.implode('|', $keys) .')/iu','<strong class="text-danger">\0</strong>',$content);
else :
    $title = get_the_title();
    $content = mb_strimwidth(strip_tags(apply_filters('the_content', $post->post_content)), 0, 300,"......");
    $keys = explode(" ",$s);
    $title = preg_replace('/('.implode('|', $keys) .')/iu','<span class="text-danger">\0</span>',$title);
    $content = preg_replace('/('.implode('|', $keys) .')/iu','<span class="text-danger">\0</span>',$content);
endif;
?>
	<div class="mt20 mb20 searchResults" itemprop="itemListElement">
	<h2 class="h4" itemprop="name"><a href="<?php the_permalink(); ?>" rel="bookmark" itemprop="url"><?php echo $title; ?></a></h2>
	<div itemprop="description"><?php echo $content;?></div>
	</div>
<?php endwhile; ?>
<?php else : ?>
<div class="alert alert-warning">没有找到相关内容</div>
<blockquote>
<h3 class="text-success">换个关键词试试？</h3><br/>
</blockquote>
<?php endif; ?>
  </div>
  <div class="panel-footer text-muted">
	<?php echo '“';the_search_query();echo '”为您找到结果 ';global $wp_query;echo $wp_query->found_posts;echo ' 个';?>
  </div>
</div>
<?php dmeng_paging_nav(); //分页导航?>
<?php $archive_ad2 = $dmeng_option['advert']['_archive_ad2'];if( !wp_is_mobile() && $archive_ad2!==''){ echo'<div class="panel panel-default"><div class="panel-body">'.$archive_ad2.'</div></div>';} ?>
</div>
</div>
		
<?php if ( $search_display=="yes-right") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($search_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>

<?php get_footer();?>