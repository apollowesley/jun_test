var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
      bpmStatus: '',
      bxFy: '',
      bxRq: '',
      carChengshu: '',
      carPinggujia: '',
      carRongzie: '',
      conJine: '',
      createBy: '',
      createDate: '',
      createName: '',
      cusId: '',
      cusMobile: '',
      cusName: '',
      daoshouJine: '',
      fkBy1: '',
      fkBy10: '',
      fkBy11: '',
      fkBy12: '',
      fkBy13: '',
      fkBy2: '',
      fkBy3: '',
      fkBy4: '',
      fkBy5: '',
      fkBy6: '',
      fkBy7: '',
      fkBy8: '',
      fkBy9: '',
      gpsFy: '',
      id: '',
      liulFy: '',
      loanAmount: '',
      loanAtta: '',
      loanDate: '',
      loanDbmonth: '',
      loanMonth: '',
      loanMonthSum: '',
      loanPeriod: '',
      loanReMethod: '',
      loanRemark: '',
      loanSbamount: '',
      loanSum: '',
      luqiaoRq: '',
      lydbFuwfei: '',
      nianjianRq: '',
      orderNo: '',
      qitaFy: '',
      sysCompanyCode: '',
      sysOrgCode: '',
      updateBy: '',
      updateDate: '',
      updateName: '',
      ygJine: '',
        list:[]
    },
    onLoad: function (e) {
        var orderNo = e.orderNo;
        this.setData({
            orderNo: orderNo
        })
        this.loanFkController();
        this.plReplanController();
    },
    loanFkController:function(){
        var that = this;
        wx.request({
            url: pathUrl + '/rest/fxjLoanFkController/' + that.data.orderNo,
            success: function (res) {
                if (res.data.ok) {
                    that.setData({
                      bpmStatus: res.data.data.bpmStatus,
                      bxFy: res.data.data.bxFy,
                      bxRq: res.data.data.bxRq,
                      carChengshu: res.data.data.carChengshu,
                      carPinggujia: res.data.data.carPinggujia,
                      carRongzie: res.data.data.carRongzie,
                      conJine: res.data.data.conJine,
                      createBy: res.data.data.createBy,
                      createDate: res.data.data.createDate,
                      createName: res.data.data.createName,
                      cusId: res.data.data.cusId,
                      cusMobile: res.data.data.cusMobile,
                      cusName: res.data.data.cusName,
                      daoshouJine: res.data.data.daoshouJine,
                      fkBy1: res.data.data.fkBy1,
                      fkBy10: res.data.data.fkBy10,
                      fkBy11: res.data.data.fkBy11,
                      fkBy12: res.data.data.fkBy12,
                      fkBy13: res.data.data.fkBy13,
                      fkBy2: res.data.data.fkBy2,
                      fkBy3: res.data.data.fkBy3,
                      fkBy4: res.data.data.fkBy4,
                      fkBy5: res.data.data.fkBy5,
                      fkBy6: res.data.data.fkBy6,
                      fkBy7: res.data.data.fkBy7,
                      fkBy8: res.data.data.fkBy8,
                      fkBy9: res.data.data.fkBy9,
                      gpsFy: res.data.data.gpsFy,
                      id: res.data.data.id,
                      liulFy: res.data.data.liulFy,
                      loanAmount: res.data.data.loanAmount,
                      loanAtta: res.data.data.loanAtta,
                      loanDate: res.data.data.loanDate,
                      loanDbmonth: res.data.data.loanDbmonth,
                      loanMonth: res.data.data.loanMonth,
                      loanMonthSum: res.data.data.loanMonthSum,
                      loanPeriod: res.data.data.loanPeriod,
                      loanReMethod: res.data.data.loanReMethod,
                      loanRemark: res.data.data.loanRemark,
                      loanSbamount: res.data.data.loanSbamount,
                      loanSum: res.data.data.loanSum,
                      luqiaoRq: res.data.data.luqiaoRq,
                      lydbFuwfei: res.data.data.lydbFuwfei,
                      nianjianRq: res.data.data.nianjianRq,
                      orderNo: res.data.data.orderNo,
                      qitaFy: res.data.data.qitaFy,
                      sysCompanyCode: res.data.data.sysCompanyCode,
                      sysOrgCode: res.data.data.sysOrgCode,
                      updateBy: res.data.data.updateBy,
                      updateDate: res.data.data.updateDate,
                      updateName: res.data.data.updateName,
                      ygJine: res.data.data.ygJine,
                    })
                    if (res.data.data.loanReMethod == '10'){
                        that.setData({
                            loanReMethod: '等额本金'
                        })
                    }else if (res.data.data.loanReMethod == '20'){
                        that.setData({
                            loanReMethod: '等额本息'
                        })
                    } else if (res.data.data.loanReMethod == '30') {
                        that.setData({
                            loanReMethod: '先息后本'
                        })
                    }
                } else {
                    wx.showModal({
                        title: '提示',
                        content: '获取数据失败',
                        confirmColor: '#7aa6d1',
                        showCancel: false
                    })
                }
            }
        })
    },
    plReplanController:function(){
        var that = this;
        console.log(that.data.orderNo)
        wx.request({
            url: pathUrl + '/rest/fxjPlReplanController/listbyorderNo/' + that.data.orderNo,
            data: {
                pageNumber: 1,
                pageSize: 1000
            },
            success: function (res) {
                console.log(res)
                if (res.data.ok) {
                    that.setData({
                        list:res.data.data
                    })
                } else {
                    wx.showModal({
                        title: '提示',
                        content: '获取数据失败',
                        confirmColor: '#7aa6d1',
                        showCancel: false
                    })
                }
            }
        })
    },
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

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