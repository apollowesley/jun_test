import request from '@/utils/request'

export function fetchMenuList(query) {
  return request({
    url: '/sys/menu/getMenus',
    method: 'get',
    params: query
  })
}

export function fetchNoAuthList(query) {
  return request({
    url: '/sys/menu/getNoAuthMenus',
    method: 'get',
    params: query
  })
}

export function fetchAuthList(query) {
  return request({
    url: '/sys/menu/getAuthMenus',
    method: 'get',
    params: query
  })
}
