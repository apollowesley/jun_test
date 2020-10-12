<template>
    <view class="uni-page-body" :style="'height:'+winHeight+'px'">
        <mask-common :state="socketState" text="网络断开-是否重连" :changes="maskCallbake"></mask-common>

        <scroll-view class="uni-list" :style="'height:'+winHeight+'px'">
            <view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value,key) in msgList" :key="key">
                <view v-if="value.type=='chat'" class=" uni-media-list uni-flex" @tap="goPage(value,key)">
                    <view class="uni-media-list-logo " style="position: relative;">
                        <view v-if="value.num" class="uni-badge uni-badge-red" style="z-index: 1;position: absolute;right: -10upx;top:-10upx">
                            <text v-if="value.num<10">{{value.num}}</text><text v-else>…</text>
                        </view>
                        <image v-if="value.img" :src="value.img" style="z-index: 0;"></image>
                        <image v-else-if="value.headimg" :src="value.headimg" style="z-index: 0;"></image>
                    </view>
                    <view class="uni-media-list-body uni-item" style="flex: 1;">
                        <view class="uni-media-list-text-top">
                            <text v-if="value.title">{{value.title}}</text>
                            <text v-else-if="value.nickname">{{value.nickname}}</text>
                        </view>
                        <view class="uni-media-list-text-bottom uni-ellipsis">
                            <view v-if="value.typeof =='string'">
                                {{value.content}}
                            </view>
                            <view v-else-if="value.typeof =='object' && value.content.type && value.content.type=='text'">
                                {{value.content.value}}
                            </view>
                            <image v-else-if="value.typeof =='object' && value.content.type && value.content.type=='image'">
                                {{value.content.value}}
                            </image>

                        </view>
                    </view>
                    <view class="uni-flex uni-column" style="width: 120upx;text-align: right;">
                        <text class="uni-item " style=" font-size: 0.7em;opacity: 0.5;">{{value.time}}</text>
                        <text class="uni-item"></text>
                    </view>
                </view>

                <view v-if="value.type=='order'" class=" uni-media-list uni-flex" @tap="goPage(value,key)">
                    <view class="uni-media-list-logo " style="position: relative;">
                        <view v-if="value.num" class="uni-badge uni-badge-red" style="z-index: 1;position: absolute;right: -10upx;top:-10upx">
                            <text v-if="value.num<10">{{value.num}}</text><text v-else>…</text>
                        </view>
                        <image v-if="value.img" :src="value.img" style="z-index: 0;"></image>
                        <image v-else-if="value.headimg" :src="value.headimg" style="z-index: 0;"></image>
                    </view>
                    <view class="uni-media-list-body tui-item" style="flex: 1;">
                        <view class="uni-media-list-text-top">
                            <text>订单通知</text>

                        </view>
                        <view class="uni-media-list-text-bottom uni-ellipsis">
                            <view v-if="value.typeof =='string'">
                                {{value.content}}
                            </view>
                            <view v-else-if="value.typeof =='object' && value.content.type && value.content.type=='text'">
                                {{value.content.value}}
                            </view>
                            <image v-else-if="value.typeof =='object' && value.content.type && value.content.type=='image'">
                                {{value.content.value}}
                            </image>

                        </view>
                    </view>
                    <view class="tui-flex tui-column" style="width: 120upx;text-align: right;">
                        <text class="tui-item " style=" font-size: 0.7em;opacity: 0.5;">{{value.time}}</text>
                        <text class="tui-item"></text>
                    </view>
                </view>
            </view>
            <!-- {{msgList}} -->
        </scroll-view>
        <!-- <chat-list :contentHeight="winHeight" ></chat-list> -->
        <!-- </view> -->
    </view>
</template>

