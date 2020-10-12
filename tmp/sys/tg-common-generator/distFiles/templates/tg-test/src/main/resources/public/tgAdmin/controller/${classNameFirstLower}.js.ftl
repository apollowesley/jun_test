// ${table.remarks!""}
layui.define([ 'table', 'form' ], function(exports) {
    var $ = layui.$, table = layui.table, form = layui.form;
    /** ******************* 列表 ********************** */
    // 表格列
    var cols = [ [ {
        type : 'numbers', // 序号
        title : '#',
        width : 60
    }
    <#list table.columns as column>
        <#if column.columnName == "create_data_time">
        <#elseif column.columnName == "create_data_username">
        <#elseif column.columnName == "update_data_time">
        <#elseif column.columnName == "update_data_username">
        <#else>
            , {
                field : '${column.fieldNameFirstLower}',
                title : '${column.remarks!}',
                minWidth : 100
            }
        </#if>
    </#list>
    , {
        title : '操作',
        fixed : 'right',
        align : 'center',
        toolbar : '#tgToolbar',
        width : 165
    } ] ];
});