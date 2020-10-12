var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        index: '',
        openId: '',
        showIndex:0,
        arrList:[],
        growthValue:'', // 成长值
        description:'', // 成长值说明
        gainer:'',  // 成长值获得者
        loadingComplete: false, //把“没有数据”设为true，显示  
        loading: false,//把"上拉加载"的变量设为false，隐藏  
        pageNumber: 0,
        growthList:[],
        nameList:[]
    },
    onLoad: function (e) {
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId
        });
        // 获取自定义成长值
        this.getGrwothValueList();
    },
    toggleList: function (e) {
        if (e.currentTarget.dataset.index != this.data.showIndex) {
            this.setData({
                showIndex: e.currentTarget.dataset.index
            })
        } else {
            this.setData({
                showIndex: 0
            })
        }
        var id = e.currentTarget.dataset.id;
        var that = this;
        wx.request({
            url: pathUrl + '/rest/panduolaStaffController/list/' + that.data.openId,
            data: {
                ownKindergarten: e.currentTarget.dataset.departmentname,
                pageNumber: 1,
                pageSize: 10
            },
            success: function (res){
                if (res.data.ok) {
                    that.setData({
                        arrList: res.data.data
                    })
                }
            }
        })
    },
    // 选择自定义成长值
    bindPickerChange(e) {
        // console.log(e.detail.value)
        var that = this;
        that.setData({
            index: e.detail.value,
            growthValue: that.data.growthList[e.detail.value],
            description: that.data.nameList[e.detail.value]
        });
    },
    // 获得成长值自定义
    getGrwothValueList:function(e){
        var that = this;
        var growthArr = [];
        var nameArr = [];
        wx.request({
            url: pathUrl + '/rest/panduolaGrwothvalueDefinitionController/list/' + that.data.openId,
            data: {
                pageNumber: 1,
                pageSize: 10
            },
            success: function (res) {
                console.log(res.data)
                if (res.data.ok) {
                    for (var i = 0; i < res.data.data.length; i++){
                        growthArr.push(res.data.data[i].growthValue);
                        nameArr.push(res.data.data[i].name)
                    }
                    that.setData({
                        arr: res.data.data,
                        growthList: growthArr,
                        nameList: nameArr
                    })
                }
            }
        })
    },
    // 输入成长值
    growthValueInput:function(e){
        console.log(e)
        this.setData({
            growthValue:e.detail.value
        })
    },
    // 
    descriptionInput:function(e){
        this.setData({
            description:e.detail.value
        })
    },
    // 获得者
    checkboxChange:function(e) {
        console.log('checkbox发生change事件，携带value值为：', e.detail.value);
        this.setData({
            gainer:e.detail.value
        })
    },
    // 开点
    confirmBtn:function(e){
        var that = this;
        if (!that.data.gainer){
            wx.showModal({
                title: '提示',
                content: '请选择获得者',
                confirmColor: '#7aa6d1',
                showCancel: false
            })
            return false;
        }
        if (!that.data.growthValue) {
            wx.showModal({
                title: '提示',
                content: '请输入成长值',
                confirmColor: '#7aa6d1',
                showCancel: false
            })
            return false;
        }
        if (!that.data.description) {
            wx.showModal({
                title: '提示',
                content: '请输入成长值说明',
                confirmColor: '#7aa6d1',
                showCancel: false
            })
            return false;
        }
       
        wx.request({
            url: pathUrl + '/rest/panduolaGrowthValueController',
            method:"POST",
            data: {
                gainer: (that.data.gainer).join(','),
                giver:that.data.openId,
                growthValue: that.data.growthValue,
                description: that.data.description
            },
            success:function(res){
              console.log(that.data.openId)

              console.log((that.data.gainer).join(','))
                console.log(res.data)
                if (res.data.ok) {
                    // wx.showModal({
                    //     title: '提示',
                    //     content: '开点成功',
                    //     confirmColor: '#7aa6d1',
                    //     showCancel: false
                    // });
                  wx.showToast({
                    title: '提交成功',
                    duration: 1000,
                    mask: true,
                    success: function () {
                      setTimeout(function () {
                        wx.switchTab({
                          url: "/pages/index/index"
                        })
                      }, 1000);
                    }
                  })                  
                }else{
                  wx.showModal({
                    title: '提示',
                    content: res.data.message,
                    confirmColor: '#7aa6d1',
                    showCancel: false
                  });
                }
            }
        })
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
   
    /* 生命周期函数--监听页面隐藏 */
    onHide: function () { },
    /* 生命周期函数--监听页面卸载 */
    onUnload: function () { },

    /* 页面相关事件处理函数--监听用户下拉动作 */
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
    /* 页面上拉触底事件的处理函数 */
    onReachBottom: function () {
        var that = this;
        var pageNumber = that.data.pageNumber + 1;
        var allList = that.data.list;
        wx.request({
            url: pathUrl + '/rest/panduolaDepartmentManagementController/list/' + that.data.openId,
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
    /* 用户点击右上角分享 */
    onShareAppMessage: function () { }
})
