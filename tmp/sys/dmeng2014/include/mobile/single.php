<?php include_once('header.php');?>

<article>
<?php
if ( have_posts() ) :
	while ( have_posts() ) : the_post();
	?>
	<div class="header">
		<h1><?php the_title();?></h1>
	<p class="text-muted">
  <time itemprop="datePublished" datetime="<?php echo get_the_date('c'); ?>"><?php echo get_the_date(); ?></time>发布<?php echo ' 共有'.getPostViews(get_the_ID()).'次浏览';?>
	</p>
	</div>
	<div class="content">
<?php if(is_attachment()){ 
$post = get_post();
if ( preg_match( '/^.*?\.(\w+)$/', get_attached_file( $post->ID ), $matches ) ) { $filetype = esc_html( strtoupper( $matches[1] ) ); }
else { $filetype = strtoupper( str_replace( 'image/', '', $post->post_mime_type ) ); }
$filename = esc_html( wp_basename( $post->guid ) );?>
<pre>文件类型 <span><?php echo $filetype;?></span> <br/>文件名称 <span><?php echo $filename;?></span></pre>
<?php } ?>
		<?php the_content();?>
	</div>
<?php dmeng_link_pages(); //文章分页?>
  <div class="footer">
	<div class="text-muted">
  本文由 <span itemprop="author"><?php the_author(); ?></span> 在 <time itemprop="datePublished" datetime="<?php echo get_the_date('c'); ?>"><?php echo get_the_date(); ?></time> 发布<?php if(!is_attachment()){?>在 <span itemprop="articleSection"><?php the_category('，'); ?></span> <?php the_tags('贴了标签 <span itemprop="keywords">', '，', '</span>'); ?> <?php } ?><?php setPostViews(get_the_ID());?> <?php echo ' 共有 '.getPostViews(get_the_ID()).' 次浏览';?>
	</div>
  </div>
<?php if(!is_attachment()){?>
<ul class="pager">
  <?php previous_post_link( '<li class="previous">'.'%link', '<span>' . _x( '上一篇：', 'Previous post link' ) . '</span> %title </li>', $in_same_cat = true ); ?>
  <?php next_post_link( '<li class="next">'.'%link', '<span>' . _x( '下一篇：', 'Next post link' ) . '</span> %title </li>', $in_same_cat = true ); ?>
</ul>
<?php } ?>
	<?php
	endwhile;
endif;
?>
</article>

<?php if(!is_attachment()){?>
<div id="comments"><?php comments_template('/include/mobile/comments.php'); ?></div>
<?php } ?>

<?php include_once('footer.php');?>