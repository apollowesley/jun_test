<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="css/testMa.css" />
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
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("ul li").click(function name() {
			$("ul li").removeClass("selected");
			$(this).addClass("selected");
		});
	});
</script>
<div class="pageContent">
	<div class="divider"></div>
	<ul id="navMenu">
		<ul>
			<li class="selected"><s:a
				action="PaperAction_doPaperQuestionsByQuesType?pid=%{model.id }&&questionsType=0"
				target="ajax" rel="questionBox"><span>单选题</span></s:a></li>
			<li><s:a
				action="PaperAction_doPaperQuestionsByQuesType?pid=%{model.id }&&questionsType=1"
				target="ajax" rel="questionBox"><span>多选题</span></s:a></li>
			<li><s:a
				action="PaperAction_doPaperQuestionsByQuesType?pid=%{model.id }&&questionsType=2"
				target="ajax" rel="questionBox"><span>判断题</span></s:a></li>
		</ul>
	</ul>
	<div id="questionBox" class="unitBox">
		<jsp:include page="quesTab.jsp" />
	</div>

</div>