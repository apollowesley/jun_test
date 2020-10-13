import request from '@/utils/request'

export function fetchMenuList(query) {
  return request({
    url: '/sys/menu/list',
    method: 'get',
    params: query
  })
}

export function createMenu(data) {
  return request({
    url: '/sys/menu/add',
    method: 'post',
    data
  })
}

export function changeSort(rowId, value) {
  return request({
    url: '/sys/menu/' + rowId + '/changeSort',
    method: 'post',
    data: { sort: value }
  })
}

export function updateMenu(data) {
  return request({
    url: '/sys/menu/' + data.id + '/update',
    method: 'post',
    data
  })
}

export function deleteMenu(id) {
  return request({
    url: '/sys/menu/' + id + '/delete',
    method: 'post'
  })
}

export function generateButton(data) {
  return request({
    url: '/sys/menu/' + data.menuId + '/generate/button',
    method: 'post',
    data
  })
}

