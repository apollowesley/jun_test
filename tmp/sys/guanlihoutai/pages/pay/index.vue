<template>
    <view class="uni-page-body">
        <view class="yc-ul">
            <view class="qiun-title-bar" >

                <view class=" uni-flex" style="justify-content:space-between">
                    <view class="uni-flex-item">
                        
                        <view class="qiun-title-dot-light" v-for="(item,index) in countArr" :key="index" :style="item.color?'color:'+item.color:''">
                            {{item.name}}:{{item.count}}元
                        </view>
                        <view class="qiun-title-dot-light"  >
                                结余:{{count}}元
                        </view>
                    </view>
                    <view class="uni-flex uni-row" style="width: 350upx;justify-content:space-around">
                        <view class="uni-flex uni-column" style="justify-content: center;">
                            <view class="" @tap="btnMonth(0)" style="border: 1px  solid #DDDDDD;border-radius: 5px;padding:0 3px;background: #FFFFFF;">
                                上月

                            </view>
                        </view>
                        <view class="uni-flex uni-row" style="padding: 0 2px;align-items: center;box-sizing: border-box;">
                            <picker fields="month" mode="date" :value="input.date" :start="startDate" :end="endDate"
                                @change="bindDateChange">
                                <input value="2019-01" style="border: 1px  solid #DDDDDD;background: #FFFFFF;width: 70px;padding: 0 2px;border-radius: 5px;"
                                    type="text" v-model="input.date" />
                            </picker>
                        </view>
                        <view class="uni-flex uni-column" style="justify-content: center;">
                            <view class="" @tap="btnMonth(1)" style="border: 1px  solid #DDDDDD;border-radius: 5px;padding:0 3px;background: #FFFFFF;">
                                下月

                            </view>
                        </view>
                    </view>
                </view>

            </view>
            
        </view>
        <!-- -->
        <view class="qiun-charts">
            <!--#ifdef MP-ALIPAY -->
            <canvas canvas-id="canvasLineA" id="canvasLineA" class="charts" :style="{'width':cWidth*pixelRatio+'px','height':cHeight*pixelRatio+'px', 'transform': 'scale('+(1/pixelRatio)+')','margin-left':-cWidth*(pixelRatio-1)/2+'px','margin-top':-cHeight*(pixelRatio-1)/2+'px'}"
                disable-scroll=true @touchstart="touchLineA" @touchmove="moveLineA" @touchend="touchEndLineA"></canvas>
            <!-- 使用图表拖拽功能时，建议给canvas增加disable-scroll=true属性，在拖拽时禁止屏幕滚动 -->
            <!--#endif-->
            <!--#ifndef MP-ALIPAY -->
            <canvas canvas-id="canvasLineA" id="canvasLineA" class="charts" disable-scroll=true @touchstart="touchLineA"
                @touchmove="moveLineA" @touchend="touchEndLineA"></canvas>
            <!-- 使用图表拖拽功能时，建议给canvas增加disable-scroll=true属性，在拖拽时禁止屏幕滚动 -->
            <!--#endif-->
        </view>
        <view class="yc-li" v-for="(item,idx) in list" :key="item.name">
            <view class="yc-li-item" @tap="tapItem(item)">
                <view class="">
                    {{item.label}}
                </view>
                <view class="iconfont">
                    &#xe95a;
                </view>
            </view>

        </view>
    </view>
    </view>
</template>

