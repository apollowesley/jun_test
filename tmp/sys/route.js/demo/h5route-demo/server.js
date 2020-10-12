var express = require("express");
var http=require("http");
var app=express();
var httpapp=http.createServer(app);
var fs=require("fs");

app.use(express.static(__dirname+"/../.."));

app.use(function(req,res){
	fs.readFile(__dirname+"/h5route-demo.html",function(err,data){
		res.send(data.toString());
	});
});
httpapp.listen(7777,function(){
	console.log("已运行，端口:7777   浏览器访问 localhost:7777");
});