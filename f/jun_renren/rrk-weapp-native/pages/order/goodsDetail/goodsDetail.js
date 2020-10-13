// pages/order/goodsDetail/goodsDetail.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()

Page({
  data: {
    userId: '',
    num: '',
    type: '',
    view: ''
  },

  onLoad(options) {
    this.data.num = options.orderNum
    this.data.type = options.orderType
    this.data.userId = (wx.getStorageSync('userInfo').UserId) || ''
    this.getOrderDetail(res => {
      wx.hideLoading()
    })
  },

  // 获取订单详情
  getOrderDetail(callback) {
    util.loading()
    let params = { ordernum: this.data.num, activityType: this.data.type }
    util.request(api.getOrderDetail, params).then(res => {
      if (res.code === '0') {
        this.setData({ view: res.body })
      } else {
        util.alert(res.msg)
      }
      if(callback) callback()
    })
  },

  // 支付
  toPay({target}) {
    let [num, price] = [target.dataset.num, target.dataset.price]
    wx.navigateTo({
      url: '/pages/pay/index/index?totalMoney=' + price + '&orderNum=' + num
    })
  }
})