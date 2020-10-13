var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        searchInput:'',
        searchValue:'',
        deleteBtnHide:true,
        list: [],
        pageNumber: 0,
        loading: false, //"上拉加载"的变量，默认false，隐藏  
        loadingComplete: false,  //“没有数据”的变量，默认false，隐藏  
    },
    onLoad: function (options) {
        wx.showLoading({
            title: '加载中',
        })
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId
        })
        this.onReachBottom();
    },
    //点击搜索按钮
    search:function(e){
        this.setData({
            searchValue: e.detail.value
        })
        // wx.nextTick(() => {
        //     this.setData({ 
        //         searchValue: ''
        //     })
        // })
        this.ssjg();
    },
    //删除输入框value
    deleteBtn:function () {
        this.setData({
            searchValue: '',
            deleteBtnHide: true
        })
    },
    //监听input value
    searchInputValue: function (e) {
        this.setData({
            searchValue: e.detail.value
        })
        if (e.detail.value != ''){
            this.setData({
                deleteBtnHide: false
            })
        }else {
            this.setData({
                deleteBtnHide: true
            })
        }
    },
    ssjg:function(){
        var that = this;
        that.setData({
            loadingComplete: false, //把“没有数据”设为true，显示  
            loading: false,//把"上拉加载"的变量设为false，隐藏  
            pageNumber: 0,
            list: []
        });
        this.onReachBottom();
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
            url: pathUrl + '/rest/basebi/getbi/' + that.data.openId,
            data: {
                pageNumber: pageNumber,
                pageSize: 10,
                bitype: "appwdtd",
                search:that.data.searchValue
            },
            success: function (res) {
                console.log(res.data)
                if (res.data.ok) {
                    wx.hideLoading();
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