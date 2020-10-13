// pages/user/address/address.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userId: '',
    views: [],
    defaultAddressId: '',
    selectedAddressId: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    if (wx.getStorageSync('selectAddressId')) {
      this.setData({
        selectedAddressId: wx.getStorageSync('selectAddressId')
      })
    }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    if (wx.getStorageSync('userInfo')) this.data.userId = wx.getStorageSync('userInfo').UserId
    this.getUserAddress()
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {
    if (this.data.views.length === 0) {
      wx.removeStorageSync('selectAddress')
    }
  },

  // 获取用户地址列表
  getUserAddress() {
    util.loading()
    let params = { userId: this.data.userId }
    util.request(api.getAddress, params).then(res => {
      if (res.code === '0') {
        if (res.body.length === 0) {
          util.toast('没有地址，去添加一个吧')
          this.setData({ views: res.body })
          return
        }
        res.body.forEach(el => {
          if (this.data.selectedAddressId) {
            el.active = el.id === this.data.selectedAddressId
          } else {
            el.active = el.istrue === 1
          }
        })
        this.setData({views: res.body})
      } else {
        util.toast(res.msg || '没有数据')
      }
      wx.hideLoading()
    })
  },

  // 
  onAddress ({currentTarget}) {
    let index = currentTarget.dataset.index
    wx.setStorageSync('selectAddressId', this.data.views[index].id)
    this.data.views.forEach(el => { el.active = false })
    this.data.views[index].active = true
    this.setData({views: this.data.views})
    wx.navigateBack()
  },

  // 设为默认
  onDefault ({currentTarget}) {
    let index = currentTarget.dataset.index
    util.loading()
    this.data.views.forEach(el => { el.istrue = 0 })
    this.data.views[index].istrue = 1
    this.setData({
      views: this.data.views,
      defaultAddressId: this.data.views[index].id
    })
    this.setDefaultAddress()
  },

  // 设为默认地址方法
  setDefaultAddress() {
    let params = { saId: this.data.defaultAddressId }
    util.request(api.defaultAddress, params).then(res => {
      if (res.code === '0') {
        this.setData({views: res.body})
        util.success(res.msg)
      } else {
        util.alert(res.msg)
      }
    })
  },

  // 删除地址
  onDelete({target}) {
    let index = target.dataset.index
    console.log(index)
    this.data.defaultAddressId = this.data.views[index].id
    util.confirm('确定要删除地址吗？').then(res => {
      console.log(0)
      this.deleteAddress(index)
    }).catch(err => {
      return
    })
  },

  // 删除地址方法
  deleteAddress(index) {
    util.loading()
    let params = { saId: this.data.defaultAddressId }
    util.request(api.delAddress, params).then(res => {
      if (res.code === '0') {
        util.toast(res.msg)
        this.data.views.splice(index, 1)
        this.setData({views: this.data.views})
      } else {
        wx.alert(res.msg)
      }
    })
    wx.hideLoading()
  },

  // 编辑地址
  jumpToEdit({target}) {
    console.log(target.dataset.index)
    let index = target.dataset.index
    wx.setStorageSync('selectedAddressEdit', this.data.views[index])
    wx.navigateTo({
      url: '/pages/user/editAddr/editAddr',
    })
  },

  // 新增地址
  plusAddress() {
    wx.navigateTo({
      url: '/pages/user/plusAddr/plusAddr'
    })
  }
})