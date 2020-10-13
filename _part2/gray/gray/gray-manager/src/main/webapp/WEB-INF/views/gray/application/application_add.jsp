<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    	$('#applicationAddOrganizationId').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });
        $('#applicationAddForm').form({
            url : '${path}/application/add',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('提示', result.msg, 'warning');
                }
            }
        });
    });
    
    function strategyCheckFun(obj){
    	var flag = obj.checked;
    	$("#"+obj.value+" :input").each(function(){
    		if(this.id.indexOf(obj.value) != -1){
    			$("#strategy_"+obj.value).combobox({required:flag,disabled:!flag});
    			$("#"+this.id).textbox({required:flag,disabled:!flag});
    		}
    	});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: auto;padding: 3px;">
        <form id="applicationAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>系统名</td>
                    <td><input name="name" type="text" placeholder="请输入系统名称" class="easyui-validatebox" data-options="required:true" value=""></td>
	                <td>唯一标识</td>
                    <td>
                        <input name="applicationId" type="text" placeholder="请输入唯一标识" class="easyui-validatebox" data-options="required:true" value="">
                    </td>
                </tr>
                <tr>
                    <td>部门</td>
                    <td><select id="applicationAddOrganizationId" style="width: 125px; " name="organizationId" class="easyui-validatebox" data-options="required:true"></select></td>
                    <td>灰度参数</td>
                    <td>
                        <input name="param" type="text" placeholder="请输入灰度参数" class="easyui-validatebox" data-options="required:true" value="">
                    </td>
                </tr>
                 <c:forEach var="item"   items="${engineList }">
                      <tr>
		                  <td>
			                  <input type="checkbox" onclick="strategyCheckFun(this)" class="easyui-validatebox"  value="${item.settingValue}">
			                  ${item.remark}
		                  </td>
		                  <td colspan="3">
		                    <div id="${item.settingValue}" style="margin:5px;">
		                    <input id="strategy_${item.settingValue}" name="strategy_${item.settingValue}" 
		                    	class="easyui-combobox" style="width:380px;"
                    			data-options="prompt:'灰度策略设置',disabled:true,required:true,valueField:'id',textField:'name',url:'${path}/strategy/getStrategyByType'">
			                  	<c:forEach var="paramItem"   items="${item.childrens}">
			                  		<div style="margin-top:5px;">
			                  			<textarea id="${item.settingValue}_${paramItem.settingParam}" name="${item.settingValue}_${paramItem.settingParam}"  class="easyui-textbox" data-options="disabled:true,required:false,multiline:true,prompt:'${paramItem.remark}'" 
										style="width:380px;height:40px">
		                  				${paramItem.settingValue eq null ? "" : paramItem.settingValue}</textarea>
									</div>
								</c:forEach>
							</div>
		                  </td>
		              </tr>     	
                </c:forEach>
                <tr>
                    <td>备注</td>
                    <td colspan="3">
                    	<textarea rows="2" cols="60" name="remark"></textarea>
                    </td>
                </tr>
            </table>
            <input name="status" type="hidden"  value="0">
        </form>
    </div>
</div>