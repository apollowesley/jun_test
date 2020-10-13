<form class="layui-form">
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form">
            <div class="layui-row">
                <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" class="layui-input searchVal" placeholder="请输入角色名称" id="authName"/>
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
    </blockquote>
</form>
<table id="dataList" lay-filter="dataList"></table>
<script type="text/javascript" src="/assets/iartisan/js/role/role_index.js?${staticVerison!'1'}"></script>