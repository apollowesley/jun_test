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
                        <a class="active">修改密码</a>
                    </div>
                </div>

                <div class="login form">
                    <div class="group" id="emailGroup" v-show="sendEmailShow">
                        <div class="group-ipt email">
                            <input type="text" name="email" id="email" class="ipt" placeholder="邮箱地址" required>
                        </div>
                        <div class="group-ipt verify">
                            <div id="captcha">
                                <p id="wait">正在加载验证码......</p>
                            </div>
                        </div>
                    </div>
                    <div class="group" id="contentGroup" v-show="confirmShow">
                        <div class="group-ipt password">
                            <input type="password" name="password" class="ipt" placeholder="设置登录密码" required>
                        </div>
                        <div class="group-ipt password1">
                            <input type="password" name="password1" id="password1" class="ipt" placeholder="重复密码" required>
                        </div>
                        <div class="group-ipt verify">
                            <input type="text" name="verify" id="verify" class="ipt" placeholder="输入邮箱验证码" required>
                            <button class="repeat-send" @click="repeatSend()">没收到？</button>
                        </div>
                    </div>
                </div>

                <div class="button">
                    <button class="login-btn register-btn" id="button" v-show="sendEmailShow" @click="sendEmail()">发送邮件</button>
                    <button class="login-btn register-btn" id="button" v-show="confirmShow" @click="confirm()">确定修改</button>
                </div>

                <div class="remember clearfix">
                    <label>
                        <a href="javascript:;" style="color:#0f88eb;opacoty:1;" @click='router("/login")'>去登陆</a>
                    </label>
                </div>
            </div>
        </div>
        <bottom></bottom>
    </div>
</template>

<script>
    import particles from 'particles.js'
    import bottom from '../common/footer.vue'
    import Ajax from '../../assets/js/ajax.js'
    import initGeetest from '../../assets/js/basic/initGeetest.js'
    import api from '../../assets/js/common/api.js'

    export default {
        components : {
            bottom
        },
        data() {
            return {
                sendEmailShow: true,
                confirmShow: false
            }
        },
        methods: {
            /** 发送邮件按钮 */
            sendEmail() {
                this.confirmShow = true;
                this.sendEmailShow = false;
            },
            /** 没收到？按钮  */
            repeatSend() {
                this.confirmShow = false;
                this.sendEmailShow = true;
            },
            confirm() {
                // return new Promise((resolve) => {
                //     new Ajax({
                //         "url" : "http://localhost:9090/common/captcha/getCaptcha",
                //         "method" : "GET"
                //     })
                //     .Ajax()
                //     .then(function(res) {
                //         resolve(res);
                //         console.info("res=", res);
                //     })
                //     .catch(function(err) {
                //         console.info(err);
                //     })
                // })
            },
            /** 获取验证码 */
            getGeetest(res) {
                initGeetest(res);
            },
            /** 路由跳转 */
            router(url) {
                this.$router.push({'path': url});
            }
        },
        mounted() {
            //验证码
            initGeetest.util.getGt();
            // Promise.all([
            //     this.confirm()
            // ])
            particlesJS.load('box', 'static/js/particles.data');
        }
    }
</script>

<style lang="stylus">
    @import "../../assets/css/register-login.styl"
    .cent-box-header .main-title
        background url('../../assets/image/logo.png') no-repeat   
    .repeat-send 
        background-color: #87CEEB;
        border: none;
        color: white;
        padding: 3px 10px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 50%;
        position: absolute;
        top: 5px;
        right: 10px;
        outline:none;
</style>