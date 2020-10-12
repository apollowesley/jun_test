<template>
    <view id="page_id" class="uni-page-body uni-flex uni-column" style="position: relative;height: 100%;justify-content: space-between;">
        <popup-input :onClose="onClosePopup" :change="popupInput" :param="popupParam" :show="popupShow"></popup-input>
        <view class="uni-flex-item" style="">
            <scroll-view scroll-y="true" style="box-sizing: border-box;" :style="'height:'+scrollHeight+'px'"
                :scroll-top="scrollTop">
                <view id="header" v-if="param.image_url" class="banner">
                    <image mode="aspectFill" class="banner-img" :src="param.image_url"></image>
                    <view class="banner-title">{{param.title}}</view>
                </view>
                <view class="article-meta" style=" ">
                    <text class="article-author" style="color:#00B2EE;" @tap="goPage('chat')">{{detail.nickname}}</text>
                    <text class="article-text">发表于</text>
                    <text class="article-time">{{param.datetime}}</text>
                </view>

                <view class="article-content" style="width: 100%;box-sizing: border-box; background: #FFFFFF;">

                    <view v-if="!param.image_url" class="article-content" style="font-size: 1.5em;font-weight: 600;background: #FFFFFF; ">{{param.title}}</view>

                    <rich-text :nodes="detail.content" style="text-indent:2em;"></rich-text>

                    <view style="width: 100%;box-sizing: border-box;" v-for="(img,idx) in detail.news_image" :key="idx">
                        <view style="padding: 20upx;width: 100%;box-sizing: border-box; background: #FFFFFF;display: flex;justify-content: center;">
                            <image mode="widthFix" :src="img.src" @tap="previewImage(detail.news_image,idx)"> </image>
                        </view>

                    </view>
                </view>
                <view class="article-meta" style="flex-wrap: wrap;">
                    <text v-for="(item,key) in  tagList" :key="key" class="article-text iconfont" @click="tapTag(key)"
                        v-html="item.icon+detail[item.key]">
                    </text>

                </view>

                <view id="comment" class="comment-wrap"></view>
                <!-- :reply_btn="reply_btn" :zan_btn="zan_btn" -->
                <view @click="goPage('comment')" style="padding: 20upx;">
                    <comments-list :commentsData="commentsData"></comments-list>
                </view>

            </scroll-view>
        </view>
        <nav-tab-bar :tabBar="bottomNav" :change="tabBarTap" :style="'height:'+TabBarHeight+'px'"></nav-tab-bar>
    </view>

</template>

