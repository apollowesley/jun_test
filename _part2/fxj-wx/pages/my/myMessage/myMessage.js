var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    tabIndex: 0,
    tabBars: [
      { name: '未读' },
      { name: '已读' },
      { name: '全部' }
    ]
  },
  onLoad: function (options) {
    console.log(pathUrl)
  },
  clickTab: function (e) {
    var that = this;
    if (this.data.tabIndex === e.currentTarget.dataset.index) {
      return false;
    } else {
      that.setData({
        tabIndex: e.currentTarget.dataset.index
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