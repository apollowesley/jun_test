var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    openId: '',
    list: []
  },
  onLoad: function (options) {
    var openId = wx.getStorageSync('openId');
    this.setData({
      openId: openId
    })
    this.fxjProMainController();
  },
  //产品详情
  fxjProMainController: function () {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/fxjProMainController/list/' + that.data.openId,
      data: {
        pageNumber: 1,
        pageSize: 20
      },
      success: function (res) {
        if (res.data.ok || res.data.data) {
          that.setData({
            list: res.data.data
          })
        } else {
          wx.showModal({
            title: '提示',
            content: '获取数据失败',
            confirmColor: '#7aa6d1',
            showCancel: false
          })
        }
      }
    })
  },

  goApplyMortgage: function (e) {
    var proName = e.currentTarget.dataset.proname;
    var proNo =e.currentTarget.dataset.prono;
    console.log(e.currentTarget.dataset)
    if (proNo == 10){
      wx.navigateTo({
        url: "/pages/applyMortgage/applyMortgage?proNo=" + proNo + '&proName=' + proName
      })
    }
    if (proNo == 11) {
      wx.navigateTo({
        url: "/pages/goCredit/goCredit?proNo=" + proNo + '&proName=' + proName
      })
    }

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
    this.fxjProMainController();
    wx.stopPullDownRefresh();
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