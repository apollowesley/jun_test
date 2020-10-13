<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
.demo-rtl .portal-column-left{
padding-left: 10px;
padding-right: 10px;
}
.demo-rtl .portal-column-right{
padding-left:10px;
padding-right: 0;
}
</style>
<script type="text/javascript">

//----------------------------------------------1 start------------------------------------//

option1 = {
	    title : {
	        text: '未来一周气温变化',
	        subtext: '纯属虚构'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['最高气温','最低气温']
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : ['周一','周二','周三','周四','周五','周六','周日']
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLabel : {
	                formatter: '{value} °C'
	            }
	        }
	    ],
	    series : [
	        {
	            name:'最高气温',
	            type:'line',
	            data:[11, 11, 15, 13, 12, 13, 10],
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            },
	            markLine : {
	                data : [
	                    {type : 'average', name: '平均值'}
	                ]
	            }
	        },
	        {
	            name:'最低气温',
	            type:'line',
	            data:[1, -2, 2, 5, 3, 2, 0],
	            markPoint : {
	                data : [
	                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
	                ]
	            },
	            markLine : {
	                data : [
	                    {type : 'average', name : '平均值'}
	                ]
	            }
	        }
	    ]
	};
//----------------------------------------------1 end------------------------------------//

//----------------------------------------------2 start------------------------------------//

option2 = {
	    title : {
	        text: '漏斗图',
	        subtext: '纯属虚构'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c}%"
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    legend: {
	        data : ['展现','点击','访问','咨询','订单']
	    },
	    calculable : true,
	    series : [
	        {
	            name:'漏斗图',
	            type:'funnel',
	            width: '40%',
	            data:[
	                {value:60, name:'访问'},
	                {value:40, name:'咨询'},
	                {value:20, name:'订单'},
	                {value:80, name:'点击'},
	                {value:100, name:'展现'}
	            ]
	        },
	        {
	            name:'金字塔',
	            type:'funnel',
	            x : '50%',
	            sort : 'ascending',
	            itemStyle: {
	                normal: {
	                    // color: 各异,
	                    label: {
	                        position: 'left'
	                    }
	                }
	            },
	            data:[
	                {value:60, name:'访问'},
	                {value:40, name:'咨询'},
	                {value:20, name:'订单'},
	                {value:80, name:'点击'},
	                {value:100, name:'展现'}
	            ]
	        }
	    ]
	};
	                                        
//----------------------------------------------2 end ------------------------------------//

//----------------------------------------------3 start------------------------------------//


var nodes = [];
var links = [];
var constMaxDepth = 4;
var constMaxChildren = 3;
var constMinChildren = 2;
var constMaxRadius = 10;
var constMinRadius = 2;
var mainDom = document.getElementById('chart3');

function rangeRandom(min, max) {
    return Math.random() * (max - min) + min;
}

function createRandomNode(depth) {
    var x = mainDom.clientWidth / 2 + (.5 - Math.random()) * 200;
    var y = (mainDom.clientHeight - 20) * depth / (constMaxDepth + 1) + 20;
    var node = {
        name : 'NODE_' + nodes.length,
        value : rangeRandom(constMinRadius, constMaxRadius),
        // Custom properties
        id : nodes.length,
        depth : depth,
        initial : [x, y],
        fixY : true,
        category : depth === constMaxDepth ? 0 : 1
    }
    nodes.push(node);

    return node;
}

function forceMockThreeData() {
    var depth = 0;

    var rootNode = createRandomNode(0);
    rootNode.name = 'ROOT';
    rootNode.category = 2;

    function mock(parentNode, depth) {
        var nChildren = Math.round(rangeRandom(constMinChildren, constMaxChildren));
        
        for (var i = 0; i < nChildren; i++) {
            var childNode = createRandomNode(depth);
            links.push({
                source : parentNode.id,
                target : childNode.id,
                weight : 1 
            });
            if (depth < constMaxDepth) {
                mock(childNode, depth + 1);
            }
        }
    }

    mock(rootNode, 1);
}

forceMockThreeData();

