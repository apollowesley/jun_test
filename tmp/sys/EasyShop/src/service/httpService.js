import {query} from './axios'

export default {
    login(params) {
        return query('/api/user/login', params)
    },
    updateToken(params){
        return query('/api/user/updateToken', params)
    }
}