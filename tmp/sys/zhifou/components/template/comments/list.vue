<template>
    <view>

        <view class="uni-comment-list" v-for="(items,idx) in commentsData" :key="idx">
            <view v-if="items.headimg" class="uni-comment-face" @tap="tapBtn({key:'headimg',items,level:1})">
                <image :src="items.headimg" mode="widthFix"></image>
            </view>
            <view style="width: 100%;">
                <view class="uni-comment-right-body" style="font-size: 1em;">

                    <view class="uni-comment-top" style="font-size: 0.7em;" @tap="tapBtn({key:'nickname',items,level:1})">
                        <text>{{items.nickname}}</text>
                    </view>
                    <view @tap="tapBtn({key:'content',items,level:1})" class="uni-comment-content">{{items.content}}</view>
                    <view class="uni-comment-date uni-flex" style="justify-content: space-between;width: 100%;">
                        <view class="" style="font-size: 0.8em;opacity: 0.7;">{{items.time}}</view>
                        <view class="uni-row uni-flex-item uni-flex" style="justify-content:flex-end;"  v-if="bottonObj" >
                            <view v-for="(btn,key) in bottonObj" :key="key" style="padding:0 10upx;justify-content: center;align-items: center;display: flex;"
                                :class="btn.class?btn.class:''" class="iconfont" hover-class="uni-list-cell-hover" @tap="tapBtn({key,idx,items,level:1})">
                                    <text v-if="btn.value" :style="{'font-size':btn.size?btn.size:'','color':items['is_'+key]?'#00B2EE':''}"
                                        v-html="btn.value"></text>
                                    <text v-if="items[key+'_num']" style="padding-left: 5upx;">{{items[key+'_num']}}</text>
                            
                            </view>
                        </view>
                        
                    </view>

                </view>

                <view v-if="idx==index" class="uni-reply-body uni-flex uni-row" v-for="(item,idxb) in items.reply" :key="idxb">
                    <view v-if="item.headimg" class="uni-comment-face" @tap="tapBtn({key:'headimg',item,idxb,idx,level:2})">
                        <image :src="item.headimg" mode="widthFix"></image>
                    </view>
                    <view class="uni-comment-right uni-flex-item" style="border-top:0px #E5E5E5 solid; ">
                        <view class="uni-comment-right-body">
                            <view style="">
                                <text :style="{background:item.user_id==uid?'rgba(200,200,200,0.4)':''}" class="uni-comment-top"
                                    @tap="tapBtn({key:'nickname',item,idxb,idx,level:2})">{{item.nickname}}</text>
                                <text style="padding:0 10upx;font-size: 0.7em;" class="iconfont" @click="onUid(item.user_id,'uid')">&#xe6ff; :</text>

                            </view>
                            <view class="uni-comment-content">

                                <text @tap="tapBtn({key:'content',item:item,items:items,level:2})">
                                    {{item.content}}
                                </text>
                                <text :style="{background:item.to_uid==uid?'rgba(200,200,200,0.4)':''}" style="font-size: 0.8em;"
                                    v-if="item.to_nickname" @tap="tapBtn({key:'to_nickname',item,idxb,idx,level:2})">//@
                                    <text style="color:#00B2EE;">{{item.to_nickname}}</text></text>
                                <text style="padding:0 10upx; font-size: 0.7em;" class="iconfont" @click="onUid(item.to_uid,'uid')">&#xe6ff;</text>
                            </view>

                            <view v-if="bottonObj" class="uni-comment-date uni-flex">
                                <view class="uni-flex-item" style="font-size: 0.8em;opacity: 0.7;">
                                    {{item.time}}

                                </view>
                                <view v-for="(btn,key) in bottonObj" :key="key" style="padding:0 10upx;justify-content: center;align-items: center;display: flex;"
                                    :class="btn.class?btn.class:''" class="iconfont" hover-class="uni-list-cell-hover"
                                    @tap="tapBtn({key,idx,item,idxb,level:2})">
                                    <view class="uni-row" v-if="key!='reply'">
                                        <text v-if="btn.value" :style="{'font-size':btn.size?btn.size:'','color':item['is_'+key]?'#00B2EE':''}"
                                            v-html="btn.value"></text>
                                        <text v-if="item[key+'_num']">{{item[key+'_num']}}</text>
                                    </view>

                                </view>
                            </view>

                        </view>

                    </view>
                </view>
                <view v-if=" idx==index && items.have" style="font-size: 0.7em;text-align: center;padding: 5upx;"
                    @click="getReply(items,idx)">
                    获取更多……
                </view>
            </view>

        </view>
    </view>
