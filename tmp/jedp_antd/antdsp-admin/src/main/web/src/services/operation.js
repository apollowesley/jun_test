import request from '@/utils/request';
import qs from 'qs';

const API_BASE = "/antdsp-api";
const ARTICLE_API = API_BASE + '/operation/article';

export async function fetchAllArticle(params){

    return request(`${ARTICLE_API}?${qs.stringify(params, { indices: false })}`);
}

export async function fetchArticleById(params){
    return request(`${ARTICLE_API}/${params.id}`)
}

export async function addArticle(params){

    return request(`${ARTICLE_API}`,{
        method: 'POST',
        data: {
            ...params
        }
    });
}
export async function updateArticle(params){
    return request(`${ARTICLE_API}/${params.id}`,{
        method: 'PUT',
        data: {
            ...params
        }
    });
}

export async function delArticle(params){
    return request(`${ARTICLE_API}/${params.id}`,{
        method: 'DELETE',
    })
}