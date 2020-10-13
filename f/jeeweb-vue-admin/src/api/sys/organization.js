import request from '@/utils/request'

export function fetchOrganizationList(query) {
  return request({
    url: '/sys/organization/list',
    method: 'get',
    params: query
  })
}

export function createOrganization(data) {
  return request({
    url: '/sys/organization/add',
    method: 'post',
    data
  })
}

export function updateOrganization(data) {
  return request({
    url: '/sys/organization/' + data.id + '/update',
    method: 'post',
    data
  })
}

export function deleteOrganization(id) {
  return request({
    url: '/sys/organization/' + id + '/delete',
    method: 'post'
  })
}

