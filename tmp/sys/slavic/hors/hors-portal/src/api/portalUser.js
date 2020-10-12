import request from '../utils/request'

export const login = (data) => {
  return request({
    url: 'hors/portal/user/login',
    method: 'post',
    params: data
  })
}

export const userResources = () => {
  return request({
    url: 'hors/portal/user/userResources',
    method: 'post'
  })
}

export const list = (data) => {
  return request({
    url: 'hors/portal/user/list',
    method: 'post',
    params: data
  })
}

export const deleteUser = (data) => {
  return request({
    url: 'hors/portal/user/delete',
    method: 'post',
    params: data
  })
}

export const add = (data) => {
  return request({
    url: 'hors/portal/user/add',
    method: 'post',
    params: data
  })
}

export const update = (data) => {
  return request({
    url: 'hors/portal/user/update',
    method: 'post',
    params: data
  })
}

export const roles = (data) => {
  return request({
    url: 'hors/portal/user/roles',
    method: 'post',
    params: data
  })
}

export const reRoles = (data) => {
  return request({
    url: 'hors/portal/user/reRoles',
    method: 'post',
    data
  })
}

export const userDetail = () => {
  return request({
    url: 'hors/portal/user/detail',
    method: 'post'
  })
}

export const loginRecords = () => {
  return request({
    url: 'hors/portal/user/loginRecords',
    method: 'post'
  })
}