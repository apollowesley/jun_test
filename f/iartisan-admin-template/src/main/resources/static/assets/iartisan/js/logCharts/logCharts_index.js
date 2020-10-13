layui.config({
    base: "/assets/iartisan/plugins/lib/"
}).use(['router', "util"], function () {
    var router = layui.router, util = layui.util;

    var urls = {
        getCharts: "/logCharts/getCharts"
    };

    var chartDiv = document.getElementById('charts');

    //自适应宽高
    var chartContainer = function () {
        chartDiv.style.width = window.innerWidth-200 + 'px';
        chartDiv.style.height = window.innerHeight-200 + 'px';
    };
    chartContainer();
    var charts = echarts.init(chartDiv);

    function loadChart() {
        //加载饼图
        router.post({
            url: urls.getCharts, data: {}, success: function (res) {
                var data = res.data;
                var chartTitle = [];
                for (var i = 0; i < data.length; i++) {
                    chartTitle.push(data[i].name);
                }

                var option = {
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} ：{d}%"
                    },
                    legend: {
                        orient: 'horizontal',
                        bottom: 'bottom',
                        data: chartTitle
                    },
                    series: [
                        {
                            name: '方法占比',
                            type: 'pie',
                            data: data,
                            radius : '55%',
                            center: ['40%', '50%'],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                charts.setOption(option);
            }
        });
    }

    loadChart();
    window.onresize = function () {
        chartContainer();
        charts.resize();
    };
});