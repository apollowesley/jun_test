import request from '@/utils/request'

export function getIndicatorGradeReferenceList(params) {
  return request({
    url: process.env.ADMIN_API + '/indicatorGradeReference/getList',
    method: 'get',
    params
  })
}

export function addIndicatorGradeReference(param) {
  return request({
    url: process.env.ADMIN_API + '/indicatorGradeReference',
    method: 'post',
    data: param
  })
}

export function editIndicatorGradeReference(param) {
  return request({
    url: process.env.ADMIN_API + '/indicatorGradeReference',
    method: 'put',
    data: param
  })
}

export function deleteIndicatorGradeReference(param) {
  return request({
    url: process.env.ADMIN_API + '/indicatorGradeReference/delete',
    method: 'post',
    data: param
  })
}
