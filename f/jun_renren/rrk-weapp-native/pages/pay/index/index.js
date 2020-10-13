// pages/pay/index/index.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderNum: '',
    totalMoney: 0,
    openId: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.data.openId = (wx.getStorageSync('userInfo').openId) || ''
    if (!options.totalMoney || !options.orderNum) {
      wx.navigateBack()
    } else {
      this.setData({
        totalMoney: options.totalMoney,
        orderNum: options.orderNum
      })
    }
  },

  wxPay() {
    util.loading()
    util.request('https://gw.lgh81.com/Wap/WxAppWxPay?orderno='+ this.data.orderNum +'&openId=' + this.data.openId).then(info => {
      console.log(info)
      if (info.Code !== 'OK') {
        util.confirm(info.Msg, false).then(res => {
          wx.navigateBack()
          console.log('错误')
        })
      } else {
        // let pay = info.body.weCharSign
        wx.requestPayment({
          'timeStamp': info.timeStamp,
          'nonceStr': info.nonceStr,
          'package': 'prepay_id=' + info.prepayId,
          'signType': 'MD5',
          'paySign': info.paySign,
          'success': res => {
            console.log(res)
            wx.redirectTo({
              url: '/pages/pay/success/success?prepayId=' + info.prepayId + '&price=' + this.data.totalMoney + '&orderNum=' + this.data.orderNum
            })
          },
          'fail': err => {
            console.log(err)
            wx.navigateTo({
              url: '/pages/pay/fail/fail',
            })
          }
        })
      }
      wx.hideLoading()
    })
  }
})