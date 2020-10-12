import request from '@/utils/request'

export function getIndicatorGradeList(params) {
  return request({
    url: process.env.ADMIN_API + '/indicatorGrade/getList',
    method: 'get',
    params
  })
}

export function addIndicatorGrade(param) {
  return request({
    url: process.env.ADMIN_API + '/indicatorGrade',
    method: 'post',
    data: param
  })
}

export function editIndicatorGrade(param) {
  return request({
    url: process.env.ADMIN_API + '/indicatorGrade',
    method: 'put',
    data: param
  })
}

export function deleteIndicatorGrade(param) {
  return request({
    url: process.env.ADMIN_API + '/indicatorGrade/delete',
    method: 'post',
    data: param
  })
}
