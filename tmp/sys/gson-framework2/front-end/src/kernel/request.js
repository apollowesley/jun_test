/**
 *<p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : </li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月29日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
import axios from 'axios'
import Qs from 'qs'
import kernel from './kernel'

const instance = axios.create()

instance.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
instance.defaults.headers['X-Requested-With'] = 'XMLHttpRequest'

instance.interceptors.request.use(function(config) {
  let token = kernel.token()
  if (token) {
    config.headers['authorization'] = token
  }
  return config
}, function(error) {
  return Promise.reject(error)
})

const request = (config) => {
  return new Promise((resolve, reject) => {
    HeyUI.$LoadingBar.start()
    instance.request(Object.assign({
      baseURL: '/api',
    }, config)).then(response => {
      if (response.headers['authorization']) {
        kernel.saveToken(response.headers['authorization'])
      }
      HeyUI.$LoadingBar.success()
      resolve(response.data)
    }).catch(err => {
        let {response, message} = err
        if (response) {
          switch (response.status) {
            case 403:
              kernel.clearToken()
              break
          }
        }
        HeyUI.$LoadingBar.fail()
        reject(message)
      }
    )
  })
}

const transformRequest = (data, headers) => {
  return Qs.stringify(data, {allowDots: true})
}

export default {
  get(url, params = {}) {
    return request({
      url,
      params,
      method: 'get'
    })
  },
  post(url, data = {}) {
    return request({
      url,
      data,
      method: 'post',
      transformRequest: transformRequest
    })
  },
  delete(url, params = {}) {
    return request({
      url,
      params,
      method: 'delete'
    })
  },
  upload(url, data = {}, onUploadProgress) {
    return request({
      url,
      data,
      method: 'post',
      headers: {'Content-Type': 'multipart/form-data'},
      transformRequest: transformRequest,
      onUploadProgress: onUploadProgress
    })
  },
  download(url, params, onDownloadProgress) {
    return request({
      url,
      params,
      method: 'get',
      responseType: 'arraybuffer',
      onDownloadProgress: onDownloadProgress
    })
  },
  put(url, data = {}) {
    return request({
      url,
      data,
      method: 'put',
      transformRequest: transformRequest
    })
  }
}
