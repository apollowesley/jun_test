// pages/index/index.js
const app = getApp()
import { $stopWuxRefresher } from '../dist/index'
var dateTimePicker = require('../../utils/dateTimePicker.js');
Page({
  /**
   * 页面的初始数据
   */
  data: {
    multiArray: null,
    multiIndex: null,
    year:'',
    month:'',
    pockets:'',
    outlay:0,
    income:0,
    balance:0
  },

  onRefresh() {
    setTimeout(() => {
      $stopWuxRefresher()
    }, 1500)
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //初始化数据
    var obj = dateTimePicker.dateTimePicker(1900, 2050);
    var arr = new Array();
    var y = obj.dateTimeArray[0];
    var m = obj.dateTimeArray[1];
    arr[0] = obj.dateTimeArray[0];
    arr[1] = [1,2,3,4,5,6,7,8,9,10,11,12];
    var indexArr = new Array();
    var date = new Date();
    var year = date.getFullYear();
    var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
    for (var i = 0; i < 152;i++){
      if(year == y[i]){
        indexArr[0] = i;
        break;
      }
    }
    for (var i = 0; i < 12; i++) {
      if (month == m[i]) {
        indexArr[1] = i;
        break;
      }
    }
    this.setData({
      multiArray:arr,
      multiIndex:indexArr
    })

    //获取账户信息
    var state = app.user.status
    const that = this
    if (state == 1) {
      wx.request({
        url: app.url.url + '/pocket?userID=' + app.user.id + '&year=' + year + '&month=' + (date.getMonth() + 1),
        method: 'GET',
        header: {
          'Authorization': 'Bearer ' + app.user.token
        },
        success: function (res) {
          if (res.data.code == 200) {
            that.setData({
              pockets: res.data.data.pockets
            })
          }
        }
      })
      wx.request({
        url: app.url.url + '/pocket/detail?userID=' + app.user.id + '&year=' + year + '&month=' + (date.getMonth() + 1),
        method: 'GET',
        header: {
          'Authorization': 'Bearer ' + app.user.token
        },
        success: function (res) {
          if (res.data.code == 200) {
            that.setData({
              outlay: res.data.data.outlay,
              income: res.data.data.income,
              balance: res.data.data.balance,
            })
          }
        }
      })
    }
    wx.showLoading({
      mask: true,
      title: '加载中'
    })
    setTimeout(function () {
      wx.hideLoading()
    }, 1000)
  },
  edit:function(e){
    var that = this;
    var id = e.currentTarget.dataset.index;
    var idarr = id.split("-");
    if (idarr[3] == '收入'){
      console.log('收入')
      wx.navigateTo({
        url: '../edit/edit?pid=' + idarr[2] + '&id=1'
      })
    }else{
      wx.navigateTo({
        url: '../edit/edit?pid=' + idarr[2]
      })
    }
  },
  delete: function (e) {
    var that = this;
    var id = e.currentTarget.dataset.index;
    var idarr = id.split("-");
    wx.showModal({
      title: '提示',
      content: '确定要删除此记录吗？',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            url: app.url.url + '/pocketDetail/?id=' + idarr[2] + '&userID=' + app.user.id,
            method: 'DELETE',
            header: {
              'Authorization': 'Bearer ' + app.user.token
            },
            success: function (res) {
              if (res.data.code == 200) {
                that.data.pockets[idarr[1]].pocketDetails.splice(idarr[0], 1);
                if (that.data.pockets[idarr[1]].pocketDetails.length == 0){
                  that.data.pockets.splice([idarr[1]], 1);
                }
                that.setData({
                  pockets: that.data.pockets
                })
              } else{
                wx.showToast({
                  title: '删除失败，请联系管理员',
                  icon: 'none',
                  duration: 1000
                })
              }
            }
          })        
        } else if (res.cancel) {
          return false;
        }       
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },
  // 选择二级联动
  changeMultiPicker(e) {
    const that = this
    that.setData({ multiIndex: e.detail.value })
    var year = that.data.multiArray[0][e.detail.value[0]];
    var month = that.data.multiArray[1][e.detail.value[1]];
    //获取账户信息
    wx.request({
      url: app.url.url + '/pocket?userID=' + app.user.id + '&year=' + year + '&month=' + month,
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + app.user.token
      },
      success: function (res) {
        if (res.data.code == 200) {
          that.setData({
            pockets: res.data.data.pockets
          })
        } else {
          wx.showToast({
            title: '账户过期',
            icon: 'none',
            duration: 1000
          })
        }
      }
    })
    wx.request({
      url: app.url.url + '/pocket/detail?userID=' + app.user.id + '&year=' + year + '&month=' + month,
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + app.user.token
      },
      success: function (res) {
        if (res.data.code == 200) {
          that.setData({
            outlay: res.data.data.outlay,
            income: res.data.data.income,
            balance: res.data.data.balance,
          })
        } else {
          wx.showToast({
            title: '账户过期',
            icon: 'none',
            duration: 1000
          })
        }
      }
    })

    wx.showLoading({
      mask: true,
      title: '加载中'
    })
    setTimeout(function () {
      wx.hideLoading()
    }, 1000)
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    app.user.jump = '../index/index'
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