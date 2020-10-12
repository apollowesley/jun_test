<!--/*******面包屑导航*******/-->
<div class="layui-card layadmin-header">
    <div class="layui-breadcrumb" lay-filter="breadcrumb">
        <a lay-href="">主页</a> <a href="#/${table.classNameFirstLower}/"><cite>${table.remarks!""}管理</cite></a> <a><cite>查看</cite></a>
    </div>
</div>
<!--/*******页面内容*******/-->
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-body">
            <form id="tg_${table.classNameFirstLower}_show_form" lay-filter="tg_${table.classNameFirstLower}_show_form_filter" class="layui-form layui-form-pane">
                <script type="text/html" template lay-url="{{BASE_URL}}m/${table.classNameFirstLower}/get.gsp?id={{ layui.router().search.id }}" lay-done="layui.data.done(d);">
                <input name='id' value="{{d.data.id}}" type="text" hidden="true" />
                <#list table.columns as column> 
                <#if column.columnName=="id"> 
                <#elseif column.columnName=="description">
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">描述</label>
                    <div class="layui-input-block">
                        <textarea disabled class="layui-textarea layui-bg-gray">{{d.data.description||'---'}}</textarea>
                    </div>
                </div>
                <#elseif column.columnName=="remarks">
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">备注</label>
                    <div class="layui-input-block">
                        <textarea disabled class="layui-textarea layui-bg-gray">{{d.data.remarks||'---'}}</textarea>
                    </div>
                </div>
                <#elseif column.columnName=="create_data_time"> 
                <#elseif column.columnName=="create_data_username">
                <#elseif column.columnName=="update_data_time"> 
                <#elseif column.columnName=="update_data_username"> 
                <#else>
                <div class="layui-form-item">
                    <label class="layui-form-label">${column.remarks!}</label>
                    <div class="layui-input-block">
                        <input value="{{d.data.${column.fieldNameFirstLower}||'---'}}" disabled class="layui-input layui-bg-gray" type="text" />
                    </div>
                </div>
                </#if> 
                </#list>
                </script>
            </form>
        </div>
    </div>
</div>
<!--/*******当前页js*******/-->
<script>
</script>