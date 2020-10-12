var app = getApp();
const Utils = require('../../utils/util.js');
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pageHidden: true,
        pathUrl: pathUrl,
        list: [],
        id:'',
        openId:'',
        classNumber:'',
        className:'',
        pageNumber: 0,
        loading: false, //"上拉加载"的变量，默认false，隐藏  
        loadingComplete: false,  //“没有数据”的变量，默认false，隐藏 id=402880ec6a219d00016a21a0ab330002&classNumber=undefined&className=潘多拉一班
    },
    onLoad: function (options) {
        console.log(options.className)
        var openId = wx.getStorageSync('openId');
        var id = options.id;
        var classNumber = options.classNumber;
        var className = options.className;
        this.setData({
            openId: openId,
            id: id,
            classNumber: classNumber,
            className: className
        });
        wx.setNavigationBarTitle({
            title: options.className,
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
            orderStatusType: 0,
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
        wx.showLoading({
            title: '加载中',
        })
        var that = this;
        that.setData({
            loadingComplete: false, //把“没有数据”设为true，显示  
            loading: false,//把"上拉加载"的变量设为false，隐藏  
            pageNumber: 0,
            orderStatusType: that.data.orderStatusType,
            list: []
        });
        this.onReachBottom();
        wx.stopPullDownRefresh();
    },

    /**
     * 页面上拉触底事件的处理函数 id=402880ec6a219d00016a21a0ab330002&classNumber=003&className=潘多拉一班
     */
    onReachBottom: function (e) {
        var that = this;
        var pageNumber = that.data.pageNumber + 1;
        var allList = that.data.list;
        wx.request({
            url: pathUrl + '/rest/panduolaNameListController/list/' + that.data.openId,
            data: {
                className: that.data.classNumber,
                pageNumber: pageNumber,
                pageSize: 20
            },
            success: function (res) {
                console.log(res);
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