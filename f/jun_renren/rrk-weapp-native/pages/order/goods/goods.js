// pages/order/goods/goods.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    current: '10',
    userId: '',
    status: '', // 订单状态（0，等待买家付款；1，买家已付款；2，卖家已发货；3，退货中；4，交易成功；5，交易关闭；7，已评价）
    page: 0,
    ordertype: '0', // 订单类型（0，普通订单；1,活动订单）
    ordernum: '',
    orderView: [],
    finished: false,
    canRun: true
  },

  onLoad(options) {
    this.data.userId = wx.getStorageSync('userInfo').UserId || ''
    this.getGoodsOrders()
  },

  onPullDownRefresh() {
    this.data.page = 0
    this.setData({finished: false})
    this.getGoodsOrders(res => {
      wx.stopPullDownRefresh()
    })
  },

  onReachBottom() {
    console.log('bottom')
    if (this.data.finished) return
    // 函数节流
    if (!this.data.canRun) return
    this.data.canRun = false
    setTimeout(() => {
      this.getGoodsOrders(res => {
        this.data.canRun = true
      })
    }, 500)
  },

  // 获取用户订单列表
  getGoodsOrders(callback) {
    let params = { status: this.data.status, page: this.data.page, ordertype: this.data.ordertype }
    util.request(api.getOrders, params).then(res => {
      if (res.code === '0') {
        if (this.data.page === 0) this.data.orderView = []
        if (res.body && res.body.length > 0) {
          if (res.body.length < 12) this.setData({finished: true})
          // this.data.orderView = this.data.orderView.concat(res.body)
          this.data.orderView.push(...res.body)
          this.data.page++
        } else {
          this.setData({ finished: true })
        }
        this.setData({ orderView: this.data.orderView })
      } else {
        util.alert(res.msg)
        this.setData({ finished: true })
      }
      if (callback) callback()
    })
  },

  // tab切换
  onChange({detail}) {
    util.loading()
    this.data.status = detail.key === '10' ? '' : detail.key
    this.data.page = 0
    this.setData({
      current: detail.key,
      finished: false
    })
    this.getGoodsOrders(res => {
      wx.hideLoading()
    })
  },

  // 取消订单
  onCannel({detail}) {
    let [num, type, i] = [detail.num, detail.type, detail.index]
    util.confirm('确定要取消订单吗？').then(res => {
      util.loading()
      let params = { ordernum: num, ordertype: type }
      util.request(api.orderCannel, params).then(res => {
        if (res.code === '0') {
          util.success(res.msg)
        } else {
          util.error(res.msg)
        }
        wx.hideLoading()
      })
    }).catch(err => {})
  },

  // 删除订单
  onDelete({detail}) {
    let [num, type, i] = [detail.num, detail.type, detail.index]
    util.confirm('确定要删除订单吗？').then(res => {
      util.loading()
      let params = { userId: this.data.userId, orderNum: num, orderType: type }
      util.request(api.orderDelete, params).then(res => {
        if (res.code === '0') {
          util.success(res.msg)
          this.data.orderView.splice(i, 1)
          this.setData({orderView: this.data.orderView})
        } else {
          util.error(res.msg)
        }
        wx.hideLoading()
      })
    }).catch(err => {})
  }
})