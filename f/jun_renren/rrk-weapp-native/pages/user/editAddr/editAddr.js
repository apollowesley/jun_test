// pages/user/plusAddr/plusAddr.js
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userId: '',
    name: '',
    phone: '',
    region: ['', '', ''],
    remark: '',
    area:'',
    isDefault: '',
    id: ''
  },

  onLoad(options) {
  },

  onShow() {
    this.data.userId = (wx.getStorageSync('userInfo').UserId) || ''
    if (wx.getStorageSync('selectedAddressEdit')) {
      let json = wx.getStorageSync('selectedAddressEdit')
      this.setData({
        isDefault: json.istrue,
        id: json.id,
        name: json.name,
        phone: json.phone,
        remark: json.remark,
        area: json.address
      })
    }
  },

  onUnload() {
    wx.removeStorageSync('selectedAddressEdit')
  },

  nameInput({ detail }) {
    let value = detail.value
    this.setData({ name: value })
  },
  phoneInput({ detail }) {
    let value = detail.value
    this.setData({ phone: value })
  },
  remarkInput({ detail }) {
    let value = detail.value
    this.setData({ remark: value })
  },
  regionChange({ detail }) {
    this.setData({
      region: detail.value,
      area: detail.value.join(' ')
    })
  },
  switchChange({ detail }) {
    this.setData({
      isDefault: detail.value ? 1 : 0
    })
  },
  onSave() {
    let isPhone = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/.test(this.data.phone)
    if (!this.data.name) {
      util.toast('请填写收件人')
    } else if (!isPhone) {
      util.toast('请填写正确的手机号码')
    } else if (!this.data.area) {
      util.toast('请选择省市地区')
    } else if (!this.data.remark) {
      util.toast('请填写详细地址')
    } else {
      util.loading()
      let params = {
        userId: this.data.userId,
        name: this.data.name,
        phone: this.data.phone,
        address: this.data.area,
        remark: this.data.remark,
        istrue: this.data.isDefault,
        id: this.data.id
      }
      util.request(api.addAddress, params).then(res => {
        if (res.code === '0') {
          util.success(res.msg)
          setTimeout(() => {
            wx.navigateBack()
          }, 1500)
        } else {
          util.alert(res.msg)
        }
      })
    }
  }
})