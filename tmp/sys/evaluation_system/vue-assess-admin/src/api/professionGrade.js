import request from '@/utils/request'

export function getProfessionGradeList(params) {
  return request({
    url: process.env.ADMIN_API + '/professionGrade/getList',
    method: 'get',
    params
  })
}

export function getProfessionGradeCount(param) {
  return request({
    url: process.env.ADMIN_API + '/professionGrade/getcount',
    method: 'post',
    data: param
  })
}
