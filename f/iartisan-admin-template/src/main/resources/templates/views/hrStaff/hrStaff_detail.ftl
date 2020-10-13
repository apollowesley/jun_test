<form class="layui-form" autocomplete="off">
    <div class="layui-form-item">
        <label class="layui-form-label">父级部门</label>
        <div class="layui-input-inline layui-form-mid layui-word-aux">
            ${parentDept.deptName}
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门名称</label>
        <div class="layui-input-inline layui-form-mid layui-word-aux">
            ${dept.deptName}
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-danger" type="button" id="btnDel" attr-data="${dept.deptId}">
                删除
            </button>
        </div>
    </div>
</form>
<script type="text/javascript" src="/assets/iartisan/js/hrDept/hrDept_detail.js?${staticVerison!'1'}"></script>