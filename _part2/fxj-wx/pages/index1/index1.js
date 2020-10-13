var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    openId: '',
    list: []
  },
  onLoad: function (e) {
    var openId = wx.getStorageSync('openId');
    this.setData({
      openId: openId,
      proName: e.proName
    })
    this.fxjProMainController();
    console.log(e.proName)
  },
  //产品详情
  fxjProMainController: function () {
    var that = this;
    var proName = that.data.proName;
    console.log(proName)
    wx.request({
      url: pathUrl + '/rest/fxjProMainController/list/' + that.data.openId,
      data: {
        pageNumber: 1,
        pageSize: 20,
        proClass: proName
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
    var proNo = e.currentTarget.dataset.prono;
    var proName = e.currentTarget.dataset.proname;
    var proFinanceSum = e.currentTarget.dataset.profinancesum;
    var proClass = e.currentTarget.dataset.proclass;
    var newList1 = e.currentTarget.dataset.newlist1;
    var newList2 = e.currentTarget.dataset.newlist2;
    var newList3 = e.currentTarget.dataset.newlist3;
    var newList4 = e.currentTarget.dataset.newlist4;
    var newList5 = e.currentTarget.dataset.newlist5;
    var newList6 = e.currentTarget.dataset.newlist6;
    var newList7 = e.currentTarget.dataset.newlist7;
    var newList8 = e.currentTarget.dataset.newlist8;
    var newList9 = e.currentTarget.dataset.newlist9;
    var newList10 = e.currentTarget.dataset.newlist10;
    var newList11 = e.currentTarget.dataset.newlist11;
    var newList12 = e.currentTarget.dataset.newlist12;
    var newList13 = e.currentTarget.dataset.newlist13;
    var newList14 = e.currentTarget.dataset.newlist14;
    var newList15 = e.currentTarget.dataset.newlist15;
    var newList16 = e.currentTarget.dataset.newlist16;
    var newList17 = e.currentTarget.dataset.newlist17;
    var newList18 = e.currentTarget.dataset.newlist18;
    var newList19 = e.currentTarget.dataset.newlist19;
    var newList20 = e.currentTarget.dataset.newlist20;
    var newList21 = e.currentTarget.dataset.newlist21;
    var newList22 = e.currentTarget.dataset.newlist22;
    var newList23 = e.currentTarget.dataset.newlist23;
    var newList24 = e.currentTarget.dataset.newlist24;
    var newList25 = e.currentTarget.dataset.newlist25;
    var newList26 = e.currentTarget.dataset.newlist26;

    console.log(e.currentTarget.dataset)
    wx.navigateTo({
      url: "/pages/applyMortgage/applyMortgage?proNo=" + proNo + '&proName=' + proName + '&proClass=' + proClass + '&newList1=' + newList1 + '&newList2=' + newList2 + '&newList3=' + newList3 + '&newList4=' + newList4 + '&newList5=' + newList5 + '&newList6=' + newList6 + '&newList7=' + newList7 + '&newList8=' + newList8 + '&newList9=' + newList9 + '&newList10=' + newList10 + '&newList11=' + newList11 + '&newList12=' + newList12 + '&newList13=' + newList13 + '&newList14=' + newList14 + '&newList15=' + newList15 + '&newList16=' + newList16 + '&newList17=' + newList17 + '&newList18=' + newList18 + '&newList19=' + newList19 + '&newList20=' + newList20 + '&newList21=' + newList21 + '&newList22=' + newList22 + '&newList23=' + newList23 + '&newList24=' + newList24 + '&newList25=' + newList25 + '&newList26=' + newList26
    })

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