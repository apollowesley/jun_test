<div style="margin: 20px;">
    <form>
        <blockquote class="layui-elem-quote quoteBox">
            <div class="layui-row">
                <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-normal" type="button" id="btnAddPage">
                            <i class="layui-icon">&#xe608;</i>添加
                        </button>
                    </div>
                </div>
            </div>
            <input type="hidden" name="menuId" id="menuId" value="${data!''}"/>
        </blockquote>
    </form>
</div>
<table id="dataList" lay-filter="dataList"></table>
<script type="text/javascript" src="/assets/iartisan/js/resource/resource_index.js?${staticVerison!'1'}"></script>
