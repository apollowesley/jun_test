<template>
    <view class="uni-tab-bar" id="uni_tab_bar" style="height: 100%;position: relative;">

        <scroll-view id="tab_bar" class="uni-swiper-tab" scroll-x :scroll-left="scrollLeft">
            <view v-for="(tab, index) in tabBars" :key="tab.ref" :class="['swiper-tab-list',tabIndex==index ? 'active' : '']"
                :id="tab.ref" :data-current="index" @click="tapTab(index,tab)">{{tab.name}}</view>
        </scroll-view>
        <view @click="goPage(tabIndex,'publish')" @touchmove="touchmove" :style="'top:'+BtnTop+'px;left:'+BtnLeft+'px'"
                class="uni-flex iconfont" style="color: #1AAD19;position: absolute;z-index: 999;font-size: 100upx; width: 100upx;height: 100upx;border-radius: 50%;text-align: center;justify-content: center;align-items: center;">
                &#xe600;
        </view>

            <!-- #ifndef MP-BAIDU -->
            <scroll-view class="list" v-for="(tabItem, idx) in tabBars" :key="idx" v-if="tabIndex === idx" scroll-y
                @scrolltolower="loadMore(idx)" upper-threshold="50" @scrolltoupper="scrolltoupper" :style="'height:'+scrollViewHeight+'px'">
                <view v-if="refreshing" class="uni-flex" style="align-content: center;justify-content: center;"
                    @touchstart="touchstart">
                    {{refreshText}}
                </view>
                <block v-for="(newsItem, newsIndex) in tabItem.data" :key="newsItem.id">
                    <uni-media-list :options="newsItem" :index="newsIndex" @close="dislike(idx, newsIndex)" @click="goPage(newsItem,'detail')" :changeTap="tapMediaList"></uni-media-list>

                </block>
                <view class="uni-tab-bar-loading">
                    <view class="loading-more">{{loadingText}}</view>
                </view>
            </scroll-view>
            <!-- #endif -->
            <!-- #ifdef MP-BAIDU -->
            <view class="scroll-wrap" v-for="(tabItem, idx) in tabBars" :key="idx">
                <scroll-view class="list" v-if="tabIndex === idx" scroll-y @scrolltolower="loadMore(idx)" :style="'height:'+scrollViewHeight+'px'"
                    upper-threshold="50" @scrolltoupper="scrolltoupper">
                    <view v-if="refreshing" class="uni-flex" style="align-content: center;justify-content: center;"
                        @touchstart="touchstart">
                        {{refreshText}}
                    </view>
                    <block v-for="(newsItem, newsIndex) in tabItem.data" :key="newsItem.id">
                        <uni-media-list :options="newsItem" @close="dislike(idx, newsIndex)" @click="goPage(newsItem,'detail')"></uni-media-list>
                    </block>
                    <view class="uni-tab-bar-loading">
                        <view class="loading-more">{{loadingText}}</view>
                    </view>
                </scroll-view>
            </view>
            <!-- #endif -->
        <!-- </view> -->
    </view>
