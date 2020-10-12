<template>
    <view class="uni-page-body">
        <view class="yc-ul">
            <view class="yc-li" v-for="(item,key) in userInfo" :key="item.id">
                <view class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;" @tap="tapItem(item)">
                    <view class="" v-if="labels[key]">
                        {{labels[key]}}:
                    </view>
                    <view class="" v-else>
                        {{key}}:
                    </view>
                    <view class="" v-if="key=='create_time'">
                        {{item|time}}
                    </view>
                    <view class="" v-else-if="key=='update_time'">
                        {{item|time}}
                    </view>
                    <view class="" v-else-if="key=='delete_time'">
                        {{item|time}}
                    </view>
                    <view class="" v-else-if="key=='sex'">
                        {{item|sex}}
                    </view>
                    <view class="" v-else-if="key=='state'">
                        {{item|state}}
                    </view>
                    <view v-else class="">
                        {{item}}
                    </view>
                </view>
            </view>
            <view class="yc-li" v-for="(item,idx) in Btn" :key="item.name">
                <!-- <view class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;" @tap="tapItem(item)"> -->
                <button type="default" style="width: 100%;" @tap="tapUpdate(item)">{{item.label}}</button>
                <!--                    <view class="">
                        {{item}}
                    </view> -->
                <!-- </view> -->
            </view>
        </view>
    </view>
</template>

<script>
    import {
        Time
    } from '@/common/yc_js/';
    var sex = {
        '1': '男',
        '0': '女'
    }
    var state = {
        '0': '待审核',
        '1': '正常',
        '-1': '封禁'
    }
    export default {
        data() {
            return {
                Btn: [{
                    label: '删除/恢复',
                    name: 'del'
                }, {
                    label: '封禁/解除',
                    name: 'close'
                }],
                labels: {
                    nickname: '昵称',
                    id: 'ID',
                    headimg: '头像',
                    create_time: '创建时间',
                    update_time: '更新时间',
                    delete_time: '删除时间',
                    state: '账号状态',
                    group: '用户组',
                    sex: '性别',
                    ip: '注册地ip',
                    area: '注册地区',
                    country: '国家',

                },
                userInfo: {
                    id: '1234',
                    nickname: '小不点',
                    headimg: '头像',
                    create_time: '1272331031',
                    update_time: '1456233165',
                    delete_time: '41635485',
                    state: '1',
                    group: '1',
                    sex: '1',
                    ip: '127.11.0.11',
                    area: 'beijing',
                    country: 'china',
                }
            }
        },
        filters: {
            state(e) {
                return state[e] || '未知'
            },
            sex(e) {

                return sex[e] || '未知'
            },
            time(e) {
                // console.log(e)
                return Time.formatDate(e) || '未知'
            }
        },
        onLoad() {

        },
        methods: {
            tapUpdate(item) {
                uni.showModal({
                    title: '提示',
                    content: '您确定要进行【' + item.label+'】操作？',
                    success: (e) => {
                        if(e.confirm){
                            switch (item.name) {
                                case 'close':
                                 console.log(e)
                                    break;
                                case 'del':
                                 console.log(e)
                                    break;
                                default:
                                    break;
                            }
                        }
                       
                    }
                })

            },
            tapItem(e) {
                console.log(e)
                switch (e.name) {
                    case 'user':
                        uni.navigateTo({
                            url: '/pages/' + e.name + '/index'
                        })
                        break;
                    case 'realname':
                        break;
                    default:
                        break;
                }
            }
        }
    }
</script>

<style>

</style>
