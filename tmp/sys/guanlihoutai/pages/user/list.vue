<template>
    <view class="uni-page-body">
        <view class="yc-li">
            <view class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;"  @tap="selectTap">
                <view class="yc-li-label">
                    筛选条件
                </view>
                <scroll-view scroll-x style="width: 70%; white-space: nowrap;">
                    <view class="uni-flex uni-row">
                        <view style="padding: 2px;" v-for="(stm,idx) in selectLabel" :key="stm">
                            <text class="" style="padding: 2px;background: #EEEEEE;">
                                {{stm}}:{{form[idx]}}
                            </text>
                        </view>
                    </view>

                </scroll-view>
                <view class="iconfont">
                    &#xe95a;
                </view>
            </view>
        </view>
        <!-- :style="selectShow?'display:inline;':'display:none;'"  -->
        <view  class="uni-flex uni-column"  v-if="selectShow" style="z-index: 1;position: absolute; background:rgba(33,33,33,0.5);bottom: 0; top: 0;left: 0; right: 0;justify-content: center;align-items: center;">
            <view class="" style="height: 300px;width: 300px;">
                <checkbox-group @change="checkboxChange">
                    <view class="yc-li" v-for="(item,idx) in premise" :key="item.name">
                        <view class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;">

                            <view class="yc-li-label">
                                <checkbox :value="item.name" :checked="input[item.name] || null" />
                                {{labels[item.name]}}
                            </view>
                            <view class="" style="position: relative;">
                                <input style="background: #DDDDDD;padding:0 5px;" type="text" :placeholder="labels[item.name]"
                                    v-model="input[item.name]" />
                            </view>
                        </view>
                    </view>
                    <view class="yc-li">
                        <view class="yc-li-item">
                            <view class="yc-li-label">
                                <checkbox value="area" />
                                {{labels.area}}
                            </view>
                            <view class="" style="position: relative;">

                                <input style="background: #DDDDDD;padding:0 5px;" type="text" v-model="area"
                                    placeholder="请输入省份-如:北京" />
                                <scroll-view v-if="areaArrShow " scroll-y style="position: absolute;z-index: 1;max-height: 240px;"
                                    class="yc-ul">
                                    <view style="" v-for="(itm,i) in areaArr" :key="itm.label" class="yc-li">
                                        <view class="yc-li-item" style="background: #eee;" @tap="checked('area',itm)">
                                            {{itm.label}}
                                        </view>
                                    </view>
                                </scroll-view>

                            </view>
                        </view>
                    </view>
                    <view class="yc-li">
                        <view class="yc-li-item">
                            <view class="yc-li-label">
                                <checkbox value="time" />
                                {{labels.time}}
                            </view>
                            <view class="">
                                <picker mode="date" :value="input.time" :start="startDate" :end="endDate" @change="bindDateChange">
                                    <input style="background: #DDDDDD;padding:0 5px;" type="text" v-model="input.time" />
                                </picker>
                            </view>
                        </view>
                    </view>
                    <view class="yc-li">
                        <view class="yc-li-item">
                            <view class="yc-li-label">
                                <checkbox value="state" />
                                {{labels.state}}
                            </view>
                        </view>
                    </view>
                    <view class="yc-li">
                        <view class="yc-li-item">
                            <view class="yc-li-label">
                                <checkbox value="del" />
                                {{labels.del}}
                            </view>
                        </view>
                    </view>
                </checkbox-group>
                <view class="yc-li  uni-flex" style="flex-direction: row;justify-content: space-between;">
                    <button type="default" size="mini" style="flex: 1;" @tap="commit(0)">取消</button>
                    <button type="default" size="mini" style="flex: 1;" @tap="commit(1)">确认</button>
                </view>


            </view>
        </view>
        <view class="" v-else>
            <view class="yc-li">
                <view class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;">
                    <view class="" style="width: 60px;">
                        昵称
                    </view>
                    <view class="" style="width: 60px;">
                        ID
                    </view>
                    <view class="" style="width: 60px;">
                       实名状态
                    </view>
                    <view class="" style="width: 60px;">
                        账号状态
                    </view>
                    <view class="" style="width: 60px;">
                        删除状态
                    </view>
                </view>
            </view>
            <scroll-view @scrolltolower="scrolltolower" scroll-y :style="'height:'+contentHeight+'px' " class="yc-ul">
                <view class="yc-li" v-for="(item,index) in list" :key="item.id" style="white-space: nowrap;overflow-x: auto;">
                    <view class="yc-li-item" style="justify-content: space-between;font-size: 0.7em;" @tap="tapItem(item)">
                        <view class="" style="width: 60px;white-space: nowrap;overflow: auto;">
                            {{item.nickname}}
                        </view>
                        <view class="" style="width: 60px;white-space: nowrap;overflow: auto;">
                            {{item.id}}
                        </view>
                        <view class="" style="width: 60px; border-radius: 4px; border: 1px solid #00D3E7;text-align: center;">
                            <view class="" :style="item.state==1?'color: #09BB07;':'color: red;'">
                                {{item.state|state}}
                            </view>
                        </view>
                        <view class="" style="width: 60px; border-radius: 4px; border: 1px solid #00D3E7;text-align: center;">
                            <view class="" v-if="item.state" style="color: #09BB07;">
                                正常
                            </view>
                            <view class="" v-else style="color: red;">
                                已禁用
                            </view>
                        </view>
                        <view class="" style="width: 60px; border-radius: 4px; border: 1px solid #00D3E7;text-align: center;">
                            <view class="" v-if="!item.delete_time" style="color: #09BB07;">
                                正常
                            </view>
                            <view class="" v-else style="color: red;">
                                已删除
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
        data() {
            const currentDate = this.getDate({
                format: true
            });
           var systemInfo= uni.getSystemInfoSync();
            // console.log(systemInfo.windowHeight)
            return {
                labels: {
                    'ID': 'ID',
                    'time': '注册时间',
                    'area': '注册地区',
                    'del': '已删账号',
                    'state': '已禁账号',
                    'username': '用户名'
                },
                contentHeight:systemInfo.windowHeight-80,
                selectShow: false,
                selectLabel: {},
                area: '',
                // 
                areaArr: null,
                areaArrShow: null,
                lastTimeItemShow: 0,
                index: 0,
                date: currentDate,
                time: '12:01',
                premise: [{
                    name: 'ID',
                    label: '用户ID'
                }, {
                    name: 'username',
                    label: '用户名'
                }],

                list: [{
                    id: '1234323认为确认二',
                    nickname: '小不点来自遥远的东方',
                    name: 'realname',
                    label: '实名审核',
                    state: 0,
                    delete_time: 0
                }, {
                    id: '1235',
                    nickname: '小不点',
                    name: 'realname',
                    label: '实名审核',
                    state: 1,
                    delete_time: 1777777700
                }, {
                    id: '1236',
                    nickname: '小不点',
                    name: 'realname',
                    label: '实名审核',
                    state: 1,
                    delete_time: 1777777700
                }],
                input: {
                    time: '2019-12-31',
                    del: 1,
                    state: 1,
                    area: '',
                    username: '',
                    ID: ''
                },
                checkData: [],
                form: null
            }
        },
        onLoad() {

        },
        filters:{
          state(e){
              // console.log(e)
              var arr={
                  '0':'未实名',
                  '1':'已实名'
              }
              return arr[e]||'未知'
          }  
        },
        computed: {
            startDate() {
                return this.getDate('start');
            },
            endDate() {
                return this.getDate('end');
            }
        },
        watch: {
            'input.time': function(e) {
                console.log(e)
            },
            area: function(val) {
                this.areaArr = province.filter(e => {
                    var time = Date.now();
                    // console.log(JSON.stringify(e.label).indexOf(val) != -1)
                    if (val != '' && JSON.stringify(e.label).indexOf(val) != -1) {
                        if (time - this.lastTimeItemShow > 100) {
                            this.areaArrShow = true;
                        } else {
                            this.areaArrShow = null;
                        }
                        this.lastTimeItemShow = time;
                        return e.label
                    }
                    // console.log(e)
                })
                // console.log(val)
            }
        },
        methods: {

            checkboxChange(e) {
                // 多选框触发
                this.checkData = e.detail.value;
            },
            checked(name, item) {
                // 单选城市
                switch (name) {
                    case 'area':
                        this.area = item.label;
                        this.areaArrShow = null;
                        var time = Date.now();
                        this.lastTimeItemShow = time;
                        var input = this.input;
                        input[name] = item.value;
                        this.input = input;
                        break;
                    default:
                        break;
                }

            },
            selectTap(e) {
                // 查找显示触发
                this.selectShow = !this.selectShow
                // console.log(e)
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
            commit(e) {
                // 根据条件确认查找
                if (e) {
                    var form = {};
                    var checkData = this.checkData;
                    var input = this.input;

                    var selectLabel = {};
                    var labels = this.labels;
                    for (let i = 0; i < checkData.length; i++) {
                        if (input[checkData[i]]) {
                            form[checkData[i]] = input[checkData[i]];
                            selectLabel[checkData[i]] = labels[checkData[i]] || '';
                        }

                    }
                    this.selectLabel = selectLabel;
                    this.form = form;
                    this.getData()

                }
                this.selectShow = !this.selectShow
            },
            stateTabel(key) {
                if (key == 1) {
                    return '撤销删除'
                } else {
                    return '删除账号'
                }
            },
            tapItem(item) {
                // item.state=
                var state = '解除禁用';
                if (item.state == 1) {
                    state = '禁用账号'
                }
                var delState = '删除账号';
                if (item.delete_time) {
                    delState = '撤销删除'
                }
                uni.showActionSheet({
                    itemList: ['查看详情', delState, state],
                    success: (s) => {
                        // console.log(s)
                        switch (s.tapIndex) {
                            case 0:
                                uni.navigateTo({
                                    url: "./detail?query=" + encodeURIComponent(JSON.stringify(item))
                                })
                                break;
                            case 1:
                                uni.showModal({
                                    title: '提示',
                                    content: '您确定要【' + delState + '】？',
                                    success: (e) => {
                                        if (e.confirm) {
                                            if (item.delete_time) {
                                                item.delete_time = 0;
                                            } else {
                                                item.delete_time = parseInt(Date.now() / 1000)
                                            }
                                        }

                                    }
                                })

                                break;
                            case 2:
                                uni.showModal({
                                    title: '提示',
                                    content: '您确定要【' + state + '】？',
                                    success: (e) => {
                                        if (e.confirm) {

                                            if (item.state) {
                                                item.state = 0;
                                            } else {
                                                item.state = 1
                                            }

                                        }

                                    }
                                })

                                break;
                            default:
                                break;
                        }
                    }
                })
                return;
                // console.log(e)


            },

            bindDateChange: function(e) {
                var input = this.input;
                input.time = e.target.value;
                this.input = input;
            },

            getDate(type) {
                const date = new Date();
                let year = date.getFullYear();
                let month = date.getMonth() + 1;
                let day = date.getDate();

                if (type === 'start') {
                    year = year - 60;
                } else if (type === 'end') {
                    year = year + 2;
                }
                month = month > 9 ? month : '0' + month;;
                day = day > 9 ? day : '0' + day;
                return `${year}-${month}-${day}`;
            }

        }
    }
</script>

<style>

</style>
