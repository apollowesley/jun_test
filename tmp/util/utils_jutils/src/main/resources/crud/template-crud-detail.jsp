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
					<form id="myForm" class="form-horizontal">
						<input type="hidden" value="${id}" name="id" id="id">
						<input type="hidden" name="dateFormat" value="yyyy-MM-dd HH:mm">
						<div class="form-group" id="myInfoDiv" style="display: none;"></div>
						{{#form_hidden_list}}
						<input type="hidden" name="{{fieldName}}" id="{{fieldName}}">
						{{/form_hidden_list}} {{#form_text_list}} {{#oneColumn}}
						<div class="form-group">
							<label class="col-sm-1 control-label">
								{{left.cnName}}
								<span class="red_star">*</span>
							</label>
							<div class="col-sm-5">
								<input type="text" id="{{left.fieldName}}" name="{{left.fieldName}}" class="form-control" placeholder="">
							</div>
						</div>
						{{/oneColumn}} {{^oneColumn}}
						<div class="form-group">
							<label class="col-sm-1 control-label">
								{{left.cnName}}
								<span class="red_star">*</span>
							</label>
							<div class="col-sm-5">
								<input type="text" id="{{left.fieldName}}" name="{{left.fieldName}}" class="form-control" placeholder="">
							</div>
							<label class="col-sm-1 control-label">
								{{right.cnName}}
								<span class="red_star">*</span>
							</label>
							<div class="col-sm-5">
								<input type="text" id="{{right.fieldName}}" name="{{right.fieldName}}" class="form-control" placeholder="">
							</div>
						</div>
						{{/oneColumn}} {{/form_text_list}}

						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<label class="col-sm-1 control-label"></label>
							<div class="col-sm-11 ">
								<button class="btn btn-primary" type="button" id="saveOrUpdateBtn">保存详情</button>
								<a class="btn btn-primary" type="button" href="javascript:if(confirm('确定重置么?'))location.reload();">重置详情</a>
								<a class="btn btn-primary" type="button" href="${home }{{actionPath}}/forwadList">返回列表</a>
							</div>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>


	<div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="successModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body">
					<span>数据保存成功</span>
				</div>
				<div class="modal-footer">
					<a class="btn btn-primary" type="button" href="${home }{{actionPath}}/forwardDetail">继续新增</a>
					<a class="btn btn-primary" type="button" href="${home }{{actionPath}}/forwadList">返回列表</a>
				</div>
			</div>
		</div>
	</div>

	<!-- this include datatables,validation and all js -->
	<%@ include file="/common/templates/jsAndDivs.jsp"%>

	<script>
		var myTableRef;
		var isValidate;
		var pageLength;
		var pageName = "{{jspTitleName}}";

		function drawDetail() {
			var id = $('#id').val();
			$.ajax({
				type : "POST",
				url : "${home}{{actionPath}}/detail{{modelUppercase}}",
				data : {
					"id" : id
				},
				cache : false,
				dataType : "json",
				success : function(r) {
					var model = r.model;
					$('#id').val(model.id);
					{{#drawDetail_list}}
					$('#{{fieldName}}').val(model.{{fieldName}});
					{{/drawDetail_list}}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					toastr.error('拉取数据系统异常');
				}
			});
		}

		$(function() {

			if ($('#id').val()) {
				drawDetail();
			}

			isValidate = $("form#myForm").validate({
				rules : {
					{{rules}}
				},
				messages : {
					nonExistField : {
						required : "nonExistMessage"
					}
				}
			});

			$('#saveOrUpdateBtn').on("click", function() {
				if (isValidate.form() == false) {
					toastr.clear();
					toastr.error('表单填写错误，请检查');
					return;
				}
				if (!confirm('确认保存已经修改的数据吗?')) {
					return false;
				}
				$('#saveOrUpdateBtn').attr('disabled', 'disabled');
				mask();
				$.ajax({
					type : "POST",
					url : "${home}{{actionPath}}/saveOrUpdate{{modelUppercase}}",
					data : $("#myForm").serialize(),
					cache : false,
					dataType : "json",
					success : function(r) {
						unmask();
						$('#saveOrUpdateBtn').removeAttr('disabled');
						if (r.ret == 0) {
							$('#successModal').modal({
								'backdrop' : 'static'
							});
							//toastr.success('保存成功');
						} else {
							toastr.error(r.msg, '保存失败');
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						unmask();
						$('#saveOrUpdateBtn').removeAttr('disabled');
						toastr.error('请稍后重试', '系统异常');
					}
				});
			});

		});
	</script>
</body>
</html>