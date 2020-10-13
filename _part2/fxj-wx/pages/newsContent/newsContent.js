var app = getApp();
const Utils = require('../../utils/util.js');
const pathUrl = app.globalData.pathUrl;

Page({
  data: {
    noticeId: '',
    noticeTitle: '',
    noticeContent: ''
  },
  onLoad: function (e) {
    var openId = wx.getStorageSync('openId');
    console.log(e)
    this.setData({
      openId: openId,
      noticeId: e.noticeId,
      noticeTitle: e.noticeTitle,
      noticecontent: e.noticecontent
    });
    var that = this;
    // wx.setNavigationBarTitle({
    //   title: that.data.noticeTitle,
    // })
  },
  /* 生命周期函数--监听页面初次渲染完成 */
  onReady: function () { },
  /* 生命周期函数--监听页面隐藏 */
  onHide: function () { },
  /* 生命周期函数--监听页面卸载 */
  onUnload: function () { },
  /* 用户点击右上角分享 */
  onShareAppMessage: function () { }
})