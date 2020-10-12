<template>
    <div class="login">
        <header class="header header-wrap">
            <div class="header-bar flex">
                <div class="bar-left flex-item-2" @click="goto('/index')">
                    <i class="iconfont">&#xe637;</i>
                </div>
                <div class="bar-middle flex-item">
                    <h3>登陆</h3>
                </div>
                <div class="bar-right flex-item-2"></div>
            </div>
        </header>
        <section class="page">
            <div class="login-switch flex">
                <div class="flex-item">
                    <span class="active">手机号码登陆</span>
                </div>
                <div class="flex-item">
                    <span>手机验证码登陆</span>
                </div>
            </div>
            <div class="login-filed">
                <div class="filed-wrap flex">
                    <div class="wrap-left flex-item-2">
                        <span>账号</span>
                    </div>
                    <div class="wrap-right flex-item">
                        <input type="text" placeholder="邮箱/已验证的手机号" v-model="user.account"/>
                    </div>
                </div>
                <div class="filed-wrap flex">
                    <div class="wrap-left flex-item-2">
                        <span>密码</span>
                    </div>
                    <div class="wrap-right flex-item">
                        <input type="text" placeholder="请输入密码" v-model="user.password"/>
                    </div>
                </div>
            </div>
            <div class="login-btn">
                <m-button value="登陆" @click.native="login"></m-button>
            </div>
            <div class="login-guide"></div>
            <div class="login-type"></div>
        </section>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                user: {
                    account: '',
                    password: ''
                }
            }
        },
        methods: {
            login() {
                const self = this;
                let data = {
                    account: this.user.account,
                    password: this.user.password
                }
                this.$http.login(data).then(res => {
                    if (res.success === true) {
                        self.$vux.toast.show({
                            text: '登陆成功'
                        });
                        /*
                        * 将token保留在localStorage
                        * */
                        localStorage.setItem('token', res.data.token);
                        /*
                        * 返回上一级页面
                        * */
                        self.back();
                    }
                }, error => {

                });
            }
        }
    }
</script>

<style lang="less" scoped>
    .login {
        position: fixed;
        top: 0px;
        right: 0px;
        bottom: 0px;
        left: 0px;
        background: #fff;
        .header {
            position: fixed;
            top: 0px;
            left: 0px;
            right: 0px;
        }
        .header-wrap {
            height: 45px;
            background: #fff;
            border-bottom: 1px solid #bbb;
            .header-bar {
                height: 100%;
                .bar-left {
                    box-sizing: border-box;
                    padding-left: 10px;
                    i {
                        font-size: 20px;
                    }
                }
                .bar-middle {
                    display: flex;
                    h3 {
                        margin: auto;
                        text-align: center;
                        font-size: 16px;
                        font-weight: 500;
                    }
                }
            }
        }
        .page {
            margin-top: 45px;
            padding: 0px 20px;
            .login-switch {
                margin: 0px -20px;
                border-bottom: 1px solid #eee;
                text-align: center;
                span {
                    display: inline-block;
                    box-sizing: border-box;
                    width: 60%;
                    height: 44px;
                    line-height: 44px;
                    font-size: 14px;
                }
                .active {
                    border-bottom: 2px solid #f23030;
                }
            }
            .login-filed {
                .filed-wrap {
                    border-bottom: 1px solid #eee;
                    padding: 0px 7px;
                    .wrap-left {
                        height: 48px;
                        span {
                            line-height: 48px;
                            font-size: 14px;
                        }
                    }
                    .wrap-right {
                        height: 48px;
                        input {
                            line-height: 48px;
                            font-size: 14px;
                            border: none;
                            outline: none;
                        }
                    }
                }
            }
            .login-btn {
                position: relative;
                padding-top: 30px;
            }
        }
    }
</style>