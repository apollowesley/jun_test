var app = getApp();
const Utils = require('../../utils/util.js');
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pageHidden: true,
        pathUrl: pathUrl,
        list: [],
        pageNumber: 0,
        loading: false, //"上拉加载"的变量，默认false，隐藏  
        loadingComplete: false,  //“没有数据”的变量，默认false，隐藏  
    },
    onLoad: function (options) {
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId
        });
        // this.onReachBottom();
    },
    // 历史点名
    goHistoryDm:function(e){
        console.log(e)
        var id = e.currentTarget.dataset.orderno;
        var classNumber = e.currentTarget.dataset.classnumber;
        var className = e.currentTarget.dataset.classname
        wx.navigateTo({
            url: '/pages/dmHistoryList/dmHistoryList?id=' + id + '&classNumber=' + classNumber + '&className=' + className,
        })
    },
    // 完成点名
    goFinishDm:function(e){
        // console.log(e.currentTarget.dataset.classnumber);
        wx.showModal({
            title: '提示',
            content: '是否结束点名！',
            success(res) {
                if (res.confirm) {
                    console.log('用户点击确定');
                    var that = this;
                    wx.request({
                        url: pathUrl + '/rest/panduolaClassController/updatecom/' + e.currentTarget.dataset.classnumber,
                        success: function (res) {
                            if (res.data.ok) {
                                wx.showModal({
                                    title: '提示',
                                    content: '结束点名成功',
                                    confirmColor: '#7aa6d1',
                                    showCancel: false,
                                    success(res) {
                                        if (res.confirm) {
                                            this.onPullDownRefresh();
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else if (res.cancel) {
                    console.log('用户点击取消')
                }
            }
        })
    },
    // 开始点名
    goStartDm:function(e){
        var id = e.currentTarget.dataset.orderno;
        wx.navigateTo({
            url: '/pages/dmList/dmList?id='+id,
        })
    },

  //上访拍照
  goPetitionPhotos: function (e) {
    console.log(e);
    var orderNo = e.currentTarget.dataset.id;
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: "/pages/petitionPhotos/petitionPhotos?orderNo=" + orderNo + '&id=' + id
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
        wx.showLoading({
            title: '加载中',
        })
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
            url: pathUrl + '/rest/panduolaClassController/list/' + that.data.openId,
            data: {
                pageNumber: pageNumber,
                pageSize: 10
            },
            success: function (res) {
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