		function templateForms_callback(rowNum) {
			var id=$("input[name='templateForms["+rowNum+"].id']").val();
  			$.ajax({
				type : "POST",
				async : false,
				url : "QuestionTemplateAction_findQuestionTypeByTemplateId",
				data:{"id":id},
				dataType : "json",
				success : function(msg) {
					var count=msg[0];
					if(count<msg[1]){
						count=msg[1];
					}
					if(count<msg[2]){
						count=msg[2];
					}
					$("select[name='templateForms[" + rowNum + "].singleNum']").empty();
					$("select[name='templateForms[" + rowNum + "].multipleNum']").empty();
					$("select[name='templateForms[" + rowNum + "].judgeNum']").empty();
					for (var i = 0; i <=count; i++) {
						if(i<=msg[0]){
						var option = $("<option>").text(i).val(i);
						$("select[name='templateForms[" + rowNum + "].singleNum']").append(
								"<option value='"+i+"'>" + i + "</option>");
						}
						if(i<=msg[1]){
						$("select[name='templateForms[" + rowNum + "].multipleNum']").append(
								"<option value='"+i+"'>" + i + "</option>");
						}
						if(i<=msg[2]){
						$("select[name='templateForms[" + rowNum + "].judgeNum']").append(
								"<option value='"+i+"'>" + i + "</option>");
						}
					}
				}
			});
		}