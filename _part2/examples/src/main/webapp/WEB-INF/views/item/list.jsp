<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>后台管理系统</title>
<meta name="keyword" content="">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="copyright" content="胡桃夹子。All Rights Reserved">
<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/static/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/static/css/plugins/iCheck/green.css" rel="stylesheet">
<link href="${ctx}/static/css/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/style.css" rel="stylesheet">
</head>

<body class="fixed-sidebar">
	<div id="wrapper">
		<!----左侧导航开始----->
		<nav class="navbar-default navbar-static-side" role="navigation" id="leftnav"></nav>
		<!----左侧导航结束----->

		<!---右侧内容区开始---->
		<div id="page-wrapper" class="gray-bg">
			<!---顶部状态栏 star-->
			<div class="row ">
				<nav class="navbar navbar-fixed-top" role="navigation" id="topnav"></nav>
			</div>
			<!---顶部状态栏 end-->

			<!--------当前位置----->
			<div class="row  border-bottom white-bg page-heading">
				<div class="col-sm-4">
					<br />
					<ol class="breadcrumb">
						<li><a href="javascript:void(0)">操作solrcloud6.5</a></li>

					</ol>
				</div>
			</div>

			<!-----内容区域---->
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="ibox-content m-b-sm border-bottom">
					<div class="row">
						<div class="col-md-4">
							<div class="input-group">
								<input type="text" class="input-sm form-control" id="keywords" name="keywords" value="" placeholder="关键字"> <span class="input-group-btn"><button type="button" class="btn btn-sm btn-primary" id="queryItemBtn">查询</button></span>
							</div>
						</div>
						<div class="col-md-1">
							<button type="button" class="btn btn-sm btn-primary btn-sync" data-dismiss="modal" data-sync-type="add">增量同步</button>
						</div>
						<div class="col-md-1">
							<button type="button" class="btn btn-sm btn-primary btn-sync" data-dismiss="modal" data-sync-type="all">全量同步</button>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="ibox" id="ibox">
							<jsp:include page="./list_page.jsp" />
						</div>
					</div>
				</div>
			</div>
			<!-----内容结束----->

			<!----版权信息----->
			<div class="footer">
				<div class="pull-right">
					10GB of <strong>250GB</strong> Free.
				</div>
				<div>
					<strong>Copyright</strong> Example Company &copy; 2014-2015
				</div>
			</div>
		</div>
		<!---右侧内容区结束----->

	</div>

	<!----添加用户--->
	<div class="modal inmodal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-primary">
					<button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
					<h4 class="modal-title">编辑商品</h4>
				</div>
				<div class="modal-body">
					<form role="form" id="itemForm" name="itemForm" class="form-horizontal"></form>
				</div>
			</div>
		</div>
	</div>
	<!---添加用户结束--->


	<!-- 全局 scripts -->
	<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
	<script src="${ctx}/static/js/bootstrap.js"></script>
	<script src="${ctx}/static/js/wuling.js"></script>
	<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>

	<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script src="${ctx}/static/js/plugins/sweetalert/sweetalert.min.js" async></script>
	<!---对话框 alert--->
	<!-- 插件 scripts -->
	<script src="${ctx}/static/js/plugins/toastr/toastr.min.js" async></script>
	<!---顶部弹出提示--->
	<script src="${ctx}/static/js/plugins/iCheck/icheck.min.js"></script>
	<script src="${ctx}/static/js/plugins/validate/jquery.validate.min.js"></script>
	<!---表单验证--->
	<script src="${ctx}/static/js/plugins/validate/validate-cn.js"></script>
	<!--date style-->
	<script src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
	<!---validate 自定义方法--->
	<script>
    var _ctx = '${ctx}';
    $(document).ready(function() {

      $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_minimal-green',
        radioClass: 'iradio_minimal-green',
      });
      //表单验证
      $("#itemForm").validate({
        //debug: true,
        submitHandler: function(form) {
          editForm(form);
        }
      });

      //查询新闻
      $("#queryItemBtn").click(function() {
        list_page();
      });

      // 分页查询
      function list_page() {
        var keywords = $("#keywords").val();
        $("#ibox").load(_ctx + '/item/list_page', {
          "keywords": keywords
        });
      }

      $(".btn-sync").click(function() {
        var syncType = $(this).attr("data-sync-type");
        $.ajax({
          url: _ctx + "/item/sync",
          type: "post",
          data: {
            "syncType": syncType
          },
          success: function(data) {
            if (data.status == '1') {
              list_page();//保存成功，刷新数据
              toastr.success('', data.msg);
            } else
              toastr.error('', data.msg);
          },
          error: function(data) {
            toastr.error('', '保存发布失败');
          }
        });
      });

      function editForm(form) {
        $.ajax({
          url: _ctx + "/item/edit",
          type: "post",
          data: $(form).serialize(),
          success: function(data) {
            if (data.status == '1') {
              list_page();//保存成功，刷新数据
              toastr.success('', data.msg);
              $('#edit').modal('hide');//关闭编辑窗口
            } else
              toastr.error('', data.msg);
          },
          error: function(data) {
            toastr.error('', '保存发布失败');
          }
        });
      }

      //验证码在模态框出现前加载
      $("#edit").on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget);
        var id = button.data("id");
        $("#itemForm").load(_ctx + '/item/load/' + id);//加载待编辑数据
      });

      //删除数据
      $('#editable-sample button.delete').on('click', function() {
        var row = $(this).parents("tr")[0];
        var newsid = $(this).data("id");
        swal({
          title: "您确定要删除吗?",
          text: "用户账户删除后将不可恢复!",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#1ab394",
          confirmButtonText: "确定删除！",
          closeOnConfirm: false
        }, function() {
          row.className = "animated bounceOut";
          $.ajax({
            url: _ctx + "/item/delete/" + newsid,
            type: "get",
            success: function(data) {
              if (data.status == '1') {
                row.parentNode.removeChild(row);
                swal("删除成功!", data.msg, "success");
              } else {
                row.className = "animated fadeInLeft";
                swal("删除失败!", data.msg, "error");
              }
            },
            error: function(data) {
              row.className = "animated fadeInLeft";
              swal("删除失败!", "newsid=" + newsid + " 删除失败！", "error");
            }
          });
        });
      });

    });
  </script>
</body>
</html>
