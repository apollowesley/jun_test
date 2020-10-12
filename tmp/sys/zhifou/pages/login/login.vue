<template>
    <view class="page-body">
        <view class="uni-input-group">

            <view class="uni-input-row">
                <view class="uni-label uni-flex-item">
                    账号
                </view>
                <view v-if="historyLoginShow" style="position: absolute;background: rgba(250,250,150,0.5);left: 50%;z-index:3;top: 70upx;">
                    <view class="uni-flex" style="padding: 10upx;border-bottom: solid 1px #f3f3f3;" v-for="(acc,idx) in historyLogin"
                        :key="idx">
                        <view class="uni-flex-item" @tap="tapHistoryLogin(acc)">{{acc.username}}</view>
                    </view>
                </view>
                <view class="" style="position: absolute;right: 40upx;z-index:3;top:30upx">
                    <text v-if="!historyLoginShow" @tap="historyLoginShow=true">▼</text>
                    <text v-else @tap="historyLoginShow=false">✕</text>
                </view>
                <input type="text" class="uni-flex-item yc-input" v-model="account" :placeholder="'请输入'+loginList[sloginType]">
                <view v-if="sloginType=='code'" class="uni-label">
                    <view v-if="Countdown" id='msg-type' @tap="sendMessge">获取验证码</view>
                    <view v-else id='msg-type'>{{CountdownBt}}秒</view>
                </view>
            </view>
            <view class="uni-input-row uni-flex" v-if="isShowCaptcha">
                <view style="flex: 1;">
                    <image class="uni-label" style="height: 60upx;" :src="captcha" mode="aspectFit" @tap="resetCaptcha"></image>
                </view>

                <input class="uni-flex-item yc-input" type="text" v-model="inputCaptcha" placeholder="请输入图片验证码"></input>
            </view>
            <view v-if="sloginType=='psd'" class="uni-input-row uni-flex ">

                <view class="uni-label uni-flex-item">
                    密码
                </view>
                <input type="text" class="uni-flex-item yc-input" password="true" v-model="password" placeholder="请输入密码">
            </view>

            <view v-else class="uni-input-row  uni-flex">
                <view class="uni-label uni-flex-item">
                    验证码
                </view>
                <input type="text" class="uni-flex-item yc-input" v-model="code" placeholder="请输验证码">
            </view>

            <view class="action-row " style="height: 80upx;line-height: 80upx;">
                <view class="">
                    登录方式
                </view>
                <view class="" v-for="(item,key) in loginList" :key="key">
                    <navigator type="primary" @tap="loginType(key)">{{item}}</navigator>
                </view>

            </view>

        </view>

        <button type="primary" size="default" style="width: 100%;" @tap="startLogin">登录</button>

        <view class="action-row" style="margin-top:100upx;position:;">
            <navigator url="./reg">注册账号</navigator>
            <navigator url="./pwd">忘记密码</navigator>
        </view>
        <view class="oauth-row" v-if="hasProvider" v-bind:style="{top: positionTop + 'px'}">
            <view class="oauth-image" v-for="provider in providerList" :key="provider.value">
                <image :src="provider.image" @tap="oauth(provider.value)"></image>
            </view>
        </view>

    </view>
</template>

