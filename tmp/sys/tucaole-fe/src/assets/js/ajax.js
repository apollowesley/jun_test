import axios from 'axios';
/**
 * 封装ajax函数
 */

// new $ajax({
// 	url: '',
// 	data: '',
// 	method: '',
// 	isForm: ''
// })
// .Ajax()
// .then()
// .catch()
export default class $ajax {
	/**
	 * [constructor 实例创建时接收的参数]
	 * @param  {[Object]} obj [接收的参数必须是一个对象]
	 * @return {[type]}     [description]
	 */
	constructor(obj = {}) {
		this.obj = obj;
		this.res = '';
	}
	/**
	 * [主要业务逻辑 返回一个promise对象]
	 * @param {Object} data [一个token和md5 对象]
	 */
	Ajax() {
		let self = this;
		return new Promise((resolve, reject) => { 
			$ajax._beforeSend(this.obj).then((obj) => {
				axios(obj).then((msg) => {
					self.res = msg.data;
					if (self.res.code === 0) {
						resolve({
							data: self.res.result
						})
					} else {
                        //catch
						reject(self.res)
					}
				}).catch((err) => {
					// 处理err的逻辑
				})
			})
		})
	}

	// 发送ajax前数据的处理
	static _beforeSend(obj) {
		return new Promise((resolve) => {

			let data = $ajax._getToken();

			let setting = {

				url: obj.url,

				baseURL: '',//api.C_URL.imgUpload,

				method: obj.method || 'POST',

				data: obj.data

			}

			// http 提交方式
			if (!obj.isForm) {

				setting.headers = {'Content-Type': 'application/x-www-form-urlencoded'}

				setting.transformRequest = [function (data) {

				 	var qs = require('qs');

		        　　data = qs.stringify(data);

					return data;

				}]

				resolve(setting)

			} else {

				setting.url = self.obj.url + "?token=" + data.token + "&md5id=" + data.md5id;

				setting.headers = {'Content-Type': 'application/json;charset=UTF-8'}

				resolve(setting)

			}

		})
	}


	// 保存token
	setToken(obj) {
		
		const store = window.sessionStorage;

		if (obj.result) {

			if (obj.result.name && obj.result.id) {

				let md5Value = hex_md5(obj.result.id + '|' + obj.result.name)

				store.setItem('md5id', md5Value)
			}
		}
	}

	// 获取token
	static _getToken() { 

		const store = window.sessionStorage;

		let token = store.getItem('token')

		let md5id = store.getItem('md5id')

		return {
			token,
			md5id
		}
	}
}
  