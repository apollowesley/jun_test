<div class="layui-container">
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-inline">
                <input type="password" name="pwd" id="pwd" lay-verify="required" placeholder="请输入"
                       lay-verType="tips" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">确认密码</label>
            <div class="layui-input-inline">
                <input type="password" name="confirmPwd" lay-verify="required|confirmPwd" placeholder="请输入"
                       lay-verType="tips"
                       autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item layui-row layui-col-xs12">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="restPwd">确认</button>
                <button type="reset" class="layui-btn layui-btn-primary" id="reset">重置</button>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="/assets/iartisan/js/user/user_modifyPwd.js?${staticVerison!'1'}"></script>