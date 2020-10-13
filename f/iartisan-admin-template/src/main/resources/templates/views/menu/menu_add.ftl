<form class="layui-form" id="formAdd">
    <div class="layui-form-item">
        <label class="layui-form-label">父级名称</label>
        <div class="layui-input-inline">
            <select name="parentMenuId" lay-filter="parentMenuId">
                <option value="">/</option>
                <#if data ?? >
                    <#list data as val>
                        <option value="${val.id}">${val.title}</option>
                    </#list>
                </#if>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单名称</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="请输入菜单名称" lay-verify="required"
                   name="menuName">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">权限代码</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="请输入菜单名称" lay-verify="required"
                   name="menuPermission">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">链接</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="请输入菜单名称"
                   name="menuUrl" id="menuUrl">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">图标</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="请输入菜单名称" lay-verify="required"
                   name="menuIcon">
        </div>
    </div>
    <input type="hidden" lay-submit="" lay-filter="addData" id="btnAdd"/>
</form>
<script type="text/javascript" src="/assets/iartisan/js/menu/menu_add.js?${staticVerison!'1'}"></script>