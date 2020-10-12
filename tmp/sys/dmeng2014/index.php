<?php get_header();?>
<?php setPostViews('201301140918');//保存首页流量数据?>
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

<?php $slide_display = $dmeng_option['theme']['_slide_display'];if ( $slide_display=="yes") { ?>
<div id="carousel-example-generic" class="carousel slide mb20" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
<?php
$slide_number = $dmeng_option['theme']['_slide_number'];
$num = $slide_number-1;
for ($i = 1 ; $i <= $num; $i++)
{ ?>
    <li data-target="#carousel-example-generic" data-slide-to="<?php echo $i;?>"></li>
<?php } ?>
  </ol>
  <!-- Wrapper for slides -->
  <div class="carousel-inner">
          <div class="item active">
			<?php if (!empty ( $dmeng_option['slide']['_slide_a1_url'] )) { ?>
			<a href="<?php echo $dmeng_option['slide']['_slide_a1_url'];?>" target="_blank"><img src="<?php echo $dmeng_option['slide']['_slide_a1_img'];?>" alt="<?php echo $dmeng_option['slide']['_slide_a1_title'];?>" /></a>
			<?php }else{ ?>
			<img src="<?php echo $dmeng_option['slide']['_slide_a1_img'];?>" alt="<?php echo $dmeng_option['slide']['_slide_a1_title'];?>" />
			<?php } ?>
            <div class="carousel-caption">
              <?php if (!empty ( $dmeng_option['slide']['_slide_a1_title'] )) { echo '<h3>'.$dmeng_option['slide']['_slide_a1_title'].'</h3>'; } ?>
              <?php if (!empty ( $dmeng_option['slide']['_slide_a1_desc'] )) { echo '<p>'.$dmeng_option['slide']['_slide_a1_desc'].'</p>'; } ?>
            </div>
          </div>
<?php
$num = $slide_number-1;
for ($i = 1 , $inum = $i+1 ; $i <= $num; $i++ ,$inum++)
{ ?>
          <div class="item">		
			<?php if (!empty ( $dmeng_option['slide']['_slide_a'.$inum.'_url'] )) { ?>
			<a href="<?php echo $dmeng_option['slide']['_slide_a'.$inum.'_url'];?>" target="_blank"><img src="<?php echo $dmeng_option['slide']['_slide_a'.$inum.'_img'];?>" alt="<?php echo $dmeng_option['slide']['_slide_a'.$inum.'_title'];?>" /></a>
			<?php }else{ ?>
			<img src="<?php echo $dmeng_option['slide']['_slide_a'.$inum.'_img'];?>" alt="<?php echo $dmeng_option['slide']['_slide_a'.$inum.'_title'];?>" />
			<?php } ?>
            <div class="carousel-caption">
              <?php if (!empty ( $dmeng_option['slide']['_slide_a'.$inum.'_title'] )) { echo '<h3>'.$dmeng_option['slide']['_slide_a'.$inum.'_title'].'</h3>'; } ?>
              <?php if (!empty ( $dmeng_option['slide']['_slide_a'.$inum.'_desc'] )) { echo '<p>'.$dmeng_option['slide']['_slide_a'.$inum.'_desc'].'</p>'; } ?>
            </div>
          </div>
<?php } ?>
  </div> 
  
<?php $slide_nav_display = $dmeng_option['theme']['_slide_nav_display'];if ( $slide_nav_display !== "no" ) { ?>
  <!-- Controls -->
  <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev" <?php if ( $slide_nav_display=="just-arrow"){echo 'style="background:none; "';} ?>>
    <span class="glyphicon glyphicon-chevron-left"></span>
  </a>
  <a class="right carousel-control" href="#carousel-example-generic" data-slide="next" <?php if ( $slide_nav_display=="just-arrow"){echo 'style="background:none; "';} ?>>
    <span class="glyphicon glyphicon-chevron-right"></span>
  </a>
<?php } ?> 
  
</div>
<?php } ?> 

	<div class="media mb20">
		<?php if (!empty ( $dmeng_option['theme']['_headline_img'] )) { ?>
        <a class="pull-left" href="<?php echo $dmeng_option['theme']['_headline_url'];?>">
          <img class="media-object" alt="<?php echo $dmeng_option['theme']['_headline_title'];?>" src="<?php echo $dmeng_option['theme']['_headline_img'];?>" />
        </a>
		<?php } ?>
        <div class="media-body">
          <?php if (!empty ( $dmeng_option['theme']['_headline_title'] )) { echo '<h4 class="media-heading"><a href="'.$dmeng_option['theme']['_headline_url'].'" target="_blank">'.$dmeng_option['theme']['_headline_title'].'</a></h4>'; } ?>
          <?php echo $dmeng_option['theme']['_headline_desc'];?>
        </div>
    </div>

