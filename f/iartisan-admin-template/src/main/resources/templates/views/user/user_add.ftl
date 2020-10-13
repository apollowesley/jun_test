<div class="layui-form" style="margin-top: 50px;">
    <form id="formAdd">
        <div class="layui-form-item">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-inline">
                <input type="text" name="userName" id="userName" class="layui-input" placeholder="请输入用户名称"
                       lay-verify="required">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div class="layui-input-block">
                <input type="checkbox" name="userStatus" lay-skin="switch" lay-text="正常|停用" checked value="E">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">所有角色</label>
            <div class="layui-input-block">
            <#if data ?? >
                <#list data as val >
                    <input type="checkbox" name="roles" title="${val.roleName}" lay-skin="primary"
                           value="${val.roleId!''}"/>
                </#list>
            </#if>
            </div>
        </div>
        <input type="hidden" lay-submit="" lay-filter="addData" id="btnAdd"/>
    </form>
</div>
<script type="text/javascript" src="/assets/iartisan/js/user/user_add.js?${staticVerison!'1'}"></script>
