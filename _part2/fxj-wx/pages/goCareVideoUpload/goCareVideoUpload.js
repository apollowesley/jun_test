var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    orderNo: '',
    picUrlList: [],
    flag: true
  },
  onLoad: function (e) {
    this.setData({
      orderNo: e.orderNo
    })
    this.orderMainController();
  },
  orderMainController: function () {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/fxjOrderMainController/attalist/' + that.data.orderNo,
      data: {
        attaType: 920,
        pageNumber: 1,
        pageSize: 100
      },
      success: function (res) {
        console.log(res);
        if (res.data.ok) {
          if (res.data.data.length > 0) {
            that.setData({
              videoSrc: res.data.data[0].attaUrl,
              fileId: res.data.data[0].id
            })
          }
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
  //视频上传
  uploadVideo: function () {
    var that = this
    wx.chooseVideo({
      sourceType: ['album', 'camera'],
      maxDuration: 60,
      camera: 'back',
      success: function (res) {
        var tempFilePath = res.tempFilePath;
        wx.showLoading({
          title: '视频上传中',
          mask: true
        })
        wx.uploadFile({
          url: pathUrl + '/rest/base/upload',
          filePath: tempFilePath,
          name: 'uploadfile_ant',
          formData: {
            sbtype: 'NO',
            filetype: 920,
            orderNo: that.data.orderNo
          },
          header: {
            "Content-Type": "multipart/form-data"
          },
          success: function (res) {
            var data = JSON.parse(res.data);
            that.setData({
              videoSrc: data[0].fileUrl,
              fileId: data[0].fileId
            })
            // console.log("data:" + data[0].fileUrl)
            wx.hideLoading();
            wx.showModal({
              title: '提示',
              content: '上传成功',
              showCancel: false,
            })
            // setTimeout(function () {
            //   wx.navigateBack({
            //     delta: -1
            //   })
            // }, 1000);
          },
          fail: function (res) {
            wx.hideLoading();
            wx.showModal({
              title: '错误提示',
              content: '上传视频失败',
              showCancel: false
            })
          }
        });
      }
    })
  },
  removeVideo: function (e) {
    var fileId = e.currentTarget.dataset.fileid;
    console.log(fileId)
    var that = this;
    wx.request({
      url: pathUrl + '/rest/base/delfile/' + fileId,
      success: function (res) {
        if (res.data.ok) {
          that.setData({
            videoSrc: ''
          })
          wx.showToast({
            title: '视频删除成功',
            icon: 'none',
            mask: true,
            duration: 500
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