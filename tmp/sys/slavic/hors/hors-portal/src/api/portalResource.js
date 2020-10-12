import request from '../utils/request'

export const list = (data) => {
  return request({
    url: 'hors/portal/resource/list',
    method: 'post',
    params: data
  })
}