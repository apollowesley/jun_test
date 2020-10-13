var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        orderNo: '',
        picUrlList: [],
        flag: true
    },
    onLoad: function (e) {
        this.setData({
            orderNo: e.orderNo
        })
        this.orderMainController();
    },
    orderMainController: function () {
        var that = this;
        wx.request({
            url: pathUrl + '/rest/fxjOrderMainController/attalist/' + that.data.orderNo,
            data: {
                attaType: 950,
                pageNumber: 1,
                pageSize: 100
            },
            success: function (res) {
                console.log(res);
                if (res.data.ok) {
                    that.setData({
                        picUrlList: res.data.data
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
    //上传图片
    getEnclosurePic: function () {
        var that = this;
        wx.chooseImage({
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
            success: function (res) {
                // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片  
                var tempFilePaths = res.tempFilePaths;
                //启动上传等待中...  
                wx.showToast({
                    title: '正在上传...',
                    icon: 'loading',
                    mask: true,
                    duration: 10000
                })
                var uploadImgCount = 0;
                for (var i = 0, h = tempFilePaths.length; i < h; i++) {
                    wx.uploadFile({
                        url: pathUrl + '/rest/base/upload',
                        filePath: tempFilePaths[i],
                        name: 'uploadfile_ant',
                        formData: {
                            sbtype: 'NO',
                            filetype: 950,
                            orderNo: that.data.orderNo
                        },
                        header: {
                            "Content-Type": "multipart/form-data"
                        },
                        success: function (res) {
                            uploadImgCount++;
                            var data = JSON.parse(res.data);
                            console.log(data);
                            var productInfo = that.data.picUrlList;
                            productInfo.push({
                                attaUrl: data[0].fileUrl,
                                id: data[0].fileId
                            });
                            that.setData({
                                picUrlList: productInfo
                            });
                            console.log(that.data.picUrlList)
                            //如果是最后一张,则隐藏等待中  
                            if (uploadImgCount == tempFilePaths.length) {
                                wx.hideToast();
                            }
                        },
                        fail: function (res) {
                            wx.hideToast();
                            wx.showModal({
                                title: '错误提示',
                                content: '上传图片失败',
                                showCancel: false
                            })
                        }
                    });
                }
            }
        })
    },
    //删除图片
    deletePic: function (e) {
        var index = e.currentTarget.dataset.index;
        var deletPicId = e.currentTarget.dataset.picid;
        console.log(e);
        var that = this;
        if (that.data.flag) {
            that.data.picUrlList.splice(index, 1);
            that.setData({
                picUrlList: that.data.picUrlList,
                flag: false
            })
        }
        wx.request({
            url: pathUrl + '/rest/base/delfile/' + deletPicId,
            success: function (res) {
                if (res.data.ok || res.data.data) {
                    that.setData({
                        flag: true
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