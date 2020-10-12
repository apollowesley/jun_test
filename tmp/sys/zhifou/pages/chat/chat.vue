<template>
    <view class="uni-page-body">
        <mask-common :state="socketState" text="网络断开-是否重连" :changes="maskCallbake"></mask-common>
        <view class="content" id="content" :style="{height:style.contentViewHeight+'px'}">
            <scroll-view id="scrollview" scroll-y="true" :style="{height:style.contentViewHeight+'px'}"
                :scroll-with-animation="true" :scroll-top="scrollTop">
                <!-- <view :name="user.nickname"></view> -->
                <view class="" style="text-align: center;color: #D9D9D9;" v-if="showGetData">
                    <text v-if="!isGeting" @tap="getDate()">点击获取</text>
                    <text v-else>获取中……</text>
                </view>
                <view>
                    <message-show :headimg="userInfo.headimg" :youHeadimg="user.headimg" :mid="userInfo.id" v-for="(message,index) in chatList"
                        :key="index" :message="message" :id="index">
                    </message-show>
                </view>

                <view id="bottom" style="height: 5upx;"></view>
            </scroll-view>
        </view>
        <view class="foot" style="box-sizing: border-box;height: 0px;">
            <chat-input @send-message="getInputMessage" @focus="inputGetFocus"></chat-input>
        </view>
    </view>
</template>

