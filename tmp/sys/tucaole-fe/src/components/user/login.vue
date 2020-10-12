<template>
    <div>
        <div id="box"></div>
        <div class="cent-box">
            <div class="cent-box-header">
                <h1 class="main-title hide">吐槽了</h1>
                <h2 class="sub-title">不吐槽不快乐 - No Tucao is not happy</h2>
            </div>

            <div class="cont-main clearfix">
                <div class="index-tab">
                    <div class="index-slide-nav">
                        <a href="javascript:;" @click='router("/login")' class="active">登录</a>
                        <a href="javascript:;" @click='router("/register")'>注册</a>
                        <div class="slide-bar"></div>				
                    </div>
                </div>

                <div class="login form">
                    <div class="group">
                        <div class="group-ipt email">
                            <input type="text" name="email" id="email" class="ipt" placeholder="邮箱地址" required>
                        </div>
                        <div class="group-ipt password">
                            <input type="password" name="password" id="password" class="ipt" placeholder="输入您的登录密码" required>
                        </div>
                        <div class="group-ipt verify">
                            <div id="captcha">
                                <p id="wait">正在加载验证码......</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="button">
                    <button type="submit" class="login-btn register-btn" id="button">登录</button>
                </div>

                <div class="remember clearfix">
                    <label class="forgot-password">
                        <a href="javascript:;" @click='router("/forgetPwd")'>忘记密码？</a>
                    </label>
                </div>

                <div style="margin-top:35px;">
                    <a href="javascript:;" @click="qqLogin()"><img src="../../assets/image/qq.png" /></a>
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
    import api from '../../assets/js/common/api.js'

    export default {
        components : {
           bottom
        },
        data() {
            return {
                
            }
        },
        methods : {
            /** qq登录  */
            qqLogin() {
                window.open("http://localhost:18080/qqSDK/login.do","TencentLogin",  "width=450,height=320,menubar=0,scrollbars=1,resizable=1,status=1,titlebar=0,toolbar=0,location=1");
            },
            /** 获取验证码 */
            getGeetest(res) {
                initGeetest(res);
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
        }
    }
</script>

<style lang="stylus">
    @import "../../assets/css/register-login.styl"
    .cent-box-header .main-title
        background url('../../assets/image/logo.png') no-repeat
</style>