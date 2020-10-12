<?php get_header();?>

<?php $post_display = $dmeng_option['sidebar']['_post_display'];$post_number = $dmeng_option['sidebar']['_post_number']; ?>
<?php if ( $post_display=="yes-left") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($post_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>

<article itemscope itemtype="http://schema.org/Article">
<div class="<?php if ( $post_display=="no") { echo 'col-lg-12 col-md-12'; }else{echo 'col-lg-8 col-md-8'; } ?> content">
<div class="clean"></div>
<?php if (have_posts()) : ?>
<?php while (have_posts()) : the_post(); ?>
<div class="panel panel-default">
  <div class="panel-heading">
	<h1 class="pull-left" itemprop="name"><?php the_title(); ?></h1>
	<?php 
	$shareDisplay = $dmeng_option['theme']['_share_display'];
	$breadcrumbsDisplay = $dmeng_option['theme']['_breadcrumbs_display'];
	?>
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
  </div>
<?php if ( $breadcrumbsDisplay=="yes-top") {?>
  <ol class="breadcrumb">
	<li><a href="<?php echo home_url( '/' ); ?>" title="返回首页"><span class="glyphicon glyphicon-home"></span></a></li>
	<li><?php the_category('，'); ?></li>
	<li><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></li>
  </ol>
<?php } ?>
<?php $single_ad = $dmeng_option['advert']['_single_ad'];if ( !wp_is_mobile() && $single_ad!==''){ echo'<div style="border-bottom:1px solid #ddd;">'.$single_ad.'</div>';} ?>
  <div itemprop="articleBody" class="panel-body">
    <?php the_content(); ?>
  </div>
  <?php dmeng_link_pages(); //文章分页?>
<?php if ( $breadcrumbsDisplay=="yes-bottom") {?>
  <ol class="breadcrumb">
	<li><a href="<?php echo home_url( '/' ); ?>" title="返回首页"><span class="glyphicon glyphicon-home"></span></a></li>
	<li><?php the_category('，'); ?></li>
	<li><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></li>
  </ol>
<?php } ?>
<?php $single_ad2 = $dmeng_option['advert']['_single_ad2'];if ( !wp_is_mobile() && $single_ad2!==''){ echo'<div style="border-top:1px solid #ddd;">'.$single_ad2.'</div>';} ?>
  <div class="panel-footer">
	<div class="text-muted">
  本文由 <span itemprop="author"><?php the_author(); ?></span> 在 <time itemprop="datePublished" datetime="<?php echo get_the_date('c'); ?>"><?php echo get_the_date(); ?></time> 发布在 <span itemprop="articleSection"><?php the_category('，'); ?></span> <?php the_tags('贴了标签 <span itemprop="keywords">', '，', '</span>'); ?> <?php setPostViews(get_the_ID());?> <?php echo ' 共有 '.getPostViews(get_the_ID()).' 次浏览';?> 如果发现内容有误请告知我们，我们将及时更正，<?php bloginfo('name');?>因有你而美好。 <a href="#comments"><span class="text-danger"><abbr title="内容报错"><span class="glyphicon glyphicon-info-sign"></span> 报错</span></abbr></a>
	</div>
  </div>
</div>
<ul class="pager">
  <?php previous_post_link( '<li class="previous">'.'%link', '<span>' . _x( '«', 'Previous post link' ) . '</span> %title </li>', $in_same_cat = true ); ?>
  <?php next_post_link( '<li class="next">'.'%link', '%title <span>' . _x( '»', 'Next post link' ) . '</span></li>', $in_same_cat = true ); ?>
</ul>
<?php endwhile; ?>
<?php endif; ?>
<div id="comments"><?php comments_template(); ?></div>
</div>
</article>
<?php if ( $post_display=="yes-right") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($post_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>

<?php get_footer();?>