<script>
    import chatInput from '@/components/template/im-chat/chatinput.vue';
    import messageShow from '@/components/template/im-chat/messageshow.vue';
    // import yc_js from '../../common/yc_js/index.js';
    import config from '@/config/index.js';
    import Request from '@/request/index.js';
    import maskCommon from "@/components/template/mask/common.vue"
    var socketOpen = false;
    var socketMsgQueue = [];
    import {Time} from "@/common/yc_js/index.js"

    export default {
        components: {
            // chatList,
            maskCommon,
            chatInput,
            messageShow
        },
        onShow() {
            this.$store.commit('pages', this.pageName || "chatChat")
            setTimeout(this.scrollToBottom, 100);

        },
        data() {
            return {
                showGetData: true, //是否允许向服务器请求获取更多
                isGeting: false,
                chatMsg: [],
                // name:'xcecd@qq.com',
                user: {
                    uid: "10000",
                    headimg: "logo.png",
                    nickname: "匿名"
                },
                style: {
                    pageHeight: 0,
                    contentViewHeight: 0,
                    footViewHeight: 90,
                    mitemHeight: 0,
                },
                scrollTop: 0,
                messages: [{
                    user_id:1234,
                    user: 'home',
                    type: 'head', //input,content 
                    content: '你好!'
                }]
            }
        },

        watch: {
            time(e, old) {
                // console.log(e)
                if (e > old) {
                    setTimeout(this.scrollToBottom, 10);
                }
            },

        },
        computed: {

            socketState() {

                return this.socketOpen ? false : true;
            },
            socketOpen() {
                // console.log(this.$store.getters.socketOpen)
                return this.$store.getters.socketOpen;
            },
            windowSize() {
                return this.$store.getters.windowSize
            },
            time() {
                var chatList = this.chatList || []
                if (chatList.length > 0 && chatList[0]) {
                    return chatList[0].time
                } else {
                    var timestamp = Date.parse(new Date());
                    return parseInt(timestamp / 1000)
                }
            },
            chatList: {
                get() {
                    // console.log(this.chatMsg)
                    var chat = this.$store.getters.chat;
                    // console.log(chat)
                    // return []
                    var oldTime=parseInt(Date.now()/1000);
                    if (chat && typeof chat == 'object') {
                        var newChat = chat.filter(socketMessage => {
                            var time = null;
                            var cha= Math.abs(oldTime-socketMessage.time)
                            // console.log(cha)
                            if (cha >600 ) {
                                oldTime=socketMessage.time
                                // 300秒以前的数据显示时间
                                time = Time.dateTimeformat(socketMessage.time)
                            }
                            // console.log(time)
                            socketMessage.datetime = time
                            if ((socketMessage.to_uid == this.user.uid && this.userInfo.id == socketMessage
                                    .uid) ||
                                (
                                    socketMessage.uid ==
                                    this.user.uid && this.userInfo.id == socketMessage.to_uid)) {
                                return socketMessage
                            }
                            // this.chatMsg

                        });
                        function sortTime(a, b) {
                            return a.time-b.time
                        }
                        newChat.sort(sortTime);
                        return newChat || []
                    } else {
                        return null
                    }
                },
            },
            userInfo() {
                return this.$store.getters.userInfo;
            },
            socketMessage() {
                return this.$store.getters.socketMessage
            }
        },
        onBackPress: function(e) {
            // 监听返回键， 去除当前连接用户信息状态
            var toUser = this.$store.getters.connectionUser;
            // console.log(toUser)
            var user = {};
            for (var p in toUser) {
                user[p] = null;
            }
            this.$store.commit('connectionUser', user)
            // console.log([toUser,this.$store.getters.connectionUser])
        },
        onLoad: function(event) {
            // console.log(event)
            try {
                this.query = JSON.parse(decodeURIComponent(event.query));
            } catch (error) {
                this.query = JSON.parse(event.query);
            }
            if (!this.$store.getters.hasLogin) {
                return
            }
            // console.log(this.query)

            uni.setNavigationBarTitle({
                title: this.query.nickname
            });
            // this.time = parseInt(Date.now() / 1000);

            var query = this.query || this.$store.getters.connectionUser;

            let winHeight = uni.getSystemInfoSync().windowHeight;
            //创建节点选择器 获取底部导航高度 
            this.style.contentViewHeight = (winHeight - uni.upx2px(this.style.footViewHeight));
            this.style.pageHeight = winHeight;
            var nickname = query.nickname || "匿名";
            var headimg = query.headimg || query.image || "logo.png";
            headimg = config.getFileUrl(headimg)
            var uid = query.uid || query.user_id || "10000000";
            var toUser = {
                nickname,
                uid,
                headimg
            };

            this.$store.commit('connectionUser', toUser)

            this.user = toUser;

            uni.setNavigationBarTitle({
                title: '您正在与' + nickname + "对话"
            });


            var msglist = this.$store.getters.msgList;
            // console.log(msglist)
            // var list = [];
            if (msglist) {
                var number = 0;
                for (let i in msglist) {

                    // console.log(toUser)
                    if ((msglist[i].uid == toUser.uid && msglist[i].to_uid == this.userInfo.id) || (msglist[i].to_uid ==
                            toUser.uid && msglist[i].uid == this.userInfo.id)) {

                        msglist[i].num = 0;

                    }
                    number = number + parseInt(msglist[i].num || 0)
                }
                this.$store.commit('msg', number)
                this.$store.commit('setMsgList', msglist)
            }
            // console.log(this.$store.getters.msgList)
        },
        methods: {
            maskCallbake() {
                // 从连socket
                this.$store.dispatch('socketConnect')
            },
            getDate() {

                this.isGeting = true;
                var user = this.user;
                var uid = user.uid;
                var data = {
                    uid,
                    time: this.time,
                }

                Request('UserChat_list', {
                        data
                    })
                    .then(e => {

                        this.isGeting = false;
                        if (e.data.code == 200) {

                            var data = e.data.data || [];

                            data.filter(e => {
                                var val = e.content;
                                try {
                                    val = JSON.parse(e.content)
                                } catch (e) {
                                    //TODO handle the exception
                                }
                                e.content = val
                                return e

                            })

                            var oldChat = this.$store.getters.chat || [];

                            var chatAll = data.concat(oldChat)

                            this.$store.commit('setChat', chatAll)

                            // 接收到可用数据
                            if (data.length < 20) {
                                this.showGetData = false;
                                // 服务器没有更多数据了
                                return;
                            } else {
                                // 服务器还有更多数据
                                return;
                            }

                        } else {
                            this.showGetData = false;
                        }
                    })
            },
            socketSend(data) {
                // console.log(JSON.stringify(data))
                Request('UserChat_send', {
                    data: data
                }).then(res => {
                    // console.log(res.data);
                    if (res.data.code == 200) {
                        // res.data.data = Object.assign({}, res.data.data, this.user);
                        res.data.data.headimg = this.user.headimg;
                        res.data.data.nickname = this.user.nickname;
                        this.$store.commit('addMsgList', res.data.data)
                        uni.showToast({
                            icon: 'none',
                            title: res.data.message,
                            duration: 500
                        });
                        setTimeout(this.scrollToBottom, 100);
                    } else {

                    }
                })
                // this.$store.commit('socketSend', data)
            },
            getInputMessage: function(message) {
                //获取子组件的输入数据
                // this.addMessage('customer', message.content, false);
                this.toRobot(message.content);
            },
            scrollToBottom: function() {
                var that = this;
                var query = uni.createSelectorQuery();
                query.selectAll('.m-item').boundingClientRect();
                query.select('#scrollview').boundingClientRect();

                query.exec(function(res) {
                    that.style.mitemHeight = 0;
                    res[0].forEach(function(rect) {
                        // console.info(rect.height);
                        that.style.mitemHeight = that.style.mitemHeight + rect.height + 20;
                    });

                    if (that.style.mitemHeight > that.style.contentViewHeight) {
                        that.scrollTop = that.style.mitemHeight - that.style.contentViewHeight;
                    }
                });
            },
            toRobot: function(info) {
                var data = {
                    to_uid: this.user.uid,
                    type: 'text',
                    time: Math.round(Date.now() / 1000),
                    content: info

                };

                // data.headimg = this.$store.getters.userInfo.headimg
                this.socketSend(data)
            }
        }
    }
</script>

<style>
    .uni-column {
        display: flex;
        flex-direction: column;
    }

    .content {
        display: flex;
        flex: 1;
        margin-bottom: 90upx;
    }

    .foot {
        position: fixed;
        width: 100%;
        height: 90upx;
        min-height: 90upx;
        left: 0upx;
        bottom: 0upx;
        overflow: hidden;
    }
</style>