<?php 
$sticky_display = $dmeng_option['theme']['_sticky_display'];
$sticky_arrange = $dmeng_option['theme']['_sticky_arrange'];
$sticky_number = $dmeng_option['theme']['_sticky_number'];
//定义置顶文章框架大小
$sticky_arrange_display = 'col-lg-4';
if ( $sticky_arrange == 'auto') { 
	if ( $sticky_number == '1') { $sticky_arrange_display = 'col-lg-12'; }
	elseif ( $sticky_number == '2') { $sticky_arrange_display = 'col-lg-6'; }
	elseif ( $sticky_number == '3') { $sticky_arrange_display = 'col-lg-4'; }
	elseif ( $sticky_number == '4') { $sticky_arrange_display = 'col-lg-6'; }
}else{ $sticky_arrange_display = $sticky_arrange; }
//根据框架大小定义缩略图高度
if ( $sticky_arrange_display == 'col-lg-12') { $sticky_height = 'style="height:auto;"'; }
elseif ( $sticky_arrange_display == 'col-lg-6') { $sticky_height = 'style="height:160px;"'; }
//判断置顶文章是否显示
if ( $sticky_display=="yes") { ?>
<div itemscope itemtype="http://schema.org/ItemList">
<?php
$args = array(
'posts_per_page' => $sticky_number,
'post__in' => get_option('sticky_posts'),
'caller_get_posts' => 1
);
query_posts($args);
if (have_posts()) : while (have_posts()) : the_post(); ?>
<div class="<?php echo $sticky_arrange_display;?> col-md-6 col-sm-6 mb20">
	<div class="thumbnail">
      <div class="stickyImg" <?php echo $sticky_height; ?> ><?php echo dmeng_the_thumbnail('','');?></div>
      <div class="caption">
		<div class="stickyContent">
        <h3 class="stickyTitle"><a href="<?php the_permalink(); ?>" title="<?php the_title(); ?>"><span itemprop="itemListElement"><?php the_title(); ?></span></a></h3>
        <div class="stickyDesc" itemprop="description"><?php the_excerpt(); ?></div>
		</div>
        <a href="<?php the_permalink(); ?>#comments" class="btn btn-primary mr10" role="button" target="_blank">评论</a> <a itemprop="url" href="<?php the_permalink(); ?>" class="btn btn-default" role="button" target="_blank">阅读全文</a>
      </div>
    </div>
</div>
<?php
   endwhile;
endif;
wp_reset_query();
?>
</div>
<?php } ?>

<?php
$excludeCat = $dmeng_option['theme']['_exclude_cat'];
$displayCat = $dmeng_option['theme']['_display_cat'];
$arrangeCat = $dmeng_option['theme']['_arrange_cat'];
$displayPostViews = $dmeng_option['theme']['_display_post_views'];
if ( $displayCat=="yes") :
$categories = get_categories("exclude=$excludeCat");
foreach ($categories as $cat) {
$catid = $cat->cat_ID;
query_posts("showposts=4&cat=$catid"); ?>
<?php if($arrangeCat=='col-lg-12'){echo '<div class="clean"></div>';} ?>
<div class="<?php echo $arrangeCat; ?> col-md-6 col-sm-6 mb20 indexCat" itemscope itemtype="http://schema.org/ItemList">
	<div class="panel panel-default">
	  <div class="panel-heading">
		<h3 class="panel-title" itemprop="name"><a href="<?php echo get_category_link($catid);?>"><?php single_cat_title(); ?></a></h3>
	  </div>
	  <ul class="list-group">
 
<?php while (have_posts()) : the_post(); ?>
<a itemprop="url" href="<?php the_permalink() ?>" title="<?php the_title();?>" class="list-group-item">
<?php if( $displayPostViews=='yes' ){ echo '<span class="badge ml5" title="'.getPostViews(get_the_ID()).'次浏览">'.getPostViews(get_the_ID()).'</span>'; } ?>
<span itemprop="itemListElement"><?php the_title();?></span> 
</a>
<?php endwhile; ?>
	  </ul>
	</div>
</div>
<?php wp_reset_query(); } ?>
<?php endif; ?>


