<?php get_header();?>

<?php $home_display = $dmeng_option['sidebar']['_home_display'];$home_number = $dmeng_option['sidebar']['_home_number']; ?>
<?php if ( $home_display=="yes-left") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($home_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>
<div class="<?php if ( $home_display=="no") { echo 'col-lg-12 col-md-12'; }else{echo 'col-lg-8 col-md-8'; } ?> content">

<div class="panel panel-warning">
  <div class="panel-heading">
	<h1 class="h3"><?php wp_title(); ?></h1>
  </div>
  <div class="panel-body">
	<blockquote>
		<ul>
			<li><h3>可能导致的原因</h3>
				<ol>
					<li>输入的链接有误</li>
					<li>请求的页面不存在</li>
				</ol>
			</li>
			<br/>
			<li><h3>推荐进行的操作</h3>
				<ol>
					<li><a href="<?php echo home_url('/'); ?>" class="btn btn-success">返回首页</a></li>
					<li>使用相关关键词进行搜索</li>
				</ol>
			</li>
		</ul>
		<div class="navbar navbar-default mt20">
		<form class="navbar-form" role="search" method="get" id="searchform" action="<?php echo home_url( '/' ); ?>">
			<div class="input-group">
				<input type="text" class="form-control" name="s" id="s" >
				<span class="input-group-btn">
				<button type="submit" class="btn btn-default" id="searchsubmit"> 搜 索 </button>
				</span>
			</div>
		</form>
		</div>
<?php
$the_query = new WP_Query( array( 'posts_per_page' => 10, 'orderby' => 'rand') );
if ( $the_query->have_posts() ) { ?>
<ul class="mt20">
<li><h3>你可能感兴趣的</h3>
	<ol>
<?php
	while ( $the_query->have_posts() ) {
		$the_query->the_post();
		echo '<li><a href="';
		the_permalink();
		echo '">';
		the_title();
		echo '</a></li>';
	}
?>
	</ol>
</ul>
<?php
}
wp_reset_postdata();
?>
	</blockquote>
  </div>
</div>

</div>
	
<?php if ( $home_display=="yes-right") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($home_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>

<?php get_footer();?>