<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

	<link rel="stylesheet" type="text/css" href="${ctxStatic}/DataTables-1.10.4/media/css/jquery.dataTables.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/DataTables-1.10.4/examples/resources/syntax/shCore.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/DataTables-1.10.4/examples/resources/demo.css">
	
	<style type="text/css" class="init">

	</style>
	
	<script type="text/javascript" language="javascript" src="${ctxStatic}/DataTables-1.10.4/media/js/jquery.js"></script>
	<script type="text/javascript" language="javascript" src="${ctxStatic}/DataTables-1.10.4/media/js/jquery.dataTables.js"></script>
	<script type="text/javascript" language="javascript" src="${ctxStatic}/DataTables-1.10.4/examples/resources/syntax/shCore.js"></script>
	<script type="text/javascript" language="javascript" src="${ctxStatic}/DataTables-1.10.4/examples/resources/demo.js"></script>
	
	<script type="text/javascript" language="javascript" class="init">
		$(document).ready(function() {
			$('#example').dataTable( {
				"processing": true,
				"serverSide": true,
				"pageLength": 10,
				//"lengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "All"] ],
				"deferRender": true,
				"oLanguage" : { 
					"sEmptyTable": "没有记录",
					"sProcessing" : "处理中，请稍候...",
					"sLengthMenu" : "每页显示 _MENU_ 条记录",
					"sZeroRecords" : "没有记录",
					"sInfo" : "当前显示从 _START_ 到  _END_ 条记录,总记录数为 _TOTAL_ 条",
					"sInfoEmpty" : "记录数为0",
					"sInfoFiltered" : "(从全部记录数 _MAX_ 条中过滤)",
					"sInfoPostFix" : "",
					"sLoadingRecords": "正在加载中,请稍候...",
					"sSearch" : "搜索",
					"sUrl" : "",
					"oPaginate" : {
						"sFirst" : "首页",
						"sPrevious" : "上一页",
						"sNext" : "下一页",
						"sLast" : "末页"
					}
				},
				"ajax": "${ctx}/dataTables/datatables.do",
				"columns": [
			      {'data': 'firstName'},
			      {'data': 'lastName'},
			      {'data': 'position'},
			      {'data': 'office','bSortable': false},
			      {'data': 'startDate'},
			      {'data': 'age'},
			      {'data': 'salary'}
			    ],
			    "columnDefs": [
 			      {
 			    	  'targets': [7],
 			    	  'data' : 'id',
 	        	      'render': function(data, type, row, meta) {
 	        	    	  var result="<a href=\'javascript:update(\""+data+"\")\' title=\'修改\'>修改</a>&nbsp;&nbsp;";   
 	        	    	      result+="<a href=\'javascript:del(\""+data+"\")\' title=\'删除\'>删除</a> &nbsp;&nbsp;";
 	        	    	      result+="<a href=\'javascript:show(\""+data+"\")\'  title=\'查看\'>查看</a>";    	  
 	        	    	  return result;
 	        	      }	
 			      }
 			    ]
			} );
		} );
		
		function update(id) {
			alert(id);
		}
		
		function del(id) {
			alert(id);
		}
		
		function show(id) {
			alert(id);
		}

	</script>
</head>
<body>
	<div class="container">
		<table id="example" class="display" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>First name</th>
					<th>Last name</th>
					<th>Position</th>
					<th>Office</th>
					<th>Start date</th>
					<th>Age</th>
					<th>Salary</th>
					<th>Operation</th>
				</tr>
			</thead>

			<tfoot>
				<tr>
					<th>First name</th>
					<th>Last name</th>
					<th>Position</th>
					<th>Office</th>
					<th>Start date</th>
					<th>Age</th>
					<th>Salary</th>
					<th>Operation</th>
				</tr>
			</tfoot>
		</table>
	</div>
</body>
</html>