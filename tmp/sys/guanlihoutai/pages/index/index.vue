<template>
    <view class="uni-page-body">
        <view class="yc-ul">
            <view class="yc-li" v-for="(item,idx) in list" :key="item.name">
                <view class="yc-li-item" @tap="tapItem(item)">
                    {{item.label}}
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                list: [{
                    name: 'user',
                    label: '用户管理'
                }, {
                    name: 'pay',
                    label: '支付管理'
                }, {
                    name: 'goods',
                    label: '商品管理'
                }, {
                    name: 'realname',
                    label: '实名审核'
                }]
            }
        },
        onLoad() {

        },
        methods: {
            tapItem(e) {
                console.log(e)
                switch(e.name){
                    default:
                    uni.navigateTo({
                        url:'/pages/'+e.name+'/index'
                    })
                    break;
                }
            }
        }
    }
</script>

<style>

</style>
