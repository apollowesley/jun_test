var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
      openId: '',
        pathUrl: pathUrl,
        orderNo:'',
        id:'',
        msgcontent: ''
    },
    onLoad: function (e) {
        console.log(e)
      var openId = wx.getStorageSync('openId');

        this.setData({
            orderNo: e.orderNo,
          id: e.id,
          openId: openId
        })
    },

  
  msgcontentInput: function (e) {
    this.setData({
      msgcontent: e.detail.value
    })
  },
    //拍照上传
    goPhotoUpload: function () {
        wx.navigateTo({
            url: "/pages/photoUpload/photoUpload?orderNo=" + this.data.orderNo
        })
    },
    //录像上传
    goVideoUpload: function () {
        wx.navigateTo({
            url: "/pages/videoUpload/videoUpload?orderNo=" + this.data.orderNo
        })
    },
    onReady: function () {

    },

  //保存
  saveBtn: function (e) {
    var that = this;
    var data = {
      id: that.data.id,
      orderOper: 'N',
      orderNo: that.data.orderNo, //订单编号
      updateBy: that.data.openId,
      fxjOrderLogList: [{
        orderNo: that.data.orderNo,
        logContent: that.data.msgcontent

      }]
    };
    console.log("data:" + JSON.stringify(data));
    
      wx.request({

        url: pathUrl + '/rest/fxjOrderMainController/' + that.data.id,
        data: data,
        method: 'PUT',
        success: function (res) {
          console.log(res.data)
          if (res.data.ok) {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: '保存成功',
              duration: 500,
              mask: true,
              success: function () {
                setTimeout(function () {
                  wx.navigateTo({
                    url: "/pages/orderjfManagement/orderjfManagement"
                  })
                }, 1000);
              }
            })
          } else {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: '提交失败',
              icon: 'none',
              mask: true,
              duration: 1000
            })
          }
        }
      })
     
  },
  //提交
  submitBtn: function (e) {
    var that = this;
    var data = {
      id: that.data.id,
      orderOper: 'Y',
      orderNo: that.data.orderNo, //订单编号
      updateBy: that.data.openId,

      fxjOrderLogList: [{
        orderNo: that.data.orderNo,
        logContent: that.data.msgcontent

      }]
    };
    console.log("data:" + JSON.stringify(data));

 
      wx.request({
        url: pathUrl + '/rest/fxjOrderMainController/' + that.data.id,
        data: data,
        method: 'PUT',
        success: function (res) {
          console.log(res.data)
          if (res.data.ok) {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: '提交成功',
              duration: 500,
              mask: true,
              success: function () {
                setTimeout(function () {
                  wx.navigateTo({
                    url: "/pages/orderjfManagement/orderjfManagement"
                  })
                }, 1000);
              }
            })
          } else {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: '提交失败',
              icon: 'none',
              mask: true,
              duration: 1000
            })
          }
        }
      })
 
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