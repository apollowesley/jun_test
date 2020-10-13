var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        orderNo:'',
        loglist:[]
    },
    onLoad: function (e) {
        this.setData({
            orderNo: e.orderNo
        })
        console.log(this.data.orderNo)
        this.orderMainController();
    },
    //审批日志
    orderMainController: function () {
        var that = this;
        wx.request({
            url: pathUrl + '/rest/fxjOrderMainController/loglist/' + that.data.orderNo,
            data: {
                pageNumber: 1,
                pageSize: 100
            },
            success: function (res) {
                console.log(res);
                if (res.data.ok) {
                    that.setData({
                        loglist: res.data.data
                    })
                    console.log(that.data.loglist)
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
        this.orderMainController();
        wx.stopPullDownRefresh();
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