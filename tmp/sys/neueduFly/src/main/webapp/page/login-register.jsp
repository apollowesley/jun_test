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
    <title>登录和注册界面</title>
    <link rel="stylesheet" href="commons/layui/css/layui.css" />
    <link rel="stylesheet" href="style/global.css" />
    <link rel="stylesheet" href="style/login-register.css" />
    <link rel="stylesheet" href="commons/icon-font.css" />
    <script src="commons/layui/layui.js"></script>
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
                <li class="layui-nav-item"><a href="javascript:gotoLoginAndRegister('register');">注册</a></li>
                <li class="layui-nav-item"><a href="javascript:;" class="iconfont icon-qq"></a></li>
                <li class="layui-nav-item"><a href="javascript:;" class="iconfont icon-weibo"></a></li>
            </ul>
        </div>
    </div>

    <!-- 登录和注册表单信息 -->
    <div class="layui-container">
        <div class="lay-tabs">
            <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
                <ul class="layui-tab-title">
                    <li class="layui-this">登入</li>
                    <li>注册</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show user-login">
                        <form class="layui-form layui-form-pane" action="">
                            <div class="layui-form-item">
                                <label class="layui-form-label">手机/邮箱</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="tel" required lay-verify="required" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid layui-word-aux">使用手机或者邮箱中的任意一个均可（若采用手机，请确保你的帐号已绑定过该手机）</div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" name="pwd" required lay-verify="required" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">图形码</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="code" required lay-verify="required" autocomplete="off" class="layui-input loginVerifyCode">
                                </div>
                                <div class="layui-form-mid layui-word-aux">
                                    <img class="code loginImgCode" src="verifyCodeImg" />
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <button type="button" class="layui-btn" lay-submit lay-filter="login">立即登录</button>
                                <a href="" class="forget-pwd">忘记密码？</a>
                            </div>
                            <div class="layui-form-item fly-apps">
                                <span>或者使用社交账号登入</span>
                                <span class="iconfont icon-qq"></span>
                                <span class="iconfont icon-weibo"></span>
                            </div>                                     
                        </form>
                    </div>
                    <%--注册--%>
                    <div class="layui-tab-item user-register">
                        <form class="layui-form layui-form-pane" action="">
                            <div class="layui-form-item">
                                <label class="layui-form-label">手机</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="tel" required lay-verify="required|phone" autocomplete="off" class="layui-input telCode">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">图形码</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="imgCode" required lay-verify="required" autocomplete="off" class="layui-input verifyCode">
                                </div>
                                <div class="layui-form-mid layui-word-aux">
                                    <img class="code verifyCodeImg" src="verifyCodeImg" />
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">验证码</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="telCode" required lay-verify="required" placeholder="请输入手机短信验证码" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid layui-word-aux">
                                    <button type="button" class="layui-btn layui-bg-blue tel-code">获取验证码</button>
                                </div>
                            </div>
                            <div class="layui-form-item user-nike">
                                <label class="layui-form-label">昵称</label>
                                <div class="layui-input-inline">
                                    <input type="text" name="nickName" required lay-verify="required" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid layui-word-aux">你在社区的名字</div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" name="pwd" required lay-verify="required" autocomplete="off" class="layui-input">
                                </div>
                                <div class="layui-form-mid layui-word-aux">6到16个字符</div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">确认密码</label>
                                <div class="layui-input-inline">
                                    <input type="password" name="pwd2" required lay-verify="required" autocomplete="off" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item register-checkbox">
                                <input type="checkbox" name="" lay-skin="primary" checked>
                                <span class="layui-word-aux">同意用户服务条款</span>
                            </div>
                            <div class="layui-form-item">
                                <button type="button" class="layui-btn" lay-submit lay-filter="register">立即注册</button>
                            </div>
                            <div class="layui-form-item fly-apps">
                                <span>或者直接使用社交账号快捷注册</span>
                                <span class="iconfont icon-qq"></span>
                                <span class="iconfont icon-weibo"></span>
                            </div>                                     
                        </form>
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
            <a href="" class="upyun"><img src="imgs/upyun.png" alt="又拍云" /></a>
            <a href="" class="maoyun"><img src="imgs/168_1559291577683_9348.png" alt="猫云" /></a>
        </p>
    </div>
    <script src="js/global.js"></script>
    <script>
        layui.use(['element','util','form','jquery','layer'], function () {
            let element = layui.element,
                util = layui.util,
                form = layui.form,
                $ = layui.jquery,
                layer = layui.layer;

            //切换验证码
            $(".verifyCodeImg,.loginImgCode").click(function(){
                $(this).attr("src","verifyCodeImg?ran="+Math.random());
            });

            //发短信
            let seconds = 60;
            $(".tel-code").click(function(){
                if(seconds >0 && seconds < 60){  //在发送短信区间，不响应
                    return;
                }
                let _this = this;
                //判断输入的验证码是否正确
                $.post('user/checkCodeImg',{verifyCode:$(".verifyCode").val()},function(res){
                    if(res.code === 1 ){
                        $(".verifyCodeImg").attr("src","verifyCodeImg?ran="+Math.random());
                        layer.msg(res.msg,{icon: 5,anim: 6,skin:"layui-layer-molv"});
                        return;
                    }
                    let telVal = $(".telCode").val();
                    //发短信
                    $.get('user/sendSms',{tel:telVal},function(res){
                        $(".verifyCodeImg").attr("src","verifyCodeImg?ran="+Math.random());
                        if(res.code === 1){
                            layer.msg(res.msg,{icon: 5,anim: 6,skin:"layui-layer-molv"});
                        }
                        layer.msg(res.msg,{icon: 1,anim: 6,skin:"layui-layer-molv"});

                        //禁用图标，倒计时
                        $(_this).attr("class","layui-btn tel-code layui-btn-disabled");

                        let secondFn = window.setInterval(function(){
                            seconds --;
                            $(_this).text(seconds+"秒后，重新发送");
                            if(seconds === 0){
                                window.clearInterval(secondFn);
                                $(_this).attr("class","layui-btn layui-bg-blue tel-code").text("获取验证码");
                                seconds = 60;
                            }
                        },1000);
                    });
                });
            });

            //注册
            form.on('submit(register)',function(data){
                //两次密码的验证
                if(data.field.pwd !== data.field.pwd2){
                    layer.msg('两次密码输入不一致',{icon:3});
                    return;
                }
                $.post('user/register',data.field,function(res){
                     layer.msg(res.msg);
                });
            });

            //登录
            form.on('submit(login)',function(res){
                //验证码校验
                $.post('user/checkCodeImg',{verifyCode:res.field.code},function(data){
                    if(data.code === 1){
                        layer.msg(data.msg);
                        $(".loginImgCode").attr("src","verifyCodeImg?ran="+Math.random());
                        return;
                    }

                    //登录
                    $.post('user/login',res.field,function(result){
                        if(result.code === 1){
                            layer.msg(result.msg);
                            $(".loginImgCode").attr("src","verifyCodeImg?ran="+Math.random());
                            return;
                        }
                        window.location.href = "index.jsp";
                    });
                })
            });


            //固定块实例
            util.fixbar({
                bar1: '&#xe642;', 
                bgcolor:'#5FB878',
                showHeight:300, //TOP按钮的滚动条高度临界值。默认：200
                click: function(type){
                    console.log(type);
                    if(type === 'bar1'){
                        alert('点击了bar1')
                    }
                }
            });

        });
    </script>
</body>
</html>