</template>

<script>
    export default {
        props: {

            bottonObj: {
                type: Object,
                default: function() {
                    return {
                        reply: {
                            class: 'iconfont',
                            value: '&#xe8bb;',
                            name: 'reply',
                            size: '1.2em',
                        },
                        zan: {
                            class: 'iconfont',
                            value: '&#xe876;',
                            name: 'zan',
                            size: '1.2em',
                        },
                        speak: {
                            class: 'iconfont',
                            value: '@',
                            name: 'speak',
                            size: '1.2em',
                        }

                    }
                }

            },
            getMore: {
                type: Function,
                default: function() {
                    return function(){}
                }
            },
            change: {
                type: Function,
                default: function() {
                    return function(){}
                }
            },
            commentsData: {
                type: Array,
                default: function() {
                    return [{
                        headimg: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png",
                        nickname: '小明',
                        content: '抢个沙发',
                        time: '12/5 11:23',
                        reply: null
                    }, {
                        headimg: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png",
                        nickname: '小刚',
                        content: '还是你快呀',
                        time: '12/5 11:23',
                        reply: [{
                            headimg: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png",
                            nickname: '小明',
                            content: '再抢个沙发',
                            time: '12/5 11:23',
                            reply: []
                        }, {
                            headimg: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png",
                            nickname: '小刚',
                            content: '还是你快呀',
                            time: '12/5 11:23',
                            reply: []
                        }]
                    }, {
                        headimg: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png",
                        nickname: '小溜溜',
                        content: '晚了一步。被你们抢先了',
                        time: '12/5 11:23',
                        reply: [{
                            headimg: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png",
                            nickname: '小明',
                            content: '抢个小沙发',
                            time: '12/5 11:23',
                            reply: []
                        }, {
                            headimg: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/uni@2x.png",
                            nickname: '小刚',
                            content: '还是你快呀，又没抢到',
                            time: '12/5 11:23',
                            reply: []
                        }]
                    }]
                }
            }
        },
        data() {
            return {
                title: "评论",
                index: null,
                // to_uid: 0,
                uid: 0
            }
        },
        methods: {
            onUid(uid, type) {

                this[type] = uid
            },
            getReply(e, idx) {
                this.index = idx;
                this.getMore(e, idx)
            },
            tapBtn(e) {

                if (e.key == 'reply') {
                    this.index = e.idx;
                }
                this.change(e)

            }
        }
    }
</script>

<style>
    .uni-comment {
        padding: 0;
    }

    .uni-comment-top {
        font-size: 0.7em;
        color: rgb(0, 160, 250);
    }

    .uni-comment-list {
        padding: 0;
        flex-direction: row;
    }

    .uni-padding-wrap {
        width: 100%;
        box-sizing: border-box;
    }

    .uni-reply-body {

        padding-top: 5upx;
        padding-left: 10upx;
        /* border-bottom: 1px #E5E5E5 solid; */
    }

    .uni-comment-face {
        background: #FFFFFF;
    }

    .uni-comment-right {}

    .uni-comment-right-body {
        padding: 5upx 15upx;
        background: #FFFFFF;
        /* border-top: 1px #E5E5E5 solid; */
    }
</style>
