import request from '@/utils/mockRequest'

export function getList(params) {
  return request({
    url: '/table/list',
    method: 'get',
    params
  })
}
