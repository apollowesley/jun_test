var tree="";
function questionTemplateTree(treeMess) {
	$.ajax({
		type : "POST",
		async : false,
		url : "QuestionTemplateAction_showQuestionTemplate",
		dataType : "json",
		success : function(msg) {
			if (msg == null || msg.length <= 0) {
				if (treeMess == 'eventTree') {
					$('#eventTree').text("没有类容");
				}else if('questionTable'==treeMess){
					$('#questionTable').text("没有类容");
				}else{
					$('#questionsList').text("没有类容");
				}
			} else {
				if(treeMess=='eventTree'){
					tree="";
					for (i = 0; i < msg.questionTemplates.length; i++) {
						printEventTree(msg.questionTemplates[i]);
					}
					$('#eventTree').html(tree);
				}else if('questionTable'==treeMess){
					tree="";
					for (i = 0; i < msg.questionTemplates.length; i++) {
						printTree1(msg.questionTemplates[i]);
					}
					$('#questionTables').html(tree);
				} 
				else{
					tree="";
					for (i = 0; i < msg.questionTemplates.length; i++) {
						printTree(msg.questionTemplates[i]);
					}
					$('#questionsList').html(tree);
				}
			}
		}
	});
	
}
function printTree(template) {
	var childrens = template.childrens;
	pram="href='javascript'";
	if (childrens == null || childrens.length <= 0) {
		pram="href='QuestionAction_doTopic?tid="+template.id+"&questionsType=0&questionPage=typeQuestionsListPage' target='ajax' rel='jbsxBox'";
	}
	tree+="<li><a "+pram+">"+template.name+"</a>";
	if (childrens != null && childrens.length > 0) {
		tree+="<ul>";
		for (var j = 0; j < childrens.length; j++) {
			arguments.callee(childrens[j]);
		}
		tree+="</ul>";
	}
	tree+="</li>";
}
function printTree1(template) {
	var childrens = template.childrens;
	pram="href='javascript'";
	if (childrens == null || childrens.length <= 0) {
		pram="href='QuestionAction_doTopic?tid="+template.id+"&questionsType=0&questionPage=questionsTablePage' target='ajax' rel='questionTable'";
	}
	tree+="<li><a "+pram+">"+template.name+"</a>";
	if (childrens != null && childrens.length > 0) {
		tree+="<ul>";
		for (var j = 0; j < childrens.length; j++) {
			arguments.callee(childrens[j]);
		}
		tree+="</ul>";
	}
	tree+="</li>";
}
function printEventTree(template) {
	var childrens = template.childrens;
	pram="href='javascript'";
	if (childrens == null || childrens.length <= 0) {
		pram="onclick=setTemplateId('"+template.id+"','"+template.name+"')";
	}
	tree+="<li> <a "+pram+">"+template.name+"</a>";
	if (childrens != null && childrens.length > 0) {
		tree+="<ul>";
		for (var j = 0; j < childrens.length; j++) {
			arguments.callee(childrens[j]);
		}
		tree+="</ul>";
	}
	tree+="</li>";
}