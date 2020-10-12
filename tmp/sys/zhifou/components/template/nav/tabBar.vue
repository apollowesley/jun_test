<template>
    <view class="uni-flex uni-row" style="justify-content:space-between;box-shadow: 1px 0px 3px #888888;width: 100%;">
        <view v-for="(item,idx) in tabBarData" :style="item.flex?'flex:'+item.flex:''" style="padding: 0 5upx; box-sizing: border-box;"
            :key="idx">
            <view class="uni-flex-item uni-flex uni-row" style="width: 100%;align-items: center; justify-content: center; height: 100%;">

                <view v-if="item.type=='input'" class=" uni-flex uni-row  uni-flex-item " style="align-items: center;padding: 10upx;justify-content: center;">
                    <view class="uni-flex-item uni-flex uni-row" style="height: 100%; box-sizing: border-box; align-items: center;position: relative;justify-content: center;">
                        <input @tap="Tap(idx,item,item.name,'input')" :class="index==idx && tag=='input'?'uni-badge-primary uni-badge-inverted':''"
                            :disabled="!item.disabled?true:false" class="uni-flex-item" v-model="item.value" style="width: 100%; box-shadow: 0px 0px 3px;padding: 5upx 35upx 6upx 15upx;border-radius: 20upx;font-size: 0.8em;"
                            :style="{'padding-left':item.left?'35upx':'','padding-right':item.right?'35upx':''}"
                            :placeholder="item.placeholder">
                        </input>
                        <text @tap="Tap(idx,item,item.name,'right')" :class="index==idx && tag=='right'?'uni-badge-primary uni-badge-inverted':''"
                            v-if="item.right" class=" iconfont" style="font-size:1em; position: absolute;right:6upx;"
                            v-html="item.right">
                        </text>
                        <text v-if="item.left" class=" iconfont" :class="index==idx && tag=='left'?'uni-badge-primary uni-badge-inverted':''"
                            @tap="Tap(idx,item,item.name,'left')" style="font-size:1em; position: absolute;left:6upx;"
                            v-html="item.left">
                        </text>
                    </view>
                    <view class="uni-flex uni-column" style="justify-content: center;align-items: center;padding:0 10upx;height: 100%;margin-top: -3upx;">
                        <text :class="index==idx && tag=='text'?'uni-badge-primary uni-badge-inverted':''" v-if="item.text"
                            @tap="Tap(idx,item,item.name,'text')" style="" v-html="item.text">
                        </text>
                        <text class=" iconfont" :class="index==idx && tag=='icon'?'uni-badge-primary uni-badge-inverted':''"
                            v-else-if="item.icon" @tap="Tap(idx,item,item.name,'icon')" v-html="item.icon">
                        </text>
                    </view>


                </view>

                <view v-else class=" uni-flex uni-column   uni-flex-item" style="padding: 10upx;align-items: center;justify-content: center;"
                   :class="index==idx?'uni-badge-primary uni-badge-inverted':''">

                    <view v-if="item.msg" class=" uni-flex-item" style="position: relative;align-self: flex-end;" >
                        <view v-if="item.msg" class="uni-badge uni-badge-danger"  @tap="Tap(idx,item,item.name,'msg')"  style="font-size: 0.7em;position: absolute;right: -5upx;top: -10upx; ">
                            <text v-if="item.msg>10">…</text>
                            <text v-else>{{item.msg}}</text>
                        </view>
                    </view>
                    <view class="uni-flex uni-column" v-if="item.icon && item.text" style="justify-content: center;align-items: center;">
                        <text  @tap="Tap(idx,item,item.name,'icon')"  class=" iconfont" style="font-size:1.4em;line-height: 1.3em;margin-top: -10upx;"
                            v-html="item.icon">

                        </text>
                        <text  @tap="Tap(idx,item,item.name,'text')"   style="font-size: 0.7em;line-height:0.7em;">
                            {{item.text}}
                        </text>
                    </view>
                    <view class="uni-flex uni-row" v-else-if="item.icon"   style="justify-content: center;align-items: center;">
                        <text class=" iconfont" style="font-size:1.5em;line-height: 1.5em;" v-html="item.icon" @tap="Tap(idx,item,item.name,'icon')" >

                        </text>
                        <view v-if="item.tag"   @tap="Tap(idx,item,item.name,'tag')"  class="uni-flex uni-column" style="justify-content: center;">
                            <text  style="font-size: 0.8em; opacity: 0.8;">{{" "+item.tag}}</text>
                        </view>
                    </view>
                    <view v-else-if="item.text"   class="uni-flex" style="font-size: 1em;line-height:1em;">
                        <text style="text-align: center; " @tap="Tap(idx,item,item.name,'text')" >
                            {{item.text}}
                        </text>
                        <text v-if="item.tag"   @tap="Tap(idx,item,item.name,'tag')"  class="uni-badge " style="font-size: 0.8em; opacity: 0.8;">
                            <text>{{" "+item.tag}}</text>
                        </text>
                    </view>

                </view>
            </view>




        </view>
    </view>

</template>

<script>
    export default {
        props: {
            'change': {
                default: function() {
                    return
                },
                type: Function,
            },
            'tabBar': {
                default: function() {
                    return [{
                            flex: '1',
                            // msg: 0,
                            // name: '评论',
                            // tag:'12345',
                            icon: '&#xe8b4;',
                            url: '/pages/order/list'
                        }, {
                            flex: '1',
                            // msg: 0,
                            // name: '评论',
                            tag: '12345',
                            icon: '&#xe8bb;',
                            url: '/pages/order/list'
                        }, {
                            flex: '1',
                            // msg: 0,
                            // name: '订单',
                            // tag: '12345',
                            icon: '&#xe871;',
                            url: '/pages/order/list'
                        },
                        {
                            // msg: 1,
                            flex: '2',
                            type: 'input',
                            placeholder: '留言',
                            // name: '发送',
                            icon: '&#xe600;',
                            url: '/pages/order/list'
                        }, {
                            flex: '',
                            name: '发送',
                            url: '/pages/order/list'
                        }

                    ]
                },
                type: Array,
            },
        },
        name: "bottomNav",
        data() {
            return {
                index: 0,
                tag: '',
            }
        },
        mounted() {
            // console.log("template/nav/bottom/mounted")

        },
        computed: {
            tabBarData() {
                return this.tabBar.filter(e => {
                    if (e.tag && e.tag.length > 4) {
                        e.tag = e.tag.substring(0, 4) + '+'
                    }
                    return e
                })
            }
        },
        methods: {
            Tap(idx, item, name, tag) {
                this.tag = tag || null;
                this.index = idx
                this.change({
                    name: name ? name : null,
                    item,
                    idx,
                    tag: tag ? tag : null,
                    data: this.tabBarData
                })
            }
        }
    }
</script>

<style>

</style>
