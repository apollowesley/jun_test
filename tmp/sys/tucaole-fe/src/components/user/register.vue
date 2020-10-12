<template>
    <div>
        <div id="box"></div>
        <div class="cent-box register-box">
            <div class="cent-box-header">
                <h1 class="main-title hide">吐槽了</h1>
                <h2 class="sub-title">不吐槽不快乐 - No Tucao is not happy</h2>
            </div>

            <div class="cont-main clearfix">
                <div class="index-tab">
                    <div class="index-slide-nav">
                        <a href="javascript:;" @click='router("/login")'>登录</a>
                        <a href="javascript:;" @click='router("/register")' class="active">注册</a>
                        <div class="slide-bar slide-bar1"></div>
                    </div>
                </div>

                <div class="login form">
                    <div class="group">
                        <div class="group-ipt email">
                            <input v-model="email" type="email" name="email" id="email" class="ipt" placeholder="邮箱地址" required>
                        </div>
                        <div class="group-ipt user">
                            <input v-model="username" type="text" name="user" id="user" class="ipt" placeholder="昵称" required>
                        </div>
                        <div class="group-ipt password">
                            <input v-model="password" type="password" name="password" id="password" class="ipt" placeholder="设置登录密码" required>
                        </div>
                        <div class="group-ipt password1">
                            <input v-model="ensurePwd" type="password" name="ensurePwd" id="password1" class="ipt" placeholder="重复密码" required>
                        </div>
                        <div class="group-ipt verify">
                            <div id="captcha">
                                <p id="wait">正在加载验证码......</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="button">
                    <button type="submit" class="login-btn register-btn" id="button" @click="regist()">注册</button>
                </div>
            </div>
        </div>
        <bottom></bottom>
    </div>
</template>

<script>
    import particles from 'particles.js'
    import bottom from '../common/footer.vue'
    import initGeetest from '../../assets/js/basic/initGeetest.js'
    import Ajax from '../../assets/js/ajax.js'
    import api from '../../assets/js/common/api.js'
    import check from '../../assets/js/common/check.js'

    export default {
        components : {
            bottom
        },
        data() {
            return {
                email: '',
                username: '',
                password: '',
                ensurePwd: ''
            }
        },
        methods : {
            regist() {
                //1、校验参数
                let flag = this.checkParam();
                if(! flag) {
                    return ;
                }
                //2、获取参数
                let param = this.getParam();
                //3、发送ajax
                new Ajax({
                    "url" : api.TC_URL.regist,
                    "data" : param
                })
                .Ajax()
                .then(function(res) {
                    //4、成功后跳转到注册成功，请去激活页面
                    alert("注册成功，快去激活吧骚年");
                })
                .catch(function(err) {
                    // this.$layer.msg(err.msg);
                    alert(err.msg);
                })
            },
            /** 获取参数 */
            getParam() {
                var param = {};
                param.email = this.email;
                param.loginName = this.username;
                param.password = this.password;
                param.ensurePassword = this.ensurePwd;
                param.token = window.sessionStorage.getItem('token');
                param.geetestChallenge = window.sessionStorage.getItem('geetest_challenge');
                param.geetestSeccode = window.sessionStorage.getItem('geetest_seccode');
                param.geetestValidate = window.sessionStorage.getItem('geetest_validate');;
                return param;
            },
            /** 检查参数 */
            checkParam() {
                let gtFlag = window.sessionStorage.getItem('gtFlag');
                if(gtFlag == "false") {
                    this.$layer.msg("验证码不正确");
                    return false;
                }
                if(! check.util.isNull(this.email)) {
                    this.$layer.msg("请输入邮箱地址");
                    return false;
                }
                if(! check.util.isNull(this.username)) {
                    this.$layer.msg("请输入昵称");
                    return false;
                }
                if(! check.util.isNull(this.password)) {
                    this.$layer.msg("请输入登录密码");
                    return false;
                }
                if(! check.util.isNull(this.ensurePwd)) {
                    this.$layer.msg("请输入重复密码");
                    return false;
                }
                if(this.password != this.ensurePwd) {
                    this.$layer.msg("两次密码输入不一致");
                    return false;
                }
                return true;
            },
            /** 路由跳转 */
            router(url) {
                this.$router.push({path : url});
            }
        },
        mounted() {
            //验证码
            initGeetest.util.getGt();
            particlesJS.load('box', 'static/js/particles.data');

            //防止恶意攻击
            window.sessionStorage.setItem('gtFlag', false);
            
        }
    }
</script>

<style lang="stylus">
    @import "../../assets/css/register-login.styl"
    .cent-box-header .main-title
        background url('../../assets/image/logo.png') no-repeat
    .geetest_radar_btn {
	    border-top: 1px solid #ccc;
    }
</style>