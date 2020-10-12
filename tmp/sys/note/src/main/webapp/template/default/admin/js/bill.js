
var Class = {
		create: function() {
			return function() { this.initialize.apply(this, arguments); }
		}
}
/* 用于为当前的项目添加样式，并且去除上一个的样式 */
var lastObj=null;
var lastObjFirst=true;
function addstyle(obj){
	obj.parentNode.setAttribute("class","tag_open");
	if(!lastObjFirst){lastObj.parentNode.removeAttribute("class");}
	if(lastObjFirst){lastObjFirst=false;}
	lastObj=obj;
}
(function(obj){
	obj();
}(function(){
	var Bill=Class.create();
	Bill.fn=Bill.prototype = {
			initialize: function() {
				this.nowclick=null;// 当前点击按钮的对象，用于操作成功或者失败时，刷新右边的内容，使用方法
				// Bill.nowclick.click();
				this.url=null;// 保存当前显示的内容的url
			},
			topMenu:function(){
				$.ajax({
					url:Bill.urlAddDate("/Admin.yy?a=Menu&o=getH"),
					type:"GET",
					data: {},
					dataType: "json",
					success: function(data){
						var x="";
						for(var i=0;i<data.length;i++)
						{
							x+='<li><a href="'+'javascript:;;;'+'" onclick="Bill.leftMenu(this.getAttribute(\'data\'))" data="'+data[i].id+'">'+data[i].name+'</a></li>';
						}
						$(".nav_div").append('<ul>'+x+'</ul>');
					}
				});
			},
			leftMenu:function(id){
				$.ajax({
					url:Bill.urlAddDate("/Admin.yy?a=Menu&o=getV"),
					type:"GET",
					data: {id:id},
					dataType: "json",
					success: function(data){
						var x="";
						for(var i=0;i<data.length;i++)
						{
							x+='<li><a href="'+'javascript:;;;'+'" data="'+data[i].url+'" name="'+data[i].selfkey+'">'+data[i].name+'</a></li>';
						}
						x=$(x).find("a").each(function(){
							$(this).bind("click",function(){
								addstyle(this);
								var arr=Bill.leftMenuArray(this.getAttribute('name'));
								Bill.showContent(this.getAttribute('data'),arr);
								Bill.nowclick=this;
								Bill.url=Bill.urlAddDate(this.getAttribute('data'));
							});
						});
						$(".user_cent_l").html(x);
						$(".user_cent_l").find("a").wrap("<li></li>").end().find("li").wrapAll("<ul></ul>");

					}
				});
			},
			leftMenuArray:function(str){
				var array={
						"menuManagement":{"header":['ID','自键','父键','优先级','排序','名称','链接'],"body":['id','selfkey','parentkey','name','priority','order','url']},
						"userManagement":{"header":['ID','名称','邮箱','管理员'],"body":['uid','username','email','adminid'],"headermenu":[{"name":"添加","datanum":"1","url":"Admin.yy?a=Users&o=add"},{"name":"删除","datanum":"1","url":"Admin.yy?a=Users&o=delete"}],"bodymenu":[]},
						"feedbackManagement":{"header":['ID','用户名','反馈时间','反馈内容','回复时间','回复内容'],"body":['fid','username','fdate','contents','frdate','rcontents'],"headermenu":[{"name":"添加","datanum":"1","url":"Admin.yy?a=Feedback&o=add"},{"name":"删除","datanum":"1","url":"Admin.yy?a=Feedback&o=delete"}],"bodymenu":[{"name":"回复","datanum":"1","url":"Admin.yy?a=Feedback&o=reply","type":"button2"}]},
						"templateManagement":{"header":['ID','模板名称','模板路径','模板版权','模板类型'],"body":['tid','tname','tdirectory','tcopyright','ttype'],"bodymenu":[{"name":"设为默认","datanum":"1","url":"Admin.yy?a=Template&o=setDefault","type":"button1"}]},
						"version":{"header":['ID','版本','更新内容','下载地址1','下载地址2','下载地址3','下载地址4','下载地址5','下载地址6','下载地址7'],"body":['id','version','content','downloadurl1','downloadurl2','downloadurl3','downloadurl4','downloadurl5','downloadurl6','downloadurl7'],"headermenu":[{"name":"添加","datanum":"1","url":"Admin.yy?a=Version&o=add"},{"name":"删除","datanum":"1","url":"Admin.yy?a=Version&o=delete"}]},
						"exception":{"header":['ID','时间','异常信息'],"body":['id','date','exception'],"headermenu":[{"name":"添加","datanum":"1","url":"Admin.yy?a=Exception&o=add"},{"name":"删除","datanum":"1","url":"Admin.yy?a=Exception&o=delete"}]}
				};
				return array[str];
			},
			showContent:function(url,arr){// 点击左导航时，在右边显示的内容,url参数为get方法需要的url,arr为一个Json对象
				$.ajax({
					url:Bill.urlAddDate(url),
					type:"GET",
					data: {},
					dataType: "json",
					success: function(data){
						var x="";
						x+="<tr>";
						x+="<th><input type=\"checkbox\" name=\"ids\" /></th>";
						// 内容页面表头
						for(var i in arr["header"])
						{
							x+="<th>"+arr["header"][i]+"</th>";
						}
						x+="</tr>";
						// 内容页面表体
						for(var i=0;i<data.length;i++)
						{
							x+="<tr>";
							x+="<td><input type=\"checkbox\" name=\"id\" value=\""+data[i][arr["body"][0]]+"\"/></td>";
							// 表格内容
							for(var j in arr["body"])
							{
								x+="<td>"+data[i][arr["body"][j]]+"</td>";
							}
							// 内容后面的按钮
							for(var j in arr["bodymenu"])
							{
								x+="<td><a href=\"javascript:;;;\" url=\""+arr["bodymenu"][j].url+"\">"+arr["bodymenu"][j].name+"</a></td>";
							}
							x+="</tr>";
						}
						$("#myTable").html(x);
						var myTrs=$("#myTable").find("tr");
						myTrs.on("click",function(){$(this).siblings().css("background","");this.style.background="#afb4db";});
						myTrs.css("cursor","pointer");
						// 双击一行数据，显示更新表格--------------------------------------------------------------------------------------------
						myTrs.bind("dblclick",function(){
							var id=this.firstChild.firstChild.value;
							var t="<table class=\"addtable\" style=\"position:absolute;border:1px solid #aaa;background:#fff;width:600px;height:400px;\">";// table
							for(var i in arr["header"])
							{	
								t+="<tr><th>"+arr["header"][i]+"</th>";
								t+="<td><input type=\"text\" name=\""+arr["body"][i]+"\"/></td></tr>";
							}
							t+="<tr><td colspan=\"2\"><input class=\"ok\" style=\"height:24px;width:60px;\" type=\"button\" value=\"确定\"/><input class=\"cancel\" style=\"height:24px;width:60px;\" type=\"button\" value=\"取消\"/><input class=\"id\" type=\"hidden\" value=\"\" url=\"\"></td></tr></table>";
							$(document.body).append(t);
							var addtable=$(".addtable");
							addtable.css("top",$(window).height()/2-132).css("left",$(window).width()/2-220);
							$.ajax({
								url:Bill.urlAddDate(Bill.url),
								type:"POST",
								data: {"id":id,"o":"getById"},
								dataType: "json",
								success: function(data){
									for(var j in arr["body"])
									{
										addtable.find("input[name='"+arr["body"][j]+"']").val(data[0][arr["body"][j]]);
									}
								}
							});
							addtable.find("input[class='ok']").bind("click",function(){
								addtable.wrapAll("<form></form>")
								// jqyery序列化表单，再发送请求到服务器
								var content=addtable.parent().serialize();
								content+='&id='+id+'&o=update';
								$.ajax({
									url:Bill.urlAddDate(Bill.url),
									type:"POST",
									data: content,
									dataType: "json",
									success: function(data){
										if(data.realSucess==="true")
										{
											Bill.nowclick.click();
											$(".addtable").remove();
											alert("操作成功！");
										}else{
											alert("操作失败！");            	            		
										}
									}
								});
							});
							addtable.find("input[class='cancel']").bind("click",function(){
								addtable.remove();
							});
						});
						// --------------------------------------------------------------------------------------------------------------
						if($("#myTable").find("a").length>0)
						{// 根据button的类型，动态调用
							$("#myTable").find("a").each(function(){eval("Bill."+arr["bodymenu"][0].type+"(this)");});
						}
						// 表格头部按钮
						$(".tr_options").empty();
						for(var i in arr["headermenu"])
						{
							var menu="<a class=\"headermenu\" href=\"javascript:;;;\" url=\""+arr["headermenu"][i].url+"\">"+arr["headermenu"][i].name+"</a>";

							menu=$(menu).bind("click",function(){
								if(!confirm("确定执行此操作吗？\n此操作不可恢复"))
									return;
								var idList=$("input[name='id']:checked");
								var ids=Bill.assembleIds(idList);
								var url=this.getAttribute("url");
								$.ajax({
									url:Bill.urlAddDate(url),
									type:"POST",
									data: ids,
									dataType: "json",
									success: function(data){
										if(data.realSucess==="true")
										{
											Bill.nowclick.click();
											alert("操作成功！");
										}else{
											alert("操作失败！");            	            		
										}
									}
								});
							});
							$(".tr_options").append(menu);
						}
					},
					error:function(){

					}
				});
			},
			button1:function(obj){// 普通按钮，点击根据url后post到服务器id，根据button的type调用
				$(obj).bind("click",function(){
					if(!confirm("确定执行此操作吗？\n此操作不可恢复"))
						return;

					var id=this.parentNode.parentNode.firstChild.firstChild.value;
					var url=this.getAttribute("url");
					$.ajax({
						url:Bill.urlAddDate(url),
						type:"POST",
						data: {"id":id},
						dataType: "json",
						success: function(data){
							if(data.realSucess==="true")
							{
								Bill.nowclick.click();
								alert("操作成功！");
							}else{
								alert("操作失败！");            	            		
							}
						}
					});
				});
			},
			button2:function(obj){// 一般按钮，点击后，可输入内容，然后再根据url
				// post到服务器id，根据button的type调用
				$(obj).bind("click",function(){
					obj.parentNode.parentNode.style.background="#102b6a";
					obj.parentNode.parentNode.style.color="#ffffff";
					var box=$("<div style=\"position:absolute;height:300px;width:300px;z-index:11;\"><textarea style=\"height:200px;width:300px;\"></textarea><input class=\"ok\" style=\"height:24px;width:60px;\" type=\"button\" value=\"确定\"/><input class=\"cancel\" style=\"height:24px;width:60px;\" type=\"button\" value=\"取消\"/><input class=\"id\" type=\"hidden\" value=\""+obj.parentNode.parentNode.firstChild.firstChild.value+"\" url=\""+obj.getAttribute("url")+"\"></div>");
					box.css("top",$(window).height()/2-132);
					box.css("left",$(window).width()/2-220);
					var mask=$("<div style=\"position:absolute;width:100%;height:100%;background:#999;z-index:10;opacity: 0.5;\"></div>");
					$(document.body).append(box);
					$(document.body).append(mask);
					box.children("input[class='ok']").bind("click",function(){
						var obj=$(this.parentNode);
						var id=obj.children("input[class='id']").val();
						var url=obj.children("input[class='id']").attr("url");
						var reply=obj.children("textarea").val();
						if(reply.length==0)
						{
							alert("回复内容不能为空！");
							return false;
						}
						$.ajax({
							url:Bill.urlAddDate(url),
							type:"POST",
							data: {"id":id,"reply":reply},
							dataType: "json",
							success: function(data){
								if(data.realSucess==="true")
								{
									Bill.nowclick.click();
									box.children("input[class='cancel']").click();
									alert("操作成功！");
								}else{
									alert("操作失败！");            	            		
								}
							}
						});
					});
					box.children("input[class='cancel']").bind("click",function(){
						box.remove();
						mask.remove();
						obj.parentNode.parentNode.style.background="";
						obj.parentNode.parentNode.style.color="";
					});
					return false;
				});
			},
			generateTable:function(){// 生成表格

			},
			assembleIds:function(obj){// 用于组装checkbox的id,obj是一个jQuery对象
				var ids=[];
				for(var i=obj.length;i--;)
				{
					// ids.push(obj[i].value);
					ids.unshift(obj[i].value);
				}
				ids={"ids":ids.join(",")};
				return ids;
			},
			checkAllIds:function(){// 用于点击头部的ids Checkbox时，全选所有的id
				$(document).delegate("input[name='id']","click",function(){$("input[name='id']").length==$("input[name='id']:checked").length&&$("input[name='ids']").prop("checked", true)||$("input[name='ids']").prop("checked", false);});
				$(document).delegate("input[name='ids']","click",function(){this.checked&&($("input[name='id']").prop("checked", true),true)||$("input[name='id']").prop("checked", false);});
			},
			urlAddDate:function(url){// 给URL添加一个当前时间，确保每一次请求都是从服务器获取的最新信息
				return url+"&_="+new Date().getTime();
			}
	};
	Bill.extend=Bill.fn.extend=function(){};

	window.Bill=Bill=new Bill();
	return Bill;
}));
/**
 * 普通方法，执行一些普通操作
 */
function common(){
	$(".user_cent").css("height",$(window).height()-132);
	$(".user_cent_r").css("width",$(window).width()-220);
}
$(function(){
	common();
	Bill.topMenu();
	window.onresize=common;
	Bill.checkAllIds();
});
