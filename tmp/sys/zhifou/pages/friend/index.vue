<template>
    <view class="uni-page-body" :style="'height:'+winHeight+'px'">
        <mask-common :state="!hasLogin" :changes="maskCallbake" :text="'立即登录'"></mask-common>
        <scroll-view class="uni-list" :style="'height:'+winHeight+'px'">
            <view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value,key) in friendList" :key="key">
                <view class=" uni-media-list tui-flex" @tap="goPage(value)">
                    <view class="uni-media-list-logo " style="position: relative;">

                        <image v-if="value.headimg" :src="value.headimg" style="z-index: 0;"></image>
                    </view>
                    <view class="uni-media-list-body tui-item" style="flex: 1;">
                        <view class="uni-media-list-text-top">
                            <text>{{value.nickname}}</text>
                        </view>
                        <view class="uni-media-list-text-bottom uni-ellipsis">{{value.individuality}}</view>
                    </view>
                    <view class="tui-flex tui-column" style="width: 120upx;text-align: right;">
                        <text @longtap="delet(value)" class="tui-item iconfont" style=" font-size: 1.2em;opacity: 0.5;">&#xe921;</text>
                        <text class="tui-item"></text>
                    </view>
                </view>
            </view>

        </scroll-view>
    </view>
</template>

<script>
    import yc_js from "../../common/yc_js/index.js";
    import Request from "../../request/index.js";
    import config from '../../config/index.js';
    import maskCommon from "../../components/template/mask/common.vue"
    var Time = yc_js.Time;

    export default {
        components: {
            maskCommon
        },
        data() {
            return {
                pageName: 'friendIndex',
                // userList: [],
                title: "通讯录",
                winHeight: 0,
                contentHeight: 0,
            }
        },
        computed: {
            hasLogin() {
                return this.$store.getters.hasLogin
            },
            friendList() {
                return this.$store.getters.friend
            }
        },
        onShow() {
            if (typeof this.friendList == 'object' && this.hasLogin) {
                this.getFriend()
            }
            this.$store.commit('pages', this.pageName)
            // console.log('onshow') 
        },
        onLoad(e) {
            // console.log(e)

        },
        onNavigationBarButtonTap(e) {
            // console.log("点击了导航右侧分享自定义按钮");
            uni.navigateTo({
                url: '/pages/friend/add'
            })
            // console.log("点击了自定义按钮");
        },
        onReady() {
            // console.log('onready')
            let winHeight = uni.getSystemInfoSync().windowHeight;
            //创建节点选择器 获取底部导航高度 
            this.contentHeight = (winHeight - uni.upx2px(100));
            this.winHeight = winHeight;
            setTimeout(() => {
                this.showImg = true;
            }, 400)

            uni.setNavigationBarTitle({
                title: this.title
            });
        },
        methods: {
            maskCallbake(e) {
                // 去登陆
                uni.navigateTo({
                    url: '../login/login',
                });
            },
            delet(val) {
                var that = this;
                uni.showModal({
                    title: '提示',
                    content: '确定要删除吗？',
                    success(res) {
                        if (res.confirm) {
                            Request('UserFriend_delete', {
                                    data: {
                                        friend_id: val.user_id
                                    }
                                })
                                .then(e => {
                                    // console.log(e)
                                    if (e.data.code == 200) {
                                        var friendList = that.friendList;
                                        var friend = {};
                                        for (var p in friendList) {
                                            if (friendList[p].id != val.id) {
                                                friend[p] = friendList[p]
                                            } else {
                                                uni.showToast({
                                                    title: '删除成功',
                                                    icon: 'none',
                                                    duration: 1000
                                                });
                                            }
                                        }

                                        that.$store.commit('friend', friend)

                                    }
                                })
                            // console.log('用户点击确定');
                        } else if (res.cancel) {
                            // console.log('用户点击取消');
                        }
                    }
                });

            },
            getFriend() {

                Request('UserFriend_list')
                    .then(e => {
                        // console.log(e)
                        if (e.data.code == 200) {
                            var userList = e.data.data;
                            var friend = {};
                            for (var p in userList) {
                                var uid = userList[p].user_id;
                                friend[uid] = userList[p]
                                friend[uid].headimg = config.getFileUrl(friend[uid].headimg)
                            }
                            this.$store.commit('friend', friend)
                        }
                    })
            },
            goPage(value) {
                // console.log(value)
                var to_uid = value.user_id;
                var uid = this.$store.getters.userInfo.id;
                if (uid == to_uid) {
                    uni.showToast({
                        title: '不能给消息给自己',
                        icon: 'none',
                        duration: 1000
                    });
                    return;
                }

                value.type = 'chat';
                // value.to_uid = to_uid;
                value.uid = to_uid;
                // delete value.user_id;
                // msgList[to_uid]=value;
                this.$store.commit('connectionUser', value)
                // this.$store.commit('setMsgList',msgList);
                // }
                // console.log(msgList)
                
                var url = '/pages/chat/chat?query=' + encodeURIComponent(JSON.stringify(value))

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

    .uni-list-cell:last-child {
        border-bottom: 1px solid #e5e5e5;
    }
</style>
