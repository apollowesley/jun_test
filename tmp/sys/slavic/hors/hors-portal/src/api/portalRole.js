import request from '../utils/request'

export const list = (data) => {
  return request({
    url: 'hors/portal/role/list',
    method: 'post',
    params: data
  })
}

export const add = (data) => {
  return request({
    url: 'hors/portal/role/add',
    method: 'post',
    params: data
  })
}

export const update = (data) => {
  return request({
    url: 'hors/portal/role/update',
    method: 'post',
    params: data
  })
}

export const deleteRole = (data) => {
  return request({
    url: 'hors/portal/role/delete',
    method: 'post',
    params: data
  })
}

export const viewUsers = (data) => {
  return request({
    url: 'hors/portal/role/viewUsers',
    method: 'post',
    params: data
  })
}

export const resourceList = (data) => {
  return request({
    url: 'hors/portal/resource/list',
    method: 'post',
    params: data
  })
}

export const reResources = (data) => {
  return request({
    url: 'hors/portal/role/reResources',
    method: 'post',
    data
  })
}