${r'<#include "/common/_layout.html"/>'}
${r'<@layout>'}
<div class="container-fluid">

	<!-- BEGIN PAGE HEADER-->

	<div class="row-fluid">

		<div class="span12">

			<!-- BEGIN STYLE CUSTOMIZER -->

			${r'<#include "/common/_color.html"/>'}

			<!-- END BEGIN STYLE CUSTOMIZER -->  

			<!-- BEGIN PAGE TITLE & BREADCRUMB-->

			<h3 class="page-title">

				${tableDesc}管理 <small>${tableDesc}列表</small>

			</h3>

			<ul class="breadcrumb">

				<li>

					<i class="icon-home"></i>

					<a href="${'$'}{CONTEXT_PATH}/">首页</a> 

					<i class="icon-angle-right"></i>

				</li>

				<li><a href="${'$'}{CONTEXT_PATH}/${tableName}">${tableDesc}管理</a></li>

			</ul>

			<!-- END PAGE TITLE & BREADCRUMB-->

		</div>

	</div>

	<!-- END PAGE HEADER-->

	<!-- BEGIN PAGE CONTENT-->

	<div class="row-fluid">

		<div class="span12">

			<!-- BEGIN EXAMPLE TABLE PORTLET-->

			<div class="portlet box light-grey">

				<div class="portlet-title">

					<div class="caption"><i class="icon-globe"></i>${tableDesc}</div>

					<div class="tools">

						<a href="javascript:;" class="collapse"></a>

						<a href="#portlet-config" data-toggle="modal" class="config"></a>

						<a href="javascript:;" class="reload"></a>

						<a href="javascript:;" class="remove"></a>

					</div>

				</div>

				<div class="portlet-body">

					<div class="clearfix">

						<div class="btn-group">

							<a href="${'$'}{CONTEXT_PATH}/${tableName}/add" class="btn blue">新增${tableDesc}<i class="icon-plus"></i></a>

						</div>

						<div class="btn-group pull-right">

							<button class="btn dropdown-toggle" data-toggle="dropdown">工具 <i class="icon-angle-down"></i>

							</button>

							<ul class="dropdown-menu pull-right">

								<li><a href="#">打印</a></li>

								<li><a href="#">保存为PDF</a></li>

								<li><a href="#">导出Excel</a></li>

							</ul>

						</div>

					</div>

					<table class="table table-striped table-bordered table-hover" id="sample_1">
						<div class="row-fluid">
							
							<div class="span12">
								<div class="dataTables_filter" id="sample_editable_1_filter">
								<form action="${'$'}{CONTEXT_PATH}/${tableName}/">
									<label>查找: <input type="text" name="search" value="${'$'}{(search)!}" aria-controls="sample_editable_1" class="m-wrap medium"></label>
									</form>
								</div>
							</div>
						</div>
						<thead>

							<tr>

								<th style="width:8px;"><input type="checkbox" class="group-checkable" data-set="#sample_1 .checkboxes" /></th>
							    <#list columns as c>
								<th class="hidden-480">${c.columnDesc}</th>
							    </#list>
								<th style="width:180px;">操作</th>
							</tr>

						</thead>

						<tbody>
						${'<#list'} ${tableName}Page.getList() as p>
							<tr>
							    <td><input type="checkbox" class="checkboxes" value="${'$'}{p.id}" /></td>
								<#list columns as c>
								<td class="center hidden-480">${'$'}{(p.${c.columnName})!}</td>
								</#list>
								<td><a href="${'$'}{CONTEXT_PATH}/${tableName}/view/${'$'}{p.id}" class="btn mini green-stripe">查看</a>  <a href="${'$'}{CONTEXT_PATH}/${tableName}/edit/${'$'}{p.id}" class="btn mini blue-stripe">编辑</a> <a href="${'$'}{CONTEXT_PATH}/${tableName}/delete/${'$'}{p.id}" class="btn mini red-stripe delete">删除</a></td>
							</tr>
							${r'</#list>'}
							
						</tbody>
						
					</table>
					  ${r'<#include "/common/_paginate.html" />'}
					  ${r'<@paginate'} currentPage=${tableName}Page.pageNumber totalPage=${tableName}Page.totalPage actionUrl="${'$'}{CONTEXT_PATH}/${tableName}/" />
				</div>

			</div>

			<!-- END EXAMPLE TABLE PORTLET-->

		</div>

	</div>


	<!-- END PAGE CONTENT-->

</div>

${r'</@layout>'}

${r'<@scripts>'}

	<!-- BEGIN PAGE LEVEL STYLES -->

	<link rel="stylesheet" type="text/css" href="${'$'}{CONTEXT_PATH}/media/css/select2_metro.css" />

	<link rel="stylesheet" href="${'$'}{CONTEXT_PATH}/media/css/DT_bootstrap.css" />

	<!-- END PAGE LEVEL STYLES -->

	

	<!-- BEGIN PAGE LEVEL PLUGINS -->

	<script type="text/javascript" src="${'$'}{CONTEXT_PATH}/media/js/select2.min.js"></script>

	<script type="text/javascript" src="${'$'}{CONTEXT_PATH}/media/js/jquery.dataTables.js"></script>

	<script type="text/javascript" src="${'$'}{CONTEXT_PATH}/media/js/DT_bootstrap.js"></script>

	<!-- END PAGE LEVEL PLUGINS -->

	<!-- BEGIN PAGE LEVEL SCRIPTS -->

	<script src="${'$'}{CONTEXT_PATH}/media/js/app.js"></script>

	<script src="${'$'}{CONTEXT_PATH}/media/js/table-managed.js"></script>     

	<script>

		jQuery(document).ready(function() {       

		   App.init();

		   TableManaged.init();

		});

	</script>
${r'</@scripts>'}