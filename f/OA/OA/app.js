App({

  globalData: {
    hasLogin: false,
    sessionKey: null,
    appid: 'wxd0e6ae0dc1a02b00',
    key: '723f2018ca42d8e686e4c90ee3c36c77',
    access_token: null,
    expires_in: null,
    lastTime: 0,

    userInfo: null,
    user: {
      sap: null,
      name: null,
      sex: null,
      wxid: null,
      department: null,
      post: null,
      mobile: null,
    },
  },
  //监听小程序初始化，初始化完成时会触发onLaunch，全局只触发一次
  // onLaunch: function () {
  //   var that=this;
  //   wx.getUserInfo({
  //     success: function (res) {
  //       that.globalData.userInfo=res.userInfo
  //     }
  //   });
  // },
   onLaunch: function () {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
   },
  getUserInfo: function (cb) {
    var that = this
    if (this.globalData.userInfo) {
      typeof cb == "function" && cb(this.globalData.userInfo)
    } else {
      //调用登录接口
      wx.login({
        success: function () {
          wx.getUserInfo({
            success: function (res) {
              that.globalData.userInfo = res.userInfo
              typeof cb == "function" && cb(that.globalData.userInfo)
            }
          })
        }
      })
    }
  },

  //监听小程序显示
  onShow: function () {console.log('App Show')},
  //监听小程序隐藏
  onHide: function () { console.log('App Hide') },
  //全局数据变量

  
})
