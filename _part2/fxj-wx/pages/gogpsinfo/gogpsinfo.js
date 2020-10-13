var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    orderNo: '',
    picUrlList: [],
    flag: true,
    orderBy40:'', 
    orderBy37:'', 
    orderBy39:'',
    isSubmit: true, //防止重复提交
    items: [
      { name: '优秀', value: '优秀' },
      { name: '良好', value: '良好', },
      { name: '一般', value: '一般' },
      { name: '事故/泡水', value: '事故/泡水' },
    ]
  },
  onLoad: function (e) {
    this.setData({
      orderNo: e.orderNo,
    })
    console.log(e.orderNo)
    this.orderMainController();
  },
  //GPS编号
  lsnumInput: function (e) {
    this.setData({
      orderBy38: e.detail.value
    })
  },
  
 orderBy53Input: function (e) {
    this.setData({
      orderBy53: e.detail.value
    })
  },
  radioChange: function (e) {
    var that =this;
    var orderBy52 = this.data.orderBy52;
    console.log('radio发生change事件，携带value值为：', e.detail.value)
    that.setData({
      orderBy52:e.detail.value
    })
  },
  //信号正常与否
  framenoInput: function (e) {
    this.setData({
      orderBy39: e.detail.value
    })
  },
  //车辆停放位置
  enginenoInput: function (e) {
    this.setData({
      orderBy40: e.detail.value
    })
  },
  orderMainController: function () {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/fxjOrderMainController/' + that.data.orderNo,
      data: {

      },
      success: function (res) {
        console.log(res.data)
        if (res.data.ok) {
          var data = res.data.data;
          console.log(data)
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
            id:data.id,
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
            residentialAddress: data.cusRemark,
            telephone: data.cusMobile,
            checkedValue: data.cusMaritalStatus,
            orderEmerList: data.fxjOrderEmerList,
            village: data.orderCarno,
            acreage: data.orderCartype,
            money: data.orderAmount,
            howLong: data.orderPeriod,
            proIsdb: data.proIsdb,
            guaranteeName: data.orderBy1,
            guaranteeIdCard: data.orderBy2,
            guaranteePhone: data.orderBy3,
            orderBy4: data.orderBy4,
            orderBy5: data.orderBy5,
            orderBy6: data.orderBy6,
            orderBy7: data.orderBy7,
            orderBy8: data.orderBy8,
            orderBy39: data.orderBy39,
            orderBy40: data.orderBy40,
            orderBy51: data.orderBy51,
            orderBy52: that.data.orderBy52,
            orderBy53: that.data.orderBy53,
            orderBy38: data.orderBy38,
            fxjProAttList: data.fxjProAttList
          })
          console.log(data.fxjOrderEmerList)
          if (that.data.howLong == '12') {
            that.setData({
              pickerIndex: 0,
              orderPeriod: 12
            })
          } else if (that.data.howLong == '18') {
            that.setData({
              pickerIndex: 1,
              orderPeriod: 18
            })
          } else if (that.data.howLong == '24') {
            that.setData({
              pickerIndex: 2,
              orderPeriod: 24
            })
          } else if (that.data.howLong == '36') {
            that.setData({
              pickerIndex: 3,
              orderPeriod: 36
            })
          } else if (that.data.howLong == '48') {
            that.setData({
              pickerIndex: 4,
              orderPeriod: 48
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
        orderCartype: that.data.brand, //车型 如果是房子就是面积
        orderCarno: that.data.plateNumber, // 车牌  如果是房子就是小区名称
        cusRemark: that.data.residentialAddress, // 个人资料的居住地址
        cusMobile: that.data.telephone, //电话
        cusMaritalStatus: that.data.checkedValue, //婚姻状况
        cusDeu: '', //学历
        cusAge: '',
        cusQqid: '', //qq号
        orderAmount: that.data.money, // 金额
        orderPeriod: that.data.orderPeriod, // 期数
        orderBy1: that.data.guaranteeName, //担保人姓名
        orderBy2: that.data.guaranteeIdCard, //担保人身份证号
        orderBy3: that.data.guaranteePhone, //担保人电话
        orderBy4: that.data.orderBy4, //担保人姓名
        orderBy5: that.data.orderBy5, //担保人身份证号
        orderBy6: that.data.orderBy6, //担保人电话
        orderBy7: that.data.orderBy7, //担保人姓名
        orderBy8: that.data.orderBy8, //担保人身份证号
        orderBy39: that.data.orderBy39,
        orderBy40: that.data.orderBy40,
        orderBy51: that.data.orderBy51,
        orderBy52: that.data.orderBy52,
        orderBy38: that.data.orderBy38,
        orderBy53: that.data.orderBy53,
        fxjOrderEmerList: that.data.orderEmerList,
        // fxjOrderEmerList: [{
        //   emergencyRelation: '', //关系
        //   emergencyContact: that.data.spouseName, //姓名
        //   emergencyMobile: that.data.spouseTelephone, //电话
        //   emergencyOffice: '',
        //   emergencyResAddr: '' //地址
        // }, {
        //   emergencyRelation: '', //关系
        //   emergencyContact: that.data.urgent, //姓名
        //   emergencyMobile: that.data.urgentTelephone, //电话
        //   emergencyOffice: '',
        //   emergencyResAddr: '' //地址
        // }, {
        //   emergencyRelation: '', //关系
        //   emergencyContact: that.data.urgent2, //姓名
        //   emergencyMobile: that.data.urgentTelephone2, //电话
        //   emergencyOffice: '',
        //   emergencyResAddr: '' //地址
        // }],
        fxjOrderLoanList: [{
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
        }]
      };
      console.log("data:" + JSON.stringify(data));
      console.log(data);

    if (that.data.isSubmit) {
      that.setData({
        isSubmit: false
      });
      wx.request({
        url: pathUrl + '/rest/fxjOrderMainController/' + that.data.orderNo,
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