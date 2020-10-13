<form class="layui-form" id="formAdd">
    <div class="layui-form-item">
        <label class="layui-form-label">角色名称</label>
        <div class="layui-form-mid layui-word-aux">
           ${data.roleName!''}
        </div>
    </div>
   <#-- <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-form-mid layui-word-aux">

        </div>
    </div>-->
    <div class="layui-form-item">
        <label class="layui-form-label">授权</label>
        <div class="layui-input-block">
            <div id="auths" style="border:1px solid block;">
                <ul id="permissions" name="permissions"  class="ztree"></ul>
            </div>
        </div>
    </div>
    <input type="hidden" id="roleId" value="${data.roleId}" >
    <input type="hidden" lay-submit="" lay-filter="addData" id="btnAdd"/>
</form>
<link rel="stylesheet" href="/assets/iartisan/plugins/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/assets/iartisan/js/role/role_modify.js?${staticVerison!'1'}"></script>