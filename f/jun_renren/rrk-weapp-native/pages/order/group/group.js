// pages/order/group/group.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()

Page({
  data: {
    current: '10',
    userId: '',
    page: 0,
    type: '',
    orderView: [],
    orderNum: '',
    finished: false,
    canRun: true
  },

  onLoad(options) {
    this.data.userId = wx.getStorageSync('userInfo').UserId || ''
    this.getOrders()
  },

  onPullDownRefresh() {
    this.data.page = 0
    this.setData({ finished: false })
    this.getOrders(res => {
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
      this.getOrders(res => {
        this.data.canRun = true
      })
    }, 500)
  },

  // 获取团购订单列表
  getOrders(callback) {
    let params = { userId: this.data.userId, isCompany: '1', type: this.data.type, page: this.data.page }
    util.request(api.getGroupOrder, params).then(res => {
      if (res.code === '0') {
        if (this.data.page === 0) this.data.orderView = []
        if (res.body && res.body.length > 0) {
          if (res.body.length < 10) this.setData({ finished: true })
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
  onChange({ detail }) {
    util.loading()
    this.data.type = detail.key === '10' ? '' : detail.key
    this.data.page = 0
    this.setData({
      current: detail.key,
      finished: false
    })
    this.getOrders(res => {
      wx.hideLoading()
    })
  },

  // pay
  orderPay({target}) {
    let [num, price] = [target.dataset.num, target.dataset.price]
    wx.navigateTo({
      url: '/pages/pay/index/index?totalMoney=' + price + '&orderNum=' + num
    })
  },

  // 关闭
  orderDel({target}) {
    let [num, index] = [target.dataset.num, target.dataset.index]
    util.confirm('确认要关闭此订单吗？').then(res => {
      util.loading()
      let params = { userId: this.data.userId, orderNum: num, isCompany: '1' }
      util.request(api.groupOrderCannel, params).then(res => {
        if (res.code === '0') {
          this.data.orderView.splice(index, 1)
          this.setData({orderView: this.data.orderView})
          util.success('已关闭')
        } else {
          util.alert(res.msg)
        }
      })
    }).catch(err => {})
  },

  // 查看
  jumpToDetail(e) {
    let num = e.target.dataset.num || e.currentTarget.dataset.num
    let state = e.target.dataset.state || e.currentTarget.dataset.state
    wx.navigateTo({
      url: '/pages/order/groupDetail/groupDetail?orderNum=' + num + '&state=' + state
    })
  }
})