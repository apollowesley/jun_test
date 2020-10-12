<template>
    <view class="m-item uni-column" :id="'message'+id" style="position: relative;">
        <view  style="height: auto;" v-if="data.nickname" class="" :style="notMe?'align-self: flex-start;':'align-self: flex-end;font-size: 0.6em;'">
            <text style="color:#C8C7CC;padding: 5px 18upx; font-weight: 200;">{{data.nickname}}</text>
        </view>
        <view class="uni-flex uni-row">
            <view class="m-left" @click="tapMsg('headimg',notMe,data)">
                <image class="head_icon" mode="aspectFill" :src="youHeadimg" v-if="notMe"></image>
            </view>
            <view class="m-content">

                <view class="m-content-head" :class="notMe?'m-content-head-left':'m-content-head-right'">
                    <view :class="notMe?'m-content-head-home':'m-content-head-customer'">
                        <view v-if="data.content.type && data.content.type=='text'">
                            {{data.content.value}}
                        </view>

                    </view>
                </view>
            </view>
            <view class="m-right"  @click="tapMsg('headimg',notMe,data)">
                <image class="head_icon" mode="aspectFill" :src="headimg" v-if="!notMe"></image>
            </view>
        </view>

        <view v-if="data.datetime" class="uni-flex" style="justify-content: center;font-size: 0.7em;width: 100%;bottom: 0upx;">
            <text style="color: #C8C7CC;padding:0 5upx 0 5upx;font-weight: 100;">{{data.datetime}}</text>
        </view>
    </view>
</template>

<script>

    export default {
        props: ['message', 'id', 'mid', 'headimg', 'youHeadimg'],
        data() {
            return {
                lastTime: null,
            }
        },
        methods: {

        },
        computed: {
            data(){
              return   this.message
            },
//             data: {
//                 get() {
//                     var message = JSON.parse(JSON.stringify(this.message));
//                     // console.log(message.time)
//                     var time = null
//                     // if ((message.time*1000+300000)<Date.now()) {
//                     // 300秒以前的数据显示时间
//                     time = Time.dateTimeformat(message.time)
//                     // }
//                     message.time = time
//                     return message;
//                 },
//                 set(val) {
// 
//                 }
//             },
            notMe() {
                if (this.message.uid == this.mid) {
                    return false
                } else {
                    return true
                }
            }
        }
    }
</script>

<style>
    view {
        width: auto;
    }

    .m-item {
        justify-content: space-between;
        display: flex;
        flex-direction: row;
        padding-bottom: 20upx;
    }

    .m-left {
        flex-direction: row;
        box-sizing: border-box;
        display: flex;
        width: 120upx;
        justify-content: center;
        align-items: flex-start;
    }

    .m-content {
        box-sizing: border-box;
        display: flex;
        flex: 1;
        flex-direction: column;
        justify-content: center;
        word-break: break-all;
    }

    .m-right {
        flex-direction: row;
        box-sizing: border-box;
        display: flex;
        width: 120upx;
        justify-content: center;
        align-items: flex-end;
    }

    .head_icon {
        width: 80upx;
        height: 80upx;
    }

    .m-content-head {
        display: flex;
        justify-content: space-between;
        flex-direction: row;
        position: relative;
    }

    .m-content-head-left {
        text-align: left;
        justify-content: flex-start;

    }

    .m-content-head-right {
        text-align: right;
        justify-content: flex-end;
    }

    .m-content-head-home {

        justify-content: flex-start;
        text-align: left;
        background: #1482d1;
        border: 1px #1482d1 solid;
        border-radius: 20upx;
        padding: 15upx;
        color: white;
    }

    .m-content-head-home:before {
        border: 15upx solid transparent;
        border-right: 15upx solid #1482d1;
        left: -26upx;
        width: 0;
        height: 0;
        position: absolute;
        content: ' '
    }

    .m-content-head-customer {
        border: 1upx white solid;
        background: white;
        border-radius: 20upx;
        padding: 15upx;
    }

    .m-content-head-customer:after {
        border: 15upx solid transparent;
        border-left: 15upx solid white;
        top: 20upx;
        right: -26upx;
        width: 0;
        height: 0;
        position: absolute;
        content: ' '
    }
</style>
