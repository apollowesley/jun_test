//app.js
const Utils = require("./utils/util.js");
App({
  onLaunch: function () {
    // // 展示本地存储能力
    Utils.checkSystemInfo(this);
  },
  globalData: {
    userInfo: null,
    pathUrl: 'https://www.zhaodui.com.cn/fxj380'
  }
})