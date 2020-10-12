<?php get_header();?>

<?php $tag_display = $dmeng_option['sidebar']['_tag_display'];$tag_number = $dmeng_option['sidebar']['_tag_number']; ?>
<?php if ( $tag_display=="yes-left") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($tag_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>

<div class="<?php if ( $tag_display=="no") { echo 'col-lg-12 col-md-12'; }else{echo 'col-lg-8 col-md-8'; } ?> content">
<div class="clean"></div>
<?php $archive_ad = $dmeng_option['advert']['_archive_ad'];if( !wp_is_mobile() && $archive_ad!==''){ echo'<div class="panel panel-default"><div class="panel-body">'.$archive_ad.'</div></div>';} ?>
<div class="panel panel-default">
  <div class="panel-heading">
	<h1 class="pull-left"><?php single_tag_title('标签归档 &raquo; '); ?></h1>
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
	<?php echo tag_description();?>
  </div>
  <div class="panel-body">
<?php if (have_posts()) : ?>
<?php while (have_posts()) : the_post(); ?>
<h2 class="h4"><a href="<?php the_permalink(); ?>" rel="bookmark"><?php the_title(); ?></a></h2>
    <?php the_excerpt(); ?>
<?php endwhile; ?>
<?php else : ?>
<div class="alert alert-warning">该分类暂无文章</div>
<blockquote>
<h3>看看其他分类</h3>
<?php wp_list_categories('orderby=name&show_count=1&hide_empty=0&style=none'); ?>
</blockquote>
<?php endif; ?>
  </div>
  <div class="panel-footer text-muted">
	<?php echo '“';single_tag_title('');echo '”标签为您找到结果 '; global $wp_query; echo $wp_query->found_posts; echo ' 个';?>
  </div>
</div>
<?php dmeng_paging_nav(); //分页导航?>
<?php $archive_ad2 = $dmeng_option['advert']['_archive_ad2'];if( !wp_is_mobile() && $archive_ad2!==''){ echo'<div class="panel panel-default"><div class="panel-body">'.$archive_ad2.'</div></div>';} ?>
</div>
		
<?php if ( $tag_display=="yes-right") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($tag_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>

<?php get_footer();?>