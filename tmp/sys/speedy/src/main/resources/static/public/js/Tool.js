var Tool = {
	id: "dataid" //数据ID\
}
var tree={};
//http请求
var http = {};
	//重组上传图片按钮
	Tool.uploadOverwrite = function(tagid,none,valueid) {
		if(tagid.indexOf(",") > -1) {
			var tags = tagid.split(",");
			if(none){
				var nones=none.split(",");
			}
			var valueids=valueid.split(",");
			for(var i = 0; i < tags.length; i++) {
				var div = document.getElementById(tags[i]);
				var img="";
				if(none){
					img="<img width='200' src='"+nones[i]+"' />";
				}
				div.innerHTML = "<label style='float:left;border:1px solid #000000;padding:10px;' title='上传图片'>上传图片<input type='file' onchange='Tool.pictureToBase64(event,\""+tagid+""+i+"\",\""+valueids[i]+"\")' style='display:none;'/></label><div id='"+tagid+""+i+"' style='width:100%;float:left;'>"+img+"</div>";
			}
		} else {
			var div = document.getElementById(tagid);
			var img="";
			if(none){
				img="<img width='200' src='"+none+"' />";
			}
			div.innerHTML = "<label style='float:left;border:1px solid #000000;padding:10px;' title='上传图片'>上传图片<input type='file' onchange='Tool.pictureToBase64(event,\"showImg"+tagid+"\",\""+valueid+"\")' style='display:none;'/></label><div id='showImg"+tagid+"' style='width:100%;float:left;'>"+img+"</div>";
		}
	}
	//上传图片
	Tool.pictureToBase64=function pictureToBase64(event,tagid,valueid){
		var file = event.target.files[0];
        var a = new FileReader();
        a.onload = function (e) {
            var base64Str = e.target.result;//获取base64
            //下面是测试得到的base64串能否正常使用：
            document.getElementById(tagid).innerHTML ="<img width=200 src='"+base64Str+"' />" ;
            document.getElementById(tagid).className='showPic';
        }
        a.readAsDataURL(file);
        var inputid=tagid+"s";
        event.target.setAttribute("id",inputid);
        //进行事件上传
        http.uploadFile("/tool/upload",inputid,"images",function(res){
			//valueid
			if(res.state==1){
				document.getElementById(valueid).value = res.data;
			}
		});
        
	}
	Tool.loadFile=function(title,tagid,callback){

		var parame=document.getElementById(tagid).dataset.parame;
		 parame=eval("("+parame+")");
		 //加载请求文件路径，选中之后通过设定的主键值和文字返回选中的数据内容
		var valueKey=parame.value;
		var textKey=parame.text;

		http.get(parame.path,"value="+valueKey+"&text="+textKey,function(res){
			if(res.indexOf("<body>")>-1){
				var body=res.split("<body>")[1].split("</body>")[0];
				tree.code(title,body+"<button id='sure'style='width: 70px;height:30px;'>确定</button>");
				if(callback){
					callback(parame);
				}
			}else{
				tree.code(title,res+"<button id='sure' style='width: 70px;height:30px;'>确定</button>");
				if(callback){
					callback(parame);
				}

			}
		});
		
	}
	
	tree.code=function(title,htmlmessage){
		var alert=document.getElementById("alert");
		//防止重复
		if(null==alert){
			var bgkDiv=document.createElement("div");
			var allDiv=document.createElement("div");
			bgkDiv.id="bgk";
			allDiv.id="alert";
			allDiv.style="background:#fff;z-index:9999;background:#fff; position: fixed;display:flex;align-items: center;height:80%;justify-content:center;left: 50%;right: 50%;top: 9%;";
			var tagDiv="<div style='z-index:9998;position:fixed;background:#fff;border:1px solid #666;overflow:scroll;height:95%;box-shadow:1px 1px 1px #999999;padding:10px;'><p>"+title+"</p><div style='font-size:14px;'>"+htmlmessage+"<button id='close' style='width: 70px;height:30px;'>关闭</button></div></div>";
			allDiv.innerHTML=tagDiv;
			document.body.insertBefore(allDiv,document.body.children[0]);
			document.getElementById("close").onclick=function(){
				document.getElementById("alert").parentNode.removeChild(document.getElementById("alert"));
			}
		}
	}
	http.uploadFile = function(url, fileid, type, success, error) {
		var fileObj = document.getElementById(fileid).files[0]; // js 获取文件对象
		var form = new FormData(); // FormData 对象
		form.append("file", fileObj); // 文件对象
		form.append("type", type); //文件类型
		var table=document.getElementById("table").value;
		form.append("table",table);
		var xhr = new XMLHttpRequest(); // XMLHttpRequest 对象
		xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
		xhr.onload = function(evt) {
			var data = JSON.parse(evt.target.responseText);
			if(success){
				success(data);
			}
			setTimeout(function() {
				document.getElementById("panel").style.display = "none";
			}, 1000)
		}; //请求完成
		xhr.onerror = function(evt) {
			var data = JSON.parse(evt.target.responseText);
			if(error){
				error(data);
			}
			setTimeout(function() {
				var panel = document.getElementById("panel");
				panel.style.display = "none"
			}, 1000)
		}; //请求失败

		xhr.upload.onprogress = progressFunction; //【上传进度调用方法实现】
		xhr.upload.onloadstart = function() { //上传开始执行方法
			ot = new Date().getTime(); //设置上传开始时间
			oloaded = 0; //设置上传开始时，以上传的文件大小为0
		};
		xhr.send(form); //开始上传，发送form数据
	},
	http.post = function(url, data, callback) {
		var httpRequest = new XMLHttpRequest(); //第一步：建立所需的对象

		httpRequest.open('POST', url, true); //第二步：打开连接
		httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); //设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
		httpRequest.send(data); //发送请求 将情头体写在send中
		/**
		 * 获取数据后的处理程序
		 */
		httpRequest.onreadystatechange = function() {
			if(httpRequest.readyState == 4 && httpRequest.status == 200) {
				var json = httpRequest.responseText; //获取到json字符串，还需解析
				callback(eval('(' + json + ')'));
			}
		};
	},
	http.get = function(url, data,callback) {
		var httpRequest = new XMLHttpRequest(); //第一步：建立所需的对象
		httpRequest.open('GET', url + "?" + data, true); //第二步：打开连接  将请求参数写在url中  ps:"./Ptest.php?name=test&nameone=testone"
		httpRequest.send(); //第三步：发送请求  将请求参数写在URL中
		/**
		 * 获取数据后的处理程序
		 */
		httpRequest.onreadystatechange = function() {
			if(httpRequest.readyState == 4 && httpRequest.status == 200) {
				if(url.indexOf(".")==-1){
					var json = httpRequest.responseText; //获取到json字符串，还需解析
					callback(eval('(' + json + ')'));
				}else{
					var json = httpRequest.responseText; //获取到json字符串，还需解析
					callback(json);
				}
				
			}
		};
	}
	
