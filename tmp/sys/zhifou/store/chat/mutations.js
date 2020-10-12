import {
    Storage,
    Obj
} from '../../common/yc_js/index.js'
import Request from "../../request/index.js";
import config from '../../config'
export default {

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
    addChat(state, msg) {
        var oldMsg = Storage.Sync.get('chat');

        if (typeof oldMsg != 'object') {
            oldMsg = [];
        }
        oldMsg.push(msg)
        state.chat = oldMsg;
        // console.log(oldMsg);
        Storage.Sync.set('chat', oldMsg, 380000);
    },
    setChat(state, chatAll) {
        state.chat = chatAll;
        Storage.Sync.set('chat', chatAll, 380000);
    },
    setMsgList(state, msgList) {
        state.msgList = msgList;
        Storage.Sync.set('msgList', msgList, 80000);
    },
    addMsgList(state, msg) {
        console.log({
            state,
            msg
        });
        var my_uid = state.userInfo.id;
        if (typeof msg == 'object' && msg.to_uid) {

            // h5+ 消息push 消息推送;
            // #ifdef APP-PLUS
            console.log({
                a: 'h5+',
                msg,
                state: state.userInfo
            })
            if (msg.to_uid == my_uid) {
                var options = {
                    cover: false,
                    title: "通知"
                };
                var str = '';
                if (msg.type == 'order') {
                    options.title = '订单通知';
                } else if (msg.type == 'chat') {
                    options.title = msg.nickname || msg.title || '新消息';
                }
                if (msg.content) {
                    if ((typeof(msg.content) == 'object')) {
                        if (msg.content.type) {

                            switch (msg.content.type) {
                                case 'text':
                                    str += msg.content.value || msg.content.text || '新消息';
                                    break;
                                case 'image':
                                    str += '图片';
                                    break;
                                case 'img':
                                    str += '图片';
                                    break;
                                default:
                                    str += JSON.stringify(msg.content)
                                    break;
                            }
                        } else {
                            str += msg.content;
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

            var list = this.getters.msgList || [];
            var face_uid = msg.to_uid;
            if (msg.to_uid == my_uid) {
                face_uid = msg.uid;
            }
            var oldMsg = null; //相同用户的上次消息内容
            var msgList = [];

            for (let i = 0; i < list.length; i++) {
                if ((list[i].uid == face_uid && list[i].to_uid == my_uid) || (list[i].to_uid ==
                        face_uid && list[i].uid == my_uid)) {

                    oldMsg = list[i];

                } else {

                    msgList.push(list[i])
                }
            }
            if (oldMsg) {
                var num = oldMsg.num || 0;
                msg.num = num + 1;
                Object.assign(oldMsg, msg);
            } else {
                msg.num = 1;
                oldMsg = msg;
            }

            if (state.connectionUser.uid == face_uid) {
                oldMsg.num = 0;
            }

            var nickname = null;
            var to_headimg = null;
            // oldMsg.headimg=oldMsg.headimg || null;
            if (face_uid==oldMsg.to_uid) {
                to_headimg = oldMsg.headimg || state.connectionUser.headimg ||  null;
                nickname = oldMsg.nickname || state.connectionUser.nickname ||  "匿名";
            }

            if (!to_headimg && oldMsg.uid) {
                // console.log({face_uid,my_uid})
                // ajax() 略去服务器请求 对方头像和对方用户名
                if (oldMsg.uid == '100000000') {
                    // 系统消息
                    oldMsg.headimg = 'logo.png';
                    oldMsg.nickname = '系统消息';
                    msgList.unshift(oldMsg)
                    // console.log(msgList)
                    // state.msgList = msgList;
                    state.socketMessage = Object.assign({}, oldMsg);
                    var msgNum = 0;
                    for (var i = 0; i < msgList.length; i++) {
                        if (msgList[i].num && msgList[i].num > 0) {
                            msgNum = msgNum + parseInt(msgList[i].num)
                        }

                    }
                    // console.log(msgNum)
                    state.msg = msgNum;
                    this.commit('setMsgList', msgList)
                    this.commit('addChat', msg)
                } else {
                    //用户对话 消息内容不含用户头像，从服务器获取发送人的头像 信息
                    Request('UserUser_info', {
                            data: {
                                uid: oldMsg.uid
                            }
                        })
                        .then(e => {
                            // console.log(e)
                            if (e.data.code == 200) {
                                var userInfo = e.data.data;
                                oldMsg.headimg = config.getFileUrl(userInfo.headimg);
                                oldMsg.nickname = userInfo.nickname || '匿名';
                                msgList.unshift(oldMsg)
                                // console.log(msgList)
                                // state.msgList = msgList;
                                state.socketMessage = Object.assign({}, oldMsg);
                                var msgNum = 0;
                                for (var i = 0; i < msgList.length; i++) {
                                    if (msgList[i].num && msgList[i].num > 0) {
                                        msgNum = msgNum + parseInt(msgList[i].num)
                                    }

                                }
                                // console.log(msgNum)
                                state.msg = msgNum;
                                this.commit('setMsgList', msgList)
                                this.commit('addChat', msg)
                            }
                        })
                }


            } else {
                
                // 消息内容包含了发送人的头像，无需从服务器获取  
                if (my_uid == face_uid) {

                    oldMsg.headimg = to_headimg;
                    oldMsg.nickname = nickname;
                }

                msgList.unshift(oldMsg)
                // console.log(msgList)
                // state.msgList = msgList;
                state.socketMessage = Object.assign({}, oldMsg);
                var msgNum = 0;
                for (var i = 0; i < msgList.length; i++) {
                    if (msgList[i].num && msgList[i].num > 0) {
                        msgNum = msgNum + parseInt(msgList[i].num)
                    }

                }
                // console.log(msgNum)
                state.msg = msgNum;
                this.commit('setMsgList', msgList)
                this.commit('addChat', msg)
            }
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

        var data = Obj.StrToJSON(msg.data);

        if (data && typeof data == 'object') {
            // var msg = data.data || {};
            // console.log(data)
            if (data.type == 'chat' && data.uid && data.to_uid) {
                if (data.uid == this.getters.userInfo.id) {
                    // console.log(data)
                    // if (data.state != 1) {
                    //     title = '对方不在线';
                    // } else {
                    this.commit('addMsgList', data)
                    // state.socketMessage = data;
                    // }
                    // uni.showToast({
                    //     mask: true,
                    //     duration: 500,
                    //     title: '发送成功',
                    //     icon: "none"
                    // })
                } else if (data.to_uid == this.getters.userInfo.id) {
                    this.commit('addMsgList', data)
                    // state.socketMessage = data;
                    // }
                    // uni.showToast({
                    //     title: '新消息',
                    //     icon: "none"
                    // })

                }
            } else if (data.type == 'order') {
                data.uid = 10000;
                this.commit('addMsgList', data)
            }

        } else {
            console.log(['不合法', data])
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
                    if (!this.getters.socketOpen) {
                        that.dispatch('socketConnect')
                    }
                })
                // uni.showModal({
                //     title: '服务器已断开,尝试重新连接?',
                //     content: '断开原因:1、网络不稳定，解决(尝试连接);2、登录授权过期/其他设备登录,[解决](重新登录);3、密码泄露,被他人登录踢掉线，[解决](重新登录,修改密码)。',
                //     cancelText: '重新登录',
                //     confirmText: '尝试连接',
                //     success(e) {
                //         if (e.confirm) {
                //             that.dispatch('socketConnect')
                //         } else {
                //             //登陆
                //             uni.navigateTo({
                //                 url: '/pages/login/login'
                //             })
                //         }
                //     }
                // })
            } else {
                // uni.showModal({
                //     title: '立即登录?',
                //     content: ' ',
                //     cancelText: '重新登录',
                //     confirmText: '取消',
                //     success(e) {
                //         if (e.confirm) {
                //             // that.dispatch('socketConnect')
                //         } else {
                //             //登陆
                //             uni.navigateTo({
                //                 url: '/pages/login/login'
                //             })
                //         }
                //     }
                // })
            }
        }

        // console.log(['连接错误', error])

        // state.socketOnerror=error
    },
    socketConnect(state, socket) {
        state.socketConnect = socket
    }


}