<script>
    import {
        Time
    } from '@/common/yc_js'
    import uCharts from '@/components/u-charts/u-charts.js';
    var _self;
    var canvaColumn = null;
    var canvaLineA = null;

    export default {

        data() {
            const currentDate = this.getDate({
                format: true
            });
            return {

                count: 0, //总量统计
                countArr: [],
                cWidth: 0,
                cHeight: 0,

                pixelRatio: 1,
                serverData: '',

                list: [{
                    name: 'list',
                    label: '支付流水'
                }],
                input: {
                    date: this.getDate('end')
                }
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
        filters: {
            // day(timestamp) {
            //     var date = new Date(timestamp * 1000); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
            //     return date.getDate() + ' ';
            // }
        },
        watch: {
            'input.date': function(e) {
                console.log(e)
            },
        },
        onLoad() {
            _self = this;
            //#ifdef MP-ALIPAY
            uni.getSystemInfo({
                success: function(res) {
                    if (res.pixelRatio > 1) {
                        //正常这里给2就行，如果pixelRatio=3性能会降低一点
                        //_self.pixelRatio =res.pixelRatio;
                        _self.pixelRatio = 2;
                    }
                }
            });
            //#endif
            this.cWidth = uni.upx2px(750);
            this.cHeight = uni.upx2px(500);
            var item = {
                "num": 0,
                "date": 1577721600,
                "type": "user"
            };
            var data = [];
            for (var i = 0; i < 31; i++) {
                var item = Object.assign({}, item);
                item.date = item.date + 86400;
                item.num = parseInt((item.date + '').substring((item.date + '').length - 6));
                data.push(item)
            }

            var date = [];
            var num = [];
            var num2 = [];
            data.filter(e => {
                num.push(e.num);
                num2.push(parseInt(e.num / 10))
                if (e.date && typeof e.date == 'number') {
                    var date2 = new Date(e.date * 1000); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
                    date.push(date2.getDate() + '');
                } else {
                    date.push('01')
                }
            })
            // var date = new Date(timestamp * 1000); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
            // var Y = date.getFullYear() + '-';
            // var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
            // var D = date.getDate() + ' ';

            var canvasLineA = {
                "categories": date,
                "series": [{
                    "name": '进账',
                    "data": num,
                    "color": "#1890ff"
                }, {
                    "name": '出账',
                    "color": "#ff5599",
                    "data": num2
                }]
            }
            // this.showColumn("canvasColumn", Column);
            setTimeout(e => {
                this.showLineA("canvasLineA", canvasLineA);
            }, 1000)

        },

        methods: {
            touchLineA(e) {
                // console.log(e)
                canvaLineA.scrollStart(e);
            },
            moveLineA(e) {
                // console.log(e)
                canvaLineA.scroll(e);
            },
            touchEndLineA(e) {
                // console.log(e)
                canvaLineA.scrollEnd(e);
                //下面是toolTip事件，如果滚动后不需要显示，可不填写
                canvaLineA.showToolTip(e, {
                    format: function(item, category) {
                        return category + ' ' + item.name + ':' + item.data
                    }
                });
            },

            // changeData() {
            //     canvaColumn.updateData({
            //         series: _self.serverData.ColumnB.series,
            //         categories: _self.serverData.ColumnB.categories
            //     });
            // },
            tapItem(e) {
                console.log(e)
                switch (e.name) {
                    case 'list':
                        uni.navigateTo({
                            url: './' + e.name
                        })
                        break;
                    case 'realname':
                        break;
                    default:
                        break;
                }
            },
            showLineA(canvasId, chartData) {

                // console.log(chartData.series)
                var countArr = [];
                for (var i = 0; i < chartData.series.length; i++) {
                    var count = 0;
                    (chartData.series[i].data).filter(e => {
                        count = count + e;
                    })
                    countArr.push({
                        count,
                        name: chartData.series[i].name,
                        color: chartData.series[i].color || ''
                    })
                }
                this.countArr = countArr;
                this.count =countArr[0].count-countArr[1].count
                // this.count = count;
                canvaLineA = new uCharts({
                    $this: _self,
                    canvasId: canvasId,
                    type: 'line',
                    fontSize: 11,
                    legend: true,
                    dataLabel: false,
                    dataPointShape: false,
                    background: '#FFFFFF',
                    pixelRatio: _self.pixelRatio,
                    categories: chartData.categories,
                    series: chartData.series,
                    animation: false,
                    enableScroll: true, //开启图表拖拽功能
                    xAxis: {
                        disableGrid: false,
                        type: 'grid',
                        gridType: 'dash',
                        itemCount: 10, //可不填写，配合enableScroll图表拖拽功能使用，x轴单屏显示数据的数量，默认为5个
                        scrollShow: true, //新增是否显示滚动条，默认false
                        scrollAlign: 'left',
                        //scrollBackgroundColor:'#F7F7FF',//可不填写，配合enableScroll图表拖拽功能使用，X轴滚动条背景颜色,默认为 #EFEBEF
                        //scrollColor:'#DEE7F7',//可不填写，配合enableScroll图表拖拽功能使用，X轴滚动条颜色,默认为 #A6A6A6
                    },
                    yAxis: {
                        //disabled:true
                        gridType: 'dash',
                        splitNumber: 8,
                        min: 10,
                        max: 180,
                        format: (val) => {
                            return val.toFixed(0) + ' '
                        } //如不写此方法，Y轴刻度默认保留两位小数
                    },
                    width: _self.cWidth * _self.pixelRatio,
                    height: _self.cHeight * _self.pixelRatio,
                    dataLabel: true,
                    dataPointShape: true,
                    extra: {
                        lineStyle: 'straight'
                    },
                });
            },
            btnMonth(e) {
                var date = '';
                if (e) {
                    date = Time.nextMonth(this.input.date)
                } else {
                    date = Time.preMonth(this.input.date)
                }
                var input = this.input;
                input.date = date.substring(0, 7);
                this.input = input;
            },
            bindDateChange: function(e) {
                // console.log(e)
                var input = this.input;
                input.date = e.target.value.substring(0, 7);
                this.input = input;
            },

            getDate(type) {
                const date = new Date();
                let year = date.getFullYear();
                let month = date.getMonth() + 1;
                if (type === 'start') {
                    year = year - 60;
                } else if (type === 'end') {
                    year = year + 0;
                }
                month = month > 9 ? month : '0' + month;;
                return `${year}-${month}`;
            }
        }

    }
</script>

<style>
    /* 通用样式 */
    .qiun-charts {

        width: 750upx;
        height: 500upx;
        background-color: #FFFFFF;
    }

    .charts {
        width: 750upx;
        height: 500upx;
        background-color: #FFFFFF;
    }

    .qiun-title-bar {
        width: 96%;
        padding: 10upx 2%;
        flex-wrap: nowrap;
    }

    .qiun-title-dot-light {
        border-left: 10upx solid #0ea391;
        padding-left: 10upx;
        font-size: 32upx;
        color: #000000
    }
</style>
