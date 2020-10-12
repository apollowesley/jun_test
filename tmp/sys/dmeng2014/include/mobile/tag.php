<?php include_once('header.php');?>

<article>
<div class="header">
<h1><?php single_tag_title('标签归档 &raquo; '); ?></h1>
<?php echo tag_description();?>
</div>
<ul>
<?php
if ( have_posts() ) :
	while ( have_posts() ) : the_post();
		echo '<li><a href="'.get_permalink().'">';
		echo '<h2>'.get_the_title().'</h2>';
		the_excerpt();
		echo '</a></li>';
	endwhile;
endif;
?>
</ul>
</article>
<?php dmeng_paging_nav(); //分页导航?>

<?php include_once('footer.php');?>