option3 = {
    title : {
        text: 'Force',
        subtext: 'Force-directed tree',
        x:'right',
        y:'bottom'
    },
    tooltip : {
        trigger: 'item',
        formatter: '{a} : {b}'
    },
    toolbox: {
        show : true,
        feature : {
            restore : {show: true},
            magicType: {show: true, type: ['force', 'chord']},
            saveAsImage : {show: true}
        }
    },
    legend: {
        x: 'left',
        data:['叶子节点','非叶子节点', '根节点']
    },
    series : [
        {
            type:'force',
            name : "Force tree",
            ribbonType: false,
            categories : [
                {
                    name: '叶子节点',
                    itemStyle: {
                        normal: {
                            color : '#ff7f50'
                        }
                    }
                },
                {
                    name: '非叶子节点',
                    itemStyle: {
                        normal: {
                            color : '#6f57bc'
                        }
                    }
                },
                {
                    name: '根节点',
                    itemStyle: {
                        normal: {
                            color : '#af0000'
                        }
                    }
                }
            ],
            itemStyle: {
                normal: {
                    label: {
                        show: false
                    },
                    nodeStyle : {
                        brushType : 'both',
                        strokeColor : 'rgba(255,215,0,0.6)',
                        lineWidth : 1
                    }
                }
            },
            minRadius : constMinRadius,
            maxRadius : constMaxRadius,
            nodes : nodes,
            links : links
        }
    ]
};

//----------------------------------------------3 end------------------------------------//
                     
//----------------------------------------------4 start------------------------------------//


option4 = {
    title : {
        text: '预算 vs 开销（Budget vs spending）',
        subtext: '纯属虚构'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        orient : 'vertical',
        x : 'right',
        y : 'bottom',
        data:['预算分配（Allocated Budget）','实际开销（Actual Spending）']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    polar : [
       {
           indicator : [
               { text: '销售（sales）', max: 6000},
               { text: '管理（Administration）', max: 16000},
               { text: '信息技术（Information Techology）', max: 30000},
               { text: '客服（Customer Support）', max: 38000},
               { text: '研发（Development）', max: 52000},
               { text: '市场（Marketing）', max: 25000}
            ]
        }
    ],
    calculable : true,
    series : [
        {
            name: '预算 vs 开销（Budget vs spending）',
            type: 'radar',
            data : [
                {
                    value : [4300, 10000, 28000, 35000, 50000, 19000],
                    name : '预算分配（Allocated Budget）'
                },
                 {
                    value : [5000, 14000, 28000, 31000, 42000, 21000],
                    name : '实际开销（Actual Spending）'
                }
            ]
        }
    ]
};
                    
//----------------------------------------------4 end------------------------------------//

	                    
	center = function(){
		debugger;
		center_this=this;
		//定义一个公用调用的函数,添加tabs
		center_this.addTabs=function(tabObject){
			debugger;

			var centerTabsOptions=$("#center_tabs");
			if (centerTabsOptions.tabs('exists', tabObject.title)) {
				centerTabsOptions.tabs('select', tabObject.title);
			} else {
				centerTabsOptions.tabs('add', tabObject);
			}
		}
		
	}
	var centerObject=new center();
	$(function(){
		$('#index').portal({
            border: false,
            fit: true,
        });
        var myChart = echarts.init(document.getElementById('chart1')); 
        myChart.setOption(option1); 

        var myChart2 = echarts.init(document.getElementById('chart2')); 
        myChart2.setOption(option2); 
        
        var myChart3 = echarts.init(document.getElementById('chart3')); 
        myChart3.setOption(option3); 
        
        var myChart4 = echarts.init(document.getElementById('chart4')); 
        myChart4.setOption(option4);
        
		var windowHeight=$(window).height();
		var bodyHeight=windowHeight-150;
		var divPort=(bodyHeight-40)/2;
		var portalPanels=$('#index').portal('getPanels');
		$.each(portalPanels,function(i,p){
			p.panel({
				height:divPort,
			})	  
		});	
		
		
		//$('#index').portal('resize');
	})
</script>
<div class="easyui-tabs" fit="true" id="center_tabs" border=false style="position:relative">
 <div id="index" title="首页" data-options="iconCls : 'icon-home'" style="padding:5px">
 
	    <div style="width:50%">
		    <div id='div1-1'  closable=true collapsed=false >
		    	<div id='chart1' style="height:220px">
		    	</div>
		    </div>
		    <div id='div1-2'  closable=true collapsed=false >
		    	<div id='chart2' style="height:220px">
		    	</div>
		    </div>
	    </div>
	    <div style="width:50%">
	      <div id='div2-1'    closable=true collapsed=false >
	      	    	<div id='chart3' style="height:220px">
		    	</div>
		   </div>
		   <div id='div2-2'   closable=true collapsed=false >
		   	    	<div id='chart4' style="height:220px">
		    	</div>		    
		    </div>
		</div>

 </div>
</div>

