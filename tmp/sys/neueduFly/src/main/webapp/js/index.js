layui.use(['element', 'carousel','laypage','util'], function () {
    let element = layui.element,
        carousel = layui.carousel,
        laypage = layui.laypage,
        util = layui.util;

    //轮播图实例
    carousel.render({
        elem: '#advert' , 
        width:'100%',
        height: '172px', 
        arrow: 'hover',  //始终显示箭头
        anim: 'fade' //切换动画方式
    });
    //分页实例
    laypage.render({
        
        elem: 'note-page', //注意，这里的 note-page 是 ID，不用加 # 号
        count: 36, //数据总条数，从服务端得到
        limit: 5,  //每页显示的条数
        first: '首页', //总页数大于页码总数才会出现
        last: '尾页'
    });
    //固定块实例
    util.fixbar({
        bar1: '&#xe642;', 
        bgcolor:'#5FB878',
        showHeight:300, //TOP按钮的滚动条高度临界值。默认：200
        click: function(type){
            console.log(type);
            if(type === 'bar1'){
                alert('点击了bar1')
            }
        }
    });

});