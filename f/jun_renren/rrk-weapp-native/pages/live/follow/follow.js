// pages/live/follow/follow.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()

Page({

  data: {
    userId: '',
    liveIds: '',
    finished: false,
    list: [],
    canRun: true
  },

  onLoad (options) {
    this.data.userId = (wx.getStorageSync('userInfo').UserId) || ''
    this.getUserAttent()
  },
  
  onPullDownRefresh() {
    this.data.liveIds = ''
    this.setData({ finished: false })
    this.getUserAttent(res => {
      wx.stopPullDownRefresh()
    })
  },

  onReachBottom() {
    console.log('bottom')
    // 函数节流
    if (!this.data.canRun) return
    this.data.canRun = false
    setTimeout(() =>{
      this.getUserAttent(res => {
        this.data.canRun = true
      })
    }, 500)
  },

  // 函数防抖
  debounce(method, context) {
    clearTimeout(method.time)
    method.time = setTimeout(() => {
      method.call(context)
    },1000)
  },

  // 获取用户关注的直播
  getUserAttent(callback) {
    let params = { userId: this.data.userId, liveIds: this.data.liveIds }
    util.request(api.getUserAttent, params).then(res => {
      if (res.code === '0') {
        if (!this.data.liveIds) this.data.list = []
        if (res.body && res.body.list.length > 0) {
          this.data.liveIds = res.body.liveIds
          this.data.list = this.data.list.concat(res.body.list)
          this.setData({ list: this.data.list })
          if (res.body.list.length < 10) this.setData({ finished: true })
        } else {
          this.setData({ finished: true })
        }
      } else {
        this.setData({ finished: true })
        util.alert(res.msg)
      }
      if (callback) callback()
    })
  },
})