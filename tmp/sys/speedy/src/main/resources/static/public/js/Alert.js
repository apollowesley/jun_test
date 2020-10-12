var Alert={
	hint:function(message){
		var alert=document.getElementById("alerts");
		//防止重复
		if(null==alert){
			var tagDiv=document.createElement("div");
			tagDiv.id="alerts";
			var offsetWidth=document.body.offsetWidth;
			tagDiv.style="z-index:99999;position: fixed;display:flex;align-items:center;justify-content:center;height:100%;width:100%;";
			tagDiv.innerHTML="<p style='color:#000;background:#fff;font-size:14px;padding-left:20px;padding-right:20px;padding-top:10px;padding-bottom:10px;border:1px solid #666;box-shadow:1px 1px 1px #999999;'>" +message+ "</p>";
			document.body.insertBefore(tagDiv,document.body.children[0]);
			setTimeout(function(){
				document.getElementById("alerts").parentNode.removeChild(document.getElementById("alerts"));
			},2000)
		}
	},
	code:function(title,htmlmessage){
		var alert=document.getElementById("alert");
		//防止重复
		if(null==alert){
			var bgkDiv=document.createElement("div");
			var allDiv=document.createElement("div");
			bgkDiv.id="bgk";
			allDiv.id="alert";
			allDiv.style="background:#fff;z-index:9999;background:#fff; position: fixed;display:flex;align-items: center;height:100%;justify-content:center;left: 50%;right: 50%;";
			var tagDiv="<div style='z-index:9998;position:fixed;background:#fff;border:1px solid #666;overflow:hidden;box-shadow:1px 1px 1px #999999;top:25%;padding:10px;'><p>"+title+"</p><div style='font-size:14px;'>"+htmlmessage+"<button id='close' style='width: 70px;height:30px;'>关闭</button></div></div>";
			allDiv.innerHTML=tagDiv;
			document.body.insertBefore(allDiv,document.body.children[0]);
			document.getElementById("close").onclick=function(){
				document.getElementById("alert").parentNode.removeChild(document.getElementById("alert"));
			}
		}
	}
}