var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        proNo: '',
        detailInfo: '',
        phoneNumber: '',
        proName: '',
        proMark:'',
        checkVal:''
    },
    onLoad: function (e) {
        var openId = wx.getStorageSync('openId');
        var proNo = e.proNo;
        var proMark = e.proMark;
        var checkVal = e.checkVal;
        var id = e.id;
        this.setData({
            proNo: proNo,
            openId: openId,
            proMark: proMark,
            checkVal: checkVal
        })
        console.log("proNo:" + proNo);
        this.detailInfo();
        
    },

    //获取详情
    detailInfo: function () {
        var that = this;
        wx.request({
            url: pathUrl + '/rest/posXjtaskController/' + that.data.proNo,
            success: function (res) {
                if (res.data.ok || res.data.data) {
                    that.setData({
                        detailInfo: res.data.data,
                        phoneNumber: res.data.data.posMobile,
                        posName: res.data.data.posName
                    })
                    wx.setNavigationBarTitle({
                        title: that.data.posName
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
    previewImage: function (e) {
        var that = this;
        var listIdx = e.currentTarget.dataset.listIdx;
        var imgIdx = e.currentTarget.dataset.imgIdx;
        // var imgArr = that.data.picUrlList;
        var all = [that.data.detailInfo.shPic1, that.data.detailInfo.shPic2, that.data.detailInfo.shYyzz, that.data.detailInfo.shShxy];
        // for (var index in imgArr) {
        //     all = all.concat(imgArr[index].attaUrl);
        // }
        wx.previewImage({
            current: all[imgIdx], // 当前显示图片的http链接
            urls: all // 需要预览的图片http链接列表
        })
    },
    // 评估
    goPetitionPhotos: function () {
        var that = this;
        wx.navigateTo({
            url: "/pages/petitionPhotos/petitionPhotos?orderNo=" + that.data.proNo + '&proName=' + that.data.proName + '&proMark=' + that.data.proMark + '&checkVal=' + that.data.checkVal
        })
    },
    // 拨打电话
    callPhone: function () {
        var that = this;
        wx.makePhoneCall({
            phoneNumber: that.data.phoneNumber
        })
    },
    // 获取经纬度
    gotGps: function () {
        wx.getLocation({
            type: 'wgs84',
            success(res) {
                const latitude = res.latitude
                const longitude = res.longitude
                const speed = res.speed
                const accuracy = res.accuracy
                console.log('latitude==' + res.latitude + '=longitude=' + res.longitude)
            }
        });
    },
    // 导航
    mapNavigation: function () {

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