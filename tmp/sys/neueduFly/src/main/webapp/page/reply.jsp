<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <base href="<%=basePath %>"/>
    <title>Fly</title>
    <link rel="stylesheet" href="../commons/layui/css/layui.css" />
    <link rel="stylesheet" href="../style/index.css" />
    <link rel="stylesheet" href="../style/global.css" />
    <link rel="stylesheet" href="../commons/icon-font.css" />
    <script src="../commons/layui/layui.js"></script>
</head>

<body>
    <!-- 标题 -->
    <div class="fly-header layui-bg-black">
        <div class="layui-container">
            <a class="fly-logo" href="javascript:gotoIndex();"><img src="../imgs/logo.png" alt="Fly社区" /></a>
            <ul class="layui-nav fly-nav">
                <li class="layui-nav-item layui-this"><a href="javascript:gotoIndex();"><i class="iconfont icon-jiaoliu"></i>交流</a>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;"><i class="iconfont icon-chanpin"></i>专区<span
                            class="layui-badge-dot"></span></a>
                    <dl class="layui-nav-child">
                        <!-- 二级菜单 -->
                        <dd><a href="javascript:;">layuiAdmin</a></dd>
                        <dd><a href="javascript:;">LayIM</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item"><a href="javascript:;"><i class="iconfont icon-ui"></i>框架</a></li>
            </ul>
            <ul class="layui-nav fly-nav-user">
                <li class="layui-nav-item"><a href="javascript:gotoLoginAndRegister('login');" class="iconfont icon-touxiang"></a></li>
                <li class="layui-nav-item"><a href="javascript:gotoLoginAndRegister('login');">登入</a></li>
                <li class="layui-nav-item"><a href="javascript:gotoLoginAndRegister('login');">注册</a></li>
                <li class="layui-nav-item"><a href="javascript:;" class="iconfont icon-qq"></a></li>
                <li class="layui-nav-item"><a href="javascript:;" class="iconfont icon-weibo"></a></li>
            </ul>
        </div>
    </div>
    <!-- 快捷导航 -->
    <div class="fly-penel fly-penel-color">
        <div class="layui-container">
            <ul class="layui-nav fly-panel-left fly-penel-color">
                <li class="layui-nav-item layui-this"><a href="javascript:;">首页</a></li>
                <li class="layui-nav-item"><a href="javascript:;">提问</a></li>
                <li class="layui-nav-item"><a href="javascript:;">分享</a></li>
                <li class="layui-nav-item"><a href="javascript:;">讨论</a></li>
                <li class="layui-nav-item"><a href="javascript:;">建议</a></li>
                <li class="layui-nav-item"><a href="javascript:;">公告</a></li>
                <li class="layui-nav-item"><a href="javascript:;">动态</a></li>
            </ul>
            <div class="fly-panel-right">
                <!-- 搜索按钮 -->
                <span class="lay-search">
                    <i class="layui-icon layui-icon-search"></i>
                </span>
                <a href="sendPost.jsp" class="layui-btn">发表新帖</a>
            </div>
        </div>
    </div>
    
	<div class="layui-container">
        <div class="layui-row layui-col-space15">
            <!-- 左侧界面信息 -->
            <div class="layui-col-md8">
               <!-- 帖子列表 -->
                <div class="fly-panel-style">
                    帖子信息
                </div>
				<div class="fly-panel-style">
				    帖子回复
				</div>
            </div>
            <!-- 右侧界面信息 -->
            <div class="layui-col-md4">
                <!-- 温馨通道 -->
                <div class="fly-panel-style">
                    <div class="fly-panel-title">温馨通道</div>
                    <div class="layui-row fly-panel-content">
                        <div class="layui-clear fly-panel-content-border">
                            <div class="layui-col-xs6">
                                <div><a href="javascript:;">组件平台</a></div>
                            </div>
                            <div class="layui-col-xs6">
                                <div><a href="javascript:;">年度案例</a></div>
                            </div>
                            <div class="layui-col-xs6">
                                <div><a href="javascript:;">精铁集锦</a></div>
                            </div>
                            <div class="layui-col-xs6">
                                <div><a href="javascript:;">Git仓库</a></div>
                            </div>
                            <div class="layui-col-xs6">
                                <div><a href="javascript:;">layui模板</a></div>
                            </div>
                            <div class="layui-col-xs6">
                                <div><a href="javascript:;">关于飞吻</a></div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 签到 -->
                <div class="fly-panel-style">
                    <div class="fly-panel-title">
                        <span class="layui-breadcrumb checked-info" lay-separator="|">
                            <a href="javascript:;" class="checked-this">签到</a>
                            <a href="javascript:;">说明</a>
                            <a href="javascript:;">活跃榜<span class="checked-dot layui-badge-dot"></span></a>
                        </span>
                    </div>
                    <div class="layui-row fly-panel-content checked-panel">
                        <div class="layui-btn layui-btn-danger">今日签到</div>
                    </div>
                </div>
                <!-- 赞助商 -->
                <div class="fly-panel-style">
                    <div class="fly-panel-title">
                        <span class="layui-breadcrumb checked-info" lay-separator="|">
                            <a href="" class="checked-this">钻级赞助商</a>
                            <a href="">我要加入</a>
                        </span>
                    </div>
                    <div class="layui-row fly-panel-content">
                        <div class="zanzhushang">
                            <img src="../imgs/upload/sm-168_1557540349142_16350.png" alt="赞助商" />
                            <img src="../imgs/upload/sm-168_1561705136068_74941.jpg" alt="赞助商" />
                            <img src="../imgs/upload/sm-168_1563368810488_56384.jpg" alt="赞助商" />
                        </div>
                    </div>
                </div>
                <!-- 回帖周榜 -->
                <div class="fly-panel-style">
                    <div class="fly-panel-title">
                        回帖周榜
                    </div>
                    <div class="layui-row fly-panel-content">
                        <div class="answer-list layui-clear">
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/3.jpg" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/2.jpg" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/1.jpg" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/37921464.jpg" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/5g.jpg" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/4g.jpg" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/6174336.jpg" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/8.jpg" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/88g.png" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                            <div class="answer">
                                <a href="">
                                    <img src="../imgs/user-header/9g.jpg" alt="" />
                                    <cite>HiTerry</cite>
                                    <i>82次回答</i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 本周热议 -->
                <div class="fly-panel-style">
                    <div class="fly-panel-title">
                        本周热议
                    </div>
                    <div class="layui-row fly-panel-content">
                        <div class="week-hot">
                            <div class="hot-note">
                                <a href="">自己写的json数据layui table怎么分页</a>
                                <span>
                                    <i class="iconfont icon-pinglun1"></i>24
                                </span>
                            </div>
                            <div class="hot-note">
                                <a href="">MyLayui plan</a>
                                <span>
                                    <i class="iconfont icon-pinglun1"></i>24
                                </span>
                            </div>
                            <div class="hot-note">
                                <a href="">Layui 自定义主题工具升级！！！</a>
                                <span>
                                    <i class="iconfont icon-pinglun1"></i>24
                                </span>
                            </div>
                            <div class="hot-note">
                                <a href="">LayUI架构重大问题.必须重新规划设计,不然组件越多越烂泥</a>
                                <span>
                                    <i class="iconfont icon-pinglun1"></i>24
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 友情链接 -->
                <div class="fly-panel-style">
                    <div class="fly-panel-title">
                        友情链接
                    </div>
                    <div class="layui-row fly-panel-content">
                        <div class="fly-link">
                            <a href="javascript:;">WebIM</a>
                            <a href="javascript:;">JSON在线工具</a>
                            <a href="javascript:;">小微OA</a>
                            <a href="javascript:;">FineUI开源控件</a>
                            <a href="javascript:;">Java菜鸟社区</a>
                            <a href="javascript:;">掘金开发者社区</a>
                            <a href="javascript:;">简历模板</a>
                            <a href="javascript:;">TouchUI移动框架</a>
                            <a href="javascript:;">DCloud开发者社区</a>
                            <a href="javascript:;">猫云</a>
                            <a href="javascript:;">t-io</a>
                            <a href="javascript:;" style="color: #01AAED;">申请友链 </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 版权声明 -->
    <div class="fly-footer">
        <p>
            <a href="">Fly 社区</a>
            2019 ©
            <a href="">layui.com</a>
        </p>
        <p>
            <a href="javascript:;">付费计划</a>
            <a href="javascript:;">组件平台</a>
            <a href="javascript:;">模板市场</a>
            <a href="javascript:;">年度案例</a>
            <a href="javascript:;">公众号</a>
        </p>
        <p>感谢以下服务商提供云加速赞助</p>
        <p>
            <a href="" class="upyun"><img src="../imgs/upyun.png" alt="又拍云" /></a>
            <a href="" class="maoyun"><img src="../imgs/168_1559291577683_9348.png" alt="猫云" /></a>
        </p>
    </div>

    <script src="../js/index.js"></script>
    <script src="../js/global.js"></script>
</body>

</html>