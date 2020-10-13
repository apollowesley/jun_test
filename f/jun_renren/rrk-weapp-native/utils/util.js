var api = require('../config/api.js');

function formatTime(date) {
	var year = date.getFullYear()
	var month = date.getMonth() + 1
	var day = date.getDate()

	var hour = date.getHours()
	var minute = date.getMinutes()
	var second = date.getSeconds()


	return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatNumber(n) {
	n = n.toString()
	return n[1] ? n : '0' + n
}

/**
 * 封封微信的的request
 */
let head = {
	'Accept': 'application/json',
	'Content-Type': 'application/x-www-form-urlencoded'
}

function request(url, data = {}, headers = {}, method = "POST") {
	let header = {}
	let userInfo = wx.getStorageSync('userInfo') || ''
	if (userInfo) {
		header = {
			userId: userInfo.UserId,
			token: userInfo.Token
		}
	}
	return new Promise((resolve, reject) => {
		wx.request({
			url: url,
			data: data,
			method: method,
      header: { ...head, ...header, ...headers },
			success(res) {
				if (res.statusCode == 200) {
					resolve(res.data)
				} else {
					reject(res.data)
				}
			},
			fail(err) {
				reject(err)
				console.log("failed")
			}
		})
	});
}

/**
 * 检查微信会话是否过期
 */
function checkSession() {
	return new Promise(function (resolve, reject) {
		wx.checkSession({
			success: function () {
				resolve(true);
			},
			fail: function () {
				reject(false);
			}
		})
	});
}

function loading(msg = '加载中') {
	wx.showLoading({
		title: msg,
		mask: true
	})
}

function success(title, onHide) {
	wx.showToast({
		title: title,
		icon: 'success',
		mask: true
	})
	// 隐藏结束回调
	if (onHide) {
		setTimeout(() => {
			onHide()
		}, 2000)
	}
}

function error(title, onHide) {
	wx.showToast({
		title: title,
		image: '/assets/error.png',
		mask: true
	})
	// 隐藏结束回调
	if (onHide) {
		setTimeout(() => {
			onHide()
		}, 2000)
	}
}

function toast(title, onHide) {
	wx.showToast({
		title: title,
		icon: 'none'
	})

	// 隐藏结束回调
	if (onHide) {
		setTimeout(() => {
			onHide()
		}, 1000)
	}
}

/**
 * 警告框
 * @param {string} title 警告内容
 */
function alert(title) {
	wx.showToast({
		title: title,
		image: '/assets/alert.png',
		mask: true
	})
}

/**
 * 确认弹窗
 */
function confirm(text, cancel = true, payload = {}, title = '提示') {
	return new Promise((resolve, reject) => {
		wx.showModal({
			title: title,
			content: text,
			showCancel: cancel,
			success: res => {
				if (res.confirm) {
					resolve(payload)
				} else if (res.cancel) {
					reject(payload)
				}
			},
			fail: res => {
				reject(payload)
			}
		})
	})
}

/**
 * 节流函数
 */
function throttle(fn, delay = 1000) {
	let last = 0
	return (...args) => {
		const now = Date.now()
		if (now - last > delay) {
			fn.call(this, args)
			last = now
		}
	}
}

module.exports = {
	formatTime,
	request,
	checkSession,
	loading,
	success,
	error,
	alert,
	toast,
	confirm,
	throttle
}
