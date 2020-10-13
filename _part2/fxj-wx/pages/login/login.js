var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        canIUse: wx.canIUse('button.open-type.getUserInfo')
    },
    onLoad: function() {
        // var openId = wx.getStorageSync('openId');
        // wx.getSetting({
        //     success: function(res) {
        //         if (res.authSetting['scope.userInfo']) {
        //             if (openId) {
        //                 wx.switchTab({
        //                     url: "/pages/index/index"
        //                 })
        //             }
        //         }
        //     }
        // })
    },
    onGotUserInfo: function(info) {
        var that = this;
        console.log(info)
        if (info.detail.userInfo) {
            wx.login({
                success: res => {
                    if (res.code) {
                        var code = res.code;
                        //var encryptedData = (info.detail.encryptedData);
                        //var iv = info.detail.iv;
                        var rawData = info.detail.rawData;
                        //var signature = info.detail.signature;
                        that.login_test(code, rawData);
                    } else {
                        console.log('获取用户登录态失败！' + res.errMsg)
                    }
                }
            })
        } else {
            wx.showModal({
                title: '温馨提示',
                content: '为保证您的正常使用，请进行授权!',
                showCancel: false,
                confirmText: '返回授权',
                success: function(res) {
                    if (res.confirm) {
                        console.log('用户点击了“返回授权”')
                    }
                }
            })
        }
    },
  authUri: '/rest/tokens/authv3',
  login_test: function (code, rawData) {
    wx.request({
      url: pathUrl + this.authUri,
      data: {
        JSCODE: code,
        appCode: 'posshop'
      },
      success: function (res) {
        console.log("data*******" + JSON.stringify(res));
        console.log("JSCODE*******" + code);
        console.log(res);
        // 设置缓存
        wx.setStorageSync('openId', res.data.data);
        wx.setStorageSync('rawData', rawData);
        if (res.data.total == 1) {
          wx.switchTab({
            url: "/pages/index/index"
          })
        } else {
          wx.switchTab({
            url: "/pages/index/index"
          })
          // wx.navigateTo({
          //   url: "/pages/my/bindUsers/bindUsers?username="
          // })
        }
      }

    })
  },
    onReady: function() {

    },
    onShow: function() {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
})