<?php
/***
*
*Template Name:全宽模板
*
***/
get_header();?>

<article itemscope itemtype="http://schema.org/Article">
<div class="col-lg-12 col-md-12 content">

<?php if (have_posts()) : ?>
<?php while (have_posts()) : the_post(); ?>

<div class="panel panel-default">
  <div class="panel-heading">
	<h1 class="pull-left"><?php the_title(); ?></h1>
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
  </div>
  <div class="panel-body">
    <?php the_content(); ?>
  </div>
  <?php dmeng_link_pages(); //文章分页?>
  <div class="panel-footer">
  本文由 <?php the_author(); ?> 在 <?php the_date(' Y-m-d ');?> 发布 <?php setPostViews(get_the_ID());?> <?php echo ' 共有 '.getPostViews(get_the_ID()).' 次浏览';?> <a href="#comments"><span class="text-danger"><abbr title="内容报错"> <span class="glyphicon glyphicon-info-sign"></span> 报错</abbr></span></a>
  </div>
</div>
<ul class="pager">
  <?php previous_post_link( '<li class="previous">'.'%link', '<span>' . _x( '«', 'Previous post link' ) . '</span> %title </li>' ); ?>
  <?php next_post_link( '<li class="next">'.'%link', '%title <span>' . _x( '»', 'Next post link' ) . '</span></li>' ); ?>
</ul>
<?php endwhile; ?>
<?php endif; ?>

<div id="comments"><?php comments_template(); ?></div>

</div>
</article>

<?php get_footer();?>