

Page({
    /**
   * 页面的初始数据
   */
   data: {
     name: '',
     password: ''
   },

  // 获取输入账号 
  nameInput: function (e) {
    this.setData({
      name: e.detail.value
    })
  },

  // 获取输入密码 
  passwordInput: function (e) {
    this.setData({
      password: e.detail.value
    })
  },

  // 登录 
  login: function () {
    var _this=this;
    var name=_this.data.name;
    var password=_this.data.password;
    var reqData = "{ 'name': '"+name+"', 'password': '"+password+"'}";
    wx.request({
      //url: 'http://localhost:8080/procePlatform/employee/login.do',
      url: 'http://192.169.0.239:8080/procePlatform/employee/login.do', 
      header: { 'Content-Type': 'application/x-www-form-urlencoded' },
      method: "POST",
      dataType: "json",
      data: { reqData: reqData },
      success: function (res) {
        //console.log(res.data);

        var _code = res.data.result.code;
        if (_code=="0") {
            //将返回的用户信息设置到globalData
             var app = getApp();
             app.globalData.user=res.data.user;
            //登录成功后页面跳转到主页
             wx.switchTab({
              url: '/page/component/message/message'
             });
        } else if (_code == "1") {
          wx.showToast({
            title: '用户名不存在',
          })
        } else if (_code == "2") {
          wx.showToast({
            title: '密码不正确',
          })
        } else {
        }

      },
      fail: function (res) {

      }

    });
  }
})