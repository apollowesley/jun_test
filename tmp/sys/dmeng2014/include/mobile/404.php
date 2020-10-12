<?php include_once('header.php');?>

<article>
	<div class="header">
		<h1>未找到页面</h1>
	</div>
	<div class="content">
	<blockquote>
		<ul>
			<li><h3>可能导致的原因</h3>
				<ol>
					<li>输入的链接有误</li>
					<li>请求的页面不存在</li>
				</ol>
			</li>
			<br>
			<li><h3>推荐进行的操作</h3>
				<ol>
					<li><a href="<?php echo home_url( '/' ); ?>">返回首页</a></li>
					<li>使用相关关键词进行搜索</li>
				</ol>
			</li>
		</ul>
	</blockquote>
	</div>
  <div class="footer">
	<div class="text-muted">404 ERROR</div>
  </div>
</article>

<?php include_once('footer.php');?>