//app.js
App({
  onLaunch: function () {
    const that = this
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          wx.login({
            success: function (res) {
              if (res.code) {
                //发起网络请求
                wx.request({
                  url: that.url.url+'/user/login?code=' + res.code,
                  success:function(data){
                    if (data.data.code == 200){
                      that.user.status = '1'
                      that.user.token = data.data.data.token
                      that.user.id = data.data.data.user.id
                    }else{
                      wx.navigateTo({
                        url: 'login/login',
                        fail: function () {
                          wx.reLaunch({
                            url: 'pages/index/login/login'
                          })
                        }
                      })
                    }
                  }
                })
              } else {
                wx.navigateTo({
                  url: 'login/login',
                  fail: function () {
                    wx.reLaunch({
                      url: 'pages/index/login/login'
                    })
                  }
                })
              }
            }
          })
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo
              console.log(res.userInfo)
              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        //未登录，跳转到登录界面
        }else{
          wx.navigateTo({
            url: 'login/login',
            fail:function(){
              wx.reLaunch({
                url: 'pages/index/login/login'
              })
            }
         })
        }
      }
    })
  },
  globalData: {
    userInfo: null
  },
  url:{
     url: 'http://118.25.192.171:8082'
     //url: 'http://localhost'
  },
  user: {
    id: '',
    token:'',
    status:0,
    jump:''
  },
  getUser: function () {
    var that = this;
    return new Promise(function (resolve, reject) {
      wx.login({
        success: function (res) {
          if (res.code) {
            //发起网络请求
            wx.request({
              url: that.url.url + '/user/login?code=' + res.code,
              success: function (data) {
                if (data.data.code == 200) {
                  that.user.status = '1'
                  that.user.token = data.data.data.token
                  that.user.id = data.data.data.user.id
                  resolve(that.user);
                }
              }
            })
          }
        }
      })
    });
  },
})