<footer>
	<ul>
		<li><a href="<?php echo home_url('/');?>">首页</a></li>
		<li><a href="<?php echo home_url('/?show=cat');?>">分类</a></li>
		<li><a href="<?php echo home_url('/?show=tag');?>">标签</a></li>
		<div class="clean"></div>
	</ul>
<p>&copy; <?php echo date('Y').' <a href="';bloginfo('url');echo '">';bloginfo('name');echo '</a>';?> 版权所有 <?php global $dmeng_option;$icp = $dmeng_option['theme']['_icp_text'];if (!empty($icp)) { echo '<a href="http://www.miitbeian.gov.cn/" target="_blank">'.$icp.'</a>'; } ?></p>
<p><?php echo $dmeng_option['theme']['_analytics_code']; //统计代码 ?></p>
</footer>
<?php wp_footer();?>
</body>
</html>