import {
    Base64
} from '../../common/yc_js/index.js'
import config from '../../config'
var pingSocket = null;
export default {
    socketConnect(context) {
        // if (!context.getters.socketOpen) {
        if (context.getters.hasLogin) {
            var token = context.getters.token || {};
            var acctoken = token.uid + "." + token.token + "." + context.getters.source;
            //字符串转base64
            var access_token = '?access_token=' + Base64.urlDecoder(acctoken);
            console.log('wss://' + config.websockethost + access_token)

            var websocket = uni.connectSocket({
                url: 'wss://' + config.websockethost + access_token
            });
            // console.log(websocket)
            uni.onSocketError(function(res) {
                console.log('WebSocket连接打开失败，10秒后重连！');
                if (context.getters.hasLogin) {
                    setTimeout(() => {
                        context.socketConnect()
                    }, 10000);
                }

            });

            var socketMsgQueue = []; //建立连接认证信息

            uni.onSocketOpen((res) => {
                console.log('WebSocket连接已打开！');
                context.commit('socketOpen', websocket)

                pingSocket = setInterval((e) => {
                    // console.log('ping')
                    uni.sendSocketMessage({
                        data: 'ping'
                    });
                    // self.$store.commit('socketSend', 'ping')
                }, 10000)
            });
            uni.onSocketMessage(function(res) {
                context.commit('socketMessage', res)
                // console.log('收到服务器内容：' + res);
            });
            var that = this
            uni.onSocketClose(function(res) {
                console.log('WebSocket 已关闭！' + JSON.stringify(res));
                context.commit('socketOpen', false)
            });
        } else {

            uni.showModal({
                title: '立即登录?',
                content: '当前状态未登录，或网络断开。',
                cancelText: '重新登录',
                confirmText: '取消',
                success(e) {
                    if (e.confirm) {
                        // that.dispatch('socketConnect')
                    } else {
                        //登陆
                        uni.navigateTo({
                            url: '../login/login'
                        })
                    }
                }
            })


        }


    }
}
