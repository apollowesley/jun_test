var app = getApp();
const Utils = require('../../utils/util.js');
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pageHidden: true,
        pathUrl: pathUrl,
        list: [],
        id:'',
        pageNumber: 0,
        loading: false, //"上拉加载"的变量，默认false，隐藏  
        loadingComplete: false,  //“没有数据”的变量，默认false，隐藏  
    },
    onLoad: function (options) {
        var openId = wx.getStorageSync('openId');
        var id = options.id;
        this.setData({
            openId: openId,
            id: id
        });
      this.getList();
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
        wx.showLoading({
            title: '加载中',
        })
        var that = this;
        that.setData({
            pageHidden: true,
            orderStatusType: 0,
            loadingComplete: false, //把“没有数据”设为true，显示  
            loading: false,//把"上拉加载"的变量设为false，隐藏  
            pageNumber: 0,
            list: []
        })
      this.getList();
    },
    // 出勤
    goQd:function(e){
        wx.showModal({
            title: '提示',
            content: '确认' + e.currentTarget.dataset.stutentname+'已出勤！',
            success(res) {
                if (res.confirm) {
                    // console.log('用户点击确定');
                    var that = this;
                    wx.request({
                        method: 'PUT',
                        url: pathUrl + '/rest/panduolaClassController/updatedm/' + e.currentTarget.dataset.orderno,
                        data: {
                            id: e.currentTarget.dataset.orderno,
                            remark: '出勤'
                        },
                        success: function (res) {
                            if (res.data.ok) {
                                wx.showModal({
                                    title: '提示',
                                    content: '签到成功',
                                    confirmColor: '#7aa6d1',
                                    showCancel: false,
                                    success(res) {
                                        if (res.confirm) {
                                            that.onPullDownRefresh();
                                        }
                                    }
                                });

                            }
                        }
                    });
                } else if (res.cancel) {
                    console.log('用户点击取消')
                }
            }
        })
        
    },
  // 签到
  goQdqj: function (e) {
      wx.showModal({
          title: '提示',
          content: '确认' + e.currentTarget.dataset.stutentname + '请假！',
          success(res) {
              if (res.confirm) {
                //   console.log('用户点击确定');
                  var that = this;
                  wx.request({
                      method: 'PUT',
                      url: pathUrl + '/rest/panduolaClassController/updatedm/' + e.currentTarget.dataset.orderno,
                      data: {
                          id: e.currentTarget.dataset.orderno,
                          remark: '请假'
                      },
                      success: function (res) {
                          if (res.data.ok) {
                              wx.showModal({
                                  title: '提示',
                                  content: '签到成功',
                                  confirmColor: '#7aa6d1',
                                  showCancel: false,
                                  success(res) {
                                      if (res.confirm) {
                                          that.onPullDownRefresh();
                                      }
                                  }
                              });

                          }
                      }
                  })
              } else if (res.cancel) {
                  console.log('用户点击取消')
              }
          }
      });
  },
    // 补点
    goQdbd:function(e){
        wx.showModal({
            title: '提示',
            content: '确认' + e.currentTarget.dataset.stutentname + '补点！',
            success(res) {
                if (res.confirm) {
                    // console.log('用户点击确定');
                    var that = this;
                    wx.request({
                        method: 'PUT',
                        url: pathUrl + '/rest/panduolaClassController/updatedm/' + e.currentTarget.dataset.orderno,
                        data: {
                            id: e.currentTarget.dataset.orderno,
                            remark: '补点'
                        },
                        success: function (res) {
                            if (res.data.ok) {
                                wx.showModal({
                                    title: '提示',
                                    content: '签到成功',
                                    confirmColor: '#7aa6d1',
                                    showCancel: false,
                                    success(res) {
                                        if (res.confirm) {
                                            that.onPullDownRefresh();
                                        }
                                    }
                                });

                            }
                        }
                    })
                } else if (res.cancel) {
                    console.log('用户点击取消')
                }
            }
        })
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
        wx.showLoading({
            title: '加载中',
        })
        var that = this;
        that.setData({
            loadingComplete: false, //把“没有数据”设为true，显示  
            loading: false,//把"上拉加载"的变量设为false，隐藏  
            pageNumber: 0,
            orderStatusType: that.data.orderStatusType,
            list: []
        });
      this.getList();
        wx.stopPullDownRefresh();
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function (e) {
        
    },
    getList: function(e){
      var that = this;
      var pageNumber = that.data.pageNumber + 1;
      var allList = that.data.list;
      wx.request({
        url: pathUrl + '/rest/panduolaClassController/getdm/' + that.data.id,
        data: {
          pageNumber: pageNumber,
          pageSize: 100
        },
        success: function (res) {
          if (res.data.ok) {
            wx.hideLoading();
            that.setData({
              pageHidden: false
            })
            if (res.data.data.memStudentList.length > 0) {
              allList = allList.concat(res.data.data.memStudentList);
              if (res.data.data.memStudentList.length < 10) {
                that.setData({
                  pageNumber: pageNumber,
                  list: allList,
                  loadingComplete: true, //把“没有数据”设为true，显示  
                  loading: false //把"上拉加载"的变量设为false，隐藏  
                })
              } else {
                that.setData({
                  pageNumber: pageNumber,
                  list: allList,
                  loading: true
                })
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
              pageHidden: false
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
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})