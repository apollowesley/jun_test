var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        lsnum: '闽',//车牌号
        frameno: '',//车架号后六位
        engineno: '',//发动机号后六位
        queryResults: '',//查询结果
        violationTimes: '',//违章次数
        totalDeduction: '',//总扣分
        totalFine: '',//总罚款
        list: []
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (e) {
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId
        })
    },
    //车牌号赋值
    lsnumInput: function (e) {
        this.setData({
            lsnum: e.detail.value
        })
    },
    //车架号后六位赋值
    framenoInput: function (e) {
        this.setData({
            frameno: e.detail.value
        })
    },
    //发动机号后六位赋值
    enginenoInput: function (e) {
        this.setData({
            engineno: e.detail.value
        })
    },
    sbumitBtn: function () {
        var that = this;
        console.log("lsnum" + that.data.lsnum)
        console.log("frameno" + that.data.frameno)
        console.log("engineno" + that.data.engineno)
        wx.request({
            url: pathUrl + '/rest/fk/do360wz',
            data: {
                createBy: that.data.openId,
                lsnum: that.data.lsnum,
                frameno: that.data.frameno,
                engineno: that.data.engineno,
                shiietype: '02'
            },
            method: 'POST',
            success: function (res) {
                console.log(res)
                if (res.data.ok) {
                    that.setData({
                        queryResults: res.data.message,//查询结果
                        violationTimes: res.data.data.result.wzts,//违章次数
                        totalDeduction: res.data.data.result.wzjfhj,//总扣分
                        totalFine: res.data.data.result.wzfkhj,//总罚款
                        list: res.data.data.result.data
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
    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

  //上传OCR图片
  upEnclosure: function (e) {
    var that = this;
    var sbtype = e.currentTarget.dataset.sbtype
    wx.chooseImage({
      count: 1, //最多可以选择的图片总数  
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
          duration: 20000
        })
        var uploadImgCount = 0;
        for (var i = 0, h = tempFilePaths.length; i < h; i++) {
          wx.uploadFile({
            url: pathUrl + '/rest/base/uploadsb',
            filePath: tempFilePaths[i],
            name: 'uploadfile_ant',
            formData: {
              sbtype: sbtype,
              filetype: 'sb',
              orderNo: 'sbdb'
            },
            header: {
              "Content-Type": "multipart/form-data"
            },
            success: function (res) {
              uploadImgCount++;
              console.log(res);
              if (sbtype == 2) {
                var data = JSON.parse(res.data);
                data = JSON.parse(data[0].piccontent);
                var items = data.result.cardsinfo[0].items;
                that.setData({
                  ocrName: items[1].content,
                  ocrSex: items[2].content,
                  ocrNation: items[3].content,
                  ocrBirth: items[4].content,
                  ocrAddress: items[5].content,
                  ocrIdNumber: items[6].content,
                })
                console.log("sbtype == 2:" + JSON.stringify(items))
              } else if (sbtype == 3) {
                var data = JSON.parse(res.data);
                data = JSON.parse(data[0].piccontent);
                var items = data.result.cardsinfo[0].items;
                that.setData({
                  ocrOffice: items[1].content,
                  ocrValidityPeriod: items[2].content,
                  cusIdValidfrom: items[3].content,
                  cusIdValidto: items[4].content
                })
                console.log("sbtype == 3:" + JSON.stringify(items))

              } else if (sbtype == 6) {
                var data = JSON.parse(res.data);
                data = JSON.parse(data[0].piccontent);
                var items = data.result.cardsinfo[0].items;
               
                that.setData({
                  
                      lsnum: items[1].content,
                
                      frameno: items[6].content,
                      engineno: items[7].content,
                })
                console.log("sbtype == 6:" + JSON.stringify(items))
              }
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
    });
  },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        this.setData({
            // lsnum: '',//车牌号
            // frameno: '',//车架号后六位
            // engineno: '',//发动机号后六位
            // queryResults: '',//查询结果
            // violationTimes: '',//违章次数
            // totalDeduction: '',//总扣分
            // totalFine: '',//总罚款
            // list: []
        })
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