<form class="layui-form" id="formAdd" autocomplete="off">
    <input type="hidden" name="staffDept" value="${deptEntity.deptId}">
    <div class="layui-form-item">
        <label class="layui-form-label">部门名称</label>
        <div class="layui-input-inline layui-form-mid layui-word-aux">
            ${deptEntity.deptName}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">员工姓名</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" placeholder="请输员工姓名" lay-verify="required"
                   name="staffName">
        </div>
    </div>
    <input type="hidden" lay-submit="" lay-filter="addData" id="btnAdd"/>
</form>
<script type="text/javascript" src="/assets/iartisan/js/hrStaff/hrStaff_add.js?${staticVerison!'1'}"></script>
