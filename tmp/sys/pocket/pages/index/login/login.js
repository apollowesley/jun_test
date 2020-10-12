//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: '体验功能需要授权的哟~'
  },
  onLoad: function () {
  },
  getUserInfo: function (e) {

    if (e.detail.userInfo != undefined){
      wx.login({
        success: function (res) {
          if (res.code) {
            //发起网络请求
            wx.request({
              url: app.url.url +'/user/authorization?code=' + res.code,
              method: 'POST',
              success: function (data) {
                if (data.data.code == 200){
                  app.user.token = data.data.data.token
                  app.user.id = data.data.data.user.id
                  app.user.status = 1
                  wx.showToast({
                    title: '授权成功',
                    icon: 'success',
                    duration: 1500
                  })
                  setTimeout(function () {
                    wx.reLaunch({
                      url: '../../index/index'
                    })
                  }, 1500)
                } else{
                  wx.showToast({
                    title: '授权失败，请联系管理员',
                    icon: 'none',
                    duration: 1500
                  })
                }
              }
            })
          } else {
            wx.showToast({
              title: '授权失败，请联系管理员',
              icon: 'none',
              duration: 1500
            })
          }
        }
      })
    }else{
      wx.showModal({
        title: '用户未授权',
        content: '如需正常使用小程序，请点击登录按钮重新授权，勾选用户信息并点击确定。',
        showCancel: false
      })
    }
    app.globalData.userInfo = e.detail.userInfo
  }
})