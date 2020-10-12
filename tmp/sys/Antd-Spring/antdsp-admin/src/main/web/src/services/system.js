import { async } from "q";
import request, { download } from '@/utils/request';
import qs from 'qs';

const API_BASE = "/antdsp-api";
const USER_API = API_BASE + "/system/user";
const MENU_API = API_BASE + "/system/menu";
const ROLE_API = API_BASE + "/system/role";

export async function fetchAllUser(params){
    return request(`${USER_API}?${qs.stringify(params, { indices: false })}`);
}

export async function addUser(params){
    return request(`${USER_API}`,{
        method: 'POST',
        data:{
            ...params
        }
    });
}

export async function updateUser(params){
    const userId = params.user.id;
    return request(`${USER_API}/${userId}`,{
        method: 'PUT',
        data:{
            ...params
        }
    });
}

export async function delUserById(params){
    return request(`${USER_API}/${params.id}`,{
        method: 'DELETE',
    });
}

export async function fetchUser(params){
    return request(`${USER_API}/${params.id}`,{
        method: 'GET'
    });
}

export async function fetchAllMenu(){
    return request(`${MENU_API}`)
}

export async function addMenu(params){

    return request(`${MENU_API}`,{
        method: 'POST',
        data:{
            ...params
        }
    });
}
export async function updateMenu(params){
    return request(`${MENU_API}`,{
        method: 'PUT',
        data:{
            ...params
        }
    });
}

export async function delMenuById(param){
    return request(`${MENU_API}/${param.id}`,{
        method: 'DELETE',
    })
}

export async function fetchAllRole(param){
    return request(`${ROLE_API}?${qs.stringify(param, { indices: false })}`)
}


export async function addRole(params){
    return request(`${ROLE_API}`,{
        method: 'POST',
        data: {
            ...params
        },
    })
}
export async function updateRole(params){
    return request(`${ROLE_API}`,{
        method: 'PUT',
        data: {
            ...params
        }
    });
}

export async function delRoleById(param){
    return request(`${ROLE_API}/${param.id}`,{
        method: 'DELETE'
    })
}

export async function fetchRoleById(param){
    return request(`${ROLE_API}/${param.id}`);
}

export async function queryRoles(){
    return request(`${ROLE_API}/queryRoleNameAndIds`);
}

export async function queryRoleMenus(){
    return request(`${MENU_API}/route`);
}

export async function fetchAllLog(param){
    return request(`${API_BASE}/operation/log?${qs.stringify(param, { indices: false })}`);
}

export async function exportLog(params){
    return download(`${API_BASE}/operation/log/export?${qs.stringify(params, { indices: false })}`)
}