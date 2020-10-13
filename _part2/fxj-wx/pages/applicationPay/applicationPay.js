var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    openId: '',
    id1:'',
    fkBy11: '',
    fkBy21: '',
    fkBy31: '',
    fkAmount1: '',
    id2: '',
    fkBy12: '',
    fkBy22: '',
    fkBy32: '',
    fkAmount2: '',
    id3: '',
    fkBy13: '',
    fkBy23: '',
    fkBy33: '',
    fkAmount3: '',
    id4: '',
    fkBy14: '',
    fkBy24: '',
    fkBy34: '',
    fkAmount4: '',
    // array: [],
    // index: 0,
    // msgtype: '咨询',
    // msgcontent: '',
    // cusName: '',
    // cusMobile: ''
  
    
  },

  onLoad: function (options) {
    var openId = wx.getStorageSync('openId');
    this.setData({
      openId: openId,
      orderNo:options.orderNo
    })
    // this.theme();
    // this.info();
    this.carAmountByOrderNo();
    
  },
  carAmountByOrderNo:function(){
    var that = this ;
    var orderNo = that.data.orderNo
    wx.request({
      url: pathUrl + '/rest/fxjLoanFkController/getbyorderNo/' + orderNo,
      data:{
        pageNumber:1,
        pageSize:10,
        // fkMethod:"车款",
      },
      method: 'GET',
      success: function (res) {

      //  res.data.data;
      for(var i=0;i<res.data.total;i++){
        if(i==0){
          console.log("res.data.data[i]=" + JSON.stringify(res.data.data[i]));
          that.setData({
            id1: res.data.data[i].id,
            fkBy11: res.data.data[i].fkBy1,
            fkBy21: res.data.data[i].fkBy2,
            fkBy31: res.data.data[i].fkBy3,
            fkAmount1: res.data.data[i].fkAmount

          })
          
        }
        if (i == 1) {
          that.setData({
            id2: res.data.data[i].id,
            fkBy12: res.data.data[i].fkBy1,
            fkBy22: res.data.data[i].fkBy2,
            fkBy32: res.data.data[i].fkBy3,
            fkAmount2: res.data.data[i].fkAmount

          })
        }
        if (i == 2) {
          that.setData({
            id3: res.data.data[i].id,
            fkBy13: res.data.data[i].fkBy1,
            fkBy23: res.data.data[i].fkBy2,
            fkBy33: res.data.data[i].fkBy3,
            fkAmount3: res.data.data[i].fkAmount

          })
        }
        if (i == 3) {
          that.setData({
            id4: res.data.data[i].id,
            fkBy14: res.data.data[i].fkBy1,
            fkBy24: res.data.data[i].fkBy2,
            fkBy34: res.data.data[i].fkBy3,
            fkAmount4: res.data.data[i].fkAmount

          })
        }

      }     
       }
      
    })
    
  },
  fkBy11Input: function (e) {
    var that = this;
    var fkBy11 = that.data.fkBy11;
    this.setData({
      fkBy11: e.detail.value
    })
  },
  fkBy21Input: function (e) {
    var that = this;
    var fkBy21 = that.data.fkBy21;

    this.setData({
      fkBy21: e.detail.value
    })
  },
  fkBy31Input: function (e) {
    var that = this;
    var fkBy31 = that.data.fkBy31;

    this.setData({
      fkBy31: e.detail.value
    })
  },
  fkAmount1Input: function (e) {
    var that = this;
   
    this.setData({
      fkAmount1: e.detail.value
    })
  },


  fkBy12Input: function (e) {
    var that = this;

    this.setData({
      fkBy12: e.detail.value
    })
  },
  fkBy22Input: function (e) {
    var that = this;

    this.setData({
      fkBy22: e.detail.value
    })
  },
  fkBy32Input: function (e) {
    var that = this;

    this.setData({
      fkBy32: e.detail.value
    })
  },
  fkAmount2Input: function (e) {
    var that = this;

    this.setData({
      fkAmount2: e.detail.value
    })
  },

  fkBy13Input: function (e) {
    var that = this;

    this.setData({
      fkBy13: e.detail.value
    })
  },
  fkBy23Input: function (e) {
    var that = this;

    this.setData({
      fkBy23: e.detail.value
    })
  },
  fkBy33Input: function (e) {
    var that = this;

    this.setData({
      fkBy33: e.detail.value
    })
  },
  fkAmount3Input: function (e) {
    var that = this;

    this.setData({
      fkAmount3: e.detail.value
    })
  },


  fkBy14Input: function (e) {
    var that = this;

    this.setData({
      fkBy14: e.detail.value
    })
  },
  fkBy24Input: function (e) {
    var that = this;

    this.setData({
      fkBy24: e.detail.value
    })
  },
  fkBy34Input: function (e) {
    var that = this;

    this.setData({
      fkBy34: e.detail.value
    })
  },
  fkAmount4Input: function (e) {
    var that = this;

    this.setData({
      fkAmount4: e.detail.value
    })
  },


  submitBtn: function () {
    var that = this;
    console.log("that.data.id1"+that.data.id1)
    console.log("that.data.id2" + that.data.id2)
    console.log("that.data.id3" + that.data.id3)
    console.log("that.data.id4" + that.data.id4)

    wx.request({
      url: pathUrl + '/rest/fxjLoanFkController/createfkme',
      data: {
        id: that.data.id1,
        orderNo: that.data.orderNo,
        fkBy1: that.data.fkBy11,
        fkBy2: that.data.fkBy21,
        fkBy3: that.data.fkBy31,
        fkAmount: that.data.fkAmount1,
      },
    
      method: 'POST',
      success: function (res) {
        console.log(res.data)
        // if (res.data.ok) {
        //   wx.showToast({
        //     title: '提交成功',
        //     duration: 500,
        //     mask: true,
        //     success: function () {
        //       setTimeout(function () {
        //         wx.navigateBack({
        //           delta: 1
        //         })
        //       }, 500);
        //     }
        //   })
        // } else {
        //   wx.showModal({
        //     title: '提示',
        //     content: '获取数据失败',
        //     confirmColor: '#7aa6d1',
        //     showCancel: false
        //   })
        // }
      }
    })
    wx.request({
      url: pathUrl + '/rest/fxjLoanFkController/createfkme',
      data: {
        id: that.data.id2,
        orderNo: that.data.orderNo,
        fkBy1: that.data.fkBy12,
        fkBy2: that.data.fkBy22,
        fkBy3: that.data.fkBy32,
        fkAmount: that.data.fkAmount2,
      },

      method: 'POST',
      success: function (res) {
        console.log(res.data)
        // if (res.data.ok) {
        //   wx.showToast({
        //     title: '提交成功',
        //     duration: 500,
        //     mask: true,
        //     success: function () {
        //       setTimeout(function () {
        //         wx.navigateBack({
        //           delta: 1
        //         })
        //       }, 500);
        //     }
        //   })
        // } else {
        //   wx.showModal({
        //     title: '提示',
        //     content: '获取数据失败',
        //     confirmColor: '#7aa6d1',
        //     showCancel: false
        //   })
        // }
      }
    })
    wx.request({
      url: pathUrl + '/rest/fxjLoanFkController/createfkme',
      data: {
        id: that.data.id3,
        orderNo: that.data.orderNo,
        fkBy1: that.data.fkBy13,
        fkBy2: that.data.fkBy23,
        fkBy3: that.data.fkBy33,
        fkAmount: that.data.fkAmount3,
      },

      method: 'POST',
      success: function (res) {
        console.log(res.data)
        // if (res.data.ok) {
        //   wx.showToast({
        //     title: '提交成功',
        //     duration: 500,
        //     mask: true,
        //     success: function () {
        //       setTimeout(function () {
        //         wx.navigateBack({
        //           delta: 1
        //         })
        //       }, 500);
        //     }
        //   })
        // } else {
        //   wx.showModal({
        //     title: '提示',
        //     content: '获取数据失败',
        //     confirmColor: '#7aa6d1',
        //     showCancel: false
        //   })
        // }
      }
    })
    wx.request({
      url: pathUrl + '/rest/fxjLoanFkController/createfkme',
      data: {
        id: that.data.id4,
        orderNo: that.data.orderNo,
        fkBy1: that.data.fkBy14,
        fkBy2: that.data.fkBy24,
        fkBy3: that.data.fkBy34,
        fkAmount: that.data.fkAmount4,
      },

      method: 'POST',
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