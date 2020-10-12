import * as types from './mutation-types'

export default {
    [types.UPDATE_USER_INFO](state, o) {
        state.userInfo[o.name] = o.value;
    }
}