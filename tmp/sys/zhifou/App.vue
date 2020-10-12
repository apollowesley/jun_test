<script>
    import Request from './request/index.js'
    // import Time from './common/yc_js/Time.js'
    import {
        Time,
        Storage
    } from './common/yc_js/index.js'

    export default {

        data() {
            var appid = '';
            var imei = '';
            // #ifdef APP-PLUS
            appid = plus.runtime.appid;
            imei = plus.device.imei;
            // #endif
            return {
                appInfo: {
                    name: "zf",
                    appid,
                    imei,
                    version: '1.0.7'

                },
                pingSocket: '',
            }
        },
        computed: {

            page() {
                var pages = this.$store.getters.pages || [];
                return pages[0] || '';
            },
            msg() {
                return this.$store.getters.msg;
            },
            token() {
                return this.$store.getters.token; //需要监听的数据
            },
            socketOpen() {
                return this.$store.getters.socketOpen;
            }
        },

        watch: {

            page(val) {
                console.log(JSON.stringify(val))
                if (typeof this.shuaxinMsg === 'function') {
                    this.shuaxinMsg()
                }

            },
            msg(val) {
                // console.log(val)
                if (typeof this.shuaxinMsg === 'function') {
                    this.shuaxinMsg()
                }
            },
            socketOpen(val) {
                if (val) {
                    if (typeof this.SocketPing === 'function' && this.$store.hasLogin) {
                        this.SocketPing()
                    }
                } else {
                    this.pingSocket = null;
                }
            },
            token: {
                handler: function(newVal, oldVal) { //特别注意，不能用箭头函数，箭头函数，this指向全局

                    if (this.$store.getters.hasLogin == true) {
                        this.$store.dispatch('socketConnect')
                        // this.SocketPing()
                    }
                },
                deep: true //深度监听
            }
        },
        methods: {
            start() {
                var msgList = Storage.Sync.get('msgList');
                this.$store.commit('setMsgList', msgList);
                console.log('App Launch');
                // return;
                // token 和用户信息
                var token = this.$store.getters.token;
                var userInfo = this.$store.getters.userInfo;
                var nowTime = Time.getTime();

                // console.log(this.$store.getters.hasLogin)
                if (this.$store.getters.hasLogin && token && token.token && token.expires && token.expires > nowTime) {
                    // 每次刷新需要重新设置login信息
                    this.$store.commit('login', null);
                    this.$store.commit('token', token);
                    this.$store.commit('userInfo', userInfo);
                    //有效期少于600秒 请求更新token
                    if (token.expires - nowTime < 300) {
                        //这里重置token
                        return Request.get('resetLogin')
                            .then((res) => {
                                console.log(res)
                                var rt = res.data;
                                if (rt.code == 200) {

                                    this.$store.commit('login');
                                    //家里保持长期登录环境
                                    this.$store.commit('userInfo', rt.data.userInfo);
                                    this.$store.commit('token', rt.data.token);

                                } else {
                                    this.$store.commit('logOut');
                                }


                            });
                    }

                } else {
                    this.$store.commit('logOut');
                }
            },
            shuaxinMsg() {
                // console.log(this.page)
                // 刷新未读消息数量
                var page = this.page;

                var actionPage = {
                    chatIndex: true,
                    chatChat: true,
                    friendIndex: true,
                    userWode: true,
                    mainMain: true
                }
                // console.log([page,this.msg,actionPage[page]])
                if (!page || actionPage[page]) {

                    var number = this.$store.getters.msg;
                    // console.log(number)
                    if (number > 0) {
                        uni.setTabBarBadge({
                            index: 0,
                            text: JSON.stringify(number),
                        })
                    } else {
                        uni.removeTabBarBadge({
                            index: 0,
                            fail: function(e) {
                                // console.log(e)
                            }
                        })
                    }
                }

            },
            // 保持socket连接 自动发送心跳 30秒
            SocketPing() {
                // clearInterval(SocketPing);
                var self = this;
                this.pingSocket = setInterval(function() {
                    uni.sendSocketMessage({
                        data: 'ping'
                    });
                    // self.$store.commit('socketSend', 'ping')
                }, 10000)
            }
        },
        onLaunch: function() {
 
            uni.getSystemInfo({
                success: res => {
                    var windowSize = {
                        windowWidth: res.windowWidth,
                        windowHeight: res.windowHeight,
                        screenWidth: res.screenHeight,
                        screenHeight: res.screenHeight
                    }
                    // console.log(JSON.stringify(windowSize))
                    this.$store.commit('windowSize', windowSize)
                    this.$store.commit('systemInfo', res)
                }
            });
            // 监听窗口变化
            // uni.onWindowResize((res) => {
            //     var windowSize = {
            //         windowWidth: res.size.windowWidth,
            //         windowHeight: res.size.windowHeight,
            //         screenWidth: res.size.screenHeight,
            //         screenHeight: res.size.screenHeight
            //     }
            //     this.$store.commit('windowSize', windowSize)
            //     // console.log(res)
            // })
            Storage.init(); //删除过期缓存
            // #ifdef APP-PLUS

            // 更新版本

            var appInfo = this.appInfo || {
                name: "zf",
                appid: plus.runtime.appid,
                version: plus.runtime.version,
                imei: plus.device.imei
            }
            var version = appInfo.version || '1.0.0';
             // console.log(JSON.stringify(appInfo))
            Request("Device_edition", {
                data: appInfo
            }).then(e => {
                // console.log(JSON.stringify(data))
                var data = e.data
                if (data.code == 200) {
                    var info = data.data;
                    if (info.version && info.version != version) {
                        info.message = info.message || '';
                        var name = info.name || appInfo.name || '';
                        uni.showModal({
                            title: "发现新版本,立即更新?",
                            content: name + ':当前版本:' + version + ",更新版本:" + info.version + ";\n" +
                                info.message,
                            success: (res) => {
                                if (res.confirm) {
                                    uni.reLaunch({
                                        url: '/pages/update/index?query=' +
                                            encodeURIComponent(JSON.stringify(info))
                                    });
                                } else if (res.cancel) {

                                }
                            }
                        })

                    }
                } else {
                    // 启动
                    this.start()
                }
                // console.log(JSON.stringify(e))
            })
            // #endif
            // #ifndef APP-PLUS
            // 启动
            
            this.start()
            // #endif

        },
        onShow: function() {
            setTimeout(this.shuaxinMsg, 300)
            // console.log('App Show');
        },
        onHide: function() {
            // console.log('App Hide');
        }
    }
</script>

<style>
    @import "./common/uni.css";

    @font-face {
        font-family: 'iconfont';
        src: url('~static/font_1207671_hp3m9dzgf86.ttf') format('truetype');
    }

    page {
        background-color: #F4F5F6;
        height: 100%;
        font-size: 28upx;
        line-height: 1.8;
    }

    .iconfont {
        font-family: iconfont;
        /* margin-left: 20upx; */
    }
</style>
