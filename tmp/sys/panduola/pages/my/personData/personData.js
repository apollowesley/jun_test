var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        rawData: [],
        list:[]
    },
    /* 生命周期函数--监听页面加载 */
    onLoad: function (options) {
        var openId = wx.getStorageSync('openId');
        var rawData = wx.getStorageSync('rawData');
        rawData = JSON.parse(rawData);
        this.setData({
            openId: openId,
            rawData: rawData
        })
        this.getuser();
    },
    getuser:function(){
        var that = this;
        wx.request({
            url: pathUrl + '/rest/base/getuser/' + that.data.openId,
            success: function (res) {
                if (res.data.ok) {
                    that.setData({
                        list:res.data.data
                    })
                } else {
                    wx.showModal({
                        title: '提示',
                        content: '未绑定系统用户，请绑定',
                        confirmColor: '#7aa6d1',
                        showCancel: false
                    })
                }
            }
        })
    },
    // 绑定系统用户
    goBindUsers:function(e){
        var username = e.currentTarget.dataset.username;
        wx.navigateTo({
            url: "/pages/my/bindUsers/bindUsers?username=" + username
        })
    },
  // 修改密码
  goBindUserspass: function (e) {
    var username = e.currentTarget.dataset.username;
    wx.navigateTo({
      url: "/pages/my/bindUserspass/bindUserspass?username=" + username
    })
  },
    //退出
    loginOutBtn:function(){
        wx.showModal({
            title: '提示',
            content: '是否确定退出？',
            cancelText: '取消',
            confirmText: '确认',
            confirmColor: '#3c8cf0',
            success: function (res) {
                if (res.confirm) {
                    wx.clearStorageSync();
                    wx.showToast({
                        title: '成功退出',
                        duration: 1000,
                        mask: true,
                        success: function () {
                            setTimeout(function () {
                                wx.navigateTo({
                                    url: "/pages/login/login"
                                })
                            }, 1000);
                        }
                    })
                }
            }
        })
    },
    /* 生命周期函数--监听页面初次渲染完成 */
    onReady: function () {},
    /* 生命周期函数--监听页面显示 */
    onShow: function () {},
    /* 生命周期函数--监听页面隐藏 */
    onHide: function () {},
    /* 生命周期函数--监听页面卸载 */
    onUnload: function () {},
    /* 页面相关事件处理函数--监听用户下拉动作 */
    onPullDownRefresh: function () {},
    /* 页面上拉触底事件的处理函数 */
    onReachBottom: function () {},
    /* 用户点击右上角分享 */
    onShareAppMessage: function () {}
})