<template>
    <view class="uni-page-body">
        <popup-input ref='popupInput' @change="inputChange"></popup-input>
        <view class="">
            <view class="yc-li">
                <view class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;">
                    <view class="" style="width: 60px;">
                        昵称
                    </view>
                    <view class="" style="width: 60px;">
                        UID
                    </view>
                    <view class="" style="width: 60px;">
                        实名状态
                    </view>
                    <view class="" style="width: 60px;">
                        状态
                    </view>
                </view>
            </view>
            <scroll-view @scrolltolower="scrolltolower" scroll-y :style="'height:'+contentHeight+'px' " class="yc-ul">
                <view class="yc-li" v-for="(item,idx) in list" :key="item.id" style="white-space: nowrap;overflow-x: auto;">
                    <view class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;" @tap="tapItem(item,idx)">
                        <view class="" style="width: 60px;white-space: nowrap;overflow: auto;">
                            {{item.nickname}}
                        </view>
                        <view class="" style="width: 60px;white-space: nowrap;overflow: auto;">
                            {{item.user_id}}
                        </view>
                        <view class="" style="width: 60px; text-align: center;">
                            <view class="" :style="item.state==1?'color: #09BB07;':'color: red;'">
                                {{item.state|state}}
                            </view>
                        </view>
                        <view class="" style="width: 60px; border-radius: 4px; text-align: center;">
                            <view class="" :style="item.action=='create'?'color: #09BB07;':'color: red;'">
                                {{item.action|action}}
                            </view>
                        </view>
                    </view>
                </view>
            </scroll-view>
        </view>

    </view>
</template>

<script>
    import area from '@/components/picker/city-data/area.js'
    import city from '@/components/picker/city-data/city.js'
    import province from '@/components/picker/city-data/province.js'
    export default {
        components: {
            // popupInput
        },
        data() {

            var systemInfo = uni.getSystemInfoSync();

            return {

                contentHeight: systemInfo.windowHeight - 80,

                list: [{
                    user_id: '1234323认为确认二',
                    nickname: '小不点来自遥远的东方',
                    name: 'realname',
                    label: '实名审核',
                    action: 'create',
                    state: 0,
                    delete_time: 0
                }, {
                    user_id: '1235',
                    nickname: '小不点',
                    name: 'realname',
                    label: '实名审核',
                    action: 'create',
                    state: 1,
                    delete_time: 1777777700
                }, {
                    user_id: '1236',
                    nickname: '小不点',
                    name: 'realname',
                    label: '实名审核',
                    action: 'update',
                    state: 1,
                    delete_time: 1777777700
                }],

                form: null
            }
        },
        onLoad() {

        },
        filters: {
            action(e) {
                var arr = {
                    'create': '创建',
                    'update': '更新'
                }
                return arr[e] || '未知'
            },
            state(e) {
                // console.log(e)
                var arr = {
                    '0': '未实名',
                    '1': '已实名'
                }
                return arr[e] || '未知'
            }
        },
        computed: {

        },
        watch: {

        },
        methods: {
            inputChange(e) {
                console.log(e)
            },
            scrolltolower() {
                this.getData()
            },
            getData() {
                // 从服务器获取数据
                var lst = this.list;
                var newData = [{
                    id: '1',
                    nickname: '匿名',
                    state: 0,
                    delete_time: 0
                }, {
                    id: '1',
                    nickname: '匿名',
                    state: 1,
                    delete_time: 127000221
                }, {
                    id: '1',
                    nickname: '匿名',
                    state: 0,
                    delete_time: 0
                }, {
                    id: '1',
                    nickname: '匿名',
                    state: 1,
                    delete_time: 127000221
                }, {
                    id: '1',
                    nickname: '匿名',
                    state: 1,
                    delete_time: 127000221
                }, {
                    id: '1',
                    nickname: '匿名',
                    state: 1,
                    delete_time: 127000221
                }, {
                    id: '1',
                    nickname: '匿名',
                    state: 1,
                    delete_time: 127000221
                }, {
                    id: '1',
                    nickname: '匿名',
                    state: 1,
                    delete_time: 127000221
                }, {
                    id: '1',
                    nickname: '匿名',
                    state: 1,
                    delete_time: 127000221
                }]
                var list = lst.concat(newData);

                this.list = list.filter((e, i) => {
                    e.id = 'id' + i;
                    return e;
                })
                // console.log(li)
            },

            stateTabel(key) {
                if (key == 1) {
                    return '撤销删除'
                } else {
                    return '删除账号'
                }
            },
            tapExamine(item, idx) {

            },
            tapItem(item, idx) {

                uni.navigateTo({
                    url: "./detail?query=" + encodeURIComponent(JSON.stringify(item))
                })

            }

        }
    }
</script>

<style>

</style>
