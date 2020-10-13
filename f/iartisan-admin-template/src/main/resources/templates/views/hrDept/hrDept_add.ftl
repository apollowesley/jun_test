<form class="layui-form" id="formAdd" autocomplete="off">
    <input type="hidden" name="deptParent" value="${parentDept.deptId}">
    <div class="layui-form-item">
        <label class="layui-form-label">父级部门</label>
        <div class="layui-input-inline layui-form-mid layui-word-aux">
            ${parentDept.deptName}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门名称</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="请输入部门名称" lay-verify="required"
                   name="deptName">
        </div>
    </div>
    <input type="hidden" lay-submit="" lay-filter="addData" id="btnAdd"/>
</form>
<script type="text/javascript" src="/assets/iartisan/js/hrDept/hrDept_add.js?${staticVerison!'1'}"></script>
