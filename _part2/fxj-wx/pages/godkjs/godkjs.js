var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    openId: '',
    lsnum: '',//估值/万
    lsnum2: '0.9',//估值/万
    frameno: '',//利息/厘
    frameno1: '',//年息

    engineno: '',//申请期数/月
    engineno1: '',//银行利息

    queryResults: '',//查询结果
    queryResults1: '',//查询结果

    queryResults2: '',//查询结果

    violationTimes: '',//违章次数
    totalDeduction: '',//总扣分
    totalFine: '',//总罚款
    list: []
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (e) {
    var openId = wx.getStorageSync('openId');
    this.setData({
      openId: openId
    })
  },
  //申请金额
  moneyInput: function (e) {
    this.setData({
      money: e.detail.value
    })
  },
  //车牌号赋值
  lsnumInput: function (e) {
    this.setData({
      lsnum: e.detail.value
    })
    var that = this;
    var lsnum = that.data.lsnum;
    var lsnum2 = that.data.lsnum2;
    var money = Math.round(Number(lsnum) * Number(lsnum2))
    this.setData({
      money: money
    })
  },
  //车牌号赋值
  lsnum2Input: function (e) {
    this.setData({
      lsnum2: e.detail.value
    })
    var that = this;
    var lsnum = that.data.lsnum;
    var lsnum2 = that.data.lsnum2;
    var money = Math.round(Number(lsnum) * Number(lsnum2))
    this.setData({
      money: money
    })
  },
  //保险费
  insuranceInput: function (e) {
    this.setData({
      insurance: e.detail.value
    })
  },
  //返佣比例
  fanyongInput: function (e) {
    this.setData({
      fanyong: e.detail.value
    })
  },
  //GPS费用
  gpsInput: function (e) {
    this.setData({
      gps: e.detail.value
    })
  },
  //月息
  framenoInput: function (e) {
    this.setData({
      frameno: e.detail.value
    })
    var that = this;
    var frameno = that.data.frameno;

    var frameno1 = that.data.frameno1;
    var engineno = that.data.engineno;
    var frameno1 = Math.abs(Number(frameno) *Number(engineno))
    this.setData({
      frameno1: frameno1

    })
  },
  //年息
  frameno1Input: function (e) {
    this.setData({
      frameno1: e.detail.value
    })
    var that = this;
    var frameno1 = that.data.frameno1;
    var engineno = that.data.engineno;
    var frameno = Math.abs(Number(frameno1) /  Number(engineno))
    this.setData({
      frameno: frameno,
      frameno1: frameno1

    })
    console.log(frameno)
    console.log(frameno1)
    console.log(engineno)

  },
  //发动机号后六位赋值
  enginenoInput: function (e) {
    this.setData({
      engineno: e.detail.value
    })
    var that = this;
    var frameno1 = that.data.frameno1;
    var engineno = that.data.engineno;
    var frameno = Math.abs(Number(frameno1) / Number(engineno))
    this.setData({
      frameno: frameno,
      frameno1: frameno1

    })
  },
  //发动机号后六位赋值
  engineno1Input: function (e) {
    this.setData({
      engineno1: e.detail.value
    })
  },
  sbumitBtn: function () {
    var that = this;
    var lsnum = that.data.lsnum;
    var lsnum2 = that.data.lsnum2;
    var insurance = that.data.insurance; 
    var frameno = that.data.frameno;
    var frameno1 = that.data.frameno1;
    var engineno = that.data.engineno;
    var gps = that.data.gps;
    var fanyong = that.data.fanyong;

    var result1 = Math.abs(Number(lsnum) * Number(lsnum2))
    var result2 = Math.abs(Number(result1)*10000 + Number(insurance) + Number(gps))
    var result3 = Math.abs(Number(result2) * (1+Number(frameno1)))
    var result4 = Math.abs(Number(result3) / Number(engineno))
    var result5 = Math.abs(Number(result2) * Number(fanyong))
    var result6 = Math.abs(Number(result3) / Number(1.1083))

    that.setData({
      result1: result1 + '万元',
      result2: result2 + '元',
      result3: result3 + '元',
      result4: result4 + '元',
      result5: result5 + '元',
      result6: result6 + '元',
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      lsnum: '',//车牌号

      frameno: '',//车架号后六位
      engineno: '36',//发动机号后六位
      engineno1: '',//发动机号后六位

      queryResults: '',//查询结果
      violationTimes: '',//违章次数
      totalDeduction: '',//总扣分
      totalFine: '',//总罚款
      list: []
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})