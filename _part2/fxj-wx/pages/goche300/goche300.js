var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    brand: '', //品牌/车型
    plateNumber: '', //车牌号
    holder: '', //所有人
    dateIssue: '', //注册/发证日期
    nature: '', //使用性质
    address: '', //住址
    vinno: '', //车架号
    zone: '2',
    mile: '',
    type: [
      {
        id: 14,
        name: '福州'
      },
      {
        id: 44,
        name: '厦门'
      },
      {
        id: 128,
        name: '泉州'
      },
      {
        id: 156,
        name: '漳州'
      },
      {
        id: 210,
        name: '龙岩'
      },
      {
        id: 100,
        name: '三明'
      },
      {
        id: 72,
        name: '莆田'
      },
      {
        id: 236,
        name: '宁德'
      },
      {
        id: 183,
        name: '南平'
      }
    ],
    index: 1
  },

  onLoad: function (e) {
    var openId = wx.getStorageSync('openId');
    console.log(e)
    this.setData({
      openId: openId,
    })
  },
  //ocr名字赋值
  ocrNameInput: function (e) {
    this.setData({
      ocrName: e.detail.value
    })
  },


  //公里数
  mileInput: function (e) {
    this.setData({
      mile: e.detail.value
    })
  },

  //品牌/车型
  brandInput: function (e) {
    this.setData({
      brand: e.detail.value
    })
  },
  //车牌号
  plateNumberInput: function (e) {
    this.setData({
      plateNumber: e.detail.value
    })
  },
  //所有人
  holderInput: function (e) {
    this.setData({
      holder: e.detail.value
    })
  },

  //注册/发证日期
  dateIssueInput: function (e) {
    this.setData({
      dateIssue: e.detail.value
    })
  },

  //使用性质
  natureInput: function (e) {
    this.setData({
      nature: e.detail.value
    })
  },

  //住址
  addressInput: function (e) {
    this.setData({
      address: e.detail.value
    })
  },

  //车架号
  vinnoInput: function (e) {
    this.setData({
      vinno: e.detail.value
    })
  },
  //发动机号
  engineNumberInput: function (e) {
    this.setData({
      engineNumber: e.detail.value
    })
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
              orderNo: that.data.orderNo
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
                  brand: items[5].content,
                  plateNumber: items[1].content,
                  holder: items[3].content,
                  dateIssue: items[8].content,
                  nature: items[10].content,
                  address: items[4].content,
                  vinno: items[6].content,
                  engineNumber: items[7].content,
                })
                console.log("sbtype == 6:" + JSON.stringify(items))
              } else if (sbtype == 5) {
                var data = JSON.parse(res.data);
                data = JSON.parse(data[0].piccontent);
                var items = data.result.cardsinfo[0].items;
                that.setData({
                  holder1: items[2].content,
                  dateIssue1: items[8].content,
                  nature1: items[7].content,
                  address1: items[4].content,

                })
                console.log(data);
                console.log(items);
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
  goche300: function () {
    var that = this;
    var vinno = that.data.vinno;
    var index = that.data.index;
    var dateIssue = that.data.dateIssue;
    var mile = that.data.mile;
    var i = that.data.index;
    var zone = that.data.type[i].id;
    console.log(vinno)
    console.log(zone)
    console.log(mile)
    console.log(dateIssue)
    wx.request({
      url: pathUrl + '/rest/fk/doche300gujiabyvin/',
      data: {
        keyword1: vinno,
        keyword2: zone,
        keyword3: dateIssue,
        keyword4: mile,

      },
      method: 'POST',
      success: function (res) {
        console.log(res)
        var str = res.data.data.rmContent;
        if (str == null) {
          wx.showModal({
            title: '提示',
            content: "车架号有误，请重新输入",
            showCancel: false,
          })
        };
        arr = str.split(",");
        console.log(str);
        console.log(arr);
        console.log(arr[0]);
        var str0 = arr[0];
        var arr0 = str0.split(':');
        console.log(arr0[1]);

        if (arr0[1] == '"0"') {
          console.log('2313')
          console.log(arr[2])
          if (arr[2] == undefined) {
            console.log('123123')

            var str1 = arr[1];
            var arr1 = str1.split(':');
            var msg = arr1[1];
            wx.showModal({
              title: '提示',
              content: msg,
              showCancel: false,
            })
            return false;
          } else {
            var str2 = arr[2];
            var arr2 = str2.split(':');
            var msg = arr2[1];
            wx.showModal({
              title: '提示',
              content: msg,
              showCancel: false,
            })
            return false;
          }
        }
        var str1 = arr[1];
        var arr1 = str1.split(':');
        var str2 = arr[2];
        var arr2 = str2.split(':');
        var str3 = arr[3];
        var arr3 = str3.split(':');
        var str4 = arr[4];
        var arr4 = str4.split(':');
        var str5 = arr[5];
        var arr5 = str5.split(':');
        var str6 = arr[6];
        var arr6 = str6.split(':');
        var str7 = arr[7];
        var arr7 = str7.split(':');
        var str9 = arr[9];
        var arr9 = str9.split(':');
        var str10 = arr[10];
        var arr10 = str10.split(':');
        var str11 = arr[11];
        var arr11 = str11.split(':');
        console.log(arr1)
        console.log(arr1[1])
        if (res.data.ok) {
          that.setData({
            arr1: arr1[1],
            arr2: arr2[1],
            arr3: arr3[1],
            arr4: arr4[1],
            arr5: arr5[1],
            arr6: arr6[1],
            arr7: arr7[1],
            arr9: arr9[1],
            arr10: arr10[1],
            arr11: arr11[1],

          })
        }
        else {
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