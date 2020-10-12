<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>所有试卷</title>
<script type="text/javascript" src="customJs/templateTree.js"></script>
</head>
<style type="text/css">
ul.rightTools {
	float: right;
	display: block;
}

ul.rightTools li {
	float: left;
	display: block;
	margin-left: 5px
}
.tree div {
	display: inline-block;
	white-space: nowrap;
	overflow: visible;
	float: none;
	height: 22px;
	width: 100%;
	word-wrap: normal;
}
</style>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			questionTemplateTree('questionTable');
		});
	</script>

	<div class="pageContent" style="padding: 5px">
		<!-- 	<div class="divider"></div> -->
		<div layoutH="20"
			style="float: left; display: block; overflow: auto; width: 150px; border: solid 1px #CCC; line-height: 21px; background: #fff">
			<div>
				<ul class="tree treeFolder expand" id="questionTables">
					<li><a href="javascript">题型选择</a>
						<ul>
							<li><a href="QuestionAction_doTopic?questionsType=0"
								target="ajax" rel="jbsxBox">单选题单选题单选题单选题单选题</a>
								<ul>
									<li><a href="QuestionAction_doTopic?questionsType=0"
										target="ajax" rel="questionTable">单选题</a></li>
								</ul></li>
							<li><a href="QuestionAction_doTopic?questionsType=1"
								target="ajax" rel="questionTable">多选题</a></li>
							<li><a href="QuestionAction_doTopic?questionsType=2"
								target="ajax" rel="questionTable">判断题</a></li>
						</ul></li>
				</ul>
				<input id="pid" type="hidden" value="${pid }">
			</div>
		</div>
		<div id="questionTable" class="unitBox" style="margin-left: 156px;">
		</div>
	</div>
</body>
</html>


