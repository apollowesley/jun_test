export default {

    msg: 0, //未读消息数量
    connectUserId: null, //当前连线用户id
    msgList: null,
    // 当前对话框的用户
    connectionUser: {
        user_id: null
    },
    chat: null,
    socketOpen: false, //是否已成功连接
    websocketErrNum:0,//连接失败次数
    // socketConnect:null,//
    socketMessage: null,

}
