var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
      lsnum: '10',//估值/万
      lsnum2: '7',//估值/万
      frameno: '6.8',//利息/厘
      engineno: '36',//申请期数/月
       engineno1:'10.43',//银行利息
     
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
    //车牌号赋值
    lsnumInput: function (e) {
        this.setData({
            lsnum: e.detail.value
        })
    },
  //车牌号赋值
  lsnum2Input: function (e) {
    this.setData({
      lsnum2: e.detail.value
    })
  },
    //车架号后六位赋值
    framenoInput: function (e) {
        this.setData({
            frameno: e.detail.value
        })
    },
    //发动机号后六位赋值
    enginenoInput: function (e) {
        this.setData({
            engineno: e.detail.value
        })
    },
  //发动机号后六位赋值
  engineno1Input: function (e) {
    this.setData({
      engineno1: e.detail.value
    })
  },
    sbumitBtn: function () {
      var aa = Math.floor(Math.random() * 50 + 10); 
      var that = this;
      var guzhi = that.data.lsnum;
      var chengshu = that.data.lsnum2;
      var lixi = that.data.frameno;
      var qishu = that.data.engineno;
      var yhflx = that.data.engineno1;
      var chbd = Math.floor((guzhi * chengshu / 10) / (1+(lixi*qishu-yhflx*10)/1000)*100)/100;
      var yinhangshenqingdai = Math.floor((chbd) * (1 + (lixi * qishu - yhflx * 10) / 1000) * 100) / 100;
  
      var khhk = Math.floor(yinhangshenqingdai * 100 * yhflx / qishu) + Math.floor(yinhangshenqingdai * 10000  / qishu);
      console.log(Math.floor(yinhangshenqingdai * 100 * yhflx / qishu))
      console.log(Math.floor(yinhangshenqingdai * 10000 / qishu))
      that.setData({
        queryResults: chbd+'万元',//违章次数
        violationTimes: yinhangshenqingdai + '万元',
        queryResults2: khhk + '元',//客户每月还款
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
            lsnum: '10',//车牌号
        
            frameno: '6.8',//车架号后六位
            engineno: '36',//发动机号后六位
          engineno1: '10.43',//发动机号后六位

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