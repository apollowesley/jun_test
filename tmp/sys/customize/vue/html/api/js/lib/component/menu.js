/**
 * Created by guozhongqiang on 16/6/14.
 */
/**
 * 菜单组件
 */
hml.define('ui.component.menu',
    ['ui.component.widget'],
    function(widget,objectUtil) {
        'use strict';
    var menu = {name:'menu'};

    console.log(widget, objectUtil);

    return menu;
});
//通过这个来返回对象的值;seajs.require('ui.component.menu')