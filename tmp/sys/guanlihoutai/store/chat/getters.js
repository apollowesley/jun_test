
import yc_js from '../../common/yc_js/index.js'

export default {
    websocketErrNum(state){
       return state.websocketErrNum 
    },
    msg(state) {
        return state.msg || 0;
    },
    chat(state) {
        // console.log(state.chat)
        if (state.chat && typeof state.chat == 'object' && state.chat.length) {
            // console.log(state.chat)
            return state.chat;
        } else {
            return yc_js.Storage.Sync.get('chat') 

        }
    },
    msgList(state) {
        // console.log(state.msgList)
        if (state.msgList && typeof state.msgList == "object" ) {
            return state.msgList
        } else {
            var msgList = yc_js.Storage.Sync.get('msgList') || {};
            if(typeof msgList=='object'){
                return msgList
            }else{
                return {}
            }

        }
        // return state.msgList;
    },
    socketOpen(state) {
        return state.socketOpen;
    },
    socketMessage(state) {
        return state.socketMessage;
    },
    socketOnerror(state) {
        return state.socketOnerror
    },
    connectionUser(state) {

        return state.connectionUser
    }

}
