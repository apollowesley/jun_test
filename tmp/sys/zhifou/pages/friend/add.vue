<template>
    <view class="uni-page-body" :style="'height:'+winHeight+'px'">

        <scroll-view class="uni-list" :style="'height:'+winHeight+'px'">
            <view class=" uni-media-list" style="display: flex;flex-direction: row;box-sizing: border-box;padding-left: 20upx;">
                <!-- 查找 -->
                <input type="text" class="uni-input uni-flex-item" style="width: 100%;border: 1px #BBBBBB solid;"
                    placeholder="请输入对方用户名" v-model="username">
                <view class="iconfont " @tap="getUser()" style="width: 50px;display: flex;align-items: center;justify-content: center;">
                    &#xe62f</view>
                </input>

            </view>
            <view v-if="userInfo" class="uni-list-cell" hover-class="uni-list-cell-hover">
                <view class=" uni-media-list tui-flex" @tap="addFriend()">
                    <view class="uni-media-list-logo " style="position: relative;">
                        <image v-if="userInfo.headimg" :src="userInfo.headimg" style="z-index: 0;"></image>
                    </view>
                    <view class="uni-media-list-body tui-item" style="flex: 1;">
                        <view class="uni-media-list-text-top">
                            <text>{{userInfo.nickname}}</text>
                        </view>
                        <view class="uni-media-list-text-bottom uni-ellipsis">{{userInfo.individuality}}</view>
                    </view>
                    <view class="tui-flex tui-column iconfont" style="width: 120upx;text-align: right;line-height: 70upx;">
                        &#xe657
                        <!-- <text class="tui-item " style=" font-size: 0.7em;opacity: 0.5;">{{value.msg.time}}</text> -->
                    </view>
                </view>
            </view>

        </scroll-view>
    </view>
</template>

<script>
    import yc_js from "../../common/yc_js/index.js"
    var Time = yc_js.Time;
    import Request from "../../request/index.js";
    import config from "../../config/index.js"
    export default {
        onShow() {
            this.onshow = true;
            this.$store.commit('pages', this.pageName || "friendAdd")
        },
        data() {
            return {
                username: '',
                userInfo: null,
                title: "添加",
                winHeight: 0,
                contentHeight: 0,
            }
        },
        onLoad(e) {
            console.log(e)
        },
        onNavigationBarButtonTap() {
            uni.navigateTo({
                url: './add.vue'
            })
            console.log("点击了自定义按钮");
        },
        mounted() {
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
            // socketConnect() {
            //     this.$store.dispatch('socketConnect')
            // },
            addFriend() {
                var friendList = this.$store.getters.friend || {};

                if (typeof friendList == 'object' && friendList[this.userInfo.user_id]) {
                    uni.showToast({
                        title: '好友已存在,无需重复添加',
                        icon: 'none',
                        duration: 1000
                    });
                    return;
                }
                uni.showLoading({
                    title: '请求中'
                });
                Request('UserFriend_add', {
                        data: {
                            uid: this.userInfo.user_id
                        }
                    })
                    .then(e => {
                        // console.log(e.data.data)
                        if (e.data.code == 200) {
                            uni.hideLoading();
                            friendList[this.userInfo.user_id] = this.userInfo
                            // console.log(friendList)
                            this.$store.commit('friend', friendList)
                            // console.log(this.$store.getters.friend)
                            uni.showToast({
                                title: '添加成功',
                                icon: 'none',
                                duration: 1000
                            });
                        } else if (e.data.message) {
                            uni.showToast({
                                title: e.data.message,
                                icon: 'none',
                                duration: 1000
                            });
                        }
                    })
            },
            getUser() {
                uni.showLoading({
                    title: '请求中'
                });
                Request('UserUser_info', {
                        data: {
                            username: this.username
                        }
                    })
                    .then(e => {
                        uni.hideLoading();
                        // console.log(e.data.data)
                        if (e.data.code == 200) {
                            var userInfo = e.data.data;
                            userInfo.headimg = config.getFileUrl(userInfo.headimg)
                            this.userInfo = userInfo
                        } else {
                            uni.showToast({
                                title: e.data.message,
                                icon: 'none'
                            })
                        }
                    })
            },

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
