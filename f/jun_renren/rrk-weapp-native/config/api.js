const ApiUrl = 'https://app.lgh81.com'
const AppUrl = 'https://gw.lgh81.com'

module.exports = {
	// 协议
	AgreeMent: AppUrl + '/API/Agree?action=',
	
	// 获取用户信息类
	userLogin: ApiUrl + '/App_API.ashx?action=MiniProgramLogin', // 用户登录
	sendSms: ApiUrl + '/App_API.ashx?action=GetCheckCode', // 发送短信验证码
	userBindMobile: ApiUrl + '/App_API.ashx?action=H5Register', // 用户绑定手机号
	
	// 直播/回放类
	getLiveInfo: ApiUrl + '/Live_API.ashx?action=GetH5LiveInfo', // 获取直播回放信息
	getFollow: ApiUrl + '/Live_API.ashx?action=GetUserAttentionH5LiveInfo', // 获取直播/回放关注列表
	setUserAttention: ApiUrl + '/App_API.ashx?action=SetUserAttention', // 用户关注
	cancelUserAttention: ApiUrl + '/App_API.ashx?action=CancelUserAttention', // 取消关注
	getLiveGoods: ApiUrl + '/Product_API.ashx?action=GetIsOnlineProduct', // 获取直播推荐产品
  getTodayLIve: ApiUrl + '/Index_API.ashx?action=RRKTodayHostModule', // 今日热门主播

	// 用户支付类
  orderWxPay: ApiUrl + '/PaySign_API.ashx?action=PaySingByOrderNum', // 用户微信支付

	// 个人中心
	getUserInfo: ApiUrl + '/App_API.ashx?action=GetUserById', // 个人信息
	getUserAttent: ApiUrl + '/Live_API.ashx?action=GetUserAttentionH5LiveInfo', // 查询用户关注H5直播信息

	// 购物车类
	addCart: ApiUrl + '/App_API.ashx?action=UserShoppingCar', // 加入购物车
	getUserCart: ApiUrl + '/Product_API.ashx?action=GetShoppingCarList', // 购物车列表
	delUserCart: ApiUrl + '/Product_API.ashx?action=DelShoppingCar', // 删除购物车
	buyUserCart: ApiUrl + '/Product_API.ashx?action=BuyProductByCar', // 购物车产品购买下单

	// 用户地址管理
	getAddress: ApiUrl + '/App_API.ashx?action=GetShippingAddress', // 获取用户地址
	addAddress: ApiUrl + '/App_API.ashx?action=AddShippingAddress', // 添加用户地址
	defaultAddress: ApiUrl + '/App_API.ashx?action=EditShippingAddress', // 修改默认地址
	delAddress: ApiUrl + '/App_API.ashx?action=DeleteSA', // 删除地址

  // 普通订单
  getOrders: ApiUrl + '/Product_API.ashx?action=GetUserOrderList', // 产品和秒杀订单
  getOrderDetail: ApiUrl + '/Product_API.ashx?action=GetUserOrderDetail', // 查询订单详情
  orderCannel: ApiUrl + '/Product_API.ashx?action=CancelOrder', // 取消订单
  orderDelete: ApiUrl + '/MerchantIndent_API.ashx?action=IndentDeleteGoods', // 删除产品订单

  // 团购订单
  getGroupOrder: ApiUrl + '/Product_API.ashx?action=GetUserRimGroupIndent', // 周边团订单
  getGroupOrderDetail: ApiUrl + '/Product_API.ashx?action=GetRimGroupInfo', // 周边团订单详情
  groupOrderCannel: ApiUrl + '/Product_API.ashx?action=CanlRimGroupIndent', // 周边团订单关闭
  
  // 订单类
  normalOrder: ApiUrl + '/Product_API.ashx?action=BuyProduct', // 提交正常订单
  activityOrder: ApiUrl + '/Product_API.ashx?action=BuyGroupProduct', // 提交活动订单
  grouponOrder: ApiUrl + '/Product_API.ashx?action=BuyRimGroupProductNew', // 提交团购订单
};
