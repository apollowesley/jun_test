<form class="layui-form" id="formAdd">
    <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
            <legend style="font-size:16px;">流程信息</legend>
        </fieldset>
    </div>
    <div class="layui-form-item">
        <label for="beginTime" class="layui-form-label">
            请选择流程
        </label>
        <div class="layui-input-inline">
            <select name="processId" lay-verify="required">
                <#list deployments as deployment>
                    <option value="${deployment.id!''}">${deployment.name!''}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
            <legend style="font-size:16px;">请假信息</legend>
        </fieldset>
    </div>
    <div class="layui-form-item">
        <label for="beginTime" class="layui-form-label">
            开始时间
        </label>
        <div class="layui-input-inline">
            <input type="text" id="beginTime" name="beginTime" lay-verify="beginTime" placeholder="yyyy-MM-dd"
                   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label for="endTime" class="layui-form-label">
                结束时间
            </label>
            <div class="layui-input-inline">
                <input type="text" id="endTime" name="endTime" lay-verify="endTime" placeholder="yyyy-MM-dd"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label for="endTime" class="layui-form-label">
                请假天数
            </label>
            <div class="layui-input-inline">
                <input type="text" id="days" name="days" class="layui-input">
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
            <legend style="font-size:16px;">原因</legend>
        </fieldset>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label for="reason" class="layui-form-label">
               请假原因
            </label>
            <div class="layui-input-inline">
                <input type="text" id="reason" style="width: 300px;" name="reason" lay-verify="reason"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
    </div>
    <input type="hidden" lay-submit="" lay-filter="addData" id="btnAdd"/>
</form>
<script type="text/javascript" src="/assets/iartisan/js/bpm/demo/new_item/newItem_add.js?${staticVerison!'1'}"></script>
