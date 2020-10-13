<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/taglibs.jsp"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ctx" value="<%=basePath %>"/>


<link href="${ctx}/resources/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/resources/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/resources/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/resources/css/font-awesome.min.css" />
<!-- <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css'> -->

<link href="${ctx}/resources/css/animate.min.css" rel="stylesheet">
<link href="${ctx}/resources/css/style.min.css?v=3.0.0" rel="stylesheet">

<!-- 下拉框-->
<link rel="stylesheet" href="${ctx}/resources/css/chosen.css" />
<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/resources/css/ace.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/ace-responsive.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/ace-skins.min.css" />
<link rel="stylesheet" href="${ctx}/resources/css/datepicker.css" /><!-- 日期框 -->
<link href="${ctx}/resources/css/bootstraptable/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/resources/plugins/jquery-confirm/jquery-confirm.css" rel="stylesheet" type="text/css"/>



<style type="text/css">
    body{
        font-family:'微软雅黑';
        font-size: 12px;
    }
</style>



<%-- <script type="text/javascript" src="${ctx}/resources/js/jquery-1.9.1.min.js"></script> --%>
<script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.1.min.js"></script>
<script src="${ctx}/resources/js/bootstrap.min.js?v=3.4.0"></script>


<!-- 参考 http://demo.jb51.net/js/2016/jquery_confirm/  -->
<script src="${ctx}/resources/plugins/jquery-confirm/jquery-confirm.js"></script>

<script src="${ctx}/resources/js/jquery.metisMenu.js"></script>
<script src="${ctx}/resources/js/jquery.slimscroll.min.js"></script>


<!-- boottable -->
<script src="${ctx}/resources/plugins/bootstraptable/bootstrap-table.min.js"></script>
<script src="${ctx}/resources/plugins/bootstraptable/bootstrap-table-zh-CN.min.js"></script>

<!-- bootstrap table 扩展插件 ———— 实现行拖动   在html中的对应table中加入属性 data-use-row-attr-func="true" data-reorderable-rows="true" -->
<script src="${ctx}/resources/plugins/bootstraptable/extend/tr_drag/jquery.tablednd.js"></script>
<script src="${ctx}/resources/plugins/bootstraptable/extend/tr_drag/bootstrap-table-reorder-rows.js"></script>

<!-- bootstrap table 扩展插件 ———— 实现列拖动 ，在html中的对应table中加入属性  data-reorderable-columns="true" -->
<%-- <script src="${ctx}/resources/plugins/bootstraptable/extend/column_drag/bootstrap-table-reorder-columns.js"></script>
<link rel="stylesheet" href="${ctx}/resources/plugins/bootstraptable/extend/column_drag/dragtable.css" />
<script src="${ctx}/resources/plugins/bootstraptable/extend/column_drag/jquery-ui.js"></script>
<script src="${ctx}/resources/plugins/bootstraptable/extend/column_drag/jquery.dragtable.js"></script>
 --%>


<!-- bootstrap table 扩展插件 ———— 实现列编辑保存 ，在初始化表格js的列中加入属性  editable:true -->
<link rel="stylesheet" href="${ctx}/resources/plugins/bootstraptable/extend/edit_column/bootstrap-editable.css" />
<script src="${ctx}/resources/plugins/bootstraptable/extend/edit_column/bootstrap-editable.js"></script>
<script src="${ctx}/resources/plugins/bootstraptable/extend/edit_column/bootstrap-table-editable.js"></script>


<!-- bootstrap table 扩展插件 ———— 实现数据导出 ，在初始化表格js的列中加入属性  ：
        showExport: true, //是否显示导出 
        exportDataType: "basic"  //basic', 'all', 'selected'.分别 表示导出的模式是当前页、所有数据还是选中数据。 
-->
<script src="${ctx}/resources/plugins/bootstraptable/extend/export/bootstrap-table-export.js"></script>
<script src="${ctx}/resources/plugins/bootstraptable/extend/export/tableExport.js"></script>


<!--引入弹窗组件start-->
<script type="text/javascript" src="${ctx}/resources/plugins/zDialog/zDrag.js"></script>
<script type="text/javascript" src="${ctx}/resources/plugins/zDialog/zDialog.js"></script>

<!-- 提示信息插件 -->
<link rel="stylesheet" href="${ctx}/resources/plugins/msgbox/msgbox.css" />
<script type="text/javascript" src="${ctx}/resources/plugins/msgbox/msgbox.js"></script>

<!--引入弹窗组件end-->
<script type="text/javascript" src="${ctx}/resources/js/jquery.tips.js"></script>

<!-- ztree树插件 -->
<link href="${ctx}/resources/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<link href="${ctx}/resources/plugins/ztree/css/zTreeStyle/zTreeStyle-custom.css" rel="stylesheet">
<script src="${ctx}/resources/plugins/ztree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${ctx}/resources/plugins/ztree/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>

<!-- form表单验证 -->
<script src="${ctx}/resources/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/resources/plugins/validate/messages_zh.min.js"></script>
<script src="${ctx}/resources/js/validate-methods.js"></script>


<script src="${ctx}/resources/plugins/layer/layer.min.js"></script>

<script src="${ctx}/resources/js/jquery.cookie.js"></script>

<script src="${ctx}/resources/js/hplus.min.js?v=3.0.0"></script>
<script type="text/javascript" src="${ctx}/resources/js/contabs.min.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/index/menusf.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/baseUtil.js"></script>


<script>
    /* function layerBtnInit(){
    	$("[class^=layui-layer-btn]").bind("click", 
            function(event){
            }
        );
    } */
</script>