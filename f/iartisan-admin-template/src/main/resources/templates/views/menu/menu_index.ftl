<div class="layui-card layui-card-body">
    <form class="layui-form">
        <div class="layui-row">
            <div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
                <div class="layui-inline">
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input searchVal" placeholder="请输入菜单名称" id="menuName"/>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                <div class="layui-inline">
                    <button class="layui-btn" id="btnQuery" type="button">查询</button>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layui-btn-normal" type="button" id="btnAddPage">
                        <i class="layui-icon">&#xe608;</i>添加

                    </button>
                </div>
            </div>
        </div>
    </form>

</div>

<div class="layui-card layui-card-body">
<table id="dataList" lay-filter="dataList"></table>
</div>
<script type="text/javascript" src="/assets/iartisan/js/menu/menu_index.js?${staticVerison!'1'}"></script>