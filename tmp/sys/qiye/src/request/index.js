// 模拟数据请求部分 ————————————————————————————————————————————————————————————————————
import ceshiData from './data/index.js'; //测试数据代替远程服务器的请求返回数据
import yc from "yc-js";
import ElementUI from './../element/index.js';

var Id=yc.Id;
var YKID=Id.Id();//游客的ID
var YKTOKEN=Id.Id();//游客的token
// import {
//     Notification,Loading
// } from 'element-ui';
import store from '../store/index.js'

// 正文
import axios from 'axios'
import api from './api.js'

// import yc from "yc-js"
var Time=yc.Time;
var Md5=yc.Md5;
import Config from '../config.js'

var loading;
var ajax = axios.create({
    withCredentials: false,
});
//创建ajax自定义实例

var runLocal = false; //是否运行模式 本地测试数据=true。非本地=false
var moniDate; //模擬數據的全局變量
// 覆写库的超时默认值
// 现在，在超时前，所有请求都会等待 2.5 秒
ajax.defaults.timeout = 10000;
// var loading;
// 添加一个请求拦截器 请求之前进行拦截处理
ajax.interceptors.request.use(function(config) {
    loading = ElementUI.Loading.service({
                  lock: true,
                  text: 'Loading',
                  spinner: 'el-icon-loading',
                  background: 'rgba(0, 0, 0, 0.7)'
                });
    // console.log('拦截请求')
    // 请求密钥
    // 请求密钥
    function configure() {
        var time = Time.toTimestamp(10);
        
        var token=store.getters.token;
        if(typeof token !='object' || !token.token){
            token.token=YKTOKEN;
            
        }
        var uid=token.uid || YKID;
        return {
            uid: uid,
            token:token.token,
            time: time
        }
    }

    if (runLocal) {
        return moniDate = ceshiData[config.url]
    }
    var Obj = api[config.url];
    // 替换请求参数
    if (Obj) {
        for (let i in Obj) {
            if (typeof config[i] === 'object') {
                config[i] = Object.assign(Obj[i], config[i])
            } else {
                config[i] = Obj[i];
            }
            if (Obj[i]['access_token']) {
                // config[i]['access_token']='101552115164859.a406e7603c4dbc29c71c8a5214696218.1553076341';
                config[i]['access_token'] = configure().uid + '.' + configure().token + '.' + configure().time;
            }
        }
    }
    // console.log(config)
    config.validateStatus = function(status) {
        return status >= 200 && status < 50000
    }
    return config;
}, function(error) {
    // console.log(error)
    return Promise.reject(error);
});
// 添加一个响应拦截器

ajax.interceptors.response.use(function(response) {
    // console.log(response)
    loading.close();
    return response;
}, function(error) {
    if (runLocal) { //本地模式 返回测试数据
        return {
            data: {
                code: 200,
                message: 'ok',
                data: moniDate
            }
        }
    }
    loading.close();
        ElementUI.Notification({
        title: '警告',
        message: '请求操作失败',
        type: 'warning'
    });
    return Promise.reject(error);
});

// export {ajax};
export default ajax
