<div class="layui-form">
    <div class="layui-form-item">
        <label class="layui-form-label">用户名：</label>
        <div class="layui-form-mid">
        ${data.userEntity.userName!''}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色：</label>
        <div class="layui-form-mid">
        <#if data ?? >
            <#list (data.roleEntities) as val >
                       <#if (val.own)>${val.roleName!''}</#if>
            </#list>
        </#if>
        </div>
    </div>
</div>