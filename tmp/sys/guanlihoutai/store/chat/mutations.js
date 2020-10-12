import {
    Storage,
    Obj
} from '../../common/yc_js/index.js'
import Request from "../../request/index.js";
import config from '../../config'
export default {
    websocketErrNum(state, val) {
        state.websocketErrNum = val;
    },
    socketClose(state) {
        if (state.socketOpen) {
            uni.closeSocket();
        }
        state.socketOpen = false;
    },
    connectionUser(state, val) {
        // 创建一个新的对象，将info,state.xxx对象复制到新对象中
        let data = Object.assign({}, state.connectionUser, val);
        // 将state.xxx指向新对象的引用地址
        state.connectionUser = data
    },
    msg(state, msg) {
        state.msg = msg
    },
    // 新增一条消息内容缓存到本地
    addChat(state, msg) {
        var oldMsg = state.chat
        // var oldMsg = Storage.get('chat');
        if (!oldMsg) {
            oldMsg = Storage.Sync.get('chat') || []
        }

        oldMsg.push(msg)
        state.chat = oldMsg;
        // console.log(msg);
        Storage.set('chat', oldMsg, 380000);
    },
    // 消息内容覆盖缓存到本地
    setChat(state, chatAll) {
        state.chat = chatAll;
        Storage.Sync.set('chat', chatAll, 380000);
    },
    // 消息来源列表，缓存到本地
    setMsgList(state, msgList) {

        // console.log( msgList)
        state.msgList = Object.assign({}, msgList);
        Storage.Sync.set('msgList', msgList, 80000);
    },
    // 收发消息 处理，1，分类对话消息来源，2，缓存消息内容，3未读消息推送提示。
    addMsgList(state, msg) {
        console.log(msg)
        var my_uid = state.userInfo.id;
        if (typeof msg == 'object' && msg.to_uid && msg.uid && msg.content) {
            msg.unread = true; //标记未读
            this.commit('addChat', msg)
            // var msgCount = state.msg || 0;
            // console.log({
            //     "收到": msg
            // })
            // this.commit('msg', msgCount + 1)
            // 
            // h5+ 消息push 消息推送;
            // #ifdef APP-PLUS
            if (msg.to_uid == my_uid) {
                var options = {
                    cover: false,
                    title: "通知"
                };
                var str = '';
                if (msg.type == 'order') {
                    options.title = '订单通知';
                } else {
                    options.title = msg.nickname || msg.title || '新消息';
                }
                if (msg.content) {

                    if (msg.type) {

                        switch (msg.type) {
                            case 'text':
                                str += msg.content || '新消息';
                                break;
                            case 'image':
                                str += '图片';
                                break;
                            case 'img':
                                str += '图片';
                                break;
                            case 'outreach':
                                str += '外连';
                            default:
                                str += msg.content
                                break;
                        }
                    } else {
                        str += msg.content;
                    }

                    plus.push.createMessage(str, "LocalMSG", options);
                    plus.push.addEventListener("click", function(msg) {
                        // 分析msg.payload处理业务逻辑 

                        // var message = plus.push.getClientInfo();
                        // plus.push.remove(message)
                        // console.log('删除一条');


                        // console.log('全部清除');
                        plus.push.clear();
                        // console.log(JSON.stringify(msg))

                    }, false);
                }
            }

            // #endif

        } else {
            uni.showToast({
                title: '非法数据',
                icon: 'none'
            })
        }


    },
    socketOpen(state, websocket) {
        // 打开或关闭连接状态
        if (websocket != false) {

            // this.commit('socketSend', 'Hello Server!' + isTrue);
        } else {
            this.commit('socketOnerror', "连接失败")
        }
        state.socketOpen = websocket;
    },
    socketSend(state, msg) {
        if (msg == 'ping') {
            // console.log(msg)
            uni.sendSocketMessage({
                data: msg
            });
        } else {
            if (typeof msg == 'object' && state.msgList && typeof state.msgList == 'object' && !state.msgList[msg.to_uid]) {
                // console.log(this.getters.userInfo);
                msg.headimg = this.getters.userInfo.headimg
            }

            msg = JSON.stringify(msg)
            uni.sendSocketMessage({
                data: msg
            });
        }
        // console.log(msg)

    },

    socketMessage(state, msg) {
        // console.log(msg)
        var data = Obj.StrToJSON(msg.data);
        // console.log(data)
        if (typeof data == 'object') {

            if (data.type && data.uid && data.to_uid) {


                if (data.uid == this.getters.userInfo.id) {
                    // 如果发送人id=我的id，就是我发出信息的回执单
                } else if (data.to_uid == this.getters.userInfo.id) {
                    // 别人发给我的信息
                    this.commit('addMsgList', data)
                }

            } else {
                // console.log(data)
                // if (data.code && data.code == 444) {
                //     this.commit('logOut')
                // }
                if (data.code = 444) {
                    // uni.showToast({
                    //     title: data.message,
                    //     icon: 'none'
                    // })
                    // setTimeout(() => {
                    //     if (!state.hasLogin) {
                    //         this.commit('logOut')
                    //     }
                    // }, 1000);

                } else if (data.code == 441) {
                    // uni.showToast({
                    //     title: data.message
                    // })
                    // setTimeout(() => {
                    //     if (!state.hasLogin) {
                    //         this.commit('logOut')
                    //     }
                    // }, 1000);
                } else if (data.code > 200) {
                    // uni.showToast({
                    //     title: data.message
                    // })
                } else if (data.code == 200) {
                    // if (data.type == 'login') {
                    //     Storage.Sync.set('websocketToken', data.data.token, 380000);
                    // }
                    // console.log(data)
                }
            }

        } else {
            // console.log(['不合法', data])
        }
    },
    socketOnerror(state, error) {
        var that = this;
        // var title = "立即登录";
        var hasLogin = this.getters.hasLogin;
        // console.log(hasLogin)
        if (!this.getters.socketOpen) {
            if (hasLogin) {
                setTimeout(e => {
                    if (!that.getters.socketOpen) {
                        that.dispatch('socketConnect')
                    }
                }, 10000)
            } else {

            }
        }


    },
    socketConnect(state, socket) {
        state.socketConnect = socket
    }


}
