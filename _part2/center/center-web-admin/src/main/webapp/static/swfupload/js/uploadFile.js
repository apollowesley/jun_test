function uploadComplete(resultData) {
	$("#resultValue" + resultData.targetId).val(resultData.url);
	var showUrl = imageShowDomain + resultData.url;
	var title = "<img  src='" + showUrl
			+ "' style='width: 200px;height: 200px;'>";
	$("#resultTip" + resultData.targetId).tooltip({
		content : title,
		onShow : function() {
			$(this).tooltip('tip').css({
				backgroundColor : '#666',
				borderColor : '#666'
			});
		}
	});
	$("#resultTip" + resultData.targetId).find("img").attr("src", showUrl);
}

function showImage(index, url) {
	$("#resultValue" + index).val(url);
	var showUrl = imageShowDomain + url;
	var title = "<img  src='" + showUrl
			+ "' style='width: 200px;height: 200px;'>";
	$("#resultTip" + index).tooltip({
		content : title,
		onShow : function() {
			$(this).tooltip('tip').css({
				backgroundColor : '#666',
				borderColor : '#666'
			});
		}
	});
	$("#resultTip" + index).find("img").attr("src", showUrl);
}