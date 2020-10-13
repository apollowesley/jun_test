var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pageHidden: true,
        pathUrl: pathUrl,
        orderStatusType: 3,
        list: [],
        pageNumber: 0,
        loading: false, //"上拉加载"的变量，默认false，隐藏  
        loadingComplete: false,  //“没有数据”的变量，默认false，隐藏  
    },
    onLoad: function (options) {
      var openId = wx.getStorageSync('openId');
      this.setData({
        openId: openId
      })
    },
    //客户资料
    goCustomerInfo: function (e) {
        console.log(e);
        var orderNo = e.currentTarget.dataset.orderno;
        var proNo = e.currentTarget.dataset.prono;
        wx.navigateTo({
            url: "/pages/customerInfo/customerInfo?orderNo=" + orderNo + '&proNo=' + proNo
        })
        //GET /rest/fxjOrderMainController/{orderNo}
    },
    //提交申请
    submit: function (e) {
        var orderNo = e.currentTarget.dataset.orderno;
        var id = e.currentTarget.dataset.id;
        var that = this;
        console.log("orderNo：" + orderNo)
        console.log("id：" + id)
        wx.showModal({
            title: '提示',
            content: '是否确定提交申请？',
            cancelText: '取消',
            confirmText: '确认',
            confirmColor: '#3c8cf0',
            success: function (res) {
                if (res.confirm) {
                    wx.request({
                        url: pathUrl + '/rest/fxjOrderMainController/' + id,
                        data: {
                            id: id,
                            orderNo: orderNo,
                            orderOper: 'Y'
                        },
                        method: 'PUT',
                        success: function (res) {
                            console.log(res);
                            if (res.data.ok) {
                                wx.showToast({
                                    title: '提交成功',
                                    duration: 1000,
                                    mask: true,
                                    success: function () {
                                        setTimeout(function () {
                                            that.onPullDownRefresh();
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
    //继续申请
    continueToApply:function(e){
        var orderNo = e.currentTarget.dataset.orderno;
        var proNo = e.currentTarget.dataset.prono;
        var id = e.currentTarget.dataset.id;
        wx.navigateTo({
            url: "/pages/editOrder/editOrder?orderNo=" + orderNo + '&proNo=' + proNo + '&id=' + id
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
        wx.showLoading({
            title: '加载中',
        })
        var that = this;
        that.setData({
            pageHidden: true,
            loadingComplete: false, //把“没有数据”设为true，显示  
            loading: false,//把"上拉加载"的变量设为false，隐藏  
            pageNumber: 0,
            list: []
        })
        this.onReachBottom();
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
        var that = this;
        that.setData({
            loadingComplete: false, //把“没有数据”设为true，显示  
            loading: false,//把"上拉加载"的变量设为false，隐藏  
            pageNumber: 0,
            list: []
        });
        this.onReachBottom();
        wx.stopPullDownRefresh();
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        var that = this;
        var pageNumber = that.data.pageNumber + 1;
        var allList = that.data.list;
        wx.request({
            url: pathUrl + '/rest/fxjOrderMainController/list/' + that.data.openId,
            data: {
                orderStatusType: that.data.orderStatusType,
                pageNumber: pageNumber,
                pageSize: 10
            },
            success: function (res) {
                console.log(res.data)
                if (res.data.ok) {
                    wx.hideLoading();
                    that.setData({
                        pageHidden: false
                    })
                    if (res.data.data.length > 0) {
                        allList = allList.concat(res.data.data);
                        if (res.data.data.length < 10) {
                            that.setData({
                                pageNumber: pageNumber,
                                list: allList,
                                loadingComplete: true, //把“没有数据”设为true，显示  
                                loading: false //把"上拉加载"的变量设为false，隐藏  
                            })
                        } else {
                            that.setData({
                                pageNumber: pageNumber,
                                list: allList,
                                loading: true
                            })
                        }
                    } else {
                        that.setData({
                            loadingComplete: true, //把“没有数据”设为true，显示  
                            loading: false //把"上拉加载"的变量设为false，隐藏  
                        })
                    }
                } else {
                    wx.hideLoading();
                    that.setData({
                        pageHidden: false
                    })
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

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})