<?php
$displayLatestPost = $dmeng_option['theme']['_display_latest_post'];
$ignore_sticky_posts = $dmeng_option['theme']['_ignore_sticky_posts'];
$latestPostTime = $dmeng_option['theme']['_latest_post_time'];
$latestPostTitle = $dmeng_option['theme']['_latest_post_title'];
$latestPostPagination = $dmeng_option['theme']['_latest_post_pagination'];
$excludePostCat = $dmeng_option['theme']['_exclude_post_cat'];
$latestPostExcerpt = $dmeng_option['theme']['_latest_post_excerpt'];
$latestPostThumbnail = $dmeng_option['theme']['_latest_post_thumbnail'];
$paged = get_query_var('paged');
if ( $displayLatestPost=="yes") :
$r = new WP_Query( apply_filters( 'widget_posts_args', array( 'cat' => $excludePostCat, 'paged' => $paged, 'no_found_rows' => true, 'post_status' => 'publish', 'ignore_sticky_posts' => $ignore_sticky_posts, ) ) );
if ($r->have_posts()) {
?>
<!-- <?php echo $dmeng_option['theme']['_ignore_sticky_posts'];?> -->
<div class="clean"></div>
<div class="col-lg-12 col-md-12 col-sm-12 mb20 latestpostBox" itemscope itemtype="http://schema.org/ItemList">
	<div class="panel panel-default">
	<?php if(!empty($latestPostTitle)){?>
		<div class="panel-heading">
			<h3 class="panel-title" itemprop="name"><?php echo $latestPostTitle;?></h3>
		</div>
	<?php } ?>
		<ul class="list-group latestpost">
		<?php while ( $r->have_posts() ) : $r->the_post(); ?>
			<a href="<?php the_permalink(); ?>" class="list-group-item" itemprop="url">
			<?php if($latestPostThumbnail=='yes'){ echo dmeng_the_thumbnail('thumbnail',''); }?>
			<?php if($latestPostExcerpt=='yes'){?>
				<?php if(is_sticky()){ ?>
					<span class="badge pull-right ml5 sticky">置 顶</span>
				<?php }else{ ?>
					<?php if ( $latestPostTime=="yes") {?><span class="badge pull-right ml5" title="<?php echo get_the_date(); ?>" itemprop="datePublished" datetime="<?php echo get_the_date('c'); ?>"><?php echo get_the_date('m-d'); ?></span><?php } ?>
				<?php } ?>
				<div class="list-group-item-heading h4" itemprop="itemListElement"><?php the_title();?> <?php echo recommend(); ?></div>
				<div itemprop="description"><?php the_excerpt(); ?></div>
			<?php }else{ ?>
				<?php if ( $latestPostTime=="yes") :?><span class="badge pull-right ml5" title="<?php echo get_the_date(); ?>" itemprop="datePublished" datetime="<?php echo get_the_date('c'); ?>"><?php echo get_the_date('m-d'); ?></span><?php endif; ?>
				<span itemprop="itemListElement"><?php the_title();?></span> <?php echo recommend(); ?> <?php if(is_sticky()){ echo '<span class="label label-success">置顶</span>';}; ?>
			<?php } ?>
			</a>
		<?php endwhile; ?>
		</ul>
	</div>
</div>
<?php } wp_reset_query();?>
<?php if($latestPostPagination=="yes") { dmeng_paging_nav(); } //分页导航 ?>
<?php endif; ?>
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