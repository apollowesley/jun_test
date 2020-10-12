import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);//发布时候因为有cdn 这个需要删除不然会重复引入
import win from "./win.js"
import yc from 'yc-js';
var store = {
    state: {
        cacheExpires:172800,//缓存时间秒默认2天
        longLogin:false,//是否保持长登录状态//存入本地缓存。短登录仅存入session的临时缓存
        hasLogin: false, //登陆状态 是否已登录
        loginProvider: "pc", //登陆方式 如 微信
        token: {
            token: '',
            expires: 0
        },
        message: 0, //未读消息数量
        address: {}, //收货地址
        userInfo: {
            nickname: "未登录",
            headimg: "../assets/images/logo.png",
            id: Date.now(),
            individuality: "爱你一万年",
            address: "北京市西城区中南海大院1号",
            sex: "男",
            area: "北京-北京-东城区"
        } //用户信息
    },
    getters: {
        loginProvider(state) {
            return state.loginProvider;
        },
        longLogin(state){
            if(state.hasLogin){
                 return state.longLogin; 
            }else{
              state.longLogin=yc.Storage.Sync.get('longLogin') || false;
              return state.longLogin;
            }
        },
        hasLogin(state) {
            return state.hasLogin;
        },token(state) {
            if(state.hasLogin){
                return state.token;
            }else if (state.longLogin) {
            	return  yc.Storage.Sync.get('token') || state.token;
            } else{
            	return  yc.Storage.Session.get('token') || state.token;
            }
        },
        message(state) {
            return state.message;
        },
        userInfo(state) {
           if(state.hasLogin){
               return state.userInfo;
           }else if (state.longLogin) {
                return  yc.Storage.Sync.get('userInfo') || state.userInfo;
           }else{
                return  yc.Storage.Session.get('userInfo')|| state.userInfo;
           }
        },
        login(state) {
            return state.hasLogin;
        },
        address(state) {
            return state.address;
        }
    },
    mutations: {
        longLogin(state,sate){
            var sate=sate || false;
            yc.Storage.Sync.set('longLogin',sate,state.token.expires); 
            state.longLogin=  sate;
        },
        login(state){
                state.hasLogin =true;
        },
        userInfo(state, val) {
            state.userInfo = val;
            if (state.longLogin) {
                yc.Storage.Sync.set('userInfo',val,state.token.expires);
            }else{
                // 临时登录环境
                yc.Storage.Session.set('userInfo',val,state.token.expires);
            }
        },
        loginOut(state) {
            state.userInfo = {};
            yc.Storage.clear();
            state.hasLogin = false;
        },
        loginProvider(state, val) {
            state.loginProvider = val;
        },
        token(state, val) {
            state.token = val;
            if (state.longLogin) {
                yc.Storage.Sync.set('token',val,state.token.expires);
            }else{
                yc.Storage.Session.set('token',val,state.token.expires);
            }
        }
    }
};
export default new Vuex.Store({
    modules: {
        store: store,
        win: win

    }
})
