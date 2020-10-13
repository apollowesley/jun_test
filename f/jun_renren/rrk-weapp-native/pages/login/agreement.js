const util = require('../../utils/util.js')
const api = require('../../config/api.js')
const app = getApp()

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		agreement: ''
	},
	
	// 页面加载
	onLoad(query) {
		// console.log(query)
		let type = query.type || 'mallUser'
		this.getAgreeMent(type)
	},

	// 获取注册协议
	getAgreeMent(type) {
		util.request(api.AgreeMent + type, {}, 'GET').then(res => {
			this.setData({
				agreement: res.body
			})
			wx.setNavigationBarTitle({
				title: res.title
			})
		})
	},

	// 同意协议
	agreeThis() {
		wx.navigateBack({
			delta: 1
		})
	}

})
