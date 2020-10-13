var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        orderNo:'',
        id:'',
        loglist:[]
    },
    onLoad: function (e) {
        this.setData({
            orderNo: e.orderNo,
            id: e.id
        })
        this.orderMainController();
    },
    //撤销申请列表
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
    definiteRevocation: function () {
        var that = this;
        wx.showModal({
            title: '提示',
            content: '是否确定撤销申请？',
            cancelText: '取消',
            confirmText: '确认',
            confirmColor: '#3c8cf0',
            success: function (res) {
                if (res.confirm) {
                    wx.request({
                        url: pathUrl + '/rest/fxjOrderMainController/' + that.data.id,
                        data:{
                            id: that.data.id,
                            orderNo: that.data.orderNo,
                            updateBy: that.data.openId,

                            orderOper: 'R',
                        },
                        method:'PUT',
                        success: function (res) {
                            console.log(res);
                            if (res.data.ok) {
                                wx.showToast({
                                    title: '撤销成功',
                                    duration: 1000,
                                    mask: true,
                                    success: function () {
                                        setTimeout(function () {
                                            wx.switchTab({
                                                url: "/pages/orderManagement/orderManagement"
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
                } 
            }
        })
    },
    depositDraft: function () {
        var that = this;
        wx.showModal({
            title: '提示',
            content: '是否确定存入草稿箱？',
            cancelText: '取消',
            confirmText: '确认',
            confirmColor: '#3c8cf0',
            success: function (res) {
                if (res.confirm) {
                    wx.request({
                        url: pathUrl + '/rest/fxjOrderMainController/' + that.data.id,
                        data: {
                            id: that.data.id,
                            orderNo: that.data.orderNo,
                          updateBy: that.data.openId,

                            orderOper: 'N',
                        },
                        method: 'PUT',
                        success: function (res) {
                            console.log(res);
                            if (res.data.ok) {
                                wx.showToast({
                                    title: '撤销成功',
                                    duration: 1000,
                                    mask: true,
                                    success: function () {
                                        setTimeout(function () {
                                            wx.switchTab({
                                                url: "/pages/orderManagement/orderManagement"
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