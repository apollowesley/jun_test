// pages/groupon/confirm.js
const util = require('../../utils/util.js')
const api = require('../../config/api.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    loading: false,
    grouponInfo: {},
    userInfo: {},
    selectNum: 1,
    totalPrice: '0'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      grouponInfo: wx.getStorageSync('grouponInfo'),
      selectNum: wx.getStorageSync('selectNum'),
      userInfo: app.globalData.userInfo
    })
    let totalPrice = parseFloat(this.data.grouponInfo.sellPrice) * this.data.selectNum
    this.setData({
      totalPrice: totalPrice
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    wx.removeStorageSync('grouponInfo')
    wx.removeStorageSync('selectNum')
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    wx.removeStorageSync('grouponInfo')
    wx.removeStorageSync('selectNum')
  },

  /**
   * 数量变化
   */
  numChange(e) {
    this.setData({
      selectNum: e.detail,
      totalPrice: parseFloat(this.data.grouponInfo.sellPrice) * e.detail
    })
  },

  /**
   * 提交订单
   */
  onSubmit() {
    this.setData({
      loading: true
    })
    this.orderSubmit()
  },

  /**
   * 提交操作
   */
  orderSubmit() {
    let params = {}
    params.isCompany = 1
    params.userId = app.globalData.userInfo.UserId.toString()
    params.productId = this.data.grouponInfo.id
    params.number = this.data.selectNum
    params.remark = app.globalData.userInfo.MobilePhone.toString()
    params.distribution = wx.getStorageSync('shareUserId') || ''

    util.request(api.grouponOrder, params).then(res => {
      if (res.code !== '0') {
        util.toast(res.msg)
      } else {
        wx.redirectTo({
          url: '/pages/pay/index/index?totalMoney=' + this.data.totalPrice + '&orderNum=' + res.body
        })
      }
    })
  }

})