<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>新增或修改${remark!}</title>
    <app:include type="css" />
	<app:include type="javascript" />
	<script type="text/javascript" src="${'$'}{CONTEXT_PATH}/"></script>
</head>
    <body>
        <form class="layui-form layui-form-pane" action="" method="post" id="formDemo" style="margin: 5% 16%;">
            <input type="hidden" name="${primaryProperty}" value="${'$'}{${entityName}.${primaryProperty}}">
            <div class="layui-form-item">
                <#if columns??>
                <#list columns as col>
                <#if !col.primaryKey >
                <label class="layui-form-label" style="width: 105px;">${col.remark!}</label>
                <div class="layui-input-inline">
                    <input type="text" name="${col.propertyName}" value="${'$'}{${entityName}.${col.propertyName}}" lay-verify="" class="layui-input" />
                </div>
                
                <#if col_index % 2 == 0>
            </div>
             <div class="layui-form-item">
                </#if>
                </#if>
                </#list>
            </#if>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-inline" style="width: 91%; text-align: center;">
					<button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
            </div>
        </form>
    </body>
</html>