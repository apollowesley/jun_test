<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<title>feiynn | {{jspTitleName}}</title>
<%@ include file="/common/link.jsp"%>
</head>
<body class="top-navigation">

	<div id="page-wrapper" class="white-bg">
		<%@ include file="/common/navs/top-nav.jsp"%>

		<div class="col-md-2">
			<%@ include file="/common/navs/left-nav.jsp"%>
		</div>

		<div class="col-md-10">

			<%@ include file="/common/navs/head-nav.jsp"%>

			<div class="row">

				<div class="col-lg-12">

					<form role="form" class="form-inline" id="mySearchForm">
						<div class="form-group">
							<label for="startTime" class="">开始时间</label>
							<input type="text" id="startTime" name="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,lang:'zh-cn'})" class="form-control" placeholder="开始时间">
						</div>
						<div class="form-group">
							<label for="endTime" class="">结束时间</label>
							<input type="text" id="endTime" name="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,lang:'zh-cn'})" class="form-control" placeholder="结束时间">
						</div>
						<input type="hidden" name="start" id="start">
						<input type="hidden" name="length" id="length">
						<button id="mySearchFormBtn" class="btn btn-primary" type="button">查询记录</button>
						<button id="mySearchFormResetBtn" class="btn btn-primary" type="button">重置条件</button>
					</form>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-12">
					<form id="myForm">
						<table class="table table-striped table-bordered table-hover dataTables-example" id="myTable">
							<thead>
								<tr>
									<th>
										<input id="chkAll" type="checkbox">
									</th>
									{{#dataTables_list}}
									<th>{{cnName}}</th>
									{{/dataTables_list}}
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</form>
					<p>
						<a class="btn btn-primary" type="button" href="${home }{{actionPath}}/forwardDetail">新增记录</a>
						<button class="btn btn-primary hidden" type="button" id="createModalBtn">新增记录</button>
						<button class="btn btn-primary hidden" type="button" id="updateModalBtn">编辑记录</button>
						<button class="btn btn-primary" type="button" id="deleteModalBtn">删除记录</button>
					</p>
				</div>
			</div>

			<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="deleteModalLabel">确定吗?</h4>
						</div>
						<div class="modal-body">
							<form id="deleteModalForm" class="form-horizontal">
								<div class="form-group">
									<div class="row">
										<div class="col-md-12">这将删除所选记录</div>
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" id="deleteModalConfirmBtn" class="btn btn-primary" data-loading-text="执行中...">确定</button>
							<button type="button" id="deleteModalCloseBtn" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- this include datatables,validation and all js -->
	<%@ include file="/common/templates/jsAndDivs.jsp"%>

	<script>
		var myTableRef;
		var pageLength;
		var pageName = "{{jspTitleName}}";
		var uploadPath = '${imgUploadPath}';

		$(function() {
			
			$('#chkAll').prop('checked',false);

			$('#deleteModalBtn').on('click', function() {
				if ($('input[name=myIds]:checked').length == 0) {
					info('至少选择一条记录来执行删除操作');
					return;
				}
				$('#deleteModal').modal({
					'backdrop' : 'static'
				});
			});

			$("#chkAll").on("click", function() {
				$("input:checkbox").prop("checked", $(this).prop('checked'));
			});

			$("#mySearchFormResetBtn").on('click',function(){
				$("#mySearchForm")[0].reset();
			});
			

			$('#deleteModalConfirmBtn').on("click", function() {
				$(this).button('操作进行中...');
				$.ajax({
					type : "POST",
					url : '${home}{{actionPath}}/delete{{modelUppercase}}',
					data : $("#myForm").serialize(),
					cache : false,
					dataType : "json",
					timeout : 5000,
					success : function(r) {
						$('#deleteModalBtn').button('reset');
						$('#deleteModalCloseBtn').click();
						myTableRef.ajax.reload();
						info("成功删除记录");
					},
					error : function(jqXHR, textStatus, errorThrown) {
						$('#deleteModalBtn').button('reset');
						$('#deleteModalCloseBtn').click();
					}
				});
			});
			
			$("#saveOrUpdateModalCloseBtn").on('click',function(){
				$("label.error").hide();
				$(".error").removeClass("error");
			});
			
			myTableRef = $('#myTable').DataTable({
				"autoWidth" : false,
				"pageLength" : pageLength,
				"autoWidth" : true,
				"processing" : true,
				"serverSide" : true,
				"deferRender" : true,
				"paging" : true,
				"filter" : false,
				"lengthChange" : false,
				"ordering" : false,
				"orderClasses" : false,
				"pagingType" : "full_numbers",
				"info" : true,
				"ajax" : {
					"url" : "${home}{{actionPath}}/list{{modelUppercase}}",
					"type" : "POST",
					"dataType" : 'json',
					"data" : function(d) {
						$('input#start').val(d.start);
						$('input#length').val(d.length);
						return  $('#mySearchForm').serialize();
					}
				},
				"initComplete": function () {
		        },
				"columns" : [{
					"mData" : function(source, type,
							val) {
						return '<input id="myIds" name="myIds" value="'+source.id+'" type="checkbox">';
					}
				}
				{{#dataTables_list}}
				, {
					"data" : "{{fieldName}}"
				}
				{{/dataTables_list}}
				, {
					"mData" : function(source, type, val) {
						var location = "${home}{{actionPath}}/forwardDetail?id=" + source.id;
						return '<button class="btn btn-primary" type="button" onclick="location.href='+"'"+location+"'"+'">详情管理</button>';
					}
				}
				]
			}); 
			//end datatable
			$('#mySearchFormBtn').on('click', function() {
				myTableRef.ajax.reload();
			});
		});
	</script>
</body>
</html>