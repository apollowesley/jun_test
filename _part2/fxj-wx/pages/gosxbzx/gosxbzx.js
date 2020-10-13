var app = getApp();
const pathUrl = app.globalData.pathUrl;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    pathUrl: pathUrl,
    openId: '',
    queryResults: '',
    violationTimes: '',
    totalDeduction: '',
    totalFine: '',//总罚款
    list: ' ',
    index: 0,
    name: '',//姓名
    ocrSex: '', //ocr性别
    ocrNation: '', //ocr民族
    ocrBirth: '', //ocr出生
    ocrAddress: '', //ocr地址
    ocrIdNumber: '', //ocr身份证号
    ocrOffice: '', //ocr签发机关
    ocrValidityPeriod: '', //ocr有效期限
    cusIdValidfrom: '', //证件开始日期
    cusIdValidto: '', //证件结束日期
    brand: '', //品牌/车型
    plateNumber: '', //车牌号
    holder: '', //所有人
    dateIssue: '', //注册/发证日期
    nature: '', //使用性质
    address: '', //住址
    vinno: '', //车架号
    engineNumber: '', //发动机号
    residentialAddress: '', //居住地址
    telephone: '', //联系电话
    checkedValue: '', //婚姻情况
    spouseName: '', //配偶姓名
    spouseTelephone: '', //联系电话
    urgent: '', //紧急联系人
    urgentTelephone: '', //紧急联系人联系电话
    urgent2: '', //紧急联系人
    urgentTelephone2: '', //紧急联系人联系电话
    isSubmit: true, //防止重复提交
    guaranteeName: '', //担保人姓名
    guaranteeIdCard: '', //担保人身份照
    guaranteePhone: '', //担保人电话
    orderAmount: '', //金额
    pickerIndex: '3',//选择期数index
    orderPeriod: '36',//期数
    orderBy6: '',
    orderBy7: '',
    orderBy8: '',
    orderBy4: '',
    orderBy5: '',
    select: false,
  },
  bindShowMsg() {
    this.setData({
      select: !this.data.select
    })
  },
  mySelect(e) {
    var name = e.currentTarget.dataset.name
    this.setData({
      tihuoWay: name,
      select: false
    })
  },
  bindPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },
  bankcardno: function (e) {
    this.setData({
      bankcardno: e.detail.value
    })
  },
  mobile: function (e) {
    this.setData({
      mobile: e.detail.value
    })
  },
  //姓名赋值
  nameInput: function (e) {
    this.setData({
      name: e.detail.value
    })
  },
  //车牌赋值
  plateInput: function (e) {
    this.setData({
      plate: e.detail.value
    })
  },
  //ocr性别赋值
  ocrSexInput: function (e) {
    this.setData({
      ocrSex: e.detail.value
    })
  },
  //ocr民族赋值
  ocrNationInput: function (e) {
    this.setData({
      ocrNation: e.detail.value
    })
  },
  //ocr出生赋值
  ocrBirthInput: function (e) {
    this.setData({
      ocrBirth: e.detail.value
    })
  },
  //ocr地址赋值
  ocrAddressInput: function (e) {
    this.setData({
      ocrAddress: e.detail.value
    })
  },
  //ocr身份证号
  ocrIdNumberInput: function (e) {
    this.setData({
      ocrIdNumber: e.detail.value
    })
  },
  //ocr发证机关
  ocrOfficeInput: function (e) {
    this.setData({
      ocrOffice: e.detail.value
    })
  },
  //ocr有效期限
  ocrValidityPeriodInput: function (e) {
    this.setData({
      ocrValidityPeriod: e.detail.value
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
  orderBy6Input: function (e) {
    this.setData({
      orderBy6: e.detail.value
    })
  },
  //
  orderBy7Input: function (e) {
    this.setData({
      orderBy7: e.detail.value
    })
  },  //
  orderBy8Input: function (e) {
    this.setData({
      orderBy8: e.detail.value
    })
  },
  //储蓄卡
  orderBy9Input: function (e) {
    this.setData({
      orderBy9: e.detail.value
    })
  },
  //开户行
  orderBy10Input: function (e) {
    this.setData({
      orderBy10: e.detail.value
    })
  },
  //银行预留电话
  orderBy11Input: function (e) {
    this.setData({
      orderBy11: e.detail.value
    })
  },

  submitBtn: function () {
    var that = this;
    wx.showLoading("数据请求中...");
    wx.request({
      url: pathUrl + '/rest/fk/dosxbzx',
      data: {
        realName: that.data.name,
        cardNo: that.data.ocrIdNumber,
        shiietype:'1'
      },
      method: 'POST',

      success: function (res) {
        //   setTimeout(function () {
        //   wx.showToast({
        //     title: "提交成功",
        //     icon: 'success',
        //   })//要延时执行的代码
        // }, 1000) //延迟时间 这里是1秒
        wx.hideLoading();
        console.log(res)
        var str = JSON.parse(res.data.data.rmContent);
        console.log(str)


        // if (str.msg == 'success') {
        //   wx.showModal({
        //     title: '提示',
        //     content: '查询成功',
        //     confirmColor: '#7aa6d1',
        //     showCancel: false
        //   })
        // } else {
        //   wx.showModal({
        //     title: '提示',
        //     content: str.msg,
        //     confirmColor: '#7aa6d1',
        //     showCancel: false
        //   })
        // };

        if (str.msg =='查询成功,扣费') {
          if (str.result.msg =='查询成功,无数据'){
            wx.showModal({
              title: '提示',
              content: '该人员暂无被执行记录',
              confirmColor: '#7aa6d1',
              showCancel: false
            })
          }else{
            console.log(str.result.data.list)
            console.log(str.result.data.count)
            wx.showModal({
            title: '提示',
            content: '查询成功',
            confirmColor: '#7aa6d1',
            showCancel: false
          })
            that.setData({
              list:str.result.data.list,
              tiaoshu:str.result.data.count
            })
          }
        } else {
          wx.showModal({
            title: '提示',
            content: str.result.msg,
            confirmColor: '#7aa6d1',
            showCancel: false
          })
        }
      }
    })
  },
  // submitBtn: function () {
  //   var that = this;
  //   console.log("name" + that.data.ocrName)
  //   console.log("cardid" + that.data.ocrIdNumber)
  //     wx.request({
  //       url: pathUrl + '/rest/fk/dotongdun',
  //       data: {
  //         realName: that.data.ocrName,
  //         cardNo: that.data.ocrIdNumber,
  //         bankCardNo: that.data.bankcardno,
  //         mobile: that.data.mobile,

  //       },
  //       method: 'POST',
  //       success: function (res) {
  //         console.log(res)
  //         console.log(typeof (res.data.data.rmContent))
  //         var str = res.data.data.rmContent,
  //           arr = str.split(",");
  //         console.log(str);
  //         console.log(arr);
  //         console.log(typeof (arr[2]));
  //         console.log(arr[2]);
  //         var str1 = arr[2];
  //         var arr1 = str1.split(':');
  //         console.log(typeof (arr1))
  //         console.log(arr1)
  //         console.log(arr1[4])
  //         if (res.data.ok) {
  //           // wx.showModal({
  //           //     title: '提示',
  //           //     content: "查询"+ res.data.message,
  //           //     showCancel: false
  //           // })
  //           that.setData({
  //             queryResults: res.data.data.rmBy7,//查询结果
  //             violationTimes: arr1[4],//信用分数

  //           })
  //         } else {
  //           wx.showModal({
  //             title: '提示',
  //             content: '获取数据失败',
  //             confirmColor: '#7aa6d1',
  //             showCancel: false
  //           })
  //         }
  //       }
  //     })
  //   else {
  //     wx.showModal({
  //       title: '提示',
  //       content: '获取数据失败',
  //       confirmColor: '#7aa6d1',
  //       showCancel: false
  //     })
  //   }
  // },






  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (e) {
    var openId = wx.getStorageSync('openId');
    this.setData({
      openId: openId
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
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