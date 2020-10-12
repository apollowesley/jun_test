<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>加入搭伙 - ${websiteName}</title>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link href="${templatePath}/css/bills.css" rel="stylesheet" />
<script charset="utf-8" src="${templatePath}/js/jquery-2.0.3.min.js"></script>

</head>

<body>
	<%@include file="global_topbar.jsp"%>
<div class="container">

<div class="lcontent bgl">
<div class="joinus">
<div class="titles" id="titles">
<ul>
<li><lable>标题</lable><a href="JoinUs.yy?a=detail&id=${data.jaapid}" target="_blank">${data.jaaptitle}</a></li>
<li><lable>发起人</lable>${data.username}</li>
<li><lable>预计总费用</lable>${data.jaapcost}</li>
<li><lable>预计人数</lable>${data.jaapthenumberofperson}</li>
<li style="display:none"><lable>可参与的人</lable>${data.jaapcontent}</li>
<li><lable>截止时间</lable>${data.jaapdeadline}</li>
<li><div class="contentdetail"  style="clear:both;">${data.jaapcontent}</div></li>
<li>你的建议：<textarea class="editor" id="editor_" name="content" style="width:660px;height:70px;"></textarea></div></li>
</ul>
</div>
<c:choose>
<c:when test="${state==0}"><p class="btn"><a class="btn_a" id="btn_a" href="javascript:void(0);;;" ><span class="btn_30px f14">申请加入</span></a></p></c:when>
<c:when test="${state==1}"><p class="btn"><a class="btn_a gray"><span class="btn_30px f14">已经申请</span></a></p></c:when>
<c:when test="${state==2}"><p class="btn"><a class="btn_a gray"><span class="btn_30px f14">已加入</span></a></p></c:when>
<c:when test="${state==3}"><p class="btn"><a class="btn_a gray"><span class="btn_30px f14">未通过</span></a></p></c:when>
<c:when test="${state==4}">
<div class="bold f14">申请人：</div>
<c:forEach items="${listApply}" var="list" varStatus="status">
<div>
<div>昵称：${list.username}</div>
<div>电话：${list.telephone}</div>
<div>建议：${list.jaapadvice}</div>
<div style="border-bottom:1px #ccc solid"></div>
</div>
</c:forEach>
<div><a id="deleteJoin" href="javascript:void(0);;;" class="my_a right">删除</a></div>
</c:when>
<c:otherwise></c:otherwise>
</c:choose>
</div>
</div>
  <div class="rcontent">
    <div class="calendar"><%@include file="../html/Calendar3.html" %></div>
  </div>
</div>
<script type="text/javascript" src="${templatePath}/js/Calendar3.js"></script>

<script>

	$("#btn_a").bind("click",function(){
		var data='id='+${data.jaapid}+'&content='+$("#editor_").val();
		$.ajax({
			url:'/JoinUs.yy?a=applyjoin&date='+new Date(),
			type:"POST",
			data: data,
			dataType: "json",
			success: function(data){
				if(data.realSucess==="true")
				{
					alert("申请加入成功！请等待发起人联系你！");
					location.replace(location.href);
				}else{
					alert(data.msg);            	            		
				}
			}
		});
		return false;
	});
	


	$("#deleteJoin").bind("click", function() {
		var data = 'id=' + ${data.jaapid};

		if (confirm("确定要删除吗？")) {
			$.ajax({
				url : '/JoinUs.yy?a=delete&date=' + new Date(),
				type : "POST",
				data : data,
				dataType : "json",

				success : function(data) {
						if (data.realSucess === "true") {
							alert("删除成功！您还可以继续发起其它搭伙~");
							location.replace("/JoinUs.yy?");
						} else {
							alert(data.msg);
						}
				},
				error:function(){
					alert("删除失败，可能网络有点慢~");
					location.replace("/JoinUs.yy?");
				}
			});
		}
		return false;
	});
</script>
	<%@include file="../jsp/global_footer.jsp"%>
	<%@include file="../html/modbacktop.html"%>
	<div style="clear:both;"></div>
</body>
</html>
