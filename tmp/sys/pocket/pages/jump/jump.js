// pages/plus/jump/jump.js
const app = getApp()
Page({
  data: {
    num: 0,
  },
  onLoad: function () { },
  onShow: function () {
    this.data.num++;
    var jump = app.user.jump
    if (this.data.num % 2 == 0) {
      wx.switchTab({
          url: jump
      });
    } else {
      var state = app.user.status
      if (state == 1){
        wx.navigateTo({
          url: '../plus/plus'
        })
      }else{
        wx.navigateTo({
          url: '../index/login/login'
        })
      }
    }
  }
})