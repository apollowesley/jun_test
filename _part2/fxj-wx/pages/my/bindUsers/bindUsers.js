var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        userName:'',
        userPassword:''
    },
    onLoad: function (e) {
        var openId = wx.getStorageSync('openId');
      console.log("openId***************" + openId);
        this.setData({
            openId: openId,
            userName: e.username
        })
    },
    //姓名赋值
    userNameInput: function (e) {
        this.setData({
            userName: e.detail.value
        })
    },
    //密码赋值
    userPasswordInput: function (e) {
        this.setData({
            userPassword: e.detail.value
        })
    },
    //提交
    sbumitBtn:function(){
        var that = this;
        var openid = that.data.openId;
        var username = that.data.userName;
        var password = that.data.userPassword;
        wx.request({
            url: pathUrl + '/rest/tokens/otherloginsave?openid=' + openid + '&username=' + username + '&password=' + password,
            method:'POST',
            success: function (res) {
                console.log("1212121212****"+JSON.stringify(res));
                if (res.data.ok) {
                    wx.showToast({
                        title: '绑定成功',
                        duration: 1000,
                        mask: true,
                        success: function () {
                            setTimeout(function () {
                                wx.switchTab({
                                    url: "/pages/my/person/person"
                                })
                            }, 1000);
                        }
                    })
                } else {
                    wx.showModal({
                        title: '提示',
                        content: res.data.message,
                        confirmColor: '#7aa6d1',
                        showCancel: false
                    })
                }
            }
        })
    },

  ykumitBtn: function () {
    var that = this;
    var openid = that.data.openId;
    var username = that.data.userName;
    var password = that.data.userPassword;
    wx.request({
      url: pathUrl + '/rest/tokens/otherloginsave?openid=' + openid + '&username=guest&password=guest',
      method: 'POST',
      success: function (res) {
        console.log("1212121212****" + JSON.stringify(res));
        if (res.data.ok) {
          wx.showToast({
            title: '绑定成功',
            duration: 1000,
            mask: true,
            success: function () {
              setTimeout(function () {
                wx.switchTab({
                  url: "/pages/my/person/person"
                })
              }, 1000);
            }
          })
        } else {
          wx.showModal({
            title: '提示',
            content: res.data.message,
            confirmColor: '#7aa6d1',
            showCancel: false
          })
        }
      }
    })
  },
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})