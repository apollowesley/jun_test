var app = getApp();
const Utils = require('../../utils/util.js');
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pageHidden: true,
    pathUrl: pathUrl,
    orderStatusType: 0,
    list: [],
    pageNumber: 0,
    noticeContent: ''
  },
  onLoad: function (options) {
    var openId = wx.getStorageSync('openId');
    this.setData({
      openId: openId
    });
    this.getNotice();
  },
  // 获取消息
  getNotice: function (e) {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/base/getNotice/' + that.data.openId,
      success: function (res) {
        console.log(res)
        if (res.data.ok || res.data.data) {
          that.setData({
            list: res.data.data,
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
    });

  },
  // 查看
  viewNotice: function (e) {
    console.log(e)
    var noticeId = e.currentTarget.dataset.id;
    var noticecontent = e.currentTarget.dataset.noticecontent;
    var noticeTitle = e.currentTarget.dataset.noticetitle;
    var str = e.currentTarget.dataset.noticecontent //接收客户输入的内容
    str = str.substring(str.lastIndexOf(">"));
    console.log(str)
    wx.navigateTo({
      url: '/pages/newsContent/newsContent?noticeId=' + noticeId + '&noticeTitle=' + noticeTitle + '&noticecontent=' + noticecontent,
    })
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