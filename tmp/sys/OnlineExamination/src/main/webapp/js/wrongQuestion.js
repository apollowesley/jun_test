function showTab(questionType, currentPage) {
	$.ajax({
				type : "POST",
				async : false,
				url : "WrongQuestionAction_showTypeWrongQuestion",
				data : {
					"questionType" : questionType,
					"pageNum" : currentPage
				},
				dataType : "json",
				success : function(msg) {
					var errorQues = msg.list;
					var page = msg.page;
					if (errorQues == null || errorQues.length <= 0) {
						$('#content').text("没有错题");
						$('#pageNumShown').text(0);
					} else {
						var mess = "";
						for (i = 0; i < errorQues.length; i++) {
							mess += "<tr>"
									+ "<td>"
									+ (i + 1)
									+ "</td>"
									+ "<td><div class='autoCut'>"
									+ errorQues[i].questions.title
									+ "</div></td>"
									+ "<td>"
									+ errorQues[i].correctNumber
									+ "</td>"
									+ "<td><a class='btn btn-info btn-small' type='button'"
									+ "onclick="
									+ "showWrongQuestion('"
									+ errorQues[i].questions.id
									+ "');"
									+ ">查看试题</a></td>"
									+ "<td><a class='btn btn-success btn-small' type='button' href='#remove'"
									+ "onclick=" + "showDeleteDialog('"
									+ errorQues[i].id + "');" + ">删除</a></td>"
									+ "</tr>";
						}
						$('#content').html(mess);
						$('#type').val(errorQues[0].questions.questionsType);
						$('#pageNumShown').text(page.pageNumShown);
						$('#currentPage').text(page.currentPage);
					}
				}
			});
}

function showPageQuestions(tag) {
	var pageNumShown = parseFloat($('#pageNumShown').text());
	var currentPage = parseFloat($('#currentPage').text());
	var type = $('#type').val();
	if (tag == 'first') {
		if (pageNumShown == 0) {
			return;
		}
		showTab(type, 1);
	} else if (tag == 'next') {
		if (currentPage >= pageNumShown) {
			return;
		} else {
			showTab(type, currentPage + 1);
		}
	} else if (tag == 'pre') {
		if (currentPage <= 1) {
			return;
		} else {
			showTab(type, currentPage - 1);
		}
	} else if (tag == 'end') {
		if (pageNumShown == 0) {
			return;
		}
		showTab(type, pageNumShown);
	}

}

function showDeleteDialog(id) {
	$("#deleteQuestion").val(id);
	$('#remove').modal('toggle');
}
function deleteWrongQuestion() {
	var id = $("#deleteQuestion").val();
	$.ajax({
		type : "POST",
		async : false,
		url : "WrongQuestionAction_deleteWrongQuestion",
		data : {
			"wid" : id
		},
		dataType : "json",
		success : function(msg) {
			$('#remove').modal('toggle');
			$('#resultMeg').modal('show');
			$("#hiht").text(msg);
		}
	});
}
function showWrongQuestion(qid) {
	$.ajax({
		type : "POST",
		async : false,
		url : "WrongQuestionAction_showWrongQuestion",
		data : {
			"qid" : qid
		},
		dataType : "json",
		success : function(msg) {
			if (msg.questionsType == 2) {
				$('#qtype').css('display','none');
				$('#questionAnswer').text(msg.answer == 'right' ? '正确' : '错误');
			} else if (msg.questionsType < 2) {
				$('#qtype').css('display', 'block');
				$('#answerA').text(msg.optionA);
				$('#answerB').text(msg.optionB);
				$('#answerC').text(msg.optionC);
				$('#answerD').text(msg.optionD);
				$('#questionAnswer').text(msg.answer);
			}
			$('#questionTitle').html(msg.title);
			$('#questionAnalysis').html(msg.content);
			$('#QuestionDiaolg').modal('show');
		}
	});
}