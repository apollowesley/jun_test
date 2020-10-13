<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Upload Files using XMLHttpRequest - Minimal</title>

<script type="text/javascript">
	if (!window.applicationCache) {
		alert('浏览器不支持HTML5,请使用Chrome或Firefox');
		if (window.stop)
			window.stop();
		else
			document.execCommand("Stop");
	}

	function fileSelected() {
		var file = document.getElementById('fileToUpload').files[0];
		if (file) {
			var fileSize = 0;
			if (file.size > 1024 * 1024)
				fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString()+ 'MB';
			else
				fileSize = (Math.round(file.size * 100 / 1024) / 100).toString()+ 'KB';

			document.getElementById('fileName').innerHTML = 'Name: ' + file.name;
			document.getElementById('fileSize').innerHTML = 'Size: ' + fileSize;
			document.getElementById('fileType').innerHTML = 'Type: ' + file.type;
		}
	}

	function uploadFile() {
		var fd = new FormData();
		fd.append("fileToUpload", document.getElementById('fileToUpload').files[0]);
		var xhr = new XMLHttpRequest();
		xhr.upload.addEventListener("progress", uploadProgress, false);
		xhr.addEventListener("load", uploadComplete, false);
		xhr.addEventListener("error", uploadFailed, false);
		xhr.addEventListener("abort", uploadCanceled, false);
		xhr.open("POST", "/async/UploadServlet");
		xhr.send(fd);
// 	    var formData = new FormData($('form')[0]);
// 	    $.ajax({
// 	        url: '/async/UploadServlet',  //server script to process data
// 	        type: 'POST',
// 	        xhr: function() {  // custom xhr
// 	            myXhr = $.ajaxSettings.xhr();
// 	            if(myXhr.upload){ // check if upload property exists
// 	            	// for handling the progress of the upload
// 	                myXhr.upload.addEventListener('progress', uploadProgress, false);
// 	            }
// 	            return myXhr;
// 	        },
// 	        //Ajax事件
// 	        success: uploadComplete,
// 	        error: uploadFailed,
// 	        // Form数据
// 	        data: formData,
// 	        //Options to tell JQuery not to process data or worry about content-type
// 	        cache: false,
// 	        processData:false, // 告诉jQuery不要去处理发送的数据
// 	        contentType:false // 告诉jQuery不要去设置Content-Type请求头
// 	    });
	}

	function uploadProgress(evt) {
		if (evt.lengthComputable) {
			var percentComplete = Math.round(evt.loaded * 100 / evt.total);
			document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
		} else {
			document.getElementById('progressNumber').innerHTML = 'unable to compute';
		}
	}

	function uploadComplete(evt) {
		/* This event is raised when the server send back a response */
		alert(evt.target.responseText);
	}

	function uploadFailed(evt) {
		alert("There was an error attempting to upload the file.");
	}

	function uploadCanceled(evt) {
		alert("The upload has been canceled by the user or the browser dropped the connection.");
	}
</script>
</head>
<body>
	<form id="form1" enctype="multipart/form-data" method="post">
		<div class="row">
			<label for="fileToUpload">Select a File to Upload</label>
			<input type="file" name="fileToUpload" id="fileToUpload"
				onchange="fileSelected();" />
		</div>
		<div id="fileName"></div>
		<div id="fileSize"></div>
		<div id="fileType"></div>
		<div class="row">
			<input type="button" onclick="uploadFile()" value="Upload" />
		</div>
		<div id="progressNumber"></div>
	</form>

	<p></p>
	<input type="button" onclick="window.open('/async/DownloadServlet')" value="下载" />
</body>
</html>
