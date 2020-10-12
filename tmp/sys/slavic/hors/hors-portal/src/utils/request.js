import axios from 'axios'
import {
  MessageBox,
  Message
} from 'element-ui'
import {
  getToken,
  removeToken
} from '../utils/token'

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 10000
})

service.interceptors.request.use(
  config => {
    config.headers['token'] = getToken()
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.header.code != '200') {
      Message({
        message: res.header.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      if (res.header.code == '305') {
        MessageBox.confirm('登录态失效,请重新登录', '登录提示', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'error'
        }).then(() => {
          removeToken().then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res.body
    }
  },
  error => {
    console.log('err' + error)
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service