</template>
<script>
    import Request from '@/request/index.js'
    import uniMediaList from '@/components/uni-media-list/uni-media-list.vue';
    import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
    import {
        Time,
        Storage
    } from '@/common/yc_js/index.js';


    export default {
        components: {
            uniMediaList,
            uniLoadMore
        },
        data() {
            return {
                tabBarHeight: 90,
                BtnLeft: 220,
                BtnTop: 280,
                windowHeight: 0,
                loadingText: {
                    contentdown: '上拉加载更多',
                    contentrefresh: '正在加载...',
                    contentnomore: '没有更多数据了'
                },
                scrollLeft: 0,
                refreshing: false,
                refreshText: '点击可以刷新',

                tabIndex: 0,
                tabBars: [],

                noData: {

                },
                windowSize: {

                }
            }
        },
        computed: {

            scrollViewHeight() {
                return 'height:' + (this.windowHeight - this.tabBarHeight) + 'px';
            }
        },
        watch: {
            tabIndex(e) {
                // console.log(e)
                if (this.tabBars[e] && this.tabBars[e].name) {
                    uni.setNavigationBarTitle({
                        title: this.tabBars[e].name
                    });
                }

            }
        },
        onLoad: function() {
            uni.setNavigationBarTitle({
                title: '一名电影人'
            });
            uni.getSystemInfo({
                success: (res) => {
                    // console.log(res)
                    this.windowHeight = res.windowHeight;
                    this.BtnLeft = res.windowWidth - 80
                    this.BtnTop = res.windowHeight - 80
                    this.windowSize = res;
                    let view = uni.createSelectorQuery().select("#uni_tab_bar");
                    view.boundingClientRect(data => {

                        // console.log(data)
                        if (data && data.height) {
                            this.tabBarHeight = data.height || 36;
                        }

                    }).exec()
                }
            })


            var navList = Storage.Sync.get('navList')
            if (navList && typeof navList == 'object') {
                this.tabBars = navList;
                // console.log(JSON.stringify(this.tabBars))
                this.getList(0);
            } else {
                // 初始化列表信息

                Request('NewsCategory_list', {
                    data: {
                        quality: 2
                    },
                    // responseType: 'arraybuffer',
                }).then(result => {

                    uni.hideLoading();

                    // console.log(JSON.stringify(result.data.data))
                    if (result.statusCode == 200) {
                        var tabBars = result.data.data;

                        // var arr = [10, 20, 1, 2];
                        tabBars.sort(function(a, b) {
                            if (a.ranking < b.ranking) {
                                return -1;
                            } else if (a.ranking > b.ranking) {
                                return 1;
                            } else {
                                return 0;
                            }
                        })
                        this.tabBars = tabBars.filter((item, index) => {
                            item.ref = item.id;
                            item.have = true;
                            // id: 'tabBar' + index,
                            item.data = [];
                            item.requestParams = {
                                category_id: item.id,
                                minId: 0,
                                // quality:item.quality,
                                pageSize: 10,
                                // column: 'id,post_id,title,author_name,cover,published_at,comments_count'
                            }
                            // item.loadingText = '加载中...';
                            return item
                        })
                        Storage.Sync.set('navList', this.tabBars, 84000)
                        // console.log(this.tabBars)
                        this.getList(0);
                    }
                })
            }

        },
        onShow() {

        },
        methods: {
            tapMediaList(e){
              console.log(e)  
            },
            touchmove(e) {
                // this.BtnLeft=  e.target.offsetLeft
                // this.BtnTop=  e.target.offsetTop
                var BtnLeft = e.touches[0].clientX

                var BtnTop = e.touches[0].clientY
                var windowSize = this.windowSize

                if (BtnLeft < 20) {
                    BtnLeft = 20
                }
                if (BtnTop < 50) {
                    BtnTop = 50
                }
                if (BtnLeft > windowSize.windowWidth - 60) {
                    BtnLeft = windowSize.windowWidth - 60
                }
                if (BtnTop > windowSize.windowHeight - 60) {
                    BtnTop = windowSize.windowHeight - 60
                }


                this.BtnLeft = BtnLeft
                this.BtnTop = BtnTop
                // console.log(e)
            },
            scrolltoupper() {
                // 滚到顶部显示刷新按钮 2秒自动消失
                this.refreshing = true;
                setTimeout(() => {
                    this.refreshing = false;
                }, 2000);
            },
            touchstart(e) {
                this.refreshing = true;
                // 点击刷新
                // console.log(e)
                this.onRefresh()
            },
            onRefresh(event) {
                this.getList(0);
            },
            loadMore(e) {
                //加载更多
                // console.log(e)
                this.getList(2);
            },
            getList(action = 1) {


                let activeTab = this.tabBars[this.tabIndex];

                activeTab.requestParams.time = activeTab.requestParams.time || parseInt((new Date().getTime()) /
                    1000);

                if (action == 0) {
                    // 刷新
                    // activeTab.requestParams.category_id = 17;
                    activeTab.requestParams.time = parseInt((new Date().getTime()) /
                        1000);
                    activeTab.data = [];
                    activeTab.have = true;
                } else if (action === 1) {

                    activeTab.requestParams.minId = 0;
                }
                if (activeTab.have) {
                    this.loadingText = '加载中...';
                    uni.showLoading({
                        title: '加载中'
                    })
                    setTimeout(() => {
                        uni.hideLoading();
                    }, 5000);

                    Request('News_list', {
                        data: activeTab.requestParams,
                        // responseType: 'arraybuffer',
                    }).then(result => {

                        uni.hideLoading();

                        // console.log(JSON.stringify(result.data.data))
                        if (result.statusCode == 200) {

                            const data = result.data.data.map((news) => {

                                var item = {
                                    id: news.id,
                                    article_type: 0,
                                    datetime: Time.dateTimeformat(news.create_time,
                                        "mm/dd hh:MM"),
                                    time: news.create_time,
                                    title: news.title + "\n",
                                    // news.abstract.substring(0,100)

                                    nickname: news.nickname,
                                    comment_count: news.comment_num,
                                };

                                var image_list = [];
                                if (typeof news.image == 'object') {

                                    image_list = news.image.map((img) => {
                                        if (img && img.length > 6) {
                                            return {
                                                url: this.$config.getFileUrl(img) + ""
                                            }
                                        }
                                    })
                                    delete news.image
                                    switch (image_list.length) {
                                        case 1:
                                            item.article_type = 1;
                                            break;
                                        case 2:
                                            item.article_type = 2;
                                            break;
                                        case 3:
                                            item.article_type = 4;
                                            break;
                                        default:
                                            item.article_type = 1;
                                            break;

                                    }

                                } else {

                                    item.article_type = 1;
                                    if (news.image && news.image.length > 6) {
                                        image_list = [{
                                            url: this.$config.getFileUrl(news.image) +
                                                ""
                                        }];

                                    }

                                }
                                item.image_url = image_list[0] ? image_list[0].url : null
                                item.image_list = image_list;
                                // console.log(item)

                                return item

                            });
                            // console.log(data)
                            if (action === 1) {
                                activeTab.data = data;
                            } else {
                                data.forEach((news) => {
                                    activeTab.data.push(news);
                                });
                            }
                            if (typeof data == 'object') {
                                if (data.length > 0) {
                                    activeTab.requestParams.time = data[data.length - 1].time;
                                    activeTab.requestParams.minId = data[data.length - 1].id;
                                }
                                if (data.length < 10) {
                                    activeTab['have'] = false
                                    this.loadingText = '没有更多数据';

                                } else {
                                    activeTab['have'] = true;
                                }

                            } else {

                                activeTab['have'] = false
                                this.loadingText = '没有更多数据';
                            }

                        } else {
                            activeTab['have'] = false
                            this.loadingText = '没有更多数据';
                        }
                        this.tabBars[this.tabIndex] = activeTab
                    })
                }

            },
            goPage(detail, page = 'detail') {
                // console.log(page)
                switch (page) {
                    case 'detail':
                        detail.nickname = '匿名';
                        // console.log(JSON.stringify(detail))
                        uni.navigateTo({
                            url: '/pages/news/detail?query=' + encodeURIComponent(JSON.stringify(detail))
                        });
                        break;
                    case 'publish':
                        var activeTab = this.tabBars[this.tabIndex]
                        var query = {
                            id: activeTab.id,
                            name: activeTab.name,
                            user_id: activeTab.user_id
                        }
                        uni.navigateTo({
                            url: '/pages/news/publish?query=' + encodeURIComponent(JSON.stringify(query))
                        });
                        break;
                    default:
                        break;
                }

            },
            dislike(tabIndex, newsIndex) {
                uni.showModal({
                    content: '不感兴趣？',
                    success: (res) => {
                        if (res.confirm) {
                            this.tabBars[tabIndex].data.splice(newsIndex, 1);
                        }
                    }
                })
            },

            async changeTab(event) {
                let index = event.detail.current;
                if (this.isClickChange) {
                    this.tabIndex = index;
                    this.isClickChange = false;
                    return;
                }
                let tabBar = await this.getElSize('tab-bar');
                let tabBarScrollLeft = tabBar.scrollLeft;
                let width = 0;

                for (let i = 0; i < index; i++) {
                    let result = await this.getElSize(this.tabBars[i].ref);
                    width += result.width;
                }
                let winWidth = uni.getSystemInfoSync().windowWidth,
                    nowElement = await this.getElSize(this.tabBars[index].ref),
                    nowWidth = nowElement.width;
                if (width + nowWidth - tabBarScrollLeft > winWidth) {
                    this.scrollLeft = width + nowWidth - winWidth;
                }
                if (width < tabBarScrollLeft) {
                    this.scrollLeft = width;
                }
                this.isClickChange = false;
                this.tabIndex = index;

                // 首次切换后加载数据
                const activeTab = this.newsList[this.tabIndex];
                if (activeTab.data.length === 0) {
                    this.getList();
                }
            },
            getNodeSize(node) {
                return new Promise((resolve, reject) => {
                    dom.getComponentRect(node, (result) => {
                        resolve(result.size);
                    });
                });
            },

            getElSize(id) { //得到元素的size
                return new Promise((res, rej) => {
                    uni.createSelectorQuery().select('#' + id).fields({
                        size: true,
                        scrollOffset: true
                    }, (data) => {
                        res(data);
                    }).exec();
                });
            },
            async tapTab(index, tab) {
                //点击tab-bar
                if (tab && tab.ref == 'publish') {
                    // console.log(tab)
                    uni.navigateTo({
                        url: '/pages/news/' + tab.ref,
                        // url: '/pages/news/detail?query=' + encodeURIComponent(JSON.stringify(detail))
                    });
                } else {
                    if (this.tabIndex === index) {
                        return false;
                    } else {
                        this.tabIndex = index;
                        // 首次切换后加载数据
                        const activeTab = this.tabBars[this.tabIndex];
                        if (activeTab.data.length === 0) {

                            this.getList();
                        }
                    }
                }
            }
        }
    }
</script>
<style>
    page {
        /* background-color: #999; */
        height: 100%;
        font-size: 11px;
        line-height: 1.8;
    }

    .uni-tab-bar {
        display: flex;
        flex: 1;
        flex-direction: column;
        overflow: hidden;
        height: 100%;
    }

    .uni-tab-bar .list {
        width: 750upx;
        height: calc(100% - 70upx);
        margin-top: 70upx;
    }

    .uni-swiper-tab {
        width: 100%;
        white-space: nowrap;
        line-height: 70upx;
        height: 70upx;
        /* border-bottom: 1px solid #c8c7cc; */
        position: fixed;
        background: #FFFFFF;
        z-index: 999;
        top: var(--window-top);
        left: 0;
    }

    .swiper-tab-list {
        font-size: 30upx;
        width: 150upx;
        display: inline-block;
        text-align: center;
        color: #555;
    }

    .uni-tab-bar .active {
        color: #007AFF;
    }

    .uni-tab-bar .swiper-box {
        flex: 1;
        width: 100%;
        height: calc(100% -70upx);
        overflow: scroll;
    }

    .uni-tab-bar-loading {
        text-align: center;
        padding: 20upx 0;
        font-size: 14px;
        color: #CCCCCC;
    }
</style>
