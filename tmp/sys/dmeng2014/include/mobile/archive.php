<?php include_once('header.php');?>

<article>
<div class="header">
<h1><?php
					if ( is_day() ) :
						echo '日期归档 &raquo; '.get_the_date('Y / m / d');
					elseif ( is_month() ) :
						echo '月份归档 &raquo; '.get_the_date('Y / m');
					elseif ( is_year() ) :
						echo '年份归档 &raquo; '.get_the_date('Y');
					elseif ( is_author() ) :
						echo '作者归档 &raquo; ';the_author();
					else :
						_e( '归档', 'twentytwelve' );
					endif;
				?></h1>
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