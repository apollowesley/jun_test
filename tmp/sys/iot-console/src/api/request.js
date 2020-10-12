import axios from 'axios'
// import { Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import qs from 'qs'
import { Message, MessageBox } from 'element-ui'
// create an axios instance
axios.defaults.withCredentials = true;
axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
const service = axios.create({
  // baseURL: process.env.BASE_API, // api的base_url
  timeout: 8000, // request timeout
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded',
    'Admin-Token': getToken(),//token从全局变量那里传过来
  },
  // paramsSerializer: function(params) {
  //   return qs.stringify(params, {arrayFormat: 'repeat',allowDots: true})
  // }
})

// // request interceptor
// service.interceptors.request.use(config => {
//   // Do something before request is sent
//   if (getToken()) {
//     // 让每个请求携带token-- ['X-Token']为自定义key 请根据实际情况自行修改
//     config.headers['X-Token'] = getToken()
    
//   }
//   return config
// }, error => {
//   // Do something with request error
//   Promise.reject(error)
// })

// respone interceptor
service.interceptors.response.use(
  
  /**
   * 下面的注释为通过在response里，自定义code来标示请求状态
   * 当code返回如下情况则说明权限有问题，登出并返回到登录页
   * 如想通过xmlhttprequest来状态码标识 逻辑可写在下面error中
   * 以下代码均为样例，请结合自生需求加以修改，若不需要，则可删除
   */
  response => {
    const res = response.data
    if(res.code!==200||res.msg){
      console.log('res.code进入这里')
      Message({
        message: res.msg,
        type: 'error',
        duration: 5 * 1000
      })
    }
    if (response.status !== 200) {
      Message({
        message: res.message,
        type: 'error',
        duration: 5 * 1000
      })
      // 10006:Token 过期了;
      if (res.code === 10006 || res.code === 5001 || res.code === 5002) {
        // 请自行在引入 MessageBox
        Message({
          message: res.msg,
          type: 'error',
          duration: 5 * 1000
        })
        MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('FedLogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      }
      console.log(res.code)
      
      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 2000
    })
    return Promise.reject(error)
  })
function http(config){
  if(config.method.toLowerCase() === 'post'){
    config.data = qs.stringify(config.data,{arrayFormat: 'repeat',allowDots: true});
  }else{
    config.params = config.data;
  }
  return service(config);
}

export default http;
// export default service
