var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    url_name: '公证云网址：',
    url: "https://signdemo.esnotary.com",
    isSubmit: true, //防止重复提交

  },
  onLoad: function (e) {
    console.log(e)
    var openId = wx.getStorageSync('openId');
    var orderNo = e.orderNo;
    var proNo = e.proNo;
    var id =e.id;
    this.setData({
      openId: openId,
      orderNo: orderNo,
      id:id,
      proNo: proNo
    })
    this.orderMainController();
  },
  // 一键复制事件
  copyBtn: function (e) {
    var that = this;
    var orderNo = that.data.orderNo;
    var url_name = that.data.url_name;
    var url = that.data.url;
    wx.setClipboardData({
      //准备复制的数据
      data: url + '/' + orderNo,
      success: function (res) {
        wx.showToast({
          title: '复制成功',
        });
      }
    });
  },
  //ocr名字赋值
  ocrNameInput: function (e) {
    this.setData({
      ocrName: e.detail.value
    })
  },
  //上传附件图片
  getEnclosurePic: function (e) {
    var picIndex = e.currentTarget.dataset.index;
    var attType = e.currentTarget.dataset.atttype;
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
              filetype: attType,
              orderNo: that.data.orderNo
            },
            header: {
              "Content-Type": "multipart/form-data"
            },
            success: function (res) {
              uploadImgCount++;
              var data = JSON.parse(res.data);
              var proAttListInfo = that.data.fxjProAttList;
              proAttListInfo[picIndex].fxjOrderAttaList.push({
                attaUrl: data[0].fileUrl,
                id: data[0].fileId
              });
              that.setData({
                fxjProAttList: proAttListInfo
              });

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
  //删除上传附件图片
  deletePic: function (e) {
    var that = this;
    var index = e.currentTarget.dataset.index;
    var imgIdx = e.currentTarget.dataset.imgIdx;
    var deletPicId = that.data.fxjProAttList[index].fxjOrderAttaList[imgIdx].id;
    if (that.data.flag) {
      that.data.fxjProAttList[index].fxjOrderAttaList.splice(imgIdx, 1);
      that.setData({
        fxjProAttList: that.data.fxjProAttList,
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
                  orderBy28: items[2].content,
                  orderBy29: items[8].content,
                  orderBy30: items[7].content,
                  orderBy31: items[4].content,

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
  orderMainController: function () {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/fxjOrderMainController/' + that.data.id,
      data: {

      },
      success: function (res) {
        console.log(res.data)
        if (res.data.ok) {
          var data = res.data.data;
          var fxjOrderLoanList = res.data.data.fxjOrderLoanList;
          var ocrValidityPeriod = res.data.data.cusIdValidfrom + ' - ' + res.data.data.cusIdValidto;
          if (fxjOrderLoanList && fxjOrderLoanList.length > 0) {
            that.setData({
              brand: fxjOrderLoanList[0].elementContent,
              plateNumber: fxjOrderLoanList[1].elementContent,
              holder: fxjOrderLoanList[2].elementContent,
              dateIssue: fxjOrderLoanList[3].elementContent,
              nature: fxjOrderLoanList[4].elementContent,
              address: fxjOrderLoanList[5].elementContent,
              vinno: fxjOrderLoanList[6].elementContent,
              engineNumber: fxjOrderLoanList[7].elementContent,
            })
          }
          that.setData({
            ocrName: data.cusName,
            ocrSex: data.cusGender,
            ocrNation: data.cusEthnical,
            ocrBirth: data.cusBirthday,
            ocrAddress: data.cusResAddr,
            ocrIdNumber: data.cusIncard,
            ocrOffice: data.createSysorg,
            cusIdValidfrom: data.cusIdValidfrom,
            cusIdValidto: data.cusIdValidto,
            ocrValidityPeriod: ocrValidityPeriod,
            // residentialAddress: data.cusRemark,
            // telephone: data.cusMobile,
            checkedValue: data.cusMaritalStatus,
            orderEmerList: data.fxjOrderEmerList,
            village: data.orderCarno,
            acreage: data.orderCartype,
            // orderAmount: data.orderAmount,
            howLong: data.orderPeriod,
            proIsdb: data.proIsdb,
            urgent: data.urgent,
            guaranteeName: data.orderBy1,
            guaranteeIdCard: data.orderBy2,
            guaranteePhone: data.orderBy3,
            // orderBy4: data.orderBy4,
            // orderBy5: data.orderBy5,
            // orderBy6: data.orderBy6,
            // orderBy7: data.orderBy7,
            // orderBy8: data.orderBy8,
            // orderBy9: data.orderBy9,
            // orderBy10: data.orderBy10,
            // orderBy11: data.orderBy11,
            // orderBy12: data.orderBy12,
            // orderBy13: data.orderBy13,
            // orderBy14: data.orderBy14,
            // orderBy15: data.orderBy15,
            // orderBy16: data.orderBy16,
            // orderBy17: data.orderBy17,
            // orderBy18: data.orderBy18,
            // orderBy19: data.orderBy19,
            // orderBy20: data.orderBy20,
            // orderBy21: data.orderBy21,
            // orderBy22: data.orderBy22,
            // orderBy23: data.orderBy23,
            // orderBy24: data.orderBy24,
            // orderBy25: data.orderBy25,
            // orderBy26: data.orderBy26,
            // orderBy27: data.orderBy27,
            orderBy28: data.orderBy28,
            orderBy29: data.orderBy29,
            orderBy30: data.orderBy30,
            orderBy31: data.orderBy31,
            // orderBy32: data.orderBy32,
            // orderBy33: data.orderBy33,
            // orderBy34: data.orderBy34,
            // orderBy35: data.orderBy35,
            // orderBy36: data.orderBy36,
            // orderBy37: data.orderBy37,
            // orderBy38: data.orderBy38,
            // orderBy39: data.orderBy39,
            // orderBy40: data.orderBy40,
            fxjProAttList: data.fxjProAttList
          })
          if (data.cusMaritalStatus == '已婚') {
            that.setData({
              ['items[0].checked']: true
            })
          };
          if (data.cusMaritalStatus == '未婚') {
            that.setData({
              ['items[1].checked']: true
            })
          };
          if (data.cusMaritalStatus == '离婚/丧偶') {
            that.setData({
              ['items[2].checked']: true
            })
          }
          if (that.data.howLong == '3') {
            that.setData({
              pickerIndex: 0,
              orderPeriod: 3
            })
          } else if (that.data.howLong == '6') {
            that.setData({
              pickerIndex: 1,
              orderPeriod: 6
            })
          } else if (that.data.howLong == '12') {
            that.setData({
              pickerIndex: 2,
              orderPeriod: 12
            })
          } else if (that.data.howLong == '24') {
            that.setData({
              pickerIndex: 3,
              orderPeriod: 24
            })
          } else if (that.data.howLong == '36') {
            that.setData({
              pickerIndex: 4,
              orderPeriod: 36
            })
          }
          if (that.data.proNo == 10) {
            that.setData({
              spouseName: that.data.orderEmerList[0].emergencyContact,
              spouseTelephone: that.data.orderEmerList[0].emergencyMobile,
              urgent: that.data.orderEmerList[1].emergencyContact,
              urgentTelephone: that.data.orderEmerList[1].emergencyMobile,
              urgent2: that.data.orderEmerList[2].emergencyContact,
              urgentTelephone2: that.data.orderEmerList[2].emergencyMobile
            })
          } else {
            that.setData({
              spouseName: that.data.orderEmerList[0].emergencyContact,
              spouseTelephone: that.data.orderEmerList[0].emergencyMobile
            })
          }
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
  
  //提交
  submitBtn: function (e) {
    var no = e.currentTarget.dataset.prono;
    var that = this;
    var checkedValue = that.data.checkedValue;
    var again = that.data.again;
    var orderBy40 = that.data.orderBy40;
    console.log(that.data.proNo)

    var data = {
      orderOper: 'Y',
      id: that.data.id,
      updateBy: that.data.openId,

      orderNo: that.data.orderNo, //订单编号
      proNo: that.data.proNo, //产品编号
      cusName: that.data.ocrName, //姓名
      cusGender: that.data.ocrSex, //性别
      cusEthnical: that.data.ocrNation, //民族
      cusBirthday: that.data.ocrBirth, //出生日期
      cusResAddr: that.data.ocrAddress, //地址
      cusrIdtype: '身份证', //身份证为固定值
      cusIncard: that.data.ocrIdNumber, // 身份证号
      createSysorg: that.data.ocrOffice, //签发机关
      cusIdValidfrom: that.data.cusIdValidfrom, //证件开始日期
      cusIdValidto: that.data.cusIdValidto, //证件结束日期
      orderCartype: that.data.acreage, //房子面积
      orderCarno: that.data.village, // 小区名称
      orderAmount: that.data.money, // 金额
      orderPeriod: that.data.orderPeriod, // 期数
      cusRemark: that.data.residentialAddress, // 个人资料的居住地址
      cusMobile: that.data.telephone, //电话
      cusMaritalStatus: that.data.checkedValue, //婚姻状况
      cusDeu: '', //学历
      cusAge: '',
      cusQqid: '', //qq号
      orderBy1: that.data.guaranteeName,//担保人姓名
      orderBy2: that.data.guaranteeIdCard,//担保人身份证号
      orderBy3: that.data.guaranteePhone,//担保人电话
      orderBy4: that.data.orderBy4,
      orderBy5: that.data.orderBy5,
      orderBy6: that.data.orderBy6,
      orderBy7: that.data.orderBy7,
      orderBy8: that.data.orderBy8,
      orderBy9: that.data.orderBy9, //储蓄卡
      orderBy10: that.data.orderBy10,//开户行
      orderBy11: that.data.orderBy11,//银行预留电话
      orderBy12: that.data.orderBy12,
      orderBy13: that.data.orderBy13,
      orderBy14: that.data.orderPeriod1, //利率或押金
      orderBy15: that.data.orderBy15, //GPS费或月前租金
      orderBy16: that.data.orderBy16, //服务费或月租金
      orderBy17: that.data.orderBy17, //其他
      orderBy18: that.data.orderBy18, //邮箱
      orderBy19: that.data.orderBy19,
      orderBy20: that.data.orderBy20,
      orderBy21: that.data.orderBy21,
      orderBy22: that.data.orderBy22,
      orderBy23: that.data.orderBy23,
      orderBy24: that.data.orderBy24,
      orderBy25: that.data.orderBy25,
      orderBy26: that.data.orderBy26,
      orderBy27: that.data.orderBy27,
      orderBy28: that.data.orderBy28,
      orderBy29: that.data.orderBy29,
      orderBy30: that.data.orderBy30,
      orderBy31: that.data.orderBy31,
      orderBy32: that.data.orderBy32,
      orderBy33: that.data.orderBy33,
      orderBy34: that.data.orderBy34,
      orderBy35: that.data.orderBy35,
      orderBy36: that.data.orderBy36,
      orderBy37: that.data.orderBy37,
      orderBy38: that.data.orderBy38,
      orderBy39: that.data.orderBy39,
      orderBy40: that.data.orderBy40,
      orderBy80: '',

      orderAmount: that.data.orderAmount,// 金额
      orderPeriod: that.data.orderPeriod, // 期数
      fxjOrderEmerList: [{
        emergencyRelation: '', //关系
        emergencyContact: that.data.spouseName, //姓名
        emergencyMobile: that.data.spouseTelephone, //电话
        emergencyOffice: '',
        emergencyResAddr: '' //地址
      }],
      fxjOrderLoanList: [
        {
          elementNo: 10,
          elementContent: that.data.brand
        }, {
          elementNo: 20,
          elementContent: that.data.plateNumber
        }, {
          elementNo: 30,
          elementContent: that.data.holder
        }, {
          elementNo: 40,
          elementContent: that.data.dateIssue
        }, {
          elementNo: 50,
          elementContent: that.data.nature
        }, {
          elementNo: 60,
          elementContent: that.data.address
        }, {
          elementNo: 70,
          elementContent: that.data.vinno
        }, {
          elementNo: 80,
          elementContent: that.data.engineNumber
        }
      ]

    };
    console.log("data:" + JSON.stringify(data));
    
    if (that.data.isSubmit) {
      that.setData({
        isSubmit: false
      });
      wx.request({
        url: pathUrl + '/rest/fxjOrderMainController/' + that.data.id,
        data: data,
        method: 'PUT',
        success: function (res) {
          console.log(res.data)
          if (res.data.ok) {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: '提交成功',
              duration: 500,
              mask: true,
              success: function () {
                setTimeout(function () {
                  wx.navigateBack({
                    delta: 1
                  })
                }, 500);
              }
            })
          } else {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: '提交失败',
              icon: 'none',
              mask: true,
              duration: 1000
            })
          }
        }
      })
    } else {
      wx.showToast({
        title: '请勿重复提交',
        icon: 'none',
        mask: true,
        duration: 1000
      })
    }
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


