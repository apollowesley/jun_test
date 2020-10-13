// pages/login/bind-mobile.js
const util = require('../../utils/util.js')
const api = require('../../config/api.js')
const app = getApp()

Page({

  data: {
    phone: '',
    code: '',
    isSend: false,
    time: 10
  },

  onShow() {
    wx.setNavigationBarColor({
      frontColor: '#000000',
      backgroundColor: '#ffffff',
    })
  },

  // 发送短信操作
  smsSend() {
    if (!this.checkPhone()) {
      return false
    }
    this.setData({
      isSend: !this.data.isSend
    })
    this.sendSms(req => {
      let intervalId = setInterval(req => {
        this.setData({
          time: this.data.time - 1
        })
        if (this.data.time === 0) {
          clearInterval(intervalId)
          this.setData({
            isSend: false
          })
        }
      }, 1000)
    })
  },

  // 发送短信
  sendSms(callback) {
    let params = {}
    params.phone = this.data.phone
    util.request(api.sendSms, params).then(res => {
      wx.hideLoading()
      if (res.code !== '0') {
        util.error(info.msg)
        return false
      } else {
        util.success('短信已发送')
        if (callback) callback()
      }
    })
  },

  // 获取输入内容
  inputValue(e) {
    let key = e.target.dataset.key
    switch (key) {
      case 'phone':
        this.setData({
          phone: e.detail.value
        })
        break;
      case 'code':
        this.setData({
          code: e.detail.value
        })
        break;
    }
  },

  // 手机号验证
  checkPhone() {
    let phone = this.data.phone
    let reg = /^((13|14|15|17|18)[0-9]{1}\d{8})$/
    if (phone === '') {
      util.error('请输入号码')
      return false
    } else if (!reg.test(phone)) {
      util.error('手机格式错误')
      return false
    } else {
      return true
    }
  },

  // 访问注册协议
  userAgreement() {
    wx.navigateTo({
      url: '/pages/login/agreement'
    })
  },

  // 用户绑定手机号码
  bindMobile(e) {
    if (!this.checkPhone()) {
      return false
    }
    if (this.data.code === '') {
      util.error('请输入验证码')
      return false
    }
    // console.log(e)
    if (e.detail.userInfo) {
      let userInfo = e.detail.userInfo
      let params = {}
      params.recommendUser = wx.getStorageSync('shareUserId') || ''
      params.userId = app.globalData.userInfo.UserId
      params.unionid = app.globalData.userInfo.unionId
      params.phone = this.data.phone
      params.code = this.data.code
      params.type = 0
      params.userName = userInfo.nickName
      params.sex = userInfo.gender
      params.picture = userInfo.avatarUrl

      util.request(api.userBindMobile, params).then(res => {
        if (res.code !== '0') {
          util.error(res.msg)
          return false
        } else {
          util.success('登录成功')
          let userInfo = {}
          userInfo.UserId = res.body.id
          userInfo.UserName = res.body.userName
          userInfo.HeadIconUrl = res.body.picture
          userInfo.Type = res.body.type
          userInfo.UserExperience = res.body.userExperience
          userInfo.Grade = res.body.grade
          userInfo.Age = res.body.age
          userInfo.IsOnline = res.body.isOnLine
          userInfo.BaoMoney = res.body.baoMoney
          userInfo.IsCompany = res.body.isCompany
          userInfo.MobilePhone = res.body.phone
          userInfo.Token = res.body.token
          userInfo.unionId = app.globalData.userInfo.unionId
          userInfo.openId = app.globalData.userInfo.openId
          app.globalData.userInfo = userInfo
          wx.setStorageSync('userInfo', userInfo)
          wx.navigateBack({
            delta: 1
          })
        }
      })
    } else {
      util.error('微信授权失败')
    }
  },

})