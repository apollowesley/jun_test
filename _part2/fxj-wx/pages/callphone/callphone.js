var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    focus: false,
    isSubmit: true, //防止重复提交

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (e) {
    console.log(e);
    var openId = wx.getStorageSync('openId');
    this.setData({
      orderNo: e.orderNo,
      proNo: e.proNo,
      id: e.id,
      openId: openId,
    })
    this.orderMainController();
  },
  textareaInput1: function (e) {
    console.log(e.detail.value)
    var that = this;
    var orderBy36 = that.data.orderBy36
    that.setData({
      orderBy36: e.detail.value
    })
  },
  callPhone: function () {
    var that = this;
    var phone = that.data.telephone;
    wx.makePhoneCall({
      phoneNumber: phone,
    })
  },
  callPhone1: function () {
    var that = this;
    var phone = that.data.spouseTelephone;
    wx.makePhoneCall({
      phoneNumber: phone,
    })
  },
  callPhone2: function () {
    var that = this;
    var phone = that.data.urgentTelephone;
    wx.makePhoneCall({
      phoneNumber: phone,
    })
  },
  orderMainController: function () {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/fxjOrderMainController/' + that.data.orderNo,
      data: {

      },
      success: function (res) {
        console.log(res.data.data.fxjOrderEmerList)
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
            residentialAddress: data.cusRemark,
            telephone: data.cusMobile,
            checkedValue: data.cusMaritalStatus,
            orderEmerList: data.fxjOrderEmerList,
            village: data.orderCarno,
            acreage: data.orderCartype,
            money: data.orderAmount,
            howLong: data.orderPeriod,
            proIsdb: data.proIsdb,
            urgent: data.urgent,
            guaranteeName: data.orderBy1,
            guaranteeIdCard: data.orderBy2,
            guaranteePhone: data.orderBy3,
            orderBy4: data.orderBy4,
            orderBy5: data.orderBy5,
            orderBy6: data.orderBy6,
            orderBy7: data.orderBy7,
            orderBy8: data.orderBy8,
            orderBy9: data.orderBy9,
            orderBy10: data.orderBy10,
            orderBy11: data.orderBy11,
            orderBy12: data.orderBy12,
            orderBy13: data.orderBy13,
            orderBy14: data.orderBy14,
            orderBy15: data.orderBy15,
            orderBy16: data.orderBy16,
            orderBy17: data.orderBy17,
            orderBy18: data.orderBy18,
            orderBy19: data.orderBy19,
            orderBy20: data.orderBy20,
            orderBy21: data.orderBy21,
            orderBy22: data.orderBy22,
            orderBy23: data.orderBy23,
            orderBy24: data.orderBy24,
            orderBy25: data.orderBy25,
            orderBy26: data.orderBy26,
            orderBy27: data.orderBy27,
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
          that.setData({
            fxjOrderEmerList: res.data.data.fxjOrderEmerList,
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
  submitBtn: function (e) {
    var no = e.currentTarget.dataset.prono;
    var that = this;
    var data = {
      orderOper: 'S',
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
      orderBy36: that.data.orderBy36,
      orderBy37: '是',
      
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