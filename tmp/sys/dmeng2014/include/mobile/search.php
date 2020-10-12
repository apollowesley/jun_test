<?php include_once('header.php');?>

<article>
<div class="header">
<h1>搜索结果 &raquo; <?php the_search_query(); ?></h1>
<p><?php echo '为您找到结果 ';global $wp_query;echo $wp_query->found_posts;echo ' 个';?></p>
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