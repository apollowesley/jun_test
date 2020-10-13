// pages/cart/confirm/confirm.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userId: '',
    loading: false,
    views: [],
    totalPrice: '',
    carId: [],
    carNum: [],
    params: {
      carId: '',
      userId: '',
      remark: '',
      shippingId: '',
      isCompany: '1',
      num: ''
    },
    defaultAdd: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let arrs = wx.getStorageSync('orderInfo')
    arrs.forEach(el => {
      let price = 0
      let num = 0
      let post = [0]
      el.productS.forEach(ele => {
        if (ele.selected) {
          num += parseInt(ele.num) // 计算数量
          price += parseFloat(ele.sellPrice) * parseInt(ele.num) // 计算小计
          post.push(ele.postage) // 运费
          this.data.carId.push(ele.id) // 选择的商品id
          this.data.carNum.push(ele.num) // 选择的商品数量
        }
      })
      el.totalNum = num
      el.totalPost = (Math.max.apply(Math, post)) // 取每个商品运费的最大值
      el.totalPrice = price + el.totalPost
      el.remarks = ''
    })
    // 渲染
    this.setData({
      views: arrs,
      totalPrice: wx.getStorageSync('orderTotalPrice') || '',
      userId: wx.getStorageSync('userInfo').UserId || '',
      'params.userId': wx.getStorageSync('userInfo').UserId || ''
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.setData({ loading: false })
    // 判断选择的地址
    if (wx.getStorageSync('selectAddressId')) {
      let shoppingId = wx.getStorageSync('selectAddressId')
      this.getAddress(shoppingId)
    } else {
      this.getAddress()
    }
  },

  // 获取用户地址列表
  getAddress(shoppingId) {
    util.loading()
    let params = { userId: this.data.userId }
    util.request(api.getAddress, params).then(res => {
      // 仅一个时，只选这个
      if (res.body.length <= 1) {
        this.data.defaultAdd = res.body[0]
        if (res.body.length === 0) this.data.defaultAdd = ''
      } else {
        // 存在选择的地址id时
        if (shoppingId) {
          this.data.defaultAdd = res.body.find(el => { return el.id === shoppingId })
        } else {
          this.data.defaultAdd = res.body.find(el => { return el.istrue === 1 })
          if (!this.data.defaultAdd) this.data.defaultAdd = res.body[0]
        }
      }
      this.setData({defaultAdd: this.data.defaultAdd})
      wx.hideLoading()
    })
  },

  // 留言
  remarkInput({detail, target}) {
    let i = target.dataset.index
    this.setData({
      ['views['+ i +'].remarks']: detail.value
    })
  },

  // 提交订单
  onSubmit() {
    // this.setData({loading: !this.data.loading})
    this.data.params.carId = this.data.carId.join(',')
    this.data.params.num = this.data.carNum.join(',')
    let remark = []
    this.data.views.forEach(el => {
      if (el.onShow) {
        let json = { 'userId': this.data.userId, 'remark': el.remarks }
        remark.push(json)
      }
    })
    this.data.params.remark = JSON.stringify(remark)
    this.data.params.shippingId = this.data.defaultAdd.id
    this.setData({
      params: this.data.params
    })
    this.confirmOrder()
  },

  // 提交至接口
  confirmOrder() {
    util.loading('提交中')
    util.request(api.buyUserCart, this.data.params).then(res => {
      if (res.code === '0') {
        console.log(res)
        wx.navigateTo({
          url: '/pages/pay/index/index?totalMoney=' + this.data.totalPrice + '&orderNum=' + res.body
        })
        wx.hideLoading()        
      } else {
        if (res.code === '') {
          util.alert('此订单已提交')
        } else {
          util.alert(res.msg)
        }
      }
    })
  }
})