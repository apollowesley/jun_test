<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="content_left fl">
		<div id="menu">
			<ul>
				<li><span class="menu_title">我的考试<i
						class="triangle-down"></i></span>
					<ul>
						<s:iterator var="t" value="#application['paperTypes']">
							<li id="paper${t.getValue() }"><a
								href="FrontendPaperAction_toPaperPage?paperType=${t.getValue() }">${t.getName() }</a></li>
						</s:iterator>
					<!-- 	<li id="all"><a href="FrontendPaperAction_toPaperPage">全部试卷 -->
						</a></li>
					</ul></li>
				<li><span class="menu_title">成绩记录<i
						class="triangle-down"></i></span>
					<ul>
						<li id="query"><a href="FrontendPaperAction_showPaperType">成绩查询</a></li>
						<li id="error"><a href="WrongQuestionAction_toMyWrongQuestionsPage">错题库</a></li>
						<li id="finalExam"><a href="FrontendGradeAction_toFormalTestGradePage">期末考试成绩</a></li>
					</ul></li>
				<li><span class="menu_title">个人中心<i
						class="triangle-down"></i></span>
					<ul>
						<li id="userInfo"><a href="FrontendUserAction_toUserinfoPage">个人信息</a></li>
						<li id="changepass"><a href="FrontendUserAction_toChangepassPage">修改密码</a></li>
					</ul></li>
				<li><span class="menu_title">在线交流<i
						class="triangle-down"></i></span>
					<ul>
						<li><a href="#">在线提问区</a></li>
						<li><a href="#">在线讨论区</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</body>
</html>