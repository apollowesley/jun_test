// pages/cart/index/index.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userId: '',
    carId: '',
    views: [],
    loading: false,
    isEdit: false,
    allPrice: 0,
    allNum: 0,
    allPost: 0
  },

  onLoad(options) {
    this.data.userId = wx.getStorageSync('userInfo').UserId
  },

  onShow() {
    this.setData({ loading: false })
    this.getUserCart()
    wx.removeStorageSync('selectAddress')
    wx.removeStorageSync('selectAddressId')
  },

  // 获取用户购物车商品
  getUserCart() {
    util.loading()
    let params = { userId: this.data.userId }
    util.request(api.getUserCart, params).then(res => {
      console.log(res)
      if (res.code === '0') {
        if (res.body && res.body.length > 0) {
          res.body.forEach(el => {
            el.selected = false
            el.onShow = false
            el.productS.forEach(ele => { ele.selected = false })
          })
          this.setData({ views: res.body })
          wx.hideLoading()
        } else {
          this.setData({ views: res.body })
          util.toast('没有商品，去逛逛吧~')
        }
      } else {
        util.error(res.msg)
      }
    })
  },

  // 店铺选择
  onShopSelect({ currentTarget }) {
    let ev = currentTarget.dataset
    this.setData({
      ['views[' + ev.sindex + '].selected']: !ev.selected,
      ['views[' + ev.sindex + '].onShow']: !ev.selected
    })
    // 判断是否全选当前店铺商品
    if (this.data.views[ev.sindex].selected) {
      this.data.views[ev.sindex].productS.forEach(el => { el.selected = true })
    } else {
      this.data.views[ev.sindex].productS.forEach(el => {
        this.data.views[ev.sindex].productS.forEach(el => { el.selected = false })
      })
    }
    this.setData({ ['views[' + ev.sindex + ']']: this.data.views[ev.sindex] })
    // 
    this.getTotalValue()
  },

  // 店铺商品选择
  onGoodsSelect({ currentTarget }) {
    let ev = currentTarget.dataset
    this.setData({
      ['views[' + ev.sindex + '].productS[' + ev.gindex + '].selected']: !ev.selected
    })
    // 判断是否全部选中
    let isAll = this.data.views[ev.sindex].productS.every(el => { return el.selected === true })
    let isShow = this.data.views[ev.sindex].productS.some(el => { return el.selected === true })
    this.setData({
      ['views[' + ev.sindex + '].selected']: isAll,
      ['views[' + ev.sindex + '].onShow']: isShow
    })
    // 
    this.getTotalValue()
  },

  // 编辑
  onEdit() {
    this.setData({
      isEdit: !this.data.isEdit
    })
  },

  // 删除商品
  delGoods({ target }) {
    let index = target.dataset.index
    let arrs = []
    this.data.views[index].productS.forEach(el => {
      if (el.selected) arrs.push(el.id)
    })
    this.data.carId = arrs.join(',')
    this.delUserCart(res => {
      this.setData({allPrice: 0, allNum: 0, allPost: 0})
    })
  },

  delUserCart(callback) {
    let params = { carId: this.data.carId }
    util.request(api.delUserCart, params).then(res => {
      if (res.code === '0') {
        this.getUserCart()
      } else {
        util.toast(res.msg)
      }
      callback()
    })
  },

  // 计数器数量
  numChange({ detail: stepper, target }) {
    let s = target.dataset.sindex
    let g = target.dataset.gindex
    this.setData({
      ['views[' + s + '].productS[' + g + '].num']: stepper
    })
    this.getTotalValue()
  },

  // 计算总价，件数，运费
  getTotalValue() {
    let cart = this.data.views
    let totalPrice = 0
    let totalNum = 0
    let totalPost = 0
    cart.forEach(el => {
      el.productS.forEach(ele => {
        if (ele.selected) {
          totalPrice += parseFloat(ele.sellPrice) * parseInt(ele.num)
          totalNum += parseInt(ele.num)
          totalPost += parseFloat(ele.postage)
        }
      })
    })
    this.setData({
      allPrice: totalPrice,
      allNum: totalNum,
      allPost: totalPost,
    })
  },

  // 结算
  onSubmit() {
    if (this.data.allPrice <= 0) {
      util.toast('请选择要结算的商品')
    } else {
      this.setData({ loading: true })
      wx.setStorageSync('orderTotalPrice', this.data.allPrice + this.data.allPost)
      wx.setStorageSync('orderInfo', this.data.views)
      wx.navigateTo({
        url: '/pages/cart/confirm/confirm'
      })
    }
  }
})