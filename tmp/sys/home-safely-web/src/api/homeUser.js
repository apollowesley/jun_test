import request from '@/utils/request'


export function getHomeUserList() {
  return request({
    url: '/web/user',
    method: 'get'
  })
}

export function getHomeDeptList() {
  return request({
    url: '/web/user/deptList',
    method: 'get'
  })
}

export function syncData() {
  return request({
    url: '/web/dingtalk/syncData',
    method: 'get'
  })
}

export function sendHomeMsg() {
  return request({
    url: '/web/dingtalk/mock/sendHomeMsg',
    method: 'get'
  })
}

export function attendanceList() {
  return request({
    url: '/web/dingtalk/attendance',
    method: 'get'
  })
}
