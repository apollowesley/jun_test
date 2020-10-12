import {
    fetchAllRole,
    fetchAllMenu,
    addRole,
    updateRole,
    fetchRoleById,
    delRoleById
} from '@/services/system'

export default {

    namespace: "systemrole",

    state: {
        RoleList:{
            data: [],
            pagination:{}
        },
        Menus:{
            children: []
        }
    },

    effects: {
        *fetchAll({payload},{call, put}){
            const response = yield call(fetchAllRole , payload)

            yield put({
                type: 'reducersRoleList',
                payload: response
            })
        },
        *menus({_},{call , put}){
            const response = yield call(fetchAllMenu)

            yield put({
                type: 'reducersMenus',
                payload: response
            });
        },
        *save({payload , callback},{call}){
            const roleInfo = payload.role;
            let response ;
            if(!roleInfo.id){
                response = yield call(addRole , roleInfo);
            }else {
                response = yield call(updateRole , roleInfo);
            }
            
            if(callback) callback(response);
        },
        *fetchById({payload , callback},{call}){
            const response = yield call(fetchRoleById , payload);
            if(callback) callback(response);
        },
        *del({payload , callback}, {call}){
            const response = yield call(delRoleById , payload);
            if(callback) callback(response);
        }
    },

    reducers: {
        reducersRoleList(state, action){
            return {
                ...state,
                RoleList: action.payload
            }
        },
        reducersMenus(state, action){
            return {
                ...state,
                Menus: action.payload
            }
        }
    },

}