<template>
    <view class="uni-page-body">
        <popup-input ref='popupInput'></popup-input>
        <scroll-view scroll-y style="height: 100%;">
            <view class="yc-ul" v-if="realnameInfoUpdate" style="background: #DD524D; padding: 5px; box-sizing: border-box;">
                <view class="yc-li" style="background: #DD524D;font-weight: 600;">
                    <view class="yc-li-item">
                        数据变化【待审核】
                    </view>
                </view>
                <view class="yc-li" v-for="(item,key) in realnameInfoUpdate" :key="item.id">
                    <view class="" v-if="key=='images'">
                        <view class="yc-li-item">
                            {{labels[key]}}:
                        </view>
                        <view class="yc-li-item" style="height: auto;" v-for="(img,k) in item" :key='img.name'>
                            <view class="" style=" ">
                                <view class="" v-if="labels[img.name]">
                                    {{labels[img.name]}}
                                </view>
                                <view class="" v-else>
                                    {{img.name}}
                                </view>
                            </view>
                            <view class="" style="padding: 10px;">
                                <image style="height: 200px;" src="../../static/ad/1.jpg" mode=""></image>
                            </view>

                        </view>
                    </view>
                    <view class="" v-else-if="key=='photo'">
                        <view class="yc-li-item">
                            {{labels[key]}}:
                        </view>
                        <view class="yc-li-item" style="height: auto;" v-for="(img,k) in item" :key='img.name'>
                            <view class="" style=" ">
                                <view class="" v-if="labels[img.name]">
                                    {{labels[img.name]}}
                                </view>
                                <view class="" v-else>
                                    {{img.name}}
                                </view>
                            </view>
                            <view class="" style="padding: 10px;">
                                <image style="height: 200px;" src="../../static/ad/1.jpg" mode=""></image>
                            </view>

                        </view>
                    </view>
                    <view v-else class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;">
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
                        <view class="" v-else-if="key=='effective_time'">
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
            </view>

            <view class="yc-ul" style="background: #4CD964; padding: 5px; box-sizing: border-box;">
                <view class="yc-li" style="background: #4CD964;font-weight: 600;">
                    <view class="yc-li-item">
                        实名内容
                    </view>
                </view>
                <view class="yc-li" v-for="(item,key) in realnameInfo" :key="item.id">
                    <view class="" v-if="key=='images'">
                        <view class="yc-li-item">
                            {{labels[key]}}:
                        </view>
                        <view class="yc-li-item" style="height: auto;" v-for="(img,k) in item" :key='img.name'>
                            <view class="" style=" ">
                                <view class="" v-if="labels[img.name]">
                                    {{labels[img.name]}}
                                </view>
                                <view class="" v-else>
                                    {{img.name}}
                                </view>
                            </view>
                            <view class="" style="padding: 10px;">
                                <image style="height: 200px;" src="../../static/ad/1.jpg" mode=""></image>
                            </view>

                        </view>
                    </view>
                    <view class="" v-else-if="key=='photo'">
                        <view class="yc-li-item">
                            {{labels[key]}}:
                        </view>
                        <view class="yc-li-item" style="height: auto;" v-for="(img,k) in item" :key='img.name'>
                            <view class="" style=" ">
                                <view class="" v-if="labels[img.name]">
                                    {{labels[img.name]}}
                                </view>
                                <view class="" v-else>
                                    {{img.name}}
                                </view>
                            </view>
                            <view class="" style="padding: 10px;">
                                <image style="height: 200px;" src="../../static/ad/1.jpg" mode=""></image>
                            </view>

                        </view>
                    </view>
                    <view v-else class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;">
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
                        <view class="" v-else-if="key=='effective_time'">
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
            </view>
            <view class="yc-li" v-for="(item,idx) in Btn" :key="item.name">
                <button type="warn"  style="width: 100%;" @tap="tapUpdate(item)">{{item.label}}</button>
            </view>
        </scroll-view>
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
                    label: '审核/退回',
                    name: 'examine'
                }],
                labels: {
                    zhengmian: '正面',
                    beimian: '背面',
                    shouchi: '手持',

                    user_id: '用户ID',
                    name: '姓名',
                    identifier: "证件号",
                    area_id: '地区',
                    phone: '手机号',
                    address: '家庭住址',
                    images: '证件照',
                    photo: '人像采集照',
                    effective_time: '证件有效期',
                    tips: '认证提示',
                    fail_explain: '失败说明',
                    create_time: '创建时间',
                    update_time: '更新时间',
                    delete_time: '删除时间',
                    state: '实名状态',
                    // nickname: '昵称',
                    // id: 'ID',
                    // headimg: '头像',

                    // state: '账号状态',
                    // group: '用户组',
                    // sex: '性别',
                    // ip: '注册地ip',
                    // area: '注册地区',
                    // country: '国家',


                },
                realnameInfoUpdate: {
                    id: '1234',
                    user_id: '1234545',
                    name: '李安',
                    identifier: '证件号',
                    area_id: '地区',
                    phone: '实名手机号',
                    address: '家庭地址',
                    "identifier": "210881199908089999",
                    "phone": "18888888889",
                    "effective_time": 1262131200,
                    "images": [{
                        name: 'zhengmian',
                        "image": "user_pealname\/19\/12\/30\/1577703373273797.jpeg"
                    }, {
                        name: 'beimian',
                        "image": "user_pealname\/19\/12\/30\/1577703373683115.jpeg"
                    }, {
                        name: 'shouchi',
                        "image": "user_pealname\/19\/12\/30\/1577703373743489.jpeg"
                    }], //证件照
                    photo: [{}], //人像采集照
                    state: 1, //实名审核状态
                    tips: '认证提示',
                    fail_explain: '失败说明',

                    create_time: '1272331031',
                    update_time: '1456233165',
                    delete_time: '41635485',
                },
                realnameInfo: {

                    id: '1234',
                    user_id: '1234545',
                    name: '李安',
                    identifier: '证件号',
                    area_id: '地区',
                    phone: '实名手机号',
                    address: '家庭地址',
                    "identifier": "210881199908089999",
                    "phone": "18888888889",
                    "effective_time": 1262131200,
                    "images": [{
                        name: 'zhengmian',
                        "image": "user_pealname\/19\/12\/30\/1577703373273797.jpeg"
                    }, {
                        name: 'beimian',
                        "image": "user_pealname\/19\/12\/30\/1577703373683115.jpeg"
                    }, {
                        name: 'shouchi',
                        "image": "user_pealname\/19\/12\/30\/1577703373743489.jpeg"
                    }], //证件照
                    photo: [{}], //人像采集照
                    state: 1, //实名审核状态
                    tips: '认证提示',
                    fail_explain: '失败说明',

                    create_time: '1272331031',
                    update_time: '1456233165',
                    delete_time: '41635485',

                    // nickname: '小不点',
                    // headimg: '头像',
                    // sex: '1',
                    // ip: '127.11.0.11',
                    // area: 'beijing',
                    // country: 'china',
                }
            }
        },
        filters: {
            zhengjian(e) {

                return
                console.log(e)
            },
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
                    cancelText: '退回',
                    confirmText: '通过',
                    content: '您确定通过审核？',
                    success: (e) => {
                        if (e.confirm) {
                            this.realnameInfoUpdate = null
                        } else {
                            this.$refs.popupInput.show({
                                title: '退回原因'
                            }, (e => {

                                if (e.type == 'button') {
                                    console.log(e.content[0].items[
                                        0].value)
                                    if (e.idx == 1) {

                                    } else {

                                    }
                                    this.realnameInfoUpdate = null
                                    this.$refs.popupInput.show(null)
                                }

                            }));
                        }

                    }
                })
            }
        }
    }
</script>

<style>

</style>
