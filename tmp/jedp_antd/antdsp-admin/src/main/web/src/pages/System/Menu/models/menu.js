import {
    fetchAllMenu,
    addMenu,
    updateMenu,
    delMenuById
} from '@/services/system';

export default {

    namespace: 'systemmenu',
    
    state: {
        MenuList:[]
    },

    effects: {
        *fetchAll({payload},{call , put}){
            const response = yield call(fetchAllMenu);

            yield put({
                type: 'reducersMenuList',
                payload: response
            })
        },
        *save({payload , callback},{call , put}){
            
            const menuInfo = payload;
            let response ;
            if(!menuInfo.id){
                response = yield call(addMenu , menuInfo);
            }else {
                response = yield call(updateMenu , menuInfo);
            }

            if(callback) callback(response);
        },
        *delById({payload, callback},{call,put}){
            const response = yield call(delMenuById , payload);
            if(callback) callback(response);
        }
    },

    reducers: {
        reducersMenuList(state , action){
            const menus = action.payload;
            return {
                ...state,
                MenuList: menus.children || []
            }
        }
    }
}