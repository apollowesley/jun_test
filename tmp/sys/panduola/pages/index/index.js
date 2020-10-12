var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        openId: '',
        list: [],
        pageNumber: 0,
        loading: false, //"上拉加载"的变量，默认false，隐藏  
        loadingComplete: false,  //“没有数据”的变量，默认false，隐藏  
    },
    onLoad: function () {
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId
        });
        // this.onReachBottom();
    },
    // goStep: function (e) {
    //     var proNo = e.currentTarget.dataset.prono;
    //     var proName = e.currentTarget.dataset.proname;
    //     wx.navigateTo({
    //         url: "/pages/posXjOne/posXjOne?proNo=" + proNo + '&proName=' + proName
    //     })
    // },
     /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        // wx.showLoading({
        //     title: '加载中',
        // })
        // var that = this;
        // that.setData({
        //     pageHidden: true,
        //     bpmStatus: '异常',
        //     loadingComplete: false, // 把“没有数据”设为true，显示  
        //     loading: false, // 把"上拉加载"的变量设为false，隐藏  
        //     pageNumber: 0,
        //     list: []
        // })
        // this.onReachBottom();
    },
    // 巡检
    goPosXj:function(){
        wx.navigateTo({
            url: "/pages/dianming/dianming"
        });
    },
    // 开点
    goIncomingPorts:function(e){
        var proNo = e.currentTarget.dataset.prono;
        var proName = e.currentTarget.dataset.proname;
        wx.navigateTo({
            url: '/pages/kaidian/kaidian?proNo=' + proNo + '&proName=' + proName,
        });
    },
    // 统计
    goPosInstall: function () {
        wx.navigateTo({
            url: "/pages/count/count"
        });
    },
  goPosSearch: function () {
    wx.navigateTo({
      url: "/pages/cxList/cxList"
    });
  },
    /* 生命周期函数--监听页面隐藏 */
    onHide: function () {},
    /* 生命周期函数--监听页面卸载 */
    onUnload: function () { },

    /* 页面相关事件处理函数--监听用户下拉动作 */
    onPullDownRefresh: function () {
        // wx.showLoading({
        //     title: '加载中',
        // })
        // var that = this;
        // that.setData({
        //     loadingComplete: false, //把“没有数据”设为true，显示  
        //     loading: false,//把"上拉加载"的变量设为false，隐藏  
        //     pageNumber: 0,
        //     bpmStatus: '异常',
        //     list: []
        // });
        // this.onReachBottom();
        // wx.stopPullDownRefresh();
    },
    /* 页面上拉触底事件的处理函数 */
    onReachBottom: function () {
        // var that = this;
        // var pageNumber = that.data.pageNumber + 1;
        // var allList = that.data.list;
        // wx.request({
        //     url: pathUrl + '/rest/posXjtaskController/list/' + that.data.openId,
        //     data: {
        //         bpmStatus: '异常',
        //         pageNumber: pageNumber,
        //         pageSize: 10
        //     },
        //     success: function (res) {
        //         if (res.data.ok || res.data.data) {
        //             wx.hideLoading()
        //             if (res.data.data.length > 0) {
        //                 allList = allList.concat(res.data.data);
        //                 if (res.data.data.length < 10) {
        //                     that.setData({
        //                         pageNumber: pageNumber,
        //                         list: allList,
        //                         loadingComplete: true, //把“没有数据”设为true，显示  
        //                         loading: false //把"上拉加载"的变量设为false，隐藏  
        //                     })
        //                 } else {
        //                     that.setData({
        //                         pageNumber: pageNumber,
        //                         list: allList,
        //                         loading: true
        //                     })
        //                 }
        //             } else {
        //                 wx.hideLoading();
        //                 that.setData({
        //                     list: allList,
        //                     loadingComplete: true, //把“没有数据”设为true，显示  
        //                     loading: false //把"上拉加载"的变量设为false，隐藏  
        //                 })
        //             }
        //         } else {
        //             wx.showModal({
        //                 title: '提示',
        //                 content: '获取数据失败',
        //                 confirmColor: '#7aa6d1',
        //                 showCancel: false
        //             })
        //         }
        //     }
        // })
    },
    /* 用户点击右上角分享 */
    onShareAppMessage: function () { }
})