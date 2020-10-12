import request from '@/utils/request'

export function getProfessionList(params) {
  return request({
    url: process.env.ADMIN_API + '/profession/getList',
    method: 'get',
    params
  })
}

export function addProfession(param) {
  return request({
    url: process.env.ADMIN_API + '/profession',
    method: 'post',
    data: param
  })
}

export function editProfession(param) {
  return request({
    url: process.env.ADMIN_API + '/profession',
    method: 'put',
    data: param
  })
}

export function deleteProfession(param) {
  return request({
    url: process.env.ADMIN_API + '/profession/delete',
    method: 'post',
    data: param
  })
}

export function getProfessionCount(param) {
  return request({
    url: process.env.ADMIN_API + '/profession/getcount',
    method: 'post',
    data: param
  })
}
