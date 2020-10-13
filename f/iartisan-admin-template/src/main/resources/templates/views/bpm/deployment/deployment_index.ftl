<div class="layui-card layui-card-body">
    <form class="layui-form">
        <div class="layui-row">
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md2">
                <button class="layui-btn" id="btnQuery" type="button">查询</button>
            </div>
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md2">
                <button class="layui-btn" id="btnUpload" type="button">上传新流程</button>
            </div>
            <div class="layui-col-xs6 layui-col-sm6 layui-col-md2">
                <button class="layui-btn" id="btnDesign" type="button">去设计</button>
            </div>
        </div>
    </form>

</div>
<table id="dataList" lay-filter="dataList"></table>

<script type="text/javascript" src="/assets/iartisan/js/bpm/deployment/deployment_index.js?${staticVerison!'1'}"></script>