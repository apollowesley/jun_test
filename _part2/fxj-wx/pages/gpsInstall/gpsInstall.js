var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        orderNo: '',
        id: ''
    },
    onLoad: function (e) {
        this.setData({
            orderNo: e.orderNo,
            id: e.id
        })
        this.good();
    },
    //拍照上传
  gogpsPhotoUpload: function () {
        wx.navigateTo({
          url: "/pages/gpsPhotoUpload/gpsPhotoUpload?orderNo=" + this.data.orderNo
        })
    },
  //录像
  goCareVideoUpload: function () {
    wx.navigateTo({
      url: "/pages/goCareVideoUpload/goCareVideoUpload?orderNo=" + this.data.orderNo
    })
  },
  
  gogpsinfo: function () {
    wx.navigateTo({
      url: "/pages/gogpsinfo/gogpsinfo?orderNo=" + this.data.orderNo
    })
  },
   good:function(){

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