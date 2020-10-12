/**
 * Created by guozhongqiang on 16/6/13.
 */
seajs.config({
    base:'../js/lib',
    // 别名配置
    alias: {
        'vue':'vue.js',
        'jquery':'jquery.js',
        'define':'component/define',
        'object':'util/object'
        //'jquery': '/sea/jquery-sea',
        //'home': '/sea/home',
        //'data': '/sea/data'
    },

    // 路径配置
    //paths: {
    //  'gallery': 'https://a.alipayobjects.com/gallery'
    //},

    // 变量配置
    vars: {
      'locale': 'zh-cn'
    },

    // 映射配置
    //map: [
    //  ['http://example.com/js/app/', 'http://localhost/js/app/']
    //],

    // 预加载项
    preload: ['vue'],

    // 调试模式
    debug: true,

    // Sea.js 的基础路径
    //base: 'http://example.com/path/to/base/',

    // 文件编码
    charset: 'utf-8'
});
seajs.use(['vue','jquery','object','define'],function(){
    seajs.use('../js/lib/component/widget.js');
    seajs.use('../js/lib/component/menu.js');

});
