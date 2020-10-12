// 模拟数据请求部分 ————————————————————————————————————————————————————————————————————
import ceshiData from './data/index.js'; //测试数据代替远程服务器的请求返回数据
import API from './api.js';
var runLocal = false; //是否运行模式 本地测试数据=true。非本地=false
import store from '../store/index.js'
import config from '@/config/index.js'
import yc_js from "../common/yc_js/index.js"
var Url = yc_js.Url;
var Time = yc_js.Time;
var Md5 = yc_js.Md5;
var Id = yc_js.Id;
var Json = yc_js.Json;
var YKID = Id.Id(); //游客的ID
var YKTOKEN = Id.Id(); //游客的token
var Base64 = yc_js.Base64
// 设置请求apiName对应的请求参数
var apiObj = API;
if (runLocal) {
    apiObj = ceshiData
}

/**
 * 修补请求方法 发请求前自动根据需求添加token
 * urlName			string 		请求地址 name
 * successFun 		function 	请求成功回调函数；
 * dataObj			object		请求参数数据requeObj.data
 * requeObj			object		请求参数全部参数(含 method,data,header,dataType,responseType,fail等更多) 
 */
function ajax(apiNameStr, requeObj) {
    // 请求密钥
    function configure() {
        var time = Time.toTimestamp(10);
        // console.log(store.getters)
        var token = store.getters.token || {};
        if (typeof token != 'object' || !token.token) {
            token = {}
            token.token = YKTOKEN;

        }
        var uid = token.uid || YKID;
        // source=store.getters.source
        return uid + '.' + token.token + '.' + store.getters.source + '.' + store.getters.systemInfo.model;
    }

    // console.log(requeObj)
    var apiNameStr = apiNameStr || "index";

    var requeObj = requeObj || {data:{}};
    var Obj = apiObj[apiNameStr];
    // console.log(apiObj)
    return new Promise((resolve, reject) => {
        // console.log(JSON.stringify(Obj))
        if (Obj) {
            for (let i in Obj) {
                if (typeof requeObj[i] === 'object') {
                    requeObj[i] = Object.assign({}, Obj[i], requeObj[i])
                } else {
                    requeObj[i] = Obj[i];
                }
                if (requeObj[i]['Authorization']) {
                    requeObj[i]['Authorization'] = Base64.urlDecoder(configure());
                }

            }
            requeObj.dataType = requeObj.dataType || "json";
            // requeObj.data.source = config.source;
            requeObj.method = requeObj.method || 'GET';
            if (requeObj.method == 'GET') {
                // requeObj.data=Url.urlEncode(requeObj.data).slice(1)
            }
            // console.log(JSON.stringify(requeObj))
            Object.assign(requeObj, {

                success: function(res) {

                    // console.log(res)
                    var data = res.data;
                    switch (data.code) {
                        case 205:
                            if (data.data['Authorization']) {
                                store.commit('token', data.data['Authorization']);
                                
                                // console.log(store.state)
                            }
                            // return ajax(apiNameStr, requeObj);
                            break;
                        default:
                        
                        // if (data.code && data.code == 441) {
                        //     store.commit('logOut');//退出登录
                        //     uni.showToast({
                        //         title: data.message || '请求失败',
                        //         icon: 'none',
                        //     })
                        // }
                        
                        if (requeObj.dataType == "text") {
                        
                            try {
                                // 转成json对象，如果数字长度超过16位会转成字符串。防止js解析丢失精度
                                res.data = Json.getRealJsonData(res.data);
                        
                            } catch (e) {
                                res.data = data;
                            }
                        }
                        resolve(res)
                            break;
                    }

                },
                fail: function(e) {
                    uni.showToast({
                        title: '未知错误请求',
                        icon: 'none',
                    })
                    // console.log(e)
                }
            })
            // console.log(requeObj)
            uni.request(requeObj)
            // 			// 合并 用户请求参数+系统预置请求参数	
        } else {
            resolve(false)
            reject(false)
        }

    })
}

export default ajax
// // 中断请求任务
// requestTask.abort();
