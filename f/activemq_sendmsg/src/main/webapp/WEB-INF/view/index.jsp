<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<!-- jsp文件头和头部 -->
	<meta charset="utf-8" />
	<title>首页</title>
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<%@ include file="/WEB-INF/view/common/common.jsp"%>
    
</head>
<body  class="fixed-sidebar full-height-layout gray-bg" style="overflow: hidden;">

	<!-- 页面顶部¨ -->
	<%@ include file="head.jsp"%>

	<div class="container-fluid" id="main-container">
		<a href="#" id="menu-toggler"><span></span></a>
		<!-- menu toggler -->

		<!-- 左侧菜单 -->
		<%@ include file="left.jsp"%>

		<div id="main-content" class="clearfix">
			<!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="icon-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/index/login_default">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="icon-forward"></i>
                </button>
                <button class="roll-nav roll-right dropdown J_tabClose" style="font-size: 12px;"><span class="dropdown-toggle" data-toggle="dropdown">关闭操作<span class="caret"></span></span>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <!-- <li class="divider"></li> -->
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </button>
                <!-- <a href="logout" class="roll-nav roll-right J_tabExit" onclick="return PageMain.exitWiseXClient()"><i class="fa fa fa-sign-out"></i> 退出</a> -->
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe id="mainFrame" data-id="${ctx}/index/login_default" class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/index/login_default" frameborder="0" ></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2016 <a href="http://www.2b2b92b.com" target="_blank">www.2b2b92b.com</a>
                </div>
            </div>
        </div>
        <!--右侧部分结束-->

			<!-- 换肤 -->
			<div id="ace-settings-container">
				<div class="btn btn-app btn-mini btn-warning" id="ace-settings-btn">
					<i class="icon-cog"></i>
				</div>
				<div id="ace-settings-box">
					<div>
						<div class="pull-left">
							<select id="skin-colorpicker" class="hidden">
								<option data-class="default" value="#438EB9"
									<c:if test="${user.SKIN =='default' }">selected</c:if>>#438EB9</option>
								<option data-class="skin-1" value="#222A2D"
									<c:if test="${user.SKIN =='skin-1' }">selected</c:if>>#222A2D</option>
								<option data-class="skin-2" value="#C6487E"
									<c:if test="${user.SKIN =='skin-2' }">selected</c:if>>#C6487E</option>
								<option data-class="skin-3" value="#D0D0D0"
									<c:if test="${user.SKIN =='skin-3' }">selected</c:if>>#D0D0D0</option>
							</select>
						</div>
						<span>&nbsp; 选择皮肤</span>
					</div>
					<div>
						<label><input type='checkbox' name='menusf' id="menusf" onclick="menusf();" /><span class="lbl" style="padding-top: 5px;">菜单缩放</span></label>
					</div>
				</div>
			</div>
			<!--/#ace-settings-container-->

		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	
	
	<script src="<c:url value="/resources/js/ace-elements.min.js"/>"></script>
    <script src="<c:url value="/resources/js/ace.min.js"/>"></script>
</body>
</html>
