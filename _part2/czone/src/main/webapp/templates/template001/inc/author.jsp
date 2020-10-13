<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="sidebar-module">
	<div class="widget">
		<div class="feed-about">
			<div class="about-img">
				<img src="${templatePath}img/wx_300px.png" class="" alt="微信公众号">
			</div>
			<div class="about-name">${authorInfo.nickName }</div>
			<div class="about-the">${authorInfo.sign }</div>
			<div class="clear"></div>
		</div>
	</div>
</div>
<div class="sidebar-module">
	<h5 class="sidebar-title">
		<i class="fa fa-home icon"></i><strong>关于我</strong>
	</h5>
	<div class="widget">
		<div class="feed-about">
			<ul class="list-unstyled about-list">
				<li><i class="fa fa-info-circle fa-fw"></i>博主：<small>${authorInfo.nickName }</small></li>
				<li><i class="fa fa-user-circle fa-fw"></i>职业：<small>${authorInfo.occupation }</small></li>
				<li><i class="fa fa-home fa-fw"></i>籍贯：<small>${authorInfo.address }</small></li>
				<li><i class="fa fa-envelope-square fa-fw"></i>邮箱：<small>${authorInfo.email }</small></li>
			</ul>
		</div>
	</div>
</div>