<script>
    // import captchaInput from '../../components/template/verify/captchaInput.vue';

    import Request from '../../request/index.js'

    import {
        Validate,
        Img,
        Base64,
        Md5,
        Storage
    } from '../../common/yc_js/'
    // import {
    //     mapGetters,
    //     mapMutations
    // } from 'vuex'

    export default {
        onShow() {
            // console.log(mapGetters)

        },
        data() {
            return {
                historyLoginShow: false,
                captcha: '', //图片验证码地址
                inputCaptcha: 0, //图片验证码输入值
                loginList: {
                    psd: '用户名',
                    mobile: '手机',
                    email: '邮箱'
                },
                sloginType: 'psd', //当前在使用的登陆方法
                Countdown: true, //获取验证码状态开启
                CountdownBt: 0, //验证码有效期
                providerList: [], //第三方快捷登陆方法
                hasProvider: false, //是否开启第三方登陆
                account: '18888888889',
                password: '12341234',
                // passwordMd5:'',
                failAccount: {}, //登陆失败的账号 再次登陆需要验证码
                isShowCaptcha: false,
                code: '', //手机验证码
                positionTop: 0
            }
        },
        filter: {},
        watch: {
            // password(){
            //     
            //    this.passwordMd5= Md5.Md5(Md5.Md5(this.form.username + this.password));
            // },
            captcha() {
                this.isShowCaptcha = this.failAccount[this.account] ? true : false;
            }
        },
        computed: {
            passwordMd5() {
                return Md5.Md5(Md5.Md5(this.account + this.password));
            },
            historyLogin() {
                // console.log(this.$store.getters.historyLogin)
                return this.$store.getters.historyLogin || []
            },
            // ...mapGetters(['forcedLogin']),
        },
        // ...mapMutations(['login','loginOut']),
        onLoad() {
            this.onshow = true;
            // this.$store.commit('pages', this.pageName || "loginLogin")
            this.initPosition();
            this.initProvider();
            this.resetCaptcha();

            // this.account='手机/邮箱/用户名';
        },
        methods: {
            tapHistoryLogin(val) {
                // console.log(val)
                this.account = val.username;
                this.password = val.password;
                this.historyLoginShow = false;
            },
            resetCaptcha() {


                var that = this;
                Request('Captcha', {
                    data: {
                        outType: 'base64'
                    }
                }, {
                    // responseType: 'arraybuffer',
                }).then(res => {
                    if (res.data) {
                        that.isShowCaptcha = true;
                        that.captcha = res.data;
                    }
                }).catch(res => {
                    // console.log(res)
                })
            },
            loginType(type) {
                this.sloginType = type;

            },
            sendMessge() {
                var that = this;
                var inputCaptcha = this.inputCaptcha;
                if (inputCaptcha.length < 4) {
                    uni.showModal({
                        showCancel: false,
                        title: '失败提示',
                        content: '图片验证码至少4位'
                    });
                    return;
                }
                var form = {};

                form.account = that.account;
                form.captcha = inputCaptcha;

                let rule = (tp) => {
                    return [{
                        checkType: tp,
                        name: 'account',
                        errorMsg: '这个错拉'
                    }];
                };

                let data = {
                    account: that.account
                };
                let dt = {
                    type: '',
                    msg: ''
                };
                // console.log(graceChecker.check(data,rule('email')))
                if (Validate.check(data, rule('email'))) {
                    // type='email';
                    dt = {
                        type: 'email',
                        msg: '邮箱'
                    };
                }
                if (Validate.check(data, rule('phoneno'))) {
                    // type='phone';
                    dt = {
                        type: 'phone',
                        msg: '手机'
                    };
                }
                if (dt.type != 'email' && dt.type != 'phone') {
                    uni.showToast({
                        icon: 'none',
                        title: '输入有误,请输入手机号或者邮箱',
                    });
                    return;
                }
                Request('SendSms', {
                    data: form
                }).then(res => {
                    // console.log(res.data);
                    if (res.data.code === 200) {
                        uni.showToast({
                            icon: 'none',
                            title: res.data.message + ',手机验证码:' + res.data.code,
                            duration: 3000
                        });
                        // 倒计时秒
                        function settime(num) {
                            var num = num || 60;
                            if (num > 1) {
                                that.Countdown = false;
                                num--;
                                that.CountdownBt = num;
                                setTimeout(function() {
                                    settime(num)
                                }, 1000)
                            } else {
                                that.Countdown = true;
                            }
                        }
                        settime(90);
                    } else {
                        uni.showModal({
                            showCancel: false,
                            title: '失败提示',
                            content: res.data.message ? res.data.message : '未知'
                        });

                    }

                    that.failAccount[that.account] = that.account;

                    that.resetCaptcha(); //刷新验证码	
                })

            },
            initProvider() {
                const filters = ['weixin', 'qq', 'sinaweibo'];
                uni.getProvider({
                    service: 'oauth',
                    success: (res) => {

                        if (res.provider && res.provider.length) {
                            for (let i = 0; i < res.provider.length; i++) {
                                if (~filters.indexOf(res.provider[i])) {
                                    this.providerList.push({
                                        value: res.provider[i],
                                        image: '../../static/img/' + res.provider[i] + '.png'
                                    });
                                }
                            }
                            this.hasProvider = true;
                        }
                    },
                    fail: (err) => {
                        console.error('获取服务供应商失败：' + JSON.stringify(err));
                    }
                });
            },
            initPosition() {
                /**
                 * 使用 absolute 定位，并且设置 bottom 值进行定位。软键盘弹出时，底部会因为窗口变化而被顶上来。
                 * 反向使用 top 进行定位，可以避免此问题。
                 */
                this.positionTop = uni.getSystemInfoSync().windowHeight - 100;
            },
            startLogin() {
                var that = this;
                var captchaInput = this.inputCaptcha; //图片验证码

                // console.log(captchaInput)
                /**
                 * 客户端对账号信息进行一些必要的校验。
                 * 实际开发中，根据业务需要进行处理，这里仅做示例。
                 */
                if (this.account.length < 5) {
                    uni.showModal({
                        showCancel: false,
                        title: '失败提示',
                        content: '账号最短为 5 个字符'
                    });
                    return;
                }
                if (this.password.length < 5) {
                    uni.showModal({
                        showCancel: false,
                        title: '失败提示',
                        content: '密码最短为 5 个字符'
                    });
                    return;
                }
                /**
                 * 登陆服务器
                 */


                // http://www.api.com/user/account/login/psd?
                // var client= {"client":that.$config.client()};
                var form = {};
                form.username = this.account;
                form.source = this.$store.getters.source;
                // 判断登陆方法
                switch (that.sloginType) {
                    case 'code': //验证码登陆

                        if (that.code.length < 4) {
                            uni.showModal({
                                showCancel: false,
                                title: '失败提示',
                                content: '验证码至少4位'
                            });
                            return;
                        }
                        // var code=that.account+that.code;
                        form.code = that.code;
                        break;
                    default:
                        if (captchaInput.length < 4) {
                            uni.showModal({
                                showCancel: false,
                                title: '失败提示',
                                content: '验证码至少4位'
                            });
                            return;
                        }
                        //密码登陆
                        form.captcha = captchaInput;
                        form.password = this.passwordMd5;
                        break;
                }


                // console.log('login');
                // console.log(Base64.encoder(client.client));
                form.loginType = that.loginType;
                form.source = this.$store.getters.source;
                Request('Login', {
                    data: form
                }).then(res => {
                    var data = res.data;
                    // console.log(data.code)
                    // return;
                    if (data.code === 200) {
                        // var historyLogin = {
                        //     // source:form.source
                        // };
                        var historyLogin = {
                            username: form.username,
                            password: this.password,
                            uid: data.data.userInfo.id
                        }
                        // console.log(data)
                        this.saveLogin(historyLogin)
                        // this.$store.commit('clear')


                        // console.log(this.$store.getters.msgList);
                        this.$store.commit('login', historyLogin);
                        this.$store.commit('longLogin', true);
                        // 
                        //                         //家里保持长期登录环境
                        this.$store.commit('userInfo', data.data.userInfo);
                        this.$store.commit('token', data.data.token);
                        uni.showToast({
                            // icon:'none',
                            title: data.message,
                            duration: 3000
                        });
                        this.toMain('password')
                    } else {
                        // console.log(this.account)
                        this.failAccount[this.account] = this.account;
                        this.resetCaptcha(); //刷新验证码	
                        uni.showModal({
                            showCancel: false,
                            title: '失败提示',
                            content: res.data.message
                        });
                    }

                })

            },
            oauth(value) {
                var self = this;
                this.$store.dispatch('thirdLogin', value).then(res => {
                    if (res) {
                        // console.log(res)
                        this.toMain(res.userInfo.nickname);
                    } else {
                        console.error('授权登录失败：' + JSON.stringify(err));
                    }
                });
            },
            saveLogin(historyLogin) {

                if (historyLogin && typeof historyLogin == 'object') {

                    // 记住账号密码
                    // this.commit('historyLogin', historyLogin)
                    var oldHistoryLogin = Storage.Sync.get('historyLogin') || [];
                    // console.log(historyLogin)
                    var lastuid = null;
                    if (typeof oldHistoryLogin == 'object' && oldHistoryLogin.length) {
                        lastuid = oldHistoryLogin[0].uid || null;
                    }
                    // console.log([lastuid, val])
                    if (historyLogin.uid && lastuid != historyLogin.uid) {
                        // console.log('login——clear')
                        Storage.clear(); //清空缓存
                        // 如果上次登录的用户id不等于当前登录的用户id 
                        this.$store.commit('clear'); //清除上次登录的缓存信息
                        // console.log(this.$store.getters)
                    }
                    var val2 = [historyLogin];
                    for (let i in oldHistoryLogin) {
                        if (oldHistoryLogin[i].username != historyLogin.username) {
                            val2.push(oldHistoryLogin[i])
                        }
                    }
                    var data = [];

                    if (val2.length > 5) {
                        for (var i = 0; i < 5; i++) {
                            data.unshift(val2[i])
                        }
                    } else {
                        data = val2
                    }
                    // state.oldHistoryLogin = data;
                    Storage.Sync.set('historyLogin', data);

                }
                return true
            },
            toMain(userName) {
                // console.log(this.forcedLogin)
                /**
                 * 强制登录时使用reLaunch方式跳转过来
                 * 返回首页也使用reLaunch方式
                 */
                if (this.$store.getters.hasLogin) {

                    var pages = this.$store.getters.pages;
                    // console.log(JSON.stringify(pages))
                    // var lastPage = {
                    //     shopIndex: true,
                    //     shopGood: true
                    // }

                    if (pages.length > 1 ) {
                        uni.navigateBack();
                    } else {

                        uni.reLaunch({
                            url: '/pages/main/main',
                        });
                    }
                }

            }
        },
        components: {
            // captchaInput
        }
    }
</script>

<style>
    .page-body {
        justify-content: flex-start;
    }



    .action-row {
        display: flex;
        flex-direction: row;
        justify-content: center;
    }

    .action-row navigator {
        justify-content: center;
        align-items: center;
        color: #007aff;
        padding: 0 20upx;
    }

    .oauth-row {
        display: flex;
        flex-direction: row;
        justify-content: center;
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
    }

    .oauth-image {
        width: 100upx;
        height: 100upx;
        border: 1upx solid #dddddd;
        border-radius: 100upx;
        margin: 0 40upx;
        background-color: #ffffff;
    }

    .oauth-image image {
        width: 60upx;
        height: 60upx;
        margin: 20upx;
    }
</style>
