var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        rawData: [],
        atitude: '',
        longitude: ''
    },
    onLoad: function (options) {
        var rawData = wx.getStorageSync('rawData');
        rawData = JSON.parse(rawData);
        this.setData({
            rawData: rawData
        });
        this.gotGps();
    },
    // 获取经纬度
    gotGps: function () {
        var that = this;
        wx.getLocation({
            type: 'wgs84',
            success(res) {
                that.setData({
                    latitude: res.latitude,
                    longitude: res.longitude
                });

            }
        });
    },
    // 个人资料
    goPersonData: function () {
        wx.navigateTo({
            url: "/pages/my/personData/personData"
        })
    },
    // 我获得的成长值
    getMyGainGrowth:function(e){
        wx.navigateTo({
            url: '/pages/growthList/growthList',
        })
    },
  // 总排名
  getCountAll: function (e) {
    wx.navigateTo({
      url: '/pages/countListall/countListall',
    })
  },

  
    // 我赠送的成长值
    getMyGiveGrowth:function(e){
        wx.navigateTo({
            url: '/pages/giveGrowthList/giveGrowthList',
        })
    },
    onReady: function () {},
    /* 生命周期函数--监听页面显示 */
    onShow: function () {

    },
    /* 生命周期函数--监听页面隐藏 */
    onHide: function () {

    },
    /* 生命周期函数--监听页面卸载 */
    onUnload: function () {

    }, 
    /* 页面相关事件处理函数--监听用户下拉动作 */
    onPullDownRefresh: function () {

    }, 
    /* 页面上拉触底事件的处理函数 */
    onReachBottom: function () {

    }, 
    /* 用户点击右上角分享 */
    onShareAppMessage: function () {
        
    }
})