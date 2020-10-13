var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        array: [],
        index: 0,
        msgtype:'咨询',
        msgcontent:'',
        cusName:'',
        cusMobile:''
    },
    onLoad: function (options) {
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId
        })
        this.theme();
    },
    theme: function () {
        var that = this;
        wx.request({
            url: pathUrl + '/rest/base/getDictValue/re_type',
            success: function (res) {
                console.log(res.data)
                if (res.data.ok || res.data.data) {
                    var newArray = [];
                    for (var i = 0; i < res.data.data.length; i++) {
                        newArray.push(
                            res.data.data[i].typename
                        );
                    }
                    that.setData({
                        array: newArray
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
    bindPickerChange: function (e) {
        var that =  this;
        var index = e.detail.value;
        that.setData({
            index: index,
            msgtype: that.data.array[index]
        })
    },
    cusNameInput: function (e) {
        this.setData({
            cusName: e.detail.value
        })
    },
    cusMobileInput: function (e) {
        this.setData({
            cusMobile: e.detail.value
        })
    },
    msgcontentInput:function(e){
        this.setData({
            msgcontent: e.detail.value
        })
    },
    submitBtn:function(){
        var that = this;
        wx.request({
            url: pathUrl + '/rest/fxjCrmMessgaeController',
            data:{
                msgType: that.data.msgtype,
                msgContent: that.data.msgcontent,
                cusMobile: that.data.cusMobile,
                cusName: that.data.cusName
            },
            method:'POST',
            success: function (res) {
                console.log(res.data)
                if (res.data.ok) {
                    wx.showToast({
                        title: '提交成功',
                        duration: 500,
                        mask: true,
                        success: function () {
                            setTimeout(function () {
                                wx.navigateBack({
                                    delta: 1
                                })
                            }, 500);
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