//上传进度
 function progressFunction(evt) {
	var panel=document.getElementById("panel");
	panel.style.display="block";
	var progressBar = document.getElementById("progressBar");
	var percentageDiv = document.getElementById("percentage");
	// event.total是需要传输的总字节，event.loaded是已经传输的字节。如果event.lengthComputable不为真，则event.total等于0
	if (evt.lengthComputable) {//
		progressBar.max = evt.total;
		progressBar.value = evt.loaded;
		percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
	}
	var time = document.getElementById("time");
	var nt = new Date().getTime();//获取当前时间
	var pertime = (nt-ot)/1000; //计算出上次调用该方法时到现在的时间差，单位为s
	ot = new Date().getTime(); //重新赋值时间，用于下次计算
	var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b
	oloaded = evt.loaded;//重新赋值已上传文件大小，用以下次计算
	//上传速度计算
	var speed = perload/pertime;//单位b/s
	var bspeed = speed;
	var units = 'b/s';//单位名称
	if(speed/1024>1){
		speed = speed/1024;
		units = 'k/s';
	}
	if(speed/1024>1){
		speed = speed/1024;
		units = 'M/s';
	}
	speed = speed.toFixed(1);
	//剩余时间
	var resttime = ((evt.total-evt.loaded)/bspeed).toFixed(1);
	time.innerHTML = '，速度：'+speed+units+'，剩余时间：'+resttime+'s';
	if(bspeed==0) time.innerHTML = '上传已取消';
}
 window.onload=function(){
 	var panel=document.createElement("div");
	panel.id="panel";
	panel.style="position:fixed;width:300px;background:#fff;z-index:999;display:none;right:0;bottom:0;";
	panel.innerHTML="上传进度:<progress id='progressBar' value='0' max='100' style='width: 300px;'></progress>"+
					"<span id='percentage'></span><span id='time'></span>";
	document.body.appendChild(panel);
 }
