<template>

    <view class="uni-page-body">

        <view class="" style="padding-top: 10upx;" hover-class="uni-list-cell-hover" v-for="(item,index) in addressAll"
            :key="index">
            <view class="uni-flex uni-media-list">
                <view class="uni-flex-item ">
                    {{item.name}}
                </view>
                <view class="uni-flex-item">
                    {{item.phone}}
                </view>
                <view class="" style="align-self: flex-end;width: 50upx;font-size: 1.3em;">
                    <text v-if="item.selected" class="iconfont" style="color: #32cd32;" :data-index="index" @tap="selectList(index)">&#xe99c;</text>
                    <text v-else class="iconfont" :data-index="index" @tap="selectList(index)">&#xe9ae;</text>
                </view>
            </view>
            <view class="uni-flex uni-media-list">
                <view class="uni-flex-item">{{item.address}}</view>
                <view class="" style="align-self: flex-end;width: 50upx;font-size: 1.3em;">
                    <text class="iconfont" style="color: rgb(200,200,200);" @tap="delet(index)">&#xe921;</text>
                </view>
            </view>

        </view>


        <view style="position: absolute;width: 100%;height: 100%;top: 0;background:;bottom: 0;display: flex;flex-direction: column;justify-content: center;"
            v-if="hasAddress">
            <view class="" style="opacity: 0.5;width: 100%;background: #000000;height: 100%;position: absolute;z-index: 1;">

            </view>
            <view style="position: absolute;z-index: 2; width: 100%;background: #FFFFFF;">
                <view class="yc-list">
                    <view class="yc-list-item uni-flex" style="justify-content: center;">
                        <view class="">
                            输入新地址
                        </view>
                        <text class="iconfont " style="position: absolute;right: 10upx; color: rgb(200,200,200);font-size:1.3em ;"
                            @tap="closeInput">&#xe921;</text>
                    </view>
                    <view class="uni-form-item uni-column">
                        <view class="title">姓名</view>
                        <input class="uni-input" maxlength="8" v-model="form.name" placeholder="最大输入长度为8" />
                    </view>
                    <view class="uni-form-item uni-column">
                        <view class="title">电话</view>
                        <input class="uni-input" maxlength="11" v-model="form.phone" placeholder="手机号码" />
                    </view>

                    <view class="uni-form-item uni-column">
                        <view class="title">地区</view>
                        <input class="uni-input " disabled @click="showMulLinkageThreePicker" maxlength="32" style="flex: 1;"
                            type="text" v-model="value" />
                    </view>
                    <mpvue-city-picker :themeColor="themeColor" ref="mpvueCityPicker" :pickerValueDefault="cityPickerValueDefault"
                        @onConfirm="cityConfirm"></mpvue-city-picker>
                    <view class="uni-form-item uni-column">
                        <view class="title">详细地址</view>
                        <input class="uni-input " maxlength="32" style="flex: 1;" type="text" v-model="form.address" />
                    </view>

                </view>
                <button style="width: 100%;" @tap="add()" type="primary">确认添加</button>
            </view>
        </view>

        <view v-if="!hasAddress" style="height: 200upx;">
            <button style="width: 100%;" @tap="closeInput">
                <text>添加新地址</text>
            </button>
            <button style="width: 100%;" @tap="closePage" type="warn">
                <text>关闭</text>
            </button>
        </view>

    </view>
</template>

