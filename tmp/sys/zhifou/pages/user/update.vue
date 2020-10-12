<template>
    <view class="uni-page-body uni-flex" v-if="isShow" style="width: 100%;">
        <view v-if="key=='headimg'" class="uni-list" style="width: 100%;">
            <image-cut @changes="headimgchanges" :imgSrc="value"></image-cut>
        </view>
        <view v-else class="uni-flex" style="flex-direction: column;width: 100%;">
            <view v-if="key=='area'" class="uni-list">
                <view class=" uni-list-cell-navigate  uni-navigate-right" @click="showMulLinkageThreePicker">
                    {{value}}
                </view>
                <mpvue-city-picker :themeColor="themeColor" ref="mpvueCityPicker" :pickerValueDefault="cityPickerValueDefault"
                    @onConfirm="cityConfirm"></mpvue-city-picker>
            </view>
            <view v-else class="uni-list">
                <view class="uni-list-cell uni-flex" style="justify-content: space-between;" hover-class="uni-list-cell-hover">
                    <input class="uni-input" type="text" v-model="value" />
                </view>
            </view>
            <view   class="uni-flex" style="padding: 40upx 0;align-self: flex-end;width: 100%;">

                <button style="width: 100%;" type="primary" @tap="confirm()">确认</button>
            </view>
        </view>

    </view>
</template>

<script>
    import Storage from "../../common/yc_js/Storage.js";
    import Request from "../../request/index.js";
    import mpvueCityPicker from '../../components/mpvue-citypicker/mpvueCityPicker.vue'
    import imageCut from '../../components/template/image/cut.vue';
    import yc_js from "../../common/yc_js/index.js";
    var YCImg = yc_js.Img
    var Base64 = yc_js.Base64;
    export default {
        components: {
            imageCut,
            mpvueCityPicker
        },
        onShow() {
            this.onshow = true;
            this.$store.commit('pages', this.pageName || "userUpdate")
        },
        data() {
            return {
                isShow: false,
                title: "修改页面",
                value: "",
                oldValue: "",
                key: "",
                cityPickerValueDefault: [0, 0, 1],
                themeColor: '#007AFF',
                areaObj: {}, //三联地址内容
            }
        },
        computed: {
            userInfo() {
                return this.$store.getters.userInfo;
            }
        },
        onLoad(e) {
            // console.log(e)
            // 这个编码解码主要是防止图片地址传递的时候容易丢失数据因为图片链接有时候带?号
            if (e.key == 'headimg') {
                e.value = Base64.urlEncoder(e.value);
            }
            this.key = e.key;
            this.value = e.value;
        },
        methods: {
            headimgchanges(e) {
                // console.log(JSON.stringify(e))
                YCImg.canvasToBase64(e)
                    .then(e => {
                        // console.log(JSON.stringify(e))
                        this.value = e;
                        this.confirm();
                        // console.log(e)
                    })
                    .catch(e => {
                        uni.showToast({
                            title: '失败！' + e.message,
                            icon: 'none',
                            duration: 1000
                        });
                    })
            },
            // 三级联动选择
            showMulLinkageThreePicker() {
                this.$refs.mpvueCityPicker.show()
            },
            cityConfirm(e) {
                // console.log(e)
                this.value = e.label;
                this.areaObj = e;

            },

            confirm() {

                if (this.userInfo[this.key] == this.value) {
                    uni.showModal({
                        title: "提示",
                        content: '未做任何修改？',
                        showCancel: false,
                        success(e) {
                            if (e.confirm) {
                                console.log('已确认')
                            }
                        }
                    })

                } else {
                    uni.showModal({
                        title: "提示",
                        content: "确认修改？",
                        success: (e) => {
                            if (e.confirm) {
                                var param = {};
                                var key = this.key;
                                var value = this.value;
                                var newValue = value;
                                switch (key) {
                                    case 'area':
                                        key = 'area_id';
                                        value = this.areaObj.cityCode;
                                        newValue = this.areaObj.label;
                                        break;
                                    default:
                                        break;
                                }
                                param[key] = value;
                                // console.log(param)
                                Request('UserUser_Update', {
                                    data: param
                                }).then(e => {
                                    // console.log(JSON.stringify(e.data))
                                    if (e.data.code == 200) {
                                        uni.showToast({
                                            title: '成功',
                                            duration: 2000
                                        });
                                        // this.oldValue=this.value;
                                        // console.log([key,newValue])
                                        var userInfo = this.$store.getters.userInfo;
                                        // if(userInfo[key]){

                                        if (e.data.data[key] ) {
                                            if (key != 'area_id') {
                                                userInfo[key] = e.data.data[key];
                                            } else {
                                                userInfo['area_id'] = e.data.data['area_id'];
                                                userInfo['area'] = newValue;
                                            }
                                        }
                                        // console.log(JSON.stringify([e.data.data,e.data.data[key],userInfo]))
                                        this.$store.commit('userInfo', userInfo);
                                        // }
                                        uni.navigateBack({
                                            delta: 1
                                        })
                                    } else {
                                        uni.showModal({
                                            title: "提示",
                                            content: e.data.message,
                                            showCancel: false,
                                        })
                                    }
                                })

                            } else {

                            }
                        }
                    })
                }

            }
        },
        mounted() {
            this.$nextTick(() => {
                this.isShow = true;
                // 				if(this.key=='area'){
                // 					this.showMulLinkageThreePicker();	
                // 				}
            })
        }
    }
</script>
<style>

</style>
