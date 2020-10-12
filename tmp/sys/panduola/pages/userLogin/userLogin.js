var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        userName: '',
        userPassword: ''
    },
    onLoad: function (e) {
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId
        })
    },
    //姓名赋值
    userNameInput: function (e) {
        this.setData({
            userName: e.detail.value
        })
    },
    //密码赋值
    userPasswordInput: function (e) {
        this.setData({
            userPassword: e.detail.value
        })
    },
    //提交
    sbumitBtn: function () {
        
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