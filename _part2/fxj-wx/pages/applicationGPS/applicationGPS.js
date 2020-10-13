var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    openId: '',
    array: [],
    index: 0,
    // msgtype: '',
    // msgcontent: '',
    // cusName: '',
    // cusMobile: '',
    list:'',
    // caraddress:'',
  },
  
  onLoad: function (options) {
    var openId = wx.getStorageSync('openId');
    this.setData({
      openId: openId,
      orderNo: options.orderNo,

    })
    this.theme();
    this.getInfoByorderNo();
  },
  //获取初始值
  getInfoByorderNo:function(){
    var that = this ;
    wx.request({
      url: pathUrl + '/rest/fxjLoanGpsController/' + that.data.orderNo,
      
      success: function (res) {
        that.setData({
          list:res.data.data,
        })
        console.log(res.data.data)
      }
      
    }) 
    
  },

  theme: function () {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/base/getDictValue/gpsazy',
      success: function (res) {
        console.log(res.data)
        if (res.data.ok || res.data.data) {
          var newArray = [];
          for (var i = 0; i < res.data.data.length; i++) {
            newArray.push(
              res.data.data[i].typename
            );
          }
          that.setData({
            array: newArray
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
  bindPickerChange: function (e) {
    var that = this;
    var index = e.detail.value;
    that.setData({
      index: index,
      msgtype: that.data.array[index]
    })
  },
  // installNameInput: function (e) {
  //   this.setData({
  //     installName: e.detail.value
  //   })
  // },
  cusNameInput: function (e) {
    var that = this;
    var cusName = that.data.list.cusName;
    this.setData({
      cusName: e.detail.value
    })
  },
  cusMobileInput: function (e) {
    var that = this;
    var cusMobile = that.data.list.gpsMobile;
    this.setData({
      cusMobile: e.detail.value
    })
  },
  msgcontentInput: function (e) {
    var that = this;
    var msgcontent = that.data.list.gpsType;
    this.setData({
      msgcontent: e.detail.value
    })
  },
  cartypeInput: function (e) {
    var that = this;
    var cartype = that.data.list.carType
    this.setData({
      cartype: e.detail.value
    })
  },
  caraddressInput: function (e) {
    var that = this;
    var caraddress = that.data.list.gpsAddr
    this.setData({
      caraddress: e.detail.value
    })
  },
  
  submitBtn: function (e) {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/fxjLoanGpsController/gpsapp/' + that.data.list.id,
      data: {
        installUser: that.data.msgtype,
        gpsType: that.data.msgcontent,
        gpsMobile: that.data.cusMobile,
        cusName: that.data.cusName,
        // installUser: that.data.installName,
        orderNo: that.data.list.orderNo,
        gpsAddr: that.data.caraddress,
        carType: that.data.cartype,
        id: that.data.list.id,
      },
      
      method: 'PUT',
      success: function (res) {
        console.log(res.data)
        if (res.data.ok) {
          wx.showToast({
            title: '提交成功',
            duration: 500,
            mask: true,
            success: function () {
              setTimeout(function () {
                wx.navigateBack({
                  delta: 1
                })
              }, 500);
            }
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