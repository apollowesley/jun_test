var app = getApp();
const Utils = require('../../utils/util.js')

const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pageHidden: true,
        pathUrl: pathUrl,
        orderStatusType: 0,
        tabBars: [
            { name: '未完成' },
            { name: '已完成' },
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
      console.log(e)
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
        // wx.navigateTo({
        //   url: "/pages/fkOrder/fkOrder?orderNo=" + orderNo 
        // })
      wx.navigateTo({
        url: "/pages/approvalLog/approvalLog?orderNo=" + orderNo
      })
      // wx.navigateTo({
      //   url: "/pages/editOrder/editOrder?orderNo=" + orderNo
      // })
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
      var proName = e.currentTarget.dataset.proname;
      var orderBy38 = e.currentTarget.dataset.orderby38;
      var mpUrl = e.currentTarget.dataset.mpurl;
      var bpmStatus = e.currentTarget.dataset.bpmstatus;

        var uid = Utils.getUid();
        console.log(mpUrl + "?orderNo=" + orderNo + '&id=' + id);
      var a = mpUrl + "?orderNo=" + orderNo + '&id=' + id
      if (bpmStatus =='30'){
        console.log("1")
      if (orderBy38 == "1" || orderBy38 == "2"){
        console.log("2")

        wx.navigateTo({
          url: mpUrl + "?orderNo=" + orderNo + '&id=' + id + '&proNo=' + proNo + '&proName=' + proName
        })
      }else{
        console.log("3")

        wx.navigateTo({
          url: "/pages/approvalLog/approvalLog"	 + "?orderNo=" + orderNo + '&id=' + id + '&proNo=' + proNo + '&proName=' + proName
        })
      }
      }else{
        console.log("4")

        wx.navigateTo({
          url: mpUrl + "?orderNo=" + orderNo + '&id=' + id + '&proNo=' + proNo + '&proName=' + proName
        })
      }
     
      // if (orderBy80 == "1"){

      //   wx.navigateTo({
      //     url: `/pages/goctrlc/goctrlc` + "?orderNo=" + orderNo + '&id=' + id + '&proNo=' + proNo + '&value=' + value
      //   });
      //   console.log('123')

      //   }else{
      //     wx.navigateTo({
      //       url: mpUrl + "?orderNo=" + orderNo + '&id=' + id + '&proNo=' + proNo + '&value=' + value
      //     })
      //     console.log('ssas')
      //   }
        
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
            orderStatusType:that.data.orderStatusType,
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
                    var arr = [];
                    if(res.data.data.length > 0){
                        if (that.data.orderStatusType == '2'){
                            for(let i=0;i<res.data.data.length;i++){
                                if (res.data.data[i].orderStatus != "已删除"){
                                    arr = arr.concat(res.data.data[i])
                                }
                            }
                            allList = allList.concat(arr);
                            if (res.data.data.length < 10) {
                                that.setData({
                                    pageNumber: pageNumber,
                                    list: allList,
                                    loadingComplete: true, //把“没有数据”设为true，显示  
                                    loading:false //把"上拉加载"的变量设为false，隐藏  
                                })
                            } else {
                                that.setData({
                                    pageNumber: pageNumber,
                                    list: allList,
                                    loading: false
                                })
                            }
                        }
                        else{
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
                                    loading: false
                                })
                            }
                        }


                        
                    }else {
                        that.setData({
                            loadingComplete: true, //把“没有数据”设为true，显示  
                            loading:false //把"上拉加载"的变量设为false，隐藏  
                        })
                    }
                } else {
                    wx.hideLoading();
                    that.setData({
                        pageHidden: true
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