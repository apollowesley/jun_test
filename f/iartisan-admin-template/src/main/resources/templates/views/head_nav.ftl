<div class="layui-logo"><img src="/assets/iartisan/images/face.jpg"/>${_title!''}</div>
<ul class="layui-nav layui-layout-left">
    <li class="layui-nav-item">
        <a href="javascript:;" id="switchNav"><i class="layui-icon">&#xe668;</i></a>
    </li>
    <#-- <li class="layui-nav-item"><a href="">最新活动</a></li>-->
</ul>
<ul class="layui-nav layui-layout-right">
    <li class="layui-nav-item" pc>
        <a href="javascript:;" lay-tips="点击清除缓存" id="clearCache"><i class="layui-icon">&#xe65f;</i></a>
    </li>
    <!-- 消息提醒 -->
    <li class="layui-nav-item">
        <a href="javascript:void(0);" id="btnNotify">
            <i class="fa layui-icon layui-icon-notice"></i>
            <span class="layui-badge notify"
                  style="border-radius: 50%;position: absolute !important;margin-top: -24px !important; display: none;">1</span>
        </a>

    </li>
    <li class="layui-nav-item">
        <a href="javascript:void(0);" id="btnFull">
            <i class="fa layui-icon layui-icon-screen-full"></i>
        </a>
    </li>
    <li class="layui-nav-item" id="userInfo">
        <a href="javascript:;"><img src="/assets/iartisan/images/face.jpg" class="layui-nav-img userAvatar"
                                    width="35" height="35"><cite
                    class="adminName">${_user.userName!''}</cite></a>
        <dl class="layui-nav-child">
            <dd><a href="javascript:;" id="ziliao" attr-data="${_user.userId!''}"><i class="seraph icon-ziliao"
                                                                                     data-icon="icon-ziliao"></i><cite>个人资料</cite></a>
            </dd>
            <dd><a href="javascript:;" data-url="/userSupport/modifyPwdPage"><i class="seraph icon-xiugai"
                                                                                data-icon="icon-xiugai"></i><cite>修改密码</cite></a>
            </dd>
            <dd><a href="javascript:;" class="showNotice"><i
                            class="layui-icon">&#xe645;</i><cite>系统公告</cite><#--<span class="layui-badge-dot"></span>-->
                </a></dd>
            <dd><a href="javascript:;" class="changeTheme"><i
                            class="layui-icon">&#xe66a;</i><cite>切换主题</cite>
                </a></dd>
            <dd pc><a href="javascript:;" class="functionSetting"><i
                            class="layui-icon">&#xe620;</i><cite>功能设定</cite><span class="layui-badge-dot"></span></a>
            </dd>
            <dd><a href="/logout" class="signOut"><i class="seraph icon-tuichu"></i><cite>退出</cite></a></dd>
        </dl>
    </li>
</ul>