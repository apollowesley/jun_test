<blockquote class="layui-elem-quote quoteBox">
    <form class="layui-form">
        <input type="hidden" name="deptId" id="deptId" value="${deptEntity.deptId}">
        <div class="layui-row">
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                <div class="layui-inline">
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input searchVal" placeholder="请输入员工姓名" id="userName"/>
                    </div>
                    <i style="color: #FF5722;" class="layui-icon" lay-tips="支持模糊查询"></i>
                </div>
            </div>
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                <div class="layui-inline">
                    <button class="layui-btn" id="btnQuery" type="button">查询</button>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-normal" type="button" id="btnAddPage">
                        <i class="layui-icon">&#xe608;</i> 添加
                    </button>
                </div>
            </div>
        </div>
    </form>
</blockquote>
<table id="dataList" lay-filter="_table"></table>
<script type="text/javascript" src="/assets/iartisan/js/hrStaff/hrStaff_manager.js?${staticVerison!'1'}"></script>