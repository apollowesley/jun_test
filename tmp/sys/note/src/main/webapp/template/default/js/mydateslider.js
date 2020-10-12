/*函数原型*/
function mydateslider(time1,time2){this.time1=time1;this.time2=time2;this.content="";this.startDate=time1;this.endDate=time2;}
/*原型方法：显示*/
mydateslider.prototype.show=function(){
        this.calc(this.time1,this.time2);
        $$("J-date-slider").innerHTML=this.content;
        this.bind();
//$$("J-date-slider").innerHTML=        '<div class="ui-date-slider" data-widget-cid="widget-3"> <div class="ui-slider-line ui-slider ui-slider-horizontal" seed="f-consume-standard-shijianzhou" data-widget-cid="widget-4"><div class="ui-slider-range" style="left: 12.19%; width: 87.8%;"></div><a class="ui-slider-handle ui-state-default" style="left: 12.19%;" href="#" seed="f-consume-standard-youbiao1"></a><a class="ui-slider-handle ui-state-default" style="left: 100%;" href="#" seed="f-consume-standard-youbiao2"></a></div> <div class="ui-date-slider-scales" style="margin-left: -40.32px;"> <div class="ui-date-slider-scale" style="visibility: hidden;"><span class="ui-date-slider-month">3</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">4</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">5</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">6</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">7</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">8</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">9</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">10</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">11</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">12</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">1</span><span class="ui-date-slider-line ui-date-slider-line-long"></span><span class="ui-date-slider-year">2014</span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">2</span><span class="ui-date-slider-line"></span></div> <div class="ui-date-slider-scale"><span class="ui-date-slider-month">3</span><span class="ui-date-slider-line"></span></div> </div> <span class="ui-slider-handle-date" id="J-slider-handle-date-0" style="left: 82.63px; top: 12px; position: absolute;">05.10</span><span class="ui-slider-handle-date" id="J-slider-handle-date-1" style="left: 609.5px; top: 12px; position: absolute;">03.25</span></div>'
/*此方法是我写，但是这个页面布局设计不是我做的，源来自于支付宝，我只是仿了一个*/
}
/*原型方法：计算（时间1，时间2）*/
mydateslider.prototype.calc=function(time1,time2){
var date1=new Date(time1);//开始时间，用户输入，Date类型
var date2=new Date(time2);//结束时间，用户输入，Date类型
var enddate=new Date(new Date().format("yyyy-MM-dd"));//游标最大结束时间，Date类型
var calcstartdate=enddate.getTime()*1-60*60*24*365*1000;//31536000s=1y，从1970年1月1日8点开始到去年的今天的毫秒数
var startdate=new Date(calcstartdate);//游标最小开始时间，Date类型
this.startDate=startdate;
this.enddate=enddate;

/*游标只能显示1年，超出范围的不再显示，如果数据不合法，同样只显示1年*/
if(date1.getTime()*1<startdate.getTime()*1||date1=="Invalid Date") {date1=startdate;}
if(date1.getTime()*1>enddate.getTime()*1||date1=="Invalid Date") {date1=enddate;}
if(date2.getTime()*1>enddate.getTime()*1||date2=="Invalid Date") {date2=enddate;}
if(date2.getTime()*1<startdate.getTime()*1||date2=="Invalid Date") {date2=startdate;}
/*游标只能显示1年，超出范围的不再显示*/

var slider1_left_position_percent=(date1.getTime()-startdate.getTime())/(1000*60*60*24*365);//滑标1距离左边的百分比距离，同时也是滑标和线条的开始位置
var slider2_left_position_percent=(date2.getTime()-startdate.getTime())/(1000*60*60*24*365);//滑标2距离左边的百分比距离
var slider_width_percent=slider2_left_position_percent-slider1_left_position_percent;//两个滑标之间线条的宽度


this.content+='';
this.content+='<div class="ui-date-slider" data-widget-cid="widget-3">';
this.content+='<div class="ui-slider-line ui-slider ui-slider-horizontal" seed="f-consume-standard-shijianzhou" data-widget-cid="widget-4" id="date-slider-line">';
this.content+='<div id="ui-slider-range" class="ui-slider-range" style="left:'+ slider1_left_position_percent*100+'%; width: '+slider_width_percent*100+'%;"></div>';
this.content+='<a id="ui-slider-handle1" class="ui-slider-handle ui-state-default" style="left: '+ slider1_left_position_percent*100+'%;"  seed="f-consume-standard-youbiao1"></a>';
this.content+='<a id="ui-slider-handle2" class="ui-slider-handle ui-state-default" style="left: '+slider2_left_position_percent*100+'%;"  seed="f-consume-standard-youbiao2"></a>';
this.content+='</div>';
this.content+='<div class="ui-date-slider-scales" style="margin-left: -40.32px;">';

this.content+='<div class="ui-date-slider-scale" style="visibility: hidden;"><span class="ui-date-slider-month">'+enddate.getMonth()+1+'</span><span class="ui-date-slider-line"></span></div>';

var temp='<span class="ui-date-slider-line"></span>';
var tempyear='<span class="ui-date-slider-line ui-date-slider-line-long"></span><span class="ui-date-slider-year">'+enddate.getFullYear()+'</span>';
for(var i=enddate.getMonth()+2;i<=12;i++)
{
        if(i==1){
                this.content+='<div class="ui-date-slider-scale"><span class="ui-date-slider-month">'+i+'</span>'+tempyear+'</div>';
}else
        this.content+='<div class="ui-date-slider-scale"><span class="ui-date-slider-month">'+i+'</span>'+temp+'</div>';
}
for(var i=1;i<=enddate.getMonth()+1;i++)
{
        if(i==1){
                this.content+='<div class="ui-date-slider-scale"><span class="ui-date-slider-month">'+i+'</span>'+tempyear+'</div>';
}else
        this.content+='<div class="ui-date-slider-scale"><span class="ui-date-slider-month">'+i+'</span>'+temp+'</div>';
}
this.content+='</div>';
this.content+='<span id="ui-slider-handle-date1" class="ui-slider-handle-date" id="J-slider-handle-date-0" style="left:'+ (slider1_left_position_percent*600+25/2)+'px; top: 12px; position: absolute;">'+(date1.getMonth()+1)+"."+date1.getDate()+'</span>';
this.content+='<span id="ui-slider-handle-date2" class="ui-slider-handle-date" id="J-slider-handle-date-1" style="left:'+ (slider2_left_position_percent*600+25/2)+'px; top: 12px; position: absolute;">'+(date2.getMonth()+1)+"."+date2.getDate()+'</span>';
this.content+='</div>';

}
/*原型方法：添加点击事件绑定，用于点击选择时间*/
mydateslider.prototype.bind=function(event){
        var start=this.startDate;//开始时间
        var end=this.endDate;//结束时间
        var time1=new Date(this.time1);
        var time2=new Date(this.time2);
        var clickget=function(event){clickcallback.call(this,start,end,time1,time2,event);};//定义一个变量，给它赋值为一个方法，用于绑定事件时，自己call自己，传递参数。一定要在绑定事件前声名并赋值。
        addlistennerMain($$("date-slider-line"),"click",clickget);//绑定点击事件
        function clickcallback(startDate,endDate,time1,time2,event){//回调函数，可以传参数，但是不要用this.xxx传
                var x=getMousePos(event);
                if(x>=90&&x<=690)
                        {
                                var clickDay=Math.round((x-90)/600*365);//在滑标上点击的位置对应的从开始时间算起，过了多少天数，-3是测试过程中用于修正的参数，没有来源
                                var clicktime=new Date(startDate.getTime()+clickDay*24*60*60*1000);//Date类型，点击的时间
                                var clickDate=new Date(clicktime).format("yyyy-MM-dd");//String类型，点击的时间
                                var originalStart=time1.format("yyyy-MM-dd");
                                var originalEnd=time2.format("yyyy-MM-dd");
                                if(Math.abs(clicktime.getTime()-time1.getTime())<Math.abs(clicktime.getTime()-time2.getTime()))
                                {/*此处用来判定移动哪一个滑标。距离哪一个近就移动哪一个。*/
                                        location.href=document.getElementsByTagName("base")[0].href+"Records.yy?startDate="+clickDate+"&endDate="+originalEnd;
                                }
                                else
                                {
                                        location.href=document.getElementsByTagName("base")[0].href+"Records.yy?startDate="+originalStart+"&endDate="+clickDate;
                                }
                        }
                }
        /*2014-05-19从搜狐面试javaweb开发工程师面试回来，我说了这是我的遗憾，人家问，那你现在想到怎么实现了吗？我真的想说，我知道怎么做，就是后来真的不想做的，想让遗憾成为永恒。可是想想，还是告诉了他怎么做，那就这实现吧。下次再面试就没有遗憾了*/
        var date_slider_line=$$("date-slider-line");
        var ui_slider_handle1=$$("ui-slider-handle1");
        var ui_slider_handle2=$$("ui-slider-handle2");
        var ui_slider_range=$$("ui-slider-range");
        var ui_slider_handle_date1=$$("ui-slider-handle-date1");
        var ui_slider_handle_date2=$$("ui-slider-handle-date2");
        
        var handle1mousedown=function(event){
                date_slider_line.setAttribute("slider","1");//区别是移动的谁
                addlistennerMain(document.body,"mousemove",function(event){
                        var x=(getMousePos(event)-90)/600*100;
                        if(x>0&&x<parseFloat(ui_slider_handle2.style.left)){
                                ui_slider_handle1.style.left=x+"%";
                                ui_slider_range.style.left=x+"%";
                                ui_slider_range.style.width=(parseFloat(ui_slider_handle2.style.left)-x)+"%";
                                ui_slider_handle_date1.style.left=(x*6+12.5)+"px";

                                var xdate=getMousePos(event);
                                var clickDay=Math.round((xdate-90)/600*365);//在滑标上点击的位置对应的从开始时间算起，过了多少天数，
                                var clicktime=new Date(start.getTime()+clickDay*24*60*60*1000);//Date类型，点击的时间
                                ui_slider_handle_date1.innerHTML=(clicktime.getMonth()+1)+"."+clicktime.getDate();//修改显示时间
                        }
                });
        };
        var handle2mousedown=function(event){
                date_slider_line.setAttribute("slider","2");//区别是移动的谁
                addlistennerMain(document.body,"mousemove",function(event){
                        var x=(getMousePos(event)-90)/600*100;
                        if(x<100&&x>parseFloat(ui_slider_handle1.style.left)){
                                ui_slider_handle2.style.left=x+"%";
                                ui_slider_range.style.width=(x-parseFloat(ui_slider_handle1.style.left))+"%";
                                ui_slider_handle_date2.style.left=(x*6+12.5)+"px";

                                var xdate=getMousePos(event);
                                var clickDay=Math.round((xdate-90)/600*365);//在滑标上点击的位置对应的从开始时间算起，过了多少天数，
                                var clicktime=new Date(start.getTime()+clickDay*24*60*60*1000);//Date类型，点击的时间
                                ui_slider_handle_date2.innerHTML=(clicktime.getMonth()+1)+"."+clicktime.getDate();//修改显示时间
                        }
                });
        };
        var mouseupget=function(event){mouseupcallback.call(this,start,end,time1,time2,event);};//定义一个变量，给它赋值为一个方法，用于绑定事件时，自己call自己，传递参数。一定要在绑定事件前声名并赋值。
        //addlistennerMain($$("date-slider-line"),"mouseup",mouseupget);//绑定点击事件
        function mouseupcallback(startDate,endDate,time1,time2,event){//回调函数，可以传参数，但是不要用this.xxx传
                var x=getMousePos(event);
                if(x<90)
                        x=90;
                if(x>690)
                        x=690;
                if(x>=90&&x<=690)
                        {
                                var clickDay=Math.round((x-90)/600*365);//在滑标上点击的位置对应的从开始时间算起，过了多少天数，-3是测试过程中用于修正的参数，没有来源
                                var clicktime=new Date(startDate.getTime()+clickDay*24*60*60*1000);//Date类型，点击的时间
                                var clickDate=new Date(clicktime).format("yyyy-MM-dd");//String类型，点击的时间
                                var originalStart=time1.format("yyyy-MM-dd");
                                var originalEnd=time2.format("yyyy-MM-dd");
                                if(date_slider_line.getAttribute("slider")=="1")
                                {
                                        if(new Date(clickDate)>new Date(originalEnd))
                                                clickDate=originalEnd;//开始时间不能大于结束时间
                                        location.href=document.getElementsByTagName("base")[0].href+"Records.yy?startDate="+clickDate+"&endDate="+originalEnd;
                                }
                                else if(date_slider_line.getAttribute("slider")=="2")
                                {
                                        if(new Date(clickDate)<new Date(originalStart))
                                                clickDate=originalStart;//结束时间不能小于开始时间
                                        location.href=document.getElementsByTagName("base")[0].href+"Records.yy?startDate="+originalStart+"&endDate="+clickDate;
                                }
								else{
									//location.reload();
								}
                        }
                }
        addlistennerMain(ui_slider_handle1,"mousedown",handle1mousedown);//绑定鼠标按下事件，左边的滑块
        addlistennerMain(ui_slider_handle2,"mousedown",handle2mousedown);//绑定鼠标按下事件，右边的滑块
        addlistennerMain(document.body,"mouseup",mouseupget);//绑定鼠标弹起事件 到body
        /*好吧，做完了，就这样了。*/
        
}
function getMousePos(event) {
    var e = event || window.event;
    var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
    var x = e.pageX || e.clientX + scrollX;//用于计算鼠标点击相对于文档的坐标，和下面的文档宽度有关
    var docwidth=document.body.scrollWidth;//网页的宽度，即浏览器大于网页时，是网页加上边上的空白的宽度，浏览器小于网页时，是网页的宽度
    var docwidth1= x-(docwidth-970)/2;//用于计算鼠标点击相对文档的位置，用x减去左边可能多出来的一些空白，就是鼠标的真实相对网页（文档）位置，970为我的网页宽度，这个值在不同情况下点击同一个位置返回固定的值
    //alert('x: ' + x+'\n docwidth:'+docwidth+'\n'+docwidth1);
    return docwidth1;
}