var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    firstin: '0',
    pathUrl: pathUrl,
    openId: '',
    orderNo: '',
    proNo: '',
    id: '',
    proIsdb: '', //是否有担保人
    ocrName: '', //ocr姓名
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
    orderAmount: '',
    checkedValue: '', //婚姻情况
    spouseName: '', //配偶姓名
    spouseTelephone: '', //联系电话
    urgent: '', //紧急联系人
    urgentTelephone: '', //紧急联系人联系电话
    urgent2: '', //紧急联系人
    urgentTelephone2: '', //紧急联系人联系电话
    orderEmerList: [], //紧急联系人列表
    village: '', //小区
    acreage: '', //面积
    money: '', //金额
    array: ['3', '6', '12', '24', '36'],
    array1: ['0.89', '0.99', "1.19", '1.28', '1.48', '2.48'],
    pickerIndex: '4', //选择期数index
    orderPeriod: '36', //期数
    pickerIndex1: '1', //选择期数index
    orderPeriod1: '0.99', //期数
    howLong: '', //借多久
    guaranteeName: '', //担保人姓名
    guaranteeIdCard: '', //担保人身份照
    guaranteePhone: '', //担保人电话
    fxjProAttList: [],
    flag: true, //删除图片防止重复提交
    isSubmit: true, //防止重复提交
    orderBy1: '',
    orderBy2: '',
    orderBy3: '',
    orderBy4: '',
    // orderBy5: '本科', 
    // orderBy6: '厦门找对科技', 
    // orderBy7: '厦门市湖里区高崎北路', 
    // orderBy8:'20k', 
    // orderBy9:'62321118700',
    // orderBy10: '',
    // orderBy11: '',
    // orderBy12: '',
    // orderBy13: '',
    // orderBy14: '',
    // orderBy15: '',
    // orderBy16: '',
    // orderBy17: '',
    // orderBy18: '',
    // orderBy19: '',
    // orderBy20: '',
    // orderBy21: '亲属',
    // orderBy22: '',
    // orderBy23: '',
    // orderBy24: '',
    // orderBy25: '',
    // orderBy26: '',
    // orderBy27: '',
    // orderBy28: '',
    // orderBy29: '',
    // orderBy30: '',
    // orderBy31: '',
    // orderBy32: '',
    // orderBy33: '',
    // orderBy34: '',
    // orderBy35: '',
    // orderBy36: '',
    // orderBy37: '',
    // orderBy38: '',
    // orderBy39: '',
    // orderBy40: '',
    orderBy5: '',
    orderBy6: '',
    orderBy7: '',
    orderBy8: '',
    orderBy9: '',
    orderBy10: '',
    orderBy11: '',
    orderBy12: '',
    orderBy13: '',
    orderBy14: '',
    orderBy15: '',
    orderBy16: '',
    orderBy17: '',
    orderBy18: '',
    orderBy19: '',
    orderBy20: '',
    orderBy21: '',
    orderBy22: '',
    orderBy23: '',
    orderBy24: '',
    orderBy25: '',
    orderBy26: '',
    orderBy27: '',
    orderBy28: '',
    orderBy29: '',
    orderBy30: '',
    orderBy31: '',
    orderBy32: '',
    orderBy33: '',
    orderBy34: '',
    orderBy35: '',
    orderBy36: '',
    orderBy37: '',
    orderBy38: '',
    orderBy39: '',
    orderBy40: '',
    orderBy51: '',
  },
  onLoad: function (e) {
    console.log(e);
    var openId = wx.getStorageSync('openId');
    this.setData({
      firstin: '1',
      orderNo: e.orderNo,
      proNo: e.proNo,
      id: e.id,
      openId: openId,
    })
    this.orderMainController();
  },
  //ocr名字赋值
  ocrNameInput: function (e) {
    this.setData({
      ocrName: e.detail.value
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
  //居住地址
  residentialAddressInput: function (e) {
    this.setData({
      residentialAddress: e.detail.value
    })
  },
  //联系电话
  telephoneInput: function (e) {
    this.setData({
      telephone: e.detail.value
    })
  },

  //配偶姓名
  spouseNameInput: function (e) {
    this.setData({
      spouseName: e.detail.value
    })
  },
  //联系电话
  spouseTelephoneInput: function (e) {
    this.setData({
      spouseTelephone: e.detail.value
    })
  },
  //紧急联系人1
  orderBy19Input: function (e) {
    this.setData({
      orderBy19: e.detail.value
    })
  },
  //紧急联系人联系电话
  orderBy20Input: function (e) {
    this.setData({
      orderBy20: e.detail.value
    })
  },
  //紧急联系人关系
  orderBy21Input: function (e) {
    this.setData({
      orderBy21: e.detail.value
    })
  },
  orderBy12Input: function (e) {
    this.setData({
      orderBy12: e.detail.value
    })
  },
  //紧急联系人关系
  orderBy13Input: function (e) {
    this.setData({
      orderBy13: e.detail.value
    })
  },
  //紧急联系人2
  orderBy22Input: function (e) {
    this.setData({
      orderBy22: e.detail.value
    })
  },
  //紧急联系人联系电话
  orderBy23Input: function (e) {
    this.setData({
      orderBy23: e.detail.value
    })
  },
  //紧急联系人关系
  orderBy24Input: function (e) {
    this.setData({
      orderBy24: e.detail.value
    })
  },
  //紧急联系人3
  orderBy25Input: function (e) {
    this.setData({
      orderBy25: e.detail.value
    })
  },
  //紧急联系人联系电话
  orderBy26Input: function (e) {
    this.setData({
      orderBy26: e.detail.value
    })
  },
  //紧急联系人关系
  orderBy27Input: function (e) {
    this.setData({
      orderBy27: e.detail.value
    })
  },
  orderBy28Input: function (e) {
    this.setData({
      orderBy28: e.detail.value
    })
  },
  orderBy29Input: function (e) {
    this.setData({
      orderBy29: e.detail.value
    })
  },
  //紧急联系人联系电话
  orderBy20Input: function (e) {
    this.setData({
      orderBy20: e.detail.value
    })
  },
  //紧急联系人关系
  orderBy21Input: function (e) {
    this.setData({
      orderBy21: e.detail.value
    })
  },
  //紧急联系人2
  orderBy22Input: function (e) {
    this.setData({
      orderBy22: e.detail.value
    })
  },
  //紧急联系人联系电话
  orderBy23Input: function (e) {
    this.setData({
      orderBy23: e.detail.value
    })
  },
  //紧急联系人关系
  orderBy24Input: function (e) {
    this.setData({
      orderBy24: e.detail.value
    })
  },
  //紧急联系人3
  orderBy25Input: function (e) {
    this.setData({
      orderBy25: e.detail.value
    })
  },
  //紧急联系人联系电话
  orderBy26Input: function (e) {
    this.setData({
      orderBy26: e.detail.value
    })
  },
  //紧急联系人关系
  orderBy27Input: function (e) {
    this.setData({
      orderBy27: e.detail.value
    })
  },
  orderBy28Input: function (e) {
    this.setData({
      orderBy28: e.detail.value
    })
  },
  orderBy29Input: function (e) {
    this.setData({
      orderBy29: e.detail.value
    })
  },
  orderBy30Input: function (e) {
    this.setData({
      orderBy30: e.detail.value
    })
  },
  orderBy31Input: function (e) {
    this.setData({
      orderBy31: e.detail.value
    })
  },
  orderBy32Input: function (e) {
    this.setData({
      orderBy32: e.detail.value
    })
  },
  orderBy33Input: function (e) {
    this.setData({
      orderBy33: e.detail.value
    })
  },

  orderBy34Input: function (e) {
    this.setData({
      orderBy34: e.detail.value
    })
  },
  orderBy35Input: function (e) {
    this.setData({
      orderBy35: e.detail.value
    })
  },
  orderBy36Input: function (e) {
    this.setData({
      orderBy36: e.detail.value
    })
  },
  orderBy37Input: function (e) {
    this.setData({
      orderBy37: e.detail.value
    })
  },
  orderBy38Input: function (e) {
    this.setData({
      orderBy38: e.detail.value
    })
  },
  orderBy39Input: function (e) {
    this.setData({
      orderBy39: e.detail.value
    })
  },
  orderBy40Input: function (e) {
    this.setData({
      orderBy40: e.detail.value
    })
  },

  //紧急联系人2
  urgent2Input: function (e) {
    this.setData({
      urgent2: e.detail.value
    })
  },
  //紧急联系人联系电话2
  urgentTelephone2Input: function (e) {
    this.setData({
      urgentTelephone2: e.detail.value
    })
  },
  //担保人姓名
  guaranteeNameInput: function (e) {
    this.setData({
      guaranteeName: e.detail.value
    })
  },
  //担保人身份证号
  guaranteeIdCardInput: function (e) {
    this.setData({
      guaranteeIdCard: e.detail.value
    })
  },
  //担保人电话
  guaranteePhoneInput: function (e) {
    this.setData({
      guaranteePhone: e.detail.value
    })
  },
  //小区
  villageInput: function (e) {
    this.setData({
      village: e.detail.value
    })
  },
  //面积
  acreageInput: function (e) {
    this.setData({
      acreage: e.detail.value
    })
  },
  //点选金额
  chooesAmount: function (e) {
    var chooesamount = e.currentTarget.dataset.amount;
    this.setData({
      money: chooesamount
    })
  },
  //金额
  moneyInput: function (e) {
    this.setData({
      money: e.detail.value
    })
  },
  //选择期限
  bindRelationship: function (e) {
    console.log(e)
    var index = e.detail.value;
    var orderPeriod = this.data.array[index];
    console.log(orderPeriod)
    this.setData({
      pickerIndex: index,
      orderPeriod: orderPeriod
    })
  },
  //选择方案
  bindRelationship1: function (e) {
    var index = e.detail.value;
    console.log(e)
    var orderPeriod1 = this.data.array1[index];
    console.log(orderPeriod1)
    this.setData({
      pickerIndex1: index,
      orderPeriod1: orderPeriod1
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

  //银行预留电话
  againInput: function (e) {
    this.setData({
      again: e.detail.value
    })
  },
  //   利率或押金
  orderBy14Input: function (e) {
    this.setData({
      orderBy14: e.detail.value
    })
  },
  //   GPS费或月前租金
  orderBy15Input: function (e) {
    this.setData({
      orderBy15: e.detail.value
    })
  },
  //   服务费或月租金
  orderBy16Input: function (e) {
    this.setData({
      orderBy16: e.detail.value
    })
  },
  //   其他
  orderBy17Input: function (e) {
    this.setData({
      orderBy17: e.detail.value
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
  },  //
  orderBy4Input: function (e) {
    this.setData({
      orderBy4: e.detail.value
    })
  },  //
  orderBy5Input: function (e) {
    this.setData({
      orderBy5: e.detail.value
    })
  },
  //配偶姓名
  spouseNameInput: function (e) {
    this.setData({
      spouseName: e.detail.value
    })
  },
  //配偶证件号码
  spouseNumber: function (e) {
    this.setData({
      spouseNumber: e.detail.value
    })
  },
  //配偶单位名称
  spouseCompanyName: function (e) {
    this.setData({
      spouseCompanyName: e.detail.value
    })
  },
  //配偶单位地址
  spouseCompanyAddress: function (e) {
    this.setData({
      spouseCompanyAddress: e.detail.value
    })
  },
  //配偶月收入
  spouseMonthlyIncome: function (e) {
    this.setData({
      spouseMonthlyIncome: e.detail.value
    })
  },
  //配偶供养人数
  spouseChildren: function (e) {
    this.setData({
      spouseChildren: e.detail.value
    })
  },
  //联系电话
  spouseTelephoneInput: function (e) {
    this.setData({
      spouseTelephone: e.detail.value
    })
  },
  //紧急联系人2
  urgent2Input: function (e) {
    this.setData({
      urgent2: e.detail.value
    })
  },
  //紧急联系人联系电话2
  urgentTelephone2Input: function (e) {
    this.setData({
      urgentTelephone2: e.detail.value
    })
  },
  radioChangeOne: function (e) {
    this.setData({
      orderBy4: e.detail.value
    })
  },
  //车身价
  orderAmountInput: function (e) {
    this.setData({
      orderAmount: e.detail.value
    })
  },
  //担保人姓名
  guaranteeNameInput: function (e) {
    this.setData({
      guaranteeName: e.detail.value
    })
  },
  //担保人身份证号
  guaranteeIdCardInput: function (e) {
    this.setData({
      guaranteeIdCard: e.detail.value
    })
  },
  //担保人电话
  guaranteePhoneInput: function (e) {
    this.setData({
      guaranteePhone: e.detail.value
    })
  },
  //驾驶证所有人
  holder1Input: function (e) {
    this.setData({
      orderBy28: e.detail.value
    })
  },
  //驾驶证注册/发证日期
  dateIssue1Input: function (e) {
    this.setData({
      orderBy29: e.detail.value
    })
  },
  //联系电话
  telephoneInput: function (e) {
    this.setData({
      telephone: e.detail.value
    })
  },
  //驾驶证使用性质
  nature1Input: function (e) {
    this.setData({
      orderBy30: e.detail.value
    })
  },
  //驾驶证住址
  address1Input: function (e) {
    this.setData({
      orderBy31: e.detail.value
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
      url: pathUrl + '/rest/fxjOrderMainController/' + that.data.orderNo,
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
            orderEmerList: data.fxjOrderEmerList,
            village: data.orderCarno,
            acreage: data.orderCartype,
            // orderAmount: data.orderAmount,
            howLong: data.orderPeriod,
            proIsdb: data.proIsdb,
            urgent: data.urgent,
            orderBy1: data.orderBy1,
            orderBy2: data.orderBy2,
            orderBy3: data.orderBy3,
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
      orderBy1: that.data.orderBy1,//担保人姓名
      orderBy2: that.data.orderBy2,//担保人身份证号
      orderBy3: that.data.orderBy3,//担保人电话
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