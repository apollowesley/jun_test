var app = getApp();
const pathUrl = app.globalData.pathUrl;
var myDate = new Date();
var myDate = myDate.toLocaleDateString().split('/').join('-');
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        startDate: myDate,
        date: '',
        time: '',
        orderNo: '',
        id: ''
    },
    onLoad: function(e) {
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId,
            orderNo: e.orderNo,
            id: e.id
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
        if (that.data.date == '') {
            return wx.showToast({
                title: '请选择预约日期',
                icon: 'none',
                mask: true,
                duration: 1000
            })
        }
        if (that.data.time == '') {
            return wx.showToast({
                title: '请选择预约时间',
                icon: 'none',
                mask: true,
                duration: 1000
            })
        }
        wx.request({
            url: pathUrl + '/rest/fxjOrderMainController/' + that.data.id,
            data: {
                updateBy: that.data.openId,
                id: that.data.id,
                orderNo: that.data.orderNo,
                orderOper: 'Y',
                bmDate: that.data.data,
                bmTime: that.data.time
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