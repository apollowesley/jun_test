var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        rawData:[]
    },
    onLoad: function (options) {
        var rawData = wx.getStorageSync('rawData');
        rawData = JSON.parse(rawData);
        this.setData({
            rawData: rawData
        })
    },
    //个人资料
    goPersonData:function(){
      wx.navigateTo({
        url: "/pages/my/personData/personData"
      })
    },
    //我的草稿
    goMyDraft: function () {
      wx.navigateTo({
        url: "/pages/my/myDraft/myDraft"
      })
    },
    //消息通知
    goMessage: function () {
      wx.navigateTo({
        url: "/pages/my/myMessage/myMessage"
      })
    },
    //我的统计
    goCount: function () {
        wx.navigateTo({
            url: "/pages/my/myCount/myCount"
        })
    },
    //我的团队
    goTeam: function () {
        wx.navigateTo({
            url: "/pages/my/myTeam/myTeam"
        })
    },
    //展业工具
    goTool: function () {
      wx.navigateTo({
        url: "/pages/tool/tool"
      })
    },
    //反馈意见
    goFeedback: function () {
        wx.navigateTo({
            url: "/pages/my/myFeedback/myFeedback"
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