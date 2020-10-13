<form class="layui-form" id="formModify">
    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-form-mid">
        ${data.userEntity.userName!''}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所有角色</label>
        <div class="layui-input-block">
        <#if data ?? >
            <#list (data.roleEntities) as val >
                <input type="checkbox" name="roles" title="${val.roleName}" lay-skin="primary" value="${val.roleId!''}"
                       <#if (val.own)>checked</#if>
                >
            </#list>
        </#if>
        </div>
    </div>
    <input type="hidden" name="userId" value="${data.userEntity.userId!''}">
    <input type="hidden" lay-submit="" lay-filter="modifyData" id="btnModify"/>
</form>
<script type="text/javascript" src="/assets/iartisan/js/user/user_modify.js?${staticVerison!'1'}"></script>