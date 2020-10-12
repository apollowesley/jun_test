var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        rawData: [],
        list :[]
    },
    onLoad: function (options) {
        var openId = wx.getStorageSync('openId');
        var rawData = wx.getStorageSync('rawData');
        rawData = JSON.parse(rawData);
        this.setData({
            openId: openId,
            rawData: rawData
        })
        this.getbi();
    },
    getbi:function(){
        var that = this;
        wx.request({
          // url: pathUrl + '/rest/posXjtaskController/gettj/' + that.data.openId,
          url: pathUrl + '/rest/posXjtaskController/gettj/admin',
          success: function (res) {
              console.log(res.data)
              if (res.data.ok) {
                      that.setData({
                          list:res.data.data,
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
        this.getbi();
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