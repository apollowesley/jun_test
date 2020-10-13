// pages/order/groupDetail/groupDetail.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()
const QRCode = require('../../../utils/weapp-qrcode.js')
let qrcode

Page({

  data: {
    userId: '',
    state: '',
    orderNum: '',
    x: '',
    y: '',
    info: '',
    showCode: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.data.userId = wx.getStorageSync('userInfo').UserId || ''
    this.data.orderNum = options.orderNum
    this.setData({ state: options.state })
    wx.getLocation({
      type: 'wgs84',
      success: res => {
        this.data.x = res.latitude
        this.data.y = res.longitude
      },
      complete: req => {
        this.getInfo()
      }
    })
  },

  // 获取订单详情
  getInfo() {
    util.loading()
    let params = {
      orderNum: this.data.orderNum,
      userId: this.data.userId,
      x: this.data.x,
      y: this.data.y,
      isCompany: '1'
    }
    util.request(api.getGroupOrderDetail, params).then(res => {
      if (res.code === '0') {
        this.setData({info: res.body})
        wx.hideLoading()
      } else {
        util.alert(res.msg)
      }
    })
  },

  // 
  callPhone({target}) {
    console.log(target)
    let phone = target.dataset.tel
    wx.makePhoneCall({
      phoneNumber: phone
    })
  },

  // 支付
  jumpToPay({ target }) {
    let [num, price] = [target.dataset.num, target.dataset.price]
    wx.navigateTo({
      url: '/pages/pay/index/index?totalMoney=' + price + '&orderNum=' + num
    })
  },

  createCode({target}) {
    let code = target.dataset.code
    console.log(code)
    qrcode = new QRCode('canvas', {
      text: code,
      width: 150,
      height: 150,
      colorDark: "#000000",
      colorLight: "#ffffff",
      correctLevel: QRCode.CorrectLevel.H,
    })
    if (!this.data.showCode) this.setData({ showCode: true })
  }
})