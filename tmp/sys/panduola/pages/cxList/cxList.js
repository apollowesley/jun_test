var app = getApp();
const Utils = require('../../utils/util.js');
const pathUrl = app.globalData.pathUrl;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    openId: '',
    className:'',
    studentName:'',
    studentNo:'',
    list:[],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var openId = wx.getStorageSync('openId');
    this.setData({
      openId: openId,
    });
  },
  className:function(e){
    this.setData({
      className: e.detail.value.replace(/\s+/g, '')
    })
  },
  studentName: function (e) {
    this.setData({
      studentName: e.detail.value.replace(/\s+/g, '')
    })
  },
  studentNo: function (e) {
    this.setData({
      studentNo: e.detail.value.replace(/\s+/g, '')
    })
  },
  getSearch:function(e){
    var that = this;
    var stutentName = (that.data.studentName);
    var classNumber = that.data.className;
    var studentNo = that.data.studentNo;
    var openId =  that.data.openId;
    // if (stutentName == '' && classNumber == ''){
    //   wx.showModal({
    //     title: '提示',
    //     content: '输入不能为空',
    //     confirmColor: '#7aa6d1',
    //     showCancel: false,
    //   });
    //   that.setData({
    //     list: []
    //   })
    //   return false;
    // }
    that.setData({
      list: []
    })
    console.log('22')
    var allList = that.data.list;
    wx.request({
      url: pathUrl + '/rest/panduolaClassController/getstudent?username=' +openId+'&stutentName=' + stutentName + '&classNumber=' + classNumber ,
      success:function(res){
        console.log(res);
        if(res.data.ok){
          console.log('33');
          if (res.data.data.length > 0){
            console.log('44');
            allList = allList.concat(res.data.data);
            that.setData({
              list: allList
            })
          }else{
            wx.showModal({
              title: '提示',
              content: '暂无数据',
              confirmColor: '#7aa6d1',
              showCancel: false
            })
          }
        }else{
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