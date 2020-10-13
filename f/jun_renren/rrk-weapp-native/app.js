const util = require('./utils/util.js')
const api = require('./config/api.js')
let visitor = { UserId: '360001', MobilePhone: '', UserName: '游客', Grade: '1' }

App({

  // 小程序启动
  onLaunch() {
    // 存储设备信息
    wx.getSystemInfo({
      success: (res) => {
        wx.setStorageSync('systemInfo', res)
      }
    })

    //检查登录状态
    util.checkSession().then(res => {
      console.log(this.data.visitor)
      this.globalData.userInfo = wx.getStorageSync('userInfo') || visitor
    }).catch(err => {
      // 登录
      wx.login({
        success: res => {
          console.log(res)
          util.request(api.userLogin, {code: res.code}).then(res => {
            //授权成功
            console.log(res)
            if (res.code === '0') {
              this.globalData.userInfo = res.body
              wx.setStorageSync('userInfo', res.body)
              if (this.loginCallback) {
                this.loginCallback(res.body)
              }
            } else {
              console.log('error')
              this.globalData.userInfo = visitor
              wx.setStorageSync('userInfo', visitor)
              if (this.loginCallback) {
                this.loginCallback(visitor)
              }
              console.log('游客，您好')
            }
          })
        },
        error: res => {
          // util.error('微信登录失败')
          console.log('微信登录失败')
        }
      })
    })
  },
  // 存储推荐码
  onShow(options) {
    console.log(options)
    // 先获取并存储推荐码
    if (options.query.shareUserId && options.query.shareUserId !== '') {
      wx.setStorageSync('shareUserId', options.query.shareUserId)
    }
    // 识别二维码方式打开小程序
    if ((options.scene === 1011 || options.scene === 1012 || options.scene === 1013) && options.query.q) {
      let params = this.decodeURL(options.query.q)
      if (params.shareUserId) {
        wx.setStorageSync('shareUserId', params.shareUserId)
      }
      // 存放扫码数据
      wx.setStorageSync('qrcodeInfo', params)
    }
  },
  decodeURL(str) {
    // 取出完整url
    let url = decodeURIComponent(str)
    // 取出参数
    let params = url.split('?')
    let reg = /([^=&\s]+)[=\s]*([^&\s]*)/g
    let obj = {}
    while (reg.exec(params[1])) {
      obj[RegExp.$1] = RegExp.$2
    }
    return obj
  },
  globalData: {
    userInfo: {}
  }
})