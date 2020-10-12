import {
    Storage,
    Base64
} from '../../common/yc_js/index.js'
import config from '../../config'
var pingSocket = null;
export default {
    socketConnect(context) {
        // console.log(context.getters.socketOpen)
        // if (!context.getters.socketOpen) {
        if (context.getters.hasLogin) {
            if (!context.getters.socketOpen) {
                var token = context.getters.token || {};
                var acctoken = token.uid + "." + token.token + "." + context.getters.source + "." + context.getters.systemInfo
                    .model;
                // var websocketToken = Storage.Sync.get('websocketToken');
                //字符串转base64 
                // + '&token=' + websocketToken
                var access_token = '?access_token=' + Base64.urlDecoder(acctoken);
                // console.log('wss://' + config.websockethost + access_token)

                var websocket = uni.connectSocket({
                    url: 'wss://' + config.websockethost + access_token
                });
                // console.log(websocket)
                uni.onSocketError((res) => {
                    context.commit('socketOnerror', websocket)
                    // console.log('WebSocket连接打开失败，10秒后重连！');
                    // if (context.getters.hasLogin) {
                    //     setTimeout(() => {
                    //         context.dispatch('socketConnect')
                    //     }, 100000);
                    // }else{
                    //      context.commit('socketOpen', null)
                    // }

                });

                var socketMsgQueue = []; //建立连接认证信息

                uni.onSocketOpen((res) => {
                    // console.log(res)
                    // console.log('WebSocket连接已打开！');
                    context.commit('websocketErrNum', 0)
                    context.commit('socketOpen', websocket)

                });
                uni.onSocketMessage((res) => {
                    context.commit('socketMessage', res)
                    // console.log('收到服务器内容：' + res);
                });
                var that = this
                uni.onSocketClose((res) => {
                    console.log('WebSocket 已关闭！' + JSON.stringify(res));
                    context.commit('socketOpen', false)
                    if (context.state.websocketErrNum < 5) {
                        
                        context.commit('websocketErrNum', context.state.websocketErrNum + 1)
                        setTimeout(() => {
                            context.dispatch('socketConnect')
                        }, 3000)

                    }

                });
            }

        } else {
            uni.showToast({
                title: '请先登录',
                icon: 'none'
            })
            // context.dispatch('isLogin')

        }


    }
}
