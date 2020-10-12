import request from '@/utils/request'

export function getIndicatorList(params) {
  return request({
    url: process.env.ADMIN_API + '/indicator/getlist',
    method: 'get',
    params
  })
}

export function addIndicator(param) {
  return request({
    url: process.env.ADMIN_API + '/indicator',
    method: 'post',
    data: param
  })
}

export function editIndicator(param) {
  return request({
    url: process.env.ADMIN_API + '/indicator',
    method: 'put',
    data: param
  })
}

export function deleteIndicator(param) {
  return request({
    url: process.env.ADMIN_API + '/indicator/delete',
    method: 'post',
    data: param
  })
}

export function getIndicatorCount(param) {
  return request({
    url: process.env.ADMIN_API + '/indicator/getcount',
    method: 'post',
    data: param
  })
}
