${r'<#include "/common/_layout.html"/>'}
${r'<@layout>'}
<div class="container-fluid">

	<!-- BEGIN PAGE HEADER-->   

	<div class="row-fluid">

		<div class="span12">

			<!-- BEGIN STYLE CUSTOMIZER -->

			${r'<#include "/common/_color.html" />'}

			<!-- END BEGIN STYLE CUSTOMIZER -->  

			<h3 class="page-title">

				 ${tableDesc}管理

				 <small>编辑${tableDesc}</small>

			</h3>

			<ul class="breadcrumb">

				<li>

					<i class="icon-home"></i>

					<a href="${'$'}{CONTEXT_PATH}/">首页</a> 

					<span class="icon-angle-right"></span>

				</li>


				<li>
				
					<a href="${'$'}{CONTEXT_PATH}/${tableName}">${tableDesc}管理</a>
					<span class="icon-angle-right"></span>
				</li>
				
				<li>
				
					<a href="#">编辑${tableDesc}</a>

				</li>

			</ul>

		</div>

	</div>

	<!-- END PAGE HEADER-->

	<!-- BEGIN PAGE CONTENT-->

	<div class="row-fluid">

		<div class="span12">

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-reorder"></i>编辑${tableDesc}</div>

								<div class="tools">

									<a href="javascript:;" class="collapse"></a>

									<a href="#portlet-config" data-toggle="modal" class="config"></a>

									<a href="javascript:;" class="reload"></a>

									<a href="javascript:;" class="remove"></a>

								</div>

							</div>

							<div class="portlet-body form">

								<!-- BEGIN FORM-->
								<form id="sample-form" action="${'$'}{CONTEXT_PATH}/${tableName}/save" method="POST" class="form-horizontal">
								${r'<#include "_form.html" />'}
								</form>
								<!-- END FORM-->                

							</div>

						</div>

		</div>

	</div>

	<!-- END PAGE CONTENT-->         

</div>
${r'</@layout>'}
${r'<@scripts>'}

	<!-- BEGIN PAGE LEVEL STYLES -->

	<link rel="stylesheet" type="text/css" href="${'$'}{CONTEXT_PATH}/media/css/select2_metro.css" />

	<!-- END PAGE LEVEL STYLES -->

	

	<!-- BEGIN PAGE LEVEL PLUGINS -->

	<script type="text/javascript" src="${'$'}{CONTEXT_PATH}/media/js/select2.min.js"></script>
	<script type="text/javascript" src="${'$'}{CONTEXT_PATH}/media/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${'$'}{CONTEXT_PATH}/media/js/jquery.validate.messages_cn.js"></script>

	<!-- END PAGE LEVEL PLUGINS -->

	<!-- BEGIN PAGE LEVEL SCRIPTS -->

	<script src="${'$'}{CONTEXT_PATH}/media/js/app.js"></script>

	<script src="${'$'}{CONTEXT_PATH}/media/js/form-samples.js"></script>   

	<!-- END PAGE LEVEL SCRIPTS -->

	<script>

		jQuery(document).ready(function() {    

		   // initiate layout and plugins

		   App.init();

		   FormSamples.init();

		});

	</script>
${r'</@scripts>'}