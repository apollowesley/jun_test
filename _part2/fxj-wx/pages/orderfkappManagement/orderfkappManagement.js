var app = getApp();
const Utils = require('../../utils/util.js')

const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pageHidden: true,
        pathUrl: pathUrl,
        orderStatusType: 2,
        tabBars: [
            // { name: '未完成' },
            // { name: '已完成' },
            { name: '全部' }
        ],
        list:[],
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
    clickTab:function(e){
        var that = this;
        if (this.data.orderStatusType === e.currentTarget.dataset.index) {
            return false;
        } else {
            wx.showLoading({
                title: '加载中',
            })
            that.setData({
                orderStatusType: e.currentTarget.dataset.index,
                loadingComplete: false, //把“没有数据”设为true，显示  
                loading: false,//把"上拉加载"的变量设为false，隐藏  
                pageNumber: 0,
                list: []
            })
            this.onReachBottom();
        }

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
    //审批日志
    goApprovalLog: function (e) {
        var orderNo = e.currentTarget.dataset.orderno;
        wx.navigateTo({
            url: "/pages/approvalLog/approvalLog?orderNo=" + orderNo
        })
    },
    gofkinfo:function(e){
      var orderNo = e.currentTarget.dataset.orderno;
      var id = e.currentTarget.dataset.id;
      wx.navigateTo({
        url: "/pages/applicationPay/applicationPay?orderNo=" + orderNo + '&id=' + id
      })
    },
    //方案确认
    goCaseConfirm:function (e) {
        var orderNo = e.currentTarget.dataset.orderno;
        var id = e.currentTarget.dataset.id;
        wx.navigateTo({
            url: "/pages/paymentScheduleConf/paymentScheduleConf?orderNo=" + orderNo + '&id=' + id
        })
    },

    //撤销申请
    goDischargePetition: function (e) {
        console.log(e);
        var orderNo = e.currentTarget.dataset.orderno;
        var id = e.currentTarget.dataset.id;
        wx.navigateTo({
            url: "/pages/dischargePetition/dischargePetition?orderNo=" + orderNo + '&id=' + id
        })
    },
    //预约面签
    goSignedFace:function(e){
        console.log(e);
        var orderNo = e.currentTarget.dataset.orderno;
        var id = e.currentTarget.dataset.id;
        var proNo = e.currentTarget.dataset.prono;
        var mpUrl = e.currentTarget.dataset.mpurl;
        var uid = Utils.getUid();

        console.log(mpUrl + "?orderNo=" + orderNo + '&id=' + id);
      if (mpUrl =="/pages/meeting/meeting"){

        wx.navigateTo({
          url: `/pages/meeting/meeting?channel=${orderNo}&uid=${uid}&role=broadcaster`
        });

        }else{
          wx.navigateTo({
            url: mpUrl + "?orderNo=" + orderNo + '&id=' + id + '&proNo=' + proNo
          })
        }
        
    },
    //补充材料
    goannex: function () {
        wx.navigateTo({
            url: "/pages/annex/annex"
        })
    },
    //上访拍照
    goPetitionPhotos: function () {
        wx.navigateTo({
            url: "/pages/petitionPhotos/petitionPhotos"
        })
    },
    //视频验车
    goVideoInspection: function () {
        wx.navigateTo({
            url: "/pages/videoInspection/videoInspection"
        })
    },
    //还款计划表
    goPaymentSchedule: function (e) {
        var orderNo = e.currentTarget.dataset.orderno;
        wx.navigateTo({
            url: "/pages/paymentSchedule/paymentSchedule?orderNo=" + orderNo
        })
        //GET /rest/fxjLoanFkController/{orderNo} 上面放款资料
        //GET /rest/fxjPlReplanController/listbyorderNo/{orderNo} 计划列表
    },
    //结果
    goResultNotification: function () {
        wx.navigateTo({
            url: "/pages/resultNotification/resultNotification"
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
        var that =  this;
        that.setData({
            pageHidden: true,
            orderStatusType: 2,
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
                        orderStatusType:2,

            // orderStatusType:that.data.orderStatusType,
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
                    if(res.data.data.length > 0){
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
                    }else {
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