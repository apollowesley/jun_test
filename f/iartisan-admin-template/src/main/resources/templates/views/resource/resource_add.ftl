<div class="layui-form" style="margin-top:5px;margin-right: 20px;">
    <form id="formAdd">
        <div class="layui-form-item">
            <label class="layui-form-label">权限名称</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" placeholder="请输入菜单名称" lay-verify="required"
                       name="resourceName">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">权限代码</label>
            <div class="layui-input-inline">
                <input type="text" class="layui-input" placeholder="请输入菜单名称" lay-verify="required"
                       name="resourcePermission">
            </div>
        </div>
        <input type="hidden" name="menuId" value="${data!''}"/>
        <input type="hidden" lay-submit="" lay-filter="addData" id="btnAdd"/>
    </form>
</div>
<script type="text/javascript" src="/assets/iartisan/js/resource/resource_add.js?${staticVerison!'1'}"></script>