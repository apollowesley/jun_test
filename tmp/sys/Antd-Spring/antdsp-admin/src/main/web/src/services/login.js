import { stringify } from 'qs';
import request from '@/utils/request';

const API_BASE = "/antdsp-api";

export async function loginAction(param){
    return request(`${API_BASE}/login`,{
        method: 'POST',
        data: {
            ...param
        }
    })
}

export async function logoutAction(param){
    return request(`${API_BASE}/logout`);
}