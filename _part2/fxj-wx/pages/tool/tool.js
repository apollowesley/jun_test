var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        console.log(pathUrl)
    },
    goWebUrl: function () {
        wx.navigateTo({
          url: "/pages/toolskc/toolskc"
        })
    },
  navigateToMiniProgram() {
    wx.navigateToMiniProgram({
      appId: 'wx3cbba435621590ab',
      path: 'pages/index/index',
      extraData: {
        from: 'xxxxx'
      },
      envVersion: 'develop',
      success(res) {
        // 打开其他小程序成功同步触发
        wx.showToast({
          title: '跳转成功'
        })
      }
    })
  },
  godkjs: function () {
    wx.navigateTo({
      url: "/pages/godkjs/godkjs"
    })
  },
  goche300: function () {
    wx.navigateTo({
      url: "/pages/goche300/goche300"
    })
  },
  gosxbzx: function () {
    wx.navigateTo({
      url: "/pages/gosxbzx/gosxbzx"
    })
  },
  goclpg: function () {
    wx.navigateTo({
      url: "/pages/goWebUrl/goWebUrl"
    })
  },
  goGps: function () {
    wx.navigateTo({
      url: "/pages/myGps/myGps"
    })
  },
  gogpsapp: function () {
    wx.navigateTo({
      url: "/pages/ordergpsappManagement/ordergpsappManagement"
    })
  },
  gofkapp: function () {
    wx.navigateTo({
      url: "/pages/orderfkappManagement/orderfkappManagement"
    })
  },
  gobankcard:function(){
    wx.navigateTo({
      url: '/pages/ordergobankcardManagement/ordergobankcardManagement',
    })
  },
  godataquery:function(){
    wx.navigateTo({
      url: '/pages/ordergodataqueryManagement/ordergodataqueryManagement',
    })
  },
  goanotherapp: function () {
    wx.navigateTo({
      url: '/pages/goanotherapp/goanotherapp',
    })
  },
  goDiya: function () {
    wx.navigateTo({
      url: "/pages/myDiya/myDiya"
    })
  },
  goGuohu: function () {
    wx.navigateTo({
      url: "/pages/myGuohu/myGuohu"
    })
  },
  gonews: function () {
    wx.navigateTo({
      url: "/pages/news/news"
    })
  },
  gojsq: function () {
    wx.navigateTo({
      url: "/pages/gojsq/jsq"
    })
  }, 
    gocalcul: function () {
        wx.navigateTo({
            url: "/pages/goWebUrl/goWebUrl"
        })
    },
    gomq: function () {
        wx.navigateTo({
            url: "/pages/meetlogin/meetlogin"
        })
    },
  goyhmq: function () {
    wx.navigateTo({
      url: "/pages/yhManagement/yhManagement"
    })
  },
  goqdmq: function () {
    wx.navigateTo({
      url: "/pages/ordermqManagement/ordermqManagement"
    })
  },
  gomymq: function () {
    wx.navigateTo({
      url: "/pages/ordermymqManagement/ordermymqManagement"
    })
  },
  gojiafang: function () {
    wx.navigateTo({
      url: "/pages/orderjfManagement/orderjfManagement"
    })
  },
    //违章查询
    goviolationEnquiry: function () {
        wx.navigateTo({
            url: "/pages/violationEnquiry/violationEnquiry"
        })
    },
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
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