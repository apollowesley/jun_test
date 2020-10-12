<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>成都工业学院在线开始系统后台</title>

<!-- 引入css样式 -->
<link href="themes/default/style.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="themes/css/core.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="themes/css/print.css" rel="stylesheet" type="text/css"
	media="print" />
<link rel="stylesheet" type="text/css" href="uploadify/css/uploadify.css" /> 

<!-- [if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]

[if lte IE 9]>
<script src="js/speedup.js" type="text/javascript"></script>
<![endif] -->

<!-- 引入外部支持的库 -->
<script src="js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="js/jquery.cookie.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="uploadify/scripts/jquery.uploadify.js"
	type="text/javascript"></script>


<script src="bin/dwz.min.js" type="text/javascript"></script>

<script src="js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="js/time.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		DWZ.init("dwz.frag.xml", {
			loginUrl : "latar/login_dialog.jsp",
			loginTitle : "登录", // 弹出登录对话框
			//		loginUrl:"login.html",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			}, //【可选】
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "themes"
				}); // themeBase 相对于index页面的主题base路径
			}
		});
	});
</script>
</head>

<body scroll="no">
	<div id="layout">

		<!-- 上方的盒子 -->
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="http://www.cdtu.edu.cn">标志</a>
				<ul class="nav">
					<li><s:a action="">你好:${sessionScope.adminUser.name}</s:a></li>
					<li><s:a action="">修改密码</s:a></li>
					<li><s:a action="LoginAction_doLoginOut?loginType=latar">退出</s:a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>

			<!-- navMenu -->

		</div>
		<!-- 左边的菜单栏 -->
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>主菜单</h2>
					<div>收缩</div>
				</div>
				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>试卷管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a>模拟考试</a>
								<ul>
									<li><s:a action="DictionaryAction_classifyTag" target="navTab" rel="classifyTab">试卷类别</s:a></li>
									<li><s:a action="PaperAction_findConditionPaper" target="navTab" rel="paperTab" >试卷列表</s:a></li>
								</ul></li>
						<li><a>期末考试</a>
								<ul>
									<li><s:a action="FormalTestAction_toFormalTestManangePage" target="navTab" rel="formalTestTab">期末试卷</s:a></li>
									<li><s:a action="PaperAction_findConditionPaper" target="navTab" rel="paperTab" >考场管理</s:a></li>
								</ul></li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>试题管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a>模板管理</a>
								<ul>
									<li><s:a
											action="QuestionTemplateAction_toQuestionTemplatePage"
											target="navTab" rel="tmeplateTab">试题模板</s:a></li>
									<li><s:a action="QuestionAction_toQuestionStatisticsPage"
											target="navTab" rel="questionStatisticsTab">试题统计</s:a></li>
									<li><s:a action="QestionsExcelAction_toImporteExportPage"
											target="navTab" rel="importExportTab">导入导出</s:a></li>
								</ul></li>
							<li><a>题目管理</a>
								<ul>
									<li><s:a action="QuestionAction_toQuestionsContentPage"
											target="navTab" rel="questionsTab">试题列表</s:a></li>
									<li><s:a action="QuestionAction_toQuestionIndexPage"
											target="navTab" rel="questionsIndex">试题索引</s:a></li>
								</ul></li>
						</ul>
					</div>
			<%-- 		<div class="accordionHeader">
						<h2>
							<span>Folder</span>考试管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><s:a action="ExamTypeAction_toExamTypeManagePage" target="navTab" rel="examTypeTab">分类标签管理</s:a></li>
							<li><s:a action="ExamAction_toExamManagePage" target="navTab" rel="examTab">考试列表</s:a></li>
						</ul>
					</div> --%>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>成绩管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><a>模拟考试</a>
								<ul>
									<li><s:a action="GradeAction_toGradePage" target="navTab" rel="gradeTab">成绩管理</s:a></li>
									<li><s:a action="AnalyzeAction_toAnsWerAnalyzePage" target="navTab" rel="gradestaTab">成绩分析和统计</s:a></li>
								</ul>	
							</li>
							<li><a>期末考试</a>
								<ul>
									<li><s:a action="GradeAction_toGradePage?formalTest=true" target="navTab" rel="gradeMessTab">成绩详情</s:a></li>
									<li><s:a action="AnalyzeAction_toAnsWerAnalyzePage?formalTest=true" target="navTab" rel="statisticsTab">分析和统计</s:a></li>
								</ul>	
							</li>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>前台用户
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><s:a action="UserAction_toUsersPage" target="navTab" rel="userTab">用户管理</s:a></li>		
						</ul>
					</div>
					<div class="accordionHeader">
						<h2>
							<span>Folder</span>系统管理
						</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<li><s:a action="AdminUserAction_toAdminUserManagePage" target="navTab" rel="adminUserTab">管理员管理</s:a></li>
							<li><s:a action="RoleAction_toRoleManagePage" target="navTab" rel="roleTab">角色管理</s:a></li>
							<li><s:a action="RightAction_toRightManagePage" target="navTab" rel="rightTab">权限管理</s:a></li>
							<li><s:a action="LogAction_toLogManagePage" target="navTab" rel="logTab" >系统日志</s:a></li>
						</ul>
					</div>
				
				</div>
			</div>
		</div>
		<!-- 类容区 -->
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<div class="alertInfo">
								<h2>
									<a href="doc/dwz-user-guide.pdf" target="_blank">DWZ框架使用手册(PDF)</a>
								</h2>
								<a href="doc/dwz-user-guide.swf" target="_blank">DWZ框架演示视频</a>
							</div>
							<div class="right">
								<p>
									<a href="doc/dwz-user-guide.zip" target="_blank"
										style="line-height: 19px">DWZ框架使用手册(CHM)</a> <a
										href="latar/testMa.html" target="_blank">所有试卷</a>
								</p>
								<p>
									<a href="doc/dwz-ajax-develop.swf" target="_blank"
										style="line-height: 19px">DWZ框架Ajax开发视频教材</a>
								</p>
							</div>
							<p>
								<span>DWZ富客户端框架</span>
							</p>
							<p>
								DWZ官方微博:<a href="http://weibo.com/dwzui" target="_blank">http://weibo.com/dwzui</a>
							</p>
						</div>
						<div class="pageFormContent" layoutH="80">
							<h1 align="center">我的首页</h1>
						</div>

						<div style="width: 230px; position: absolute; top: 60px; right: 0"
							layoutH="80">
							<iframe width="100%" height="430" class="share_self"
								frameborder="0" scrolling="no"
								src="http://widget.weibo.com/weiboshow/index.php?width=0&height=430&fansRow=2&ptype=1&skin=1&isTitle=0&noborder=1&isWeibo=1&isFans=0&uid=1739071261&verifier=c683dfe7"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<!------ extra ----->
		<p>Copyright © 2014 成都工业学院</p>
	</div>
</body>
</html>