// pages/pay/success/success.js
const util = require('../../../utils/util.js')

Page({

  data: {
    price: '',
    orderNum: '',
    prepayId: '',
    accessToken: '',
    expiresIn: 0
  },

  onLoad(options) {
    this.data.prepayId = options.prepayId
    this.data.price = options.price
    this.data.orderNum = options.orderNum
  },

  onReady() {

  },

  onHide() {

  },

  onSubmit(e) {
    console.log(e)
    let that = this
    this.getAccessToken(() => {
      this.send(e.detail.formId)
      // let time = setInterval(() => {
      //   that.send()
      // }, 5000)
    })
  },

  getAccessToken(callback) {
    let api = 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8c10c50c9d837128&secret=3169e63d68a3dced769a25f40604e893'
    util.request(api, {}, {}, 'GET').then(res => {
      this.data.accessToken = res.access_token
      this.data.expiresIn = res.expires_in
      if (callback) callback()
    })
  },
  send(formId) {
    let header = {
      'Content-Type': 'application/json'
    }
    let api = 'https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token='
    let params = {
      "touser": wx.getStorageSync('userInfo').openId,
      "template_id": "tzrcdlpGWrSJt92_wS_mrAPTlPuDAu8b75GNvCp-c9Q",
      "page": "",
      "form_id": formId,
      "data": {
        "keyword1": {
          "value": this.data.orderNum
        },
        "keyword2": {
          "value": "2018-09-07"
        },
        "keyword3": {
          "value": this.data.price
        }
      },
      "emphasis_keyword": "keyword3.DATA"
    }
    util.request(api + this.data.accessToken, params, header).then(res => {
      if (res.errcode === 0) {} else {
        wx.reLaunch({
          url: '/pages/live/index/index',
        })
      }
    })
    wx.navigateBack()
  },

  goBack() {
    wx.navigateBack()
  }
})