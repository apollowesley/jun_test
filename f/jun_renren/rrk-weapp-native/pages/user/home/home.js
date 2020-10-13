// pages/user/home/home.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    time: '',
    userId: '',
    userInfo: '',
    rrk: '',
    actions: [
      { name: '商品订单' },
      { name: '团购订单' }
    ],
    visible: false,
    liveIds: '',
    finished: false,
    liveViews: '',
    canRun: true
  },

  getUserInfo(callback) {
    let params = { Id: this.data.userInfo.UserId, lookUserId: this.data.userInfo.UserId }
    util.request(api.getUserInfo, params).then(res => {
      this.setData({
        rrk: res.body
      })
      wx.hideLoading()
      if (callback) callback()
    })
  },

  getTodayLIve(callback) {
    let params = { address: '', isCompany: '1', ids: this.data.liveIds }
    util.request(api.getTodayLIve, params).then(res => {
      if (res.code === '0') {
        if (!this.data.liveIds) this.data.liveViews = []
        if (res.body.body && res.body.body.length > 0) {
          this.data.liveIds = res.body.liveIds
          this.data.liveViews = this.data.liveViews.concat(res.body.body)
          this.setData({ liveViews: this.data.liveViews })
          if (res.body.body.length < 10) this.setData({ finished: true })
        } else {
          this.setData({ finished: true })
        }
      } else {
        this.setData({ finished: true})
        util.alert(res.msg)
      }
      wx.hideLoading()
      if (callback) callback()
    })
  },

  showActions() {
    this.setData({ visible: true})
  },

  onActions({detail}) {
    if (detail.index === 0) wx.navigateTo({ url: '/pages/order/goods/goods' })
    if (detail.index === 1) wx.navigateTo({url: '/pages/order/group/group'})
    this.setData({ visible: false})
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    util.loading()
    app.loginCallback = res => {
      console.log(res)
      this.setData({
        userInfo: res
      }, () => {
        this.getUserInfo(res => {
          this.getTodayLIve()
        })
      })
      wx.setStorageSync('userInfo', res)
    }
    if (wx.getStorageSync('userInfo')) {
      this.setData({
        userInfo: wx.getStorageSync('userInfo')
      }, () => {
        this.getUserInfo(res => {
          this.getTodayLIve()
        })
      })
    }
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {
    this.data.liveIds = ''
    this.setData({ finished: false })
    this.getTodayLIve(res => {
      wx.stopPullDownRefresh()
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    if (this.data.finished) return
    if (!this.data.canRun) return
    this.data.canRun = false
    setTimeout(() => {
      this.getTodayLIve(res => {
        this.data.canRun = true
      })
    }, 500)
  }
})