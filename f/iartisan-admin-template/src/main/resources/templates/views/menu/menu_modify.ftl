<form class="layui-form" id="formModify">
    <div class="layui-form-item">
        <label class="layui-form-label">父级名称</label>
        <div class="layui-input-inline">
            <select name="parentMenuId" disabled>
                <option value="">/</option>
            <#if data ?? >
                <#list firstMenus as val>
                    <option value="${val.id}" <#if (val.id)=(data.parentMenuId) >selected</#if>>${val.title}</option>
                </#list>
            </#if>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单名称</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="请输入菜单名称" lay-verify="required"
                   name="menuName" value="${data.menuName!''}" lay-verType="tips">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">权限代码</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input"
                   name="menuPermission" value="${data.menuPermission!''}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">链接</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input"
                   name="menuUrl" value="${data.menuUrl!''}">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">图标</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="请输入图标代码"
                   name="menuIcon" value="${data.menuIcon?replace("\r\n", "")?replace("\"", "'")}">
        </div>
        <div class="layui-form-mid"><i class="layui-icon ${data.menuIcon!''}"> </i></div>
    </div>
    <input type="hidden" name="menuId" value="${data.menuId!''}">
    <input type="hidden" lay-submit="" lay-filter="submitData" id="btnModify"/>
</form>
<script type="text/javascript" src="/assets/iartisan/js/menu/menu_modify.js?${staticVerison!'1'}"></script>