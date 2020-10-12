
import yc_js from '../../common/yc_js/index.js'

export default {

    msg(state) {
        return state.msg || 0;
    },
    chat(state) {
        // console.log(state.chat)
        if (state.chat && typeof state.chat == 'object' && state.chat.length) {
            // console.log(state.chat)
            return state.chat;
        } else {
            var chat = yc_js.Storage.Sync.get('chat');
            // if (typeof msgList == 'object' && chat.length) {
            //     console.log(chat)
            //     this.commit('setChat', chat);
            // }

            return chat;
        }
    },
    msgList(state) {
        // console.log(state.msgList)
        if (state.msgList && typeof state.msgList == "object" && state.msgList.length) {
            return state.msgList
        } else {
            var msgList = yc_js.Storage.Sync.get('msgList');
//             if (typeof msgList == 'object' && msgList.length) {
//                 // console.log(store)
//                 // this.commit('msgList', msgList);
// 
//             }
            return msgList;

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