<script>
    import yc_js from "../../common/yc_js/index.js"
    import config from '../../config/index.js';
    var Time = yc_js.Time;
    // import chatList from "../../components/pages/chat/list.vue"
    import maskCommon from "../../components/template/mask/common.vue"
    export default {
        components: {
            // chatList,
            maskCommon
        },
        data() {
            return {
                socketConnect: 1,
                pageName: 'chatIndex',
                shuaxinTime: null,
                //  msgList:[{
                //         uid: 10000,
                //         to_uid: "1234",
                //         title: "订单通知",
                //         type: "order",
                //         content: "水煮鱼,小碗米饭,雪碧已接单",
                //         num: "1",
                //         time: '刚刚',
                //         img: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg?imageView2/3/w/200/h/100/q/90"
                //     },
                //     {
                //         to_uid: "1235",
                //         title: "小甜甜",
                //         type: "chat",
                //         content: "能和心爱的人一起睡觉，是件幸福的事情；可是，打呼噜怎么办？",
                //         num: "1",
                //         time: '刚刚',
                //         img: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg?imageView2/3/w/200/h/100/q/90"
                //     },
                //     {
                //         to_uid: "1236",
                //         title: "大柱子",
                //         type: "chat",
                //         content: "想要这样一间小木屋，夏天挫冰吃瓜，冬天围炉取暖。",
                //         num: "1",
                //         time: '刚刚',
                //         img: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/muwu.jpg?imageView2/3/w/200/h/100/q/90"
                //     },
                //     {
                //         to_uid: "1237",
                //         title: "信阳烧烤",
                //         type: "chat",
                //         content: "烤炉模式的城，到黄昏，如同打翻的调色盘一般。",
                //         num: "3",
                //         time: '刚刚',
                //         img: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/cbd.jpg?imageView2/3/w/200/h/100/q/90"
                //     }],
                msgList: [],
                // msgList: {},
                title: "消息",
                webviewStyles: {
                    progress: {
                        color: '#FF3333'
                    }
                },
                winHeight: 0,
                contentHeight: 0,

            }
        },
        computed: {
            socketState() {

                return this.socketOpen ? false : true;
            },
            socketOpen() {
                // console.log(this.$store.getters.socketOpen)
                return this.$store.getters.socketOpen;
            },
            userInfo() {
                return this.$store.getters.userInfo
            },
            storeMsgList() {
                // this.shuaxinMsgList()
                var msgList = this.$store.getters.msgList || [];
                return msgList;
            }
        },
        watch: {

            storeMsgList: {
                handler(val, oldVal) {
                    this.shuaxinMsgList();
                    // console.log({val, oldVal}); //但是这两个值打印出来却都是一样的
                },
                deep: true
                // console.log(msg)
            }
        },
        onShow() {

            this.$store.commit('pages', this.pageName)
            // console.log('onshow')
            if (typeof this.msgList == 'object') {
                this.shuaxinMsgList();
            }
        },

        onLoad(e) {
            this.shuaxinMsgList()
            // this.$store.commit('setMsgList', this.msgList)

            // setInterval(this.shuaxinMsgList, 5000)
        },
        mounted() {
            let winHeight = uni.getSystemInfoSync().windowHeight;
            //创建节点选择器 获取底部导航高度 
            this.contentHeight = (winHeight - uni.upx2px(100));
            this.winHeight = winHeight;
            uni.setNavigationBarTitle({
                title: this.title
            });
        },
        methods: {
            maskCallbake() {
                // 从连socket
                this.$store.dispatch('socketConnect')
            },
            shuaxinMsgList() {

                var pages = this.$store.getters.pages || [];
                // console.log(pages)
                if (typeof pages == 'object' && pages[0] && pages[0] == 'chatIndex') {
                    var msgList = JSON.parse(JSON.stringify(this.$store.getters.msgList));

                    var number = 0;
                    for (var p in msgList) {
                        msgList[p].typeof = typeof msgList[p].content;
                        if (msgList[p].time) {
                            // msgList[p].time = Time.formatDate(msgList[p].time,"mm月dd日 hh:MM:ss")
                            msgList[p].time = Time.dateTimeformat(msgList[p].time, "mm-dd hh:MM")
                        }
                        if (msgList[p].num) {
                            number = number + parseInt(msgList[p].num)
                        }

                    }
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
                    // this.$store.commit('update',{msg:number})
                    this.msgList = msgList || [];
                }

            },

            goPage(value, idx) {
                // value.num=0;
                // var upVal={value:{num:0},index:idx}
                this.shuaxinTime = null;
                // console.log(value)
                var type = value.type || 'chat';
                var url = '';
                switch (type) {
                    case 'chat':
                        var face_uid = value.to_uid;
                        if (this.userInfo.id == value.to_uid) {
                            face_uid = value.uid || 0;
                        }
                        var msgList = this.msgList;
                        var headimg = null;
                        var nickname = null;

                        for (let i = 0; i < msgList.length; i++) {
                            if ((msgList[i].uid == face_uid && msgList[i].to_uid == this.userInfo.id) || (msgList[i].to_uid ==
                                    face_uid && msgList[i].uid == this.userInfo.id)) {
                                msgList[i].num = 0;
                                headimg = msgList[i].headimg || "logo.png";
                                nickname = msgList[i].title || msgList[i].nickname || "匿名";
                                break;
                            }
                        }
                        // this.$store.commit('setMsgList', msgList);

                        headimg = config.getFileUrl(headimg)
                        this.$store.commit('connectionUser', {
                            nickname,
                            headimg: headimg,
                            uid: face_uid
                        })
                        var queryData = {
                            uid: face_uid,
                            headimg,
                            nickname
                        }
                        url = '/pages/chat/chat?query=' + encodeURIComponent(JSON.stringify(queryData))
                        break;
                    case 'order':
                        url = '/pages/chat/order';
                        break;
                    default:

                        return;
                        break;
                }
                uni.navigateTo({
                    url: url
                });
            }
        }
    }
</script>
<style>
    .title {
        padding: 20upx;
    }

    /* #ifdef H5 */
    .uni-list-cell {
        border-top: 1px solid #e5e5e5;
    }

    /* #endif */

    .uni-list-cell:last-child {
        border-bottom: 1px solid #e5e5e5;
    }
</style>