<script>
    import Validate from "../../common/yc_js/Validate.js";
    import Storage from "@/common/yc_js/Storage.js";
    import Request from "../../request/index.js";
    import mpvueCityPicker from '../../components/mpvue-citypicker/mpvueCityPicker.vue';
    export default {
        components: {
            mpvueCityPicker
        },
        onShow() {

            this.onshow = true;
            this.$store.commit('pages', this.pageName || "userAddress")
        },
        data() {
            return {
                pageName: 'userAddress',
                value: '请选择',
                cityPickerValueDefault: [0, 0, 1],
                themeColor: '#007AFF',
                areaObj: {}, //三联地址内容
                form: {
                    name: '',
                    phone: '',
                    address: '',
                    area_id: ""
                },
                hasAddress: false,
            }
        },
        computed: {
            userInfo() {
                return this.$store.getters.userInfo;
            },
            addressAll() {
                var addressAll = this.$store.getters.addressAll;
                for (var i = 0; i < addressAll.length; i++) {
                    if (addressAll[i].id == this.userInfo.address_id) {
                        addressAll[i].selected = true;
                    } else {
                        addressAll[i].selected = false;
                    }

                }
                return addressAll
            }
        },
        onLoad() {
            // if (!this.addressAll) {
            this.getAddressAll()
            // }
        },
        methods: {
            closeInput() {
                this.hasAddress = !this.hasAddress
            },
            getAddressAll() {


                Request('UserAddress_List', {
                        data: {}
                    })
                    .then(e => {
                        // console.log(JSON.stringify(e.data.data))
                        if (e.data.code == 200) {
                            this.$store.commit('addressAll', e.data.data);
                        }
                    })
            },
            // 三级联动选择
            showMulLinkageThreePicker() {
                this.$refs.mpvueCityPicker.show()
            },
            cityConfirm(e) {
                console.log(e)
                this.value = e.label;
                this.areaObj = e;
                this.form.area_id = e.cityCode;

            },
            closePage() {
                uni.navigateBack({
                    delta: 2
                });
            },
            delet(index) {
                var addressAll = this.addressAll;
                var id = addressAll[index].id || null;
                // console.log(addressAll)
                if (id) {
                    if (addressAll[index].selected) {
                        uni.showToast({
                            icon: "none",
                            title: "默认地址无法删除"
                        })
                    } else {
                        var that = this;
                        uni.showModal({
                            title: '提示',
                            content: '你确认要删除吗？',
                            success: function(res) {
                                if (res.confirm) {
                                    Request('UserAddress_Delete', {
                                            data: {
                                                id: id
                                            }
                                        })
                                        .then(e => {
                                            // console.log(e)
                                            if (e.data.code == 200) {
                                                uni.showToast({
                                                    title: '删除成功',
                                                    duration: 2000
                                                })
                                                var newdata = addressAll.filter(e => {
                                                    if (e.id != id) {
                                                        return e;
                                                    }
                                                });
                                                // console.log(newdata)
                                                that.$store.commit('addressAll', newdata);

                                            }
                                        })
                                } else if (res.cancel) {
                                    // console.log('用户点击取消');
                                }
                            }
                        });

                    }
                } else {
                    uni.showToast({
                        icon: "none",
                        title: "服务器不存在该数据,无需进行删除操作"
                    })
                }
            },

            // 选中默认地址
            selectList(key) {
                var addressAll = this.addressAll;
                // id不存在的地址可能是本地地址，需要先提交到服务器返回id
                if (addressAll[key].id) {
                    this.userUpdate(key)
                } else {
                    for (let i in this.form) {
                        if (addressAll[key][i]) {
                            this.form[i] = addressAll[key][i];
                        }
                    }
                    this.add()
                }
                // console.log(addressAll)
            },
            // 更新用户表默认地址id
            userUpdate(key) {
                var addressAll = this.addressAll;
                var userInfo = this.$store.getters.userInfo;
                if (addressAll[key].id == userInfo['address_id']) {
                    uni.showToast({
                        title: '无需操作',
                        icon: 'none',
                        duration: 2000
                    })
                } else {
                    var that = this;
                    uni.showModal({
                        title: '提示',
                        content: '你确认要修改吗？',
                        success: function(res) {
                            if (res.confirm) {
                                userInfo.address_id = addressAll[key].id || null;
                                userInfo.address = addressAll[key];
                                var param = {};
                                param['address_id'] = userInfo.address_id || key;
                                Request('UserUser_Update', {
                                        data: param
                                    })
                                    .then(e => {
                                        // console.log(e.data)
                                        if (e.data.code == 200) {
                                            uni.showToast({
                                                title: '成功',
                                                duration: 2000
                                            })
                                            that.$store.commit('addressAll', addressAll);
                                            that.$store.commit('userInfo', userInfo);
                                            // this.$set(this.addressAll, key,addressAll[key]);
                                        }
                                    })
                            } else {

                            }

                        }
                    })
                }

            },
            // 添加地址
            add() {
                var addressAll = this.addressAll;
                var length = addressAll.length;
                if (length > 4) {
                    uni.showModal({
                        title: "失败提示",
                        content: "最多只能保存5个地址信息，请删除多余信息后提交"
                    })
                    return;
                }
                var rule = [{
                    name: "phone",
                    errorMsg: "手机号输入不正确",
                    checkType: "phoneno",
                }, {
                    name: "name",
                    errorMsg: "收件人",
                    checkType: "betweenL",
                    checkRule: "2,8"
                }, {
                    name: "address",
                    errorMsg: "地址",
                    checkType: "betweenL",
                    checkRule: "6,20"
                }, {
                    name: "area_id",
                    errorMsg: "请选择地区",
                    checkType: "between",
                    checkRule: "100000,999999"
                }, ];
                var formData = this.form
                var Vali = Validate.check(formData, rule);
                if (!Vali) {
                    uni.showModal({
                        title: "提示",
                        content: Validate.error,
                        showCancel: false
                    })
                } else {
                    Request('UserAddress_Add', {
                            data: formData
                        })
                        .then(e => {
                            // console.log(e.data)
                            if (e.data.code == 200) {
                                uni.showToast({
                                    title: '成功',
                                    duration: 2000
                                })
                                var addressAll=this.addressAll|| [];
                                addressAll = addressAll.concat([e.data.data])
                                this.$store.commit('addressAll', addressAll)
                                this.hasAddress = false;
                                uni.showToast({
                                    title: "提交成功"
                                })
                            } else {
                                uni.showToast({
                                    icon: "none",
                                    title: e.data.message || "失败",
                                    duration: 2000
                                })
                            }
                        })

                }
                // console.log(this.address)
            }
        }
    }
</script>

<style>
    .uni-media-list {
        background: #FFFFFF;
    }

    .uni-card .uni-list {
        box-sizing: border-box;
        /* margin:10upx 0; */
        padding: 0 15upx;
    }
</style>
