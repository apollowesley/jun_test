<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>${remark!}</title>
	<app:include type="css" />
	<app:include type="javascript" />
	<script type="text/javascript" src="${'$'}{CONTEXT_PATH}/js/billing/pay/arrivebank_list.js"></script>
	<c:set var="add" value="${'$'}{app:auth('00000')}"></c:set>
	<c:set var="del" value="${'$'}{app:auth('00000')}"></c:set>
	<c:set var="edit" value="${'$'}{app:auth('00000')}"></c:set>
</head>

<body class="layui-layout-body "style="height: 800px">
	<div class="layui-layout-admin">
		<div class="layui-header">
			<app:header active="10000100"></app:header>
		</div>
		<div class="clearfix"></div>
		<div class="layui-side layui-bg-black" style="overflow: hidden;">
			<div class="layui-side-scroll">
				<app:sideMenu render="sideMenu" active="10000100"></app:sideMenu>
			</div>
		</div>

		<!-- END -->
		<div class="layui-body layadmin-pagetabs layui-layout-body " id="container">
			<div class="layui-tab layui-tab-brief" lay-filter="tabs" lay-allowClose="true" style="margin: 0px;" lay-filter="tabs" lay-allowClose="true">
				<div class="layui-tab-content">
					<form class="layui-form layui-form-pane" id="searchForm" name="searchForm" action="#" method="post">
						<div class="page-head">
							<div class="table-search" style="margin-top: 1rem;">
								
								<div class="layui-form-item">
								<#if columns??>
								<#list columns as col>
								<#if !col.primaryKey >
								<#if col_index < 4>
								<div class="layui-inline">
									<label class="layui-form-label" style="width: 105px;">${col.remark!}</label>
									<div class="layui-input-inline">
										<input type="" name="${col.propertyName}" id="${col.propertyName}" lay-verify="" autocomplete="off" class="layui-input">
									</div>
								</div>
								</#if>
								</#if>
								</#list>
								</#if>
								<div class="pull-right">
									<a id="search" data-type="reload" class="layui-btn layui-btn-small">查询</a>
									<button type="reset" class="layui-btn layui-btn-small">重置</button>
								</div>
							</div>

							</div>
						</div>
					</form>
					
					 <!--<div class="layui-btn-group demoTable">
						  <button class="layui-btn " data-type="add">表格外面的按钮放这里</button>
					</div>-->
			 
					<table class="layui-hide" id="test" lay-filter="test"></table>
				</div>
			</div>
		</div>
	</div>
	<div id="system-log-div" style="display: none">
		<table id="system-log-table"></table>
	</div>
</body>
    
<script type="text/html" id="barDemo">
	<c:if test="${'$'}{edit}">
  		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  	</c:if>
  	<c:if test="${'$'}{del}">
  		  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
  	</c:if>
</script>

<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
  	<c:if test="${'$'}{add}">
		 <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
	</c:if>
	<c:if test="${'$'}{batchDel}">
		 <button class="layui-btn layui-btn-sm" lay-event="batchDelete">批量删除</button>
	</c:if>
  </div>
</script>
    
</html>