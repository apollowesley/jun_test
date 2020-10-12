//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: '第一个小程序',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    console.log('dsad');
    //打开新页面
    wx.navigateTo({
        url: '/pages/list/list?title=参数'
    })
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
     
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        console.log(app.globalData.userInfo);
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  onShareAppMessage:function(){
    //用户转发
    return {
      title: '自定义转发标题',
      path: 'pages/index/index?id=111'
    }
  },
  onPullDownRefresh:function(){
    //监听用户下拉刷新事件
    console.log('下拉刷新中。。');
    //停止当前页面下拉刷新。
    wx.stopPullDownRefresh();
  },
  onReachBottom:function(){
    //监听用户上拉触底事件
    console.log('监听用户上拉触底事件 在触发距离内滑动期间，本事件只会被触发一次');
  },
  viewTap: function () {
    this.setData({
      text: 'changed data'
    })
    //事件绑定
    console.log('事件绑定 view tap')
  }
})
