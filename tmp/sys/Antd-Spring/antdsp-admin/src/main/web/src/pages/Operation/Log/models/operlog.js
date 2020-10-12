import { fetchAllLog ,exportLog} from "@/services/system";

export default {
    namespace: 'operlog',

    state: {
        LogList:{
            data:[],
            pagination:{}
        }
    },

    effects: {
        *fetchAll({payload},{call, put}){
            const response = yield call(fetchAllLog, payload);

            yield put({
                type: 'reducersLogList',
                payload: response,
            })
        },
        *download({payload},{call}){
            yield call(exportLog, payload)
        }
    },

    reducers: {
        reducersLogList(state,{payload}){
            return {
                ...state,
                LogList: payload
            }
        }
    }
}