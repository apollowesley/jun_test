var app = getApp();
const pathUrl = app.globalData.pathUrl;
var myDate = new Date();
var myDate = myDate.toLocaleDateString().split('/').join('-');
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        startDate: myDate,
      bpmStatus: '',
      createBy: '',
      createDate: '',
      createName: '',
      fbAmount: '',
      fbDate: '',
      fbRemark: '',
      fbStatus: '',
      fkAmount: '',
      fkAttaNo: '',
      fkBy1: '',
      fkBy2: '',
      fkBy3: '',
      fkBy4: '',
      fkBy5: '',
      fkBy6: '',
      fkBy7: '',
      fkDate: '',
      fkMethod: '',
      fkRemark: '',
      id: '',
      orderNo: '',
      sysCompanyCode: '',
      sysOrgCode: '',
      updateBy: '',
      updateDate: '',
      updateName: ''

    },
    onLoad: function(e) {
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId,
            orderNo: e.orderNo,
            id: e.id
        });
      this.orderMainController();
    },

  orderMainController: function () {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/fxjLoanFkController/listfkme/' + that.data.orderNo,
      data: {

      },
      success: function (res) {
        console.log(res)
        if (res.data.ok) {
          var data = res.data.data;
   
          that.setData({
            bpmStatus: data.bpmStatus,
            createBy: data.createBy,
            createDate: data.createDate,
            createName: data.createName,
            fbAmount: data.fbAmount,
            fbDate: data.fbDate,
            fbRemark: data.fbRemark,
            fbStatus: data.fbStatus,
            fkAmount: data.fkAmount,
            fkAttaNo: data.fkAttaNo,
            fkBy1: data.fkBy1,
            fkBy2: data.fkBy2,
            fkBy3: data.fkBy3,
            fkBy4: data.fkBy4,
            fkBy5: data.fkBy5,
            fkBy6: data.fkBy6,
            fkBy7: data.fkBy7,
            fkDate: data.fkDate,
            fkMethod: data.fkMethod,
            fkRemark: data.fkRemark,
            id: data.id,
            orderNo: data.orderNo,
            sysCompanyCode: data.sysCompanyCode,
            sysOrgCode: data.sysOrgCode,
            updateBy: data.updateBy,
            updateDate: data.updateDate,
            updateName: data.updateName
          })
        } else {
          wx.showModal({
            title: '提示',
            content: res.data.respMsg,
            confirmColor: '#7aa6d1',
            showCancel: false
          })
        }
      }
    })
  },

    //选择预约日期
    bindDateChange: function(e) {
        this.setData({
            date: e.detail.value
        })
        console.log(this.data.date)
    },
    //选择预约时间
    bindTimeChange: function(e) {
        this.setData({
            time: e.detail.value
        })
        console.log(this.data.time)
    },
    goOrderManagement: function() {
        var that = this;
        // if (that.data.date == '') {
        //     return wx.showToast({
        //         title: '请选择收到日期',
        //         icon: 'none',
        //         mask: true,
        //         duration: 1000
        //     })
        // }
        // if (that.data.time == '') {
        //     return wx.showToast({
        //         title: '请选择收到时间',
        //         icon: 'none',
        //         mask: true,
        //         duration: 1000
        //     })
        // }
        wx.request({
          url: pathUrl + '/rest/fxjLoanFkController/updatefkme/' + that.data.id,
            data: {
                updateBy: that.data.openId,
                id: that.data.id,
                orderNo: that.data.orderNo,
               fbStatus: 'Y'

            },
            method: 'PUT',
            success: function(res) {
                console.log(res);
                if (res.data.ok) {
                    wx.showToast({
                        title: '提交成功',
                        duration: 1000,
                        mask: true,
                        success: function() {
                            setTimeout(function() {
                                wx.switchTab({
                                    url: "pages/orderManagement/orderManagement"
                                })
                            }, 1000);
                        }
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
    onReady: function() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
})