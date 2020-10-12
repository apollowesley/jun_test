import request from '@/utils/request'

export function getRelationList(params) {
  return request({
    url: process.env.ADMIN_API + '/professionIndicatorRelation/getList',
    method: 'get',
    params
  })
}

export function findBrother(params) {
  return request({
    url: process.env.ADMIN_API + '/professionIndicatorRelation/findBrother',
    method: 'get',
    params
  })
}

export function getAllson(params) {
  return request({
    url: process.env.ADMIN_API + '/professionIndicatorRelation/getallson',
    method: 'get',
    params
  })
}

export function addRelation(param) {
  return request({
    url: process.env.ADMIN_API + '/professionIndicatorRelation',
    method: 'post',
    data: param
  })
}

export function editRelation(param) {
  return request({
    url: process.env.ADMIN_API + '/professionIndicatorRelation',
    method: 'put',
    data: param
  })
}

export function deleteRelation(param) {
  return request({
    url: process.env.ADMIN_API + '/professionIndicatorRelation/delete',
    method: 'post',
    data: param
  })
}
