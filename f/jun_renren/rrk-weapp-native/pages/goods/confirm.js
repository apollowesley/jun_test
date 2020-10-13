// pages/goods/confirm.js
const util = require('../../utils/util.js')
const api = require('../../config/api.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    loading: false,
    goodsInfo: {},
    selectNorms: 0,
    selectNum: 1,
    defaultAdd: {},
    totalPrice: '0',
    remark: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let selected = wx.getStorageSync('selectNorms')
    this.setData({
      goodsInfo: wx.getStorageSync('goodsInfo'),
      selectNum: wx.getStorageSync('selectNum')
    })
    this.setData({
      selectNorms: this.data.goodsInfo.pInfo[selected],
    })
    let totalPrice = (parseFloat(this.data.selectNorms.sellPrice) * this.data.selectNum) + parseFloat(this.data.goodsInfo.postage)
    this.setData({
      totalPrice: totalPrice
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.getUserAddress()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    wx.removeStorageSync('selectNorms')
    wx.removeStorageSync('goodsInfo')
    wx.removeStorageSync('selectNum')
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    wx.removeStorageSync('selectNorms')
    wx.removeStorageSync('goodsInfo')
    wx.removeStorageSync('selectNum')
  },

  /**
   * 获取用户收货地址
   */
  getUserAddress() {
    util.loading()
    let params = {}
    params.userId = app.globalData.userInfo.UserId.toString()
    util.request(api.getAddress, params).then(res => {
      wx.hideLoading()
      if (res.code !== '0') {
        util.error(res.msg)
        return false
      } else {
        let defaultAdd = ''
        if (res.body.length <= 1) {
          defaultAdd = res.body[0]
        } else {
          let shoppingId = wx.getStorageSync('selectAddressId')
          if (shoppingId) {
            defaultAdd = res.body.find(el => {
              return el.id === shoppingId
            })
          } else {
            defaultAdd = res.body.find(el => {
              return el.istrue === 1
            })
            if (!defaultAdd) defaultAdd = res.body[0]
          }
        }
        this.setData({
          defaultAdd: defaultAdd
        })
      }
    })
  },

  /**
   * 监听留言
   */
  remarkInput({ detail, target }) {
    this.setData({
      remark: detail.value
    })
  },

  /**
   * 提交订单
   */
  onSubmit() {
    this.setData({
      loading: true
    })
    if (!this.data.defaultAdd) {
      util.toast('请选择收货地址')
      this.setData({
        loading: false
      })
      return
    }

    this.orderSubmit()
  },

  /**
   * 提交操作
   */
  orderSubmit() {
    let params = {}
    params.userId = app.globalData.userInfo.UserId.toString()
    params.remark = this.data.remark || ''
    params.shippingId = this.data.defaultAdd.id
    params.isCompany = 1
    params.productId = this.data.goodsInfo.id
    params.specId = this.data.selectNorms.id
    params.num = this.data.selectNum
    params.distributionId = wx.getStorageSync('shareUserId') || ''
    params.shopId = this.data.goodsInfo.userId

    util.request(api.normalOrder, params).then(res => {
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