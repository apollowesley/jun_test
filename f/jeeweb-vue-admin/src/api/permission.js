import request from '@/utils/request'

export function fetchPermissionList(query) {
  return request({
    url: '/sys/menu/getPermissions',
    method: 'get',
    params: query
  })
}
