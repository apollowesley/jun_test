<template>
    <view>
        <popup-input :onClose="onClosePopup" :change="popupInput" :param="popupParam" :show="popupShow"></popup-input>
        <scroll-view lower-threshold=50 @scrolltolower="scrolltolower" scroll-y="true" style="box-sizing: border-box;padding: 0 10upx;"
            :style="'height:'+scrollHeight+'px'" :scroll-top="scrollTop">
            <comments-list :getMore="getMoreReply" :commentsData="commentsData" :index="commentIndex" :change="commentTap"
                style="padding: 20upx;"></comments-list>
            <view class="uni-flex uni-row" style="justify-content: center;">

                <text v-if="commentsHave" @click="getMoreComments">获取更多评论……</text>
                <text v-else>没有更多数据……</text>
            </view>
        </scroll-view>
        <nav-tab-bar :tabBar="bottomNav" :change="tabBarTap" :style="'height:'+TabBarHeight+'px'"></nav-tab-bar>
    </view>
</template>

<script>
    import Request from '@/request/index.js'
    import commentsList from "@/components/template/comments/list.vue"
    import NavTabBar from "@/components/template/nav/tabBar.vue"
    import popupInput from "@/components/template/popup/input.vue"
    import {
        Time,
        Storage
    } from '@/common/yc_js/index.js';
    export default {
        components: {
            popupInput,
            NavTabBar,
            commentsList
        },
        onBackPress() {
            // console.log()
        },
        onShow() {
            // console.log('onshow')
        },
        onLoad(event) {
            // console.log('onload')
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
            Storage.get('news_comments_' + this.param.id).then(comments => {
                if (!comments) {
                    this.getMoreComments(0);
                } else {
                    Storage.get('news_comments_replys_' + this.param.id).then(replys => {
                        this.replys = replys
                    })
                    this.comments = comments
                }
            })
            // console.log(this.param)
            // this.getMoreComments(0);
            uni.setNavigationBarTitle({
                title: '评论列表'
            });
        },
        data() {
            return {
                commentsRequestParams: {},
                windowHeight: 0,
                // TabBarHeight: 0,
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
                commentIndex: null,

                commentsHave: true,

                // 标识请求状态防止连续点击
                commentsLoading: false,
                NewsZanLoading: false,
                NewsCollectLoading: false,
                NewsShareLoading: false,
                NewsReportLoading: false,

                CommentZanLoading: false,
                CommentReple_updateReq: false,



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
                    value: '',
                }, {
                    flex: '8',
                    type: 'input',
                    placeholder: '发表你的评论',
                    value: '',
                    name: 'input',
                    left: '&#xe7b1;',
                }, {
                    flex: '1',
                    value: '',
                }],

                param: {},
                item: {},
                content: '',
                replys: [],
                comments: [],

                requestParam: {

                }
            }
        },
        watch: {
            comments: {
                handler(val, old) {
                    Storage.set('news_comments_' + this.param.id, val, 600)
                },
                deep: true
            },
            replys: {
                handler(val, old) {
                    Storage.set('news_comments_replys_' + this.param.id, val, 600)
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

                var comments = []


                var replys = [];
                if (this.replys) {
                    replys = JSON.parse(JSON.stringify(this.replys))

                    function sortId(a, b) {
                        return b.id - a.id
                    }

                    replys.sort(sortId);
                }


                if (this.comments) {
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

                        var reply = [];
                        for (let i = 0; i < replys.length; i++) {
                            var time2 = replys[i].create_time || parseInt(Date.now() / 1000)

                            replys[i].time = Time.dateTimeformat(time2);
                            if (time2 < e.requestParams.time) {
                                e.requestParams.time = time2
                            }

                            if (replys[i].to_uid == e.user_id) {
                                replys[i].to_nickname = e.nickname;
                            }
                            if (replys[i].headimg) {
                                replys[i].headimg = this.$config.getFileUrl(replys[i].headimg, 'image');
                            }

                            if (replys[i].comment_id && replys[i].comment_id == e.id) {

                                reply.push(replys[i])
                                replys.splice(i, 1);
                                i--;
                            }

                        }

                        e.reply = reply;
                        e.requestParams.count = reply.length
                        // console.log(reply)
                        return e;
                    })


                    // console.log(comments)
                }

                return comments
            }
        },
        methods: {
            scrolltolower() {
                this.getMoreComments()
            },
            getMoreComments(type = 1) {
                if (type == 0) {
                    var query = {};
                    query.time = parseInt(Date.now() / 1000)
                    // query.minId = item.id
                    query.tid = this.param.id
                } else {
                    var item = this.commentsData[this.commentsData.length - 1] || {};
                    // console.log(item)
                    var query = {};
                    query.time = item.refresh_time || parseInt(Date.now() / 1000)
                    query.minId = item.id
                    query.tid = this.param.id
                }
                // console.log(query)
                // this.commentsRequestParams=query
                if (!this.$store.getters.hasLogin) {
                    uni.showModal({
                        title: "您还未登陆,立即登陆?",
                        content: "需登陆后才能继续访问",
                        success(e) {
                            if (e.confirm) {
                                //登陆
                                uni.navigateTo({
                                    url: '/pages/login/login'
                                })
                            }
                        }
                    })
                    return
                }



                if (!this.commentsHave) {
                    // uni.showToast({
                    //     title: '没有数据了',
                    //     icon: 'none'
                    // });
                    return;
                }
                if (this.commentsLoading) {
                    uni.showToast({
                        title: '请求中',
                        icon: 'none'
                    });
                    return;
                }
                this.commentsLoading = true
                Request('NewsComment_list', {
                    data: query,
                }).then(result => {
                    // console.log(result)
                    if (result.statusCode == 200) {
                        var data = result.data.data;
                        this.commentsHave = false;
                        if (typeof data == 'object' && data.length) {
                            var len = data.length
                            if (len == 10) {
                                this.commentsHave = true;
                            }
                            this.comments = this.comments.concat(data)
                        }
                    } else {

                        this.commentsHave = false;
                    }

                    this.commentsLoading = false

                })


            },
            getMoreReply(item) {
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
                var query = item.requestParams;

                var have = item.have
                if (!have) {
                    uni.showToast({
                        title: '没有数据',
                        icon: 'none'
                    });
                    return;
                }
                if (item.loading) {
                    uni.showToast({
                        title: '请求中',
                        icon: 'none'
                    });
                    return;
                }
                item.loading = true
                Request('NewsCommentReply_list', {
                    data: query,
                    // responseType: 'arraybuffer',
                }).then(result => {
                    // console.log(result)
                    var len = 0;
                    if (result.statusCode == 200) {
                        var data = result.data.data;
                        have = false;
                        if (typeof data == 'object' && data.length) {
                            len = data.length
                            if (len == 10) {
                                have = true;
                            }
                            this.replys = this.replys.concat(data)
                        }
                    } else {
                        uni.uni.showToast({
                            title: result.data.message,
                            icon: 'none'
                        });
                        have = false;
                    }
                    // console.log(query)
                    // query.p = query.p + 1;
                    // query.count = query.count + len;
                    item.loading = false
                    this.comments = this.commentsData.filter(e => {
                        if (e.id == query.tid) {
                            e.have = have
                            e.loading = item.loading
                            e.requestParams = query
                        }
                        return e
                    })

                })

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
                                console.log(JSON.stringify(result))
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
            commentTap(Obj) {
                // console.log(val)
                var btn = Obj.key;

                var level = Obj.level || 1;

                var requestParam = {};
                // item['is_' + btn] = true;
                // 点击状态匹配
                if (level == 1) {
                    var item = Obj.items;
                    requestParam.comment_id = item.id;
                    requestParam.pid = 0;
                    requestParam.to_uid = item.user_id;
                    // 1==评论区的触发操作
                    switch (btn) {
                        case 'zan':
                            var query = {
                                tid: this.param.id
                            }
                            // Request('UserNewsZan_update', {
                            //     data: query,
                            // }).then(result => {
                            //     console.log(result)
                            //     if (result.statusCode == 200) {
                            //         var data = result.data.data;
                            //         this.commentsHave = false;
                            //     }
                            // })
                            if (item['is_' + btn]) {
                                uni.showToast({
                                    title: "您已经赞过了",
                                    'icon': 'none'
                                })
                            } else {
                                var comments = this.comments;
                                this.comments = comments.filter(e => {
                                    if (e.id == item.id) {
                                        e['is_' + btn] = true;
                                    }
                                    return e;
                                })
                            }
                            break;
                        case 'reply':
                            if (item['is_' + btn]) {

                            } else {
                                var comments = this.comments;
                                this.comments = comments.filter(e => {
                                    if (e.id == item.id) {
                                        e['is_' + btn] = true;
                                    }
                                    return e;
                                })

                            }
                            // console.log(item)
                            // this.requestParam = requestParam;
                            if (!item.requestParams.count) {
                                this.getMoreReply(item)

                            }

                            // this.popupShow = true;
                            break;

                        case 'speak':
                            if (item['is_' + btn]) {

                            } else {
                                var comments = this.comments;
                                this.comments = comments.filter(e => {
                                    if (e.id == item.id) {
                                        e['is_' + btn] = true;
                                    }
                                    return e;
                                })
                            }
                            this.popupParam[0].content[0].text = '请输入回复内容';
                            this.popupParam[0].content[0].placeholder = '@' + item.nickname;
                            this.requestParam = requestParam;
                            this.popupShow = true;
                            break;
                        default:
                            break;
                    }
                } else if (level == 2) {
                    var item = Obj.item;
                    requestParam.comment_id = item.comment_id;
                    requestParam.pid = item.id;
                    requestParam.to_uid = item.user_id;
                    // level2 ==回复区的触发操作
                    switch (btn) {
                        case 'zan':
                            if (item['is_' + btn]) {
                                uni.showToast({
                                    title: "您已经赞过了",
                                    'icon': 'none'
                                })
                            } else {
                                var comments = this.replys;
                                this.replys = comments.filter(e => {
                                    if (e.id == item.id) {
                                        e['is_' + btn] = true;
                                    }
                                    return e;
                                })
                            }
                            break;
                        case 'reply':
                            if (item['is_' + btn]) {

                            } else {
                                var comments = this.replys;
                                this.replys = comments.filter(e => {
                                    if (e.id == item.id) {
                                        e['is_' + btn] = true;
                                    }
                                    return e;
                                })
                            }
                            this.requestParam = requestParam;
                            break;
                        case 'speak':
                            if (item['is_' + btn]) {

                            } else {
                                var comments = this.replys;
                                this.replys = comments.filter(e => {
                                    if (e.id == item.id) {
                                        e['is_' + btn] = true;
                                    }
                                    return e;
                                })
                            }

                            this.requestParam = requestParam;
                            this.popupParam[0].content[0].text = '请输入回复内容';
                            this.popupParam[0].content[0].placeholder = '@' + item.nickname;
                            this.popupShow = true;
                            break;
                        default:
                            break;

                    }
                }

            },
            publishReply(value) {
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
                var requestParam = this.requestParam;
                var query = {
                    // tid: this.param.id,
                    comment_id: requestParam.comment_id,
                    to_uid: requestParam.to_uid,
                    pid: requestParam.pid,
                    content: value
                };
                console.log(query)
                // return 
                return Request('UserNewsCommentReply_publish', {
                    data: query,
                    // responseType: 'arraybuffer',
                })
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
                // console.log(query)
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
                        this.scrollToComment();

                        break;
                    case 'input':
                        // 输入
                        var param = {
                            tid: this.param.id,
                            to_uid: this.param.user_id
                        }
                        this.popupParam[0].content[0].text = '请输入评论内容';
                        this.popupParam[0].content[0].placeholder = '@主题留言';
                        this.requestParam = param;
                        this.popupShow = true;
                        break;
                    case 'zan':
                        //点赞
                        var query = {
                            tid: this.param.id
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
                console.log(page)
                switch (page) {
                    case 'chat':
                        var query = {
                            uid: this.param.user_id,
                            headimg: this.param.headimg,
                            nickname: this.param.nickname
                        };
                        uni.navigateTo({
                            url: '/pages/chat/chat?query=' + encodeURIComponent(JSON.stringify(query))
                        });
                        break;
                    case 'comment':
                        var query = {
                            uid: this.param.user_id,
                            headimg: this.param.headimg,
                            nickname: this.param.nickname
                        };
                        uni.navigateTo({
                            url: '/pages/news/comment/comment?query=' + encodeURIComponent(JSON.stringify(query))
                        });
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
            }
        }
    }
</script>

<style>

</style>
