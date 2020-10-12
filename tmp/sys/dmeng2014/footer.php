<div class="clean"></div>
</div>
<footer id="footer">
	<div class="col-lg-12 col-md-12">
		<div <?php if ( !wp_is_mobile() ) { echo 'class="container"'; } //非手机客户端添加固定宽度容器?>>
			<div class="panel panel-default">
			<?php if ( is_active_sidebar( 'footer-sidebar' ) ) : ?>
			  <div class="panel-body text-muted">
			  <?php dynamic_sidebar( 'footer-sidebar' ); ?>
			  </div>
			<?php endif; ?>
			  <div class="panel-footer">
				<span class="text-muted pull-left">&copy; <?php echo date('Y').' <a href="';bloginfo('url');echo '">';bloginfo('name');echo '</a>';?> 版权所有 <?php global $dmeng_option;$icp = $dmeng_option['theme']['_icp_text'];if (!empty($icp)) { echo '<a href="http://www.miitbeian.gov.cn/" target="_blank">'.$icp.'</a>'; } ?> <?php echo $dmeng_option['theme']['_analytics_code']; //统计代码 ?></span>
				<span class="text-muted pull-right"><a href="http://www.dmeng.net/category/wordpress-theme-development/">WordPress主题</a> 源自 <a href="http://www.dmeng.net/">多梦网络</a></span>
				<div class="clean"></div>
			  </div>
			</div>
		</div>
	</div>

</footer>
<?php wp_footer();?>
<?php if ( $dmeng_option['theme']['_floatButton_display']=="yes" ) { ?>
<?php if ( !wp_is_mobile() ) { ?>
<div class="btn-group-vertical floatButton">
<button type="button" class="btn btn-default" id="goTop" title="去顶部"><span class="glyphicon glyphicon-arrow-up"></span></button>
<button type="button" class="btn btn-default" id="refresh" title="刷新"><span class="glyphicon glyphicon-repeat"></span></button>
<?php if ( is_single() || is_page() ) { echo '<button type="button" class="btn btn-default" id="goComments" title="评论"><span class="glyphicon glyphicon-align-justify"></span></button>';}?>
<button type="button" class="btn btn-default" id="goBottom" title="去底部"><span class="glyphicon glyphicon-arrow-down"></span></button>
</div>
<script type="text/javascript">
jQuery(document).ready(function($){
	$('#goTop').click(function(){$('html,body').animate({scrollTop: '0px'}, 800);});
	$('#goBottom').click(function(){$('html,body').animate({scrollTop:$('#footer').offset().top}, 800);});
	$('#goComments').click(function(){$('html,body').animate({scrollTop:$('#comments').offset().top}, 800);});
	$('#refresh').click(function(){location.reload();});
});
</script>
<?php } ?><?php } ?>
</body>
</html>