<script>
    const FAIL_CONTENT = '<p>获取信息失败</p>';
    import Request from '@/request/index.js'
    import commentsList from "@/components/template/comments/list.vue"
    import NavTabBar from "@/components/template/nav/tabBar.vue"
    import popupInput from "@/components/template/popup/input.vue"
    import {
        Time
    } from '@/common/yc_js/index.js';
    export default {
        components: {
            popupInput,
            NavTabBar,
            commentsList
        },
        data() {
            return {
                commentsRequestParams: {},
                windowHeight: 0,
                TabBarHeight: 44,
                scrollTop: 0,
                popupParam: [{
                    type: 'textarea',
                    class: 'uni-column ',
                    content: [{
                        style: 'flex:2',
                        value: '',
                        name: 'content',
                        text: '消息内容'
                    }]
                }, {
                    type: 'button',
                    // class: 'uni-column ',
                    content: [{
                        // style:'height:100upx;flex:2',
                        value: '',
                        name: 'cancel',
                        text: '取消'
                    }, {
                        type: 'primary',
                        // style:'height:100upx;flex:2',
                        value: '',
                        name: 'submit',
                        text: '确认'
                    }]
                }],
                // commentIndex: null,

                // commentsHave: false,

                // 标识请求状态防止连续点击
                // commentsLoading: false,
                NewsZanLoading: false,
                NewsCollectLoading: false,
                NewsShareLoading: false,
                // NewsReportLoading: false,

                // CommentZanLoading: false,
                // CommentReple_updateReq: false,



                popupShow: false,
                tagList: {
                    comment: {
                        title: '评论',
                        icon: '&#xe8bb;',
                        name: 'comment',
                        key: 'comment_num',
                    },
                    zan: {
                        title: '赞',
                        icon: '&#xe876;',
                        name: 'zan',
                        key: 'zan_num',
                    },
                    collect: {
                        title: '收藏',
                        icon: '&#xe602;',
                        name: 'collect',
                        key: 'collect_num',
                    },
                    share: {
                        title: '分享',
                        icon: '&#xe871;',
                        name: 'share',
                        key: 'share_num',
                    },
                    report: {
                        title: '举报',
                        icon: '&#xe601;',
                        name: 'report',
                        key: 'report_num',
                    }
                },
                bottomNav: [{
                        flex: '1',
                        icon: '&#xe619;',
                        name: 'chat',
                        // text:'聊天',
                        url: '/pages/order/list'
                    }, {
                        flex: '1',
                        name: 'reple',
                        tag: '0',
                        icon: '&#xe8bb;',
                        url: '/pages/order/list'
                    }, {
                        flex: '1',
                        tag: '0',
                        icon: '&#xe876;',
                        name: 'zan',
                    }, {
                        flex: '1',
                        icon: '&#xe871;',
                        name: 'share',
                        url: '/pages/order/list'
                    },
                    {
                        // msg: 1,
                        flex: '2',
                        type: 'input',
                        placeholder: '留言',
                        value: '',
                        name: 'input',
                        left: '&#xe7b1;',
                        // text: '发送',
                        url: '/pages/order/list'
                    }

                ],

                param: {},
                item: {},
                content: '',

                comments: [],


                requestParam: {

                },
            }
        },
        watch: {
            detail: {
                handler(e, old) {
                    this.bottomNav[1].tag = e.comment_num;
                    this.bottomNav[2].tag = e.zan_num;

                    // console.log(e)
                    // bottomNav
                },
                deep: true
            }
        },
        computed: {
            windowSize() {
                var windowSize = this.$store.getters.windowSize
                // console.log(windowSize)
                return windowSize;
            },
            scrollHeight() {

                return this.windowHeight - this.TabBarHeight
            },
            commentsData() {
                var comments = JSON.parse(JSON.stringify(this.comments))

                function sortTime(a, b) {
                    return b.refresh_time - a.refresh_time
                }
                comments.sort(sortTime);


                comments.filter(e => {

                    if (!e.requestParams) {
                        if (e.reply_num && e.reply_num > 0) {
                            e.have = true;
                        } else {
                            e.have = false;
                        }
                        e.requestParams = {
                            tid: e.id,
                            time: parseInt(Date.now() / 1000),
                            count: 0,
                            // quality:item.quality,
                            p: 0,
                            // column: 'id,post_id,title,author_name,cover,published_at,comments_count'
                        }
                    }

                    e.headimg = this.$config.getFileUrl(e.headimg, 'image');
                    var time = e.refresh_time || e.create_time || parseInt(Date.now() / 1000)
                    e.time = Time.dateTimeformat(time);

                    return e;
                })


                // console.log(comments)
                return comments
            },
            detail() {
                var item = Object.assign({}, this.item);
                var imgs = item.news_image || []
                imgs.filter(e => {
                    e.src = this.$config.getFileUrl(e.src, 'image')
                    return e
                })
                item.headimg = this.$config.getFileUrl(item.headimg, 'image')
                return item
            }
        },
        onShareAppMessage() {
            return {
                title: this.param.title,
                path: '/pages/detail/detail?query=' + JSON.stringify(this.param)
            }
        },
        onLoad(event) {
            uni.getSystemInfo({
                success: (res) => {
                    // console.log(res)
                    this.windowHeight = res.windowHeight;
                    let view = uni.createSelectorQuery().select(".header-height");
                    view.boundingClientRect(data => {
                        // console.log(data)
                    }).exec()
                }
            })
            // 目前在某些平台参数会被主动 decode，暂时这样处理。
            try {
                this.param = JSON.parse(decodeURIComponent(event.query));
            } catch (error) {
                this.param = JSON.parse(event.query);
            }
            // console.log(this.param)
            this.getDetail(this.param.id);
            uni.setNavigationBarTitle({
                title: this.param.title
            });
        },
        methods: {
            tapTag(tag) {
                var tagList = this.tagList
                var item = tagList[tag] || {};
                uni.showToast({
                    title: item.title ? item.title + ':' + this.detail[item.key] : '',
                    icon: 'none'
                });
                // uni.showModal({
                //     title: item.title ? item.title + '?' : '',
                // })
            },

            popupInput(e) {
                var idx = e.idx;
                var idxb = e.idxb;
                var param = e.param;
                var item = e.item;
                if (e.idx == 1) {

                    if (e.idxb == 1) {
                        var value = param[0].content[0].value;
                        // console.log(value)
                        var requestParam = this.requestParam;
                        // requestParam.content = value
                        // console.log(requestParam)
                        if (requestParam.comment_id) {

                            this.publishReply(value).then(result => {
                                // console.log(JSON.stringify(result))
                                if (result.statusCode == 200) {

                                    var item = result.data.data;
                                    item.nickname = this.$store.getters.userInfo.nickname || '我';
                                    item.headimg = this.$store.getters.userInfo.headimg || '';
                                    this.comments = this.comments.filter(e => {
                                        if (e.id == item.comment_id) {
                                            e.reply_num = e.reply_num + 1;
                                        }
                                        return e;
                                    })
                                    this.replys.push(item)
                                } else {

                                }
                                uni.hideLoading();
                            })
                        } else {
                            this.publishComment(value).then(result => {

                                // console.log(JSON.stringify(result))
                                if (result.statusCode == 200) {

                                    var item = result.data.data;
                                    item.nickname = this.$store.getters.userInfo.nickname || '我';
                                    item.headimg = this.$store.getters.userInfo.headimg || '';
                                    this.comments.push(item)
                                } else {

                                }
                                uni.hideLoading();
                            })
                        }
                    }
                    this.popupShow = false;
                }
                // console.log(e)
                // this.popupShow = !this.popupShow
            },
            publishComment(val) {
                if (!this.$store.getters.hasLogin) {
                    uni.showModal({
                        title: "您还未登陆,立即登陆?",
                        content: "需登陆后才能继续访问",
                        success(e) {
                            if (e.confirm) {
                                //登陆
                                uni.navigateTo({
                                    url: '../login/login'
                                })
                            }
                        }
                    })
                    return
                }
                var query = {
                    tid: this.param.id,
                    type: 1,
                    content: val
                };
                console.log(query)
                // return 
                return Request('UserNewsComment_publish', {
                    data: query,
                    // responseType: 'arraybuffer',
                })
            },
            onClosePopup() {
                this.popupShow = false;
            },
            scrollToComment: function() {
                var query = uni.createSelectorQuery();
                query.select('#comment').boundingClientRect();
                query.exec((res) => {
                    // console.log(res[0])
                    this.scrollTop = res[0].top
                });
            },
            tabBarTap(e) {

                // console.log(e)
                var idx = e.idx;
                var name = e.name;
                var item = e.item;
                var data = e.data;

                switch (e.name) {
                    case 'reple':
                        this.goPage('comment')
                        // this.scrollToComment();

                        break;
                    case 'input':
                        // 输入
                        var param = {
                            tid: this.detail.id,
                            to_uid: this.detail.user_id
                        }
                        this.popupParam[0].content[0].text = '请输入评论内容';
                        this.popupParam[0].content[0].placeholder = '@主题留言';
                        this.requestParam = param;
                        this.popupShow = true;
                        break;
                    case 'zan':
                        //点赞
                        var query = {
                            tid: this.detail.id
                        }
                        if (this.UserNewsZan_updateReq) {
                            return;
                        }

                        this.UserNewsZan_updateReq = true
                        Request('UserNewsZan_update', {
                            data: query,
                        }).then(result => {
                            // console.log(result)
                            if (result.statusCode == 200 && result.data.data) {
                                var data = result.data.data;
                                if (typeof data == 'object') {
                                    if (!data.id) {
                                        uni.showToast({
                                            title: "赞已取消",
                                            'icon': 'none'
                                        })
                                        this.item.zan_num = this.item.zan_num - 1;
                                        this.item.is_zan = false;
                                    } else {
                                        uni.showToast({
                                            title: "赞赞赞"
                                        })
                                        this.item.is_zan = true;
                                        this.item.zan_num = this.item.zan_num + 1;
                                    }
                                }
                            }
                            this.UserNewsZan_updateReq = false
                        })

                        break;
                    case 'report':
                        // 举报
                        var param = {
                            tid: this.data.id
                        }
                        this.requestParam = param;
                        this.popupShow = !this.popupShow;
                        break;
                    case 'collect':
                        // 收藏
                        var param = {
                            tid: this.data.id
                        }
                        this.requestParam = param;
                        this.p
                    case 'chat':
                        this.goPage('chat')
                        break;
                    default:
                        break;
                }

            },
            goPage(page) {
                // console.log(page)
                switch (page) {
                    case 'chat':
                        var query = {
                            uid: this.detail.user_id,
                            headimg: this.detail.headimg,
                            nickname: this.detail.nickname
                        };
                        uni.navigateTo({
                            url: '/pages/chat/chat?query=' + encodeURIComponent(JSON.stringify(query))
                        });
                        break;
                    case 'comment':
                        var query = {
                            id: this.detail.id,
                            uid: this.detail.user_id,
                            nickname: this.detail.nickname
                        }
                        // console.log(this.$store.dispatch('isLogin'))
                        this.$store.dispatch('isLogin').then(e => {
                            
                            if (e) {
                                uni.navigateTo({
                                    url: '/pages/news/comment/comment?query=' + encodeURIComponent(
                                        JSON.stringify(query))
                                });
                            }
                        })


                        // var query = {
                        //     uid: this.detail.user_id,
                        //     headimg: this.detail.headimg,
                        //     nickname: this.detail.nickname
                        // };

                        break;
                    default:
                        break;
                }
            },
            previewImage(img, idx) {
                if (idx === false) {
                    // console.log('imgstr' + idx)
                    uni.previewImage({
                        urls: img.src
                    })
                } else {
                    // console.log('imgarr' + idx)
                    var imgs = img.map(e => {
                        return e.src
                    })
                    // console.log(imgs)
                    uni.previewImage({
                        urls: imgs,
                        current: idx
                    })
                }
            },
            getDetail(id) {
                uni.showLoading({
                    title: '加载中'
                });

                setTimeout(function() {
                    uni.hideLoading();
                }, 5000);
                Request('News_detail', {
                    data: {
                        id,
                        type: 1,
                    },
                    // responseType: 'arraybuffer',
                }).then(result => {

                    // console.log(JSON.stringify(result))
                    if (result.statusCode == 200) {
                        var data = result.data.data || {};

                        this.comments = data.comments; //评论
                        this.item = data.detail; //文章内容
                        if (this.item.comment_num) {
                            this.commentsHave = true
                        }

                    } else {
                        this.loadingText = '没有更多数据';
                        this.refreshing = false;
                    }
                    uni.hideLoading();
                })
            }
        }
    }
</script>

<style>
    image {
        max-height: none;
    }

    .banner {
        max-height: 360upx;
        overflow: hidden;
        position: relative;
        background-color: #ccc;
    }

    .banner-img {
        max-height: 360upx;
        width: 100%;
    }

    .banner-title {

        max-height: 84upx;
        overflow: hidden;
        position: absolute;
        left: 30upx;
        bottom: 30upx;
        width: 90%;
        font-size: 32upx;
        font-weight: 400;
        line-height: 42upx;
        color: white;
        z-index: 11;
    }

    .article-meta {
        line-height: 50upx;
        padding: 20upx 40upx;
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        color: gray;
    }

    .article-text {
        font-size: 26upx;
        /* line-height: 50upx; */
        margin: 0 20upx;
    }

    .article-author,
    .article-time {
        font-size: 26upx;
    }

    .article-content {
        padding: 0 30upx;
        overflow: hidden;
        font-size: 30upx;
        margin-bottom: 30upx;
    }
</style>
