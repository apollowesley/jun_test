<template>
    <div  style="display: flex;flex-direction:column;padding: 20px;justify-content: space-between;">
        <div style="display: flex;justify-content: space-between;width: 100%;height: 100px;">
            <el-card  v-for="item in tagList" :key="item.text">
                <div class="el-card-tag">
                    <div>
                        <div class="tag-title">
                            {{item.num}}
                        </div>
                        <div class="tag-text">
                           {{item.text}}
                        </div>
                    </div>
                    <div>
                       <i class="iconfont" :class="item.icon"></i>
                    </div>
                </div>
            </el-card>
        </div>
        
        <el-card style="width: 100%;flex:1;min-height: 500px;margin-top: 40px;">
        <!-- <v-echarts style="min-height: 500px;" :option="option"></v-echarts> -->
        <div id="myChart" style="width: 100%;height:100%;min-height: 500px;min-width: 300px;">
        </div>
        </el-card>
        <div style="width: 100%;flex:1;margin-top: 40px;">
            <div v-for="item in copyright" :key="item">
                {{item}}
            </div>
        </div>
    </div>
</template>
<style>
    .el-card-tag{
        display: flex;
        flex-direction:row;
    }
    .el-card-tag .iconfont{
        color:#42B983;
        margin-left: 15px;
        font-size: 40px;
    }
    .el-card-tag .tag-title{
        font-size: 24px;
    }
    .el-card-tag .tag-text{
        font-size:10px;
    }
</style>
<script>
    export default{

        data(){
            return{
                copyright:{
                    "company":"营口大唐圣殿文化发展有限公司",
                    "qq":"QQ:2782268022",
                    },
                tagList:[
                   {
                       num:256,
                       text:'今日新增用户',
                       icon:'icon-yonghushuju'
                   },{
                       num:56,
                       text:'今日新增评论',
                       icon:'icon-pinglun'
                   },{
                       num:1256,
                       text:'今日访问量',
                       icon:'icon-reyonghushujufenxi'
                   },{
                       num:9256,
                       text:'本周访问量',
                       icon:'icon-fangwenliang'
                   },
                ],
                option:{
                        title: {
                            text: '最近一周用户访问状态',
                            subtext: ''
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data:['最高','最低']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                dataZoom: {
                                    yAxisIndex: 'none'
                                },
                                dataView: {readOnly: false},
                                magicType: {type: ['line', 'bar']},
                                restore: {},
                                saveAsImage: {}
                            }
                        },
                        xAxis:  {
                            type: 'category',
                            boundaryGap: false,
                            data: ['周一','周二','周三','周四','周五','周六','周日']
                        },
                        yAxis: {
                            type: 'value',
                            axisLabel: {
                                formatter: '{value} 人次'
                            }
                        },
                        series: [
                            {
                                name:'最高',
                                type:'line',
                                data:[11, 11, 150, 13, 12, 13, 10],
                                markPoint: {
                                    data: [
                                        {type: 'max', name: '最大值'},
                                        {type: 'min', name: '最小值'}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'}
                                    ]
                                }
                            },
                            {
                                name:'最低',
                                type:'line',
                                data:[1, -2, 2, 5, 3, 2, 0],
                                markPoint: {
                                    data: [
                                        {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
                                    ]
                                },
                                markLine: {
                                    data: [
                                        {type: 'average', name: '平均值'},
                                        [{
                                            symbol: 'none',
                                            x: '90%',
                                            yAxis: 'max'
                                        }, {
                                            symbol: 'circle',
                                            label: {
                                                normal: {
                                                    position: 'start',
                                                    formatter: '最大值'
                                                }
                                            },
                                            type: 'max',
                                            name: '最高点'
                                        }]
                                    ]
                                }
                            }
                        ]
                    }
            }
        },mounted(){
            // console.log(this.option)
            this.setOption()
        },methods:{
                  setOption(){
                      // console.log(this.$echarts)
                      var myChart = this.$echarts.init(document.getElementById('myChart'));
                      myChart.setOption(this.option)
                  }  
       }
    }
</script>

<style>
</style>
