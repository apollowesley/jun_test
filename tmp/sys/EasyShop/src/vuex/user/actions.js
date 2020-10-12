import * as types from './mutation-types'

export default {
    updateUserInfo({commit}, value) {
        commit(types.UPDATE_USER_INFO, value);
    }
}