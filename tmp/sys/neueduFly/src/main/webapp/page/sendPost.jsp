<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<base href="<%=basePath %>"/>
	<title>帖子发布</title>
	<link rel="stylesheet" href="../commons/layui/css/layui.css" />
	<link rel="stylesheet" href="../style/global.css" />
	<link rel="stylesheet" href="../commons/icon-font.css" />
	<link rel="stylesheet" type="text/css" href="../style/sendPost.css" />
	<script src="../commons/layui/layui.js"></script>
</head>
<body>
	<!-- 标题 -->
	<div class="fly-header layui-bg-black">
		<div class="layui-container">
			<a class="fly-logo" href="javascript:gotoIndex();"><img src="../imgs/logo.png" alt="Fly社区" /></a>
			<ul class="layui-nav fly-nav">
				<li class="layui-nav-item layui-this"><a href="javascript:gotoIndex();"><i class="iconfont icon-jiaoliu"></i>交流</a>
				</li>
				<li class="layui-nav-item">
					<a href="javascript:;"><i class="iconfont icon-chanpin"></i>专区<span class="layui-badge-dot"></span></a>
					<dl class="layui-nav-child">
						<!-- 二级菜单 -->
						<dd><a href="javascript:;">layuiAdmin</a></dd>
						<dd><a href="javascript:;">LayIM</a></dd>
					</dl>
				</li>
				<li class="layui-nav-item"><a href="javascript:;"><i class="iconfont icon-ui"></i>框架</a></li>
			</ul>
			<ul class="layui-nav fly-nav-user">
				<li class="layui-nav-item"><a href="javascript:gotoLoginAndRegister('login');" class="iconfont icon-touxiang"></a></li>
				<li class="layui-nav-item"><a href="javascript:gotoLoginAndRegister('login');">登入</a></li>
				<li class="layui-nav-item"><a href="javascript:gotoLoginAndRegister('register');">注册</a></li>
				<li class="layui-nav-item"><a href="javascript:;" class="iconfont icon-qq"></a></li>
				<li class="layui-nav-item"><a href="javascript:;" class="iconfont icon-weibo"></a></li>
			</ul>
		</div>
	</div>

	<!-- 发表新帖 -->
	<div class="layui-container">
		<div class="lay-tabs">
			<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
				<ul class="layui-tab-title">
					<li class="layui-this">发表新帖</li>
				</ul>
				<div class="layui-tab-content layui-show">
					<form action="" class="layui-form layui-form-pane" method="">
						<div class="layui-row">
							<div class="layui-form-item">
								<label class="layui-form-label">所在专栏</label>
								<div class="layui-input-inline" pane>
									<select name="" lay-verify="">
										<option value=""></option>
										<option value="010">提问</option>
										<option value="021">分享</option>
										<option value="0571">讨论</option>
										<option value="0571">建议</option>
									</select>
								</div>
								<label class="layui-form-label">标题</label>
								<div class="layui-input-inline title-width" pane >
									<input type="text" class="layui-input" />
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">所属产品</label>
								<div class="layui-input-inline" pane>
									<select name="" lay-verify="">
										<option value=""></option>
										<option value="010">layui</option>
										<option value="021">layuiAdmin</option>
										<option value="0571">layim</option>
										<option value="0571">模板市场</option>
										<option value="0571">扩展组件</option>
									</select>
								</div>
								<label class="layui-form-label">版本</label>
								<div class="layui-input-inline" pane >
									<input type="text" class="layui-input" />
								</div>
								<label class="layui-form-label">浏览器</label>
								<div class="layui-input-inline browser-width" pane >
									<input type="text" placeholder="浏览器名称及版本,如: IE11" class="layui-input" />
								</div>
							</div>
							<div class="layui-form-item">
								<textarea id="context" placeholder="详细描述" style="display: none;"></textarea>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">悬赏</label>
								<div class="layui-input-inline" pane >
									<select name="" lay-verify="">
										<option value="010">20飞吻</option>
										<option value="021">50飞吻</option>
										<option value="0571">80飞吻</option>
										<option value="0571">100飞吻</option>
										<option value="0571">500飞吻</option>
									</select>
								</div>
								<div class="layui-form-mid layui-word-aux">发表后无法更改飞吻 <a class="tip" href="">(提升飞吻)</a></div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">图形码</label>
								<div class="layui-input-inline" pane >
									<input type="text" class="layui-input" />
								</div>
								<div class="layui-form-mid layui-word-aux">
									<img class="code" src="../imgs/imagecode.svg" alt="验证码" />
								</div>
							</div>
							<div class="layui-form-item">
								<button type="button" class="layui-btn">立即发布</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 版权声明 -->
	<div class="fly-footer">
		<p>
			<a href="">Fly 社区</a>
			2019 ©
			<a href="">layui.com</a>
		</p>
		<p>
			<a href="javascript:;">付费计划</a>
			<a href="javascript:;">组件平台</a>
			<a href="javascript:;">模板市场</a>
			<a href="javascript:;">年度案例</a>
			<a href="javascript:;">公众号</a>
		</p>
		<p>感谢以下服务商提供云加速赞助</p>
		<p>
			<a href="" class="upyun"><img src="../imgs/upyun.png" alt="又拍云" /></a>
			<a href="" class="maoyun"><img src="../imgs/168_1559291577683_9348.png" alt="猫云" /></a>
		</p>
	</div>
	<script src="../js/global.js"></script>
	<script>
		layui.use(['element', 'util', 'form','layedit'], function() {
			let element = layui.element,
				util = layui.util,
				form = layui.form,
				layedit = layui.layedit;

			//建立编辑器
			layedit.build('context', {
				height: 240, //编辑器的初始高度
				uploadImage: {url: '/upload/', type: 'post'} //上传图片的接口
			});


			//固定块实例
			util.fixbar({
				bar1: '&#xe642;',
				bgcolor: '#5FB878',
				showHeight: 300, //TOP按钮的滚动条高度临界值。默认：200
				click: function(type) {
					console.log(type);
					if (type === 'bar1') {
						alert('点击了bar1')
					}
				}
			});

		});
	</script>
</body>
</html>
