<template>
    <view>
        <mask-common :state="!hasLogin" :changes="maskCallbake" :text="'立即登录'"></mask-common>
        <scroll-view v-if="hasLogin" scroll-y :style="{height: windowHeight+'px'}">
            <view style="padding: 5upx 0; ">
                <view style="background: #FFFFFF;">
                    <view class="uni-list-cell" style="border-bottom: 0;" hover-class="uni-list-cell-hover" @tap="toPage('info')">
                        <view class=" uni-media-list uni-flex" style="box-sizing: border-box; ">
                            <view class="uni-flex" style="width: 150upx;padding: 10upx;">
                                <image style="width: 130upx;height: 130upx;box-sizing: border-box;" mode="aspectFill"
                                    v-if="userInfo.headimg" :src="userInfo.headimg"></image>
                            </view>

                            <view class="uni-flex-item uni-flex" style="flex-direction: column;">
                                <view class="" style="font-size: 1.3em;">{{userInfo.nickname}}</view>
                                <view class="" style="font-size: 0.9em;">账号:{{userInfo.username}}</view>
                            </view>

                            <view class=" uni-list-cell-navigate iconfont uni-navigate-right" style="width: 50px;"></view>
                        </view>
                    </view>
                    <view class=" uni-media-list uni-flex" style="box-sizing: border-box;">
                        <!--                        <view class="" style="font-size: 10px">
                            <text v-if="userInfo.is_auth">已实名</text>
                            <text v-else>未实名</text>
                        </view> -->
                        <view class="" style="font-size: 0.9em;">
                            积分:{{userInfo.integral}}
                        </view>
                    </view>
                </view>
            </view>
            <view style="padding: 5upx 0; ">
                <view style="padding-top: 5upx;" v-for="(list,list_i) in severList" :key="list_i">
                    <view style="padding-top: 5upx;" v-for="li in list" @tap="toPage(li.url)" :key="li.name">
                        <view style="background: #FFFFFF" hover-class="uni-list-cell-hover">
                            <view class=" uni-media-list uni-flex" style="box-sizing: border-box;justify-content: center;align-items: center;">
                                <view class="iconfont uni-flex uni-column" style="align-items: center;padding-right: 15upx;justify-content: center;"
                                    v-html="li.icon">
                                </view>
                                <view class="text uni-column uni-flex" style="justify-content: center;">{{li.name}}</view>
                                <view class=" uni-list-cell-navigate uni-navigate-right" style="width: 50px;"></view>
                            </view>
                        </view>
                    </view>
                </view>
            </view>
            <button class="uni-flex-item" style="" v-if="hasLogin" @tap="toPage('logOut')">
                退出登陆
            </button>
        </scroll-view>

    </view>

</template>
<script>
    import config from '../../config/index.js';
    import Storage from "../../common/yc_js/Storage.js";
    import Request from "../../request/index.js";
    import maskCommon from "../../components/template/mask/common.vue"
    export default {
        components: {
            maskCommon
        },
        data() {
            return {
                pageName: 'userWode',
                kefu: {
                    uid: "101555674757204",
                    nickname: "客服",
                    headimg: "logo.png"
                },
                title: "我的",

                orderTypeLise: [
                    //name-标题 icon-图标 badge-角标
                    {
                        name: '待付款',
                        icon: 'l1.png',
                        badge: 1
                    },
                    {
                        name: '待评价',
                        icon: 'l4.png',
                        badge: 9
                    },
                ],
                severList: [
                    [
                        // {name:'我的收藏',icon:'&#xe86f;',url:'../goods/collect'},
                        // 						{name:'优惠券',icon:'quan.png'},
                        // 						{name:'红包',icon:'momey.png'},
                        // 						{name:'任务',icon:'renw.png'},
                    ],
                    [

                        {
                            name: '收货地址',
                            icon: '&#xe8dd;',
                            url: 'address'
                        }, {
                            name: '新闻管理',
                            icon: '&#xe635;',
                            url: 'news_list'
                        },
                        // {name:'银行卡',icon:'bank.png'},
                        {
                            name: '安全中心',
                            icon: '&#xe8c9;',
                            url: 'info'
                        },
                        {
                            name: '在线客服',
                            icon: '&#xe8b4;',
                            url: 'kefu'
                        }
                    ]
                ],windowHeight:0,
            };
        },
        computed: {
            // windowHeight() {
            //     return this.$store.getters.windowSize.windowHeight
            // },
            isH5Plus() {
                //#ifdef APP-PLUS
                return true
                //#endif 
                return false
            },
            hasLogin() {
                return this.$store.getters.hasLogin;
            },
            userInfo() {
                return this.$store.getters.userInfo;
            }
        },
        onShow() {
            uni.getSystemInfo({
                success:(res)=> {
                    // console.log(res)
                    this.windowHeight=res.windowHeight;
                }
            })
            // this.init();
            uni.setNavigationBarTitle({
                title: this.title
            });
            this.$store.commit('pages', this.pageName)
        },

        onLoad() {

        },
        methods: {
            maskCallbake(e) {
                // 去登陆
                uni.navigateTo({
                    url: '../login/login',
                });
            },
            init() {
                var self = this;
                // this.$store.commit('isLogin')
            },
            //用户点击订单类型
            toOrderType(index) {
                uni.showToast({
                    title: this.orderTypeLise[index].name
                });
            },
            //用户点击列表项
            toPage(url) {
                // console.log(url)
                switch (url) {
                    case "info":
                        url = "/pages/user/info";
                        break;
                    case "logOut":
                        Request('logOut', {
                            // data: form
                        }).then(res => {
                            this.$store.commit('logOut');
                            // uni.navigateTo({
                            //     url: '/pages/login/login',
                            // });

                        })
                        return;
                    case "address":
                        url = "/pages/user/address";
                        break;
                    case "news_list":
                        url = "/pages/news/user/list";
                        break;
                    case "kefu":
                        var headimg = this.kefu.headimg || "logo.png";
                        var nickname = this.kefu.nickname || "匿名";

                        headimg = config.getFileUrl(headimg)
                        this.$store.commit('connectionUser', {
                            nickname,
                            headimg: headimg,
                            uid: this.kefu.uid
                        })
                        url = "/pages/chat/chat?uid=" + this.kefu.uid + "&nickname=" + this.kefu.nickname + "&headimg=" +
                            this.kefu.headimg;
                        break;

                    default:
                        break;
                }
                uni.navigateTo({
                    url: url
                })
            }
        }
    }
</script>

<style lang="scss">

</style>
