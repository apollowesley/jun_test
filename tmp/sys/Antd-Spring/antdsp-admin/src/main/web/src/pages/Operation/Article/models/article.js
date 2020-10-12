import {
    fetchAllArticle,
    fetchArticleById,
    addArticle,
    updateArticle,
    delArticle
} from "@/services/operation"

export default {
    namespace: 'article',

    state: {
        ArticleList: {
            data: [],
            pagination: {

            }
        },
        Article: {

        }
    },

    effects: {
        *fetchAll({payload},{call , put}){

            const response = yield call(fetchAllArticle , payload);

            yield put({
                type: 'reducerArticleList',
                payload: response
            })
        },
        *fetchById({payload},{call, put}){
            const response = yield call(fetchArticleById, payload);
            yield put({
                type:'reducersArticle',
                payload: response,
            })
        },
        *save({payload, callback},{call, put}){
            const Article = payload.Article;
            let response ;
            if(!Article.id){
                response = yield call(addArticle, Article);
            }else {
                response = yield call(updateArticle, Article);
            }
            if(callback) callback(response);
        },
        *del({payload, callback},{call}){
            const response = yield call(delArticle, payload);

            if(callback) callback(response);
        }
    },

    reducers: {
        reducerArticleList(state , { payload }){

            return{
                ...state,
                ArticleList: payload
            }
        },
        reducersArticle(state, {payload}){
            return{
                ...state,
                Article: payload
            }
        }
    }
}