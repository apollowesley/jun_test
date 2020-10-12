<?php include_once('header.php');?>

<?php if( $_GET['show']=='cat' ): //分类列表?>
<script>window.document.title="文章分类排行 - <?php bloginfo('name');?>";</script>
<article>
<div class="header">
<h1>文章分类排行</h1>
</div>
<?php
$tagArgs = array(
    'orderby'       => 'count', 
    'order'         => 'DESC',
    'hide_empty'    => true,
	'number'    => 0, 
);
$terms = get_terms("category",$tagArgs);
$count = count($terms);
 if ( $count > 0 ){
     echo '<ul>';
     foreach ( $terms as $term ) {
       echo '<li><a href="?cat='.$term->term_id.'">' . $term->name .'<p>共有'.$term->count.'篇文章</p></a></li>';
        
     }
     echo '</ul>';
 }
?>
</article>
<?php elseif( $_GET['show']=='tag' ): //标签列表?>
<script>window.document.title="文章标签排行 - <?php bloginfo('name');?>";</script>
<article>
<div class="header">
<h1>文章标签排行</h1>
</div>
<?php
$tagArgs = array(
    'orderby'       => 'count', 
    'order'         => 'DESC',
    'hide_empty'    => true,
	'number'    => 0, 
);
$terms = get_terms("post_tag",$tagArgs);
$count = count($terms);
 if ( $count > 0 ){
     echo '<ul>';
     foreach ( $terms as $term ) {
       echo '<li><a href="?tag='.$term->slug.'">' . $term->name .'<p>共有'.$term->count.'篇文章</p></a></li>';
        
     }
     echo '</ul>';
}
?>
</article>
<?php else : //默认首页?>
<article>
<div class="header">
<h1>最新文章</h1>
</div>
<?php

//The Query
query_posts(array('ignore_sticky_posts'=>0,'posts_per_page'=>10,'orderby'=>date,'order'=>DESC));

if ( have_posts() ) :
	echo '<ul>';
	while ( have_posts() ) : the_post();
		if(is_sticky()){ $sticky = '<span> [ 置顶 ] </span>';}else{ $sticky = '';}
		$time = '<span itemprop="datePublished" datetime="'.get_the_date('c').'"> [ '.get_the_date().' ] </span>';
		echo '<li><a href="'.get_permalink().'">';
		echo '<h2>'.get_the_title().$sticky.'</h2>';
		echo '<p>'.$time.get_the_excerpt().'</p>';
		echo '</a></li>';
	endwhile;
	echo '</ul>';
endif;

//Reset Query
wp_reset_query();

?>
<div class="header">
<h1>热议文章</h1>
</div>
<?php

//The Query
query_posts(array('ignore_sticky_posts'=>1,'posts_per_page'=>10,'orderby'=>comment_count,'order'=>DESC));

if ( have_posts() ) :
	echo '<ul>';
	while ( have_posts() ) : the_post();
		$NO = '<span> [ '.get_comments_number().'条评论 ] </span>';
		if(is_sticky()){ $sticky = '<span> [ 置顶 ] </span>';}else{ $sticky = '';}
		echo '<li><a href="'.get_permalink().'">';
		echo '<h2>'.get_the_title().$sticky.'</h2>';
		echo '<p>'.$NO.get_the_excerpt().'</p>';
		echo '</a></li>';
	endwhile;
	echo '</ul>';
endif;

//Reset Query
wp_reset_query();

?>
</article>
<?php endif; ?>

<?php include_once('footer.php');?>