import {
    fetchAllUser,
    addUser,
    updateUser,
    delUserById,
    queryRoles,
    fetchUser
} from '@/services/system';

export default {
    namespace: 'systemuser',

    state: {
        UserList:{
            data:[],
            pagination:{}
        },
        detail:{
            user: {},
            roleIds:[],
            roles: [],
        }
    },

    effects: {
        *fetchAll({payload} , {call , put}){
            const response = yield call(fetchAllUser , payload);
            yield put({
                type:'reducersUserList',
                payload: response,
            })
        },
        *save({payload , callback} , {call}){
            const user = payload.user;
            let response ;
            if(!user.id){
                response = yield call(addUser , payload);
            }else {
                response = yield call(updateUser , payload);
            }
            if(callback) callback(response);
        },
        *del({payload , callback},{call}){
            const response = yield call(delUserById , payload);
            if(callback) callback(response);
        },
        *fetchRoles({callback}, {call}){
            const response = yield call(queryRoles);
            console.log(response);
            if(callback) callback(response);
        },
        *fetchOne({payload},{call , put}){
            
            const response = yield call(fetchUser , payload);

            yield put({
                type: "reducersDetail",
                payload: response,
            })
        }
    },

    reducers: {
        reducersUserList(state , action){
            return {
                ...state,
                UserList:{
                    ...action.payload
                }
            }
        },
        reducersDetail(state, { payload}){
            return {
                ...state,
                detail: {
                    user: payload.user || {},
                    roleIds: payload.roleIds || [],
                    roles: payload.roles || [],
                },
            }
        }
    }
}