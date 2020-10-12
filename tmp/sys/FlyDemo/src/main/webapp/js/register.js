layui.use(['form','jquery','layer'], function(){
    let form = layui.form,
        $ = layui.jquery,
        layer = layui.layer;

    //切换验证码
    $(".vimCode").click(function () {
        this.src = "verifyCodeImg?ran="+Math.random();
    });

    //发短信验证
    $(".tel-btn").click(function(){
        let _this = this;
        //判断手机号是否输入，且长度为11
        let tel = $(".tel").val();
        if(tel.length === 0 || tel.length !== 11){
            //手机号不合法
            layer.msg('手机输入不合法', {icon: 5,anim: 6});
            return;
        }
        //判断验证码是否输入，且是否输入正确
        let vimCode = $(".vifiyCode").val();
        if(vimCode.length === 0){
            layer.msg('验证码必须输入', {icon: 5,anim: 6});
            return;
        }

        $.get('user/verifyImgCode',{vimCode:vimCode},function(res){
            if(res.code === 1){
                layer.msg(res.msg, {icon: 5,anim: 6});
                return;
            }
            //改变按钮的样式
            $(_this).attr("class","layui-btn layui-btn-normal tel-btn layui-btn-disabled");
            let seconds = 60;
            let intervalSecond = window.setInterval(function(){
                $(_this).html(--seconds + "s后可以重新发送");
                if(seconds === 0){
                    window.clearInterval(intervalSecond);
                    $(_this).attr("class","layui-btn layui-btn-normal tel-btn");
                    $(_this).html("获取验证码");
                }
            },1000);

            //发送手机验证码
            $.get('user/sendSms',{tel:tel},function(res){
                layer.msg(res.msg, {icon: 6,anim: 6})
            });
        });

    });





    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
});