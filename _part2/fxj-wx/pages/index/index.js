var app = getApp();
const Utils = require('../../utils/util.js')

const pathUrl = app.globalData.pathUrl;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list: [],

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (e) {
    var that = this;
    that.orderList();
    var openId = wx.getStorageSync('openId');
    that.setData({
      openId: openId
    })
  },
  //消息通知下一步
  goSignedFace: function (e) {
    console.log(e);
    var orderNo = e.currentTarget.dataset.orderno;
    var id = e.currentTarget.dataset.id;
    var proNo = e.currentTarget.dataset.prono;
    var mpUrl = e.currentTarget.dataset.mpurl;
    var uid = Utils.getUid();
    var proName = e.currentTarget.dataset.proname;

    console.log(mpUrl + "?orderNo=" + orderNo + '&id=' + id);
    if (mpUrl == "/pages/meeting/meeting") {

      wx.navigateTo({
        url: `/pages/meeting/meeting?channel=${orderNo}&uid=${uid}&role=broadcaster`
      });

    } else {
      wx.navigateTo({
        url: mpUrl + "?orderNo=" + orderNo + '&id=' + id + '&proNo=' + proNo + '&proName=' + proName
      })
    }

  },
  //消息通知加载
  orderList: function (e) {
    var that = this;
    var pageNumber = that.data.pageNumber + 1;
    var allList = that.data.list;
    var openId = wx.getStorageSync('openId');
    var index = this.data.orderStatusType;
    console.log(openId)
    wx.request({
      url: pathUrl + '/rest/fxjOrderMainController/list/' + openId,
      data: {
        index:2,
        pageNumber: 1,
        pageSize: 5
      },
      success: function (res) {
        console.log(res.data)
        if (res.data.ok) {
          wx.hideLoading();
          that.setData({
            pageHidden: false
          })
          var arr = [];
          if (res.data.data.length > 0) {

            if (that.data.orderStatusType == '2') {
              for (let i = 0; i < res.data.data.length; i++) {
                if (res.data.data[i].orderStatus != "已删除") {
                  arr = arr.concat(res.data.data[i])
                }
              }
              allList = allList.concat(arr);
              if (res.data.data.length < 10) {
                that.setData({
                  pageNumber: 1,
                  list: allList,
                  loadingComplete: true, //把“没有数据”设为true，显示  
                  loading: false //把"上拉加载"的变量设为false，隐藏  
                })
              } else {
                that.setData({
                  pageNumber: 1,
                  list: allList,
                  loading: false
                })
              }
            }
            else {
              allList = allList.concat(res.data.data);
              if (res.data.data.length < 10) {
                that.setData({
                  pageNumber: 1,
                  list: allList,
                  loadingComplete: true, //把“没有数据”设为true，显示  
                  loading: false //把"上拉加载"的变量设为false，隐藏  
                })
              } else {
                that.setData({
                  pageNumber: 1,
                  list: allList,
                  loading: false
                })
              }
            }



          } else {
            that.setData({
              loadingComplete: true, //把“没有数据”设为true，显示  
              loading: false //把"上拉加载"的变量设为false，隐藏  
            })
          }
        } else {
          wx.hideLoading();
          that.setData({
            pageHidden: true
          })
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
  // 催收名单
  goCsList: function (e) {
    console.log(e.currentTarget.dataset.name)
    wx.navigateTo({
      url: "/pages/list/list?name=" + e.currentTarget.dataset.name
    });
  },
  // 进件 
  goJjList: function () {
    wx.navigateTo({
      url: "/pages/goJjList/goJjList"
    })
  },
  // 订单管理  
  goDdList: function () {
    wx.navigateTo({
      url: "/pages/orderManagement/orderManagement"
    })
  },
  // 外访管理  
  goCpList: function () {
    wx.navigateTo({
      url: "/pages/tool/tool"
    })
  },
  // 外访管理  
  goWfList: function () {
    wx.navigateTo({
      url: "/pages/wfList/wfList"
    })
  },
  // 外访管理  
  goQcList: function () {
    wx.navigateTo({
      url: "/pages/qcList/qcList"
    })
  },

  // 还款提醒  
  goNotice: function () {
    wx.navigateTo({
      url: "/pages/notice/notice"
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

    var that = this;
    that.orderList();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.orderList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})